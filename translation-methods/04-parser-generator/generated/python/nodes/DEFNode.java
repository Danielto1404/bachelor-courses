
package python.nodes;

import python.Rule;
            
public class DEFNode extends Node {
    public final String nodeName = "DEF";
    public final Rule rule = Rule.DEF;
    
    public DEFNode() {}

    public DEFNode(String text) {
        this.text = text;
    }
}
