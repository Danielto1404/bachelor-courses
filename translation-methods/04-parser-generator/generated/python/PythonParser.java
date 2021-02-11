package python;

import python.nodes.*;

public class PythonParser {

    private PythonLexer lexer;
    
    public PythonParser() { }

    public PythonParser(PythonLexer lexer) {
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
        lexer = new PythonLexer(input);
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
            
            case AT, DEF -> {
                
                DecoratorNode decorator = decoratorTree(); 
                node.addChild(decorator);

                DefinitionNode definition = definitionTree(); 
                node.addChild(definition);

                { node.str = decorator.str; node.str = node.str + definition.str; node.n = definition.n; }
                return node;
            }
                
   
            default -> throw new ParseException("Unresolved token:" + lexer.token.rule +
                    " at position: " + (lexer.getPosition() - lexer.token.text.length()));
        }
    }

    
    public DecoratorNode decoratorTree() {
        DecoratorNode node = new DecoratorNode();
        switch (lexer.token.rule) {
            
            case AT -> {
                
                ATNode AT = ATTree(); 
                node.addChild(AT);

                IDNode ID = IDTree(); 
                node.addChild(ID);

                { node.str = AT.text; node.str = node.str + ID.text + "\n"; }
                return node;
            }
                
 
            case DEF -> {
                { node.str = ""; }
                return node;
            }
                
   
            default -> throw new ParseException("Unresolved token:" + lexer.token.rule +
                    " at position: " + (lexer.getPosition() - lexer.token.text.length()));
        }
    }

    
    public DefinitionNode definitionTree() {
        DefinitionNode node = new DefinitionNode();
        switch (lexer.token.rule) {
            
            case DEF -> {
                
                DEFNode DEF = DEFTree(); 
                node.addChild(DEF);

                FunNode fun = funTree(); 
                node.addChild(fun);

                { node.str = DEF.text; node.str = node.str + " " + fun.str; node.n = fun.n; }
                return node;
            }
                
   
            default -> throw new ParseException("Unresolved token:" + lexer.token.rule +
                    " at position: " + (lexer.getPosition() - lexer.token.text.length()));
        }
    }

    
    public FunNode funTree() {
        FunNode node = new FunNode();
        switch (lexer.token.rule) {
            
            case ID -> {
                
                IDNode ID = IDTree(); 
                node.addChild(ID);

                L_BRACENode L_BRACE = L_BRACETree(); 
                node.addChild(L_BRACE);

                ArgsNode args = argsTree(); 
                node.addChild(args);

                R_BRACENode R_BRACE = R_BRACETree(); 
                node.addChild(R_BRACE);

                { node.str = ID.text; node.str = node.str + "(" + args.str + ")"; node.n = args.n; }
                return node;
            }
                
   
            default -> throw new ParseException("Unresolved token:" + lexer.token.rule +
                    " at position: " + (lexer.getPosition() - lexer.token.text.length()));
        }
    }

    
    public ArgsNode argsTree() {
        ArgsNode node = new ArgsNode();
        switch (lexer.token.rule) {
            
            case ID -> {
                
                IDNode ID = IDTree(); 
                node.addChild(ID);

                RestArgsNode restArgs = restArgsTree(); 
                node.addChild(restArgs);

                { node.str = ID.text; node.str = node.str + restArgs.str; node.n = 1 + restArgs.n; }
                return node;
            }
                
 
            case R_BRACE -> {
                { node.str = new String(); node.n = 0; }
                return node;
            }
                
   
            default -> throw new ParseException("Unresolved token:" + lexer.token.rule +
                    " at position: " + (lexer.getPosition() - lexer.token.text.length()));
        }
    }

    
    public RestArgsNode restArgsTree() {
        RestArgsNode node = new RestArgsNode();
        switch (lexer.token.rule) {
            
            case COMMA -> {
                
                COMMANode COMMA = COMMATree(); 
                node.addChild(COMMA);

                ArgsNode args = argsTree(); 
                node.addChild(args);

                { node.str = ", "; node.str = node.str + args.str; node.n = args.n; }
                return node;
            }
                
 
            case R_BRACE -> {
                { node.str = new String(); node.n = 0; }
                return node;
            }
                
   
            default -> throw new ParseException("Unresolved token:" + lexer.token.rule +
                    " at position: " + (lexer.getPosition() - lexer.token.text.length()));
        }
    }

    
    public ATNode ATTree() {
        ATNode node = new ATNode(lexer.token.text);
        skipToken(Rule.AT);
        
        return node;
    }

    
    public COMMANode COMMATree() {
        COMMANode node = new COMMANode(lexer.token.text);
        skipToken(Rule.COMMA);
        
        return node;
    }

    
    public L_BRACENode L_BRACETree() {
        L_BRACENode node = new L_BRACENode(lexer.token.text);
        skipToken(Rule.L_BRACE);
        
        return node;
    }

    
    public R_BRACENode R_BRACETree() {
        R_BRACENode node = new R_BRACENode(lexer.token.text);
        skipToken(Rule.R_BRACE);
        
        return node;
    }

    
    public DEFNode DEFTree() {
        DEFNode node = new DEFNode(lexer.token.text);
        skipToken(Rule.DEF);
        
        return node;
    }

    
    public IDNode IDTree() {
        IDNode node = new IDNode(lexer.token.text);
        skipToken(Rule.ID);
        
        return node;
    }

}