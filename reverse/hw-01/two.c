#include <stdio.h>

int main() {
    char input[256];

    printf("Enter flag to check: ");
    scanf("%s", input);
    if (strlen(input) != 12) {
        printf("Wrong length!\n");
        return 0;
    }
    if (strcmp(input, "MicCheck_h4w") <= 0) {
        printf("Wrong check 1!\n");
        return 0;
    }
    if (strcmp(input, "MicCheck_h4y") >= 0) {
        printf("Wrong check 2!\n");
        return 0;
    }
    printf("Yes! Correct flag is %s\n", input);
    return 0;
}

// Answer:= MicCheck_h4x
