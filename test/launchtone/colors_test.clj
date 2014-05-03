(ns launchtone.colors-test
  (:use [clojure.test :refer :all]
        [launchtone.colors :only [board->colors]]))

(deftest test-board->colors
  (testing "Color selection of board->colors"
    (is (= [[[0 0] [3 0]]
            [[0 3] [0 0]]]
           (board->colors [[:e :r]
                           [:g :e]])))))

;;(run-tests)

