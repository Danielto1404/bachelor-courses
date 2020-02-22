#include "Array.h"

template<class T>
Array <T>::Array(size_t size, const T& value) :
        mSize(size), mArr(static_cast<T*>(operator new[](mSize * sizeof(T))))
{
    for (int i = 0; i < mSize; i++)
    {
        new(mArr + i) T(value);
    }
}

template<class T>
Array <T>::Array(const Array& copy) :
        mSize(copy.mSize), mArr(static_cast<T*>(operator new[](mSize * sizeof(T))))
{
    for (int i = 0; i < mSize; i++)
    {
        new(mArr + i) T(copy.mArr[i]);
    }
}

template<class T>
Array <T>::~Array()
{
    for (int i = 0; i < mSize; i++)
    {
        mArr[i].~T();
    }
    operator delete[](mArr);
}

template<class T>
Array <T>& Array <T>::operator=(const Array& copy)
{
    if (this != &copy)
    {
        Array(copy).swap(*this);
    }
    return *this;
}

template<class T>
T& Array <T>::operator[](size_t index)
{
    return *(mArr + index);
}

template<class T>
const T Array <T>::operator[](size_t index) const
{
    return *(mArr + index);
}

template<class T>
T* Array <T>::getArray()
{
    return mArr;
}

template<class T>
const T* Array <T>::getArray() const
{
    return mArr;
}

template<class T>
size_t Array <T>::size() const
{
    return mSize;
}

template<class T>
void Array <T>::swap(Array& toSwap)
{
    std::swap(mSize, toSwap.mSize);
    std::swap(mArr, toSwap.mArr);
}

template<class T>
std::ostream& operator<<(std::ostream& out, const Array <T>& arr)
{
    out << "[";
    for (int i = 0; i < arr.size(); i++)
    {
        if (i == arr.size() - 1)
        {
            out << arr[i];
        } else
        {
            out << arr[i] << ", ";
        }
    }
    out << "]";
    return out;
}