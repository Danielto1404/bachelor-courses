#include <iostream>
#include <vector>
#include <queue>
#include <algorithm>

// @author: Danielto1404

#define c_boost std::ios_base::sync_with_stdio(false); std::cin.tie(nullptr)

using namespace std;

const int root = 0;
const int undefined = -1;
const int ALPHABET_SZ = 26;

inline void file_open(const string &name) {
    freopen((name + ".in").c_str(), "r", stdin);
    freopen((name + ".out").c_str(), "w", stdout);
}

inline int getIdx(char c) {
    return (int) c - 'a';
}

struct TrieVertex {
    explicit TrieVertex(int parent, int charIdx = undefined) {
        this->parent = parent;
        this->charIdx = charIdx;
        this->sufLink = undefined;
        this->go = vector<int>(ALPHABET_SZ, undefined);
        this->next = vector<int>(ALPHABET_SZ, undefined);
    }

    int parent, sufLink, charIdx;
    vector<int> next, go, terminalIndexes;
};

class TrieDka {
public:
    TrieDka() : mTrie({TrieVertex(root)}) {}

    void addString(const string &s) {
        int v = root;
        for (char c: s) {
            int idx = getIdx(c);
            if (mTrie[v].next[idx] == undefined) {
                TrieVertex vertex = TrieVertex(v, idx);
                mTrie.push_back(vertex);
                mTrie[v].next[idx] = mTrie.size() - 1;
            }
            v = mTrie[v].next[idx];
        }
        mTrie[v].terminalIndexes.push_back(stringsCount++);
    }

    int go(int v, int charIdx) {
        if (mTrie[v].go[charIdx] == undefined) {
            if (mTrie[v].next[charIdx] != undefined)
                 mTrie[v].go[charIdx] = mTrie[v].next[charIdx];
            else mTrie[v].go[charIdx] = v == root ? root : go(getSufLink(v), charIdx);
        }
        return mTrie[v].go[charIdx];
    }

    void calculateAnswer(const string &t, vector<int> &cnt) {
        cnt.assign(stringsCount, 0);
        vector<int> visited = vector<int>(mTrie.size(), 0);
        vector<int> order;
        makeReverseOrderBfs(order);

        int current = root;
        for (char c: t) {
            current = go(current, getIdx(c));
            visited[current] += 1;
        }

        for (int v: order) {
            int sufLink = getSufLink(v);
            visited[sufLink] += visited[v];
        }

        for (int v: order)
            for (int term: mTrie[v].terminalIndexes)
                cnt[term] = visited[v];
    }

private:
    int stringsCount = 0;
    vector<TrieVertex> mTrie;

    int getSufLink(int v) {
        if (mTrie[v].sufLink == undefined) {
            if (v == root || mTrie[v].parent == root)
                 mTrie[v].sufLink = root;
            else mTrie[v].sufLink = go(getSufLink(mTrie[v].parent), mTrie[v].charIdx);
        }
        return mTrie[v].sufLink;
    }

    void makeReverseOrderBfs(vector<int> &order) {
        queue<int> q;
        q.push(root);
        while (!q.empty()) {
            int from = q.front();
            q.pop();
            for (int to: mTrie[from].next)
                if (to != undefined)
                    q.push(to);

            order.push_back(from);
        }
        reverse(order.begin(), order.end());
    }
};


int main() {
    c_boost;
    file_open("search5");
    int n;
    cin >> n;
    vector<int> repeated;
    TrieDka trieDka = TrieDka();
    string si, t;
    for (int i = 0; i < n; ++i) {
        cin >> si;
        trieDka.addString(si);
    }
    cin >> t;
    trieDka.calculateAnswer(t, repeated);
    for (int cnt: repeated)
        cout << cnt << '\n';
}
