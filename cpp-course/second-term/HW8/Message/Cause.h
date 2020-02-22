#pragma once

#include <iostream>
#include "Message.h"

class Cause : public Message
{
public:
    std::string printDetails() const override;
    Cause(std::string name, int amountAwarded, std::string description = "---");
};

