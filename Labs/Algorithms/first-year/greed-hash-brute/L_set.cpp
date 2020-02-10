#include <vector>
#include <iostream>
#include <algorithm>

#define FILE freopen("set.in", "r", stdin), freopen("set.out", "w", stdout)
#define c_boost std::ios_base::sync_with_stdio(false); std::cin.tie(nullptr)

const int mod = 100000;
const int a = mod + 239;
const int b = mod + 30 + 1;

int getHash(int key) {
    return (abs(key * a + b)) % mod;
}

class Set {
public:
    Set() : set_(std::vector <std::vector <int>>(mod)) {}
    bool exist(int key) {
        int hash = getHash(key);
        for (int j = 0; j < set_[hash].size(); j++) {
            if (set_[hash][j] == key) {
                std::swap(set_[hash][j], set_[hash].back());
                return true;
            }
        }
        return false;
    }
    void insert(int key) {
        if (!exist(key)) {
            int hash = getHash(key);
            set_[hash].push_back(key);
        }
    }
    void remove(int key) {
        if (exist(key)) {
            int hash = getHash(key);
            set_[hash].pop_back();
        }
    }
private:
    std::vector <std::vector <int>> set_;
};

int main() {
    c_boost;
    FILE;
    std::string cmd;
    int key;
    Set set;
    while (std::cin >> cmd >> key) {
        if (cmd == "insert") {
            set.insert(key);
        } else if (cmd == "delete") {
            set.remove(key);
        } else {
            std::cout << (set.exist(key) ? "true" : "false") << '\n';
        }
    }
}
