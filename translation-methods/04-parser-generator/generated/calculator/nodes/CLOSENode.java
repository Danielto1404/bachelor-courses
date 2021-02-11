
package calculator.nodes;

import calculator.Rule;
            
public class CLOSENode extends Node {
    public final String nodeName = "CLOSE";
    public final Rule rule = Rule.CLOSE;
    
    public CLOSENode() {}

    public CLOSENode(String text) {
        this.text = text;
    }
}
