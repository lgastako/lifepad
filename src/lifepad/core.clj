(ns lifepad.core
  (:require [clojure.core.async :refer [chan go alts! timeout put!]]
            [its.log :as log]
            [its.bus :as bus]
            [launchtone.board :as board]
            [launchtone.buttons :as buttons]
            [launchtone.core :as lp]
            [launchtone.devices :as devices]
            [launchtone.utils :as ltu]
            [lifepad.boards :as boards]
            [lifepad.tools :as tools]))

(defn valat [board [y x]]
  (assert (<= 0 x (count board)))
  (assert (<= 0 y (count (get board 0))))
  (get (get board y) x))

(defn dead? [board spot]  (= :_ (valat board spot)))

(def alive? (comp not dead?))

(defn occupied? [board spot] (not= :_ (valat board spot)))

(defn valid-spot? [[x y]]
  (and (<= 0 x 7)
       (<= 0 y 7)))

(defn neighbors [board [x y]]
  (let [ncells [[(inc x) (inc y)]
                [(inc x) y]
                [x (inc y)]
                [(dec x) (dec y)]
                [(dec x) y]
                [x (dec y)]
                [(inc x) (dec y)]
                [(dec x) (inc y)]]
        ncells (filter valid-spot? ncells)]
    (apply + (for [spot ncells] (if (occupied? board spot) 1 0)))))

(defn spawn? [board spot]
  (let [ns (neighbors board spot)]
    (or (and (alive? board spot)
             (or (= 2 ns) (= 3 ns)))
        (and (dead? board spot)
             (= 3 ns)))))

(defn nextval [board spot]
  (if (spawn? board spot) :y :_))

(defn evolve [board]
  (tools/next-board-by-f board nextval))

(defn pause [app] (swap! app assoc :paused true))
(defn unpause [app] (swap! app assoc :paused false))
(defn toggle [app] (swap! app update-in [:paused] not))
(defn stop [app] (swap! app assoc :stopped true))
(defn set-speed [app speed] (swap! app assoc :speed speed))
(defn set-board [app board] (swap! app assoc :board board))
(defn clear [app] (set-board app boards/blank))

(defn inc-by [n] #(+ % n))
(defn dec-by [n] #(- % n))

(def ^:private speed-delta 50)
(defn faster [app] (swap! app update-in [:speed]
                          (dec-by speed-delta)))
(defn slower [app] (swap! app update-in [:speed]
                          (max 0 (inc-by speed-delta))))

(defn dumpboard [app]
  (pprint (:board @app)))

(def ^:private captured (atom nil))

(defn capture [app]
  (reset! captured (:board @app)))

(defn restore [app]
  (set-board app @captured))

(defn init [receiver & {:keys [board speed]
                        :or {board boards/glider
                             speed 250}}]
  (let [app (atom {:receiver receiver
                   :board board
                   :speed speed
                   :paused false
                   :stopped false})
        changes (chan)]
    (board/render-board! receiver board)
    (board/auto-render receiver :auto app [:board])
    (go (while (not (:stopped @app))
          (let [[v c] (alts! [(timeout (:speed @app)) changes])]
            (when (and (not= changes c)
                       (not :paused @app))
              (swap! app assoc :board (evolve (:board @app)))))))
    (add-watch app :pauser (fn [k r o n]
                             (when (or (not= (:speed o) (:speed n))
                                       (not= (:paused o) (:paused n))
                                       (not= (:stopped o) (:stopped n)))
                               (put! changes :change))))
    (buttons/on-button :press #(let [old (valat (:board @app) (:spot %))
                                     new (if (= old :y) :_ :y)]
                                 (swap! app update-in [:board] board/assoc-at (:spot %) new)))
    (buttons/on-control :pause-button #(when (= :mixer (:control %)) (toggle app)))
    (buttons/on-control :clear-button #(when (= :session (:control %)) (clear app)))
    ;; (buttons/on-control :faster-button #(when (= :up (:control %)) (faster app)))
    (buttons/on-control :slower-button #(when (= :down (:control %)) (slower app)))
    app))

(defn- make-app []
  (let [[_ receiver] (devices/select)]
    (init receiver)))

(defn -main []
  (log/set-level! :warn)
  (bus/replace-with-printer!)
  (let [app (make-app)]
    (println "Press enter or return to quit.")
    (read-line)
    (stop app)
    app))

(defn- develop []
  (log/set-level! :debug)
  (bus/replace-with-printer!)
  (make-app))
