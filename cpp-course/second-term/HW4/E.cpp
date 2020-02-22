#include <iostream>
#include <fstream>

typedef long long LL;

LL gcd(LL a, LL b) { return b ? gcd(b, a % b) : a; }

int main() {
    std::ifstream in("gcd.in");
    LL a, b;
    in >> a >> b;
    in.close();
    std::ofstream out("gcd.out");
    out << gcd(a, b);
    out.close();
    return 0;
}
