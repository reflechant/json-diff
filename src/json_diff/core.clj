(ns json-diff.core
  (:require [clojure.data.json :as json]
            ;; [clojure.core.async :as async]
            [lambdaisland.deep-diff2 :as ddiff]
            [clojure.java.io :as io])
  (:gen-class))


(defn diff
  "returns diff between JSON files"
  [fn1 fn2]
  (with-open [file1 (io/reader fn1)
              file2 (io/reader fn2)]
    (let [json1 (json/read file1)
          json2 (json/read file2)]
      (ddiff/diff json1 json2))))

(defn -main
  "Main function"
  [& args]
  (if (> 2 (count args))
    (println "Not enough parameters!")
    (let [fn1 (first args)
          fn2 (second args)]
      (ddiff/pretty-print (diff fn1 fn2)))))