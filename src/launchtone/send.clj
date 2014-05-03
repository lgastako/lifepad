(ns launchtone.send
  (:require [launchtone.colors :as colors]
            [launchtone.launchpad :as pad]
            [overtone.live :as otl]))

(defn send-redgreen!
  "Render the given red/green values to the row/col of receiver."
  [receiver row col red green]
  (let [note (pad/point->note col row)
        velocity (pad/colors->velocity red green)]
    (otl/midi-note-on receiver note velocity)))

(defn send-color!
  "Render the given color value to the row/col of receiver."
  [receiver row col color]
  (let [[red green] (colors/color-map color)]
    (send-redgreen! receiver row col red green)))
