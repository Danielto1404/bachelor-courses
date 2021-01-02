#include <stdio.h>

int get_char_code_at_position(char * string, int position) {
    return (int) (string[position - 1]);
}

int main() {
    char input[256];

    printf("Enter flag to check: ");
    scanf("%s", input);
    if (strlen(input) != 17) {
        printf("Wrong length!\n");
        return 0;
    }
    if (atoi(input) != 333) {
        printf("Wrong check 1!\n");
        return 0;
    }
    if (get_char_code_at_position(input, 5) != 'o') { printf("No \"o1\"!\n"); return 0; }
    if (get_char_code_at_position(input, 7) != 'o') { printf("No \"o2\"!\n"); return 0; }
    if (get_char_code_at_position(input, 11) != 'o') { printf("No \"o3\"!\n"); return 0; }

    if (get_char_code_at_position(input, 9) != get_char_code_at_position(input, 13)) { printf("Wrong check 2!\n"); return 0; }
    if (get_char_code_at_position(input, 13) != get_char_code_at_position(input, 15)) { printf("Wrong check 3!\n"); return 0; }
    if (get_char_code_at_position(input, 17) != get_char_code_at_position(input, 9)) { printf("Wrong check 4!\n"); return 0; }

    if (get_char_code_at_position(input, 4) != get_char_code_at_position(input, 6)) { printf("Wrong check 5!\n"); return 0; }
    if (get_char_code_at_position(input, 4) != get_char_code_at_position(input, 8)) { printf("Wrong check 6!\n"); return 0; }
    if (get_char_code_at_position(input, 4) != get_char_code_at_position(input, 10)) { printf("Wrong check 7!\n"); return 0; }
    if (get_char_code_at_position(input, 4) != get_char_code_at_position(input, 12)) { printf("Wrong check 8!\n"); return 0; }
    if (get_char_code_at_position(input, 4) != get_char_code_at_position(input, 14)) { printf("Wrong check 9!\n"); return 0; }
    if (get_char_code_at_position(input, 4) != get_char_code_at_position(input, 16)) { printf("Wrong check 10!\n"); return 0; }

    if (get_char_code_at_position(input, 16) != 'g') { printf("No \"g\"!\n"); return 0; }
    if (get_char_code_at_position(input, 9) != '0') { printf("No zero!\n"); return 0; }

    printf("Yes! Correct flag is %s\n", input);
    return 0;
}

// Answer:= 333gogog0gog0g0g0
