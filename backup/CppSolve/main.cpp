#include <iostream>
#include <vector>
#include <cmath>
#include <map>

using LL = long long;

using namespace std;

LL manhattan_distance(const std::vector<LL> &values) {
    LL distance = 0;
    for (size_t i = 0; i < values.size() - 1; ++i)
        for (size_t j = i + 1; j < values.size(); ++j)
            distance += abs(values[i] - values[j]);

    return distance;
}

int main() {
    LL k, n, x, y;
    std::map<LL, std::vector<LL> > class_elements;

    std::cin >> k >> n;
    for (size_t i = 0; i < n; ++i) {
        std::cin >> x >> y;
        if (class_elements.find(y) != class_elements.end()) {
            class_elements[y].push_back(x);
        } else {
            class_elements[y] = std::vector<LL>(1, x);
        }
    }

    for (auto it = class_elements.begin(); it != class_elements.end(); ++it) {
        std::cout << it->first;
    }


    return 0;
}
