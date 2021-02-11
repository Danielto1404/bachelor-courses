
package calculator.nodes;

import calculator.Rule;
            
public class PLUSNode extends Node {
    public final String nodeName = "PLUS";
    public final Rule rule = Rule.PLUS;
    
    public PLUSNode() {}

    public PLUSNode(String text) {
        this.text = text;
    }
}
