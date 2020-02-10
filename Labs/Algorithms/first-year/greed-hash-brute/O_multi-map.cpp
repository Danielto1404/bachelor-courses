#include <vector>
#include <iostream>

#define FILE freopen("multimap.in", "r", stdin), freopen("multimap.out", "w", stdout)
#define c_boost std::ios_base::sync_with_stdio(false); std::cin.tie(nullptr)
#define key first
#define set_of_values second

const int MAP_MOD = 100000;
const int SET_MOD = 100;
std::vector <int> pow2;

class StringSet;

using hs_table = std::vector <std::vector <std::pair <std::string, StringSet*>>>;
using map_pair = std::pair <std::string, StringSet*>;

int getHash(std::string s, int mod = MAP_MOD) {
    int hash = 0;
    for (int i = 0; i < s.size(); ++i) {
        hash = (hash + ('z' - s[i]) * pow2[i]) % mod;
    }
    return hash;
}

class StringSet {
public:
    StringSet() : set_(std::vector <std::vector <std::string>>(SET_MOD)) {}

    bool exist(const std::string& k) {
        int hash = getHash(k, SET_MOD);
        for (int j = 0; j < set_[hash].size(); j++) {
            if (set_[hash][j] == k) {
                std::swap(set_[hash][j], set_[hash].back());
                return true;
            }
        }
        return false;
    }
    void insert(const std::string& k) {
        if (!exist(k)) {
            int hash = getHash(k, SET_MOD);
            set_[hash].push_back(k);
            ++size_;
        }
    }
    void remove(const std::string& k) {
        if (exist(k)) {
            int hash = getHash(k, SET_MOD);
            set_[hash].pop_back();
            --size_;
        }
    }
    std::string to_string() {
        std::string result = std::to_string(size_);
        for (auto& hashes: set_)
            for (auto& str : hashes)
                result += " " + str;

        return result;
    }
private:
    std::vector <std::vector <std::string>> set_;
    int size_ = 0;
};

class MultiMap {
public:
    MultiMap() : map_(hs_table(MAP_MOD)) {}

    void put(const std::string& k, const std::string& v) {
        int hash = getHash(k);
        bool contains_key = exist_key(k, hash);
        if (!contains_key) {
            auto* str_set = new StringSet();
            str_set->insert(v);
            map_[hash].emplace_back(k, str_set);
        } else {
            map_[hash].back().set_of_values->insert(v);
        }
    }
    void remove(const std::string& k, const std::string& v) {
        int hash = getHash(k);
        bool contains_key = exist_key(k, hash);
        if (contains_key) {
            map_[hash].back().set_of_values->remove(v);
        }
    }
    void remove_all(const std::string& k) {
        int hash = getHash(k);
        bool contains_key = exist_key(k, hash);
        if (contains_key) {
            delete map_[hash].back().set_of_values;
            map_[hash].pop_back();
        }
    }
    std::string get(const std::string& k) {
        int hash = getHash(k);
        bool contains_key = exist_key(k, hash);
        if (contains_key) {
            return map_[hash].back().set_of_values->to_string();
        }
        return "0";
    }
private:
    hs_table map_;

    bool exist_key(const std::string& k, int hash) {
        for (int i = 0; i < map_[hash].size(); ++i) {
            if (map_[hash][i].key == k) {
                std::swap(map_[hash][i], map_[hash].back());
                return true;
            }
        }
        return false;
    }
};

int main() {
    c_boost;
    FILE;
    int pow = 1;
    for (int i = 0; i < 20; ++i, pow *= 2) {
        pow2.push_back(pow);
    }
    MultiMap map;
    std::string cmd, k, v;
    while (std::cin >> cmd >> k) {
        if (cmd == "put") {
            std::cin >> v;
            map.put(k, v);
        } else if (cmd == "delete") {
            std::cin >> v;
            map.remove(k, v);
        } else if (cmd == "deleteall") {
            map.remove_all(k);
        } else {
            std::cout << map.get(k) << '\n';
        }
    }
}
