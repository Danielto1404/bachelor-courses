
package calculator.nodes;

import calculator.Rule;
            
public class AndExprRestNode extends Node {
    public final String nodeName = "andExprRest";
    public final Rule rule = Rule.andExprRest;
    public int val;
    public AndExprRestNode() {}

    public AndExprRestNode(String text) {
        this.text = text;
    }
}
