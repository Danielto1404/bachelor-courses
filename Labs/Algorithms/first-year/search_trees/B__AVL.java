/*
Реализуйте сбалансированное двоичное дерево поиска:

insert x — добавить в дерево ключ x. Если ключ x есть в дереве, то ничего делать не надо
delete x — удалить из дерева ключ x. Если ключа x в дереве нет, то ничего делать не надо
exists x — если ключ x есть в дереве выведите «true», если нет «false»
next x — выведите минимальный элемент в дереве, строго больший x, или «none» если такого нет
prev x — выведите максимальный элемент в дереве, строго меньший x, или «none» если такого нет
*/

import java.util.Scanner;

public class B {

    private static Scanner scanner = new Scanner(System.in);
    private static Node root;
    private static String cmd;
    private static int value;

    private static class Node {
        private Node parent;
        private Node left;
        private Node right;
        private int val;
        private int bal;
        private int h;

        Node(int val, Node parent) {
            this.val = val;
            this.parent = parent;
        }
    }

    private static void insert(int key) {
        if (root == null) {
            root = new Node(key, null);
            return;
        }
        Node curNode = root;
        while (curNode.val != key) {
            Node parent = curNode;
            boolean isLeft = curNode.val > key;
            curNode = curNode.val > key ? curNode.left : curNode.right;
            if (curNode == null) {
                if (isLeft) {
                    parent.left = new Node(key, parent);
                } else {
                    parent.right = new Node(key, parent);
                }
                doBalance(parent);
                break;
            }
        }
    }

    private static void remove(Node v) {
        if (v.left == null && v.right == null) {
            if (v.parent == null) {
                root = null;
            } else {
                Node parent = v.parent;
                if (parent.left == v) {
                    parent.left = null;
                } else {
                    parent.right = null;
                }
                doBalance(parent);
            }
            return;
        }
        if (v.left != null) {
            Node child = v.left;
            while (child.right != null) {
                child = child.right;
            }
            v.val = child.val;
            remove(child);
        } else {
            Node curNode = v.right;
            while (curNode.left != null) {
                curNode = curNode.left;
            }
            v.val = curNode.val;
            remove(curNode);
        }
    }

    public static void remove(int key) {
        if (root == null)
            return;
        Node curNode = root;
        while (curNode != null) {
            Node v = curNode;
            curNode = key >= v.val ? v.right : v.left;
            if (key == v.val) {
                remove(v);
                return;
            }
        }
    }

    private static void doBalance(Node v) {
        setBalance(v);
        if (v.bal == -2) {
            if (h(v.left.left) >= h(v.left.right)) {
                v = rotateRight(v);
            } else {
                v = leftToRight(v);
            }
        } else if (v.bal == 2) {
            if (h(v.right.right) >= h(v.right.left)) {
                v = rotateLeft(v);
            } else {
                v = rightToLeft(v);
            }
        }
        if (v.parent != null) {
            doBalance(v.parent);
        } else {
            root = v;
        }
    }

    private static Node setAndReturn(Node v, Node q) {
        if (q.parent != null) {
            if (q.parent.right == v) {
                q.parent.right = q;
            } else {
                q.parent.left = q;
            }
        }
        setBalance(v, q);
        return q;
    }

    private static Node rotateLeft(Node v) {
        Node q = v.right;
        q.parent = v.parent;
        v.right = q.left;
        if (v.right != null)
            v.right.parent = v;
        q.left = v;
        v.parent = q;
        return setAndReturn(v, q);
    }

    private static Node rotateRight(Node v) {
        Node q = v.left;
        q.parent = v.parent;
        v.left = q.right;
        if (v.left != null)
            v.left.parent = v;
        q.right = v;
        v.parent = q;
        return setAndReturn(v, q);
    }

    private static Node leftToRight(Node v) {
        v.left = rotateLeft(v.left);
        return rotateRight(v);
    }

    private static Node rightToLeft(Node v) {
        v.right = rotateRight(v.right);
        return rotateLeft(v);
    }

    private static int h(Node v) {
        if (v == null)
            return -1;
        return v.h;
    }

    private static void setBalance(Node... nodes) {
        for (Node v : nodes) {
            fixHeight(v);
            v.bal = h(v.right) - h(v.left);
        }
    }

    private static void fixHeight(Node node) {
        if (node != null) {
            node.h = 1 + Math.max(h(node.left), h(node.right));
        }
    }

    private static Node find(Node node, int key) {
        if (node == null || node.val == key) {
            return node;
        }
        return find(key < node.val ? node.left : node.right, (key));
    }

    private static Node prev(int key) {
        Node curNode = root;
        Node lastVisited = null;
        while (curNode != null) {
            if (key > (curNode.val)) {
                lastVisited = curNode;
                curNode = curNode.right;
            } else {
                curNode = curNode.left;
            }
        }
        return lastVisited;
    }

    private static Node next(int key) {
        Node curNode = root;
        Node lastVisited = null;
        while (curNode != null) {
            if ((key) < curNode.val) {
                lastVisited = curNode;
                curNode = curNode.left;
            } else {
                curNode = curNode.right;
            }
        }
        return (lastVisited);
    }

    private static boolean exist(int value) {
        return find(root, value) != null;
    }

    private static void nextOrPrev(boolean isNext, int key) {
        Node res;
        if (isNext) {
            res = (next(key));
        } else {
            res = prev(key);
        }
        if (res == null) {
            System.out.println("none");
        } else {
            System.out.println(res.val);
        }
    }

    private static void scan() {
        cmd = scanner.next();
        value = Integer.parseInt(scanner.next());
    }

    public static void main(String[] args) {
        while (true) {
            try {
                scan();
                switch (cmd) {
                    case "insert":
                        insert(value);
                        break;
                    case "delete":
                        remove(value);
                        break;
                    case "next":
                        nextOrPrev(true, value);
                        break;
                    case "prev":
                        nextOrPrev((false), value);
                        break;
                    case "exists":
                        System.out.println(exist(value));
                        break;
                    default:
                        break;
                }
            } catch (Exception ignored) {
                break;
            }
        }
    }
}