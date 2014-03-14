(ns lifepad.core-test
  (:require [clojure.test :refer :all]
            [lifepad.core :refer :all]))

(def enumerate (partial map-indexed #(vec %&)))

(def board-range (range 8))

(def test-board
  (vec (for [row board-range]
     (vec (for [col board-range]
            [col row])))))

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
  (is (= (valat slider-board [0 0]) :_))
  (is (= (valat slider-board [7 0]) :_))
  (is (= (valat slider-board [0 7]) :_))
  (is (= (valat slider-board [7 7]) :y))
  (is (= (valat slider-board [4 4]) :_))
  (is (= (valat slider-board [5 6]) :y)))
;; Good enough for me.

(deftest test-dead?
  (is (= (dead? slider-board [0 0]) true))
  (is (= (dead? slider-board [7 7]) false))
  (is (= 0 (count (remove false? (map (partial dead? test-board)
                                      (for [[rownum row] (enumerate test-board)
                                            [colnum col] (enumerate row)]
                                        [rownum colnum])))))))

(run-tests)
