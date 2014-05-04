(ns launchtone.buttons
  (:require [its.log :as log]
            [launchtone.launchpad :as pad]
            [overtone.live :as otl]))

(def controls {104 :up
               105 :down
               106 :left
               107 :right
               108 :session
               109 :user1
               110 :user2
               111 :mixer})

(defn- button-handler [f event-type key]
  (log/debug :make-handler {:event-type event-type})
  (fn [e]
    (let [note (:note e)
          velocity (:velocity e)
          [row col] (pad/note->point note)]
      (f {:spot [row col]
          :row row
          :col col
          :note note
          :velocity velocity
          :event-type event-type
          :key key}))))

(defn- control-handler [f event-type key]
  (log/debug :control-handler {:event-type event-type})
  (fn [e]
    (let [note (:note e)
          velocity (:velocity e)]
      (if (or (and (= event-type :down)
                   (= velocity 127))
              (and (= event-type :up)
                   (= velocity 0)))
        (f {:control (controls note :unknown)
            :note note
            :velocity velocity
            :event-type event-type
            :key key})))))

(defn on-button-down [key f]
  (log/debug :on-button-down {:key key})
  (otl/on-event [:midi :note-on]
                (button-handler f :down key)
                key))

(defn on-button-up [key f]
  (log/debug :on-button-up {:key key})
  (otl/on-event [:midi :note-off]
                (button-handler f :up key)
                key))

(def on-button on-button-up)

(defn cancel [key] (otl/remove-event-handler key))

(defn on-control-down [key f]
  (otl/on-event [:midi :control-change]
                (control-handler f :down key)
                key))

(defn on-control-up [key f]
  (otl/on-event [:midi :control-change]
                (control-handler f :up key)
                key))

(def on-control on-control-up)
