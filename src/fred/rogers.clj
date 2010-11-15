(ns fred.rogers)

;; set up globals that need to be accessed by other libs
(def *tree* (ref '()))

(ns fred.rogers
  (:require [fred.rogers.arborist :as arborist]
            [fred.rogers.server :as server])
  (:gen-class))

(defn -main [& args]
  (let [config (load-file "config.clj")]
    (do
                                        ; (arborist/service-tree (config :data-file) 10)
      (arborist/update-tree (config :data-file))
      (println "Starting server...")
      (server/run))))
