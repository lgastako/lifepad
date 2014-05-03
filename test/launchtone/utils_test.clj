(ns launchtone.utils-test
  (:use [clojure.test :refer :all]
        [launchtone.utils :only [enumerate]]))

(deftest test-enumerate
  (testing "Enumeration."
    (is (= [[0 :a] [1 :b] [2 :c]] (enumerate [:a :b :c])))))

;;(run-tests)
