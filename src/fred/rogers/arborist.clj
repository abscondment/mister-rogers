(ns fred.rogers.arborist
  (:require [kdtree :as kdtree]
             [clojure.contrib.string :as str])
  (:use [clojure.java.io :only [reader]]))

(defn- reload-data [file]
  (let [lines (-> file reader line-seq)]
    (map #(with-meta
            (vec (map (fn [v] (Double/parseDouble v)) (next %)))
            {:id (Integer/parseInt (first %))})
         (map #(str/split #"[\s]+" %) lines))))

(defn update-tree [file]
  (let [new (kdtree/build-tree
             (reload-data file))]
    (dosync (ref-set fred.rogers/*tree* new))))

