
package calculator.nodes;

import calculator.Rule;
            
public class OrExprNode extends Node {
    public final String nodeName = "orExpr";
    public final Rule rule = Rule.orExpr;
    public int val;
    public OrExprNode() {}

    public OrExprNode(String text) {
        this.text = text;
    }
}
