// Generated from /Users/daniilkorolev/Documents/GitHub/3-rd-year/translation-methods/03-automatic-parser-generators/src/grammar/CPP.g4 by ANTLR 4.9
package antlr;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link CPPParser}.
 */
public interface CPPListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link CPPParser#cpp}.
	 * @param ctx the parse tree
	 */
	void enterCpp(CPPParser.CppContext ctx);
	/**
	 * Exit a parse tree produced by {@link CPPParser#cpp}.
	 * @param ctx the parse tree
	 */
	void exitCpp(CPPParser.CppContext ctx);
	/**
	 * Enter a parse tree produced by {@link CPPParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterPrimaryExpression(CPPParser.PrimaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CPPParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitPrimaryExpression(CPPParser.PrimaryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CPPParser#postfixExpression}.
	 * @param ctx the parse tree
	 */
	void enterPostfixExpression(CPPParser.PostfixExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CPPParser#postfixExpression}.
	 * @param ctx the parse tree
	 */
	void exitPostfixExpression(CPPParser.PostfixExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code assListNextLevel}
	 * labeled alternative in {@link CPPParser#argumentExpressionList}.
	 * @param ctx the parse tree
	 */
	void enterAssListNextLevel(CPPParser.AssListNextLevelContext ctx);
	/**
	 * Exit a parse tree produced by the {@code assListNextLevel}
	 * labeled alternative in {@link CPPParser#argumentExpressionList}.
	 * @param ctx the parse tree
	 */
	void exitAssListNextLevel(CPPParser.AssListNextLevelContext ctx);
	/**
	 * Enter a parse tree produced by the {@code assList1}
	 * labeled alternative in {@link CPPParser#argumentExpressionList}.
	 * @param ctx the parse tree
	 */
	void enterAssList1(CPPParser.AssList1Context ctx);
	/**
	 * Exit a parse tree produced by the {@code assList1}
	 * labeled alternative in {@link CPPParser#argumentExpressionList}.
	 * @param ctx the parse tree
	 */
	void exitAssList1(CPPParser.AssList1Context ctx);
	/**
	 * Enter a parse tree produced by {@link CPPParser#unaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterUnaryExpression(CPPParser.UnaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CPPParser#unaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitUnaryExpression(CPPParser.UnaryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CPPParser#unaryOperator}.
	 * @param ctx the parse tree
	 */
	void enterUnaryOperator(CPPParser.UnaryOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link CPPParser#unaryOperator}.
	 * @param ctx the parse tree
	 */
	void exitUnaryOperator(CPPParser.UnaryOperatorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code multOp}
	 * labeled alternative in {@link CPPParser#multiplicativeExpression}.
	 * @param ctx the parse tree
	 */
	void enterMultOp(CPPParser.MultOpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code multOp}
	 * labeled alternative in {@link CPPParser#multiplicativeExpression}.
	 * @param ctx the parse tree
	 */
	void exitMultOp(CPPParser.MultOpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code multNext}
	 * labeled alternative in {@link CPPParser#multiplicativeExpression}.
	 * @param ctx the parse tree
	 */
	void enterMultNext(CPPParser.MultNextContext ctx);
	/**
	 * Exit a parse tree produced by the {@code multNext}
	 * labeled alternative in {@link CPPParser#multiplicativeExpression}.
	 * @param ctx the parse tree
	 */
	void exitMultNext(CPPParser.MultNextContext ctx);
	/**
	 * Enter a parse tree produced by the {@code addNext}
	 * labeled alternative in {@link CPPParser#additiveExpression}.
	 * @param ctx the parse tree
	 */
	void enterAddNext(CPPParser.AddNextContext ctx);
	/**
	 * Exit a parse tree produced by the {@code addNext}
	 * labeled alternative in {@link CPPParser#additiveExpression}.
	 * @param ctx the parse tree
	 */
	void exitAddNext(CPPParser.AddNextContext ctx);
	/**
	 * Enter a parse tree produced by the {@code addOp}
	 * labeled alternative in {@link CPPParser#additiveExpression}.
	 * @param ctx the parse tree
	 */
	void enterAddOp(CPPParser.AddOpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code addOp}
	 * labeled alternative in {@link CPPParser#additiveExpression}.
	 * @param ctx the parse tree
	 */
	void exitAddOp(CPPParser.AddOpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code relNext}
	 * labeled alternative in {@link CPPParser#relationalExpression}.
	 * @param ctx the parse tree
	 */
	void enterRelNext(CPPParser.RelNextContext ctx);
	/**
	 * Exit a parse tree produced by the {@code relNext}
	 * labeled alternative in {@link CPPParser#relationalExpression}.
	 * @param ctx the parse tree
	 */
	void exitRelNext(CPPParser.RelNextContext ctx);
	/**
	 * Enter a parse tree produced by the {@code relOp}
	 * labeled alternative in {@link CPPParser#relationalExpression}.
	 * @param ctx the parse tree
	 */
	void enterRelOp(CPPParser.RelOpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code relOp}
	 * labeled alternative in {@link CPPParser#relationalExpression}.
	 * @param ctx the parse tree
	 */
	void exitRelOp(CPPParser.RelOpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code eqOp}
	 * labeled alternative in {@link CPPParser#equalityExpression}.
	 * @param ctx the parse tree
	 */
	void enterEqOp(CPPParser.EqOpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code eqOp}
	 * labeled alternative in {@link CPPParser#equalityExpression}.
	 * @param ctx the parse tree
	 */
	void exitEqOp(CPPParser.EqOpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code eqNext}
	 * labeled alternative in {@link CPPParser#equalityExpression}.
	 * @param ctx the parse tree
	 */
	void enterEqNext(CPPParser.EqNextContext ctx);
	/**
	 * Exit a parse tree produced by the {@code eqNext}
	 * labeled alternative in {@link CPPParser#equalityExpression}.
	 * @param ctx the parse tree
	 */
	void exitEqNext(CPPParser.EqNextContext ctx);
	/**
	 * Enter a parse tree produced by the {@code andOp}
	 * labeled alternative in {@link CPPParser#logicalAndExpression}.
	 * @param ctx the parse tree
	 */
	void enterAndOp(CPPParser.AndOpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code andOp}
	 * labeled alternative in {@link CPPParser#logicalAndExpression}.
	 * @param ctx the parse tree
	 */
	void exitAndOp(CPPParser.AndOpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code andNext}
	 * labeled alternative in {@link CPPParser#logicalAndExpression}.
	 * @param ctx the parse tree
	 */
	void enterAndNext(CPPParser.AndNextContext ctx);
	/**
	 * Exit a parse tree produced by the {@code andNext}
	 * labeled alternative in {@link CPPParser#logicalAndExpression}.
	 * @param ctx the parse tree
	 */
	void exitAndNext(CPPParser.AndNextContext ctx);
	/**
	 * Enter a parse tree produced by the {@code orOp}
	 * labeled alternative in {@link CPPParser#logicalOrExpression}.
	 * @param ctx the parse tree
	 */
	void enterOrOp(CPPParser.OrOpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code orOp}
	 * labeled alternative in {@link CPPParser#logicalOrExpression}.
	 * @param ctx the parse tree
	 */
	void exitOrOp(CPPParser.OrOpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code orNext}
	 * labeled alternative in {@link CPPParser#logicalOrExpression}.
	 * @param ctx the parse tree
	 */
	void enterOrNext(CPPParser.OrNextContext ctx);
	/**
	 * Exit a parse tree produced by the {@code orNext}
	 * labeled alternative in {@link CPPParser#logicalOrExpression}.
	 * @param ctx the parse tree
	 */
	void exitOrNext(CPPParser.OrNextContext ctx);
	/**
	 * Enter a parse tree produced by the {@code assExprNextLevel}
	 * labeled alternative in {@link CPPParser#assignmentExpression}.
	 * @param ctx the parse tree
	 */
	void enterAssExprNextLevel(CPPParser.AssExprNextLevelContext ctx);
	/**
	 * Exit a parse tree produced by the {@code assExprNextLevel}
	 * labeled alternative in {@link CPPParser#assignmentExpression}.
	 * @param ctx the parse tree
	 */
	void exitAssExprNextLevel(CPPParser.AssExprNextLevelContext ctx);
	/**
	 * Enter a parse tree produced by the {@code assignExprList}
	 * labeled alternative in {@link CPPParser#assignmentExpression}.
	 * @param ctx the parse tree
	 */
	void enterAssignExprList(CPPParser.AssignExprListContext ctx);
	/**
	 * Exit a parse tree produced by the {@code assignExprList}
	 * labeled alternative in {@link CPPParser#assignmentExpression}.
	 * @param ctx the parse tree
	 */
	void exitAssignExprList(CPPParser.AssignExprListContext ctx);
	/**
	 * Enter a parse tree produced by {@link CPPParser#assignmentOperator}.
	 * @param ctx the parse tree
	 */
	void enterAssignmentOperator(CPPParser.AssignmentOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link CPPParser#assignmentOperator}.
	 * @param ctx the parse tree
	 */
	void exitAssignmentOperator(CPPParser.AssignmentOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link CPPParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterDeclaration(CPPParser.DeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link CPPParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitDeclaration(CPPParser.DeclarationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code initDeclNextLevel}
	 * labeled alternative in {@link CPPParser#initialValueDeclarator}.
	 * @param ctx the parse tree
	 */
	void enterInitDeclNextLevel(CPPParser.InitDeclNextLevelContext ctx);
	/**
	 * Exit a parse tree produced by the {@code initDeclNextLevel}
	 * labeled alternative in {@link CPPParser#initialValueDeclarator}.
	 * @param ctx the parse tree
	 */
	void exitInitDeclNextLevel(CPPParser.InitDeclNextLevelContext ctx);
	/**
	 * Enter a parse tree produced by the {@code initDeclEq}
	 * labeled alternative in {@link CPPParser#initialValueDeclarator}.
	 * @param ctx the parse tree
	 */
	void enterInitDeclEq(CPPParser.InitDeclEqContext ctx);
	/**
	 * Exit a parse tree produced by the {@code initDeclEq}
	 * labeled alternative in {@link CPPParser#initialValueDeclarator}.
	 * @param ctx the parse tree
	 */
	void exitInitDeclEq(CPPParser.InitDeclEqContext ctx);
	/**
	 * Enter a parse tree produced by {@link CPPParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(CPPParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link CPPParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(CPPParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link CPPParser#declarator}.
	 * @param ctx the parse tree
	 */
	void enterDeclarator(CPPParser.DeclaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link CPPParser#declarator}.
	 * @param ctx the parse tree
	 */
	void exitDeclarator(CPPParser.DeclaratorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code paramListNextLevel}
	 * labeled alternative in {@link CPPParser#functionArgs}.
	 * @param ctx the parse tree
	 */
	void enterParamListNextLevel(CPPParser.ParamListNextLevelContext ctx);
	/**
	 * Exit a parse tree produced by the {@code paramListNextLevel}
	 * labeled alternative in {@link CPPParser#functionArgs}.
	 * @param ctx the parse tree
	 */
	void exitParamListNextLevel(CPPParser.ParamListNextLevelContext ctx);
	/**
	 * Enter a parse tree produced by the {@code paramListComma}
	 * labeled alternative in {@link CPPParser#functionArgs}.
	 * @param ctx the parse tree
	 */
	void enterParamListComma(CPPParser.ParamListCommaContext ctx);
	/**
	 * Exit a parse tree produced by the {@code paramListComma}
	 * labeled alternative in {@link CPPParser#functionArgs}.
	 * @param ctx the parse tree
	 */
	void exitParamListComma(CPPParser.ParamListCommaContext ctx);
	/**
	 * Enter a parse tree produced by {@link CPPParser#argDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterArgDeclaration(CPPParser.ArgDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link CPPParser#argDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitArgDeclaration(CPPParser.ArgDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link CPPParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(CPPParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CPPParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(CPPParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link CPPParser#codeBlock}.
	 * @param ctx the parse tree
	 */
	void enterCodeBlock(CPPParser.CodeBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link CPPParser#codeBlock}.
	 * @param ctx the parse tree
	 */
	void exitCodeBlock(CPPParser.CodeBlockContext ctx);
	/**
	 * Enter a parse tree produced by the {@code blockItemListNewLine}
	 * labeled alternative in {@link CPPParser#blockItemList}.
	 * @param ctx the parse tree
	 */
	void enterBlockItemListNewLine(CPPParser.BlockItemListNewLineContext ctx);
	/**
	 * Exit a parse tree produced by the {@code blockItemListNewLine}
	 * labeled alternative in {@link CPPParser#blockItemList}.
	 * @param ctx the parse tree
	 */
	void exitBlockItemListNewLine(CPPParser.BlockItemListNewLineContext ctx);
	/**
	 * Enter a parse tree produced by the {@code blockItemListN}
	 * labeled alternative in {@link CPPParser#blockItemList}.
	 * @param ctx the parse tree
	 */
	void enterBlockItemListN(CPPParser.BlockItemListNContext ctx);
	/**
	 * Exit a parse tree produced by the {@code blockItemListN}
	 * labeled alternative in {@link CPPParser#blockItemList}.
	 * @param ctx the parse tree
	 */
	void exitBlockItemListN(CPPParser.BlockItemListNContext ctx);
	/**
	 * Enter a parse tree produced by {@link CPPParser#blockItem}.
	 * @param ctx the parse tree
	 */
	void enterBlockItem(CPPParser.BlockItemContext ctx);
	/**
	 * Exit a parse tree produced by {@link CPPParser#blockItem}.
	 * @param ctx the parse tree
	 */
	void exitBlockItem(CPPParser.BlockItemContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprMeaningful}
	 * labeled alternative in {@link CPPParser#expressionStatement}.
	 * @param ctx the parse tree
	 */
	void enterExprMeaningful(CPPParser.ExprMeaningfulContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprMeaningful}
	 * labeled alternative in {@link CPPParser#expressionStatement}.
	 * @param ctx the parse tree
	 */
	void exitExprMeaningful(CPPParser.ExprMeaningfulContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprEmpty}
	 * labeled alternative in {@link CPPParser#expressionStatement}.
	 * @param ctx the parse tree
	 */
	void enterExprEmpty(CPPParser.ExprEmptyContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprEmpty}
	 * labeled alternative in {@link CPPParser#expressionStatement}.
	 * @param ctx the parse tree
	 */
	void exitExprEmpty(CPPParser.ExprEmptyContext ctx);
	/**
	 * Enter a parse tree produced by {@link CPPParser#ifBlock}.
	 * @param ctx the parse tree
	 */
	void enterIfBlock(CPPParser.IfBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link CPPParser#ifBlock}.
	 * @param ctx the parse tree
	 */
	void exitIfBlock(CPPParser.IfBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link CPPParser#iterationBlock}.
	 * @param ctx the parse tree
	 */
	void enterIterationBlock(CPPParser.IterationBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link CPPParser#iterationBlock}.
	 * @param ctx the parse tree
	 */
	void exitIterationBlock(CPPParser.IterationBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link CPPParser#forCondition}.
	 * @param ctx the parse tree
	 */
	void enterForCondition(CPPParser.ForConditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CPPParser#forCondition}.
	 * @param ctx the parse tree
	 */
	void exitForCondition(CPPParser.ForConditionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code forExprNextLevel}
	 * labeled alternative in {@link CPPParser#forExpression}.
	 * @param ctx the parse tree
	 */
	void enterForExprNextLevel(CPPParser.ForExprNextLevelContext ctx);
	/**
	 * Exit a parse tree produced by the {@code forExprNextLevel}
	 * labeled alternative in {@link CPPParser#forExpression}.
	 * @param ctx the parse tree
	 */
	void exitForExprNextLevel(CPPParser.ForExprNextLevelContext ctx);
	/**
	 * Enter a parse tree produced by the {@code forExpr1}
	 * labeled alternative in {@link CPPParser#forExpression}.
	 * @param ctx the parse tree
	 */
	void enterForExpr1(CPPParser.ForExpr1Context ctx);
	/**
	 * Exit a parse tree produced by the {@code forExpr1}
	 * labeled alternative in {@link CPPParser#forExpression}.
	 * @param ctx the parse tree
	 */
	void exitForExpr1(CPPParser.ForExpr1Context ctx);
	/**
	 * Enter a parse tree produced by {@link CPPParser#forDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterForDeclaration(CPPParser.ForDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link CPPParser#forDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitForDeclaration(CPPParser.ForDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link CPPParser#struct}.
	 * @param ctx the parse tree
	 */
	void enterStruct(CPPParser.StructContext ctx);
	/**
	 * Exit a parse tree produced by {@link CPPParser#struct}.
	 * @param ctx the parse tree
	 */
	void exitStruct(CPPParser.StructContext ctx);
	/**
	 * Enter a parse tree produced by the {@code structDec1}
	 * labeled alternative in {@link CPPParser#structDeclarationList}.
	 * @param ctx the parse tree
	 */
	void enterStructDec1(CPPParser.StructDec1Context ctx);
	/**
	 * Exit a parse tree produced by the {@code structDec1}
	 * labeled alternative in {@link CPPParser#structDeclarationList}.
	 * @param ctx the parse tree
	 */
	void exitStructDec1(CPPParser.StructDec1Context ctx);
	/**
	 * Enter a parse tree produced by the {@code structDecNextLevel}
	 * labeled alternative in {@link CPPParser#structDeclarationList}.
	 * @param ctx the parse tree
	 */
	void enterStructDecNextLevel(CPPParser.StructDecNextLevelContext ctx);
	/**
	 * Exit a parse tree produced by the {@code structDecNextLevel}
	 * labeled alternative in {@link CPPParser#structDeclarationList}.
	 * @param ctx the parse tree
	 */
	void exitStructDecNextLevel(CPPParser.StructDecNextLevelContext ctx);
	/**
	 * Enter a parse tree produced by the {@code jumpContinue}
	 * labeled alternative in {@link CPPParser#jumpStatement}.
	 * @param ctx the parse tree
	 */
	void enterJumpContinue(CPPParser.JumpContinueContext ctx);
	/**
	 * Exit a parse tree produced by the {@code jumpContinue}
	 * labeled alternative in {@link CPPParser#jumpStatement}.
	 * @param ctx the parse tree
	 */
	void exitJumpContinue(CPPParser.JumpContinueContext ctx);
	/**
	 * Enter a parse tree produced by the {@code jumpBreak}
	 * labeled alternative in {@link CPPParser#jumpStatement}.
	 * @param ctx the parse tree
	 */
	void enterJumpBreak(CPPParser.JumpBreakContext ctx);
	/**
	 * Exit a parse tree produced by the {@code jumpBreak}
	 * labeled alternative in {@link CPPParser#jumpStatement}.
	 * @param ctx the parse tree
	 */
	void exitJumpBreak(CPPParser.JumpBreakContext ctx);
	/**
	 * Enter a parse tree produced by the {@code jumpReturn}
	 * labeled alternative in {@link CPPParser#jumpStatement}.
	 * @param ctx the parse tree
	 */
	void enterJumpReturn(CPPParser.JumpReturnContext ctx);
	/**
	 * Exit a parse tree produced by the {@code jumpReturn}
	 * labeled alternative in {@link CPPParser#jumpStatement}.
	 * @param ctx the parse tree
	 */
	void exitJumpReturn(CPPParser.JumpReturnContext ctx);
	/**
	 * Enter a parse tree produced by {@link CPPParser#translationUnit}.
	 * @param ctx the parse tree
	 */
	void enterTranslationUnit(CPPParser.TranslationUnitContext ctx);
	/**
	 * Exit a parse tree produced by {@link CPPParser#translationUnit}.
	 * @param ctx the parse tree
	 */
	void exitTranslationUnit(CPPParser.TranslationUnitContext ctx);
	/**
	 * Enter a parse tree produced by {@link CPPParser#externalDefinition}.
	 * @param ctx the parse tree
	 */
	void enterExternalDefinition(CPPParser.ExternalDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CPPParser#externalDefinition}.
	 * @param ctx the parse tree
	 */
	void exitExternalDefinition(CPPParser.ExternalDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CPPParser#function}.
	 * @param ctx the parse tree
	 */
	void enterFunction(CPPParser.FunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CPPParser#function}.
	 * @param ctx the parse tree
	 */
	void exitFunction(CPPParser.FunctionContext ctx);
}