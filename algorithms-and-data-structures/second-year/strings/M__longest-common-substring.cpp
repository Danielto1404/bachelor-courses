#include <iostream>
#include <vector>
#include <set>
#include <map>

// @author: Danielto1404

#define c_boost std::ios_base::sync_with_stdio(false); std::cin.tie(nullptr)

using namespace std;

const int RANDOM_X = 1239;
vector<int> fst_hashes;
vector<int> snd_hashes;
vector<int> powers;

inline void file_open(const string &name) {
    freopen((name + ".in").c_str(), "r", stdin);
    freopen((name + ".out").c_str(), "w", stdout);
}

int calcHash(int left, int right, const vector<int> &hashes) {
    int hash = hashes[right];
    if (left != 0) hash -= hashes[left - 1];
    hash *= powers[powers.size() - right - 1];  //  hash normalization
    return hash;
}

string getCommonString(const string &fst, const string &snd, int length) {
    set<int> used;
    map<int, int> idxMap;
    for (int i = 0; i <= fst.length() - length; ++i) {
        int fstSubstringHash = calcHash(i, i + length - 1, fst_hashes);
        used.insert(fstSubstringHash);
        idxMap[fstSubstringHash] = i;
    }
    for (int i = 0; i <= snd.length() - length; ++i) {
        int sndSubstringHash = calcHash(i, i + length - 1, snd_hashes);
        if (used.find(sndSubstringHash) != used.end()) {
            string fst_substr = fst.substr(idxMap.at(sndSubstringHash), length);
            string snd_substr = snd.substr(i, length);
            if (fst_substr == snd_substr)
                return fst_substr;
        }
    }
    return "";
}

int mostCommonSubstringBinarySearch(const string &fst, const string &snd, int left, int right, string &ans) {
    if (right == left) {
        ans = getCommonString(fst, snd, left);
        return -1;
    }
    if (right - left == 1) {
        ans = getCommonString(fst, snd, right);
        return ans.empty() ? mostCommonSubstringBinarySearch(fst, snd, left, left, ans) : -1;
    }

    int mid = (right + left) / 2;
    string commonSubstr = getCommonString(fst, snd, mid);
    commonSubstr.empty() ? right = mid - 1 : left = mid;
    return mostCommonSubstringBinarySearch(fst, snd, left, right, ans);
}

int main() {
    c_boost;
    file_open("common");

    string fst, snd;
    cin >> fst >> snd;

    fst_hashes.assign(fst.length(), fst[0]);
    snd_hashes.assign(snd.length(), snd[0]);
    powers = {1};

    for (int i = 1; i < max(fst.length(), snd.length()); ++i) {
        powers.push_back(powers[i - 1] * RANDOM_X);
        if (i < fst.length()) fst_hashes[i] = fst_hashes[i - 1] + powers[i] * fst[i];
        if (i < snd.length()) snd_hashes[i] = snd_hashes[i - 1] + powers[i] * snd[i];
    }

    string ans;
    mostCommonSubstringBinarySearch(fst, snd, 0, min(fst.length(), snd.length()), ans);
    cout << ans;
}