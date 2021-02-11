
package calculator.nodes;

import calculator.Rule;
            
public class TokenExprNode extends Node {
    public final String nodeName = "tokenExpr";
    public final Rule rule = Rule.tokenExpr;
    public int val;
    public TokenExprNode() {}

    public TokenExprNode(String text) {
        this.text = text;
    }
}
