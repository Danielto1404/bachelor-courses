
package calculator.nodes;

import calculator.Rule;
            
public class XORNode extends Node {
    public final String nodeName = "XOR";
    public final Rule rule = Rule.XOR;
    
    public XORNode() {}

    public XORNode(String text) {
        this.text = text;
    }
}
