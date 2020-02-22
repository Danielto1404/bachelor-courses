#include "Person.h"

Person::Person(std::string mFirstName, std::string mSecondName, Date mBirthday, bool mSex) :
        mBirthday(mBirthday), mFirstName(std::move(mFirstName)),
        mSecondName(std::move(mSecondName)), mSex(mSex) {}

std::string Person::getInfo() const
{
    return mSecondName + " " + mFirstName + ": " + (mSex ? "male" : "female") + " born on " + mBirthday.getInfo();
}

std::ostream& operator<<(std::ostream& out, const Person& person)
{
    out << person.getInfo();
    return out;
}

Date Person::getBirthday() const
{
    return mBirthday;
}

std::string Person::getFirstName() const
{
    return mFirstName;
}

std::string Person::getSecondName() const
{
    return mSecondName;
}

void Person::setBirthDay(Date date)
{
    mBirthday = date;
}

void Person::setFirstName(std::string word)
{
    mFirstName = std::move(word);
}

void Person::setSecondName(std::string word)
{
    mSecondName = std::move(word);
}