#pragma once

struct A
{
    A() = default;
    A(int x, int y, int z) : mX(x), mY(y), mZ(z) {}
    A& operator=(const A& a) = default;
    A(const A& a) = default;
    friend std::ostream& operator<<(std::ostream& out, const A& a);
private:
    int mX = 0, mY = 0, mZ = 0;
};

std::ostream& operator<<(std::ostream& out, const A& a)
{
    out << "{x=" << a.mX << ",y=" << a.mY << ",z=" << a.mZ << "}";
    return out;
}