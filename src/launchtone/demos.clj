(ns launchtone.demos
  (:require [its.log :as log]
            [launchtone.core :as lp]
            [launchtone.board :as board]
            [launchtone.cron :as cron]))

(def xmas-tree-board
  [[:r0g0 :r0g0 :r0g0 :r0g3 :r0g3 :r0g0 :r0g0 :r0g0]
   [:r0g0 :r0g0 :r0g2 :r0g3 :r0g3 :r0g2 :r0g0 :r0g0]
   [:r0g0 :r0g0 :r0g3 :r0g3 :r0g3 :r0g3 :r0g0 :r0g0]
   [:r0g0 :r0g2 :r0g3 :r0g3 :r0g3 :r0g3 :r0g2 :r0g0]
   [:r0g0 :r0g3 :r0g3 :r0g3 :r0g3 :r0g3 :r0g3 :r0g0]
   [:r0g2 :r0g3 :r0g3 :r0g3 :r0g3 :r0g3 :r0g3 :r0g2]
   [:r0g3 :r0g3 :r0g3 :r0g3 :r0g3 :r0g3 :r0g3 :r0g3]
   [:r0g0 :r0g0 :r0g0 :r3g3 :r3g3 :r0g0 :r0g0 :r0g0]])

(def smiley-board
  [[:e :e :y :y :y :y :e :e]
   [:e :y :y :y :y :y :y :e]
   [:y :y :e :y :y :e :y :y]
   [:y :y :y :y :y :y :y :y]
   [:y :e :y :y :y :y :e :y]
   [:y :y :e :y :y :e :y :y]
   [:e :y :y :e :e :y :y :e]
   [:e :e :y :y :y :y :e :e]])

(defn xmas-tree!
  ([]
     (xmas-tree! (lp/make-app)))
  ([app]
     (lp/set-board! app xmas-tree-board)))

(defn start-twinkling! [app [r c]]
  ;; A twinkle is picking red or yellow and then cycling from
  ;; the lowest intensity version of the color to the highest
  ;; then back to the lowest and then to green.  Will have to
  ;; figure out how to do the green part -- should we fade to
  ;; it or must pop it back or what?
  (log/debug "start-twinkling!")
  (let [cycle (if (> (rand) 0.5)
                [:r1g3 :r2g3 :r3g3 :r2g3 :r1g3 :r0g3]
                [:r1g2 :r2g1 :r3g0 :r2g1 :r1g2 :r0g3])]
    (letfn [(finish-cycle! [cycle]
              (log/debug "finishing cycle: " cycle)
              (if (pos? (count cycle))
                (let [color (first cycle)
                      remaining (rest cycle)]
                  (log/debug "work to do, color=" color)
                  (lp/set-spot-color! app r c color)
                  (log/debug "remaining " remaining)
                  (finish-cycle! remaining)))
              (log/debug "no more vals in cycle"))]
      (log/debug "starting twinkle on cycle: " cycle)
      (finish-cycle! cycle))))

(defn potential-lights
  [app]
  (let [full-green [0 3]]
    (letfn [(is-green-spot [[row col]]
              (= full-green (lp/get-redgreen app row col)))]
      (filter is-green-spot board/all-spots))))

(defn random-potential-light
  [app]
  (rand-nth (potential-lights app)))

(defn animate-tree!
  ([]
     (animate-tree! (lp/make-app)))
  ([app]
     (let [min-ms 300
           max-ms 3000
           random-interval (+ min-ms (rand-int (- max-ms min-ms)))]
       (cron/after random-interval (start-twinkling! app (random-potential-light app))))))

(defn animated-xmas-tree!
  ([app]
     (xmas-tree! app)
     (animate-tree! app)))

(defn smiley!
  ([]
     (smiley! (lp/make-app)))
  ([app]
     (lp/set-board! app smiley-board)))

(defn scrolled-row
  [row]
  (concat (vec (rest row)) [(first row)]))

(assert (= [:b :c :a] (scrolled-row [:a :b :c])))
(scrolled-row [:a :b :c])

(defn scrolled-board
  [board]
  (map scrolled-row board))

(defn scroll!
  [app]
  (letfn [(updater [old-app]
            (update-in old-app [:board] scrolled-board))]
    (swap! app updater)))

(defn scroll-smiley!
  ([]
     (scroll-smiley! (lp/make-app)))
  ([app]
     (smiley! app)
     (cron/every app 100 #(scroll! app))))

(defn scroll-xmas-tree!
  ([]
     (scroll-xmas-tree! (lp/make-app)))
  ([app]
     (xmas-tree! app)
     (cron/every app 100 #(scroll! app))))

(defn -main []
  (xmas-tree!))
