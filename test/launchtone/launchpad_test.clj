(ns launchtone.launchpad-test
  (:require [clojure.test :refer :all]
            [launchtone.launchpad :refer :all]))

(deftest test-point->note
  (testing "point->note"
    (is (= 0 (point->note 0 0)))
    (is (= 1 (point->note 1 0)))
    (is (= 16 (point->note 0 1)))
    (is (= 17 (point->note 1 1)))
    (is (= 32 (point->note 0 2)))
    (is (= 34 (point->note 2 2)))))

(deftest test-note->point
  (testing "note->point"
    (is (= [0 0] (note->point 0)))
    (is (= [0 1] (note->point 1)))
    (is (= [1 0] (note->point 16)))
    (is (= [1 1] (note->point 17)))
    (is (= [2 0] (note->point 32)))
    (is (= [2 2] (note->point 34)))))

(deftest test-colors->velocity
  (testing "colors->velocity"
    (is (= 0 (colors->velocity 0 0)))
    (is (= 3 (colors->velocity 3 0)))
    (is (= 16 (colors->velocity 0 1)))
    (is (= 34 (colors->velocity 2 2)))))

;;(run-tests)
