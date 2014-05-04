(ns lifepad.core
  (:require [clojure.core.async :refer [chan go alts! timeout put!]]
            [clojure.pprint :refer [pprint]]
            [its.log :as log]
            [its.bus :as bus]
            [launchtone.board :as board]
            [launchtone.buttons :as buttons]
            [launchtone.core :as lp]
            [launchtone.devices :as devices]
            [launchtone.utils :as ltu]
            [lifepad.boards :as boards]
            [lifepad.sounds :as sounds]
            [lifepad.tools :as tools]))

;; To capture all logging in your REPL:
;; (alter-var-root #'*out* (constantly *out*))

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

(defn select-board [app f]
  (let [boards (:boards @app)
        index (mod (f (:index @app)) (count boards))]
    (swap! app assoc
           :board (nth boards index)
           :index index)))

(defn next-board [app] (select-board app inc))
(defn prev-board [app] (select-board app dec))

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

(defn add-trigger [app spot key action]
  (swap! app assoc-in [:triggers spot key] action))

;; need dissoc-in
;; (defn remove-trigger [app spot key]
;;   (swap! app dissoc-in [:triggers spot key]))

(defn new-actives [o n]
  (filter #(let [nv (valat (:board n) %)
                 ov (valat (:board o) %)]
             (and (= ov :_)
                  (not= ov nv)))
          board/all-spots))

(defn init [& {:keys [receiver board boards speed triggers]
               :or {board boards/glider
                    boards (filter (partial not= boards/blank)
                                   (vec boards/all))
                    speed 250}}]
  (let [receiver (if receiver receiver (second (devices/select)))
        app (atom {:receiver receiver
                   :board board
                   :boards boards
                   :index 0
                   :speed speed
                   :paused true
                   :stopped false
                   :triggers triggers})
        changes (chan)]
    (board/render-board! receiver board)
    (board/auto-render receiver :auto app [:board])
    (go (while (not (:stopped @app))
          (let [[v c] (alts! [(timeout (:speed @app)) changes])]
            (when (and (not= changes c)
                       (not (:paused @app)))
              (swap! app assoc :board (evolve (:board @app)))))))
    (add-watch app :pauser (fn [k r o n]
                             (when (or (not= (:speed o) (:speed n))
                                       (not= (:paused o) (:paused n))
                                       (not= (:stopped o) (:stopped n)))
                               (put! changes :change))))
    (add-watch app :triggerer (fn [k r o n]
                                (when-let [triggers (:triggers @app)]
                                  (when-let [nas (new-actives o n)]
                                    (doall (for [spot nas]
                                             (when-let [spot-triggers (triggers spot)]
                                               (doall (for [[_ trigger] spot-triggers]
                                                        (trigger))))))))))
    (buttons/on-button :press #(let [old (valat (:board @app) (:spot %))
                                     new (if (= old :y) :_ :y)]
                                 (swap! app update-in [:board] board/assoc-at (:spot %) new)))
    (buttons/on-control :pause-button #(when (= :mixer (:control %)) (toggle app)))
    (buttons/on-control :clear-button #(when (= :session (:control %)) (clear app)))

    (buttons/on-control :prev-board #(when (= :left (:control %)) (prev-board app)))
    (buttons/on-control :next-board #(when (= :right (:control %)) (next-board app)))

    ;; (buttons/on-control :faster-button #(when (= :up (:control %)) (faster app)))
    ;; (buttons/on-control :slower-button #(when (= :down (:control %)) (slower app)))
    app))

(defn -main []
  (log/set-level! :warn)
  (bus/replace-with-printer!)
  (let [app (init)]
    (println "Press enter or return to quit.")
    (read-line)
    (stop app)
    app))

(defn- develop []
  (log/set-level! :debug)
  (bus/replace-with-printer!)
  (init))

;; delete me
(defn tmp1 [app]
  (add-trigger app [0 0] :ding sounds/ding))

(defn tmp2 [app]
  (doall (for [spot board/all-spots]
           (add-trigger app spot :ding sounds/ding))))
