-module(spbctf_2).
-export([main/1]).

%ord returns ASCII code of character, e.g. ord("a") return 97
ord(A) -> hd(A).

main(_) ->
    io:format("Ave!\n"),
    Flag = case io:fread("Give me the flag: ", "~s") of
        {ok, [R|_]} -> R;
        {error, _}  ->
            io:format("Error while reading your string"),
            erlang:exit(error_read)
    end,
    Caesar = fun(A) -> (A - ord("a") + 3) rem 26 + ord("a") end,
    Caesared = lists:map(Caesar, Flag),
    case string:equal(Caesared, "yhqlylglkdfnhulfl") of
        true  -> io:format("Right\n");
        false -> io:format("Wrong\n")
    end.
