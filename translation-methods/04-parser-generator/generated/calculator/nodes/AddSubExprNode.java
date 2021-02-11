
package calculator.nodes;

import calculator.Rule;
            
public class AddSubExprNode extends Node {
    public final String nodeName = "addSubExpr";
    public final Rule rule = Rule.addSubExpr;
    public int val;
    public AddSubExprNode() {}

    public AddSubExprNode(String text) {
        this.text = text;
    }
}
