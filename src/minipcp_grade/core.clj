(ns minipcp-grade.core
  (:gen-class)
  (:require [liberator.core :refer [resource defresource]]
            [ring.middleware.params :refer [wrap-params]]
            [ring.adapter.jetty :as jetty]
            [compojure.core :refer [defroutes context ANY]]
            [minipcp-grade.api.api-routes :refer [api-routes]]))


(defresource home 
  :available-media-types ["text/html"]
  :handle-ok "<html>MiniPCP Grades</html>")


(defroutes app
  (ANY "/" [] home)
  (context "/api/v1" []
    api-routes))
  

(def handler 
  (-> app 
      wrap-params))


(defn -main
  "MiniPCP Grades"
  [& args]
  (jetty/run-jetty handler {:port 3000}))
