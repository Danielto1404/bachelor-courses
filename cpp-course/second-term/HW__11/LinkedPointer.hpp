#pragma once

#include "LinkedPointer.h"

template<class T>
LinkedPtr <T>::LinkedPtr(T* pointer) : mPtr(pointer), mPrev(this), mNext(this) {}

template<class T>
LinkedPtr <T>::LinkedPtr(LinkedPtr& copyData) : mPtr(copyData.mPtr), mPrev(this), mNext(this)
{
    if (copyData)
        insert(copyData);
}

template<class T>
LinkedPtr <T>::LinkedPtr(LinkedPtr&& copyData) noexcept: mPtr(copyData), mPrev(this), mNext(this)
{
    copyData.mPtr = nullptr;
}

template <class T>
LinkedPtr <T>::LinkedPtr(std::nullptr_t aNullptr) : mPtr(aNullptr), mPrev(this), mNext(this) {}

template<class T>
LinkedPtr <T>& LinkedPtr <T>::operator=(LinkedPtr& copyData)
{
    if (this != &copyData)
    {
        reset(copyData.mPtr);
        insert(copyData);
    }
    return *this;
}

template<class T>
LinkedPtr <T>& LinkedPtr <T>::operator=(LinkedPtr&& copyData) noexcept
{
    if (this != &copyData)
    {
        reset(copyData.mPtr);
        copyData.mPtr = nullptr;
    }
    return *this;
}

template<class T>
LinkedPtr <T>::~LinkedPtr()
{
    if (mPtr)
        reset(nullptr);
}

template<class T>
void LinkedPtr <T>::reset(T* ptr)
{
    if (unique())
        delete mPtr;
    else
    {
        remove();
    }
    mNext = mPrev = this;
    mPtr = ptr;
}

template<class T>
bool LinkedPtr <T>::unique() const
{
    return this == mNext && this == mPrev;
}

template<class T>
T* LinkedPtr <T>::get() const
{
    return mPtr;
}

template<class T>
LinkedPtr <T>::operator bool() const
{
    return mPtr != nullptr;
}

template<class T>
T* LinkedPtr <T>::operator->()
{
    return this;
}

template<class T>
T& LinkedPtr <T>::operator*()
{
    return *mPtr;
}

template<class T>
void LinkedPtr <T>::insert(LinkedPtr& linkedPtr)
{
    if (linkedPtr.unique())
    {
        linkedPtr.mNext = linkedPtr.mPrev = this;
        mNext = mPrev = &linkedPtr;
    } else
    {
        mNext = linkedPtr.mNext;
        mPrev = &linkedPtr;
        linkedPtr.mNext = this;
    }
}

template<class T>
void LinkedPtr <T>::remove()
{
    mPrev->mNext = mNext;
    mNext->mPrev = mPrev;
}

template<class T>
bool LinkedPtr <T>::operator>(const LinkedPtr& other) const
{
    return mPtr > other.mPtr;
}

template<class T>
bool LinkedPtr <T>::operator<(const LinkedPtr& other) const
{
    return other > *this;
}

template<class T>
bool LinkedPtr <T>::operator>=(const LinkedPtr& other) const
{
    return !(*this < other);
}

template<class T>
bool LinkedPtr <T>::operator<=(const LinkedPtr& other) const
{
    return !(*this > other);
}

template<class T>
bool LinkedPtr <T>::operator==(const LinkedPtr& other) const
{
    return !(other < *this) && !(other > this);
}

template<class T>
bool LinkedPtr <T>::operator!=(const LinkedPtr& other) const
{
    return !(*this == other);
}
