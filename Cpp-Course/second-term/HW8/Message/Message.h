#pragma once

#include <string>

class Message
{
public:
    virtual std::string printDetails() const;
    Message(std::string type, std::string name, int amount, std::string description = "---");
    int getResults() const;
protected:
    int mAmount;
    std::string mType;
    std::string mName;
    std::string mDescription;
};

