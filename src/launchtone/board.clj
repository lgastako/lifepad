(ns launchtone.board
  (:require [clojure.math.combinatorics :as combo]))

(def all-spots (combo/cartesian-product
                (range 8)
                (range 8)))

(def empty-row (into [] (take 8 (repeat :e))))
(def empty-board (into [] (take 8 (repeat empty-row))))

