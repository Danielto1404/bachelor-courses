#include <iostream>
#include <fstream>
#include <cmath>
#include <iomanip>

int main ()
{
    std::ifstream in("shooter.in");
    std::ofstream out("shooter.out");
    unsigned index = 1;
    unsigned n, m, k;
    double prob, allProb, playerProb;
    in >> n >> m >> k;
    for (unsigned iter = 1; iter <= n; ++iter)
    {
        in >> prob;
        prob = pow(1 - prob, m);
        if (iter == k)
        {
            playerProb = prob;
        }
        allProb += prob;
    }
    out << std::fixed << std::setprecision(13) << playerProb / allProb;
}
