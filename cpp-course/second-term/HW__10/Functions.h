#pragma once

#include "Array.h"

template<class T, class Comp>
T minimum(Array <T>& array, Comp comp);

template<class T>
void flatten(T& element, std::ostream& out);

template<class T>
void flatten(Array <T>& array, std::ostream& out);

#include "Functions.hpp"
