package configurator.rules.atoms

import configurator.rules.Argument
import configurator.rules.Rule

class TerminalRule(name: String,
                   code: String?,
                   regex: String,
                   inheritedAttrs: List<Argument>?,
                   returnedAttrs: List<Argument>?) :
        Rule(name = name, code = code, regex = regex, inheritedAttrs = inheritedAttrs, returnedAttrs = returnedAttrs)