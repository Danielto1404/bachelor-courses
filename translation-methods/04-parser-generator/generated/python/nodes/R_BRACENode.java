
package python.nodes;

import python.Rule;
            
public class R_BRACENode extends Node {
    public final String nodeName = "R_BRACE";
    public final Rule rule = Rule.R_BRACE;
    
    public R_BRACENode() {}

    public R_BRACENode(String text) {
        this.text = text;
    }
}
