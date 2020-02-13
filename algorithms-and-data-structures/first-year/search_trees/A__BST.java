/*
Реализуйте просто двоичное дерево поиска:

insert x — добавить в дерево ключ x. Если ключ x есть в дереве, то ничего делать не надо
delete x — удалить из дерева ключ x. Если ключа x в дереве нет, то ничего делать не надо
exists x — если ключ x есть в дереве выведите «true», если нет «false»
next x — выведите минимальный элемент в дереве, строго больший x, или «none» если такого нет
prev x — выведите максимальный элемент в дереве, строго меньший x, или «none» если такого нет
*/

import java.util.Objects;
import java.util.Scanner;

public class A {
    private static Scanner scanner = new Scanner(System.in);
    private static String cmd;
    private static int value;
    private static Node root;

    static class Node {
        Node left;
        Node right;
        int val;

        Node(int val) {
            this.left = null;
            this.right = null;
            this.val = val;
        }
    }

    private static Node insert(Node node, int key) {
        if (root == null) {
            root = new Node(key);
            return root;
        }
        if (node == null) {
            return new Node(key);
        }
        if (key > node.val) {
            node.right = insert(node.right, key);
        } else if (key < node.val) {
            node.left = insert(node.left, key);
        }
        return node;
    }

    private static Node remove(Node node, int key) {
        if (node == null || (node.right == null && node.left == null && key == node.val)) {
            return null;
        }
        if (key < node.val) {
            node.left = remove(node.left, key);
        } else if (key > node.val) {
            node.right = remove(node.right, key);
        } else {
            // Ставим val = T.min - минимум поддерева и удаляем нижний минимум.
            if (node.left != null && node.right != null) {
                node.val = next(node.val).val;
                node.right = remove(node.right, node.val);
            } else return Objects.requireNonNullElseGet(node.left, () -> node.right);
        }
        return node;
    }

    private static Node find(Node node, int key) {
        if (node == null || node.val == key) {
            return node;
        }
        return find(key < node.val ? node.left : node.right, key);
    }

    private static Node prev(int key) {
        Node curNode = root;
        Node lastVisited = null;
        while (curNode != null) {
            if (key > curNode.val) {
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
            if (key < curNode.val) {
                lastVisited = curNode;
                curNode = curNode.left;
            } else {
                curNode = curNode.right;
            }
        }
        return lastVisited;
    }

    private static void insert(int key) {
        insert(root, key);
    }

    private static void remove(int value) {
        root = remove(root, value);
    }

    private static boolean exist(int value) {
        return find(root, value) != null;
    }

    private static void nextOrPrev(boolean isNext, int key) {
        Node res;
        if (isNext) {
            res = next(key);
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
                        nextOrPrev(false, value);
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

