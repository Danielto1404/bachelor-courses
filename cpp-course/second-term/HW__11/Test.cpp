#include <iostream>
#include "LinkedPointer.h"
#include "Utils.hpp"

template<class T>
void showUnique(LinkedPtr <T>& p, LinkedPtr <T>& t)
{
    std::cout << "p.unique() ? : " << (p.unique() ? "True" : "False") << "\n";
    std::cout << "t.unique() ? : " << (t.unique() ? "True" : "False") << "\n\n";
}

void test1()
{
    std::cout << "\nТест 1 :\nСоздаем умный укзатель на int LinkedPtr <int> p(new int(5))\n";
    LinkedPtr <int> p(new int(5));
    std::cout << "Получаем данные : *p = " << *p << "\n\n";
    *p = 100;
    std::cout << "Присваиваем p значение 100 : *p = 100\nРезультат : *p = " << *p << "\n\n";
    std::cout << "p.unique()? : " << (p.unique() ? "True" : "False") << "\n\n";
    std::cout << "–––––––––––––––––\n";
}

void test2()
{
    std::cout << "\nТест 2 :\nСоздаем умный укзатель на произвольную структуру\n"
                 "LinkedPtr <A> p(new A())\n\n";
    LinkedPtr <A> p(new A());
    *p = A(1, 2, 4);
    std::cout << "*p = A(1, 2, 4)\ncout << *p : " << *p << "\n\n";
    std::cout << "Создаем еще один указатель : LinkedPtr <A> t(new A(10, 20, 30))\n\n";
    LinkedPtr <A> t(new A(10, 20, 30));
    showUnique(p, t);
    p = t;
    std::cout << "Присваиваем указатель : p = t\n";
    showUnique(p, t);
    p.reset(nullptr);
    std::cout << "p.reset(nullptr)\n";
    showUnique(p, t);
    std::cout << "t data = *t = " << *t << "\n\n";
    std::cout << "–––––––––––––––––\n";
}

void test3()
{
    std::cout << "\nТест 3 :\nСоздаем несколько умныъ укзателей на одну область памяти\n"
                 "LinkedPtr <A> p(new A()), LinkedPtr t(p), LinkedPtr s(t)\n\n";
    LinkedPtr <A> p(new A());
    LinkedPtr t(p);
    LinkedPtr s(t);
    std::cout << "p.unique() ? : " << (p.unique() ? "True" : "False") << "\n";
    std::cout << "t.unique() ? : " << (t.unique() ? "True" : "False") << "\n";
    std::cout << "s.unique() ? : " << (s.unique() ? "True" : "False") << "\n\n";
    std::cout << "Меняем значение у одного : *p = A(239, 30, 622)\n";
    *p = A(239, 30, 622);
    std::cout << "Данные в t : " << *t << "\nДанные в s : " << *s << "\n\n";
    t.reset(new A());
    s.reset(new A(1, 2, 3));
    std::cout << "Сбрасываем t, s :\nt.reset(new A()), s.reset(new A(1, 2, 3))\n";
    std::cout << "p.unique() ? : " << (p.unique() ? "True" : "False") << "\n";
    std::cout << "t.unique() ? : " << (t.unique() ? "True" : "False") << "\n";
    std::cout << "s.unique() ? : " << (s.unique() ? "True" : "False") << "\n\n";
    std::cout << "Данные в t : " << *t << "\nДанные в s : " << *s << "\n\n";
    std::cout << "–––––––––––––––––\n";
}

void test4()
{
    std::cout << "\nТест 4 :\nРаботаем с nullptr :\n\n";
    LinkedPtr <char> p(nullptr);
    LinkedPtr <char> t = p;
    LinkedPtr <char> s(p);
    std::cout << "LinkedPtr <char> p(nullptr)\nLinkedPtr <char> t = p\nLinkedPtr <char> s(p)\n\n";
    std::cout << "p.unique() ? : " << (p.unique() ? "True" : "False") << "\n";
    std::cout << "t.unique() ? : " << (t.unique() ? "True" : "False") << "\n";
    std::cout << "s.unique() ? : " << (s.unique() ? "True" : "False") << "\n\n";
    std::cout << "Check bool operator :\n";
    std::cout << "p ? " << (p ? "True\n" : "False\n");
    t = LinkedPtr <char>(new char('m'));
    LinkedPtr <int> a(nullptr);
    std::cout << "t = 'm'\n";
    std::cout << "t ? " << (t ? "True | " : "False | ");
    std::cout << "We also have checked moving operator '='\n\n";
    std::cout << "Check copy moving semantics\n";
    LinkedPtr r = LinkedPtr <A>(new A(10, 20, 30));
    std::cout << "LinkedPtr r = LinkedPtr <A>(new A(10, 20, 30))\n";
    std::cout << "*r = " << *r << '\n';
    std::cout << "r ? : " << (r ? "True" : "False") << '\n';
    std::cout << "r.unique? : " << (r.unique() ? "True" : "False") << '\n';
    std::cout << "–––––––––––––––––\n\n\n";
}

using namespace std;
int main()
{
    test1();
    test2();
    test3();
    test4();
}
