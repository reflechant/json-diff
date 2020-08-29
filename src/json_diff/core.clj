(ns json-diff.core
  (:gen-class))

(defn diff
  "prints diff between JSON files"
  [file1 file2])

(defn -main
  "Main function"
  [& args]
  (if (> 2 (count args))
    (println "Not enough parameters!")
    (let [file1 (first args)
          file2 (second args)]
      (diff file1 file2))))