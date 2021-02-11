// Generated from /Users/daniilkorolev/Documents/GitHub/3-rd-year/translation-methods/04-parser-generator/src/grammar/KingRules.g4 by ANTLR 4.9
package grammar.antlr;

import configurator.rules.*;
import configurator.rules.atoms.*;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link KingRulesParser}.
 */
public interface KingRulesListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link KingRulesParser#mainGrammar}.
	 * @param ctx the parse tree
	 */
	void enterMainGrammar(KingRulesParser.MainGrammarContext ctx);
	/**
	 * Exit a parse tree produced by {@link KingRulesParser#mainGrammar}.
	 * @param ctx the parse tree
	 */
	void exitMainGrammar(KingRulesParser.MainGrammarContext ctx);
	/**
	 * Enter a parse tree produced by {@link KingRulesParser#grammarRules}.
	 * @param ctx the parse tree
	 */
	void enterGrammarRules(KingRulesParser.GrammarRulesContext ctx);
	/**
	 * Exit a parse tree produced by {@link KingRulesParser#grammarRules}.
	 * @param ctx the parse tree
	 */
	void exitGrammarRules(KingRulesParser.GrammarRulesContext ctx);
	/**
	 * Enter a parse tree produced by {@link KingRulesParser#grammarName}.
	 * @param ctx the parse tree
	 */
	void enterGrammarName(KingRulesParser.GrammarNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link KingRulesParser#grammarName}.
	 * @param ctx the parse tree
	 */
	void exitGrammarName(KingRulesParser.GrammarNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link KingRulesParser#packageName}.
	 * @param ctx the parse tree
	 */
	void enterPackageName(KingRulesParser.PackageNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link KingRulesParser#packageName}.
	 * @param ctx the parse tree
	 */
	void exitPackageName(KingRulesParser.PackageNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link KingRulesParser#rules}.
	 * @param ctx the parse tree
	 */
	void enterRules(KingRulesParser.RulesContext ctx);
	/**
	 * Exit a parse tree produced by {@link KingRulesParser#rules}.
	 * @param ctx the parse tree
	 */
	void exitRules(KingRulesParser.RulesContext ctx);
	/**
	 * Enter a parse tree produced by {@link KingRulesParser#singleRule}.
	 * @param ctx the parse tree
	 */
	void enterSingleRule(KingRulesParser.SingleRuleContext ctx);
	/**
	 * Exit a parse tree produced by {@link KingRulesParser#singleRule}.
	 * @param ctx the parse tree
	 */
	void exitSingleRule(KingRulesParser.SingleRuleContext ctx);
	/**
	 * Enter a parse tree produced by {@link KingRulesParser#terminalRule}.
	 * @param ctx the parse tree
	 */
	void enterTerminalRule(KingRulesParser.TerminalRuleContext ctx);
	/**
	 * Exit a parse tree produced by {@link KingRulesParser#terminalRule}.
	 * @param ctx the parse tree
	 */
	void exitTerminalRule(KingRulesParser.TerminalRuleContext ctx);
	/**
	 * Enter a parse tree produced by {@link KingRulesParser#syntaxRule}.
	 * @param ctx the parse tree
	 */
	void enterSyntaxRule(KingRulesParser.SyntaxRuleContext ctx);
	/**
	 * Exit a parse tree produced by {@link KingRulesParser#syntaxRule}.
	 * @param ctx the parse tree
	 */
	void exitSyntaxRule(KingRulesParser.SyntaxRuleContext ctx);
	/**
	 * Enter a parse tree produced by {@link KingRulesParser#allRules}.
	 * @param ctx the parse tree
	 */
	void enterAllRules(KingRulesParser.AllRulesContext ctx);
	/**
	 * Exit a parse tree produced by {@link KingRulesParser#allRules}.
	 * @param ctx the parse tree
	 */
	void exitAllRules(KingRulesParser.AllRulesContext ctx);
	/**
	 * Enter a parse tree produced by {@link KingRulesParser#ruleLine}.
	 * @param ctx the parse tree
	 */
	void enterRuleLine(KingRulesParser.RuleLineContext ctx);
	/**
	 * Exit a parse tree produced by {@link KingRulesParser#ruleLine}.
	 * @param ctx the parse tree
	 */
	void exitRuleLine(KingRulesParser.RuleLineContext ctx);
	/**
	 * Enter a parse tree produced by {@link KingRulesParser#atoms}.
	 * @param ctx the parse tree
	 */
	void enterAtoms(KingRulesParser.AtomsContext ctx);
	/**
	 * Exit a parse tree produced by {@link KingRulesParser#atoms}.
	 * @param ctx the parse tree
	 */
	void exitAtoms(KingRulesParser.AtomsContext ctx);
	/**
	 * Enter a parse tree produced by {@link KingRulesParser#atomRule}.
	 * @param ctx the parse tree
	 */
	void enterAtomRule(KingRulesParser.AtomRuleContext ctx);
	/**
	 * Exit a parse tree produced by {@link KingRulesParser#atomRule}.
	 * @param ctx the parse tree
	 */
	void exitAtomRule(KingRulesParser.AtomRuleContext ctx);
	/**
	 * Enter a parse tree produced by {@link KingRulesParser#terminal}.
	 * @param ctx the parse tree
	 */
	void enterTerminal(KingRulesParser.TerminalContext ctx);
	/**
	 * Exit a parse tree produced by {@link KingRulesParser#terminal}.
	 * @param ctx the parse tree
	 */
	void exitTerminal(KingRulesParser.TerminalContext ctx);
	/**
	 * Enter a parse tree produced by {@link KingRulesParser#syntax}.
	 * @param ctx the parse tree
	 */
	void enterSyntax(KingRulesParser.SyntaxContext ctx);
	/**
	 * Exit a parse tree produced by {@link KingRulesParser#syntax}.
	 * @param ctx the parse tree
	 */
	void exitSyntax(KingRulesParser.SyntaxContext ctx);
	/**
	 * Enter a parse tree produced by {@link KingRulesParser#skipRule}.
	 * @param ctx the parse tree
	 */
	void enterSkipRule(KingRulesParser.SkipRuleContext ctx);
	/**
	 * Exit a parse tree produced by {@link KingRulesParser#skipRule}.
	 * @param ctx the parse tree
	 */
	void exitSkipRule(KingRulesParser.SkipRuleContext ctx);
	/**
	 * Enter a parse tree produced by {@link KingRulesParser#arg}.
	 * @param ctx the parse tree
	 */
	void enterArg(KingRulesParser.ArgContext ctx);
	/**
	 * Exit a parse tree produced by {@link KingRulesParser#arg}.
	 * @param ctx the parse tree
	 */
	void exitArg(KingRulesParser.ArgContext ctx);
	/**
	 * Enter a parse tree produced by {@link KingRulesParser#takenArgs}.
	 * @param ctx the parse tree
	 */
	void enterTakenArgs(KingRulesParser.TakenArgsContext ctx);
	/**
	 * Exit a parse tree produced by {@link KingRulesParser#takenArgs}.
	 * @param ctx the parse tree
	 */
	void exitTakenArgs(KingRulesParser.TakenArgsContext ctx);
	/**
	 * Enter a parse tree produced by {@link KingRulesParser#takenArgsList}.
	 * @param ctx the parse tree
	 */
	void enterTakenArgsList(KingRulesParser.TakenArgsListContext ctx);
	/**
	 * Exit a parse tree produced by {@link KingRulesParser#takenArgsList}.
	 * @param ctx the parse tree
	 */
	void exitTakenArgsList(KingRulesParser.TakenArgsListContext ctx);
	/**
	 * Enter a parse tree produced by {@link KingRulesParser#args}.
	 * @param ctx the parse tree
	 */
	void enterArgs(KingRulesParser.ArgsContext ctx);
	/**
	 * Exit a parse tree produced by {@link KingRulesParser#args}.
	 * @param ctx the parse tree
	 */
	void exitArgs(KingRulesParser.ArgsContext ctx);
	/**
	 * Enter a parse tree produced by {@link KingRulesParser#argsList}.
	 * @param ctx the parse tree
	 */
	void enterArgsList(KingRulesParser.ArgsListContext ctx);
	/**
	 * Exit a parse tree produced by {@link KingRulesParser#argsList}.
	 * @param ctx the parse tree
	 */
	void exitArgsList(KingRulesParser.ArgsListContext ctx);
}