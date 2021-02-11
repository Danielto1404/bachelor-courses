package configurator

open class GrammarException(message: String) : Exception(message)

class EmptyGrammarException(name: String) : GrammarException("$name grammar is empty.")

class LL1GrammarException(badRules: MutableSet<String>) : GrammarException(
        """
         
            
Grammar is not LL1.

Bad rules: 
${badRules.joinToString(nl.repeat(2))}
""".trimIndent())