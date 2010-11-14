(ns fred.rogers.server
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
        (str/split #"&" (or (request :query-string) "")))))

(defn- find-neighbors [channel request]
  (let [params (query-map request)
        point  [(Double/parseDouble (get params "lat"))
                (Double/parseDouble (get params "lon"))]
        n (Integer/parseInt (or (get params "n") "5"))]
   (enqueue channel
            {:status 200
             :headers {"content-type" "text/html"}
             :body (str
                    (seq (kdtree/nearest-neighbor
                          @fred.rogers/*tree*
                          point
                          n)))})))

(defn run [] (start-http-server find-neighbors {:port 8080}))