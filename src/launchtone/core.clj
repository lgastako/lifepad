(ns launchtone.core
  (:require [its.log :as log]
            [overtone.live :as otl]
            [overtone.at-at :as at-at]
            [subwatch.core :as sw]
            [launchtone.board :as board]
            [launchtone.buffering :as buf]
            [launchtone.colors :as colors]
            [launchtone.devices :as devices]
            [launchtone.launchpad :as pad]
            [launchtone.utils :refer [enumerate]]))

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

(defn send-colors!
  [receiver colors]
  (doseq [[r row-colors] (enumerate colors)
          [c [red green]] (enumerate row-colors)]
    (send-redgreen! receiver r c red green)))

(defn send-board!
  "Render the given board to the given reciever."
  [receiver board]
  (send-colors! receiver (colors/board->colors board)))

(defn buffering-on?
  [app]
  (not= (app :buffer) buf/buffer-none))

;; To make this work with subwatch I would need to modify subwatch to also pass
;; in the full old/new values instead of just the sub parts, right?
(defn- render-board
  [_k ref old-board new-board]
  ;; I'm not sure that it's a good idea to deref
  ;; new-app here, I should probably arrange for
  ;; sub-watches to receive the whole thing as an additional
  ;; parameter but for now...
  (let [app @ref]
    (let [new-receiver (app :receiver)
          cur-buffer (app :buffer)]
      (when (not= old-board new-board)
        (send-board! new-receiver new-board)
        (when (buffering-on? app)
          (buf/send-flip-buffer! new-receiver cur-buffer))))))

(defn- render-buffer
  [[old-app new-app]]
  (let [old-buffer (old-app :buffer)
        new-buffer (new-app :buffer)]
    (when (not= old-buffer new-buffer)
      (buf/send-buffer-msg! (new-app :receiver) new-buffer))))

(defn buffering-on! [app]
  (swap! app #(assoc % :buffer buf/buffer-0)))

(defn buffering-off! [app]
  (swap! app #(assoc % :buffer buf/buffer-none)))

(defn- extend-key [key extra]
  (let [key (name key)
        extra (name extra)]
    (keyword (str key "|" extra))))

(defn make-app
  ([]
     (make-app :app))
  ([key]
     (let [[transmitter receiver] (devices/select-transmitter-and-receiver)]
       (make-app key transmitter receiver)))
  ([key transmitter receiver]
     (when (nil? transmitter)
       (throw (Exception. (str "invalid transmitter " transmitter))))
     (when (nil? receiver)
       (throw (Exception. (str "invalid receiver " receiver))))
     (let [app (atom {:transmitter transmitter
                      :receiver receiver
                      :line-in (otl/midi-mk-full-device-key transmitter)
                      :line-out (otl/midi-mk-full-device-key receiver)
                      :worker-pool (at-at/mk-pool)
                      :buffer buf/buffer-none
                      :board board/empty-board})
           buffer-key (extend-key key :buffer)
           board-key (extend-key key :board)]
       (sw/add-sub-watch app buffer-key [:buffer] render-buffer)
       (sw/add-sub-watch app board-key [:board] render-board)
       app)))

(def ^:private primary-app (atom nil))

(defn get-app
  []
  (if @primary-app
    @primary-app
    (let [new-app (make-app)]
      (reset! primary-app new-app)
      new-app)))

(defn set-board! [app board]
  (swap! app assoc :board board)
  board)

(defn swap-board! [app f]
  (dosync
   (let [old-board (@app :board)
         new-board (f old-board)]
     (set-board! app new-board))))

(defn set-spot-color! [app r c color]
  (let [board (@app :board)
        row (board r)
        new-row (assoc row c color)
        new-board (assoc board r new-row)]
    (set-board! app new-board)))

(defn handle-button-event [app f event-type]
  (log/debug "installing handle-button-event")
  (fn [m]
    (log/debug "firing handle-button-event")
    (let [[row col] (pad/note->point (m :note))]
      (f row col event-type m))))

(defn on-button-down [app f key]
  (log/debug "attached on-button-down for key " key)
  (otl/on-event [:midi :note-on]
                (handle-button-event app f :down)
                key))

(defn on-button-up [app f key]
  (log/debug "attached on-button-up")
  (otl/on-event [:midi :note-off]
                (handle-button-event app f :up)
                key))

(def on-button on-button-up)

(defn get-color [app r c]
  (let [board (@app :board)
        row (board r)]
    (row c)))

(defn get-redgreen [app r c]
  (colors/color-map (get-color app r c)))

(defn off!
  ([app]
     (set-board! app board/empty-board)))
