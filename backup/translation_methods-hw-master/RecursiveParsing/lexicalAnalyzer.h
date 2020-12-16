#ifndef INC_2LAB_LEXICALANALYZER_H
#define INC_2LAB_LEXICALANALYZER_H

#include <iostream>
enum token{
    NOT, AND, OR, XOR, LPAREN, RPAREN, END, BOOL
};
//  0   1   2       3   4       5       6   7
struct lexicalAnalyzer {
    explicit lexicalAnalyzer(std::string);

    void nextToken();
    token currToken();

    std::string getCurrBool();//

private:
    std::string data;
    std::string currBool;//
    int pointer;
    unsigned long dataSize;
    token currentToken = END;

    void skipBlanks();
    bool eat(std::string);
    bool eatVal();

};

struct analyzing_exception : std::exception{
};


#endif //INC_2LAB_LEXICALANALYZER_H
