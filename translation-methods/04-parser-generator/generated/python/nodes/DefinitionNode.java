
package python.nodes;

import python.Rule;
            
public class DefinitionNode extends Node {
    public final String nodeName = "definition";
    public final Rule rule = Rule.definition;
    public String str;
    public int n;
    public DefinitionNode() {}

    public DefinitionNode(String text) {
        this.text = text;
    }
}
