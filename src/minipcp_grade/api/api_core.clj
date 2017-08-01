(ns minipcp-grade.api.api-core
  (:require [clojure.data.json :as json]))


(defn api-wrap-rest [data]
  ((fn [coll] {:status "OK" :count (count coll) :data coll}) (#(if (vector? %) % [%]) data)))


(defn api-get-body [ctx]
  (json/read-str (slurp (get-in ctx [:request :body]))))


(defn map-to-kwmap [m]
  (into {} (map #(vector (keyword (first %)) (last %))) m))
