; Functions
(def div #(/ (double %1) %2))
(defn hyperbolic [op] #(div (op (Math/exp %) (Math/exp (- %))) 2))
(def sqrt #(Math/sqrt (Math/abs (double %))))
(def square #(* % %))
(def sinh (hyperbolic -))
(def cosh (hyperbolic +))

; Proto-methods
(defn proto-get [obj key]
  (cond
    (contains? obj key) (obj key)
    (contains? obj :prototype) (proto-get (obj :prototype) key)
    :else nil))

(defn proto-call [this key & args]
  (apply (proto-get this key) this args))

(defn field [key]
  (fn [this] (proto-get this key)))

; Достаем метод и применяем к объекту и аргументам
(defn method [key]
  (fn [this & args] (apply proto-call this key args)))

(def evaluate (method :evaluate))
(def toString (method :toString))
(def toStringSuffix (method :toStringSuffix))
(def diff (method :diff))

; Classes
(defn Constant [value]
  {
   :toString       (fn [_] (format "%.1f" value))
   :toStringSuffix (fn [_] (format "%.1f" value))
   :evaluate       (fn [_ _] value)
   :diff           (fn [_ _] (Constant 0))
   })

(def zero (Constant 0))
(def one (Constant 1))
(def two (Constant 2))

(def var-proto
  (let [name (field :name)]
    {
     :toString       (fn [this] (name this))
     :toStringSuffix (fn [this] (name this))
     :evaluate       (fn [this keymap] (keymap (clojure.string/lower-case (first (name this)))))
     :diff           (fn [this var] (if (= var (name this)) one zero))
     }))

(defn Variable [name]
  {
   :prototype var-proto
   :name      name
   })

(def operation-proto
  (let [diffFunc (field :diffFunc)
        name (field :name)
        op (field :op)
        operands (field :operands)]
    {
     :toString       (fn [this]
                       (str "(" (name this) " " (clojure.string/join " " (map toString (operands this))) ")"))

     :toStringSuffix (fn [this]
                       (str "(" (clojure.string/join " " (map toStringSuffix (operands this))) " " (name this) ")"))

     :evaluate       (fn [this values]
                       (apply (op this) (map #(evaluate % values) (operands this))))

     :diff           (fn [this var] (let [curArgs (operands this) a (first curArgs) b (last curArgs)]
                                      (if (= (count curArgs) 2)
                                        ((diffFunc this) a b (diff a var) (diff b var))
                                        ((diffFunc this) a (diff a var)))))
     }))

(defn operation-params [op name diffFunc]
  {
   :prototype operation-proto
   :op        op
   :name      name
   :diffFunc  diffFunc
   })

(defn operation [op name diffFunc]
  (let [params-proto (operation-params op name diffFunc)]
    (fn [& operands]
      {
       :prototype params-proto
       :operands  (vec operands)
       })))

(def Add (operation + "+" (fn [_ _ da db] (Add da db))))

(def Subtract (operation - "-" (fn [_ _ da db] (Subtract da db))))

(def Multiply (operation * "*" (fn [a b da db] (Add (Multiply da b) (Multiply db a)))))

(def Divide (operation
              div "/" (fn [a b da db] (Divide (Subtract (Multiply b da)
                                                        (Multiply a db))
                                              (Multiply b b)))))

(def Negate (operation - "negate" (fn [_ da] (Subtract da))))

(def Square (operation square "square" (fn [a da] (Multiply two (Multiply a da)))))

(def Sqrt (operation sqrt "sqrt" (fn [a da] (Divide (Multiply da a)
                                                    (Multiply
                                                      two (Sqrt (Multiply (Square a) a)))))))

(declare Cosh)

(def Sinh (operation sinh "sinh" (fn [a da] (Multiply da (Cosh a)))))

(def Cosh (operation cosh "cosh" (fn [a da] (Multiply da (Sinh a)))))

(def ops
  {'+      Add,
   '-      Subtract,
   '*      Multiply,
   '/      Divide,
   'negate Negate
   'sqrt   Sqrt
   'square Square
   'sinh   Sinh
   'cosh   Cosh
   })

