#include <iostream>
#include "Student.h"
#include "Person.h"
#include "Date.h"

using namespace std;

int main()
{
    Date d(1, 2, 2000);
    cout << d << endl;
    Person p("Filip", "Wallaert", d, true);
    cout << "\n" << p << endl;
    const Person p2("Eva", "Beyens", Date(21, 8, 1995), false);
    cout << p2 << endl;
    Person* p3 = new Student("Karen", "Verbinnen", Date(13, 5, 1985), false, 1234);
    cout << *p3 << "\n\n";
    delete p3;
    return 0;
}
