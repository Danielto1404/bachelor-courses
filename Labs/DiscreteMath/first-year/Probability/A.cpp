#include <iostream>
#include <fstream>

int main ()
{
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);
    std::ifstream in("exam.in");
    std::ofstream out("exam.out");
    unsigned n, k, students, prob;
    in >> k >> n;
    double sum = 0;
    for (unsigned iter = 0; iter < k; ++iter)
    {
        in >> students >> prob;
        sum += students * prob;
    }
    out << sum / (100 * n);
}
