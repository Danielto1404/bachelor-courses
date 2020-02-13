#include <iostream>
#include <vector>
#include <set>
#include <map>

// @author: Danielto1404

#define c_boost std::ios_base::sync_with_stdio(false); std::cin.tie(nullptr)

using namespace std;

const int RANDOM_X = 1239;
string ans;
set<int> used;
map<int, int> idxMap;
vector<int> powers;
vector<vector<int>> hashes;

int calculateHash(int left, int right, vector<int> &stringHashes) {
    int hash = stringHashes[right];
    if (left != 0) hash -= stringHashes[left - 1];
    hash *= powers[powers.size() - right - 1];  //  hash normalization
    return hash;
}

bool usedContains(int hash) { return used.find(hash) != used.end(); }

bool getCommonString(vector<string> &input, int commonLength, int minSize) {
    if (minSize < commonLength) return false;
    used.clear();
    idxMap.clear();

    for (int idx = 0; idx <= (int) input[0].length() - commonLength; ++idx) {
        int hash = calculateHash(idx, idx + commonLength - 1, hashes[0]);
        used.insert(hash);
        idxMap[hash] = idx;
    }

    for (int i = 1; i < input.size(); ++i) {
        if (used.empty()) {
            ans = "";
            return false;
        }
        set<int> curUsedHashes;
        for (int idx = 0; idx <= (int) input[i].length() - commonLength; ++idx) {
            int hash = calculateHash(idx, idx + commonLength - 1, hashes[i]);
            if (usedContains(hash)) {
                string prevSubstr = input[0].substr(idxMap.at(hash), commonLength);
                string curSubstr = input[i].substr(idx, commonLength);
                if (prevSubstr == curSubstr) {
                    ans = prevSubstr;
                    curUsedHashes.insert(hash);
                    continue;
                }
                idxMap.erase(hash);
            }
        }
        used = curUsedHashes;
    }
    return !used.empty();
}

int mostCommonSubstringBinarySearch(vector<string> &input, int left, int right, int minSize) {
    if (right == left) {
        getCommonString(input, left, minSize);
        return left;
    }
    if (right - left == 1) {
        bool haveCommon = getCommonString(input, right, minSize);
        return haveCommon ? right : mostCommonSubstringBinarySearch(input, left, left, minSize);
    }

    int mid = (right + left) / 2;
    bool haveCommon = getCommonString(input, mid, minSize);
    haveCommon ? left = mid : right = mid - 1;
    return mostCommonSubstringBinarySearch(input, left, right, minSize);
}

int main() {
    c_boost;
    int maxSize = 0, minSize = 10000, q;
    powers = {1};
    for (int i = 1; i < 10000; ++i) {
        powers.push_back(powers[i - 1] * RANDOM_X);
    }
    cin >> q;
    hashes.resize(q);
    vector<string> input(q);
    for (int i = 0; i < q; ++i) {
        cin >> input[i];
        maxSize = max(maxSize, (int) input[i].length());
        minSize = min(minSize, (int) input[i].length());
        hashes[i] = {input[i][0]};
        for (int j = 1; j < input[i].length(); ++j)
            hashes[i].push_back(hashes[i].back() + powers[j] * input[i][j]);
    }
    mostCommonSubstringBinarySearch(input, 0, maxSize, minSize);
    cout << ans;
}
