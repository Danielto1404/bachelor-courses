//#include <bits/stdc++.h>
#include <iostream>
#include <vector>
#include <random>

#define c_boost ios_base::sync_with_stdio(false); cin.tie(nullptr);

using namespace std;

typedef long long LL;

struct Node
{
    Node* left;
    Node* right;
    LL key;
    LL priority;
    LL size;

    explicit Node (LL key, LL priority) :
            left(nullptr), right(nullptr), key(key), priority(priority), size(0)
    { }
};

class Tree
{
private:
    Node* root = nullptr;

    LL getSize (Node* node)
    {
        return node == nullptr ? 0 : node->size;
    }

    void updateSize (Node* node)
    {
        if (node == nullptr)
        {
            return;
        }
        node->size = getSize(node->left) + getSize(node->right) + 1;
    }

    pair <Node*, Node*> split (Node* node, LL key)
    {
        if (node == nullptr)
        {
            return make_pair(nullptr, nullptr);
        }
        if (key > node->key)
        {
            pair <Node*, Node*> leftAndRight = split(node->right, key);
            node->right = leftAndRight.first;
            updateSize(node);
            return make_pair(node, leftAndRight.second);
        } else
        {
            pair <Node*, Node*> leftAndRight = split(node->left, key);
            node->left = leftAndRight.second;
            updateSize(node);
            return make_pair(leftAndRight.first, node);
        }
    }

    Node* merge (Node* leftTree, Node* rightTree)
    {
        if (rightTree == nullptr || leftTree == nullptr)
        {
            Node* node = rightTree != nullptr ? rightTree : leftTree;
            updateSize(node);
            return node;
        }
        if (leftTree->priority > rightTree->priority)
        {
            leftTree->right = merge(leftTree->right, rightTree);
            updateSize(leftTree);
            return leftTree;
        } else
        {
            rightTree->left = merge(leftTree, rightTree->left);
            updateSize(rightTree);
            return rightTree;
        }
    }

    void insert (Node*&root, LL key, LL priority)
    {
        pair <Node*, Node*> leftAndRight = split(root, key);
        root = merge(leftAndRight.first, new Node(key, priority));
        root = merge(root, leftAndRight.second);
    }

    void remove (Node*&node, LL key)
    {
        if (node->key > key)
        {
            remove(node->left, key);
            updateSize(node);
            return;
        }
        if (node->key < key)
        {
            remove(node->right, key);
            updateSize(node);
            return;
        }
        if (node->key == key)
        {
            node = merge(node->left, node->right);
            return;
        }
    }

    LL findK (Node* node, LL k)
    {
        LL leftSize = getSize(node->left);
        if (k == leftSize + 1)
        {
            return node->key;
        } else if (k <= leftSize)
        {
            return findK(node->left, k);
        } else
        {
            return findK(node->right, k - (leftSize + 1));
        }
    }

public:
    void insert (LL key, LL priority)
    {
        insert(root, key, priority);
    }

    void remove (LL key)
    {
        remove(root, key);
    }

    LL findMaxByPos (LL k)
    {
        return findK(root, getSize(root) - k + 1);
    }
};

int main ()
{
    c_boost;
    random_device rd;
    uniform_int_distribution <int> uid(-10000000, 10000000);
    Tree tree;
    int n;
    cin >> n;
    char symbol;
    LL key;
    for (LL i = 0; i < n; ++i)
    {
        cin >> symbol;
        switch (symbol)
        {
            case '-':
                cin >> symbol >> key;
                tree.remove(key);
                break;
            case '+':
                cin >> symbol >> key;
                tree.insert(key, uid(rd));
                break;
            case '1':
                cin >> key;
                tree.insert(key, uid(rd));
                break;
            default:
                cin >> key;
                cout << tree.findMaxByPos(key) << "\n";
                break;
        }
    }
}
