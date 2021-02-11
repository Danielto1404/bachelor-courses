grammar CPP;

cpp
    :   code=translationUnit? EOF
    ;

primaryExpression
    :   ID
    |   Number
    |   StringLiteral+
    |   '(' assignmentExpression ')'
    ;

postfixExpression
    :   primaryExpression                                  // Assigment or (String | ID | Number)
    |   postfixExpression '(' argumentExpressionList? ')'  // Currying Function calls
    |   postfixExpression '.' ID                           // Fields access operator
    ;

argumentExpressionList
    :   assignmentExpression #assListNextLevel
    |   args=argumentExpressionList Comma assign=assignmentExpression #assList1
    ;

unaryExpression
    :   postfixExpression
    |   unaryOperator unaryExpression
    ;

unaryOperator
    :   op=Plus | op=Minus | op=Not
    ;

multiplicativeExpression
    :   unaryExpression #multNext
    |   mulExpr=multiplicativeExpression op=Star unary=unaryExpression #multOp
    |   mulExpr=multiplicativeExpression op=Div unary=unaryExpression #multOp
    |   mulExpr=multiplicativeExpression op=Mod unary=unaryExpression #multOp
    ;

additiveExpression
    :   multiplicativeExpression #addNext
    |   addExpr=additiveExpression op=Plus mulExpr=multiplicativeExpression #addOp
    |   addExpr=additiveExpression op=Minus mulExpr=multiplicativeExpression #addOp
    ;

relationalExpression
    :   additiveExpression #relNext
    |   relExpr=relationalExpression op=Less addExpr=additiveExpression #relOp
    |   relExpr=relationalExpression op=Greater addExpr=additiveExpression #relOp
    |   relExpr=relationalExpression op=LessEqual addExpr=additiveExpression #relOp
    |   relExpr=relationalExpression op=GreaterEqual addExpr=additiveExpression #relOp
    ;

equalityExpression
    :   relationalExpression #eqNext
    |   eqExpr=equalityExpression op=Equal relExpr=relationalExpression #eqOp
    |   eqExpr=equalityExpression op=NotEqual relExpr=relationalExpression #eqOp
    ;

logicalAndExpression
    :   equalityExpression #andNext
    |   logicalAnd=logicalAndExpression op=And eqExpr=equalityExpression #andOp
    ;

logicalOrExpression
    :   logicalAndExpression #orNext
    |   logicalOr=logicalOrExpression op=Or logicalAnd=logicalAndExpression #orOp
    ;

assignmentExpression
    :   logicalOrExpression #assExprNextLevel
    |   unary=unaryExpression op=assignmentOperator assignExpr=assignmentExpression #assignExprList
    ;

assignmentOperator
    :   Assign | StarAssign | DivAssign | ModAssign | PlusAssign | MinusAssign
    ;

declaration
    :   t=type decl=initialValueDeclarator Semi
    ;

initialValueDeclarator
    :   declarator #initDeclNextLevel                             // Empty declaration
    |   e1=declarator Assign e2=assignmentExpression #initDeclEq  // For intial value declaration
    ;

type
    :   ('void'
    |   'char'
    |   'short'
    |   'int'
    |   'long'
    |   'float'
    |   'double')
    |   ID
    ;

declarator
    :   ID
    |   '(' declarator ')'
    |   declarator '(' functionArgs? ')'
    ;

functionArgs
    :   argDeclaration #paramListNextLevel
    |   e1=functionArgs Comma e2=argDeclaration #paramListComma
    ;

argDeclaration
    :   t=type argName=ID
    ;

statement returns [boolean ign]
    :   codeBlock
    |   e=expressionStatement {$ign = $e.ign;}
    |   ifBlock
    |   iterationBlock
    |   jumpStatement
    ;

codeBlock returns [boolean ignoreNextLine]
    :   LeftBrace scopedCode=blockItemList? RightBrace
    ;

blockItemList returns [boolean ign]
    :   blockItem #blockItemListNewLine
    |   list=blockItemList item=blockItem {$list.ign = $item.ign;} #blockItemListN
    ;

blockItem returns [boolean ign]
    :   e=statement {$ign = $e.ign;}
    |   declaration
    ;

