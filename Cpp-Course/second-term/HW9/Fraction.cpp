#include <assert.h>
#include <iostream>
#include <string>
#include <iomanip>
#include "Fraction.h"

Fraction::Fraction(int numerator) : mNumerator(numerator), mDenominator(1)
{}

Fraction::Fraction(int numerator, int denominator) : mNumerator(numerator), mDenominator(denominator)
{
    assert(denominator != 0 && "denominator must be non-zero value");
    simplify();
}

Fraction::Fraction(const Fraction& fraction) = default;

Fraction& Fraction::operator=(const Fraction& copyData)
{
    if (this != &copyData)
    {
        mNumerator = copyData.mNumerator;
        mDenominator = copyData.mDenominator;
    }
    return *this;
}

Fraction& Fraction::operator++()
{
    *this = *this + 1;
    return *this;
}

const Fraction Fraction::operator++(int)
{
    Fraction tmp(*this);
    ++(*this);
    return tmp;
}

Fraction& Fraction::operator--()
{
    *this = *this - 1;
    return *this;
}

const Fraction Fraction::operator--(int)
{
    const Fraction tmp(*this);
    --(*this);
    return tmp;
}

Fraction& Fraction::operator+()
{
    return *this;
}

Fraction Fraction::operator+(const Fraction& fraction) const
{
    Fraction f(mNumerator * fraction.mDenominator + fraction.mNumerator * mDenominator,
               mDenominator * fraction.mDenominator);
    f.simplify();
    return f;
}

Fraction Fraction::operator+(int value) const
{
    return *this + Fraction(value);
}

Fraction& Fraction::operator+=(const Fraction& fraction)
{
    if (fraction.mNumerator != 0)
    {
        *this = *this + fraction;
    }
    return *this;
}

Fraction& Fraction::operator+=(int value)
{
    *this += Fraction(value);
    return *this;
}

Fraction Fraction::operator-()
{
    mNumerator *= -1;
    return *this;
}

Fraction Fraction::operator-(const Fraction& fraction) const
{
    Fraction f(mNumerator * fraction.mDenominator - fraction.mNumerator * mDenominator,
               mDenominator * fraction.mDenominator);
    f.simplify();
    return f;
}

Fraction Fraction::operator-(int value) const
{
    return *this - Fraction(value);
}

Fraction& Fraction::operator-=(const Fraction& fraction)
{
    if (fraction.mNumerator != 0)
    {
        *this = *this - fraction;
    }
    return *this;
}

Fraction& Fraction::operator-=(int value)
{
    *this -= Fraction(value);
    return *this;
}

Fraction Fraction::operator*(const Fraction& fraction) const
{
    Fraction f(mNumerator * fraction.mNumerator, mDenominator * fraction.mDenominator);
    f.simplify();
    return f;
}

Fraction Fraction::operator*(int value) const
{
    return *this * Fraction(value);
}

Fraction& Fraction::operator*=(const Fraction& fraction)
{
    if (!(fraction.mNumerator == 1 && fraction.mDenominator == 1))
    {
        *this = *this * fraction;
    }
    return *this;
}

Fraction& Fraction::operator*=(int value)
{
    *this *= Fraction(value);
    return *this;
}

Fraction Fraction::operator/(const Fraction& fraction) const
{
    assert(fraction.mNumerator != 0 && "divisor is zero");
    Fraction f(mNumerator * fraction.mDenominator, mDenominator * fraction.mNumerator);
    f.simplify();
    return f;
}

Fraction Fraction::operator/(int value) const
{
    return *this / Fraction(value);
}

Fraction& Fraction::operator/=(const Fraction& fraction)
{
    if (!(fraction.mNumerator == 1 && fraction.mDenominator == 1))
    {
        *this = *this / fraction;
    }
    return *this;
}

Fraction& Fraction::operator/=(int value)
{
    *this /= Fraction(value);
    return *this;
}

Fraction Fraction::operator%(int value) const
{
    return Fraction(mNumerator % value, mDenominator % value);
}

Fraction& Fraction::operator%=(int value)
{
    *this = *this % value;
    return *this;
}

bool Fraction::operator>(const Fraction& fraction) const
{
    return mNumerator * fraction.mDenominator > fraction.mNumerator * mDenominator;
}

