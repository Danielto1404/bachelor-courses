package configurator.rules.atoms

import configurator.rules.Argument
import configurator.rules.Rule
import configurator.rules.SyntaxRuleLine
import java.util.ArrayList

class SyntaxRule(name: String,
                 inheritedAttrs: ArrayList<Argument>?,
                 returnedAttrs: List<Argument>?,
                 child: List<SyntaxRuleLine>?)
    : Rule(name = name, inheritedAttrs = inheritedAttrs, returnedAttrs = returnedAttrs, child = child)