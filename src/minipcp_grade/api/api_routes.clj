(ns minipcp-grade.api.api-routes
  (:require [compojure.core :refer [defroutes ANY GET POST]]
            [ring.middleware.json :refer [wrap-json-response]]
            [minipcp-grade.api.api-resources :refer 
              [produtos-lista grade-por-produto]]))


(defroutes api-routes
  (GET "/produtos" [] (wrap-json-response produtos-lista))
  (ANY "/grade/:tipo{[0-9]+}/:codigo" [tipo codigo] (wrap-json-response (grade-por-produto (Integer. tipo) codigo)))
  )