bool Fraction::operator>(int value) const
{
    return *this > Fraction(value);
}

bool Fraction::operator<(const Fraction& fraction) const
{
    return fraction > *this;
}

bool Fraction::operator<(int value) const
{
    return Fraction(value) > *this;
}

bool Fraction::operator>=(const Fraction& fraction) const
{
    return !(*this < fraction);
}

bool Fraction::operator>=(int value) const
{
    return !(*this < value);
}

bool Fraction::operator<=(const Fraction& fraction) const
{
    return !(*this > fraction);
}

bool Fraction::operator<=(int value) const
{
    return !(*this > value);
}

bool Fraction::operator==(const Fraction& fraction) const
{
    return (*this >= fraction) && (*this <= fraction);
}

bool Fraction::operator==(int value) const
{
    return (*this == Fraction(value));
}

bool Fraction::operator!=(const Fraction& fraction) const
{
    return !(*this == fraction);
}

bool Fraction::operator!=(int value) const
{
    return !(*this == value);
}

bool Fraction::operator!() const
{
    return mNumerator == 0;
}

bool Fraction::operator&&(const Fraction& fraction) const
{
    return !(!(*this) || !fraction);
}

bool Fraction::operator&&(int value) const
{
    return *this && Fraction(value);
}

bool Fraction::operator||(const Fraction& fraction) const
{
    return !(!(*this) && !fraction);
}

bool Fraction::operator||(int value) const
{
    return *this || Fraction(value);
}

Fraction& Fraction::operator~()
{
    mNumerator = ~static_cast<unsigned int>(mNumerator);
    mDenominator = ~static_cast<unsigned int>(mDenominator);
    if (mDenominator == 0)
    {
        mDenominator = 1;
    }
    simplify();
    return *this;
}

Fraction Fraction::operator&(const Fraction& fraction) const
{
    int numerator = static_cast<unsigned int>(mNumerator) & static_cast<unsigned int>(fraction.mNumerator);
    int denominator = static_cast<unsigned int>(mDenominator) & static_cast<unsigned int>(fraction.mDenominator);
    if (denominator == 0)
    {
        denominator = 1;
    }
    return Fraction(numerator, denominator);
}

Fraction Fraction::operator&(int value) const
{
    return *this & Fraction(value);
}

Fraction& Fraction::operator&=(const Fraction& fraction)
{
    *this = (*this & fraction);
    return *this;
}

Fraction& Fraction::operator&=(int value)
{
    *this &= Fraction(value);
    return *this;
}

Fraction Fraction::operator|(const Fraction& fraction) const
{
    int numerator = static_cast<unsigned int>(mNumerator) | static_cast<unsigned int>(fraction.mNumerator);
    int denominator = static_cast<unsigned int>(mDenominator) | static_cast<unsigned int>(fraction.mDenominator);
    if (denominator == 0)
    {
        denominator = 1;
    }
    return Fraction(numerator, denominator);
}

Fraction Fraction::operator|(int value) const
{
    return *this | Fraction(value);
}

Fraction& Fraction::operator|=(const Fraction& fraction)
{
    *this = (*this | fraction);
    return *this;
}

Fraction& Fraction::operator|=(int value)
{
    *this |= Fraction(value);
    return *this;
}

Fraction Fraction::operator^(const Fraction& fraction) const
{
    int numerator = static_cast<unsigned int>(mNumerator) ^static_cast<unsigned int>(fraction.mNumerator);
    int denominator = static_cast<unsigned int>(mDenominator) ^static_cast<unsigned int>(fraction.mDenominator);
    if (denominator == 0)
    {
        denominator = 1;
    }
    return Fraction(numerator, denominator);
}

Fraction Fraction::operator^(int value) const
{
    return *this ^ Fraction(value);
}

Fraction& Fraction::operator^=(const Fraction& fraction)
{
    *this = (*this ^ fraction);
    return *this;
}

Fraction& Fraction::operator^=(int value)
{
    *this ^= Fraction(value);
    return *this;
}

Fraction& Fraction::operator>>=(unsigned int shift)
{
    mNumerator = static_cast<unsigned int>(mNumerator) >> shift;
    mDenominator = static_cast<unsigned int>(mDenominator) >> shift;
    if (mDenominator == 0)
    {
        mDenominator = 1;
    }
    simplify();
    return *this;
}

