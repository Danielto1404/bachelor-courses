package tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Node {
    public String name;
    public ArrayList<Node> children;

    /**
     * Constructor that creates node with given name, terminal boolean value and children.
     *
     * @param name     Name of node.
     * @param children array of parsed children.
     */
    public Node(final String name, final Node... children) {
        this.name = name;
        this.children = new ArrayList<>(Arrays.asList(children));
    }

    /**
     * Naive string representation.
     *
     * @return {@link String} that represent this node.
     * For more complex visualization use {@link DotVisualizer} which uses Dot language for graphs and trees.
     */
    @Override
    public String toString() {
        String children = this
                .children
                .stream()
                .map(Node::toString)
                .collect(Collectors.joining("\n"));
        return "Name:= " + name + '\n' + children;
    }
}

