
package calculator.nodes;

import calculator.Rule;
            
public class OrExprRestNode extends Node {
    public final String nodeName = "orExprRest";
    public final Rule rule = Rule.orExprRest;
    public int val;
    public OrExprRestNode() {}

    public OrExprRestNode(String text) {
        this.text = text;
    }
}
