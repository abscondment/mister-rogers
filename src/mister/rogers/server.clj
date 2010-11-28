(ns mister.rogers.server
  (:require [clojure.contrib.string :as str]
            [ring.middleware.params :as params]
            [kdtree :as kdtree]
            [org.danlarkin.json :as json])
  (:use [lamina core]
        [aleph http]
        [net.cgrand moustache]))

(defn- to-double [#^String string]
  (try
    (Double/parseDouble string)
    (catch Exception _)))

(defn- to-int [#^String string]
  (try
    (Integer/parseInt string)
    (catch Exception _)))

(defn- find-neighbors [channel request]
  (let [params (:params request)
        location  [(to-double (get params "lat"))
                   (to-double (get params "lon"))]
        n (or (to-int (get params "n")) 5)]
    (enqueue channel
             (if (not-any? nil? location)
               ;; Fetch response
               {:status 200
                :headers {"content-type" "text/json"}
                :body (json/encode
                       (map
                        #(merge % (meta (:point %)))
                        (kdtree/nearest-neighbor @mister.rogers/neighborhood
                                                 location
                                                 n)))}
               ;; Bad location
               {:status 400
                :headers {"content-type" "text/plain"}
                :body (str {:error "Bad location"})}
               ))))

(def handler
     (app ["stats"]
          {:get "STATS stub"}
          
          ["neighbors" "nearest"] {:get (params/wrap-params
                                         (wrap-aleph-handler find-neighbors))}))

(defn run [] (start-http-server (wrap-ring-handler handler) {:port 8080}))