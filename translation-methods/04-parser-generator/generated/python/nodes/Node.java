
package python.nodes;

import python.Rule;
import java.util.ArrayList;

public class Node {
    public final String nodeName = "ROOT_NODE";
    public final Rule rule = Rule.START;
    public final ArrayList<Node> children = new ArrayList<>();
    public String text = "";

    public void addChild(final Node node) {
        children.add(node);
        text = text + node.text;
    }
}
