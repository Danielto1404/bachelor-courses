
package calculator.nodes;

import calculator.Rule;
            
public class UnaryMinusNode extends Node {
    public final String nodeName = "unaryMinus";
    public final Rule rule = Rule.unaryMinus;
    public int val;
    public UnaryMinusNode() {}

    public UnaryMinusNode(String text) {
        this.text = text;
    }
}
