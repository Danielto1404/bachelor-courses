#include <iostream>

int main ()
{
    std::ios_base::sync_with_stdio(false);
    std::cin.tie(nullptr);
    int price, lines;
    double E = 0;
    freopen("lottery.in", "r", stdin);
    freopen("lottery.out", "w", stdout);
    std::cin >> price >> lines;
    int elNumbers, prices[lines + 1];
    prices[0] = 0;
    double winProb = 1;
    for (unsigned line = 1; line <= lines; ++line)
    {
        std::cin >> elNumbers >> prices[line];
        double lostProb = (elNumbers - 1.0) / elNumbers;
        E += winProb * lostProb * prices[line - 1];
        winProb /= elNumbers;
    }
    std::cout << price - (E + winProb * prices[lines]);
}
