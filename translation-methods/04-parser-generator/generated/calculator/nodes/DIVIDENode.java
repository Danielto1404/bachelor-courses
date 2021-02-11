
package calculator.nodes;

import calculator.Rule;
            
public class DIVIDENode extends Node {
    public final String nodeName = "DIVIDE";
    public final Rule rule = Rule.DIVIDE;
    
    public DIVIDENode() {}

    public DIVIDENode(String text) {
        this.text = text;
    }
}
