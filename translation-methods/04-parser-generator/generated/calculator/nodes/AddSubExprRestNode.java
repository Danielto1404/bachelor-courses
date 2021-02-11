
package calculator.nodes;

import calculator.Rule;
            
public class AddSubExprRestNode extends Node {
    public final String nodeName = "addSubExprRest";
    public final Rule rule = Rule.addSubExprRest;
    public int val;
    public AddSubExprRestNode() {}

    public AddSubExprRestNode(String text) {
        this.text = text;
    }
}
