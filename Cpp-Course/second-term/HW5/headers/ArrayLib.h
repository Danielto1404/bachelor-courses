#pragma once

int **create_array (unsigned n, unsigned m);

void deleteArray (int **arr, unsigned n, unsigned m);

void printArray (int **arr, unsigned n, unsigned m);

bool checkArrayEquals (unsigned n, unsigned m, int **firstArr, int **secondArr);