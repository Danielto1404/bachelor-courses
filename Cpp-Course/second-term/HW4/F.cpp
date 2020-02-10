#include <iostream>
#include <fstream>
#include <map>
#include <vector>

using namespace std;

typedef unsigned int UI;
typedef long long int LL;

pair<LL, LL> varPowers = {0, 0};
vector<string> numbers = {};
int sign;
string expression;
UI pos = 0;
map<pair<LL, LL>, LL> operand;

bool checkPos() {
    return pos < expression.length();
}

void next() {
    pos++;
}

char curChar() {
    return expression[pos];
}

bool isDigit(char c) {
    return c >= '0' && c <= '9';
}

string getNumber() {
    string number;
    while (isDigit(curChar()) && checkPos()) {
        number += curChar();
        next();
    }
    return number;
}

bool testVariable(char c) {
    return c == 'n' || c == 'm';
}

int definePow(char c) {
    if (checkPos()) {
        if (c == '^') {
            next();
            return stoi(getNumber());
        } else if (curChar() == '*') {
            next();
        }
    }
    return 1;
}

void setSign() {
    if (pos == 0 && curChar() != '-') {
        sign = 1;
    } else {
        sign = curChar() == '-' ? -1 : 1;
        next();
    }
}

void addPows(char c) {
    if (c == 'n') {
        next();
        varPowers.first += definePow(curChar());
    } else {
        next();
        varPowers.second += definePow(curChar());
    }
}

LL mulNumbers() {
    if (numbers.empty()) {
        return sign;
    }
    LL res = 1;
    for (const auto &number : numbers) {
        res *= stoi(number);
    }
    return res * sign;
}

void nextToken() {
    varPowers.first = 0;
    varPowers.second = 0;
    numbers = {};
    if (checkPos()) {
        setSign();
        while (curChar() != '-' && curChar() != '+' && checkPos()) {
            if (isDigit(curChar())) {
                numbers.push_back(getNumber());
            } else if (testVariable(curChar())) {
                addPows(curChar());
            } else if (curChar() == '*') {
                next();
            }
        }
    }
}

string parseOnePower(LL power, string variable) {
    if (power == 0) {
        return "";
    }
    if (power == 1) {
        return variable;
    }
    return variable + "^" + to_string(power);
}

string parsePowers(LL first, LL second) {
    return parseOnePower(first, "n") + parseOnePower(second, "m");
}

string show(LL first, LL second, LL k) {
    if (k == 0) {
        return "";
    }
    if (k == 1) {
        // Если степени по 0 то просто плюсуем единичку
        string parse = parsePowers(first, second);
        if (parse.empty()) {
            return "+1";
        }
        return "+" + parse;
    }
    if (k == -1) {
        // Если степени по 0 то просто минусуем единичку
        string parse = parsePowers(first, second);
        if (parse.empty()) {
            return "-1";
        }
        return "-" + parse;
    }
    if (k > 0) {
        return "+" + to_string(k) + parsePowers(first, second);
    } else {
        return to_string(k) + parsePowers(first, second);
    }
}

void init() {
    varPowers.first = 0;
    varPowers.second = 0;
    vector<string> numbers = {};
    pos = 0;
    operand.clear();
}

int main() {
    ifstream in("polynomial.in");
    ofstream out("polynomial.out");
    getline(in, expression);
    UI n;
    n = static_cast<UI>(stoi(expression));
    for (UI i = 0; i < n; i++) {
        getline(in, expression);
        init();
        while (checkPos()) {
            nextToken();
            if (operand.count(varPowers)) {
                LL curValue = operand[varPowers];
                operand[varPowers] = curValue + mulNumbers();
            } else {
                operand[varPowers] = mulNumbers();
            }
        }
        map<pair<LL, LL>, LL>::iterator iterator;
        string expr;
        bool first = true;
        for (iterator = operand.begin(); iterator != operand.end(); iterator++) {
            string curOperand = show((*iterator).first.first, (*iterator).first.second, (*iterator).second);

            // Проверяем нужен ли плюсик в начале строки.
            if (first && !curOperand.empty() && curOperand[0] == '+' && expr.empty()) {
                expr += curOperand.substr(1, curOperand.size());
                first = false;
            } else {
                expr += curOperand;
            }
        }
        // Если все коэфициенты нули то мы возвращаем пустые строки => их сумма тоже пустая строка
        if (expr.empty()) {
            out << "0";
        } else {
            out << expr;
        }
        out << "\n";
    }
    out.close();
}
