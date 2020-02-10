#### Combinatoric parser on Clojure.

   combinators.clj - библиотека парсеров, которые будут скомбинированны в основной программе (parser.clj)

   object.clj - бибилиотека объектов (Poly operation, Binary operation, Unary operation, Variable, Constant)

   parser.clj - сам парсер, который разбирает выражения в постфиксной записи 

   Пример : expr = (2 (x (y 10 -) / ) * ) = 2 * (x / (y - 10)) 

   (evaluate expr {"x" 1 "y" 11}) = 2
