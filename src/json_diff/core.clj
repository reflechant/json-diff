(ns json-diff.core
  (:require [clojure.data.json :as json]
            [clojure.core.async :refer [>! <!! go chan]]
            [lambdaisland.deep-diff2 :as ddiff]
            [clojure.java.io :as io])
  (:gen-class))


(defn open-parse
  "open JSON file and parses it"
  [file-name]
  (with-open [f (io/reader file-name)]
    (json/read f)))

(defn diff
  "returns diff between JSON files"
  [file-name1 file-name2]
  (let [c1 (chan)
        c2 (chan)]
    (go (>! c1 (open-parse file-name1)))
    (go (>! c2 (open-parse file-name2)))
    (ddiff/diff (<!! c1) (<!! c2))))

(defn -main
  "Main function"
  [& args]
  (if (> 2 (count args))
    (println "Not enough parameters!")
    (ddiff/pretty-print
     (diff
      (first args)
      (second args)))))