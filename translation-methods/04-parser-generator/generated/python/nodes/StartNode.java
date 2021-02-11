
package python.nodes;

import python.Rule;
            
public class StartNode extends Node {
    public final String nodeName = "start";
    public final Rule rule = Rule.start;
    public String str;
    public int n;
    public StartNode() {}

    public StartNode(String text) {
        this.text = text;
    }
}
