(ns lifepad.core
  (:require [launchtone.core :as pad]))

(def slider-board
  [[:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :y :_]
   [:_ :_ :_ :_ :_ :y :_ :_]
   [:_ :_ :_ :_ :_ :y :y :y]])

(defn valat [board [x y]]
  ((board y) x))

(defn dead? [board spot]
  (= :_ (valat board spot)))

(def alive? (comp not dead?))

(defn valid-spot? [[x y]]
  (and (<= 0 x 8)
       (<= 0 y 8)))

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
    (apply + (for [spot ncells]
               (if [(= :X (valat board spot))]
                 1
                 0)))))

(defn iterate-board [board]
  (letfn [(nextval [spot]
            (let [ns (neighbors board spot)]
              (println "ns: " ns)
              (if (or (and (alive? board spot)
                           (or (= 2 ns) (= 3 ns)))
                      (and (dead? board spot)
                           (= 3 ns)))
                :y
                :_)))]
    (vec (for [x (range 0 8)]
           (vec (for [y (range 0 8)]
                  (nextval [x y])))))))

(println)
(println "slider board: " slider-board)
;; (def slider1-board (iterate-board slider-board))

(defn -main []
  (-> (pad/make-app)
      (pad/set-board! slider-board)))
