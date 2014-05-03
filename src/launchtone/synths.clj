(ns launchtone.synths
  (:require [overtone.synth.sts :refer [prophet]]))

(defn ex1 []
  (prophet :freq 110 :decay 5 :rq 0.6 :cutoff-freq 2000))

(defn ex2 []
  (prophet :freq 130 :decay 5 :rq 0.6 :cutoff-freq 2000))
