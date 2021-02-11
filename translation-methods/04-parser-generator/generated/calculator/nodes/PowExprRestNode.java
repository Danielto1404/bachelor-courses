
package calculator.nodes;

import calculator.Rule;
            
public class PowExprRestNode extends Node {
    public final String nodeName = "powExprRest";
    public final Rule rule = Rule.powExprRest;
    public int val;
    public PowExprRestNode() {}

    public PowExprRestNode(String text) {
        this.text = text;
    }
}
