(ns mister.rogers)

;; set up globals that need to be accessed by other libs
(def neighborhood (ref '()))

(ns mister.rogers
  (:require [mister.rogers.arborist :as arborist]
            [mister.rogers.server :as server])
  (:gen-class))

(defn -main [& args]
  (let [config (load-file "config.clj")]
    (do
                                        ; (arborist/service-tree (config :data-file) 10)
      (arborist/update-tree (config :data-file))
      (println "Starting server...")
      (server/run))))
