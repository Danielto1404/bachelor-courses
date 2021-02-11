
package calculator.nodes;

import calculator.Rule;
            
public class OPENNode extends Node {
    public final String nodeName = "OPEN";
    public final Rule rule = Rule.OPEN;
    
    public OPENNode() {}

    public OPENNode(String text) {
        this.text = text;
    }
}
