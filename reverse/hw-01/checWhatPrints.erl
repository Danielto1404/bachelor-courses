-module(task).
-import(string,[right/3, left/3, sub_string/3, concat/2]).
-export([ping/0, pong/0]).

ping() ->
    Pong = spawn(task, pong, []),
    Pong ! {self(), ping},
    receive
        pong ->
            pong
    end.

pong() ->
    receive
        {Ping, ping} ->
            Str1 = "This is a flag",
            Str2 = right(Str1,4,$.),
            Str3 = sub_string(Str1,5,7),
            Galf = concat(Str2, Str3),
            io:fwrite("~p ",[Galf]),
            Ping ! pong,
            Ping ! Galf
    end.
