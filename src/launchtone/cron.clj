(ns launchtone.cron
  (:require [overtone.at-at :as at-at]))

(defn every [app ms f]
  (at-at/every ms f (@app :worker-pool)))

(defn after [app ms f]
  (at-at/after ms f (@app :worker-pool)))
