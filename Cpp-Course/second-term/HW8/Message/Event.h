#pragma once

#include <iostream>
#include "Message.h"

class Event : public Message
{
public:
    std::string printDetails() const override;
    Event(std::string name, int amountPaid, std::string description = "---");
};


