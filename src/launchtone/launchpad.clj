(ns launchtone.launchpad
  (:require [its.log :as log]))

(defn point->note
  "Convert a column-row vector into the appropriate note to send to the Launchpad."
  [col row]
  (+ (* 0x10 row) col))

(defn note->point
  "Convert a note from the Launchpad into a column-row vector."
  [note]
  (let [col (bit-and-not note 0xfff0)
        row (bit-shift-right note 0x04)]
    [row col]))

(defn colors->velocity
  "Convert color to the appropriate velocity for the Launchpad."
  [red green]
  ;; Bit 6 must be 0
  ;; 5..4 are the green values
  ;; 3 clear (ignored for now)
  ;; 2 copy (ignored for now)
  ;; 1..0 red
  (let [flags 0 ;; for now
        red (or red 0)
        green (or green 0)]
    (+ (* 0x10 green) red flags)))
