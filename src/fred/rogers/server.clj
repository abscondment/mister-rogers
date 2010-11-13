(ns fred-rogers.server
  (:require [clojure.contrib.string :as str]
            [kdtree :as kdtree])
  (:use [lamina core]
        [aleph http]))

(defn- query-map [request]
  (reduce
   (fn [result [key val]]
     (assoc result key val))
   {}
   (map #(str/split #"=" %)
        (str/split #"&" (request :query-string)))))

(defn- hello-world [channel request]
  (enqueue channel
    {:status 200
     :headers {"content-type" "text/html"}
     :body (str

            )
     }))

(defn run [] (start-http-server hello-world {:port 8080}))