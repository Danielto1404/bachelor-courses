#include <iostream>
#include <vector>
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
        this->upLink = undefined;
        this->isChecked = false;
        this->go = vector<int>(ALPHABET_SZ, undefined);
        this->next = vector<int>(ALPHABET_SZ, undefined);
    }

    int parent, sufLink, upLink, charIdx, isChecked;
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
        mTrie[v].terminalIndexes.push_back(count++);
    }

    int getLinkToTerminal(int v) {
        if (mTrie[v].upLink == undefined) {
            int suf = getSufLink(v);
            if (suf == root) {
                mTrie[v].upLink = root;
            } else if (mTrie[suf].terminalIndexes.empty()) {
                mTrie[v].upLink = getLinkToTerminal(suf);
            } else {
                mTrie[v].upLink = suf;
            }
        }
        return mTrie[v].upLink;
    }

    int go(int v, int charIdx) {
        if (mTrie[v].go[charIdx] == undefined) {
            if (mTrie[v].next[charIdx] != undefined)
                 mTrie[v].go[charIdx] = mTrie[v].next[charIdx];
            else mTrie[v].go[charIdx] = v == root ? root : go(getSufLink(v), charIdx);
        }
        return mTrie[v].go[charIdx];
    }

    TrieVertex *getVertex(int v) {
        return &mTrie[v];
    }

private:
    int count = 0;
    vector<TrieVertex> mTrie;

    int getSufLink(int v) {
        if (mTrie[v].sufLink == undefined) {
            if (v == root || mTrie[v].parent == root)
                 mTrie[v].sufLink = root;
            else mTrie[v].sufLink = go(getSufLink(mTrie[v].parent), mTrie[v].charIdx);
        }
        return mTrie[v].sufLink;
    }
};


int main() {
    c_boost;
    file_open("search4");
    int n;
    cin >> n;
    vector<int> usedStrings(n, false);
    TrieDka trieDka = TrieDka();
    string si, t;
    for (int i = 0; i < n; ++i) {
        cin >> si;
        trieDka.addString(si);
    }
    cin >> t;
    int current = root;
    for (char c: t) {
        int idx = getIdx(c);
        current = trieDka.go(current, idx);
        auto vertex = trieDka.getVertex(current);
        int local = current;
        while (local != root) {
            if (!vertex->terminalIndexes.empty()) {
                for (int i : vertex->terminalIndexes)
                    usedStrings[i] = true;
                vertex->terminalIndexes.clear();
            }
            local = trieDka.getLinkToTerminal(local);
            vertex = trieDka.getVertex(local);
        }
    }

    for (int isUsed: usedStrings)
        cout << (isUsed ? "YES" : "NO") << '\n';
}
