(ns launchtone.buffering
  (:require [its.log :as log]
            [launchtone.midi :as midi]))

(def buffer-none 0x30)
(def buffer-0 0x31)
(def buffer-1 0x34)

(defn send-buffer-msg!
  [sink data]
  (log/debug "Sending buffer msg!: " data)
  (midi/send-midi-msg! sink 0xB0 0x00 data))

(defn send-flip-buffer!
  [sink cur-buffer]
  (log/debug "Sending flip buffer!")
  (let [new-buffer (if (= buffer-0 cur-buffer) buffer-1 buffer-0)]
    (send-buffer-msg! sink new-buffer)))

(defn send-buffer-on-msg!
  [sink]
  (log/debug "Sending buffer-on msg!")
  (send-buffer-msg! sink buffer-0))

(defn send-switch-to-buffer-0-msg!
  [sink]
  (log/debug "Sending buffer-0 msg!")
  (send-buffer-msg! sink buffer-1))

(defn send-buffer-off-msg!
  [sink]
  (log/debug "Sending buffer-1 msg!")
  (send-buffer-msg! sink buffer-none))
