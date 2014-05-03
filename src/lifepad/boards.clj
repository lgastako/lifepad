(ns lifepad.boards
  (:require [launchtone.board :as board]))

(defn rand-board
  ([] (rand-board 0.5))
  ([chance]
     (vec (for [y (range 8)]
            (vec (for [x (range 8)]
                   (if (>= (rand) chance) :y :_)))))))

;;(def blank board/empty-board)

;; for ease of copy/paste, for now:

(def blank
  [[:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]])

(def full
  [[:y :y :y :y :y :y :y :y]
   [:y :y :y :y :y :y :y :y]
   [:y :y :y :y :y :y :y :y]
   [:y :y :y :y :y :y :y :y]
   [:y :y :y :y :y :y :y :y]
   [:y :y :y :y :y :y :y :y]
   [:y :y :y :y :y :y :y :y]
   [:y :y :y :y :y :y :y :y]])

(def glider
  [[:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :y :_ :_]
   [:_ :_ :_ :_ :y :_ :_ :_]
   [:_ :_ :_ :_ :y :y :y :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]])

(def p2-blinker
  [[:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :y :y :y :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :y :y :y :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]])

(def block
  [[:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :y :y :_ :_ :y :y :_]
   [:_ :y :y :_ :_ :y :y :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :y :y :_ :_ :y :y :_]
   [:_ :y :y :_ :_ :y :y :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]])

(def toads
  [[:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :y :y :y :_ :_ :_]
   [:_ :y :y :y :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :y :y :y :_]
   [:_ :_ :_ :y :y :y :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]])

(def grower1
  [[:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :y :_]
   [:_ :_ :_ :_ :y :_ :y :y]
   [:_ :_ :_ :_ :y :_ :y :_]
   [:_ :_ :_ :_ :y :_ :_ :_]
   [:_ :_ :y :_ :_ :_ :_ :_]
   [:y :_ :y :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]])

(def grower2
  [[:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :y :y :y :_ :y :_ :_]
   [:_ :y :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :y :y :_ :_]
   [:_ :_ :y :y :_ :y :_ :_]
   [:_ :y :_ :y :_ :y :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]])

(def x
  [[:y :_ :_ :_ :_ :_ :_ :y]
   [:_ :y :_ :_ :_ :_ :y :_]
   [:_ :_ :y :_ :_ :y :_ :_]
   [:_ :_ :_ :y :y :_ :_ :_]
   [:_ :_ :_ :y :y :_ :_ :_]
   [:_ :_ :y :_ :_ :y :_ :_]
   [:_ :y :_ :_ :_ :_ :y :_]
   [:y :_ :_ :_ :_ :_ :_ :y]])

(def beacon
  [[:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :y :y :_ :_ :_ :_ :_]
   [:_ :y :y :_ :_ :_ :_ :_]
   [:_ :_ :_ :y :y :_ :_ :_]
   [:_ :_ :_ :y :y :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]])

(def r-pentomino
  [[:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :y :y :_ :_ :_]
   [:_ :_ :y :y :_ :_ :_ :_]
   [:_ :_ :_ :y :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]])

(def acorn
  [[:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :y :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :y :_ :_ :_]
   [:_ :y :y :_ :_ :y :y :y]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]])

(def hmmm1
  [[:_ :_ :_ :_ :y :y :y :_]
   [:_ :_ :y :_ :_ :_ :_ :y]
   [:_ :_ :y :_ :_ :_ :_ :y]
   [:_ :_ :y :_ :_ :_ :_ :y]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :y :y :y :_]
   [:y :y :_ :_ :_ :_ :_ :_]
   [:y :y :_ :_ :_ :_ :_ :_]])

(def octagon2
  [[:_ :_ :_ :y :y :_ :_ :_]
   [:_ :_ :y :_ :_ :y :_ :_]
   [:_ :y :_ :_ :_ :_ :y :_]
   [:y :_ :_ :_ :_ :_ :_ :y]
   [:y :_ :_ :_ :_ :_ :_ :y]
   [:_ :y :_ :_ :_ :_ :y :_]
   [:_ :_ :y :_ :_ :y :_ :_]
   [:_ :_ :_ :y :y :_ :_ :_]])

(def boxed-smiley
  [[:_ :y :y :y :y :y :y :_]
   [:y :_ :_ :_ :_ :_ :_ :y]
   [:y :_ :y :_ :_ :y :_ :y]
   [:y :_ :_ :_ :_ :_ :_ :y]
   [:y :_ :y :_ :_ :y :_ :y]
   [:y :_ :_ :y :y :_ :_ :y]
   [:y :_ :_ :_ :_ :_ :_ :y]
   [:_ :y :y :y :y :y :y :_]])

(def stable1
  [[:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :y :y :_ :_]
   [:_ :_ :_ :y :_ :_ :y :_]
   [:_ :_ :_ :_ :y :_ :y :_]
   [:_ :_ :_ :_ :_ :y :_ :_]
   [:y :y :_ :_ :_ :_ :_ :_]
   [:y :_ :y :_ :_ :_ :_ :_]
   [:_ :y :y :_ :_ :_ :_ :_]])
