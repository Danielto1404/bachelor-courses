
package python.nodes;

import python.Rule;
            
public class RestArgsNode extends Node {
    public final String nodeName = "restArgs";
    public final Rule rule = Rule.restArgs;
    public String str;
    public int n;
    public RestArgsNode() {}

    public RestArgsNode(String text) {
        this.text = text;
    }
}
