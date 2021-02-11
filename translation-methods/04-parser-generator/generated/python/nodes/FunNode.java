
package python.nodes;

import python.Rule;
            
public class FunNode extends Node {
    public final String nodeName = "fun";
    public final Rule rule = Rule.fun;
    public String str;
    public int n;
    public FunNode() {}

    public FunNode(String text) {
        this.text = text;
    }
}
