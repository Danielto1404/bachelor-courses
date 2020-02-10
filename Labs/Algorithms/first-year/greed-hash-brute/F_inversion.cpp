#include <iostream>
#include <vector>
#include <algorithm>

#define FILE freopen("john.in", "r", stdin), freopen("john.out", "w", stdout)
#define c_boost std::ios_base::sync_with_stdio(false); std::cin.tie(nullptr)
#define red first
#define blue second

long long count = 0;
std::vector <std::pair <int, int>> cards;

void invCount(int left, int right) {
    if (left < right) {
        int mid = (left + right) / 2 + 1;
        int half = mid - left;
        int maxInd = right - left;

        invCount(left, mid - 1);
        invCount(mid, right);

        std::vector <std::pair <int, int>> toMerge(maxInd + 1);
        for (int i = 0; i <= maxInd; ++i)
            toMerge[i] = cards[left + i];

        int leftInd = 0, rightInd = half, iter = left;

        while (leftInd < half && rightInd <= maxInd) {
            if (toMerge[leftInd].blue <= toMerge[rightInd].blue)
                cards[iter++] = toMerge[leftInd++];
            else {
                cards[iter++] = toMerge[rightInd++];
                count += half - leftInd;
            }
        }
        while (leftInd < half) {
            cards[iter++] = toMerge[leftInd++];
        }
        while (rightInd <= maxInd) {
            cards[iter++] = toMerge[rightInd++];
        }
    }
}

int main() {
    c_boost;
    FILE;
    int n;
    std::cin >> n;
    cards.resize(n);
    for (int i = 0; i < n; ++i)
        std::cin >> cards[i].red >> cards[i].blue;

    std::sort(cards.begin(), cards.end());
    invCount(0, n - 1);
    std::cout << count;
} 
