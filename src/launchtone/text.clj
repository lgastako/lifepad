(ns launchtone.text
  (:require [launchtone.midi :as midi]
            ;; v-- REMOVE THIS --v
            [launchtone.core :refer [make-app]]
            ))

(defn scroll-text [sink text]
  (midi/send-midi-msg! sink 0xF0 0x00 0x20 0x29 0x7c))

(defn say-hello-to-my-little-friend []
  (println :say-hello :a :b :c)
  (let [app (make-app)
        receiver (:receiver @app)
        bytes [0xF0 0x00 0x20 0x29 0x09 0x7C 0x05
               0x48 0x65 0x6C 0x6C 0x6F 0x20 0x02
               0x77 0x6F 0x72 0x6C 0x64 0x21 0xF7]]
    (println :say-hello {:receiver receiver :bytes bytes})
    (midi/send-sysex-msg! receiver bytes)))

