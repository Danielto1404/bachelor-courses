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
    bool hasZero;
    bool isAllZeros;

    Node (LL key, LL priority) :
            left(nullptr), right(nullptr),
            key(key), priority(priority), size(0),
            hasZero(true), isAllZeros(false)
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

    bool hasZero (Node* node)
    {
        return node == nullptr ? false : node->hasZero;
    }

    bool isAllZeros (Node* node)
    {
        return node == nullptr ? true : node->isAllZeros;
    }

    void update (Node* node)
    {
        if (node == nullptr)
        {
            return;
        }
        node->size = getSize(node->left) + getSize(node->right) + 1;
        node->hasZero = hasZero(node->left) || hasZero(node->right) || node->key == 0;
        node->isAllZeros = isAllZeros(node->left) && isAllZeros(node->right) && node->key == 0;
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
            update(node);
            return make_pair(node, leftAndRight.second);
        } else
        {
            pair <Node*, Node*> leftAndRight = split(node->left, key);
            node->left = leftAndRight.second;
            update(node);
            return make_pair(leftAndRight.first, node);
        }
    }

    Node* merge (Node* leftTree, Node* rightTree)
    {
        if (rightTree == nullptr || leftTree == nullptr)
        {
            Node* node = rightTree != nullptr ? rightTree : leftTree;
            update(node);
            return node;
        }
        if (leftTree->priority > rightTree->priority)
        {
            leftTree->right = merge(leftTree->right, rightTree);
            update(leftTree);
            return leftTree;
        } else
        {
            rightTree->left = merge(leftTree, rightTree->left);
            update(rightTree);
            return rightTree;
        }
    }

    Node* removeFirstZero (Node* node)
    {
        if (hasZero(node))
        {
            if (hasZero(node->left))
            {
                node->left = removeFirstZero(node->left);
                update(node);
                return node;
            } else if (node->key == 0)
            {
                return merge(node->left, node->right);
            } else
            {
                node->right = removeFirstZero(node->right);
                update(node);
                return node;
            }
        }
        return node;
    }

    LL findCorrectSize (Node* node)
    {
        if (!isAllZeros(node))
        {
            if (isAllZeros(node->right) && node->key == 0)
            {
                return findCorrectSize(node->left);
            } else if (!isAllZeros(node->right))
            {
                return getSize(node->left) + 1 + findCorrectSize(node->right);
            } else
            {
                return getSize(node->left) + 1;
            }
        } else
        {
            return 0;
        }
    }

    void showTree (Node* node, LL size)
    {
        if (node != nullptr && size > 0)
        {
            showTree(node->left, size);
            size -= getSize(node->left);
            if (size-- > 0)
            {
                cout << node->key << " ";
            }
            showTree(node->right, size);
        }
    }

public:
    void insertKey (LL key, LL priority, LL position)
    {
        Node* left, * right;
        pair <Node*, Node*> leftAndRight = split(root, position);
        left = leftAndRight.first;
        right = leftAndRight.second;
        right = removeFirstZero(right);
        root = merge(left, new Node(key, priority));
        root = merge(root, right);

    }

    void show ()
    {
        LL size = findCorrectSize(root);
        cout << size << "\n";
        showTree(root, size);
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
    LL pos;
    for (int i = 0; i < n + m; i++)
    {
        tree.insertKey(0, uid(rd), i);
    }
    for (LL i = 0; i < n; i++)
    {
        cin >> pos;
        tree.insertKey(i + 1, uid(rd), pos - 1);
    }
    tree.show();
}
