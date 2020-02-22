#include <iostream>
#include <string>

using namespace std;

typedef unsigned long long UL;

string generateAndCount(UL len, unsigned int repeat, long &count) {
    if (repeat) {
        if (len) {
            string nine;
            string one = "1";
            for (size_t i = 0; i < len; i++) {
                nine += "9";
            }
            string mix = nine + " " + one + " ";
            string decomposition;
            for (size_t i = 0; i < repeat; i++) {
                decomposition += mix;
            }
            count += 2 * repeat;
            return decomposition;
        }
        count++;
        return to_string(repeat);
    }
    return "";
}

int main() {
    string number;
    long count = 0;
    getline(cin, number);
    string ans;
    UL index = number.length() - 1;
    UL delta = number.length() - 1;
    while (true) {
        unsigned int digit = (unsigned int) number[delta - index] - '0';
        ans += generateAndCount(index, digit, count);
        if (!index) {
            break;
        }
        index--;
    }
    cout << count << "\n";
    cout << ans;
    return 0;
}
