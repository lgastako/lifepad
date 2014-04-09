(ns launchtone.colors)

(def brightness-off 0x00)
(def brightness-min 0x01)
(def brightness-mid 0x02)
(def brightness-max 0x03)

(def color-map {:e [brightness-off brightness-off]
                :r [brightness-max brightness-off]
                :g [brightness-off brightness-max]
                :y [brightness-max brightness-max]
                :r0g0 [brightness-off brightness-off]
                :r0g1 [brightness-off brightness-min]
                :r0g2 [brightness-off brightness-mid]
                :r0g3 [brightness-off brightness-max]
                :r1g0 [brightness-min brightness-off]
                :r1g1 [brightness-min brightness-min]
                :r1g2 [brightness-min brightness-mid]
                :r1g3 [brightness-min brightness-max]
                :r2g0 [brightness-mid brightness-off]
                :r2g1 [brightness-mid brightness-min]
                :r2g2 [brightness-mid brightness-mid]
                :r2g3 [brightness-mid brightness-max]
                :r3g0 [brightness-max brightness-off]
                :r3g1 [brightness-max brightness-min]
                :r3g2 [brightness-max brightness-mid]
                :r3g3 [brightness-max brightness-max]
                })

(defn board->colors [board]
  (into [] (map #(into [] (map color-map %)) board)))

