#include <assert.h>
#include "parser.h"

std::shared_ptr<GrammarNode> parser::parse(std::string str) {
    auto analyzer = std::make_shared<lexicalAnalyzer>(lexicalAnalyzer(str));
    return Ex1(analyzer);
}

std::shared_ptr<GrammarNode> parser::Ex1(std::shared_ptr<lexicalAnalyzer> a) {
    auto answer = std::make_shared<GrammarNode>(GrammarNode(GrammarNode::Ex1,"expXOR"));
    switch (a.get()->currToken()){
        case NOT:
        case LPAREN:
        case BOOL:
            answer.get()->children.push_back(Eo1(a));
            answer.get()->children.push_back(Ex2(a));
            break;
        default:
            throw parsing_exception("Ex",a.get()->currToken());
    }
    return answer;
}
std::shared_ptr<GrammarNode> parser::Ex2(std::shared_ptr<lexicalAnalyzer> a) {
    auto answer = std::make_shared<GrammarNode>(GrammarNode(GrammarNode::Ex2,"expXOR\'"));
    switch (a.get()->currToken()){
        case XOR:
            answer.get()->children.push_back(std::make_shared<GrammarNode>(GrammarNode(GrammarNode::TERM,"xor")));
            a.get()->nextToken();
            answer.get()->children.push_back(Eo1(a));
            answer.get()->children.push_back(Ex2(a));
            break;
        case END:
        case RPAREN:
            break;
        default:
            throw parsing_exception("Ex\'",a.get()->currToken());
    }
    return answer;
}

std::shared_ptr<GrammarNode> parser::Eo1(std::shared_ptr<lexicalAnalyzer> a) {
    auto answer = std::make_shared<GrammarNode>(GrammarNode(GrammarNode::Eo1,"expOR"));
    switch (a.get()->currToken()){
        case NOT:
        case LPAREN:
        case BOOL:
            answer.get()->children.push_back(Ea1(a));
            answer.get()->children.push_back(Eo2(a));
            break;
        default:
            throw parsing_exception("Eo",a.get()->currToken());
    }
    return answer;
}

std::shared_ptr<GrammarNode> parser::Eo2(std::shared_ptr<lexicalAnalyzer> a) {
    auto answer = std::make_shared<GrammarNode>(GrammarNode(GrammarNode::Eo2,"expOR\'"));
    switch (a.get()->currToken()){
        case OR:
            answer.get()->children.push_back(std::make_shared<GrammarNode>(GrammarNode(GrammarNode::TERM,"or")));
            a.get()->nextToken();
            answer.get()->children.push_back(Ea1(a));
            answer.get()->children.push_back(Eo2(a));
            break;
        case END:
        case RPAREN:
        case XOR:
            break;
        default:
            throw parsing_exception("Eo\'",a.get()->currToken());
    }
    return answer;
}

std::shared_ptr<GrammarNode> parser::Ea1(std::shared_ptr<lexicalAnalyzer> a) {
    auto answer = std::make_shared<GrammarNode>(GrammarNode(GrammarNode::Ea1,"expAND"));
    switch (a.get()->currToken()){
        case NOT:
        case LPAREN:
        case BOOL:
            answer.get()->children.push_back(T(a));
            answer.get()->children.push_back(Ea2(a));
            break;
        default:
            throw parsing_exception("Ea",a.get()->currToken());
    }
    return answer;
}

std::shared_ptr<GrammarNode> parser::Ea2(std::shared_ptr<lexicalAnalyzer> a) {
    auto answer = std::make_shared<GrammarNode>(GrammarNode(GrammarNode::Ea2,"expAND\'"));
    switch (a.get()->currToken()){
        case AND:
            answer.get()->children.push_back(std::make_shared<GrammarNode>(GrammarNode(GrammarNode::TERM,"and")));
            a.get()->nextToken();
            answer.get()->children.push_back(T(a));
            answer.get()->children.push_back(Ea2(a));
            break;
        case END:
        case RPAREN:
        case XOR:
        case OR:
            break;
        default:
            throw parsing_exception("Ea\'",a.get()->currToken());
    }
    return answer;
}

std::shared_ptr<GrammarNode> parser::T(std::shared_ptr<lexicalAnalyzer> a) {
    auto answer = std::make_shared<GrammarNode>(GrammarNode(GrammarNode::T,"TERM"));
    switch (a.get()->currToken()){
        case NOT:
            answer.get()->children.push_back(std::make_shared<GrammarNode>(GrammarNode(GrammarNode::TERM,"not")));
            a.get()->nextToken();
            answer.get()->children.push_back(T(a));
            break;
        case LPAREN:
            answer.get()->children.push_back(std::make_shared<GrammarNode>(GrammarNode(GrammarNode::TERM,"(")));
            a.get()->nextToken();
            answer.get()->children.push_back(Ex1(a));
            assert(a.get()->currToken()==RPAREN);
            answer.get()->children.push_back(std::make_shared<GrammarNode>(GrammarNode(GrammarNode::TERM,")")));
            a.get()->nextToken();
            break;
        case BOOL:
            answer.get()->children.push_back(std::make_shared<GrammarNode>(GrammarNode(GrammarNode::TERM,"bool("+a.get()->getCurrBool() + ")")));
            a.get()->nextToken();
            break;
        default:
            throw parsing_exception("T",a.get()->currToken());
    }
    return answer;
}