
package calculator.nodes;

import calculator.Rule;
            
public class MulDivExprRestNode extends Node {
    public final String nodeName = "mulDivExprRest";
    public final Rule rule = Rule.mulDivExprRest;
    public int val;
    public MulDivExprRestNode() {}

    public MulDivExprRestNode(String text) {
        this.text = text;
    }
}
