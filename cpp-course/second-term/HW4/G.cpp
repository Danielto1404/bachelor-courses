#include <iostream>
#include <fstream>

typedef long long LL;

std::ifstream in("reverse.in");
std::ofstream out("reverse.out");

void reverseAndWrite(LL n) {
    if (n > 0) {
        LL cur;
        in >> cur;
        reverseAndWrite(n - 1);
        out << cur << " ";
    }
}

int main() {
    LL n;
    in >> n;
    reverseAndWrite(n);
    return 0;
}
