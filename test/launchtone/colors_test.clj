(ns launchtone.colors-test
  (:require [clojure.test :refer :all]
            [launchtone.colors :refer :all]
            [lifepad.boards :refer :all]))

(deftest test-board->colors
  (is (= [[nil nil nil nil nil nil nil nil]
          [nil nil nil nil nil [3 3] nil nil]
          [nil nil nil nil [3 3] nil nil nil]
          [nil nil nil nil [3 3] [3 3] [3 3] nil]
          [nil nil nil nil nil nil nil nil]
          [nil nil nil nil nil nil nil nil]
          [nil nil nil nil nil nil nil nil]
          [nil nil nil nil nil nil nil nil]]
         (board->colors glider-board))))

(run-tests)
