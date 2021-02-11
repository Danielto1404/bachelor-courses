
package calculator.nodes;

import calculator.Rule;
            
public class NOTNode extends Node {
    public final String nodeName = "NOT";
    public final Rule rule = Rule.NOT;
    
    public NOTNode() {}

    public NOTNode(String text) {
        this.text = text;
    }
}
