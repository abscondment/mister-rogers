(ns fred.rogers)
(def *tree* (ref '()))

(ns fred.rogers
  (:require
   [fred.rogers.arborist :as arborist]
   [fred.rogers.server :as server]))

(defn -main [& args]
  (do
    (arborist/update-tree "seattle-sample.dat")
    (server/run)))
