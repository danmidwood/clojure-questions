(ns clojure-questions.core
  (:require [clojure-questions.shortener :refer [shorten]]
            [clojure.string :as string]
            [twitter.oauth :as to]
            [twitter.api.restful :as tr]
            [clj-http.client :as http]
            [http.async.client :as ac]
            [cheshire.core :as json]
            [environ.core :refer [env]]))

(def stack-api-url "https://api.stackexchange.com/2.1/questions")

(def creds (to/make-oauth-creds (env :clojureqs-app-consumer-key)
                                (env :clojureqs-app-consumer-secret)
                                (env :clojureqs-user-access-token)
                                (env :clojureqs-user-access-token-secret)))

(defn send-tweet [{:keys [title link]}]
  (let [msg (str title \space link)]
    (println "Tweeting: " msg)
    (tr/statuses-update :oauth-creds creds
                        :params {:status msg})))

(defn send-tweets [tweets] (map send-tweet tweets))

(defn trim [s length]
  (if (< (count s) length)
    s
    (str (string/join (take (- length 2) s)) \u2026)))

(defn make-tweet [{:keys [title link]}]
  (let [short-url (shorten link)
        safe-title (trim title (- 140 (count short-url) 1))] ; 1 for a space
    {:title safe-title
     :link short-url}))

(defn compose-tweets [questions]
  (map make-tweet questions))

(defn get-last-tweet-content []
  (->> (tr/statuses-user-timeline :oauth-creds creds
                                  :params {:count 1})
       :body
       first
       :text))

(defn questions []
  "Find all questions asked in the last one hour."
  (-> (http/get stack-api-url
                {:query-params {:site "stackoverflow"
                                :tagged "clojure"
                                :fromdate (quot (- (System/currentTimeMillis)
                                                   (* 1 60 60 1000)
                                                   (* 2 60 1000)) ; An extra 2 minutes for safety
                                                1000)}})
      :body
      (json/parse-string true)
      :items))

(defn filter-before [questions last-tweet-content]
  "Remove any question that have already been tweeted."
  (-> (split-with #(not (.startsWith last-tweet-content (:title %)))
                  (reverse questions))
      first
      reverse
      seq))

(def sort-by-creation (partial sort-by :creation_date))

(defn run []
  (let [last-tweet (get-last-tweet-content)]
    (-> (questions)
        sort-by-creation
        (filter-before last-tweet)
        compose-tweets
        send-tweets)))

(defn -main [& _]
  (do (println "Checking for new questions.")
      (doall (run))
      (.close (twitter.core/default-client))
      (println "Finished")))
