(ns clojure-questions.shortener
  (:require [clj-http.client :as http]
            [cheshire.core :as json]
            [environ.core :refer [env]])
  (import [java.net URLEncoder]))

(defn ^:private make-bitly-url [url]
  (format "https://api-ssl.bitly.com/v3/shorten?access_token=%s&longUrl=%s"
          (env :bitly-access-token)
          (URLEncoder/encode url "UTF-8")))

(defn shorten [url]
  (-> (http/get (make-bitly-url url))
      :body
      (json/decode true)
      :data
      :url))
