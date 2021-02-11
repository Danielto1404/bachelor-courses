// Generated from /Users/daniilkorolev/Documents/GitHub/3-rd-year/translation-methods/03-automatic-parser-generators/src/grammar/CPP.g4 by ANTLR 4.9
package antlr;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link CPPParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface CPPVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link CPPParser#cpp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCpp(CPPParser.CppContext ctx);
	/**
	 * Visit a parse tree produced by {@link CPPParser#primaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimaryExpression(CPPParser.PrimaryExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CPPParser#postfixExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPostfixExpression(CPPParser.PostfixExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code assListNextLevel}
	 * labeled alternative in {@link CPPParser#argumentExpressionList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssListNextLevel(CPPParser.AssListNextLevelContext ctx);
	/**
	 * Visit a parse tree produced by the {@code assList1}
	 * labeled alternative in {@link CPPParser#argumentExpressionList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssList1(CPPParser.AssList1Context ctx);
	/**
	 * Visit a parse tree produced by {@link CPPParser#unaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryExpression(CPPParser.UnaryExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CPPParser#unaryOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryOperator(CPPParser.UnaryOperatorContext ctx);
	/**
	 * Visit a parse tree produced by the {@code multOp}
	 * labeled alternative in {@link CPPParser#multiplicativeExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultOp(CPPParser.MultOpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code multNext}
	 * labeled alternative in {@link CPPParser#multiplicativeExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultNext(CPPParser.MultNextContext ctx);
	/**
	 * Visit a parse tree produced by the {@code addNext}
	 * labeled alternative in {@link CPPParser#additiveExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddNext(CPPParser.AddNextContext ctx);
	/**
	 * Visit a parse tree produced by the {@code addOp}
	 * labeled alternative in {@link CPPParser#additiveExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddOp(CPPParser.AddOpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code relNext}
	 * labeled alternative in {@link CPPParser#relationalExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelNext(CPPParser.RelNextContext ctx);
	/**
	 * Visit a parse tree produced by the {@code relOp}
	 * labeled alternative in {@link CPPParser#relationalExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelOp(CPPParser.RelOpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code eqOp}
	 * labeled alternative in {@link CPPParser#equalityExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqOp(CPPParser.EqOpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code eqNext}
	 * labeled alternative in {@link CPPParser#equalityExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqNext(CPPParser.EqNextContext ctx);
	/**
	 * Visit a parse tree produced by the {@code andOp}
	 * labeled alternative in {@link CPPParser#logicalAndExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndOp(CPPParser.AndOpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code andNext}
	 * labeled alternative in {@link CPPParser#logicalAndExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndNext(CPPParser.AndNextContext ctx);
	/**
	 * Visit a parse tree produced by the {@code orOp}
	 * labeled alternative in {@link CPPParser#logicalOrExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrOp(CPPParser.OrOpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code orNext}
	 * labeled alternative in {@link CPPParser#logicalOrExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrNext(CPPParser.OrNextContext ctx);
	/**
	 * Visit a parse tree produced by the {@code assExprNextLevel}
	 * labeled alternative in {@link CPPParser#assignmentExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssExprNextLevel(CPPParser.AssExprNextLevelContext ctx);
	/**
	 * Visit a parse tree produced by the {@code assignExprList}
	 * labeled alternative in {@link CPPParser#assignmentExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignExprList(CPPParser.AssignExprListContext ctx);
	/**
	 * Visit a parse tree produced by {@link CPPParser#assignmentOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignmentOperator(CPPParser.AssignmentOperatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link CPPParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaration(CPPParser.DeclarationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code initDeclNextLevel}
	 * labeled alternative in {@link CPPParser#initialValueDeclarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInitDeclNextLevel(CPPParser.InitDeclNextLevelContext ctx);
	/**
	 * Visit a parse tree produced by the {@code initDeclEq}
	 * labeled alternative in {@link CPPParser#initialValueDeclarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInitDeclEq(CPPParser.InitDeclEqContext ctx);
	/**
	 * Visit a parse tree produced by {@link CPPParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(CPPParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link CPPParser#declarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclarator(CPPParser.DeclaratorContext ctx);
	/**
	 * Visit a parse tree produced by the {@code paramListNextLevel}
	 * labeled alternative in {@link CPPParser#functionArgs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParamListNextLevel(CPPParser.ParamListNextLevelContext ctx);
	/**
	 * Visit a parse tree produced by the {@code paramListComma}
	 * labeled alternative in {@link CPPParser#functionArgs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParamListComma(CPPParser.ParamListCommaContext ctx);
	/**
	 * Visit a parse tree produced by {@link CPPParser#argDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgDeclaration(CPPParser.ArgDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link CPPParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(CPPParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link CPPParser#codeBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCodeBlock(CPPParser.CodeBlockContext ctx);
	/**
	 * Visit a parse tree produced by the {@code blockItemListNewLine}
	 * labeled alternative in {@link CPPParser#blockItemList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockItemListNewLine(CPPParser.BlockItemListNewLineContext ctx);
	/**
	 * Visit a parse tree produced by the {@code blockItemListN}
	 * labeled alternative in {@link CPPParser#blockItemList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockItemListN(CPPParser.BlockItemListNContext ctx);
	/**
	 * Visit a parse tree produced by {@link CPPParser#blockItem}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockItem(CPPParser.BlockItemContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprMeaningful}
	 * labeled alternative in {@link CPPParser#expressionStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprMeaningful(CPPParser.ExprMeaningfulContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprEmpty}
	 * labeled alternative in {@link CPPParser#expressionStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprEmpty(CPPParser.ExprEmptyContext ctx);
	/**
	 * Visit a parse tree produced by {@link CPPParser#ifBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfBlock(CPPParser.IfBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link CPPParser#iterationBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIterationBlock(CPPParser.IterationBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link CPPParser#forCondition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForCondition(CPPParser.ForConditionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code forExprNextLevel}
	 * labeled alternative in {@link CPPParser#forExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForExprNextLevel(CPPParser.ForExprNextLevelContext ctx);
	/**
	 * Visit a parse tree produced by the {@code forExpr1}
	 * labeled alternative in {@link CPPParser#forExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForExpr1(CPPParser.ForExpr1Context ctx);
	/**
	 * Visit a parse tree produced by {@link CPPParser#forDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForDeclaration(CPPParser.ForDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link CPPParser#struct}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStruct(CPPParser.StructContext ctx);
	/**
	 * Visit a parse tree produced by the {@code structDec1}
	 * labeled alternative in {@link CPPParser#structDeclarationList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStructDec1(CPPParser.StructDec1Context ctx);
	/**
	 * Visit a parse tree produced by the {@code structDecNextLevel}
	 * labeled alternative in {@link CPPParser#structDeclarationList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStructDecNextLevel(CPPParser.StructDecNextLevelContext ctx);
	/**
	 * Visit a parse tree produced by the {@code jumpContinue}
	 * labeled alternative in {@link CPPParser#jumpStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJumpContinue(CPPParser.JumpContinueContext ctx);
	/**
	 * Visit a parse tree produced by the {@code jumpBreak}
	 * labeled alternative in {@link CPPParser#jumpStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJumpBreak(CPPParser.JumpBreakContext ctx);
	/**
	 * Visit a parse tree produced by the {@code jumpReturn}
	 * labeled alternative in {@link CPPParser#jumpStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJumpReturn(CPPParser.JumpReturnContext ctx);
	/**
	 * Visit a parse tree produced by {@link CPPParser#translationUnit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTranslationUnit(CPPParser.TranslationUnitContext ctx);
	/**
	 * Visit a parse tree produced by {@link CPPParser#externalDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExternalDefinition(CPPParser.ExternalDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CPPParser#function}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction(CPPParser.FunctionContext ctx);
}