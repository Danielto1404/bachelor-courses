
package calculator.nodes;

import calculator.Rule;
            
public class AndExprNode extends Node {
    public final String nodeName = "andExpr";
    public final Rule rule = Rule.andExpr;
    public int val;
    public AndExprNode() {}

    public AndExprNode(String text) {
        this.text = text;
    }
}
