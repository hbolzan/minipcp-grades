(ns minipcp-grade.api.api-resources
  (:require [clojure.string :as str]
            [liberator.core :refer [resource defresource]]
            [liberator.representation :refer [ring-response]]
            [clojure.data.json :as json]
            [minipcp-grade.api.api-core :refer [map-to-kwmap api-get-body api-wrap-rest]]
            [minipcp-grade.core-bl :refer [produtos-com-duas-grades items-to-grid grid-to-items find-grade]]
            [minipcp-grade.db :refer [get-data set-data]]))


(defresource produtos-lista
  :available-media-types ["application/json"]
  :handle-ok 
    (fn 
      [_] 
      (let [dados (get-data :static-data)]
        (api-wrap-rest (into [] (produtos-com-duas-grades (:grades dados) (:grades-por-produto dados) (:produtos dados)))))))


(defn tratar-grade [grade]
  (cons
    (first grade)
    (map 
      (fn [linha] (cons (first linha) (map map-to-kwmap (rest linha)))) 
      (rest grade))))


(defn tratar-grid [grid]
  (let [grade (:grade grid)] 
    (assoc grid :grade (tratar-grade grade))))
    
    
(defn itens-de-outros-produtos [tipo codigo]
  (filter 
    (fn [item] (or (not= (:tipo item) tipo) (not= (first (str/split (:codigo item) #":")) codigo)))
    (get-data :itens)))
  
    

(defresource grade-por-produto [tipo codigo]
  :allowed-methods [:get :post]
  :available-media-types ["application/json"]
  :handle-ok 
    (fn [_]
      (let [dados (get-data :static-data) itens (conj (get-data :itens) {:tipo tipo :codigo codigo :quantidade 0})]
        (api-wrap-rest (find-grade tipo codigo (items-to-grid dados itens)))))
  
  :post! 
    (fn 
      [ctx]
      (let [grid (assoc (tratar-grid (map-to-kwmap (api-get-body ctx))) :produto {:tipo tipo :codigo codigo})]
        {:message (api-wrap-rest (:itens (set-data :itens (concat (itens-de-outros-produtos tipo codigo) (grid-to-items grid)))))}))
  :handle-created (fn [ctx] json/write-str (:message ctx)))
