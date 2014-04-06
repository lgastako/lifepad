(ns lifepad.tools)

(defn next-board-by-xy [board f]
  (vec (for [x (range 0 8)]
         (vec (for [y (range 0 8)]
                (f board [x y]))))))

(defn fixed-point
  ([board]
     (fixed-point board identity))
  ([board f]
     (let [gen1 (next-board-by-xy board f)
           gen2 (next-board-by-xy gen1 f)]
       (if (= gen1 gen2)
         gen1
         (fixed-point gen2 f)))))

(defn *!*-infinite-iterate-*!*
  ([board]
     (*!*-infinite-iterate-*!* board identity))
  ([board f]
     (fixed-point board f)))
