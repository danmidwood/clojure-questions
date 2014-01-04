(defproject clojure-questions "0.1.0"
  :description "A little Twitter bot that tweets Clojure stackoverflow questions."
  :dependencies [[org.clojure/clojure "1.5.0"]
                 [clj-http "0.7.8"]
                 [cheshire "5.3.0"]
                 [environ "0.4.0"]
                 [twitter-api "0.7.4"]]
  :plugins [[lein-environ "0.4.0"]]
  :main clojure-questions.core)
