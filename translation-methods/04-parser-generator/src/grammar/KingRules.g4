grammar KingRules;

@header {
import configurator.rules.*;
import configurator.rules.atoms.*;
}

mainGrammar
  : grammarRules? EOF
  ;

grammarRules
  : grammarName packageName rules
  ;

grammarName returns [String name]
  : GRAMMAR n=CAPITALIZED { $name = $n.text; }
  ;

packageName returns [String name]
  : LT_BRACE PACKAGE n=IDENTIFIER RT_BRACE { $name = $n.text; }
  ;

rules
  : singleRule+
  ;

singleRule
  : terminalRule
  | syntaxRule
  | skipRule
  ;

terminalRule returns [TerminalRule rule]
  : TERMINAL_NAME
    inheritedAttrs=argsList?
    ARROW
    returnAttrs=argsList?
    REGEXP
    CURLY_CODE?
      { $rule = new TerminalRule
          ( $TERMINAL_NAME.text,
            $CURLY_CODE.text,
            $REGEXP.text,
            $inheritedAttrs.text == null ? null : $inheritedAttrs.arguments,
            $returnAttrs.text    == null ? null : $returnAttrs.arguments
          );
      }
  ;

syntaxRule returns [String name]
  : IDENTIFIER inheritedAttrs=argsList? ARROW returnAttrs=argsList? allRules { $name = $IDENTIFIER.text; }
  ;

allRules
  : ruleLine
  | allRules OR ruleLine
  ;

ruleLine
  : atoms code=CURLY_CODE?
  ;

atoms
  : atomRule+
  ;

atomRule
  : syntax | terminal
  ;

terminal
  : TERMINAL_NAME takenArgsList?
  ;

syntax
  : IDENTIFIER takenArgsList?
  ;

skipRule returns [SkipRule rule]
  : SKIP_NAME TERMINAL_NAME ARROW REGEXP { $rule = new SkipRule($TERMINAL_NAME.text, $REGEXP.text); }
  ;

arg returns [Argument argument]
  : type=(IDENTIFIER | CAPITALIZED) name=IDENTIFIER { $argument = new Argument ($type.text, $name.text); }
  ;

takenArgs returns [StringBuilder nodeArgs]
  @init {
    $nodeArgs = new StringBuilder();
  }
  : ARG_CODE                     { $nodeArgs.append($ARG_CODE.text); }
  | acc=takenArgs COMMA ARG_CODE { $nodeArgs = $acc.nodeArgs; $nodeArgs.append(", ").append($ARG_CODE.text); }
  ;

takenArgsList returns [String toString]
  : L_BRACE takenArgs R_BRACE { $toString = "(" + $takenArgs.nodeArgs.toString() + ")"; }
  ;

args returns [ArrayList<Argument> arguments]
  @init {
    $arguments = new ArrayList<>();
  }
  : arg                { $arguments.add($arg.argument); }
  | acc=args COMMA arg { $arguments = $acc.arguments; $arguments.add($arg.argument); }
  ;

argsList returns [ArrayList<Argument> arguments]
  : L_BRACE acc=args R_BRACE { $arguments = $acc.arguments; }
  ;

// Skip block
SkipedChars
  : [ \t\r\n]+ -> skip
  ;

LT_BRACE : '<';
RT_BRACE : '>';

L_CURLY : '{';
R_CURLY : '}';

L_BRACE : '(';
R_BRACE : ')';

OR        : '|' ;
SEMICOLON : ';' ;


COMMA : ',';

PACKAGE : 'package';
GRAMMAR : 'grammar';

REGEXP     : '"' (.)*? '"' ;
SKIP_NAME  : 'skip';
ARROW      : '=>';
DOT        : '.';

IDENTIFIER
  : LOWERCASE
  ;

TERMINAL_NAME
  : [A-Z_]+
  ;

CURLY_CODE
  : L_CURLY CODE R_CURLY
  ;

LOWERCASE  : [a-z]([0-9a-zA-Z])*;
CAPITALIZED: [A-Z]([0-9a-zA-Z])*;

ARG_CODE
  : IDENTIFIER DOT IDENTIFIER (~[{}()])*
  | (~[{}()])* IDENTIFIER DOT IDENTIFIER
  ;

fragment
CODE
  : (~[{}])*
  ;