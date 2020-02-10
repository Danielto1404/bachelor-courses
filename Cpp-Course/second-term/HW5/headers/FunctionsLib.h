#pragma once

#include <ctime>

void swap_min (int* m[], unsigned rows, unsigned cols);

char* resize (const char* str, unsigned size, unsigned new_size);

char* getline ();

unsigned randUnsigned (unsigned bound);

char getRandomChar ();

bool isEnd (char c);

void fail (unsigned testNumber);

void border ();

void showTime (clock_t time);

void showAndCountTests (unsigned &testPassedCount, unsigned numberOfTest, unsigned freq);

void testSwapMin (unsigned numberOfTests);

void testResize (unsigned numberOfTests);

