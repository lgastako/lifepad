(ns lifepad.core
  (:require [its.log :as log]
            [launchtone.core :as lp]
            [launchtone.utils :as ltu]
            [lifepad.boards :refer :all]
            [lifepad.tools :as tools]))

(log/set-level! :debug)

;; (defmacro dbg[x] `(let [x# ~x] (println "dbg:" '~x "=" x#) x#))

(defn valat [board [y x]]
  ;; (assert (<= 0 x (count board)))
  ;; (assert (<= 0 y (count (get board 0))))
  (get (get board y) x))

(defn dead? [board spot]
  (= :_ (valat board spot)))

(def alive? (comp not dead?))

(defn valid-spot? [[x y]]
  (and (<= 0 x 7)
       (<= 0 y 7)))

(defn occupied? [board spot]
  (not= :_ (valat board spot)))

(defn neighbors [board [x y]]
  (let [ncells [[(inc x) (inc y)]
                [(inc x) y]
                [x (inc y)]
                [(dec x) (dec y)]
                [(dec x) y]
                [x (dec y)]
                [(inc x) (dec y)]
                [(dec x) (inc y)]]
        ;; _ (when (and (= x 0) (= y 0)) (println "ncells: " ncells))
        ncells (filter valid-spot? ncells)
        ;; _ (when (and (= x 0) (= y 0)) (println "valid: " ncells))
        ]
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

(defn paced-set-board! [app ms board]
  (lp/set-board! app board)
  (Thread/sleep ms)
  (evolve board))

(defn diterate! [board ms n]
  (let [app (lp/make-app)
        f (partial paced-set-board! app ms)]
    (iterate f board)))

(defn fp! [board ms]
  (let [app (lp/make-app)
        f (partial paced-set-board! app ms)]
    (loop [board board]
      (f board)
      (let [board' (evolve board)]
        (when (not= board board')
          (recur board'))))))

(defn -main [& _]
  (log/debug :-main)
;;  (take 100 (diterate! glider-board 100))
  (fp! (rand-board) 300)
  (System/exit 0))
