(defn operation [op] (fn [a b] (mapv op a b)))
(defn elementOperation [op] (fn [v el] (mapv (fn [component] (op component el)) v)))
(defn lineOperation [op] (fn [m el] (mapv (fn [line] (op line el)) m)))
(defn minor [a b i j] (- (* (nth a i) (nth b j)) (* (nth a j) (nth b i))))

; Vector
(def v+ (operation +))
(def v- (operation -))
(def v* (operation *))
(def v*s (elementOperation *))
(defn scalar [a b] (reduce + (v* a b)))
(defn vect [a b] [(minor a b 1 2) (minor a b 2 0) (minor a b 0 1)])

; Matrix
(def m+ (operation v+))
(def m- (operation v-))
(def m* (operation v*))
(def m*s (lineOperation v*s))
(def m*v (lineOperation scalar))
(defn transpose [m] (apply mapv vector m))
(defn m*m [a b] (mapv (partial m*v (transpose b)) a))

; Shapeless
(defn nestedOperation [op]
  (fn evaluate [a b] (if (number? a)
                       (op a b)
                       (mapv evaluate a b))))

(def s+ (nestedOperation +))
(def s- (nestedOperation -))
(def s* (nestedOperation *))