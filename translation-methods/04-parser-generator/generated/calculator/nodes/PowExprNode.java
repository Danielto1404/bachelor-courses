
package calculator.nodes;

import calculator.Rule;
            
public class PowExprNode extends Node {
    public final String nodeName = "powExpr";
    public final Rule rule = Rule.powExpr;
    public int val;
    public PowExprNode() {}

    public PowExprNode(String text) {
        this.text = text;
    }
}
