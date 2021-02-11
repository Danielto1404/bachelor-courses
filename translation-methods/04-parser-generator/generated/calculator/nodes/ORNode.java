
package calculator.nodes;

import calculator.Rule;
            
public class ORNode extends Node {
    public final String nodeName = "OR";
    public final Rule rule = Rule.OR;
    
    public ORNode() {}

    public ORNode(String text) {
        this.text = text;
    }
}
