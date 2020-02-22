#pragma once

class String
{
public:
    explicit String(const char* str = "");
    String(size_t n, char c);
    String(const String& data);
    ~String();

    String& operator=(const String& data);
    void append(const String& other);
    void show() const;
    size_t getSize() const;
private:
    size_t mSize;
    char* mString;
};
