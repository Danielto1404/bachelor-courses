
package calculator.nodes;

import calculator.Rule;
            
public class XorExprNode extends Node {
    public final String nodeName = "xorExpr";
    public final Rule rule = Rule.xorExpr;
    public int val;
    public XorExprNode() {}

    public XorExprNode(String text) {
        this.text = text;
    }
}
