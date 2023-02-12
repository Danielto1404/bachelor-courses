#include <iostream>
#include <map>
#include <vector>

// @author: Danielto1404

#define c_boost std::ios_base::sync_with_stdio(false); std::cin.tie(nullptr)

using namespace std;

int main() {
    c_boost;

    int n, m, k, type, startBlock = 0;
    cin >> n >> m >> k;

    map<int, vector<int>> typeElementsMap;
    vector<vector<int>> blocks = vector<vector<int>>(k, vector<int>());

    for (int i = 0; i < n; ++i) {
        cin >> type;
        if (typeElementsMap.find(type) != typeElementsMap.end()) typeElementsMap[type].push_back(i + 1);
        else typeElementsMap[type] = {i + 1};
    }

    for (auto &keyValue : typeElementsMap)
        for (int e: keyValue.second) {
            blocks[startBlock].push_back(e);
            startBlock = (startBlock + 1) % k;
        }

    for (auto &block: blocks) {
        cout << '\n' << block.size() << ' ';
        for (int e: block)
            cout << e << ' ';
    }
}
