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
    vector<int> next, go;
    vector<pair<int, int>> terminals;
};

class TrieAutomaton {
public:
    TrieAutomaton() : trie({TrieVertex(root)}) {}

    void addString(const string &s) {
        int v = root;
        for (char c: s) {
            int idx = getIdx(c);
            if (trie[v].next[idx] == undefined) {
                TrieVertex vertex = TrieVertex(v, idx);
                trie.push_back(vertex);
                trie[v].next[idx] = trie.size() - 1;
            }
            v = trie[v].next[idx];
        }
        trie[v].terminals.emplace_back(stringsCount++, s.length());
    }

    int go(int v, int charIdx) {
        if (trie[v].go[charIdx] == undefined) {
            if (trie[v].next[charIdx] != undefined) trie[v].go[charIdx] = trie[v].next[charIdx];
            else trie[v].go[charIdx] = v == root ? root : go(getSufLink(v), charIdx);
        }
        return trie[v].go[charIdx];
    }


    void findBorders(const string &t, vector<pair<int, int>> &ans) {
        ans.assign(stringsCount, {undefined, undefined});
        vector<pair<int, int>> borders(trie.size(), {undefined, undefined});
        vector<int> order;
        makeReverseOrderBfs(order);

        int current = root;
        for (int i = 0; i < t.length(); ++i) {
            current = go(current, getIdx(t[i]));
            if (borders[current].first == undefined) borders[current].first = i;
            borders[current].second = i;
        }

        for (int v: order) {
            int link = getSufLink(v), sufLeft, sufRight, vLeft, vRight;
            getBorderProperties(borders[link], sufLeft, sufRight);
            getBorderProperties(borders[v], vLeft, vRight);
            borders[link].first = setValue(sufLeft, vLeft, true);
            borders[link].second = setValue(sufRight, vRight, false);
        }

        for (int v: order)
            for (const auto &terminal: trie[v].terminals) {
                int left, right;
                getBorderProperties(borders[v], left, right);
                int length = terminal.second;
                left += (1 - length);
                right += (1 - length);
                ans[terminal.first] = {left < 0 ? -1 : left, right < 0 ? -1 : right};
            }
    }

private:
    int stringsCount = 0;
    vector<TrieVertex> trie;

    int getSufLink(int v) {
        if (trie[v].sufLink == undefined) {
            if (v == root || trie[v].parent == root) trie[v].sufLink = root;
            else trie[v].sufLink = go(getSufLink(trie[v].parent), trie[v].charIdx);
        }
        return trie[v].sufLink;
    }

    static int setValue(int a, int b, bool isMin) {
        return a == undefined ? b : (b == undefined ? a : (isMin ? min(a, b) : max(a, b)));
    }

    static void getBorderProperties(pair<int, int> &border, int &left, int &right) {
        left = border.first;
        right = border.second;
    }

    void makeReverseOrderBfs(vector<int> &order) {
        queue<int> q;
        q.push(root);
        while (!q.empty()) {
            int from = q.front();
            q.pop();
            for (int to: trie[from].next)
                if (to != undefined)
                    q.push(to);

            order.push_back(from);
        }
        reverse(order.begin(), order.end());
    }
};


int main() {
    c_boost;
    file_open("search6");
    int n;
    cin >> n;
    vector<pair<int, int>> borders;
    TrieAutomaton trieAutomaton = TrieAutomaton();
    string si, t;
    for (int i = 0; i < n; ++i) {
        cin >> si;
        trieAutomaton.addString(si);
    }
    cin >> t;
    trieAutomaton.findBorders(t, borders);
    for (const auto &border: borders)
        cout << border.first << ' ' << border.second << '\n';
}
