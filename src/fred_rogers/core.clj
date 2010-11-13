(ns fred-rogers.core
  (:require
   [fred-rogers.arborist :as arborist]
   [fred-rogers.server :as server]))

(def *tree* (ref '()))

(defn -main [& args]
  (do
    (arborist/update-tree)
    (server/run)))
