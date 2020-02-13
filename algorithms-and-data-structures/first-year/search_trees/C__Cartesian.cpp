//#include <bits/stdc++.h>
#include <iostream>
#include <vector>

#define c_boost ios_base::sync_with_stdio(false); cin.tie(nullptr);

using namespace std;

struct IndexedPair
{
    int x, y, z;
    
    void show ()
    {
        cout << x << " " << y << " " << z << "\n";
    }
};


struct Node
{
    Node* left;
    Node* right;
    Node* parent;
    int priority;
    int index;
    
    Node (int priority, int index) : priority(priority), index(index),
    left(nullptr), right(nullptr), parent(nullptr)
    { }
};

Node* root;

void insert (Node* current, Node* toAdd)
{
    if (current->priority > toAdd->priority)
    {
        if (current == root)
        {
            root = toAdd;
            root->left = current;
            current->parent = toAdd;
        } else
        {
            while (current->priority > toAdd->priority && current != root)
            {
                current = current->parent;
            }
            if (current == root && root->priority > toAdd->priority)
            {
                root = toAdd;
                toAdd->left = current;
                current->parent = root;
            } else
            {
                toAdd->left = current->right;
                current->right->parent = toAdd;
                current->right = toAdd;
                toAdd->parent = current;
            }
        }
    } else if (current->priority < toAdd->priority)
    {
        current->right = toAdd;
        toAdd->parent = current;
    }
}


void setIndexesForNode (Node* node, vector <IndexedPair> &answer)
{
    if (node != nullptr)
    {
        setIndexesForNode(node->left, answer);
        IndexedPair curIndexes{};
        curIndexes.x = node->parent == nullptr ? 0 : node->parent->index;
        curIndexes.y = node->left == nullptr ? 0 : node->left->index;
        curIndexes.z = node->right == nullptr ? 0 : node->right->index;
        answer[node->index - 1] = curIndexes;
        setIndexesForNode(node->right, answer);
    }
}

bool compByX (const IndexedPair &first, const IndexedPair &second)
{
    return first.x < second.x;
}

int main ()
{
    c_boost;
    size_t n;
    cin >> n;
    vector <IndexedPair> pairs(n);
    vector <IndexedPair> answer(n);
    for (int i = 0; i != n; ++i)
    {
        cin >> pairs[i].x >> pairs[i].y;
        pairs[i].z = i + 1;
    }
    sort(pairs.begin(), pairs.end(), compByX);
    Node* lastAddedNode = new Node(pairs[0].y, pairs[0].z);
    root = lastAddedNode;
    for (int i = 1; i < n; i++)
    {
        Node* curNodeToAdd = new Node(pairs[i].y, pairs[i].z);
        insert(lastAddedNode, curNodeToAdd);
        lastAddedNode = curNodeToAdd;
    }
    cout << "YES" << "\n";
    setIndexesForNode(root, answer);
    for (auto &node : answer)
    {
        node.show();
    }
}
