#include <iostream>
#include <map>
#include <set>
#include <fstream>
#include <vector>
#include <string>

using namespace std;

typedef unsigned int UI;

int main() {
    ifstream in("broken-keyboard.in");
    ofstream out("broken-keyboard.out");
    string s;
    getline(in, s);
    UI n = stoi(s);
    set<char> letters;
    vector<string> queries;
    for (UI i = 0; i < n; i++) {
        getline(in, s);
        queries.push_back(s);
    }
    string prev = queries[0];
    string cur;
    UI index = 0;
    out << "YES" << "\n";
    for (UI i = 1; i < n; i++) {
        cur = queries[i];
        index = 0;
        letters = {};
        while (!letters.count(cur[index]) && index < cur.length()) {
            letters.insert(cur[index]);
            index++;
        }
        if (index == cur.length()) {
            out << "YES" << "\n";
        } else {
            if (index > prev.length()) {
                out << "YES" << "\n";
            } else {
                if (prev.substr(0, index) < cur.substr(0, index)) {
                    out << "YES" << "\n";
                } else {
                    out << "NO" << "\n";
                }
            }
        }
        prev = cur;
    }
    out.close();
}
