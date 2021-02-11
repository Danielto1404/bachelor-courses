package configurator

import grammar.antlr.KingRulesLexer
import grammar.antlr.KingRulesParser
import configurator.rules.*
import configurator.rules.atoms.*
import configurator.rules.atoms.SyntaxRule
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import java.io.File
import java.nio.charset.StandardCharsets


private fun getRulesParser(grammarFilePath: String): KingRulesParser {
    val inputGrammar = File(grammarFilePath).readText(StandardCharsets.UTF_8)
    val charStream = CharStreams.fromString(inputGrammar)
    val lexer = KingRulesLexer(charStream)
    val tokenStream = CommonTokenStream(lexer)
    return KingRulesParser(tokenStream)
}

fun configureGrammar(grammarFilePath: String): Grammar? {
    val parser = getRulesParser(grammarFilePath)
    val mainContext = parser.mainGrammar().grammarRules() ?: return null
    val grammarName = mainContext.grammarName().name
    val packageName = mainContext.packageName().name
    val rules = mainContext.rules().singleRule().map { parseRule(it) }
    val grammar = Grammar(grammarName, packageName, rules)
    validateGrammar(grammar)
    return grammar
}


fun validateGrammar(grammar: Grammar) {
    val names = grammar.rules
            .filterIsInstance<TerminalRule>()
            .map { it.name }
            .toMutableSet()

    var hasStart = false
    val usedNames = emptySet<String>().toMutableSet()
    grammar.rules
            .filterIsInstance<SyntaxRule>()
            .forEach {
                names.add(it.name)
                hasStart = it.name == start || hasStart
                it.child?.forEach { line ->
                    line.rules?.forEach { childRule ->
                        usedNames.add(childRule.name)
                    }
                }
            }

    if (!hasStart) {
        throw GrammarException("Please provide \"start\" non-terminal rule.")
    }

    usedNames.forEach {
        if (!names.contains(it) && it != epsilon) {
            throw GrammarException("Rule $it not presented in grammar.")
        }
    }
}

fun parseRule(rule: KingRulesParser.SingleRuleContext): configurator.rules.Rule {

    if (rule.terminalRule() != null) return rule.terminalRule().rule

    if (rule.skipRule() != null) return rule.skipRule().rule

    val syntaxRule = rule.syntaxRule() ?: throw GrammarException("Invalid grammar" + rule.exception)

    return SyntaxRule(
            name = syntaxRule.name,
            inheritedAttrs = syntaxRule.inheritedAttrs?.arguments,
            returnedAttrs = syntaxRule.returnAttrs?.arguments,
            child = ruleLines(syntaxRule.allRules())
                    .map {
                        SyntaxRuleLine(rules = sequenceRule(it.atoms), code = it.code)
                    }
    )
}

private fun ruleLines(context: KingRulesParser.AllRulesContext): MutableList<SyntaxRuleLine> {
    val ruleLineContext = context.ruleLine()
    val rule = mutableListOf(SyntaxRuleLine(atoms = ruleLineContext.atoms(), code = ruleLineContext.code?.text))
    val restRules = context.allRules() ?: return rule
    return (ruleLines(restRules) + rule).toMutableList()
}

private fun sequenceRule(atomsContext: KingRulesParser.AtomsContext?): List<ChildRule> {
    val atoms = atomsContext?.atomRule() ?: return emptyList()

    return atoms
            .map { atom -> (atom.syntax() to atom.terminal()) }
            .mapNotNull { (syntax, terminal) ->
                when (syntax to terminal) {
                    null to terminal -> ChildTerminalRule(terminal.TERMINAL_NAME().text, terminal.takenArgsList()?.toString)
                    syntax to null -> ChildSyntaxRule(syntax.IDENTIFIER().text, syntax.takenArgsList()?.toString)
                    else -> null
                }
            }
}