(ns clojure-questions.core
  (:require [twitter.oauth :as to]
            [twitter.api.restful :as tr]))

(def creds
  (let [config (read-string (slurp "creds.clj"))]
    (to/make-oauth-creds (:consumer-key config)
                         (:consumer-token config)
                         (:user-token config)
                         (:user-secret config))))

(defn tweet [msg]
  (tr/update-status :oauth-creds creds
                    :params {:status msg}))
