package generators

import configurator.*
import configurator.rules.Argument
import configurator.rules.Grammar
import configurator.rules.Rule
import configurator.rules.SyntaxRuleLine
import configurator.rules.atoms.ChildRule
import configurator.rules.atoms.SyntaxRule
import configurator.rules.atoms.TerminalRule
import java.io.File

class ParserGenerator(val grammar: Grammar) {

    private val prefixPath = filePrefix + "/" + grammar.header.toLowerCase()

    private val first = mutableMapOf<String, MutableList<Set<String>>>()
    private val follow = mutableMapOf<String, Set<String>>()

    fun generate() {
        val rules = grammar.rules.filter { it is SyntaxRule || it is TerminalRule }
        configureFirst(rules)
        configureFollow(rules.filterIsInstance<SyntaxRule>())

        val badRules = findLL1BadRules()
        if (badRules.isNotEmpty()) throw LL1GrammarException(badRules)

        generateNodes(rules)
        generateParser(rules)
    }

    private fun findLL1BadRules(): MutableSet<String> {
        val badRules = mutableSetOf<String>()
        grammar.rules
                .filterIsInstance<SyntaxRule>()
                .forEach {
                    val firsts = first[it.name] ?: emptyList()
                    val follow = follow[it.name] ?: emptySet()
                    val child = it.child ?: emptyList()

                    for (i in 0 until firsts.size - 1) {
                        for (j in i + 1 until firsts.size) {
                            val alpha = firsts[i]
                            val beta = firsts[j]
                            val isRuleCorrect = if (alpha.containsEpsilon())
                                follow.intersect(beta).isEmpty()
                            else alpha.intersect(beta).isEmpty()

                            if (!isRuleCorrect) {
                                val conflictMessage =
                                        "${tab}\"${it.name}\" rule conflict:\n" +
                                                "$tab(${child[i]} [line: ${i + 1}]) conflicts with (${child[j]} [line: ${j + 1}])"
                                badRules.add(conflictMessage)
                            }
                        }
                    }
                }
        return badRules
    }

    private fun firstSetRightPart(sequenceRules: SyntaxRuleLine): Set<String> {

        // Can be implemented by recursive invocations,
        // because this is LL(1) Parse Generator, and LL(1) grammar can not be left-recursive.

        val firstSet = emptyMutableSet()
        var ruleHaveEpsilon = true
        sequenceRules.rules?.forEach { rule ->
            if (rule.isTerminal) {
                return setOf(rule.name)
            } else {
                val currentRuleSet = emptyMutableSet()
                grammar.rules
                        .first { it.name == rule.name }
                        .child?.forEach {
                            currentRuleSet.addAll(firstSetRightPart(it))
                        }
                firstSet.addAll(currentRuleSet)
                ruleHaveEpsilon = currentRuleSet.containsEpsilon()
                if (!ruleHaveEpsilon) {
                    return firstSet.minus(epsilon)
                }
            }
        }
        return when (ruleHaveEpsilon) {
            true -> firstSet
            else -> firstSet.minus(epsilon)
        }
    }

    private fun configureFirst(rules: List<Rule>) {
        rules.filterIsInstance<TerminalRule>().forEach {
            first[it.name] = listOf(singleSet(it.name)).toMutableList()
        }

        rules.filterIsInstance<SyntaxRule>().forEach { gamma ->
            val gammaRules = emptyList<Set<String>>().toMutableList()
            gamma.child?.forEach {
                gammaRules.add(firstSetRightPart(it))
            }
            first[gamma.name] = gammaRules
        }
    }

    private fun configureFollow(syntaxRules: List<Rule>) {

        syntaxRules.forEach { follow[it.name] = emptySet() }
        follow[start] = singleSet(eof)  // King grammar must contain rule named "start"

        var changes = true
        while (changes) {
            changes = false
            syntaxRules.forEach { gamma ->
                gamma.child?.forEach { sequenceRuleLine ->
                    val rules = sequenceRuleLine.rules ?: emptyList()
                    for ((i, rule) in rules.indices.zip(rules)) {
                        if (rule.isTerminal) continue
                        val before = follow[rule.name]
                        val firstNext = if (i == rules.lastIndex) {
                            singleSet(epsilon)
                        } else {
                            firstSetRightPart(
                                    SyntaxRuleLine(rules = rules.slice(i + 1 until rules.size))
                            )
                        }

                        follow[rule.name] = follow[rule.name]!!.union(firstNext.minus(epsilon))

                        if (firstNext.containsEpsilon()) {
                            follow[rule.name] = follow[rule.name]!!.union(follow[gamma.name]!!)
                        }

                        changes = before != follow[rule.name] || changes
                    }
                }
            }
        }
    }

    private fun nodeName(name: String): String {
        return name.capitalize() + "Node"
    }

    private fun generateArguments(args: List<Argument>?): String {
        val actualArgs = args ?: return "()"
        return "(" + actualArgs.joinToString(", ") { it.toString() } + ")"
    }

