#pragma once

template<class T>
class LinkedPtr {
public:
    explicit LinkedPtr(T* pointer = nullptr);
    LinkedPtr(LinkedPtr& copyData);
    LinkedPtr(LinkedPtr&& copyData) noexcept;
    explicit LinkedPtr(std::nullptr_t aNullptr);
    ~LinkedPtr();
    LinkedPtr& operator=(LinkedPtr& copyData);
    LinkedPtr& operator=(LinkedPtr&& copyData) noexcept;

    void reset(T* ptr);
    bool unique() const;
    explicit operator bool() const;

    T* get() const;
    T& operator*();
    T* operator->();

    bool operator>(const LinkedPtr& other) const;
    bool operator<(const LinkedPtr& other) const;
    bool operator>=(const LinkedPtr& other) const;
    bool operator<=(const LinkedPtr& other) const;
    bool operator==(const LinkedPtr& other) const;
    bool operator!=(const LinkedPtr& other) const;
private:
    void insert(LinkedPtr& linkedPtr);
    void remove();
    LinkedPtr* mPrev;
    LinkedPtr* mNext;
    T* mPtr;
};

#include "LinkedPointer.hpp"
