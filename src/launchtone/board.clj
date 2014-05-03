(ns launchtone.board
  (:require [clojure.math.combinatorics :as combo]
            [its.log :as log]
            [launchtone.colors :as colors]
            [launchtone.send :refer [send-redgreen!]]
            [launchtone.utils :refer [enumerate]]))

(def all-spots (combo/cartesian-product
                (range 8)
                (range 8)))

(def empty-spot :_)

(def empty-row (into [] (take 8 (repeat empty-spot))))
(def empty-board (into [] (take 8 (repeat empty-row))))

(defn assoc-at [board [r c] val]
  (let [row (nth board r)
        row' (assoc row c val)]
    (assoc board r row')))

(defn render-colors! [receiver board]
  (doseq [[r row] (enumerate board)
          [c [red green]] (enumerate row)]
    (send-redgreen! receiver r c red green)))

(defn render-board! [receiver board]
  (render-colors! receiver (colors/board->colors board)))

(defn auto-render [receiver key state path]
  (add-watch state key (fn [k r o n]
                         (log/debug :auto-render {:n n})
                         (when (not= (:board o) (:board n))
                           (render-board! receiver (:board n))))))
