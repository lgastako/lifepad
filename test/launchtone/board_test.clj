(ns launchtone.board_test
  (:require [clojure.test :refer :all]
            [launchtone.board :refer :all]))

(deftest t-all-spots
  (is (= 64 (count all-spots))))

(deftest t-assoc-at
  (is (= [[:y :_ :_ :_ :_ :_ :_ :_]
          [:_ :_ :_ :_ :_ :_ :_ :_]
          [:_ :_ :_ :_ :_ :_ :_ :_]
          [:_ :_ :_ :_ :_ :_ :_ :_]
          [:_ :_ :_ :_ :_ :_ :_ :_]
          [:_ :_ :_ :_ :_ :_ :_ :_]
          [:_ :_ :_ :_ :_ :_ :_ :_]
          [:_ :_ :_ :_ :_ :_ :_ :_]]
         (assoc-at empty-board [0 0] :y)))
  (is (= [[:_ :_ :_ :_ :_ :_ :_ :_]
          [:_ :_ :_ :_ :_ :_ :_ :_]
          [:_ :_ :_ :_ :_ :_ :_ :_]
          [:_ :_ :_ :_ :_ :_ :_ :_]
          [:_ :_ :_ :_ :_ :_ :_ :_]
          [:_ :_ :_ :_ :_ :_ :_ :_]
          [:_ :_ :_ :_ :_ :_ :_ :_]
          [:_ :_ :_ :_ :_ :_ :_ :y]]
         (assoc-at empty-board [7 7] :y)))
  (is (= [[:_ :_ :_ :_ :_ :_ :_ :_]
          [:_ :_ :_ :_ :_ :_ :_ :_]
          [:_ :_ :_ :_ :y :_ :_ :_]
          [:_ :_ :_ :_ :_ :_ :_ :_]
          [:_ :_ :_ :_ :_ :_ :_ :_]
          [:_ :_ :_ :_ :_ :_ :_ :_]
          [:_ :_ :_ :_ :_ :_ :_ :_]
          [:_ :_ :_ :_ :_ :_ :_ :_]]
         (assoc-at empty-board [2 4] :y))))

(run-tests)
