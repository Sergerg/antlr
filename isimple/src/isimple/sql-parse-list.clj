(ns isimple.core
    (:require [clojure.string :as str])
    (:require [instaparse.core :as insta])
    (:require [clojure.java.io :as io] ))

;; Загрузка правил.
(def parse-sql
  (insta/parser (io/resource "rules/sql_text.bnf")
                :output-format :enlive))

(def queries-list '("select * from table"
                    "select * from table t"
                    "select t.* from table t"))

