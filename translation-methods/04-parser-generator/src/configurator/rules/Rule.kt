package configurator.rules

import configurator.empty
import grammar.antlr.KingRulesParser
import configurator.rules.atoms.ChildRule
import configurator.ws

open class Rule(val name: String = empty,
                val code: String? = empty,
                val regex: String = empty,
                val inheritedAttrs: List<Argument>? = null,
                val returnedAttrs: List<Argument>? = null,
                val child: List<SyntaxRuleLine>? = null)

class Argument(private val type: String, private val name: String) {
    override fun toString(): String {
        return "$type $name"
    }
}

class SyntaxRuleLine(val code: String? = null,
                     val rules: List<ChildRule>? = null,
                     val atoms: KingRulesParser.AtomsContext? = null) {
    override fun toString(): String {
        val rules = rules ?: return empty
        return rules.joinToString(empty) { it.toString() }
    }
}
