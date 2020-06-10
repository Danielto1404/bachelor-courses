
{
module Parsing.Grammar (parse) where

import Parsing.Lexer
import Logic.Expression
}

%name parse
%tokentype { Token }
%error { parseError }

%token VAR          { TOKEN_VARIABLE $$  }
%token PREDICATE    { TOKEN_PREDICATE $$ }

%token OPENED_BRACE { TOKEN_OPENED_BRACE }
%token CLOSED_BRACE { TOKEN_CLOSED_BRACE }

%token ANY          { TOKEN_ANY          }
%token EXISTS       { TOKEN_EXISTS       }

%token DOT          { TOKEN_DOT          }

%token IMPLICATION  { TOKEN_IMPLICATION  }
%token OR           { TOKEN_OR           }
%token AND          { TOKEN_AND          }
%token EQUAL        { TOKEN_EQUAL        }
%token ADD          { TOKEN_ADD          }
%token MUL          { TOKEN_MUL          }

%token NEXT         { TOKEN_NEXT         }
%token NOT          { TOKEN_NOT          }

%token ZERO         { TOKEN_ZERO         }

%%

expression            : or_expression IMPLICATION expression          { Binary Impl $1 $3 }
                      | or_expression                                 { $1 }

or_expression         : or_expression OR and_expression               { Binary Or $1 $3 }
                      | and_expression                                { $1 }

and_expression        : and_expression AND unary_expression           { Binary And $1 $3 }
                      | unary_expression                              { $1 }

unary_expression      : predicate_expression                          { $1 }
                      | NOT unary_expression                          { Unary Not $2 }
                      | OPENED_BRACE expression CLOSED_BRACE          { $2 }
                      | ANY VAR DOT expression 	                      { Quan Any $2 $4}
                      | EXISTS VAR DOT expression 	              { Quan Exists $2 $4}

predicate_expression  : PREDICATE		                      { Predicate $1  }
		      | therm_expression EQUAL therm_expression       { Binary Equal $1 $3 }

therm_expression      : addendum_expression			      { $1 }
		      | therm_expression ADD addendum_expression      { Binary Add $1 $3 }

addendum_expression   : multiplied_expression  	     	              { $1 }
	              | addendum_expression MUL multiplied_expression { Binary Mul $1 $3 }

multiplied_expression : VAR				              { Var $1 }
		      | ZERO 				              { Zero }
		      | OPENED_BRACE therm_expression CLOSED_BRACE    { $2 }
		      | multiplied_expression NEXT	              { Unary Next $1 }

{

parseError :: [Token] -> a
parseError e = error $ "Parse error occured :((((("

}