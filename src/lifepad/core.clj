(ns lifepad.core
  (:require [clojure.core.async :refer [chan go alt! timeout put!]]
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
          (alt! (timeout (:speed @app)) (when (not (:paused @app))
                                          (try
                                            (let [board' (evolve (:board @app))]
                                              (swap! app assoc :board board'))
                                            (catch Exception ex
                                              (log/debug :rendering-exception ex))))
                changes (log/debug :change))))
    (add-watch app :pauser (fn [k r o n]
                             (when (or (not= (:speed o) (:speed n))
                                       (not= (:paused o) (:paused n))
                                       (not= (:stopped o) (:stopped n)))
                               (put! changes :change))))
    (buttons/on-button :press #(let [old (valat (:board @app) (:spot %))
                                     new (if (= old :y) :_ :y)]
                                 (swap! app update-in [:board] board/assoc-at (:spot %) new)))
    app))

(defn pause [app] (swap! app assoc :paused true))
(defn unpause [app] (swap! app assoc :paused false))
(defn toggle [app] (swap! app update-in [:paused] not))
(defn stop [app] (swap! app assoc :stopped true))
(defn set-speed [app speed] (swap! app assoc :speed speed))
(defn set-board [app board] (swap! app assoc :board board))
(defn clear [app] (set-board app boards/blank))

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
  (log/set-level! :warn)
  (bus/replace-with-printer!)
  (make-app))
