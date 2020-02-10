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
    LL sum;

    Node (LL key, LL priority) :
            left(nullptr), right(nullptr), key(key), priority(priority), sum(0)
    { }
};

class Tree
{
private:
    Node* root = nullptr;

    LL getSum (Node* node)
    {
        return node == nullptr ? 0 : node->sum;
    }

    void updateSum (Node* node)
    {
        if (node == nullptr)
        {
            return;
        }
        node->sum = getSum(node->left) + getSum(node->right) + node->key;
    }

    pair <Node*, Node*> split (Node* node, LL key)
    {
        if (node == nullptr)
        {
            return make_pair(nullptr, nullptr);
        }
        if (key >= node->key)
        {
            pair <Node*, Node*> leftAndRight = split(node->right, key);
            node->right = leftAndRight.first;
            updateSum(node);
            return make_pair(node, leftAndRight.second);
        } else
        {
            pair <Node*, Node*> leftAndRight = split(node->left, key);
            node->left = leftAndRight.second;
            updateSum(node);
            return make_pair(leftAndRight.first, node);
        }
    }

    Node* merge (Node* leftTree, Node* rightTree)
    {
        if (rightTree == nullptr || leftTree == nullptr)
        {
            Node* node = rightTree != nullptr ? rightTree : leftTree;
            updateSum(node);
            return node;
        }
        if (leftTree->priority > rightTree->priority)
        {
            leftTree->right = merge(leftTree->right, rightTree);
            updateSum(leftTree);
            return leftTree;
        } else
        {
            rightTree->left = merge(leftTree, rightTree->left);
            updateSum(rightTree);
            return rightTree;
        }
    }

    void insert (Node*&root, LL key, LL priority)
    {
        pair <Node*, Node*> leftAndRight = split(root, key);
        root = merge(leftAndRight.first, new Node(key, priority));
        root = merge(root, leftAndRight.second);
    }

    Node* find (Node* node, LL key)
    {
        if (node == nullptr || node->key == key)
        {
            return node;
        }
        return find(node->key >= key ? node->left : node->right, key);
    }

public:
    void insert (LL key, LL priority)
    {
        if (!exist(key))
        {
            insert(root, key, priority);
        }
    }

    bool exist (LL key)
    {
        return find(root, key) != nullptr;
    }

    LL findSum (LL leftBorder, LL rightBorder)
    {
        pair <Node*, Node*> leftAndRight = split(root, leftBorder - 1);
        Node* left = leftAndRight.first;
        Node* mid = leftAndRight.second;
        Node* right = nullptr;
        leftAndRight = split(mid, rightBorder);
        mid = leftAndRight.first;
        right = leftAndRight.second;
        LL sum = getSum(mid);
        root = merge(left, merge(mid, right));
        return sum;
    }
};

int main ()
{
    c_boost;
    int MOD = static_cast<int>(pow(10, 9));
    LL res = 0, x;
    char symbol, prev;
    int n;
    cin >> n;
    random_device rd;
    uniform_int_distribution <int> uid(-10000000, 10000000);
    Tree tree;
    for (LL i = 0; i < n; ++i)
    {
        cin >> symbol;
        switch (symbol)
        {
            case '+':
                cin >> x;
                tree.insert((res + x) % MOD, uid(rd));
                res = 0;
                break;
            default:
                LL leftBorder, rightBorder;
                cin >> leftBorder >> rightBorder;
                res = tree.findSum(leftBorder, rightBorder);
                cout << res << "\n";;
                break;
        }
    }
}
