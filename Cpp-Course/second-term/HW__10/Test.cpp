#include <iostream>
#include "Functions.h"
#include "Array.h"
#include "Utils.h"

void test1()
{
    std::cout << "\nТест 1 :\nСоздаем массив из 5 элементов : Array<int> A(5, 1) (Контсруктор)\n";
    std::cout << "Результат : ";
    Array A = Array(5, 1);
    std::cout << A << "\n\n";
    const Array <int>& B(A);
    std::cout << "Создаем новый массив : Array<int> B(A) (Конструктор копирования)\n";
    std::cout << "Результат : " << B << "\n\n";
    A = Array <int>(10, -2);
    std::cout << "A = Array<int>(10, -2) (Оператор присваивания)\n";
    std::cout << "Результат : " << A << "\n";
    std::cout << "–––––––––––––––––\n";
}

void test2()
{
    std::cout << "\nТест 2 : \n";
    Array <Array <char>> A = Array(2, Array <char>(2, 'c'));
    std::cout << "Вложенные массивы : Array<Array<char>> A = Array(2, Array(2, 'c'))\n";
    for (int i = 0; i < A.size(); i++) std::cout << "A[" << i << "] = " << A[i] << "\n";
    std::cout << '\n';
    A[1] = Array <char>(10, 'e');
    std::cout << "Изменение элемента вложенных массивов : A[1] = Array<char>(10, 'e')\n";
    for (int i = 0; i < A.size(); i++) std::cout << "A[" << i << "] = " << A[i] << "\n";
    Array D = A;
    std::cout << "\nПроверка оператора присваивания : Array D = A\n";
    std::cout << "Результат : \n";
    for (int i = 0; i < D.size(); i++) std::cout << "D[" << i << "] = " << D[i] << "\n";
    std::cout << "\n";
    A[1][5] = 'K';
    std::cout << "Изменение одного элемента : A[1][5] = 'K'\n";
    for (int i = 0; i < A.size(); i++) std::cout << "A[" << i << "] = " << A[i] << "\n";
    std::cout << "–––––––––––––––––\n";
}

void test3()
{
    std::cout << "\nТест 3 :\nПроверка функции minimum(Array, comp) :\n\n";
    Array <std::string> as = Array <std::string>(10);
    for (int i = 0; i < as.size(); i++)
    {
        as[i] = generateString(randInt(1, 5));
    }
    std::cout << "Минимальный элемент :\ncompMin = (string a, string b) { return a < b }\nМассив : " << as << "\n";
    auto compMin = [](std::string a, std::string b)
    { return a < b; };
    std::cout << "Результат : " << minimum(as, compMin) << "\n\n";
    for (int i = 0; i < as.size(); i++)
    {
        as[i] = generateString(randInt(1, 5));
    }
    std::cout << "Максимальный элемент :\ncompMax = (string a, string b) { return a > b }\nМассив : " << as << "\n";
    auto compMax = [](std::string a, std::string b)
    { return a > b; };
    std::cout << "Результат : " << minimum(as, compMax) << "\n\n";
    Array <int> ai(10);
    for (int i = 0; i < ai.size(); i++)
    {
        ai[i] = randInt(-20, 20);
    }
    std::cout << "Минимальный элемент :\ncompIntMin = (int a, int b) { return a > b }\nМассив : " << ai << "\n";
    auto compIntMin = [](int a, int b)
    { return a < b; };
    std::cout << "Результат : " << minimum(ai, compIntMin) << "\n\n";
    for (int i = 0; i < ai.size(); i++)
    {
        ai[i] = randInt(-20, 20);
    }
    std::cout << "Максимальный элемент :\ncompIntMax = (int a, int b) { return a > b }\nМассив : " << ai << "\n";
    auto compIntMax = [](int a, int b)
    { return a > b; };
    std::cout << "Результат : " << minimum(ai, compIntMax) << "\n";
    std::cout << "–––––––––––––––––\n";
}

void test4()
{
    std::cout << "\nТест 4 : Проверка функции flatten(Array, std::ostream) :\n\n";
    Array <Array <char>> A(3);
    A[0] = Array(2, 'x');
    A[1] = Array(3, 'y');
    A[2] = Array(4, 'z');
    std::cout << "Двойная вложенность :\n"
                 "Array <Array <char>> A(3)\n"
                 "A[0] = Array(2, 'x')\n"
                 "A[1] = Array(3, 'y')\n"
                 "A[2] = Array(4, 'z')\n";
    std::cout << "Результат : ";
    flatten(A, std::cout);
    Array <Array <Array <int>>> B(2);
    B[0] = Array <Array <int>>(2);
    B[1] = Array <Array <int>>(2);
    B[0][0] = Array <int>(2, 1);
    B[0][1] = Array <int>(2, 2);
    B[1][0] = Array <int>(2, 3);
    B[1][1] = Array <int>(2, 4);
    std::cout << "\n\nТройная вложенность :\n"
                 "Array <Array <Array <int>>> B(2)\n"
                 "B[0][0] = Array <int>(2, '1')\n"
                 "B[0][1] = Array <int>(2, '2')\n"
                 "B[1][0] = Array <int>(2, '3')\n"
                 "B[1][1] = Array <int>(2, '4')\n";
    std::cout << "Результат : ";
    flatten(B, std::cout);
    std::cout << "\n–––––––––––––––––\n";
}

void test5()
{
    std::cout << "\nТест 5 : Проверка getArray()\n\n";
    Array A = Array <int>(10);
    const Array B = Array <double>(5, 3.14);
    for (int i = 0; i < A.size(); i++)
    {
        A[i] = randInt(-40, 40);
    }
    std::cout << "A = " << A << "\n\n";
    auto pA = A.getArray();
    pA[5] = 239;
    std::cout << "pA = A.getArray()\n"
                 "pA[5] = 239\n" << "A = " << A << "\n\n";
    std::cout << "const Array B = Array <double>(5, 3.14)\n" << "B = " << B
              << "\npB[1] = 2 : read-only variable is not assignable\n";
    auto pB = B.getArray();
    std::cout << "Res of pB[1] = " << pB[1] << "\n";
    std::cout << "–––––––––––––––––\n";
}

void test6()
{
    std::cout << "\nТест 6 : Проверка Array<** struct / class **>\n\n";
    std::cout << "Array am(2, Array <MyClass>(2, MyClass(239, 30))) = \n";
    Array am(2, Array <MyClass>(2, MyClass(239, 30)));
    std::cout << am;
}

int main()
{
    test1();
    test2();
    test3();
    test4();
    test5();
    test6();
}