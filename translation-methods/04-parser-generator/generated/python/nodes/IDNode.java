
package python.nodes;

import python.Rule;
            
public class IDNode extends Node {
    public final String nodeName = "ID";
    public final Rule rule = Rule.ID;
    
    public IDNode() {}

    public IDNode(String text) {
        this.text = text;
    }
}
