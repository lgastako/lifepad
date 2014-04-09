(ns launchtone.midi
  (:import (javax.sound.midi ShortMessage)))

(defn- send-short-msg! [sink msg]
  (.send (:receiver sink) msg -1))

(defn- make-msg
  ([status]
     (let [msg (ShortMessage.)]
       (.setMessage msg status)
       msg))
  ([status data1 data2]
     (let [msg (ShortMessage.)]
       (.setMessage msg status data1 data2)
       msg))
  ([status channel data1 data2]
     (let [msg (ShortMessage.)]
       (.setMessage msg channel data1 data2)
       msg)))

(defn send-midi-msg! [sink & args]
  (let [msg (apply make-msg args)]
    (send-short-msg! sink msg)))

