#include <iostream>

struct Foo
{
    void say() const
    {
        std::cout << "Foo says: " << msg << "\n";
    }
protected:
    explicit Foo(const char* msg) : msg(msg) {}
private:
    const char* msg;
};

struct publicFoo : Foo
{
    explicit publicFoo(const char* msg) : Foo(msg) {};
};

void foo_says(const Foo& foo)
{
    foo.say();
}

int main()
{
    foo_says(publicFoo("Hello"));
}