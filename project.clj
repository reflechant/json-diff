(defproject json-diff "0.2.0"
  :description "A console program to produce colorized structural diff of two JSON files"
  :url "https://github.com/reflechant/json-diff"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [lambdaisland/deep-diff2 "2.0.108"]
                 [org.clojure/data.json "1.0.0"]
                 [org.clojure/core.async "1.3.610"]]
  :main ^:skip-aot json-diff.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