Fraction& Fraction::operator<<=(unsigned int shift)
{
    mNumerator = static_cast<unsigned int>(mNumerator) << shift;
    mDenominator = static_cast<unsigned int>(mDenominator) << shift;
    if (mDenominator == 0)
    {
        mDenominator = 1;
    }
    simplify();
    return *this;
}

const std::string Fraction::operator*() const
{
    std::string out;
    if (mDenominator == 1 || mNumerator == 0)
    {
        out = std::to_string(mNumerator);
    } else
    {
        out = std::to_string(mNumerator) + "/" + std::to_string(mDenominator);
    }
    return out;
}

Fraction* Fraction::operator->()
{
    return this;
}

const Fraction Fraction::operator->*(int pow)
{
    Fraction tmp = *this;
    if (pow == 0)
    {
        return Fraction(1);
    }
    for (int i = 1; i < abs(pow); i++)
    {
        tmp *= tmp;
    }
    if (pow < 0)
    {
        tmp = tmp.operator->();
    }
    return tmp;
}

Fraction::operator double() const
{
    return static_cast<double>(mNumerator) / mDenominator;
}

int Fraction::operator()() const
{
    return mNumerator / mDenominator;
}

bool Fraction::operator[](const std::function <bool(const Fraction& fraction)>& predicate) const
{
    return predicate(*this);
}

void Fraction::operator,(int precision) const
{
    std::cout << std::setprecision(precision) << static_cast<double > (*this);
}

std::ostream& operator<<(std::ostream& out, const Fraction& fraction)
{
    out << *fraction;
    return out;
}

std::istream& operator>>(std::istream& in, Fraction& fraction)
{
    int numerator = 0;
    bool hasDenominator = false;
    std::string number, fractionString;
    getline(in, fractionString);
    for (char symbol : fractionString)
    {
        if (symbol != '/')
        {
            number += symbol;
        } else
        {
            hasDenominator = true;
            numerator = stoi(number);
            number = "";
        }
    }
    int value = stoi(number);
    if (hasDenominator)
    {
        fraction = Fraction(numerator, value);
    } else
    {
        fraction = Fraction(value);
    }
    fraction.simplify();
    return in;
}

void Fraction::simplify()
{
    int sameFactor = gcd(mNumerator, mDenominator);
    mNumerator /= sameFactor;
    mDenominator /= sameFactor;

    // Make denominator positive
    if (mDenominator < 0)
    {
        mNumerator *= -1;
        mDenominator *= -1;
    }
}

int Fraction::gcd(int a, int b)
{ return b ? gcd(b, a % b) : a; }



// Common operators overloading

Fraction operator+(int value, const Fraction& fraction)
{
    return Fraction(value) + fraction;
}

Fraction operator-(int value, const Fraction& fraction)
{
    return Fraction(value) - fraction;
}

Fraction operator*(int value, const Fraction& fraction)
{
    return Fraction(value) * fraction;
}

Fraction operator/(int value, const Fraction& fraction)
{
    return Fraction(value) / fraction;
}

bool operator>(int value, const Fraction& fraction)
{
    return Fraction(value) > fraction;
}

bool operator>=(int value, const Fraction& fraction)
{
    return Fraction(value) >= fraction;
}

bool operator<(int value, const Fraction& fraction)
{
    return Fraction(value) < fraction;
}

bool operator<=(int value, const Fraction& fraction)
{
    return Fraction(value) <= fraction;
}

bool operator==(int value, const Fraction& fraction)
{
    return Fraction(value) == fraction;
}

bool operator!=(int value, const Fraction& fraction)
{
    return Fraction(value) != fraction;
}

bool operator||(int value, const Fraction& fraction)
{
    return Fraction(value) || fraction;
}

bool operator&&(int value, const Fraction& fraction)
{
    return Fraction(value) && fraction;
}

Fraction operator&(int value, const Fraction& fraction)
{
    return Fraction(value) & fraction;
}

Fraction operator|(int value, const Fraction& fraction)
{
    return Fraction(value) | fraction;
}

Fraction operator^(int value, const Fraction& fraction)
{
    return Fraction(value) ^ fraction;
}

