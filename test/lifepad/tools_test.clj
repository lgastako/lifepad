(ns lifepad.tools-test
  (:require [clojure.test :refer :all]
            [lifepad.core :refer :all]
            [lifepad.tools :refer :all]))

(deftest test-fixed-point-blank
  (is (= blank-board (fixed-point blank-board nextval))))

(deftest test-fixed-point-block
  (is (= block-board (fixed-point block-board nextval))))

(run-tests)
