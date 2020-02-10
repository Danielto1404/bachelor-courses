#pragma once

#include <string>

class Office
{
public:
    Office() = default;
    Office(std::string address, int phoneNumber);
    std::string printDetails() const;
private:
    std::string mAddress;
    int mPhoneNumber {};
};
