#pragma once

class Fraction
{
public:
    explicit Fraction(int numerator = 0);
    Fraction(int numerator, int denominator);
    Fraction(const Fraction& fraction);

    Fraction& operator=(const Fraction& copyData);

    // Arithmetic and assigment operators
    Fraction& operator++();
    const Fraction operator++(int);

    Fraction& operator--();
    const Fraction operator--(int);

    Fraction& operator+();
    Fraction operator+(const Fraction& fraction) const;
    Fraction operator+(int value) const;
    Fraction& operator+=(const Fraction& fraction);
    Fraction& operator+=(int value);

    Fraction operator-();
    Fraction operator-(const Fraction& fraction) const;
    Fraction operator-(int value) const;
    Fraction& operator-=(const Fraction& fraction);
    Fraction& operator-=(int value);

    Fraction operator*(const Fraction& fraction) const;
    Fraction operator*(int value) const;
    Fraction& operator*=(const Fraction& fraction);
    Fraction& operator*=(int value);

    Fraction operator/(const Fraction& fraction) const;
    Fraction operator/(int value) const;
    Fraction& operator/=(const Fraction& fraction);
    Fraction& operator/=(int value);

    Fraction operator%(int value) const;
    Fraction& operator%=(int value);

    // Comparison operators
    bool operator>(const Fraction& fraction) const;
    bool operator>(int value) const;

    bool operator<(const Fraction& fraction) const;
    bool operator<(int value) const;

    bool operator>=(const Fraction& fraction) const;
    bool operator>=(int value) const;

    bool operator<=(const Fraction& fraction) const;
    bool operator<=(int value) const;

    bool operator==(const Fraction& fraction) const;
    bool operator==(int value) const;

    bool operator!=(const Fraction& fraction) const;
    bool operator!=(int value) const;

    // Bool (logical operators)
    bool operator!() const;

    bool operator||(const Fraction& fraction) const;
    bool operator||(int value) const;

    bool operator&&(const Fraction& fraction) const;
    bool operator&&(int value) const;

    // Binary operators
    Fraction& operator~();

    Fraction operator&(const Fraction& fraction) const;
    Fraction operator&(int value) const;
    Fraction& operator&=(const Fraction& fraction);
    Fraction& operator&=(int value);

    Fraction operator|(const Fraction& fraction) const;
    Fraction operator|(int value) const;
    Fraction& operator|=(const Fraction& fraction);
    Fraction& operator|=(int value);

    Fraction operator^(const Fraction& fraction) const;
    Fraction operator^(int value) const;
    Fraction& operator^=(const Fraction& fraction);
    Fraction& operator^=(int value);

    Fraction& operator<<=(unsigned int shift);
    Fraction& operator>>=(unsigned int shift);

    // My own functions
    operator double() const;
    const std::string operator*() const;
    Fraction* operator->();
    const Fraction operator->*(int pow);

    int operator()() const;
    bool operator[](const std::function <bool(const Fraction& fraction)>& predicate) const;
    void operator,(int precision) const;

    friend std::ostream& operator<<(std::ostream& out, const Fraction& fraction);
    friend std::istream& operator>>(std::istream& in, Fraction& fraction);

private:

    int mNumerator = 0;
    int mDenominator = 1;
    void simplify();
    static int gcd(int a, int b);
};

// If int value is left operand //
Fraction operator+(int value, const Fraction& fraction);
Fraction operator-(int value, const Fraction& fraction);
Fraction operator*(int value, const Fraction& fraction);
Fraction operator/(int value, const Fraction& fraction);

bool operator>(int value, const Fraction& fraction);
bool operator>=(int value, const Fraction& fraction);

bool operator<(int value, const Fraction& fraction);
bool operator<=(int value, const Fraction& fraction);

bool operator==(int value, const Fraction& fraction);
bool operator!=(int value, const Fraction& fraction);

bool operator||(int value, const Fraction& fraction);
bool operator&&(int value, const Fraction& fraction);

Fraction operator&(int value, const Fraction& fraction);
Fraction operator|(int value, const Fraction& fraction);
Fraction operator^(int value, const Fraction& fraction);

