(ns minipcp-grade.core-bl
  (:require [clojure.string :as str]))
  

(defn transpose [m]
  (apply mapv vector m))


(defn find-first
  [f coll]
  (first (filter f coll)))


(defn find-by-key
  [key value coll]
  (find-first #(= value (key %)) coll))


(def find-id (partial find-by-key :id))


(defn find-grade [tipo codigo grades]
  (find-first 
    (fn [grade] (let [produto (:produto grade)] (and (= tipo (:tipo produto)) (= codigo (:codigo produto)))))
    grades))


(defn get-produto 
  [produtos tipo codigo & quantidade] 
  (assoc (find-first #(and (= (:tipo %) tipo) (= (:codigo %) codigo)) produtos) :quantidade (first quantidade)))


(defn grades-do-produto 
  [grades grades-por-produto produto]
  (map 
    #(find-id (:grade %) grades)
  	(filter #(and (= (:tipo %) (:tipo produto)) (= (:codigo %) (:codigo produto))) grades-por-produto)))


(defn grades-count 
  [grades grades-por-produto produto]
  (count (grades-do-produto grades grades-por-produto produto)))


(defn produtos-com-duas-grades
  [grades grades-por-produto produtos]
  (reduce #(if (= (grades-count grades grades-por-produto %2) 2) (conj %1 %2) %1) [] produtos))


; gerar grade bi dimensional

(defn itens-da-grade 
  [grades-itens grade]
  (filter #(= (:grade %) (:id grade)) grades-itens))


(def pad-0000 (partial format "%04d"))


(defn format-grade-id 
  [coll]
  (assoc coll :id (pad-0000 (:id coll))))


(defn grade-bi 
  [grades grades-itens] 
  (let [horizontal (map format-grade-id (itens-da-grade grades-itens (second grades)))
        primeira-linha (cons "" (map #(:descricao %) horizontal))]
    (cons
      primeira-linha
      (map
        (fn 
          [item-vertical] 
          (cons (:descricao item-vertical) (map #(hash-map :desdobramento (str (:id (format-grade-id item-vertical)) "." (:id %)) :quantidade 0) horizontal)))
        (itens-da-grade grades-itens (first grades))))))



; transformar lista de itens em grade
(defn get-prefixo
  [codigo]
  (first (str/split codigo #":")))


(defn produto-key [item]
  (hash-map :tipo (:tipo item) :codigo (get-prefixo (:codigo item))))


(defn encaixar-item-na-grade 
  [grade item]
  (let 
    [desdobramento (last (str/split (:codigo item) #":"))]
    (map 
      (fn
        [linha]
        (map
          #(if 
             (= (:desdobramento %) desdobramento) 
             (assoc % :quantidade (+ (:quantidade %) (:quantidade item)))
             %) 
          linha)) 
      grade)))


(defn grouped-item-to-grid 
  [dados grouped-item]
  (let [grupo (first grouped-item)
        items (second grouped-item)
        produto (get-produto (:produtos dados) (:tipo grupo) (:codigo grupo))
        grade-base (grade-bi (grades-do-produto (:grades dados) (:grades-por-produto dados) produto) (:grades-itens dados))]
    (hash-map :produto produto :grade (reduce encaixar-item-na-grade grade-base items))))


(defn items-to-grid [dados items]
  (let [grouped-items (group-by produto-key (sort-by :codigo items))]
    (map #(grouped-item-to-grid dados %) grouped-items)
    ))



; transformar grade em itens

(defn cell-to-item 
  [items cell]
  	(let [desdobramento (:desdobramento cell)
          quantidade-anterior (or (get items desdobramento) 0)
          quantidade (+ quantidade-anterior (:quantidade cell))]
      (if (> quantidade 0) (assoc items desdobramento quantidade) items)))


(defn grid-to-items
  [grid]
  (let 
    [produto (:produto grid)
     tipo (:tipo produto)
     prefixo (:codigo produto)
     linhas (rest (:grade grid))]
    (map
      #(hash-map :tipo tipo :codigo (str prefixo ":" (first %)) :quantidade (last %))
      (reduce
        (fn
          [items linha]
          (merge items (reduce cell-to-item {} (rest linha))))
        {}
        linhas))))


(defn grids-to-items
  [grids]
  (map grid-to-items grids))

