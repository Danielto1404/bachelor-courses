
package python.nodes;

import python.Rule;
            
public class ATNode extends Node {
    public final String nodeName = "AT";
    public final Rule rule = Rule.AT;
    
    public ATNode() {}

    public ATNode(String text) {
        this.text = text;
    }
}
