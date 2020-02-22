#pragma once

#include "Date.h"
#include <iostream>

class Person
{
public:
    Person(std::string mFirstName, std::string mSecondName, Date mBirthday, bool mSex);

    friend std::ostream& operator<<(std::ostream& out, const Person& at);

    Date getBirthday() const;
    std::string getFirstName() const;
    std::string getSecondName() const;

    void setBirthDay(Date date);
    void setFirstName(std::string word);
    void setSecondName(std::string word);

protected:
    virtual std::string getInfo() const;

private:
    Date mBirthday;
    bool mSex;
    std::string mFirstName;
    std::string mSecondName;
};