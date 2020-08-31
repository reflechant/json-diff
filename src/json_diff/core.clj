(ns json-diff.core
  (:require [clojure.data.json :as json]
            [clojure.core.async :refer [>! <!! go chan]]
            [lambdaisland.deep-diff2 :as ddiff]
            [clojure.java.io :as io]
            [editscript.core :as edcore]
            ;; [editscript.edit :as edit]
            [clojure.tools.cli :refer [parse-opts]])
  (:gen-class))

(def cli-options
  [["-m" "--mode MODE" "operation mode: 'visual' or 'patch'"
    :default :patch
    :parse-fn #(keyword %)
    :validate [#(% #{:visual :patch}) "possible modes are 'visual' or 'patch'"]]])

(defn parse
  "opens JSON file and parses it"
  [file-name]
  (with-open [f (io/reader file-name)]
    (json/read f)))

(defn parse-async
  "opens JSON file and parses it asynchronously, returns channel"
  [file-name]
  (go (parse file-name)))

(defn process
  [f & files]
  (->> files
       (map parse-async)
       (map #(<!! %))
       (map f)))

(defn patch
  "returns compact patch between two JSON objects"
  [left right]
  (println (edcore/diff left right {:algo :quick})))

(defn diff
  "returns visual diff between two JSON objects"
  [left right]
  (ddiff/pretty-print
   (ddiff/diff left right)))

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
      (= :visual mode) (apply process diff files)
      (= :patch mode) (apply process patch files))))