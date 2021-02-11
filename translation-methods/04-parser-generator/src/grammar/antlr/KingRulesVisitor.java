// Generated from /Users/daniilkorolev/Documents/GitHub/3-rd-year/translation-methods/04-parser-generator/src/grammar/KingRules.g4 by ANTLR 4.9
package grammar.antlr;

import configurator.rules.*;
import configurator.rules.atoms.*;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link KingRulesParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface KingRulesVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link KingRulesParser#mainGrammar}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMainGrammar(KingRulesParser.MainGrammarContext ctx);
	/**
	 * Visit a parse tree produced by {@link KingRulesParser#grammarRules}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGrammarRules(KingRulesParser.GrammarRulesContext ctx);
	/**
	 * Visit a parse tree produced by {@link KingRulesParser#grammarName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGrammarName(KingRulesParser.GrammarNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link KingRulesParser#packageName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPackageName(KingRulesParser.PackageNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link KingRulesParser#rules}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRules(KingRulesParser.RulesContext ctx);
	/**
	 * Visit a parse tree produced by {@link KingRulesParser#singleRule}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSingleRule(KingRulesParser.SingleRuleContext ctx);
	/**
	 * Visit a parse tree produced by {@link KingRulesParser#terminalRule}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTerminalRule(KingRulesParser.TerminalRuleContext ctx);
	/**
	 * Visit a parse tree produced by {@link KingRulesParser#syntaxRule}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSyntaxRule(KingRulesParser.SyntaxRuleContext ctx);
	/**
	 * Visit a parse tree produced by {@link KingRulesParser#allRules}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAllRules(KingRulesParser.AllRulesContext ctx);
	/**
	 * Visit a parse tree produced by {@link KingRulesParser#ruleLine}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRuleLine(KingRulesParser.RuleLineContext ctx);
	/**
	 * Visit a parse tree produced by {@link KingRulesParser#atoms}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtoms(KingRulesParser.AtomsContext ctx);
	/**
	 * Visit a parse tree produced by {@link KingRulesParser#atomRule}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtomRule(KingRulesParser.AtomRuleContext ctx);
	/**
	 * Visit a parse tree produced by {@link KingRulesParser#terminal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTerminal(KingRulesParser.TerminalContext ctx);
	/**
	 * Visit a parse tree produced by {@link KingRulesParser#syntax}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSyntax(KingRulesParser.SyntaxContext ctx);
	/**
	 * Visit a parse tree produced by {@link KingRulesParser#skipRule}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSkipRule(KingRulesParser.SkipRuleContext ctx);
	/**
	 * Visit a parse tree produced by {@link KingRulesParser#arg}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArg(KingRulesParser.ArgContext ctx);
	/**
	 * Visit a parse tree produced by {@link KingRulesParser#takenArgs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTakenArgs(KingRulesParser.TakenArgsContext ctx);
	/**
	 * Visit a parse tree produced by {@link KingRulesParser#takenArgsList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTakenArgsList(KingRulesParser.TakenArgsListContext ctx);
	/**
	 * Visit a parse tree produced by {@link KingRulesParser#args}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgs(KingRulesParser.ArgsContext ctx);
	/**
	 * Visit a parse tree produced by {@link KingRulesParser#argsList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgsList(KingRulesParser.ArgsListContext ctx);
}