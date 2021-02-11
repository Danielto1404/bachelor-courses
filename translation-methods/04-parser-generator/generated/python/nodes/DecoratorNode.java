
package python.nodes;

import python.Rule;
            
public class DecoratorNode extends Node {
    public final String nodeName = "decorator";
    public final Rule rule = Rule.decorator;
    public String str;
    public DecoratorNode() {}

    public DecoratorNode(String text) {
        this.text = text;
    }
}
