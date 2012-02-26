(defproject clojure-questions "0.1.0"
  :description "A little Twitter bot that tweets Clojure stackoverflow questions."
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [clj-http "0.3.2"]
                 [cheshire "2.2.0"]
                 [twitter-api "0.6.4"]
                 [hobbit "0.1.0-alpha1"]]
  :main clojure-questions.core)
