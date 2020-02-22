#include "Office.h"

std::string Office::printDetails() const
{
    return "Address: " + mAddress + "\nPhone number: " + std::to_string(mPhoneNumber) + "\n";
}

Office::Office(std::string address, int phoneNumber)
{
    mAddress = std::move(address);
    mPhoneNumber = phoneNumber;
}
