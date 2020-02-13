#include <iostream>
#include <vector>
#include <set>
#include <algorithm>

// @author: Danielto1404

#define c_boost std::ios_base::sync_with_stdio(false); std::cin.tie(nullptr)

using namespace std;

set <pair <int, int>> answers;

bool compare(int fst, int snd) {
    if (answers.find({fst, snd}) != answers.end()) return true;
    if (answers.find({snd, fst}) != answers.end()) return false;
    cout << "1 " << fst << ' ' << snd << '\n';
    cout.flush();
    string answer;
    cin >> answer;
    bool fst_less = answer == "YES";
    answers.insert(fst_less ? make_pair(fst, snd) : make_pair(snd, fst));
    return fst_less;
}

void lampSort(vector <int>& lamps, int low, int up) {
    if (low == up) return;
    if (up - low == 1) {
        if (compare(lamps[up], lamps[low]))
            swap(lamps[up], lamps[low]);

        return;
    }
    int mid = (low + up) / 2;
    lampSort(lamps, mid + 1, up);
    lampSort(lamps, low, mid);

    auto buf = vector <int>(up - low + 1);
    auto first = vector <int>(lamps.begin() + low, lamps.begin() + mid + 1);
    auto second = vector <int>(lamps.begin() + mid + 1, lamps.begin() + up + 1);

    merge(first.begin(), first.end(), second.begin(), second.end(), buf.begin(), compare);

    copy(buf.begin(), buf.end(), lamps.begin() + low);
}

int main() {
    c_boost;
    int n;
    cin >> n;
    vector <int> lamps(n);
    for (int i = 0; i < n; ++i) lamps[i] = i + 1;
    lampSort(lamps, 0, n - 1);

    bool isCorrect = true;
    for (int i = 1; i < n; ++i)
        if (!compare(lamps[i - 1], lamps[i])) {
            isCorrect = false;
            break;
        }

    cout << "0 ";
    for (int lamp: lamps) cout << (isCorrect ? lamp : 0) << ' ';
}
