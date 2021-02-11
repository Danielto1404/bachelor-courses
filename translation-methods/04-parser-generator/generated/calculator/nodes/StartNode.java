
package calculator.nodes;

import calculator.Rule;
            
public class StartNode extends Node {
    public final String nodeName = "start";
    public final Rule rule = Rule.start;
    public int val;
    public StartNode() {}

    public StartNode(String text) {
        this.text = text;
    }
}