expressionStatement returns [boolean ign]
    :   e=assignmentExpression Semi #exprMeaningful
    |   Semi {$ign = true;} #exprEmpty
    ;

ifBlock
    :   If '(' condition=assignmentExpression ')' ifCode=codeBlock (Else elseCode=codeBlock)?
    ;

iterationBlock
    :   name=While '(' e1=assignmentExpression ')' code=codeBlock
    |   name=For '(' forCond=forCondition ')' code=codeBlock
    |   name=Do code=codeBlock While '(' e1=assignmentExpression ')'
    ;

forCondition
	:   d=forDeclaration? Semi e1=forExpression? Semi e2=forExpression?
	|   assignExpr=assignmentExpression? Semi e1=forExpression? Semi e2=forExpression?
	;

forExpression
    :   assignmentExpression #forExprNextLevel
    |   forExprList=forExpression Comma assignExpr=assignmentExpression #forExpr1
    ;

forDeclaration
    :   t=type initDecl=initialValueDeclarator
    ;

struct
    :   'struct' n=ID '{' list=structDeclarationList? '}'
    ;

structDeclarationList
    :   declaration #structDecNextLevel
    |   fields=structDeclarationList decl=declaration #structDec1
    ;


jumpStatement
    :   Continue Semi #jumpContinue
    |   Break Semi #jumpBreak
    |   Return (returnValue=assignmentExpression)? Semi #jumpReturn
    ;

translationUnit
    :   externalDefinition
    |   translationUnit externalDefinition
    ;

externalDefinition
    : function
    | struct
    ;

function
    :   returnType=type definition=declarator code=codeBlock
    ;


Break : 'break';
Char : 'char';
Continue : 'continue';
Double : 'double';
Else : 'else';
Float : 'float';
If : 'if';
Int : 'int';
Long : 'long';
Return : 'return';
Short : 'short';
Void : 'void';
While : 'while';
For: 'for';
Do: 'do';

LeftParen : '(';
RightParen : ')';
LeftBracket : '[';
RightBracket : ']';
LeftBrace : '{';
RightBrace : '}';

Less : '<';
LessEqual : '<=';
Greater : '>';
GreaterEqual : '>=';


Plus : '+';
Minus : '-';
Star : '*';
Div : '/';
Mod : '%';

And : '&&';
Or : '||';
Not : '!';

Colon : ':';
Semi : ';';
Comma : ',';

Assign : '=';

// '*=' | '/=' | '%=' | '+=' | '-=' | '<<=' | '>>=' | '&=' | '^=' | '|='
StarAssign : '*=';
DivAssign : '/=';
ModAssign : '%=';
PlusAssign : '+=';
MinusAssign : '-=';

Equal : '==';
NotEqual : '!=';

ID : Letter (Letter | Digit)*
   ;

fragment
Letter
    :   [a-zA-Z_]
    ;

fragment
Digit
    :   [0-9]
    ;

Number
    :   DecimalNumber
    |   FractionalNumber
    ;

fragment
DecimalNumber
    :   Digit+
    ;

fragment
FractionalNumber
    :   DigitSequence? '.' DigitSequence
    |   DigitSequence '.'
    ;

fragment
Sign
    :   '+' | '-'
    ;

DigitSequence
    :   Digit+
    ;

StringLiteral
    :   '"' SChar* '"'
    ;

fragment
SChar
    :   ~["\\\r\n]
    ;

Whitespace
    :   [ \t]+
        -> skip
    ;

Newline
    :   ('\r' '\n'? | '\n')
        -> skip
    ;

BlockComment
    :   '/*' .*? '*/'
        -> skip
    ;

LineComment
    :   '//' ~[\r\n]*
        -> skip
    ;




//fragment
//CCharSequence
//    :   CChar+
//    ;
//


//fragment
//CChar
//    :   ~['\\\r\n]
//    |   SimpleEscapeSequence
//    ;


//fragment
//SimpleEscapeSequence
//    :   '\\' ['"?abfnrtv\\]
//    ;


//fragment
//SCharSequence
//    :   SChar+
//    ;

//fragment
//SChar
//    :   ~["\\\r\n]
//    |   SimpleEscapeSequence
//    |   '\\\n'   // Added line
//    |   '\\\r\n' // Added line
//    ;