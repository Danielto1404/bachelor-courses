
package calculator.nodes;

import calculator.Rule;
            
public class ANDNode extends Node {
    public final String nodeName = "AND";
    public final Rule rule = Rule.AND;
    
    public ANDNode() {}

    public ANDNode(String text) {
        this.text = text;
    }
}
