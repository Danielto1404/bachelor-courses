#include <vector>
#include <iostream>
#include <algorithm>

#define FILE freopen("linkedmap.in", "r", stdin), freopen("linkedmap.out", "w", stdout)
#define c_boost std::ios_base::sync_with_stdio(false); std::cin.tie(nullptr)

struct Node {
    Node(std::string key, std::string value) : key(std::move(key)), value(std::move(value)), next(nullptr),
                                               previous(nullptr) {}

    std::string key, value;
    Node* next, * previous;

};

const int mod = 100000;
std::vector <int> pow2;
Node* last_added = nullptr;

int getHash(std::string s) {
    int hash = 0;
    for (int i = 0; i < s.size(); ++i) {
        hash = (hash + ('z' - s[i]) * pow2[i]) % mod;
    }
    return hash;
}

class LinkedMap {
public:
    LinkedMap() : map_(std::vector <std::vector <Node*>>(mod)) {}

    void put(const std::string& key, const std::string& value) {
        int hash = getHash(key);
        Node* node_to_add = findNode(key);
        if (node_to_add != nullptr) {
            node_to_add->value = value;
        } else {
            node_to_add = new Node(key, value);
            if (last_added != nullptr) {
                last_added->next = node_to_add;
            }
            node_to_add->previous = last_added;
            last_added = node_to_add;
            map_[hash].push_back(node_to_add);
        }
    }
    void remove(const std::string& key) {
        int hash = getHash(key);
        Node* node_to_remove = findNode(key);
        if (node_to_remove != nullptr) {
            Node* cur_prev = node_to_remove->previous;
            Node* cur_next = node_to_remove->next;
            if (cur_prev) {
                cur_prev->next = cur_next;
            }
            if (cur_next) {
                cur_next->previous = cur_prev;
            }
            if (last_added == node_to_remove) {
                last_added = cur_prev;
            }
            map_[hash].pop_back();
            return;
        }
    }

    std::string get(const std::string& key) {
        Node* found = findNode(key);
        return found ? found->value : "none";
    }
    std::string prev(const std::string& key) {
        Node* found = findNode(key);
        return !found ? "none" : found->previous ? found->previous->value : "none";
    }
    std::string next(const std::string& key) {
        Node* found = findNode(key);
        return !found ? "none" : found->next ? found->next->value : "none";
    }

private:
    std::vector <std::vector <Node*>> map_;

    Node* findNode(const std::string& key) {
        int hash = getHash(key);
        for (int i = 0; i < map_[hash].size(); ++i) {
            if (map_[hash][i]->key == key) {
                std::swap(map_[hash][i], map_[hash].back());
                return map_[hash].back();
            }
        }
        return nullptr;
    }
};

int main() {
    c_boost;
  //  FILE;
    int pow = 1;
    for (int i = 0; i < 20; ++i, pow *= 2) {
        pow2.push_back(pow);
    }
    LinkedMap map;
    std::string cmd, key, value;
    while (std::cin >> cmd >> key) {
        if (cmd == "put") {
            std::cin >> value;
            map.put(key, value);
        } else if (cmd == "delete") {
            map.remove(key);
        } else if (cmd == "next") {
            std::cout << map.next(key) << '\n';
        } else if (cmd == "prev") {
            std::cout << map.prev(key) << '\n';
        } else {
            std::cout << map.get(key) << '\n';
        }
    }
}
