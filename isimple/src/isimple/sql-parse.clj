(ns isimple.core
    (:require [clojure.string :as str])
    (:require [instaparse.core :as insta])
    (:require [clojure.java.io :as io] ))

;; Test work:
(def single-a
  (insta/parser "https://gist.github.com/Engelberg/5283346/raw/77e0b1d0cd7388a7ddf43e307804861f49082eb6/SingleA"))
(single-a " a   ")
(insta/visualize (single-a "a") :output-file "images/single-a-1.png" :options {:dpi 63})

;; 2
(def sss
  "sentence = token (<whitespace> token)*
    <token> = keyword | identifier
    whitespace = #'\\s+'
    identifier = #'[a-zA-Z]+'
    keyword = 'cond' | 'defn'")
(println sss)
(def ambiguous-tokenizer
  (insta/parser
   sss))
(insta/parses ambiguous-tokenizer "defn my cond")
(insta/visualize
    (insta/parses ambiguous-tokenizer "defn my cond")
    :output-file "images/ambiguous-tokenizer-1.png"
    :options {:dpi 63})

 ;; SQL:
(def parse-sql
  (insta/parser (io/resource "rules/sql_text.bnf")
                :output-format :enlive))

(def test-query "select * from table")
(parse-sql test-query)
(insta/parses parse-sql test-query)
(insta/visualize
 (insta/parses parse-sql test-query)
 :output-file "images/parse-sql-1.png"
 :options {:dpi 63})

;; Каталог TEST
(io/resource "rules/sql.bnf")
(.getCanonicalPath (clojure.java.io/file "."))
(.getCanonicalPath (clojure.java.io/file "rules/sql.bnf"))
(System/getProperty "user.dir")
