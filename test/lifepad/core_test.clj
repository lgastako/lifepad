(ns lifepad.core-test
  (:require [clojure.test :refer :all]
            [lifepad.core :refer :all]))

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
  (is (= (valat glider-board [0 0]) :_))
  (is (= (valat glider-board [7 0]) :_))
  (is (= (valat glider-board [0 7]) :_))
  (is (= (valat glider-board [7 7]) :_))
  ;; Inner corners
  (is (= (valat glider-board [3 3]) :_))
  (is (= (valat glider-board [3 4]) :_))
  (is (= (valat glider-board [4 3]) :_))
  (is (= (valat glider-board [4 4]) :_))
  ;; A few hits for good measure :)
  (is (= (valat glider-board [0 6]) :y))
  (is (= (valat glider-board [1 5]) :y))
  (is (= (valat glider-board [2 5]) :y))
  (is (= (valat glider-board [2 7]) :y)))
;; Good enough for me.

(deftest test-dead?
  (is (= (dead? glider-board [0 0]) true))
  (is (= (dead? glider-board [7 7]) true))
  (is (= (dead? glider-board [0 6]) false))
  (is (= (dead? glider-board [2 7]) false))
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
  (is (= 0 (neighbors glider-board [0 0]))))

(deftest test-spawn?
  (is (= false (spawn? glider-board [0 0]))))

(deftest block-does-not-move
  (is (= block-board (iterate-board block-board))))

(deftest test-fixed-point-block
  (is (= block-board (fixed-point block-board iterate-board))))

(run-tests)

