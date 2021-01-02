#include <stdio.h>

int main() {
    char input[256];

    printf("Enter flag to check: ");
    scanf("%s", input);
    if (strcmp(input, "f1rst_FLAG") == 0) {
        printf("Yes! Correct flag is %s\n", input);
        return 0;
    }
    printf("Wrong check!\n");
    return 0;
}

// Answer: = f1rst_FLAG
