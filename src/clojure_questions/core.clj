(ns clojure-questions.core
  (:require [twitter.oauth :as to]
            [twitter.api.restful :as tr]
            [clj-http.client :as http]
            [cheshire.core :as json]))

(def creds
  (let [config (read-string (slurp "creds.clj"))]
    (to/make-oauth-creds (:consumer-key config)
                         (:consumer-token config)
                         (:user-token config)
                         (:user-secret config))))

(defn tweet [msg]
  (tr/update-status :oauth-creds creds
                    :params {:status msg}))

(def so "http://api.stackoverflow.com/1.1/")

(defn questions [min-date]
  (json/parse-string
    (:body
      (http/get (str so "questions")
                {:query-params {:tagged "clojure"
                                :fromdate min-date
                                :pagesize 3}}))))
