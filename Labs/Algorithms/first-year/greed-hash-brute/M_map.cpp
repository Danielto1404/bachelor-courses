#include <vector>
#include <iostream>
#include <algorithm>

#define FILE freopen("map.in", "r", stdin), freopen("map.out", "w", stdout)
#define c_boost std::ios_base::sync_with_stdio(false); std::cin.tie(nullptr)

using table_key_val = std::vector <std::vector <std::pair <std::string, std::string>>>;
const int mod = 100000;
std::vector <int> pow2;

int getHash(std::string s) {
    int hash = 0;
    for (int i = 0; i < s.size(); ++i) {
        hash = (hash + ('z' - s[i]) * pow2[i]) % mod;
    }
    return hash;
}

class Map {
public:
    Map() : map_(table_key_val(mod)) {}
    std::string get(const std::string& key) {
        int hash = getHash(key);
        for (const auto& key_val : map_[hash]) {
            if (key_val.first == key) {
                return key_val.second;
            }
        }
        return "none";
    }
    void put(const std::string& key, const std::string& value) {
        int hash = getHash(key);
        for (auto& key_val : map_[hash]) {
            if (key_val.first == key) {
                key_val.second = value;
                return;
            }
        }
        map_[hash].emplace_back(key, value);
    }
    void remove(const std::string& key) {
        int hash = getHash(key);
        for (int i = 0; i < map_[hash].size(); i++) {
            if (map_[hash][i].first == key) {
                std::swap(map_[hash][i], map_[hash].back());
                map_[hash].pop_back();
                return;
            }
        }
    }
private:
    table_key_val map_;
};

int main() {
    c_boost;
    FILE;
    int pow = 1;
    for (int i = 0; i < 20; ++i, pow *= 2) {
        pow2.push_back(pow);
    }
    Map map;
    std::string cmd, key, value;
    while (std::cin >> cmd >> key) {
        if (cmd == "put") {
            std::cin >> value;
            map.put(key, value);
        } else if (cmd == "delete") {
            map.remove(key);
        } else {
            std::cout << map.get(key) << '\n';
        }
    }
}
