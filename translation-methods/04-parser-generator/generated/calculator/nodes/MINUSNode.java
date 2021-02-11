
package calculator.nodes;

import calculator.Rule;
            
public class MINUSNode extends Node {
    public final String nodeName = "MINUS";
    public final Rule rule = Rule.MINUS;
    
    public MINUSNode() {}

    public MINUSNode(String text) {
        this.text = text;
    }
}
