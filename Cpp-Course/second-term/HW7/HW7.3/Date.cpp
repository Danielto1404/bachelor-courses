#include "Date.h"
#include <string>

Date::Date(int day, int month, int year)
{
    mDay = day;
    mMonth = month;
    mYear = year;
}

std::string Date::getInfo() const
{
    return std::to_string(mDay) + "/" + std::to_string(mMonth) + "/" + std::to_string(mYear);
}

std::ostream& operator<<(std::ostream& out, const Date& date)
{
    out << date.getInfo();
    return out;
}

int Date::getDay() const
{
    return mDay;
}

int Date::getMonth() const
{
    return mMonth;
}

int Date::getYear() const
{
    return mYear;
}

void Date::setDay(int day)
{
    mDay = day;
}

void Date::setMonth(int month)
{
    mMonth = month;
}

void Date::setYear(int year)
{
    mYear = year;
}

