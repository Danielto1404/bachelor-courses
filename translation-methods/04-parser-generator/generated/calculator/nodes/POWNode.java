
package calculator.nodes;

import calculator.Rule;
            
public class POWNode extends Node {
    public final String nodeName = "POW";
    public final Rule rule = Rule.POW;
    
    public POWNode() {}

    public POWNode(String text) {
        this.text = text;
    }
}
