#pragma once

template<class T>
class Array
{
public:
    explicit Array(size_t size = 0, const T& value = T());
    Array(const Array& copy);
    ~Array();

    Array& operator=(const Array& copy);
    T& operator[](size_t index);
    const T operator[](size_t index) const;

    T* getArray();
    const T* getArray() const;
    size_t size() const;
private:
    size_t mSize;
    T* mArr;
    void swap(Array& toSwap);
};

#include "Array.hpp"
