#include <iostream>
#include <vector>
#include <algorithm>

// @author: Danielto1404

#define c_boost std::ios_base::sync_with_stdio(false); std::cin.tie(nullptr)

using namespace std;

struct Element {
    unsigned weight, idx;
};

inline void file_open(const string &name) {
    freopen((name + ".in").c_str(), "r", stdin);
    freopen((name + ".out").c_str(), "w", stdout);
}

void dfsMarkSuperSet(unsigned set, vector<unsigned> &visited, unsigned n) {
    visited[set] = true;
    for (unsigned i = 0; i < n; ++i) {
        unsigned element = 1u << i;
        if ((set & element) == 0) {
            unsigned n_set = set | element;
            if (!visited[n_set])
                dfsMarkSuperSet(n_set, visited, n);
        }
    }
}

int main() {
    c_boost;
    file_open("cycles");
    unsigned n, m, cycleSize, x;
    cin >> n >> m;
    vector<Element> elements(n);
    vector<unsigned> cycles(m, 0);
    vector<unsigned> visited((1u << n) + 1, false);
    for (int i = 0; i < elements.size(); ++i) {
        cin >> elements[i].weight;
        elements[i].idx = i;
    }
    sort(elements.begin(), elements.end(), [](Element a, Element b) { return a.weight > b.weight; });
    for (unsigned &cycle: cycles) {
        cin >> cycleSize;
        for (int i = 0; i < cycleSize; ++i) {
            cin >> x;
            --x;
            cycle |= (1u << x);
        }
    }
    for (unsigned cycle: cycles) {
        if (!visited[cycle])
            dfsMarkSuperSet(cycle, visited, n);
    }
    unsigned currentBase = 0;
    unsigned weight = 0;
    for (Element &element: elements) {
        unsigned baseWithOneElement = 1u << element.idx;
        unsigned n_base = currentBase | baseWithOneElement;
        if (!visited[n_base]) {
            weight += element.weight;
            currentBase = n_base;
        }
    }
    cout << weight;
}
