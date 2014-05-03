(ns lifepad.tools-test
  (:require [clojure.test :refer :all]
            [lifepad.core :refer [nextval]]
            [lifepad.boards :as boards]
            [lifepad.tools :refer :all]))

(deftest test-next-board-by-f
  (is (= boards/blank (next-board-by-f boards/blank nextval))))

;; (deftest test-fixed-point-blank
;;   (is (= boards/blank (fixed-point boards/blank nextval))))

;; (deftest test-fixed-point-block
;;   (is (= boards/block (fixed-point boards/block nextval))))

(run-tests)
