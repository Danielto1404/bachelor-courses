#pragma once

template<class T, class Comp>
T minimum(Array <T>& array, Comp comp)
{
    assert(array.size() > 0);
    T min = array[0];
    for (int i = 1; i < array.size(); i++)
    {
        if (comp(array[i], min))
            min = array[i];
    }
    return min;
}

template<class T>
void flatten(T& element, std::ostream& out)
{
    out << element << " ";
}

template<class T>
void flatten(Array <T>& array, std::ostream& out)
{
    for (int i = 0; i < array.size(); i++)
    {
        flatten(array[i], out);
    }
}
