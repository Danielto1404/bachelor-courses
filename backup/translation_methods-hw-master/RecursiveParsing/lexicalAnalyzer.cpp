#include "lexicalAnalyzer.h"

lexicalAnalyzer::lexicalAnalyzer(std::string str) : data(str) {
    pointer = 0;
    dataSize = str.length();
    nextToken();
}

void lexicalAnalyzer::skipBlanks() {
    char c;
    while (pointer < dataSize && ((c = data[pointer]) == ' ' || c == '\r' || c == '\n' || c == '\t')) {
        pointer++;
    }
}

bool lexicalAnalyzer::eat(std::string str) {
    unsigned long strSize = str.length();
    if (dataSize < pointer + strSize) return false;
    for (int i = 0; i < strSize; i++) {
        if (str[i] != data[pointer + i])
            return false;
    }
    pointer += strSize;
    return true;
}

bool lexicalAnalyzer::eatVal() {
    char c;
    bool flag = false;
    currBool="";//
    while (dataSize > pointer && ((c = data[pointer]) >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z')) {
        currBool+=data[pointer];//
        pointer++;
        flag = true;
    }
    return flag;
}

void lexicalAnalyzer::nextToken() {
    skipBlanks();
    token answer;
    if (pointer == dataSize) {
        currentToken = END;
        return;
    }
    switch (data[pointer]) {
        case '(':
            if (eat("(")) {
                currentToken = LPAREN;
                return;
            }
        case ')':
            if (eat(")")) {
                currentToken = RPAREN;
                return;
            }
        case 'o':
            if (eat("or")) {
                currentToken = OR;
                return;
            }
        case 'a':
            if (eat("and")) {
                currentToken = AND;
                return;
            }
        case 'n':
            if (eat("not")) {
                currentToken = NOT;
                return;
            }
        case 'x':
            if (eat("xor")) {
                currentToken = XOR;
                return;
            }
        default:
            if (eatVal()) {
                currentToken = BOOL;
                return;
            }
    }
    throw analyzing_exception();
}

token lexicalAnalyzer::currToken() {
    return currentToken;
}

std::string lexicalAnalyzer::getCurrBool() {
    return currBool;
}