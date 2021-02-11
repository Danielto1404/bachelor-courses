
package calculator.nodes;

import calculator.Rule;
            
public class TANNode extends Node {
    public final String nodeName = "TAN";
    public final Rule rule = Rule.TAN;
    
    public TANNode() {}

    public TANNode(String text) {
        this.text = text;
    }
}
