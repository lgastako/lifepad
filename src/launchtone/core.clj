(ns launchtone.core
  (:require [overtone.live :as otl]
            [overtone.at-at :as at-at]
            [launchtone.board :as board]
            [launchtone.buffering :as buf]
            [launchtone.colors :as colors]
            [launchtone.devices :as devices]
            [launchtone.launchpad :as pad]
            [launchtone.utils :refer [enumerate]]))

;; ;; To make this work with subwatch I would need to modify subwatch to also pass
;; ;; in the full old/new values instead of just the sub parts, right?
;; (defn- render-board
;;   [_k ref old-board new-board]
;;   ;; I'm not sure that it's a good idea to deref
;;   ;; new-app here, I should probably arrange for
;;   ;; sub-watches to receive the whole thing as an additional
;;   ;; parameter but for now...
;;   (let [app @ref]
;;     (let [new-receiver (app :receiver)
;;           cur-buffer (app :buffer)]
;;       (when (not= old-board new-board)
;;         (send-board! new-receiver new-board)
;;         (when (buffering-on? app)
;;           (buf/send-flip-buffer! new-receiver cur-buffer))))))

;; (defn- render-buffer
;;   [[old-app new-app]]
;;   (let [old-buffer (old-app :buffer)
;;         new-buffer (new-app :buffer)]
;;     (when (not= old-buffer new-buffer)
;;       (buf/send-buffer-msg! (new-app :receiver) new-buffer))))

;; (defn buffering-on! [app]
;;   (swap! app #(assoc % :buffer buf/buffer-0)))

;; (defn buffering-off! [app]
;;   (swap! app #(assoc % :buffer buf/buffer-none)))

