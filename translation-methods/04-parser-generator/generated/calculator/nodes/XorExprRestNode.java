
package calculator.nodes;

import calculator.Rule;
            
public class XorExprRestNode extends Node {
    public final String nodeName = "xorExprRest";
    public final Rule rule = Rule.xorExprRest;
    public int val;
    public XorExprRestNode() {}

    public XorExprRestNode(String text) {
        this.text = text;
    }
}
