split(null, _, null, null) :- !.
split(node(Key, Val, Pr, L, R), X, node(Key, Val, Pr, L, RL), RR) :-
        Key < X, split(R, X, RL, RR), !.
split(node(Key, Val, Pr, L, R), X, LL, node(Key, Val, Pr, LR, R)) :-
        Key >= X, split(L, X, LL, LR), !.


merge(null, Node, Node) :- !.
merge(Node, null, Node) :- !.
merge(node(Key1, Val1, Pr1, L1, R1), node(Key2, Val2, Pr2, L2, R2),
        node(Key1, Val1, Pr1, L1, MergedR)) :-
        Pr1 < Pr2, merge(R1, node(Key2, Val2, Pr2, L2, R2), MergedR), !.
merge(node(Key1, Val1, Pr1, L1, R1), node(Key2, Val2, Pr2, L2, R2),
        node(Key2, Val2, Pr2, MergedL, R2)) :-
        Pr1 >= Pr2, merge(node(Key1, Val1, Pr1, L1, R1), L2, MergedL), !.


map_put(null, Key, Val, node(Key, Val, Pr, null, null)) :- rand_int(99999999, Pr), !.
map_put(Node, Key, Val, Result) :-
        split(Node, Key, LeftTree, RigthTree),
        split(RightTree, Key + 1, _, RR),
        rand_int(99999999, Pr),
        merge(LeftTree, node(Key, Val, Pr, null, null), CurNode),
        merge(CurNode, RR, Result), !.


map_remove(Node, X, Result) :-
        split(Node, X, LeftTree, RightTree),
        split(RightTree, X + 1, _, RR),
        merge(LeftTree, RR, Result), !.


tree_append([], Node, Node).
tree_append([(Key, Val) | Rest], CurNode, Result) :-
        map_put(CurNode, Key, Val, CurRes), write(CurRes), tree_append(Rest, CurRes, Result).
tree_build(ListMap, Node) :- tree_append(ListMap, null, Node).


map_get(node(FindKey, FindVal, _, _, _), FindKey, FindVal) :- !.
map_get(node(Key, _, _, L, _), FindKey, FindVal) :-
        FindKey < Key, map_get(L, FindKey, FindVal), !.
map_get(node(Key, _, _, _, R), FindKey, FindVal) :-
        FindKey > Key, map_get(R, FindKey, FindVal), !.


map_replace(null, _, _, null) :- !.
map_replace(node(Key, _, _, L, R), Key, Value, node(Key, Value, _, L, R)) :- !.
map_replace(node(K, _, _, L, R), Key, Value, node(K, _, _, L, Res)) :-
         K < Key, map_replace(R, Key, Value, Res), !.
map_replace(node(K, _, _, L, R), Key, Value, node(K, _, _, Res, R)) :-
         K > Key, map_replace(L, Key, Value, Res), !.