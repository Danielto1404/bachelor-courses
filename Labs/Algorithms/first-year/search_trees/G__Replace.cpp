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

    Node (LL key, LL priority) :
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
        LL leftSize = getSize(node->left);
        if (key > leftSize)
        {
            pair <Node*, Node*> leftAndRight = split(node->right, key - (leftSize + 1));
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

    void insert (Node*&root, LL key, LL priority, LL index)
    {
        pair <Node*, Node*> leftAndRight = split(root, index);
        root = merge(leftAndRight.first, new Node(key, priority));
        root = merge(root, leftAndRight.second);
    }

    void show (Node* node)
    {
        if (node != nullptr)
        {
            show(node->left);
            cout << node->key << " ";
            show(node->right);
        }
    }

public:
    void insert (LL key, LL priority, LL index)
    {
        insert(root, key, priority, index);
    }

    void replaceToBegin (LL leftBorder, LL rightBorder)
    {
        Node* left, * mid, * right;
        pair <Node*, Node*> leftAndRight = split(root, rightBorder);
        left = leftAndRight.first;
        right = leftAndRight.second;
        leftAndRight = split(left, leftBorder - 1);
        left = leftAndRight.first;
        mid = leftAndRight.second;
        root = merge(merge(mid, left), right);
    }

    void show ()
    {
        show(root);
    }
};

int main ()
{
    c_boost;
    int n, m;
    cin >> n >> m;
    random_device rd;
    uniform_int_distribution <int> uid(-10000000, 10000000);
    Tree tree;
    for (LL i = 0; i < n; ++i)
    {
        LL index = i;
        tree.insert(i + 1, uid(rd), index);
    }
    for (LL i = 0; i < m; ++i)
    {
        LL left, right;
        cin >> left >> right;
        tree.replaceToBegin(left, right);
    }
    tree.show();
}
