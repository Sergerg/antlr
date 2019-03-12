(ns isimple.core
    (:require [clojure.string :as str])
    (:require [instaparse.core :as insta])
    (:require [clojure.java.io :as io] ))

;; Загрузка правил.
(def parse-sql
  (insta/parser (io/resource "rules/sql_text.bnf")
                :output-format :enlive))

(def queries-list ["select * from table"
                    "select * from table t"
                    "select t.* from table t"])
queries-list
(get queries-list 0)
(parse-sql (get queries-list 0))
(def res (map parse-sql queries-list))
res
(insta/visualize
 res
 :output-file "images/parse-sql.png"
 :options {:dpi 63})

;; TODO: Цикл с созданием диаграм по каждому запросу!
