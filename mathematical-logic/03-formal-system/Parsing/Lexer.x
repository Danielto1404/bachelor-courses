{
module Parsing.Lexer where
}

%wrapper "basic"

$alpha        = [a-z]
$capitalAlpha = [A-Z]
$spaces       = [\ \t]

tokens :-
       $white+			    ;
       $alpha           	{ \x -> TOKEN_VARIABLE x   }
       $capitalAlpha        { \x -> TOKEN_PREDICATE x  }

       \(		            { \x -> TOKEN_OPENED_BRACE }
       \) 			        { \x -> TOKEN_CLOSED_BRACE }

       \@                   { \x -> TOKEN_ANY          }
       \?                   { \x -> TOKEN_EXISTS       }

       \.                   { \x -> TOKEN_DOT          }

       "->"		            { \x -> TOKEN_IMPLICATION  }
       \|		            { \x -> TOKEN_OR	       }
       \&		            { \x -> TOKEN_AND 	       }
       \=                   { \x -> TOKEN_EQUAL        }
       \+                   { \x -> TOKEN_ADD          }
       \*                   { \x -> TOKEN_MUL          }

       \'                   { \x -> TOKEN_NEXT         }
       \!		            { \x -> TOKEN_NOT	       }

       0                    { \x -> TOKEN_ZERO         }
{
data Token
     = TOKEN_VARIABLE String
     | TOKEN_PREDICATE String

     | TOKEN_OPENED_BRACE
     | TOKEN_CLOSED_BRACE

     | TOKEN_ANY
     | TOKEN_EXISTS

     | TOKEN_DOT

     | TOKEN_IMPLICATION
     | TOKEN_OR
     | TOKEN_AND
     | TOKEN_EQUAL
     | TOKEN_ADD
     | TOKEN_MUL

     | TOKEN_NEXT
     | TOKEN_NOT

     | TOKEN_ZERO
}
