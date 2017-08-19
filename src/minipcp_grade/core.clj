(ns minipcp-grade.core
  (:gen-class)
  (:require [liberator.core :refer [resource defresource]]
            [ring.middleware.params :refer [wrap-params]]
            [ring.adapter.jetty :as jetty]
            [ring.util.response :as response]
            [compojure.core :refer [defroutes context ANY]]
            [compojure.route :as route]
            [minipcp-grade.api.api-routes :refer [api-routes]]))


(defresource home 
  :available-media-types ["text/html"]
  :handle-ok "<html>MiniPCP Grades</html>")


(defroutes app
  (ANY "/" [] (response/resource-response "index.html" {:root "public"}))
  (context "/api/v1" []
    api-routes)
  (route/resources "/")
  (route/not-found "Page not found"))
  
  
(defn wrap-cors
  "Allow requests from all origins"
  [handler]
  (fn [request]
    (let [response (handler request)]
      (update-in response
                 [:headers "Access-Control-Allow-Origin"]
                 (fn [_] "*")))))


(def handler
  (-> app 
      wrap-params wrap-cors))


(defn -main
  "MiniPCP Grades"
  [& args]
  (jetty/run-jetty handler {:port 3000}))
