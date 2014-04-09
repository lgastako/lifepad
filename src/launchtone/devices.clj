(ns launchtone.devices
  (:import (javax.sound.midi ShortMessage))
  (:require [overtone.live :as otl]
            [overtone.midi :as otm]
            [overtone.config.log :as log]))

(def ^:private lp-regex "Launchpad")

;; (defn no-transmitter-and-receiver []
;;   (throw (Exception. "No Launchpads found.")))

(defn only-transmitter-and-receiver []
  ((juxt otl/midi-find-connected-device
         otl/midi-find-connected-receiver) lp-regex))

;; (defn choose-transmitter-and-receiver []
;;   ((juxt otm/midi-in otm/midi-out)))

;; (defn select-transmitter-and-receiver
;;   []
;;   (log/debug "select-transmitter-and-receiver")
;;   (let [num-launchpads (otl/midi-find-connected-devices lp-regex)]
;;     (log/debug "Found " num-launchpads " Launchpad devices.")
;;     (cond (= 0 num-launchpads) (no-transmitter-and-receiver)
;;           (= 1 num-launchpads) (only-transmitter-and-receiver)
;;           :else (choose-transmitter-and-receiver))))

(def select-transmitter-and-receiver only-transmitter-and-receiver)
