(load-file "src/object.clj")
(load-file "src/combinators.clj")

(def *all-chars (mapv char (range 0 128)))
(def *space (+char (apply str (filter #(Character/isWhitespace (char %)) *all-chars))))
(def *letter (+char (apply str (filter #(Character/isLetter (char %)) *all-chars))))
(def *digit (+char (apply str (filter #(Character/isDigit (char %)) *all-chars))))
(def *ws (+ignore (+star *space)))

(def *constant (+map (comp Constant read-string)
                     (+str (+seq (+opt (+char "-+")) (+str (+plus *digit)) (+char ".") (+str (+plus *digit))
                                 (+opt (+seq (+char "e") (+opt (+char "-+")) (+str (+plus *digit))))))))

(def *operations (+char "+-*/"))
(def *identifier (+str (+plus (+or *letter *operations))))
(def *function-or-variable (+map (comp #(ops % (Variable (str %))) symbol) *identifier))

(declare *value)
(defn *seq [begin p end] (+seqn 1 (+char begin) (+plus (+seqn 0 *ws p)) *ws (+char end)))
(def *list (+map (fn [list] (apply (last list) (butlast list))) (*seq "(" (delay *value) ")")))
(def *value (+or *constant *function-or-variable *list))

(def parseObjectSuffix (+parser (+seqn 0 *ws *value *ws)))