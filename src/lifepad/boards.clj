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

(def four-boats
  [[:_ :_ :_ :_ :y :_ :_ :_]
   [:_ :_ :_ :y :_ :y :_ :_]
   [:_ :_ :_ :y :y :_ :y :_]
   [:_ :y :y :_ :_ :y :_ :y]
   [:y :_ :y :_ :_ :y :y :_]
   [:_ :y :_ :y :y :_ :_ :_]
   [:_ :_ :y :_ :y :_ :_ :_]
   [:_ :_ :_ :y :_ :_ :_ :_]])

(def b-heptomino
  [[:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :y :_ :_ :_ :_ :_]
   [:_ :_ :y :y :_ :_ :_ :_]
   [:_ :_ :_ :y :y :_ :_ :_]
   [:_ :_ :y :y :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]])

(def biclock
  [[:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :y :_ :_ :_ :_]
   [:_ :y :y :_ :_ :_ :_ :_]
   [:_ :_ :_ :y :y :_ :_ :_]
   [:_ :_ :y :_ :_ :_ :y :_]
   [:_ :_ :_ :_ :y :y :_ :_]
   [:_ :_ :_ :_ :_ :_ :y :y]
   [:_ :_ :_ :_ :_ :y :_ :_]])

(def glide-gen
  [[:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :y :_ :_]
   [:_ :_ :_ :_ :y :_ :y :_]
   [:_ :_ :_ :y :_ :_ :y :_]
   [:_ :_ :_ :_ :y :y :_ :_]
   [:_ :_ :_ :y :_ :_ :y :_]
   [:_ :_ :_ :y :_ :y :_ :_]
   [:_ :_ :_ :_ :y :_ :_ :_]])

(def bi-pole
  [[:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :y :y :_ :_ :_ :_ :_]
   [:_ :y :_ :y :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :y :_ :y :_ :_]
   [:_ :_ :_ :_ :y :y :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]])

(def blinkers-bit-pole
  [[:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :y :_ :_ :_ :y :y :_]
   [:_ :y :_ :_ :_ :_ :y :_]
   [:y :_ :_ :y :_ :y :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:y :_ :y :_ :y :y :y :_]
   [:y :y :_ :_ :_ :_ :_ :_]])

(def block-and-glider
  [[:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :y :y :_ :_ :_ :_]
   [:_ :_ :_ :y :_ :y :_ :_]
   [:_ :_ :_ :_ :y :y :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]])

(def bookend
  [[:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :y :y :y :_ :_]
   [:_ :_ :y :_ :_ :y :_ :_]
   [:_ :_ :y :y :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]])

(def bookends
  [[:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :y :y :_ :y :y :_ :_]
   [:_ :_ :y :_ :y :_ :_ :_]
   [:y :_ :y :_ :y :_ :y :_]
   [:y :y :_ :_ :_ :y :y :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]])

(def bullet-heptomino
  [[:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :y :y :y :_ :_ :_]
   [:_ :_ :y :y :y :_ :_ :_]
   [:_ :_ :_ :y :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]])

(def bun
  [[:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :y :y :y :_ :_ :_]
   [:_ :_ :y :_ :_ :y :_ :_]
   [:_ :_ :_ :y :y :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]])

(def bunnies
  [[:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :y :_ :y :_]
   [:y :_ :y :_ :_ :y :_ :_]
   [:_ :y :_ :_ :_ :y :_ :_]
   [:_ :y :_ :_ :_ :_ :_ :y]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]])

(def by-flops
  [[:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :y :_ :_]
   [:_ :_ :_ :y :_ :y :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :y]
   [:_ :_ :y :y :y :y :y :_]
   [:_ :_ :_ :_ :_ :_ :_ :y]
   [:_ :_ :_ :y :_ :y :_ :_]
   [:_ :_ :_ :_ :_ :y :_ :_]])

(def century
  [[:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :y :_ :_ :_]
   [:_ :_ :_ :y :y :y :_ :_]
   [:_ :_ :y :y :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]])

(def c-heptomino
  [[:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :y :_ :_ :_]
   [:_ :_ :_ :y :y :y :_ :_]
   [:_ :_ :y :y :y :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]])

(def cheshire-cat
  [[:_ :_ :_ :_ :_ :_ :_ :_]
   [:_ :_ :y :y :y :y :_ :_]
   [:_ :y :_ :_ :_ :_ :y :_]
   [:_ :y :_ :y :y :_ :y :_]
   [:_ :y :_ :_ :_ :_ :y :_]
   [:_ :_ :y :y :y :y :_ :_]
   [:_ :_ :y :_ :_ :y :_ :_]
   [:_ :_ :_ :_ :_ :_ :_ :_]])

(def all (vec (filter #(and (vector? %)
                            (= 8 (count %)))
                      (map var-get
                           (map #(ns-resolve *ns* %)
                                (keys (ns-publics *ns*)))))))
