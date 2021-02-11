package calculator;

import calculator.nodes.*;

public class CalculatorParser {

    private CalculatorLexer lexer;
    
    public CalculatorParser() { }

    public CalculatorParser(CalculatorLexer lexer) {
        this.lexer = lexer;
        lexer.nextToken();
    }
    
    public static class ParseException extends RuntimeException {
        public ParseException(String message) {
            super(message);
        }
    }

    private void skipToken(final Rule expected) {
        Rule actual = lexer.token.rule;
        if (expected != actual) {
            throw new ParseException("Illegal token " + actual.name() + ", expected " + expected.name());
        }
        lexer.nextToken();
    }
    
    public StartNode parse(String input) {
        lexer = new CalculatorLexer(input);
        lexer.nextToken();
        return getParsedTree();
    }

    public StartNode getParsedTree() {
        StartNode tree = startTree();
        skipToken(Rule.EOF);
        return tree;
    }
    
    
    public StartNode startTree() {
        StartNode node = new StartNode();
        switch (lexer.token.rule) {
            
            case NOT, MINUS, NUMBER, OPEN, TAN -> {
                
                OrExprNode orExpr = orExprTree(); 
                node.addChild(orExpr);

                { node.val = orExpr.val; }
                return node;
            }
                
   
            default -> throw new ParseException("Unresolved token:" + lexer.token.rule +
                    " at position: " + (lexer.getPosition() - lexer.token.text.length()));
        }
    }

    
    public OrExprNode orExprTree() {
        OrExprNode node = new OrExprNode();
        switch (lexer.token.rule) {
            
            case NOT, MINUS, NUMBER, OPEN, TAN -> {
                
                XorExprNode xorExpr = xorExprTree(); 
                node.addChild(xorExpr);

                OrExprRestNode orExprRest = orExprRestTree(xorExpr.val); 
                node.addChild(orExprRest);

                { node.val = orExprRest.val; }
                return node;
            }
                
   
            default -> throw new ParseException("Unresolved token:" + lexer.token.rule +
                    " at position: " + (lexer.getPosition() - lexer.token.text.length()));
        }
    }

    
    public OrExprRestNode orExprRestTree(int acc) {
        OrExprRestNode node = new OrExprRestNode();
        switch (lexer.token.rule) {
            
            case OR -> {
                
                ORNode OR = ORTree(); 
                node.addChild(OR);

                XorExprNode xorExpr = xorExprTree(); 
                node.addChild(xorExpr);

                OrExprRestNode orExprRest = orExprRestTree(xorExpr.val | acc); 
                node.addChild(orExprRest);

                { node.val = orExprRest.val; }
                return node;
            }
                
 
            case EOF, CLOSE -> {
                { node.val = acc; }
                return node;
            }
                
   
            default -> throw new ParseException("Unresolved token:" + lexer.token.rule +
                    " at position: " + (lexer.getPosition() - lexer.token.text.length()));
        }
    }

    
    public XorExprNode xorExprTree() {
        XorExprNode node = new XorExprNode();
        switch (lexer.token.rule) {
            
            case NOT, MINUS, NUMBER, OPEN, TAN -> {
                
                AndExprNode andExpr = andExprTree(); 
                node.addChild(andExpr);

                XorExprRestNode xorExprRest = xorExprRestTree(andExpr.val); 
                node.addChild(xorExprRest);

                { node.val = xorExprRest.val; }
                return node;
            }
                
   
            default -> throw new ParseException("Unresolved token:" + lexer.token.rule +
                    " at position: " + (lexer.getPosition() - lexer.token.text.length()));
        }
    }

    
    public XorExprRestNode xorExprRestTree(int acc) {
        XorExprRestNode node = new XorExprRestNode();
        switch (lexer.token.rule) {
            
            case XOR -> {
                
                XORNode XOR = XORTree(); 
                node.addChild(XOR);

                AndExprNode andExpr = andExprTree(); 
                node.addChild(andExpr);

                XorExprRestNode xorExprRest = xorExprRestTree(andExpr.val ^ acc); 
                node.addChild(xorExprRest);

                { node.val = xorExprRest.val; }
                return node;
            }
                
 
            case OR, EOF, CLOSE -> {
                { node.val = acc; }
                return node;
            }
                
   
            default -> throw new ParseException("Unresolved token:" + lexer.token.rule +
                    " at position: " + (lexer.getPosition() - lexer.token.text.length()));
        }
    }

    
    public AndExprNode andExprTree() {
        AndExprNode node = new AndExprNode();
        switch (lexer.token.rule) {
            
            case NOT, MINUS, NUMBER, OPEN, TAN -> {
                
                AddSubExprNode addSubExpr = addSubExprTree(); 
                node.addChild(addSubExpr);

                AndExprRestNode andExprRest = andExprRestTree(addSubExpr.val); 
                node.addChild(andExprRest);

                { node.val = andExprRest.val; }
                return node;
            }
                
   
            default -> throw new ParseException("Unresolved token:" + lexer.token.rule +
                    " at position: " + (lexer.getPosition() - lexer.token.text.length()));
        }
    }

    
    public AndExprRestNode andExprRestTree(int acc) {
        AndExprRestNode node = new AndExprRestNode();
        switch (lexer.token.rule) {
            
            case AND -> {
                
                ANDNode AND = ANDTree(); 
                node.addChild(AND);

                AddSubExprNode addSubExpr = addSubExprTree(); 
                node.addChild(addSubExpr);

                AndExprRestNode andExprRest = andExprRestTree(addSubExpr.val & acc); 
                node.addChild(andExprRest);

                { node.val = andExprRest.val; }
                return node;
            }
                
 
            case XOR, OR, EOF, CLOSE -> {
                { node.val = acc; }
                return node;
            }
                
   
            default -> throw new ParseException("Unresolved token:" + lexer.token.rule +
                    " at position: " + (lexer.getPosition() - lexer.token.text.length()));
        }
    }

    
    public AddSubExprNode addSubExprTree() {
        AddSubExprNode node = new AddSubExprNode();
        switch (lexer.token.rule) {
            
            case NOT, MINUS, NUMBER, OPEN, TAN -> {
                
                MulDivExprNode mulDivExpr = mulDivExprTree(); 
                node.addChild(mulDivExpr);

                AddSubExprRestNode addSubExprRest = addSubExprRestTree(mulDivExpr.val); 
                node.addChild(addSubExprRest);

                { node.val = addSubExprRest.val; }
                return node;
            }
                
   
            default -> throw new ParseException("Unresolved token:" + lexer.token.rule +
                    " at position: " + (lexer.getPosition() - lexer.token.text.length()));
        }
    }

    
    public AddSubExprRestNode addSubExprRestTree(int acc) {
        AddSubExprRestNode node = new AddSubExprRestNode();
        switch (lexer.token.rule) {
            
            case PLUS -> {
                
                PLUSNode PLUS = PLUSTree(); 
                node.addChild(PLUS);

                MulDivExprNode mulDivExpr = mulDivExprTree(); 
                node.addChild(mulDivExpr);

                AddSubExprRestNode addSubExprRest = addSubExprRestTree(mulDivExpr.val + acc); 
                node.addChild(addSubExprRest);

                { node.val = addSubExprRest.val; }
                return node;
            }
                
 
            case MINUS -> {
                
                MINUSNode MINUS = MINUSTree(); 
                node.addChild(MINUS);

                MulDivExprNode mulDivExpr = mulDivExprTree(); 
                node.addChild(mulDivExpr);

                AddSubExprRestNode addSubExprRest = addSubExprRestTree(acc - mulDivExpr.val); 
                node.addChild(addSubExprRest);

                { node.val = addSubExprRest.val; }
                return node;
            }
                
 
            case AND, XOR, OR, EOF, CLOSE -> {
                { node.val = acc; }
                return node;
            }
                
   
            default -> throw new ParseException("Unresolved token:" + lexer.token.rule +
                    " at position: " + (lexer.getPosition() - lexer.token.text.length()));
        }
    }

    
    public MulDivExprNode mulDivExprTree() {
        MulDivExprNode node = new MulDivExprNode();
        switch (lexer.token.rule) {
            
            case NOT, MINUS, NUMBER, OPEN, TAN -> {
                
                ExprNotNode exprNot = exprNotTree(); 
                node.addChild(exprNot);

                MulDivExprRestNode mulDivExprRest = mulDivExprRestTree(exprNot.val); 
                node.addChild(mulDivExprRest);

                { node.val = mulDivExprRest.val; }
                return node;
            }
                
   
            default -> throw new ParseException("Unresolved token:" + lexer.token.rule +
                    " at position: " + (lexer.getPosition() - lexer.token.text.length()));
        }
    }

    
    public MulDivExprRestNode mulDivExprRestTree(int acc) {
        MulDivExprRestNode node = new MulDivExprRestNode();
        switch (lexer.token.rule) {
            
            case MULTIPLY -> {
                
                MULTIPLYNode MULTIPLY = MULTIPLYTree(); 
                node.addChild(MULTIPLY);

                ExprNotNode exprNot = exprNotTree(); 
                node.addChild(exprNot);

                MulDivExprRestNode mulDivExprRest = mulDivExprRestTree(acc * exprNot.val); 
                node.addChild(mulDivExprRest);

                { node.val = mulDivExprRest.val; }
                return node;
            }
                
 
            case DIVIDE -> {
                
                DIVIDENode DIVIDE = DIVIDETree(); 
                node.addChild(DIVIDE);

                ExprNotNode exprNot = exprNotTree(); 
                node.addChild(exprNot);

                MulDivExprRestNode mulDivExprRest = mulDivExprRestTree(acc / exprNot.val); 
                node.addChild(mulDivExprRest);

                { node.val = mulDivExprRest.val; }
                return node;
            }
                
 
            case PLUS, MINUS, AND, XOR, OR, EOF, CLOSE -> {
                { node.val = acc; }
                return node;
            }
                
   
            default -> throw new ParseException("Unresolved token:" + lexer.token.rule +
                    " at position: " + (lexer.getPosition() - lexer.token.text.length()));
        }
    }

    
    public ExprNotNode exprNotTree() {
        ExprNotNode node = new ExprNotNode();
        switch (lexer.token.rule) {
            
            case NOT -> {
                
                NOTNode NOT = NOTTree(); 
                node.addChild(NOT);

                ExprNotNode exprNot = exprNotTree(); 
                node.addChild(exprNot);

                { node.val = ~exprNot.val; }
                return node;
            }
                
 
            case MINUS, NUMBER, OPEN, TAN -> {
                
                UnaryMinusNode unaryMinus = unaryMinusTree(); 
                node.addChild(unaryMinus);

                { node.val = unaryMinus.val; }
                return node;
            }
                
   
            default -> throw new ParseException("Unresolved token:" + lexer.token.rule +
                    " at position: " + (lexer.getPosition() - lexer.token.text.length()));
        }
    }

    
    public UnaryMinusNode unaryMinusTree() {
        UnaryMinusNode node = new UnaryMinusNode();
        switch (lexer.token.rule) {
            
            case MINUS -> {
                
                MINUSNode MINUS = MINUSTree(); 
                node.addChild(MINUS);

                PowExprNode powExpr = powExprTree(); 
                node.addChild(powExpr);

                { node.val = -powExpr.val; }
                return node;
            }
                
 
            case NUMBER, OPEN, TAN -> {
                
                PowExprNode powExpr = powExprTree(); 
                node.addChild(powExpr);

                { node.val = powExpr.val; }
                return node;
            }
                
   
            default -> throw new ParseException("Unresolved token:" + lexer.token.rule +
                    " at position: " + (lexer.getPosition() - lexer.token.text.length()));
        }
    }

    
    public PowExprNode powExprTree() {
        PowExprNode node = new PowExprNode();
        switch (lexer.token.rule) {
            
            case NUMBER, OPEN, TAN -> {
                
                TokenExprNode tokenExpr = tokenExprTree(); 
                node.addChild(tokenExpr);

                PowExprRestNode powExprRest = powExprRestTree(); 
                node.addChild(powExprRest);

                { node.val = (int) Math.pow(tokenExpr.val, powExprRest.val); }
                return node;
            }
                
   
            default -> throw new ParseException("Unresolved token:" + lexer.token.rule +
                    " at position: " + (lexer.getPosition() - lexer.token.text.length()));
        }
    }

    
    public PowExprRestNode powExprRestTree() {
        PowExprRestNode node = new PowExprRestNode();
        switch (lexer.token.rule) {
            
            case POW -> {
                
                POWNode POW = POWTree(); 
                node.addChild(POW);

                PowExprNode powExpr = powExprTree(); 
                node.addChild(powExpr);

                { node.val = powExpr.val; }
                return node;
            }
                
 
            case MULTIPLY, DIVIDE, PLUS, MINUS, AND, XOR, OR, EOF, CLOSE -> {
                { node.val = 1; }
                return node;
            }
                
   
            default -> throw new ParseException("Unresolved token:" + lexer.token.rule +
                    " at position: " + (lexer.getPosition() - lexer.token.text.length()));
        }
    }

    
    public TokenExprNode tokenExprTree() {
        TokenExprNode node = new TokenExprNode();
        switch (lexer.token.rule) {
            
            case NUMBER -> {
                
                NUMBERNode NUMBER = NUMBERTree(); 
                node.addChild(NUMBER);

                { node.val = Integer.parseInt(node.text); }
                return node;
            }
                
 
            case OPEN -> {
                
                OPENNode OPEN = OPENTree(); 
                node.addChild(OPEN);

                OrExprNode orExpr = orExprTree(); 
                node.addChild(orExpr);

                CLOSENode CLOSE = CLOSETree(); 
                node.addChild(CLOSE);

                { node.val = orExpr.val; }
                return node;
            }
                
 
            case TAN -> {
                
                TANNode TAN = TANTree(); 
                node.addChild(TAN);

                OPENNode OPEN = OPENTree(); 
                node.addChild(OPEN);

                OrExprNode orExpr = orExprTree(); 
                node.addChild(orExpr);

                CLOSENode CLOSE = CLOSETree(); 
                node.addChild(CLOSE);

                { node.val = (int) Math.tan(Math.toRadians(orExpr.val)); }
                return node;
            }
                
   
            default -> throw new ParseException("Unresolved token:" + lexer.token.rule +
                    " at position: " + (lexer.getPosition() - lexer.token.text.length()));
        }
    }

    
    public NUMBERNode NUMBERTree() {
        NUMBERNode node = new NUMBERNode(lexer.token.text);
        skipToken(Rule.NUMBER);
        
        return node;
    }

    
    public PLUSNode PLUSTree() {
        PLUSNode node = new PLUSNode(lexer.token.text);
        skipToken(Rule.PLUS);
        
        return node;
    }

    
    public MINUSNode MINUSTree() {
        MINUSNode node = new MINUSNode(lexer.token.text);
        skipToken(Rule.MINUS);
        
        return node;
    }

    
    public POWNode POWTree() {
        POWNode node = new POWNode(lexer.token.text);
        skipToken(Rule.POW);
        
        return node;
    }

    
    public MULTIPLYNode MULTIPLYTree() {
        MULTIPLYNode node = new MULTIPLYNode(lexer.token.text);
        skipToken(Rule.MULTIPLY);
        
        return node;
    }

    
    public DIVIDENode DIVIDETree() {
        DIVIDENode node = new DIVIDENode(lexer.token.text);
        skipToken(Rule.DIVIDE);
        
        return node;
    }

    
    public OPENNode OPENTree() {
        OPENNode node = new OPENNode(lexer.token.text);
        skipToken(Rule.OPEN);
        
        return node;
    }

    
    public CLOSENode CLOSETree() {
        CLOSENode node = new CLOSENode(lexer.token.text);
        skipToken(Rule.CLOSE);
        
        return node;
    }

    
    public ORNode ORTree() {
        ORNode node = new ORNode(lexer.token.text);
        skipToken(Rule.OR);
        
        return node;
    }

    
    public ANDNode ANDTree() {
        ANDNode node = new ANDNode(lexer.token.text);
        skipToken(Rule.AND);
        
        return node;
    }

    
    public XORNode XORTree() {
        XORNode node = new XORNode(lexer.token.text);
        skipToken(Rule.XOR);
        
        return node;
    }

    
    public NOTNode NOTTree() {
        NOTNode node = new NOTNode(lexer.token.text);
        skipToken(Rule.NOT);
        
        return node;
    }

    
    public TANNode TANTree() {
        TANNode node = new TANNode(lexer.token.text);
        skipToken(Rule.TAN);
        
        return node;
    }

}