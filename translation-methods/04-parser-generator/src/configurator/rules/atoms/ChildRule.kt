package configurator.rules.atoms

import configurator.empty

abstract class ChildRule(val name: String, val args: String? = null) {
    abstract val isTerminal: Boolean

    override fun toString(): String {
        return "$name ${args?.let { "(${it})" } ?: empty}"
    }
}

class ChildTerminalRule(name: String, args: String? = null) : ChildRule(name, args) {
    override val isTerminal: Boolean
        get() = true
}

class ChildSyntaxRule(name: String, args: String? = null) : ChildRule(name, args) {
    override val isTerminal: Boolean
        get() = false
}