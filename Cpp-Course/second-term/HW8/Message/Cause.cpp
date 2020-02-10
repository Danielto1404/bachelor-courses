#include <utility>
#include "Cause.h"

std::string Cause::printDetails() const
{
    return "Cause's name: " + mName + "\nDescription: " + mDescription + "\nMoney awarded: " + std::to_string(mAmount);
}

Cause::Cause(std::string name, int amountAwarded, std::string description) :
        Message::Message("cause", std::move(name), amountAwarded, std::move(description)) {}
