
package calculator.nodes;

import calculator.Rule;
            
public class NUMBERNode extends Node {
    public final String nodeName = "NUMBER";
    public final Rule rule = Rule.NUMBER;
    
    public NUMBERNode() {}

    public NUMBERNode(String text) {
        this.text = text;
    }
}
