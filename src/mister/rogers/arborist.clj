(ns mister.rogers.arborist
  (:require [kdtree :as kdtree]
             [clojure.contrib.string :as str])
  (:use [clojure.java.io :only [reader]])
  (:import (java.util.concurrent Executors ExecutorService TimeUnit)))

(defn- reload-data [file]
  (let [lines (-> file reader line-seq)]
    (pmap #(with-meta
             (vec (map (fn [v] (Double/parseDouble v)) (next %)))
             {:id (Integer/parseInt (first %))})
          (pmap #(str/split #"[\s]+" %) lines))))

(defn update-tree [file]
  (let [data (doall (reload-data file))
        new (kdtree/build-tree data)]
    (dosync (ref-set mister.rogers/neighborhood new))))

(defn service-tree [file rate]
  (let [pool (Executors/newSingleThreadScheduledExecutor)
        update #(try
                  ;; TOOD: log that update is occurring
                  (update-tree file)
                  (catch Exception e
                    (.printStackTrace e)))]
    (.scheduleAtFixedRate pool update 0 rate TimeUnit/SECONDS)))

