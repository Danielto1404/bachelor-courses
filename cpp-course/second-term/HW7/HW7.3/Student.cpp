#include <utility>

#include "Student.h"

Student::Student(std::string firstName, std::string secondName, Date birthday, bool isMale, int mStudentNumber)
        : Person(std::move(firstName), std::move(secondName), birthday, isMale), mStudentNumber(mStudentNumber)
{}

std::string Student::getInfo() const
{
    return Person::getInfo() + ", student number: " + std::to_string(mStudentNumber);
}

int Student::getStudentNumber() const
{
    return mStudentNumber;
}

void Student::setStudentNumber(int number)
{
    mStudentNumber = number;
}
