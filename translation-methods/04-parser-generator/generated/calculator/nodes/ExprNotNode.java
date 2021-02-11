
package calculator.nodes;

import calculator.Rule;
            
public class ExprNotNode extends Node {
    public final String nodeName = "exprNot";
    public final Rule rule = Rule.exprNot;
    public int val;
    public ExprNotNode() {}

    public ExprNotNode(String text) {
        this.text = text;
    }
}
