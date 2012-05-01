(ns clojure-questions.core
  (:require [twitter.oauth :as to]
            [twitter.api.restful :as tr]
            [clj-http.client :as http]
            [cheshire.core :as json]
            [clojure.string :as string])
  (:gen-class))

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

(defn now [] (quot (System/currentTimeMillis) 1000))

(defn compose-tweets [questions]
  (for [{:strs [title question_id]} questions
        :let [link (str "http://stackoverflow.com/questions/" question_id)
              title-count (count title)]]
    (if (>= 140 (+ title-count 21))
      (str title \space link)
      (str (string/join (take 115 title)) \u2026 \space link))))

(defn questions [min-date]
  (compose-tweets
    ((json/parse-string
       (:body
         (http/get (str so "questions")
                   {:query-params {:tagged "clojure"
                                   :fromdate min-date}})))
       "questions")))

(defn -main [& _]
  (loop [time (now)]
    (Thread/sleep 300000)
    (println "Checking for new questions.")
    (doseq [message (questions time)]
      (println "Tweeting:" message)
      (tweet message))
    (recur (now))))
