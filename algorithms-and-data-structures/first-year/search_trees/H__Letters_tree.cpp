//#include <bits/stdc++.h>
#include <iostream>
#include <map>
#include <random>

#define c_boost ios_base::sync_with_stdio(false); cin.tie(nullptr);

using namespace std;

random_device rd;
uniform_int_distribution <int> uid (-10000000, 10000000);

map <char, int> symbols;

struct Node {
    Node* left;
    Node* right;
    int cnt, size, priority;
    unsigned letter, lets;

    Node(unsigned letter, int cnt) :
            left (nullptr), right (nullptr), letter (letter), cnt (cnt), size (0), priority (uid (rd)),
            lets (0) { }
};

class LettersTree {
private:
    Node* root = nullptr;

    static int getSize(Node* node) {
        return node == nullptr ? 0 : node->size;
    }

    static unsigned getLets(Node* node) {
        return node == nullptr ? 0 : node->lets;
    }

    static void update(Node* node) {
        if (node != nullptr) {
            node->lets = getLets (node->left) | getLets (node->right) | node->letter;
            node->size = getSize (node->left) + getSize (node->right) + node->cnt;
        }
    }

    static pair <Node*, Node*> splitRange(Node*&v, int pos) {
        int cnt = v->cnt;
        int offset = pos - getSize (v->left);

        // Changing cnt of current node
        v->cnt = offset;

        // Make new_tree
        Node* rightSubTree = new Node (v->letter, cnt - offset);
        rightSubTree->priority = uid (rd);
        rightSubTree = merge (rightSubTree, v->right);
        v->right = nullptr;
        update (v);
        return make_pair (v, rightSubTree);
    }

    static pair <Node*, Node*> split(Node*&node, int pos) {
        if (node == nullptr) {
            return make_pair (nullptr, nullptr);
        }
        int leftSize = getSize (node->left);
        if (leftSize >= pos) {
            pair <Node*, Node*> trees = split (node->left, pos);
            node->left = trees.second;
            update (node);
            return make_pair (trees.first, node);
        } else if (leftSize + node->cnt > pos) {
            pair <Node*, Node*> res = splitRange (node, pos);
            node = nullptr;
            return res;
        } else {
            pair <Node*, Node*> trees = split (node->right, pos - (leftSize + node->cnt));
            node->right = trees.first;
            update (node);
            return make_pair (node, trees.second);
        }
    }

    static Node* merge(Node* leftTree, Node* rightTree) {
        if (rightTree == nullptr || leftTree == nullptr) {
            Node* node = rightTree == nullptr ? leftTree : rightTree;
            update (node);
            return node;
        }
        if (leftTree->priority > rightTree->priority) {
            leftTree->right = merge (leftTree->right, rightTree);
            update (leftTree);
            return leftTree;
        } else {
            rightTree->left = merge (leftTree, rightTree->left);
            update (rightTree);
            return rightTree;
        }
    }

public:
    void insert(int letter, int shift, int index) {
        pair <Node*, Node*> trees = split (root, index - 1);
        Node* left = trees.first;
        Node* right = trees.second;
        root = merge (left, merge (new Node (letter, shift), right));
    }

    void remove(int left, int offset) {
        pair <Node*, Node*> leftSplit = split (root, left - 1);
        pair <Node*, Node*> rightSplit = split (leftSplit.second, offset);
        root = merge (leftSplit.first, rightSplit.second);
    }

    int find(int left, int right) {
        pair <Node*, Node*> leftSplit = split (root, left - 1);
        pair <Node*, Node*> rightSplit = split (leftSplit.second, right - left + 1);
        Node* mid = rightSplit.first;
        int res = __builtin_popcount (mid->lets);
        root = merge (leftSplit.first, merge (mid, rightSplit.second));
        return res;
    }
};

int main() {
    c_boost
    LettersTree lettersTree;
    int n;
    cin >> n;
    for (unsigned int i = 0; i < 26; i++) {
        symbols['a' + i] = (1u << i);
    }
    char op, letter;
    int pos, offset;
    for (int i = 0; i < n; i++) {
        cin >> op >> pos >> offset;
        switch (op) {
            case '+':
                cin >> letter;
                lettersTree.insert (symbols[letter], offset, pos);
                break;
            case '-':
                lettersTree.remove (pos, offset);
                break;
            case '?':
                cout << lettersTree.find (pos, offset) << "\n";
                break;
            default:
                break;
        }
    }
}
