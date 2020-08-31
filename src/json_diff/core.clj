(ns json-diff.core
  (:require [clojure.data.json :as json]
            [clojure.core.async :refer [>! <!! go chan]]
            [lambdaisland.deep-diff2 :as ddiff]
            [clojure.java.io :as io]
            [editscript.core :as edcore]
            [editscript.edit :as edit]
            [clojure.tools.cli :refer [parse-opts]])
  (:gen-class))

(def cli-options
  [["-m" "--mode MODE" "operation mode: 'visual' or 'patch'"
    :default :patch
    :parse-fn #(keyword %)
    :validate [#(% #{:visual :patch}) "possible modes are 'visual' or 'patch'"]]])

(defn parse-file
  "open JSON file and parses it"
  [fn]
  (with-open [f (io/reader fn)]
    (json/read f)))

(defn parse-files
  "parses multiple file asynchronously"
  [& files]
  ())

(defn patch
  "returns compact patch between objects"
  [json1 json2]
  )

(defn diff
  "returns visual diff between JSON files"
  [fn1 fn2]
  (let [c1 (chan)
        c2 (chan)]
    (go (>! c1 (parse-file fn1)))
    (go (>! c2 (parse-file fn2)))
    (ddiff/pretty-print
     (ddiff/diff (<!! c1) (<!! c2)))))

(defn -main
  "main function"
  [& args]
  (let [{files :arguments
         errors :errors
         {:keys [mode]} :options} (parse-opts args cli-options)]
    (cond
      errors (do (apply println errors)
                 (System/exit 1))
      (not= 2 (count files)) (do (println "you need to pass two JSON files!")
                                   (System/exit 1))
      (= :visual mode) (apply diff files)
      (= :patch mode) (apply patch files))))