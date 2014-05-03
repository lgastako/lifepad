(ns launchtone.buttons
  (:require [its.log :as log]
            [launchtone.launchpad :as pad]
            [overtone.live :as otl]))

(defn- handler [f event-type key]
  (log/debug :make-handler {:event-type event-type})
  (fn [e]
    (let [note (:note e)
          velocity (:velocity e)
          [row col] (pad/note->point note)]
      (f {:spot [row col]
          :row row
          :col col
          :velocity velocity
          :event-type event-type
          :key key}))))

(defn on-button-down [key f]
  (log/debug :on-button-down {:key key})
  (otl/on-event [:midi :note-on]
                (handler f :down key)
                key))

(defn on-button-up [key f]
  (log/debug :on-button-up {:key key})
  (otl/on-event [:midi :note-off]
                (handler f :up key)
                key))

(def on-button on-button-up)

(defn cancel [key] (otl/remove-event-handler key))
