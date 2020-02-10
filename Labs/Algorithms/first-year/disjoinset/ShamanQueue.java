package Algorithms.disjoinset;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ShamanQueue {
    private Node head = null;
    private Node tail = null;
    private Node middle = null;
    private int size = 0;

    private void add(int value) {
        if (size == 0) {
            head = new Node(value);
            head.next = null;
            tail = head;
            middle = head;
        } else {
            tail.next = new Node(value);
            tail = tail.next;
            tail.next = null;
            if (size % 2 == 0) {
                middle = middle.next;
            }
        }
        size++;
    }

    private void addMiddle(int value) {
        if (size % 2 == 0) {
            if (size != 0) {
                Node tmp = middle.next;
                middle.next = new Node(value);
                middle = middle.next;
                middle.next = tmp;
            } else {
                add(value);
                size--;
            }
        } else {
            if (size == 1) {
                tail.next = new Node(value);
                tail = tail.next;
                tail.next = null;
            } else {
                Node tmp = middle.next;
                middle.next = new Node(value);
                middle.next.next = tmp;
            }
        }
        size++;
    }

    private int pop() {
        assert size > 0;
        int result = head.value;
        if (size > 1) {
            head = head.next;
            if (size % 2 == 0) {
                middle = middle.next;
            }
            size--;
        } else {
            head = null;
            tail = null;
            middle = null;
            size = 0;
        }
        return result;
    }

    public class Node {
        final int value;
        Node next;

        Node(int value) {
            this.value = value;
        }
    }

    public static void main(String[] args) throws IOException {

        ShamanQueue queue = new ShamanQueue();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(reader.readLine());
        for (int i = 0; i < n; i++) {
            String[] tmp = reader.readLine().split("\\s");
            switch (tmp[0]) {
                case "+":
                    queue.add(Integer.parseInt(tmp[1]));
                    break;
                case "*":
                    queue.addMiddle(Integer.parseInt(tmp[1]));

                    break;
                default:
                    System.out.println(queue.pop());
                    break;
            }
        }
    }
}



