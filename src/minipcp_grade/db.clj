(ns minipcp-grade.db
  (:require [clojure.java.jdbc :as sql]
            [clojure.string :as str]))


(def conn-str "postgresql://postgres@localhost:5432/minipcp")
(def query (partial sql/query conn-str))


(defn get-static-data 
  []
  {:tipos (query "select * from prd_tipos")
   :produtos (query "select * from produtos")
   :grades (query "select id, descricao from grades order by id")
   :grades-itens (query "select id, grade, descricao from grades_itens order by id")
   :grades-por-produto (query "select id, tipo, codigo, grade from prd_grades order by tipo, codigo, grade")
   :transposicao (query "select grade_horizontal as horizontal, grade_vertical as vertical from grades_transposicao order by id desc limit 1")})


(def database (atom {:itens [] :static-data (get-static-data)}))


(defn set-data 
  [table data]
  (swap! database 
         (fn [current-db] 
           (assoc current-db table data))))


(defn get-data [table] (table @database))


(defn reload-static-data [] (set-data :static-data get-static-data))
