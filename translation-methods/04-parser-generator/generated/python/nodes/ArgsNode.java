
package python.nodes;

import python.Rule;
            
public class ArgsNode extends Node {
    public final String nodeName = "args";
    public final Rule rule = Rule.args;
    public String str;
    public int n;
    public ArgsNode() {}

    public ArgsNode(String text) {
        this.text = text;
    }
}
