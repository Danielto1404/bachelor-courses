#include <iostream>
#include "Fraction.h"

std::string border()
{
    return "\n~~~~~~\n\n";
}

void print(const Fraction& fraction, std::string const& name)
{
    std::cout << name << " : " << fraction << '\n';
}

void expectArithmetic(const Fraction& expected, const Fraction& actual, const std::string& msg)
{
    std::cout << "\n~~~~~~~\n" << msg;
    if (expected == actual)
    {
        std::cout << "\nExpected : " << expected << '\n';
        std::cout << "Actual : " << actual << '\n';
    } else
    {
        std::cout << "\n~~~~~~\nfail : " << msg;
    }
}

void expectBool(bool expected, bool actual, const std::string& msg)
{
    if (expected == actual)
    {
        std::cout << "\n" << border() << msg;
    } else
    {
        std::cout << border() << "fail : " << msg;
    }
}

void setPrev(Fraction& prev, Fraction& cur)
{
    prev = cur;
}

void expectChange(Fraction& before, Fraction& after, std::string const& op)
{
    std::cout << border() << "Before : " << before << '\n';
    std::cout << op << " | after: " << after << '\n';
    setPrev(before, after);
}

bool isBiggerThanOne(const Fraction& fraction)
{
    return fraction > 1;
}

int main()
{
    Fraction a, b(2, 3), c(25, 4), d(10, 110), e;
    std::cin >> a;
    print(a, "a");
    print(b, "b");
    print(c, "c");
    print(d, "d");
    std::cout << border() << "Operator \"=\" check\n";
    e = a;
    print(e, "e");

    expectArithmetic(Fraction(83, 12), b + c, "frac + frac");
    expectArithmetic(Fraction(23, 11), d + 2, "frac + int");
    expectArithmetic(Fraction(6), c - Fraction(1, 4), "frac - frac");
    expectArithmetic(Fraction(-21, 4), 1 - c, "int - frac");
    expectArithmetic(Fraction(10, 2), Fraction() + 5, "frac == int");
    expectArithmetic(Fraction(21, 4), Fraction(5, 2) * Fraction(21, 10), "frac * frac");
    expectArithmetic(Fraction(50, 42), Fraction(5, 2) / Fraction(21, 10), "frac / frac");
    expectArithmetic(Fraction(1), Fraction(5, 2) % 3, "frac % int");
    expectArithmetic(Fraction(12, 11), ++d, "++1/11");
    expectArithmetic(Fraction(1, 11), --d, "--12/11");
    expectArithmetic(Fraction(-4, 3), b -= 2, "b-= 2 (b = 2/3)");
    expectArithmetic(Fraction(41, 8), ~Fraction(122, 23), "~122/23 == 41/8");
    expectArithmetic(Fraction(12, 5), Fraction(132, 45) & Fraction(13, 5), "132/45 & 13/5 = 12/5");
    expectArithmetic(Fraction(20, 177), Fraction(10, 112) ^ Fraction(102, 822), "10/112 ^ 102/822 = 20/177\n");

    expectBool(true, 1 > Fraction(1, 10), "1 > 1/10");
    expectBool(true, 3 < Fraction(10, 3), "3 < 10/3");
    expectBool(true, 3 <= Fraction(27, 9), "3 <= 27/9");
    expectBool(false, 3 >= Fraction(27, 4), "3 >= 27/4 - false");
    expectBool(true, 10 == Fraction(100, 10), "10 == 100/10");
    expectBool(false, 10 != Fraction(100, 10), "10 != 100/10 - false");
    expectBool(true, Fraction(11, 12) || Fraction(10, 4), "11/12 || 10/4 - true");
    expectBool(false, Fraction(0) || 0, "Fraction(0) || 0 - false");
    expectBool(true, Fraction(11, 12) && Fraction(10, 4), "11/2 && 10/4 - true");
    expectBool(false, Fraction(0) && Fraction(10, 4), "Fraction(0) && 10/4 - false");
    expectBool(true, !Fraction(0, 10), "!0/10 = true");
    expectBool(false, !Fraction(1023 / 23), "!1023/23 = false");

    Fraction cur = Fraction(11, 17);
    Fraction prev(cur);
    expectChange(prev, ++cur, "++cur");
    expectChange(prev, --cur, "--cur");
    expectChange(prev, cur -= Fraction(10, 3), "cur -= 10/3");
    expectChange(prev, cur += Fraction(12, 5), "cur += 12/5");
    expectChange(prev, cur *= Fraction(255, 3), "cur *= 255/3");
    expectChange(prev, cur /= Fraction(11, 192), "cur /= 11/192");
    expectChange(prev, cur += 3, "cur += 3");
    expectChange(prev, cur -= 10, "cur -= 10");
    expectChange(prev, cur *= 13, "cur *= 13");
    expectChange(prev, cur /= 100, "cur /= 100");
    expectChange(prev, cur ^= 10, "cur ^= 10");
    expectChange(prev, cur |= 190, "cur |= 190");
    expectChange(prev, cur &= 11, "cur &= 11");
    expectChange(prev, ~cur, "cur = ~cur");
    expectChange(prev, cur %= 4, "cur %= 4");
    expectChange(prev, cur += Fraction(11, 17), "cur += 10/3");
    expectChange(prev, cur <<= 2, "cur <<= 2");
    expectChange(prev, cur >>= 3, "cur >>= 3");

    std::cout << border() << "Operator-> return *this : " << d.operator->() << '\n';
    std::cout << border() << "Operator* return string value of fraction : " << d.operator*() << '\n';

    double frac = Fraction(10, 3);
    std::cout << border() << "double frac = Fraction(10, 3) | frac = " << frac << '\n';
    std::cout << border() << "Result in INT fraction(10,3)() = " << Fraction(10, 3)() << '\n';
    std::cout << border() << "Predicate usage : fraction(10, 9)[isBiggerThanOne] = "
              << (Fraction(10, 9)[isBiggerThanOne] ? "true\n" : "false\n");
    std::cout << border() << "Precision print operator \",\" Fraction(10,3), 10 = ";
    Fraction(10, 3), 10;
    std::cout << '\n';
    return 0;
}