(ns launchtone.midi
  (:import (javax.sound.midi ShortMessage SysexMessage)))

(defn- send-msg! [sink msg]
  (println :send-msg! {:sink sink :msg msg})
  (.send (:receiver sink) msg -1))

(defn- make-short-msg
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
  (let [msg (apply make-short-msg args)]
    (send-msg! sink msg)))

;; F0h, 00h, 20h, 29h, 09h, colour, text ..., F7h
;; (240, 0, 32, 41, 9, colour, text ..., 247)

(defn- make-sysex-msg [bytes]
  (println :make-sysex-msg {:bytes bytes})
  (let [bytea (->ba bytes)
        ;; why does .length bytea not work??
        ;; halp how do I java
        size (count bytea)]
    (SysexMessage. bytea size)))

(defn ubyte [val]
  (if (>= val 128)
    (byte (- val 256))
    (byte val)))

(def ^:private ->ba
  (comp byte-array (partial map (comp ubyte int))))

(defn send-sysex-msg! [sink bytes]
  (println :send-sysex-msg! {:bytes bytes})
  (assert (> (count bytes) 0))
  (let [bytea (->ba bytes)
        _ (println :bytea bytea)
        msg (make-sysex-msg bytea)]
    (println :about-to-send {:msg msg})
    (send-msg! sink msg)))
