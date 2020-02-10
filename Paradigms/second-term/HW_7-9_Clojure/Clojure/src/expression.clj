;Functional expression
(defn operation [evaluate]
  (fn [op]
    (fn [& operands]
      (fn [values] (evaluate op (map #(% values) operands))))))

(def apply-op (operation apply))
(def reduce-op (operation reduce))

(def add (apply-op +))
(def subtract (apply-op -))
(def multiply (apply-op *))
(def divide (apply-op #(/ (double %1) %2)))
(def negate (apply-op -))
(def square (apply-op #(* % %)))
(def sqrt (apply-op #(Math/sqrt (Math/abs (double %)))))
(def min (reduce-op clojure.core/min))
(def max (reduce-op clojure.core/max))

(defn constant [const] (constantly const))
(defn variable [var] (fn [variables] (variables var)))

(def ops
  {'+      add
   '-      subtract
   '*      multiply
   '/      divide
   'min    min
   'max    max
   'sqrt   sqrt
   'square square
   'negate negate
   })

(defn parse [expr]
  (cond
    (number? expr) (constant expr)
    (symbol? expr) (variable (str expr))
    (list? expr) (apply (ops (first expr)) (map parse (rest expr)))))

(def parseFunction (comp parse read-string))