
package python.nodes;

import python.Rule;
            
public class L_BRACENode extends Node {
    public final String nodeName = "L_BRACE";
    public final Rule rule = Rule.L_BRACE;
    
    public L_BRACENode() {}

    public L_BRACENode(String text) {
        this.text = text;
    }
}
