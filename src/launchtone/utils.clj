(ns launchtone.utils)

(defn enumerate
  "Emulates python's enumerate."
  [xs]
  (map-indexed vector xs))
