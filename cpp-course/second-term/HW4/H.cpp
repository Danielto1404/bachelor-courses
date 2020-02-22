#include <iostream>
#include <set>

using namespace std;

const set<int> correctNumbers = {1, 2, 3, 4, 5, 6, 7, 8, 9};

bool checkSquare(unsigned int row, unsigned int column, int matrix[9][9]) {
    set<int> elements;
    for (unsigned int i = row; i < row + 3; i++) {
        for (unsigned int j = column; j < column + 3; j++) {
            elements.insert(matrix[i][j]);
        }
    }
    return elements == correctNumbers;
}

bool checkLineAndColumn(unsigned int position, int matrix[9][9], bool line) {
    set<int> elements;
    if (line) {
        for (unsigned int i = 0; i < 9; i++) {
            elements.insert(matrix[position][i]);
        }
    } else {
        for (unsigned int i = 0; i < 9; i++) {
            elements.insert(matrix[i][position]);
        }
    }
    return elements == correctNumbers;
}

int main() {
    int matrix[9][9];
    for (unsigned int i = 0; i < 9; i++) {
        for (unsigned int j = 0; j < 9; j++) {
            cin >> matrix[i][j];
        }
    }
    bool check = true;
    for (unsigned int i = 0; i < 9; i++) {
        check = checkLineAndColumn(i, matrix, true) && checkLineAndColumn(i, matrix, false);
        if (!check) {
            cout << "NO";
            return 0;
        }
    }
    for (unsigned int i = 0; i < 3; i++) {
        for (unsigned int j = 0; j < 3; j++) {
            if (!checkSquare(i * 3, j * 3, matrix)) {
                cout << "NO";
                return 0;
            }
        }
    }
    cout << "YES";
}
