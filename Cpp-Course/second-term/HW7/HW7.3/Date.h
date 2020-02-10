#pragma once

#include <iostream>

class Date
{
public:
    Date(int day, int month, int year);

    int getDay() const;
    int getMonth() const;
    int getYear() const;

    void setDay(int day);
    void setMonth(int month);
    void setYear(int year);

    std::string getInfo() const;
    friend std::ostream& operator<<(std::ostream& os, const Date& at);
private:
    int mDay;
    int mMonth;
    int mYear;
};