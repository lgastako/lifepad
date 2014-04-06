(ns lifepad.core
  (:require [launchtone.core :as pad]
            [launchtone.utils :as ltu]
            [lifepad.tools :as tools]
            [overtone.config.log :as log]))

(log/set-level! :debug)

(def blank-board
  [[:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]])

(def glider-board
  [[:_ :_ :_ :_ :_ :_ :y :_]
   [:_ :_ :_ :_ :_ :y :_ :_]
   [:_ :_ :_ :_ :_ :y :y :y]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]])

(def p2-blinker-board
  [[:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :y :y :y :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :y :y :y :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]])

(def block-board
  [[:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :y :y :_ :_ :y :y :_]
   [:_ :y :y :_ :_ :y :y :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :y :y :_ :_ :y :y :_]
   [:_ :y :y :_ :_ :y :y :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]])

(def lets-see-1
  [[:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :y :y :_ :_ :y :y :_]
   [:_ :y :y :_ :_ :y :y :_]
   [:_ :_ :_ :y :y :_ :_ :_]
   [:_ :_ :_ :y :y :_ :_ :_]
   [:_ :y :y :_ :_ :y :y :_]
   [:_ :y :y :_ :_ :y :y :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]])

(defmacro dbg[x] `(let [x# ~x] (println "dbg:" '~x "=" x#) x#))

(defn valat [board [y x]]
  (assert (<= 0 x (count board)))
  (assert (<= 0 y (count (get board 0))))
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

(defn iterate-board [board]
  (tools/next-board-by-xy board nextval))

(defn iterate-boards
  ([board n]
     (iterate-boards board n identity))
  ([board n f]
     (if (<= n 0)
       board
       (let [board' (iterate-board board)
             board'' (f board')]
         (recur board'' (dec n) f)))))

(defn piterate [board n]
  (iterate-boards board n #(do (pprint %) %)))

(defn -main []
  (-> (pad/make-app)
      (pad/set-board! glider-board)))
