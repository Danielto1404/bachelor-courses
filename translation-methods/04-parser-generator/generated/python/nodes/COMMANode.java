
package python.nodes;

import python.Rule;
            
public class COMMANode extends Node {
    public final String nodeName = "COMMA";
    public final Rule rule = Rule.COMMA;
    
    public COMMANode() {}

    public COMMANode(String text) {
        this.text = text;
    }
}
