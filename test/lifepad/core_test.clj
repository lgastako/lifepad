(ns lifepad.core-test
  (:require [clojure.test :refer :all]
            [lifepad.core :refer :all]
            [lifepad.boards :as boards]))

(def enumerate (partial map-indexed #(vec %&)))

(def board-range (range 8))

(def test-board
  (vec (for [row board-range]
     (vec (for [col board-range]
            [row col])))))

(deftest test-valat
  ;; Outer corners
  (is (= (valat test-board [0 0]) [0 0]))
  (is (= (valat test-board [0 7]) [0 7]))
  (is (= (valat test-board [7 0]) [7 0]))
  (is (= (valat test-board [7 7]) [7 7]))
  ;; Inner corners
  (is (= (valat test-board [3 3]) [3 3]))
  (is (= (valat test-board [3 4]) [3 4]))
  (is (= (valat test-board [4 3]) [4 3]))
  (is (= (valat test-board [4 4]) [4 4]))
  ;; A real board
  ;; Outer corners
  (is (= (valat boards/glider [0 0]) :_))
  (is (= (valat boards/glider [7 0]) :_))
  (is (= (valat boards/glider [0 7]) :_))
  (is (= (valat boards/glider [7 7]) :_))
  ;; Inner corners
  (is (= (valat boards/glider [3 3]) :_))
  (is (= (valat boards/glider [3 4]) :y))
  (is (= (valat boards/glider [4 3]) :_))
  (is (= (valat boards/glider [4 4]) :_))
  ;; A few hits for good measure :)
  (is (= (valat boards/glider [1 5]) :y))
  (is (= (valat boards/glider [2 4]) :y))
  (is (= (valat boards/glider [3 5]) :y))
  (is (= (valat boards/glider [3 6]) :y)))
;; Good enough for me.

(deftest test-dead?
  (is (= (dead? boards/glider [0 0]) true))
  (is (= (dead? boards/glider [7 7]) true))
  (is (= (dead? boards/glider [1 5]) false))
  (is (= (dead? boards/glider [2 4]) false))
  (is (= 0 (count (remove false? (map (partial dead? test-board)
                                      (for [[rownum row] (enumerate test-board)
                                            [colnum col] (enumerate row)]
                                        [rownum colnum])))))))

(deftest test-iterate-glider
  (is (=  [[:_ :_ :_ :_ :_ :_ :_ :_]
           [:_ :_ :_ :_ :_ :y :_ :y]
           [:_ :_ :_ :_ :_ :y :y :_]
           [:_ :_ :_ :_ :_ :_ :y :_]
           [:_ :_ :_ :_ :_ :_ :_ :_]
           [:_ :_ :_ :_ :_ :_ :_ :_]
           [:_ :_ :_ :_ :_ :_ :_ :_]
           [:_ :_ :_ :_ :_ :_ :_ :_]])))

(deftest test-neighbors
  (is (= 0 (neighbors boards/glider [0 0]))))

(deftest test-spawn?
  (is (= false (spawn? boards/glider [0 0]))))

(deftest block-does-not-move
  (is (= boards/block (evolve boards/block))))

(deftest nextval-values
  (is (= :y (nextval boards/glider [4 5])))
  (is (= :_ (nextval boards/block [0 0]))))

(run-tests)
