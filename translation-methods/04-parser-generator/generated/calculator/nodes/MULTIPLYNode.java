
package calculator.nodes;

import calculator.Rule;
            
public class MULTIPLYNode extends Node {
    public final String nodeName = "MULTIPLY";
    public final Rule rule = Rule.MULTIPLY;
    
    public MULTIPLYNode() {}

    public MULTIPLYNode(String text) {
        this.text = text;
    }
}
