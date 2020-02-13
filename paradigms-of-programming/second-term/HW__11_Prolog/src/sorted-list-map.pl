map_get([(Key, Value) | _], Key, Value).
map_get([(K, V) | T], Key, Value) :- Key > K, map_get(T, Key, Value).


map_put([], Key, Value, [(Key, Value)]) :- !.
map_put([(Key, _) | T], Key, Value, [(Key, Value) | T]) :- !.
map_put([(K, V) | T], Key, Value, [(K, V) | NLIST]) :-
         K < Key, map_put(T, Key, Value, NLIST), !.
map_put(MapList, Key, Value, [(Key, Value) | MapList]).


map_remove([(Key, _) | T], Key, T) :- !.
map_remove([(K, V) | T], Key, [(K, V) | NLIST]) :-
		    K < Key, map_remove(T, Key, NLIST), !.
map_remove(MapList, _, MapList).


map_replace([(Key, _) | T], Key, Value, [(Key, Value) | T]) :- !.
map_replace([(K, V) | T], Key, Value, [(K, V) | NLIST]) :-
         K < Key, map_replace(T, Key, Value, NLIST), !.
map_replace(MapList, _, _, MapList).