#include <stdio.h>

int main() {
    char input[256];
    char serial[] = "f2hwldozg|:wbq";
    int i;

    for (i = 0; i < strlen(serial); i++) {
        printf("%c", (serial[i] - i));
    }

    printf("Enter flag to check: ");
    scanf("%s", input);
    for (i = 0; i < strlen(serial); i++) {
        if (input[i] + i != serial[i]) {
            printf("Wrong position %d!\n", i);
            return 0;
        }
    }
    printf("Yes! Correct flag is %s\n", input);
    return 0;
}

// Answer:= f1fth_is_s0lVd
