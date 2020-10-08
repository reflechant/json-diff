(ns json-diff.core
  (:require [clojure.string :as str]
            [clojure.java.io :as io]
            [clojure.data.json :as json]
            [clojure.core.async :refer [<!! go]]
            [clojure.tools.cli :refer [parse-opts]]
            [lambdaisland.deep-diff2 :as ddiff]
            [editscript.core :refer [diff] :rename {diff compact-diff}])

  (:gen-class))

(def cli-options
  [["-m" "--mode MODE" "operation mode: 'visual' or 'patch'"
    :default :patch
    :parse-fn #(keyword %)
    :validate [#(% #{:visual :patch}) "possible modes are 'visual' or 'patch'"]]])

(defn parse-async
  "opens JSON file and parses it asynchronously, returns channel"
  [file-name]
  (go (with-open [f (io/reader file-name)]
        (json/read f))))

(defn process
  [f & files]
  (->> files
       (map parse-async)
       (map <!!)
       (apply f)))

(defn patch
  "prints compact patch between two JSON objects"
  [left right]
  (println (compact-diff left right {:algo :quick})))

(defn diff
  "prints visual diff between two JSON objects"
  [left right]
  (ddiff/pretty-print
   (ddiff/diff left right)))

(defn print-error-exit
  "prints error message and exits with error code"
  [msg code]
  (println msg)
  (System/exit code))

(defn -main
  "main function"
  [& args]
  (let [{files :arguments
         errors :errors
         {:keys [mode]} :options} (parse-opts args cli-options)]
    (cond
      errors (print-error-exit (str/join "\n" errors) 1)
      (not= 2 (count files)) (print-error-exit "you need to pass two JSON files!" 1)
      (= :visual mode) (apply process diff files)
      (= :patch mode) (apply process patch files))))