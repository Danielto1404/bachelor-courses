
package calculator.nodes;

import calculator.Rule;
            
public class MulDivExprNode extends Node {
    public final String nodeName = "mulDivExpr";
    public final Rule rule = Rule.mulDivExpr;
    public int val;
    public MulDivExprNode() {}

    public MulDivExprNode(String text) {
        this.text = text;
    }
}
