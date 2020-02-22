#include "Message.h"

Message::Message(std::string type, std::string name, int amount, std::string description)
{
    mType = std::move(type);
    mName = std::move(name);
    mDescription = std::move(description);
    mAmount = amount;
}

std::string Message::printDetails() const
{
    return "Type: " + mType + "\nDescription: " + mDescription;
}

int Message::getResults() const
{
    return mAmount;
}