    private fun generateTerminalFunction(rule: Rule): String {
        return """
    public ${nodeName(rule.name)} ${rule.name}Tree${generateArguments(rule.inheritedAttrs)} {
        ${nodeName(rule.name)} node = new ${nodeName(rule.name)}(lexer.token.text);
        skipToken(Rule.${rule.name});
        ${(rule.code ?: empty).replace(fieldAccessPrefix, nodeDot)}
        return node;
    }
"""
    }

    private fun generateSyntaxFunction(rule: Rule): String {
        return """
    public ${nodeName(rule.name)} ${rule.name}Tree${generateArguments(rule.inheritedAttrs)} {
        ${nodeName(rule.name)} node = new ${nodeName(rule.name)}();
        switch (lexer.token.rule) {
            ${
            (rule.child?.mapIndexed { index, line ->
                generateCases(rule.name, index, line)
            }?.joinToString(ws) ?: empty)
        }   
            default -> throw new ParseException("Unresolved token:" + lexer.token.rule +
                    " at position: " + (lexer.getPosition() - lexer.token.text.length()));
        }
    }
"""
    }

    private fun expandTree(rule: ChildRule): String {
        return """
                ${nodeName(rule.name)} ${rule.name} = ${rule.name}Tree${rule.args ?: "()"}; 
                node.addChild(${rule.name});
"""
    }

    private fun generateCases(gamma: String, index: Int, sequenceRuleLine: SyntaxRuleLine): String {
        val first = first[gamma]?.get(index) ?: emptySet()
        val follow = follow[gamma] ?: emptySet()
        val rules = sequenceRuleLine.rules ?: emptySet()

        val (tokens, isEps) = if (first.minus(epsilon).isEmpty()) {
            follow to true
        } else {
            first.minus(epsilon) to false
        }

        val cases = tokens.joinToString(", ")
        val code = (sequenceRuleLine.code ?: empty).replace(fieldAccessPrefix, nodeDot)

        return """
            case $cases -> {${
            if (isEps) {
                """
                $code
                return node;
            }
                """
            } else {
                """
                ${rules.joinToString(empty) { expandTree(it) }}
                $code
                return node;
            }
                """
            }
        }
"""
    }

    private fun generateParser(rules: List<Rule>) {
        File("${prefixPath}/${grammar.name}Parser.java")
                .writeText(
                        """
package ${grammar.header};

import ${grammar.header}.nodes.*;

public class ${grammar.name}Parser {

    private ${grammar.name}Lexer lexer;
    
    public ${grammar.name}Parser() { }

    public ${grammar.name}Parser(${grammar.name}Lexer lexer) {
        this.lexer = lexer;
        lexer.nextToken();
    }
    
    public static class ParseException extends RuntimeException {
        public ParseException(String message) {
            super(message);
        }
    }

    private void skipToken(final Rule expected) {
        Rule actual = lexer.token.rule;
        if (expected != actual) {
            throw new ParseException("Illegal token " + actual.name() + ", expected " + expected.name());
        }
        lexer.nextToken();
    }
    
    public StartNode parse(String input) {
        lexer = new ${grammar.name}Lexer(input);
        lexer.nextToken();
        return getParsedTree();
    }

    public StartNode getParsedTree() {
        StartNode tree = startTree();
        skipToken(Rule.EOF);
        return tree;
    }
    
${
                            rules.joinToString(nl) {
                                tab + if (it is TerminalRule)
                                    generateTerminalFunction(it)
                                else generateSyntaxFunction(it)
                            }
                        }
}
                    """.trimIndent())
    }

    // Node generation block
    private fun generateNode(rule: Rule): String {
        return """
package ${grammar.header}.nodes;

import ${grammar.header}.Rule;
            
public class ${nodeName(rule.name)} extends Node {
    public final String nodeName = "${rule.name}";
    public final Rule rule = Rule.${rule.name};
    ${rule.returnedAttrs?.joinToString(nl + tab) { "public $it;" } ?: empty}
    public ${nodeName(rule.name)}() {}

    public ${nodeName(rule.name)}(String text) {
        this.text = text;
    }
}
"""
    }

    private fun generateNodes(rules: List<Rule>) {
        val path = "${prefixPath}/nodes"
        if (!File(path).exists()) {
            File(path).mkdir()
        }

        rules.forEach {
            File("${path}/${nodeName(it.name)}.java")
                    .writeText(generateNode(it))
        }

        File("${path}/Node.java").writeText(
                """
package ${grammar.header}.nodes;

import ${grammar.header}.Rule;
import java.util.ArrayList;

public class Node {
    public final String nodeName = "ROOT_NODE";
    public final Rule rule = Rule.START;
    public final ArrayList<Node> children = new ArrayList<>();
    public String text = "";

    public void addChild(final Node node) {
        children.add(node);
        text = text + node.text;
    }
}
""")
    }
}

// Extensions
private fun Set<String>.containsEpsilon(): Boolean {
    return this.contains(epsilon)
}

private fun singleSet(forName: String): MutableSet<String> {
    return setOf(forName).toMutableSet()
}

private fun emptyMutableSet(): MutableSet<String> {
    return emptySet<String>().toMutableSet()
}