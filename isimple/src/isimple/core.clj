(ns isimple.core
  (:require [clojure.string :as str])
  (:require [instaparse.core :as insta]))

;(use 'rhizome.viz)

(defn foo!
  "I don't do a whole lot."
  [x]
  (println x "Hello, World! 2"))

(foo! "aaa")

;; (insta/parser (clojure.java.io/resource "myparser.bnf"))

;; Из инета!
(def single-a
  (insta/parser "https://gist.github.com/Engelberg/5283346/raw/77e0b1d0cd7388a7ddf43e307804861f49082eb6/SingleA"))
(single-a " a   ")
(insta/visualize (single-a "a") :output-file "images/single-a-1.png" :options {:dpi 63})

(def as-and-bs
  (insta/parser
   "S = AB*
 AB = A B
 A = 'a'+
 B = 'b'+"))

(as-and-bs "aaaaabbbaaaabb")

(insta/visualize (as-and-bs "aaaaabbbaaaabb") :output-file "images/as-and-bs-1.png" :options {:dpi 63})



(def parser_num
  (insta/parser
   "number = #'[0-9]+'"))

(parser_num "1")
(parser_num "123")


(def parser_vec
  (insta/parser
   "expr = number | vector
    vector = snumber+ number
    <snumber> = (number space)*
    <space> = <#'[ ]+'>
    number = #'[0-9]+'"))

(def transform-options
  {:number read-string
   :vector (comp vec list)
   :expr identity})

(defn parse_vec [input]
  (->> (parser_vec input) (insta/transform transform-options)))

(parser_vec "1 2 3 4 5")

(parse "1 2 3 4 5")

(def parser
  (insta/parser
   "expr = number | vector | operation
    operation = operator space+ vector
    operator = '+' | '-' | '*' | '/'
    vector = snumber+ number
    <snumber> = (number space)*
    <space> = <#'[ ]+'>
    number = #'[0-9]+'"
   :output-format :enlive))

(parser "+ 1 2 3 4")

(defn choose-operator [op]
  (case op
    "+" +
    "-" -
    "*" *
    "/" /))

(def transform-options
  {:number read-string
   :vector (comp vec list)
   :operator choose-operator
   :operation apply
   :expr identity})

(defn parse [input]
  (->> (parser input) (insta/transform transform-options)))

(parse "+ 1 2 3 4")
(parse "- 4 2 + 3")
(parse "- 4 2")
; (parse "/ 4 0")
(parse "* 4 2 4 2")
(+ 5/3 1/3)
(+ 5/3 2/3)
(* 5/3 2/3)

(insta/visualize (parser "+ 1 2 3 4") :output-file "images/parser-1.png" :options {:dpi 63})
