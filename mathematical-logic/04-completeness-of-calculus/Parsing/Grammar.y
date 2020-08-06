{
module Parsing.Grammar (parse) where

import Parsing.Lexer
import Logic.Expression
}

%name parse
%tokentype { Token }
%error { parseError }

%token VAR          { TOKEN_VARIABLE $$  }
%token OPENED_BRACE { TOKEN_OPENED_BRACE }
%token CLOSED_BRACE { TOKEN_CLOSED_BRACE }
%token IMPLICATION  { TOKEN_IMPLICATION  }
%token OR           { TOKEN_OR           }
%token AND          { TOKEN_AND          }
%token NOT          { TOKEN_NOT          }

%%

expression     : or_expression IMPLICATION expression { Binary Impl $1 $3 }
               | or_expression                        { $1 }

or_expression  : or_expression OR and_expression      { Binary Or $1 $3 }
               | and_expression                       { $1 }

and_expression : and_expression AND not_expression    { Binary And $1 $3 }
               | not_expression                       { $1 }

not_expression : VAR                                  { Var $1 }
               | NOT not_expression                   { Not $2 }
               | OPENED_BRACE expression CLOSED_BRACE { $2 }

{

parseError :: [Token] -> a
parseError e = error $ "Parse error occured :((((("

}
