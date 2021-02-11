// Generated from /Users/daniilkorolev/Documents/GitHub/3-rd-year/translation-methods/03-automatic-parser-generators/src/grammar/CPP.g4 by ANTLR 4.9
package antlr;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class CPPParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.9", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, Break=3, Char=4, Continue=5, Double=6, Else=7, Float=8, 
		If=9, Int=10, Long=11, Return=12, Short=13, Void=14, While=15, For=16, 
		Do=17, LeftParen=18, RightParen=19, LeftBracket=20, RightBracket=21, LeftBrace=22, 
		RightBrace=23, Less=24, LessEqual=25, Greater=26, GreaterEqual=27, Plus=28, 
		Minus=29, Star=30, Div=31, Mod=32, And=33, Or=34, Not=35, Colon=36, Semi=37, 
		Comma=38, Assign=39, StarAssign=40, DivAssign=41, ModAssign=42, PlusAssign=43, 
		MinusAssign=44, Equal=45, NotEqual=46, ID=47, Number=48, DigitSequence=49, 
		StringLiteral=50, Whitespace=51, Newline=52, BlockComment=53, LineComment=54;
	public static final int
		RULE_cpp = 0, RULE_primaryExpression = 1, RULE_postfixExpression = 2, 
		RULE_argumentExpressionList = 3, RULE_unaryExpression = 4, RULE_unaryOperator = 5, 
		RULE_multiplicativeExpression = 6, RULE_additiveExpression = 7, RULE_relationalExpression = 8, 
		RULE_equalityExpression = 9, RULE_logicalAndExpression = 10, RULE_logicalOrExpression = 11, 
		RULE_assignmentExpression = 12, RULE_assignmentOperator = 13, RULE_declaration = 14, 
		RULE_initialValueDeclarator = 15, RULE_type = 16, RULE_declarator = 17, 
		RULE_functionArgs = 18, RULE_argDeclaration = 19, RULE_statement = 20, 
		RULE_codeBlock = 21, RULE_blockItemList = 22, RULE_blockItem = 23, RULE_expressionStatement = 24, 
		RULE_ifBlock = 25, RULE_iterationBlock = 26, RULE_forCondition = 27, RULE_forExpression = 28, 
		RULE_forDeclaration = 29, RULE_struct = 30, RULE_structDeclarationList = 31, 
		RULE_jumpStatement = 32, RULE_translationUnit = 33, RULE_externalDefinition = 34, 
		RULE_function = 35;
	private static String[] makeRuleNames() {
		return new String[] {
			"cpp", "primaryExpression", "postfixExpression", "argumentExpressionList", 
			"unaryExpression", "unaryOperator", "multiplicativeExpression", "additiveExpression", 
			"relationalExpression", "equalityExpression", "logicalAndExpression", 
			"logicalOrExpression", "assignmentExpression", "assignmentOperator", 
			"declaration", "initialValueDeclarator", "type", "declarator", "functionArgs", 
			"argDeclaration", "statement", "codeBlock", "blockItemList", "blockItem", 
			"expressionStatement", "ifBlock", "iterationBlock", "forCondition", "forExpression", 
			"forDeclaration", "struct", "structDeclarationList", "jumpStatement", 
			"translationUnit", "externalDefinition", "function"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'.'", "'struct'", "'break'", "'char'", "'continue'", "'double'", 
			"'else'", "'float'", "'if'", "'int'", "'long'", "'return'", "'short'", 
			"'void'", "'while'", "'for'", "'do'", "'('", "')'", "'['", "']'", "'{'", 
			"'}'", "'<'", "'<='", "'>'", "'>='", "'+'", "'-'", "'*'", "'/'", "'%'", 
			"'&&'", "'||'", "'!'", "':'", "';'", "','", "'='", "'*='", "'/='", "'%='", 
			"'+='", "'-='", "'=='", "'!='"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, "Break", "Char", "Continue", "Double", "Else", "Float", 
			"If", "Int", "Long", "Return", "Short", "Void", "While", "For", "Do", 
			"LeftParen", "RightParen", "LeftBracket", "RightBracket", "LeftBrace", 
			"RightBrace", "Less", "LessEqual", "Greater", "GreaterEqual", "Plus", 
			"Minus", "Star", "Div", "Mod", "And", "Or", "Not", "Colon", "Semi", "Comma", 
			"Assign", "StarAssign", "DivAssign", "ModAssign", "PlusAssign", "MinusAssign", 
			"Equal", "NotEqual", "ID", "Number", "DigitSequence", "StringLiteral", 
			"Whitespace", "Newline", "BlockComment", "LineComment"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "CPP.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public CPPParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class CppContext extends ParserRuleContext {
		public TranslationUnitContext code;
		public TerminalNode EOF() { return getToken(CPPParser.EOF, 0); }
		public TranslationUnitContext translationUnit() {
			return getRuleContext(TranslationUnitContext.class,0);
		}
		public CppContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cpp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).enterCpp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).exitCpp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CPPVisitor ) return ((CPPVisitor<? extends T>)visitor).visitCpp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CppContext cpp() throws RecognitionException {
		CppContext _localctx = new CppContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_cpp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(73);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << Char) | (1L << Double) | (1L << Float) | (1L << Int) | (1L << Long) | (1L << Short) | (1L << Void) | (1L << ID))) != 0)) {
				{
				setState(72);
				((CppContext)_localctx).code = translationUnit(0);
				}
			}

			setState(75);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PrimaryExpressionContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(CPPParser.ID, 0); }
		public TerminalNode Number() { return getToken(CPPParser.Number, 0); }
		public List<TerminalNode> StringLiteral() { return getTokens(CPPParser.StringLiteral); }
		public TerminalNode StringLiteral(int i) {
			return getToken(CPPParser.StringLiteral, i);
		}
		public TerminalNode LeftParen() { return getToken(CPPParser.LeftParen, 0); }
		public AssignmentExpressionContext assignmentExpression() {
			return getRuleContext(AssignmentExpressionContext.class,0);
		}
		public TerminalNode RightParen() { return getToken(CPPParser.RightParen, 0); }
		public PrimaryExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primaryExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).enterPrimaryExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).exitPrimaryExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CPPVisitor ) return ((CPPVisitor<? extends T>)visitor).visitPrimaryExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrimaryExpressionContext primaryExpression() throws RecognitionException {
		PrimaryExpressionContext _localctx = new PrimaryExpressionContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_primaryExpression);
		try {
			int _alt;
			setState(88);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ID:
				enterOuterAlt(_localctx, 1);
				{
				setState(77);
				match(ID);
				}
				break;
			case Number:
				enterOuterAlt(_localctx, 2);
				{
				setState(78);
				match(Number);
				}
				break;
			case StringLiteral:
				enterOuterAlt(_localctx, 3);
				{
				setState(80); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(79);
						match(StringLiteral);
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(82); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				}
				break;
			case LeftParen:
				enterOuterAlt(_localctx, 4);
				{
				setState(84);
				match(LeftParen);
				setState(85);
				assignmentExpression();
				setState(86);
				match(RightParen);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PostfixExpressionContext extends ParserRuleContext {
		public PrimaryExpressionContext primaryExpression() {
			return getRuleContext(PrimaryExpressionContext.class,0);
		}
		public PostfixExpressionContext postfixExpression() {
			return getRuleContext(PostfixExpressionContext.class,0);
		}
		public TerminalNode LeftParen() { return getToken(CPPParser.LeftParen, 0); }
		public TerminalNode RightParen() { return getToken(CPPParser.RightParen, 0); }
		public ArgumentExpressionListContext argumentExpressionList() {
			return getRuleContext(ArgumentExpressionListContext.class,0);
		}
		public TerminalNode ID() { return getToken(CPPParser.ID, 0); }
		public PostfixExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_postfixExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).enterPostfixExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).exitPostfixExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CPPVisitor ) return ((CPPVisitor<? extends T>)visitor).visitPostfixExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PostfixExpressionContext postfixExpression() throws RecognitionException {
		return postfixExpression(0);
	}

	private PostfixExpressionContext postfixExpression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		PostfixExpressionContext _localctx = new PostfixExpressionContext(_ctx, _parentState);
		PostfixExpressionContext _prevctx = _localctx;
		int _startState = 4;
		enterRecursionRule(_localctx, 4, RULE_postfixExpression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(91);
			primaryExpression();
			}
			_ctx.stop = _input.LT(-1);
			setState(104);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(102);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
					case 1:
						{
						_localctx = new PostfixExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_postfixExpression);
						setState(93);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(94);
						match(LeftParen);
						setState(96);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LeftParen) | (1L << Plus) | (1L << Minus) | (1L << Not) | (1L << ID) | (1L << Number) | (1L << StringLiteral))) != 0)) {
							{
							setState(95);
							argumentExpressionList(0);
							}
						}

						setState(98);
						match(RightParen);
						}
						break;
					case 2:
						{
						_localctx = new PostfixExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_postfixExpression);
						setState(99);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(100);
						match(T__0);
						setState(101);
						match(ID);
						}
						break;
					}
					} 
				}
				setState(106);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class ArgumentExpressionListContext extends ParserRuleContext {
		public ArgumentExpressionListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_argumentExpressionList; }
	 
		public ArgumentExpressionListContext() { }
		public void copyFrom(ArgumentExpressionListContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class AssListNextLevelContext extends ArgumentExpressionListContext {
		public AssignmentExpressionContext assignmentExpression() {
			return getRuleContext(AssignmentExpressionContext.class,0);
		}
		public AssListNextLevelContext(ArgumentExpressionListContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).enterAssListNextLevel(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).exitAssListNextLevel(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CPPVisitor ) return ((CPPVisitor<? extends T>)visitor).visitAssListNextLevel(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AssList1Context extends ArgumentExpressionListContext {
		public ArgumentExpressionListContext args;
		public AssignmentExpressionContext assign;
		public TerminalNode Comma() { return getToken(CPPParser.Comma, 0); }
		public ArgumentExpressionListContext argumentExpressionList() {
			return getRuleContext(ArgumentExpressionListContext.class,0);
		}
		public AssignmentExpressionContext assignmentExpression() {
			return getRuleContext(AssignmentExpressionContext.class,0);
		}
		public AssList1Context(ArgumentExpressionListContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).enterAssList1(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).exitAssList1(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CPPVisitor ) return ((CPPVisitor<? extends T>)visitor).visitAssList1(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArgumentExpressionListContext argumentExpressionList() throws RecognitionException {
		return argumentExpressionList(0);
	}

	private ArgumentExpressionListContext argumentExpressionList(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ArgumentExpressionListContext _localctx = new ArgumentExpressionListContext(_ctx, _parentState);
		ArgumentExpressionListContext _prevctx = _localctx;
		int _startState = 6;
		enterRecursionRule(_localctx, 6, RULE_argumentExpressionList, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new AssListNextLevelContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(108);
			assignmentExpression();
			}
			_ctx.stop = _input.LT(-1);
			setState(115);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new AssList1Context(new ArgumentExpressionListContext(_parentctx, _parentState));
					((AssList1Context)_localctx).args = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_argumentExpressionList);
					setState(110);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(111);
					match(Comma);
					setState(112);
					((AssList1Context)_localctx).assign = assignmentExpression();
					}
					} 
				}
				setState(117);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class UnaryExpressionContext extends ParserRuleContext {
		public PostfixExpressionContext postfixExpression() {
			return getRuleContext(PostfixExpressionContext.class,0);
		}
		public UnaryOperatorContext unaryOperator() {
			return getRuleContext(UnaryOperatorContext.class,0);
		}
		public UnaryExpressionContext unaryExpression() {
			return getRuleContext(UnaryExpressionContext.class,0);
		}
		public UnaryExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unaryExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).enterUnaryExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).exitUnaryExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CPPVisitor ) return ((CPPVisitor<? extends T>)visitor).visitUnaryExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UnaryExpressionContext unaryExpression() throws RecognitionException {
		UnaryExpressionContext _localctx = new UnaryExpressionContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_unaryExpression);
		try {
			setState(122);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LeftParen:
			case ID:
			case Number:
			case StringLiteral:
				enterOuterAlt(_localctx, 1);
				{
				setState(118);
				postfixExpression(0);
				}
				break;
			case Plus:
			case Minus:
			case Not:
				enterOuterAlt(_localctx, 2);
				{
				setState(119);
				unaryOperator();
				setState(120);
				unaryExpression();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class UnaryOperatorContext extends ParserRuleContext {
		public Token op;
		public TerminalNode Plus() { return getToken(CPPParser.Plus, 0); }
		public TerminalNode Minus() { return getToken(CPPParser.Minus, 0); }
		public TerminalNode Not() { return getToken(CPPParser.Not, 0); }
		public UnaryOperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unaryOperator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).enterUnaryOperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).exitUnaryOperator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CPPVisitor ) return ((CPPVisitor<? extends T>)visitor).visitUnaryOperator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UnaryOperatorContext unaryOperator() throws RecognitionException {
		UnaryOperatorContext _localctx = new UnaryOperatorContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_unaryOperator);
		try {
			setState(127);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Plus:
				enterOuterAlt(_localctx, 1);
				{
				setState(124);
				((UnaryOperatorContext)_localctx).op = match(Plus);
				}
				break;
			case Minus:
				enterOuterAlt(_localctx, 2);
				{
				setState(125);
				((UnaryOperatorContext)_localctx).op = match(Minus);
				}
				break;
			case Not:
				enterOuterAlt(_localctx, 3);
				{
				setState(126);
				((UnaryOperatorContext)_localctx).op = match(Not);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MultiplicativeExpressionContext extends ParserRuleContext {
		public MultiplicativeExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multiplicativeExpression; }
	 
		public MultiplicativeExpressionContext() { }
		public void copyFrom(MultiplicativeExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class MultOpContext extends MultiplicativeExpressionContext {
		public MultiplicativeExpressionContext mulExpr;
		public Token op;
		public UnaryExpressionContext unary;
		public MultiplicativeExpressionContext multiplicativeExpression() {
			return getRuleContext(MultiplicativeExpressionContext.class,0);
		}
		public TerminalNode Star() { return getToken(CPPParser.Star, 0); }
		public UnaryExpressionContext unaryExpression() {
			return getRuleContext(UnaryExpressionContext.class,0);
		}
		public TerminalNode Div() { return getToken(CPPParser.Div, 0); }
		public TerminalNode Mod() { return getToken(CPPParser.Mod, 0); }
		public MultOpContext(MultiplicativeExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).enterMultOp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).exitMultOp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CPPVisitor ) return ((CPPVisitor<? extends T>)visitor).visitMultOp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class MultNextContext extends MultiplicativeExpressionContext {
		public UnaryExpressionContext unaryExpression() {
			return getRuleContext(UnaryExpressionContext.class,0);
		}
		public MultNextContext(MultiplicativeExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).enterMultNext(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).exitMultNext(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CPPVisitor ) return ((CPPVisitor<? extends T>)visitor).visitMultNext(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MultiplicativeExpressionContext multiplicativeExpression() throws RecognitionException {
		return multiplicativeExpression(0);
	}

	private MultiplicativeExpressionContext multiplicativeExpression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		MultiplicativeExpressionContext _localctx = new MultiplicativeExpressionContext(_ctx, _parentState);
		MultiplicativeExpressionContext _prevctx = _localctx;
		int _startState = 12;
		enterRecursionRule(_localctx, 12, RULE_multiplicativeExpression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new MultNextContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(130);
			unaryExpression();
			}
			_ctx.stop = _input.LT(-1);
			setState(143);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(141);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
					case 1:
						{
						_localctx = new MultOpContext(new MultiplicativeExpressionContext(_parentctx, _parentState));
						((MultOpContext)_localctx).mulExpr = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_multiplicativeExpression);
						setState(132);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(133);
						((MultOpContext)_localctx).op = match(Star);
						setState(134);
						((MultOpContext)_localctx).unary = unaryExpression();
						}
						break;
					case 2:
						{
						_localctx = new MultOpContext(new MultiplicativeExpressionContext(_parentctx, _parentState));
						((MultOpContext)_localctx).mulExpr = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_multiplicativeExpression);
						setState(135);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(136);
						((MultOpContext)_localctx).op = match(Div);
						setState(137);
						((MultOpContext)_localctx).unary = unaryExpression();
						}
						break;
					case 3:
						{
						_localctx = new MultOpContext(new MultiplicativeExpressionContext(_parentctx, _parentState));
						((MultOpContext)_localctx).mulExpr = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_multiplicativeExpression);
						setState(138);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(139);
						((MultOpContext)_localctx).op = match(Mod);
						setState(140);
						((MultOpContext)_localctx).unary = unaryExpression();
						}
						break;
					}
					} 
				}
				setState(145);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class AdditiveExpressionContext extends ParserRuleContext {
		public AdditiveExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_additiveExpression; }
	 
		public AdditiveExpressionContext() { }
		public void copyFrom(AdditiveExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class AddNextContext extends AdditiveExpressionContext {
		public MultiplicativeExpressionContext multiplicativeExpression() {
			return getRuleContext(MultiplicativeExpressionContext.class,0);
		}
		public AddNextContext(AdditiveExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).enterAddNext(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).exitAddNext(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CPPVisitor ) return ((CPPVisitor<? extends T>)visitor).visitAddNext(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AddOpContext extends AdditiveExpressionContext {
		public AdditiveExpressionContext addExpr;
		public Token op;
		public MultiplicativeExpressionContext mulExpr;
		public AdditiveExpressionContext additiveExpression() {
			return getRuleContext(AdditiveExpressionContext.class,0);
		}
		public TerminalNode Plus() { return getToken(CPPParser.Plus, 0); }
		public MultiplicativeExpressionContext multiplicativeExpression() {
			return getRuleContext(MultiplicativeExpressionContext.class,0);
		}
		public TerminalNode Minus() { return getToken(CPPParser.Minus, 0); }
		public AddOpContext(AdditiveExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).enterAddOp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).exitAddOp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CPPVisitor ) return ((CPPVisitor<? extends T>)visitor).visitAddOp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AdditiveExpressionContext additiveExpression() throws RecognitionException {
		return additiveExpression(0);
	}

	private AdditiveExpressionContext additiveExpression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		AdditiveExpressionContext _localctx = new AdditiveExpressionContext(_ctx, _parentState);
		AdditiveExpressionContext _prevctx = _localctx;
		int _startState = 14;
		enterRecursionRule(_localctx, 14, RULE_additiveExpression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new AddNextContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(147);
			multiplicativeExpression(0);
			}
			_ctx.stop = _input.LT(-1);
			setState(157);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(155);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
					case 1:
						{
						_localctx = new AddOpContext(new AdditiveExpressionContext(_parentctx, _parentState));
						((AddOpContext)_localctx).addExpr = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_additiveExpression);
						setState(149);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(150);
						((AddOpContext)_localctx).op = match(Plus);
						setState(151);
						((AddOpContext)_localctx).mulExpr = multiplicativeExpression(0);
						}
						break;
					case 2:
						{
						_localctx = new AddOpContext(new AdditiveExpressionContext(_parentctx, _parentState));
						((AddOpContext)_localctx).addExpr = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_additiveExpression);
						setState(152);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(153);
						((AddOpContext)_localctx).op = match(Minus);
						setState(154);
						((AddOpContext)_localctx).mulExpr = multiplicativeExpression(0);
						}
						break;
					}
					} 
				}
				setState(159);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class RelationalExpressionContext extends ParserRuleContext {
		public RelationalExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_relationalExpression; }
	 
		public RelationalExpressionContext() { }
		public void copyFrom(RelationalExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class RelNextContext extends RelationalExpressionContext {
		public AdditiveExpressionContext additiveExpression() {
			return getRuleContext(AdditiveExpressionContext.class,0);
		}
		public RelNextContext(RelationalExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).enterRelNext(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).exitRelNext(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CPPVisitor ) return ((CPPVisitor<? extends T>)visitor).visitRelNext(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class RelOpContext extends RelationalExpressionContext {
		public RelationalExpressionContext relExpr;
		public Token op;
		public AdditiveExpressionContext addExpr;
		public RelationalExpressionContext relationalExpression() {
			return getRuleContext(RelationalExpressionContext.class,0);
		}
		public TerminalNode Less() { return getToken(CPPParser.Less, 0); }
		public AdditiveExpressionContext additiveExpression() {
			return getRuleContext(AdditiveExpressionContext.class,0);
		}
		public TerminalNode Greater() { return getToken(CPPParser.Greater, 0); }
		public TerminalNode LessEqual() { return getToken(CPPParser.LessEqual, 0); }
		public TerminalNode GreaterEqual() { return getToken(CPPParser.GreaterEqual, 0); }
		public RelOpContext(RelationalExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).enterRelOp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).exitRelOp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CPPVisitor ) return ((CPPVisitor<? extends T>)visitor).visitRelOp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RelationalExpressionContext relationalExpression() throws RecognitionException {
		return relationalExpression(0);
	}

	private RelationalExpressionContext relationalExpression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		RelationalExpressionContext _localctx = new RelationalExpressionContext(_ctx, _parentState);
		RelationalExpressionContext _prevctx = _localctx;
		int _startState = 16;
		enterRecursionRule(_localctx, 16, RULE_relationalExpression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new RelNextContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(161);
			additiveExpression(0);
			}
			_ctx.stop = _input.LT(-1);
			setState(177);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(175);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
					case 1:
						{
						_localctx = new RelOpContext(new RelationalExpressionContext(_parentctx, _parentState));
						((RelOpContext)_localctx).relExpr = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_relationalExpression);
						setState(163);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(164);
						((RelOpContext)_localctx).op = match(Less);
						setState(165);
						((RelOpContext)_localctx).addExpr = additiveExpression(0);
						}
						break;
					case 2:
						{
						_localctx = new RelOpContext(new RelationalExpressionContext(_parentctx, _parentState));
						((RelOpContext)_localctx).relExpr = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_relationalExpression);
						setState(166);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(167);
						((RelOpContext)_localctx).op = match(Greater);
						setState(168);
						((RelOpContext)_localctx).addExpr = additiveExpression(0);
						}
						break;
					case 3:
						{
						_localctx = new RelOpContext(new RelationalExpressionContext(_parentctx, _parentState));
						((RelOpContext)_localctx).relExpr = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_relationalExpression);
						setState(169);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(170);
						((RelOpContext)_localctx).op = match(LessEqual);
						setState(171);
						((RelOpContext)_localctx).addExpr = additiveExpression(0);
						}
						break;
					case 4:
						{
						_localctx = new RelOpContext(new RelationalExpressionContext(_parentctx, _parentState));
						((RelOpContext)_localctx).relExpr = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_relationalExpression);
						setState(172);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(173);
						((RelOpContext)_localctx).op = match(GreaterEqual);
						setState(174);
						((RelOpContext)_localctx).addExpr = additiveExpression(0);
						}
						break;
					}
					} 
				}
				setState(179);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class EqualityExpressionContext extends ParserRuleContext {
		public EqualityExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_equalityExpression; }
	 
		public EqualityExpressionContext() { }
		public void copyFrom(EqualityExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class EqOpContext extends EqualityExpressionContext {
		public EqualityExpressionContext eqExpr;
		public Token op;
		public RelationalExpressionContext relExpr;
		public EqualityExpressionContext equalityExpression() {
			return getRuleContext(EqualityExpressionContext.class,0);
		}
		public TerminalNode Equal() { return getToken(CPPParser.Equal, 0); }
		public RelationalExpressionContext relationalExpression() {
			return getRuleContext(RelationalExpressionContext.class,0);
		}
		public TerminalNode NotEqual() { return getToken(CPPParser.NotEqual, 0); }
		public EqOpContext(EqualityExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).enterEqOp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).exitEqOp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CPPVisitor ) return ((CPPVisitor<? extends T>)visitor).visitEqOp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class EqNextContext extends EqualityExpressionContext {
		public RelationalExpressionContext relationalExpression() {
			return getRuleContext(RelationalExpressionContext.class,0);
		}
		public EqNextContext(EqualityExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).enterEqNext(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).exitEqNext(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CPPVisitor ) return ((CPPVisitor<? extends T>)visitor).visitEqNext(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EqualityExpressionContext equalityExpression() throws RecognitionException {
		return equalityExpression(0);
	}

	private EqualityExpressionContext equalityExpression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		EqualityExpressionContext _localctx = new EqualityExpressionContext(_ctx, _parentState);
		EqualityExpressionContext _prevctx = _localctx;
		int _startState = 18;
		enterRecursionRule(_localctx, 18, RULE_equalityExpression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new EqNextContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(181);
			relationalExpression(0);
			}
			_ctx.stop = _input.LT(-1);
			setState(191);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,16,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(189);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
					case 1:
						{
						_localctx = new EqOpContext(new EqualityExpressionContext(_parentctx, _parentState));
						((EqOpContext)_localctx).eqExpr = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_equalityExpression);
						setState(183);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(184);
						((EqOpContext)_localctx).op = match(Equal);
						setState(185);
						((EqOpContext)_localctx).relExpr = relationalExpression(0);
						}
						break;
					case 2:
						{
						_localctx = new EqOpContext(new EqualityExpressionContext(_parentctx, _parentState));
						((EqOpContext)_localctx).eqExpr = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_equalityExpression);
						setState(186);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(187);
						((EqOpContext)_localctx).op = match(NotEqual);
						setState(188);
						((EqOpContext)_localctx).relExpr = relationalExpression(0);
						}
						break;
					}
					} 
				}
				setState(193);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,16,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class LogicalAndExpressionContext extends ParserRuleContext {
		public LogicalAndExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logicalAndExpression; }
	 
		public LogicalAndExpressionContext() { }
		public void copyFrom(LogicalAndExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class AndOpContext extends LogicalAndExpressionContext {
		public LogicalAndExpressionContext logicalAnd;
		public Token op;
		public EqualityExpressionContext eqExpr;
		public LogicalAndExpressionContext logicalAndExpression() {
			return getRuleContext(LogicalAndExpressionContext.class,0);
		}
		public TerminalNode And() { return getToken(CPPParser.And, 0); }
		public EqualityExpressionContext equalityExpression() {
			return getRuleContext(EqualityExpressionContext.class,0);
		}
		public AndOpContext(LogicalAndExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).enterAndOp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).exitAndOp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CPPVisitor ) return ((CPPVisitor<? extends T>)visitor).visitAndOp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AndNextContext extends LogicalAndExpressionContext {
		public EqualityExpressionContext equalityExpression() {
			return getRuleContext(EqualityExpressionContext.class,0);
		}
		public AndNextContext(LogicalAndExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).enterAndNext(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).exitAndNext(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CPPVisitor ) return ((CPPVisitor<? extends T>)visitor).visitAndNext(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LogicalAndExpressionContext logicalAndExpression() throws RecognitionException {
		return logicalAndExpression(0);
	}

	private LogicalAndExpressionContext logicalAndExpression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		LogicalAndExpressionContext _localctx = new LogicalAndExpressionContext(_ctx, _parentState);
		LogicalAndExpressionContext _prevctx = _localctx;
		int _startState = 20;
		enterRecursionRule(_localctx, 20, RULE_logicalAndExpression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new AndNextContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(195);
			equalityExpression(0);
			}
			_ctx.stop = _input.LT(-1);
			setState(202);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,17,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new AndOpContext(new LogicalAndExpressionContext(_parentctx, _parentState));
					((AndOpContext)_localctx).logicalAnd = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_logicalAndExpression);
					setState(197);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(198);
					((AndOpContext)_localctx).op = match(And);
					setState(199);
					((AndOpContext)_localctx).eqExpr = equalityExpression(0);
					}
					} 
				}
				setState(204);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,17,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class LogicalOrExpressionContext extends ParserRuleContext {
		public LogicalOrExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logicalOrExpression; }
	 
		public LogicalOrExpressionContext() { }
		public void copyFrom(LogicalOrExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class OrOpContext extends LogicalOrExpressionContext {
		public LogicalOrExpressionContext logicalOr;
		public Token op;
		public LogicalAndExpressionContext logicalAnd;
		public LogicalOrExpressionContext logicalOrExpression() {
			return getRuleContext(LogicalOrExpressionContext.class,0);
		}
		public TerminalNode Or() { return getToken(CPPParser.Or, 0); }
		public LogicalAndExpressionContext logicalAndExpression() {
			return getRuleContext(LogicalAndExpressionContext.class,0);
		}
		public OrOpContext(LogicalOrExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).enterOrOp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).exitOrOp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CPPVisitor ) return ((CPPVisitor<? extends T>)visitor).visitOrOp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class OrNextContext extends LogicalOrExpressionContext {
		public LogicalAndExpressionContext logicalAndExpression() {
			return getRuleContext(LogicalAndExpressionContext.class,0);
		}
		public OrNextContext(LogicalOrExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).enterOrNext(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).exitOrNext(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CPPVisitor ) return ((CPPVisitor<? extends T>)visitor).visitOrNext(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LogicalOrExpressionContext logicalOrExpression() throws RecognitionException {
		return logicalOrExpression(0);
	}

	private LogicalOrExpressionContext logicalOrExpression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		LogicalOrExpressionContext _localctx = new LogicalOrExpressionContext(_ctx, _parentState);
		LogicalOrExpressionContext _prevctx = _localctx;
		int _startState = 22;
		enterRecursionRule(_localctx, 22, RULE_logicalOrExpression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new OrNextContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(206);
			logicalAndExpression(0);
			}
			_ctx.stop = _input.LT(-1);
			setState(213);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,18,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new OrOpContext(new LogicalOrExpressionContext(_parentctx, _parentState));
					((OrOpContext)_localctx).logicalOr = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_logicalOrExpression);
					setState(208);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(209);
					((OrOpContext)_localctx).op = match(Or);
					setState(210);
					((OrOpContext)_localctx).logicalAnd = logicalAndExpression(0);
					}
					} 
				}
				setState(215);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,18,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class AssignmentExpressionContext extends ParserRuleContext {
		public AssignmentExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignmentExpression; }
	 
		public AssignmentExpressionContext() { }
		public void copyFrom(AssignmentExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class AssExprNextLevelContext extends AssignmentExpressionContext {
		public LogicalOrExpressionContext logicalOrExpression() {
			return getRuleContext(LogicalOrExpressionContext.class,0);
		}
		public AssExprNextLevelContext(AssignmentExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).enterAssExprNextLevel(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).exitAssExprNextLevel(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CPPVisitor ) return ((CPPVisitor<? extends T>)visitor).visitAssExprNextLevel(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AssignExprListContext extends AssignmentExpressionContext {
		public UnaryExpressionContext unary;
		public AssignmentOperatorContext op;
		public AssignmentExpressionContext assignExpr;
		public UnaryExpressionContext unaryExpression() {
			return getRuleContext(UnaryExpressionContext.class,0);
		}
		public AssignmentOperatorContext assignmentOperator() {
			return getRuleContext(AssignmentOperatorContext.class,0);
		}
		public AssignmentExpressionContext assignmentExpression() {
			return getRuleContext(AssignmentExpressionContext.class,0);
		}
		public AssignExprListContext(AssignmentExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).enterAssignExprList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).exitAssignExprList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CPPVisitor ) return ((CPPVisitor<? extends T>)visitor).visitAssignExprList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignmentExpressionContext assignmentExpression() throws RecognitionException {
		AssignmentExpressionContext _localctx = new AssignmentExpressionContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_assignmentExpression);
		try {
			setState(221);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
			case 1:
				_localctx = new AssExprNextLevelContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(216);
				logicalOrExpression(0);
				}
				break;
			case 2:
				_localctx = new AssignExprListContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(217);
				((AssignExprListContext)_localctx).unary = unaryExpression();
				setState(218);
				((AssignExprListContext)_localctx).op = assignmentOperator();
				setState(219);
				((AssignExprListContext)_localctx).assignExpr = assignmentExpression();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AssignmentOperatorContext extends ParserRuleContext {
		public TerminalNode Assign() { return getToken(CPPParser.Assign, 0); }
		public TerminalNode StarAssign() { return getToken(CPPParser.StarAssign, 0); }
		public TerminalNode DivAssign() { return getToken(CPPParser.DivAssign, 0); }
		public TerminalNode ModAssign() { return getToken(CPPParser.ModAssign, 0); }
		public TerminalNode PlusAssign() { return getToken(CPPParser.PlusAssign, 0); }
		public TerminalNode MinusAssign() { return getToken(CPPParser.MinusAssign, 0); }
		public AssignmentOperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignmentOperator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).enterAssignmentOperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).exitAssignmentOperator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CPPVisitor ) return ((CPPVisitor<? extends T>)visitor).visitAssignmentOperator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignmentOperatorContext assignmentOperator() throws RecognitionException {
		AssignmentOperatorContext _localctx = new AssignmentOperatorContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_assignmentOperator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(223);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Assign) | (1L << StarAssign) | (1L << DivAssign) | (1L << ModAssign) | (1L << PlusAssign) | (1L << MinusAssign))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DeclarationContext extends ParserRuleContext {
		public TypeContext t;
		public InitialValueDeclaratorContext decl;
		public TerminalNode Semi() { return getToken(CPPParser.Semi, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public InitialValueDeclaratorContext initialValueDeclarator() {
			return getRuleContext(InitialValueDeclaratorContext.class,0);
		}
		public DeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).enterDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).exitDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CPPVisitor ) return ((CPPVisitor<? extends T>)visitor).visitDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclarationContext declaration() throws RecognitionException {
		DeclarationContext _localctx = new DeclarationContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_declaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(225);
			((DeclarationContext)_localctx).t = type();
			setState(226);
			((DeclarationContext)_localctx).decl = initialValueDeclarator();
			setState(227);
			match(Semi);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InitialValueDeclaratorContext extends ParserRuleContext {
		public InitialValueDeclaratorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_initialValueDeclarator; }
	 
		public InitialValueDeclaratorContext() { }
		public void copyFrom(InitialValueDeclaratorContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class InitDeclNextLevelContext extends InitialValueDeclaratorContext {
		public DeclaratorContext declarator() {
			return getRuleContext(DeclaratorContext.class,0);
		}
		public InitDeclNextLevelContext(InitialValueDeclaratorContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).enterInitDeclNextLevel(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).exitInitDeclNextLevel(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CPPVisitor ) return ((CPPVisitor<? extends T>)visitor).visitInitDeclNextLevel(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class InitDeclEqContext extends InitialValueDeclaratorContext {
		public DeclaratorContext e1;
		public AssignmentExpressionContext e2;
		public TerminalNode Assign() { return getToken(CPPParser.Assign, 0); }
		public DeclaratorContext declarator() {
			return getRuleContext(DeclaratorContext.class,0);
		}
		public AssignmentExpressionContext assignmentExpression() {
			return getRuleContext(AssignmentExpressionContext.class,0);
		}
		public InitDeclEqContext(InitialValueDeclaratorContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).enterInitDeclEq(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).exitInitDeclEq(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CPPVisitor ) return ((CPPVisitor<? extends T>)visitor).visitInitDeclEq(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InitialValueDeclaratorContext initialValueDeclarator() throws RecognitionException {
		InitialValueDeclaratorContext _localctx = new InitialValueDeclaratorContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_initialValueDeclarator);
		try {
			setState(234);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,20,_ctx) ) {
			case 1:
				_localctx = new InitDeclNextLevelContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(229);
				declarator(0);
				}
				break;
			case 2:
				_localctx = new InitDeclEqContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(230);
				((InitDeclEqContext)_localctx).e1 = declarator(0);
				setState(231);
				match(Assign);
				setState(232);
				((InitDeclEqContext)_localctx).e2 = assignmentExpression();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeContext extends ParserRuleContext {
		public TerminalNode Void() { return getToken(CPPParser.Void, 0); }
		public TerminalNode Char() { return getToken(CPPParser.Char, 0); }
		public TerminalNode Short() { return getToken(CPPParser.Short, 0); }
		public TerminalNode Int() { return getToken(CPPParser.Int, 0); }
		public TerminalNode Long() { return getToken(CPPParser.Long, 0); }
		public TerminalNode Float() { return getToken(CPPParser.Float, 0); }
		public TerminalNode Double() { return getToken(CPPParser.Double, 0); }
		public TerminalNode ID() { return getToken(CPPParser.ID, 0); }
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).enterType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).exitType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CPPVisitor ) return ((CPPVisitor<? extends T>)visitor).visitType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_type);
		int _la;
		try {
			setState(238);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Char:
			case Double:
			case Float:
			case Int:
			case Long:
			case Short:
			case Void:
				enterOuterAlt(_localctx, 1);
				{
				setState(236);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Char) | (1L << Double) | (1L << Float) | (1L << Int) | (1L << Long) | (1L << Short) | (1L << Void))) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				break;
			case ID:
				enterOuterAlt(_localctx, 2);
				{
				setState(237);
				match(ID);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DeclaratorContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(CPPParser.ID, 0); }
		public TerminalNode LeftParen() { return getToken(CPPParser.LeftParen, 0); }
		public DeclaratorContext declarator() {
			return getRuleContext(DeclaratorContext.class,0);
		}
		public TerminalNode RightParen() { return getToken(CPPParser.RightParen, 0); }
		public FunctionArgsContext functionArgs() {
			return getRuleContext(FunctionArgsContext.class,0);
		}
		public DeclaratorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declarator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).enterDeclarator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).exitDeclarator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CPPVisitor ) return ((CPPVisitor<? extends T>)visitor).visitDeclarator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclaratorContext declarator() throws RecognitionException {
		return declarator(0);
	}

	private DeclaratorContext declarator(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		DeclaratorContext _localctx = new DeclaratorContext(_ctx, _parentState);
		DeclaratorContext _prevctx = _localctx;
		int _startState = 34;
		enterRecursionRule(_localctx, 34, RULE_declarator, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(246);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ID:
				{
				setState(241);
				match(ID);
				}
				break;
			case LeftParen:
				{
				setState(242);
				match(LeftParen);
				setState(243);
				declarator(0);
				setState(244);
				match(RightParen);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(256);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,24,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new DeclaratorContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_declarator);
					setState(248);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(249);
					match(LeftParen);
					setState(251);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Char) | (1L << Double) | (1L << Float) | (1L << Int) | (1L << Long) | (1L << Short) | (1L << Void) | (1L << ID))) != 0)) {
						{
						setState(250);
						functionArgs(0);
						}
					}

					setState(253);
					match(RightParen);
					}
					} 
				}
				setState(258);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,24,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class FunctionArgsContext extends ParserRuleContext {
		public FunctionArgsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionArgs; }
	 
		public FunctionArgsContext() { }
		public void copyFrom(FunctionArgsContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ParamListNextLevelContext extends FunctionArgsContext {
		public ArgDeclarationContext argDeclaration() {
			return getRuleContext(ArgDeclarationContext.class,0);
		}
		public ParamListNextLevelContext(FunctionArgsContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).enterParamListNextLevel(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).exitParamListNextLevel(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CPPVisitor ) return ((CPPVisitor<? extends T>)visitor).visitParamListNextLevel(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ParamListCommaContext extends FunctionArgsContext {
		public FunctionArgsContext e1;
		public ArgDeclarationContext e2;
		public TerminalNode Comma() { return getToken(CPPParser.Comma, 0); }
		public FunctionArgsContext functionArgs() {
			return getRuleContext(FunctionArgsContext.class,0);
		}
		public ArgDeclarationContext argDeclaration() {
			return getRuleContext(ArgDeclarationContext.class,0);
		}
		public ParamListCommaContext(FunctionArgsContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).enterParamListComma(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).exitParamListComma(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CPPVisitor ) return ((CPPVisitor<? extends T>)visitor).visitParamListComma(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionArgsContext functionArgs() throws RecognitionException {
		return functionArgs(0);
	}

	private FunctionArgsContext functionArgs(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		FunctionArgsContext _localctx = new FunctionArgsContext(_ctx, _parentState);
		FunctionArgsContext _prevctx = _localctx;
		int _startState = 36;
		enterRecursionRule(_localctx, 36, RULE_functionArgs, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new ParamListNextLevelContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(260);
			argDeclaration();
			}
			_ctx.stop = _input.LT(-1);
			setState(267);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,25,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new ParamListCommaContext(new FunctionArgsContext(_parentctx, _parentState));
					((ParamListCommaContext)_localctx).e1 = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_functionArgs);
					setState(262);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(263);
					match(Comma);
					setState(264);
					((ParamListCommaContext)_localctx).e2 = argDeclaration();
					}
					} 
				}
				setState(269);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,25,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class ArgDeclarationContext extends ParserRuleContext {
		public TypeContext t;
		public Token argName;
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode ID() { return getToken(CPPParser.ID, 0); }
		public ArgDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_argDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).enterArgDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).exitArgDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CPPVisitor ) return ((CPPVisitor<? extends T>)visitor).visitArgDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArgDeclarationContext argDeclaration() throws RecognitionException {
		ArgDeclarationContext _localctx = new ArgDeclarationContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_argDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(270);
			((ArgDeclarationContext)_localctx).t = type();
			setState(271);
			((ArgDeclarationContext)_localctx).argName = match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementContext extends ParserRuleContext {
		public boolean ign;
		public ExpressionStatementContext e;
		public CodeBlockContext codeBlock() {
			return getRuleContext(CodeBlockContext.class,0);
		}
		public ExpressionStatementContext expressionStatement() {
			return getRuleContext(ExpressionStatementContext.class,0);
		}
		public IfBlockContext ifBlock() {
			return getRuleContext(IfBlockContext.class,0);
		}
		public IterationBlockContext iterationBlock() {
			return getRuleContext(IterationBlockContext.class,0);
		}
		public JumpStatementContext jumpStatement() {
			return getRuleContext(JumpStatementContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).exitStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CPPVisitor ) return ((CPPVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_statement);
		try {
			setState(280);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LeftBrace:
				enterOuterAlt(_localctx, 1);
				{
				setState(273);
				codeBlock();
				}
				break;
			case LeftParen:
			case Plus:
			case Minus:
			case Not:
			case Semi:
			case ID:
			case Number:
			case StringLiteral:
				enterOuterAlt(_localctx, 2);
				{
				setState(274);
				((StatementContext)_localctx).e = expressionStatement();
				((StatementContext)_localctx).ign =  ((StatementContext)_localctx).e.ign;
				}
				break;
			case If:
				enterOuterAlt(_localctx, 3);
				{
				setState(277);
				ifBlock();
				}
				break;
			case While:
			case For:
			case Do:
				enterOuterAlt(_localctx, 4);
				{
				setState(278);
				iterationBlock();
				}
				break;
			case Break:
			case Continue:
			case Return:
				enterOuterAlt(_localctx, 5);
				{
				setState(279);
				jumpStatement();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CodeBlockContext extends ParserRuleContext {
		public boolean ignoreNextLine;
		public BlockItemListContext scopedCode;
		public TerminalNode LeftBrace() { return getToken(CPPParser.LeftBrace, 0); }
		public TerminalNode RightBrace() { return getToken(CPPParser.RightBrace, 0); }
		public BlockItemListContext blockItemList() {
			return getRuleContext(BlockItemListContext.class,0);
		}
		public CodeBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_codeBlock; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).enterCodeBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).exitCodeBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CPPVisitor ) return ((CPPVisitor<? extends T>)visitor).visitCodeBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CodeBlockContext codeBlock() throws RecognitionException {
		CodeBlockContext _localctx = new CodeBlockContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_codeBlock);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(282);
			match(LeftBrace);
			setState(284);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Break) | (1L << Char) | (1L << Continue) | (1L << Double) | (1L << Float) | (1L << If) | (1L << Int) | (1L << Long) | (1L << Return) | (1L << Short) | (1L << Void) | (1L << While) | (1L << For) | (1L << Do) | (1L << LeftParen) | (1L << LeftBrace) | (1L << Plus) | (1L << Minus) | (1L << Not) | (1L << Semi) | (1L << ID) | (1L << Number) | (1L << StringLiteral))) != 0)) {
				{
				setState(283);
				((CodeBlockContext)_localctx).scopedCode = blockItemList(0);
				}
			}

			setState(286);
			match(RightBrace);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BlockItemListContext extends ParserRuleContext {
		public boolean ign;
		public BlockItemListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_blockItemList; }
	 
		public BlockItemListContext() { }
		public void copyFrom(BlockItemListContext ctx) {
			super.copyFrom(ctx);
			this.ign = ctx.ign;
		}
	}
	public static class BlockItemListNewLineContext extends BlockItemListContext {
		public BlockItemContext blockItem() {
			return getRuleContext(BlockItemContext.class,0);
		}
		public BlockItemListNewLineContext(BlockItemListContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).enterBlockItemListNewLine(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).exitBlockItemListNewLine(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CPPVisitor ) return ((CPPVisitor<? extends T>)visitor).visitBlockItemListNewLine(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BlockItemListNContext extends BlockItemListContext {
		public BlockItemListContext list;
		public BlockItemContext item;
		public BlockItemListContext blockItemList() {
			return getRuleContext(BlockItemListContext.class,0);
		}
		public BlockItemContext blockItem() {
			return getRuleContext(BlockItemContext.class,0);
		}
		public BlockItemListNContext(BlockItemListContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).enterBlockItemListN(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).exitBlockItemListN(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CPPVisitor ) return ((CPPVisitor<? extends T>)visitor).visitBlockItemListN(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockItemListContext blockItemList() throws RecognitionException {
		return blockItemList(0);
	}

	private BlockItemListContext blockItemList(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		BlockItemListContext _localctx = new BlockItemListContext(_ctx, _parentState);
		BlockItemListContext _prevctx = _localctx;
		int _startState = 44;
		enterRecursionRule(_localctx, 44, RULE_blockItemList, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new BlockItemListNewLineContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(289);
			blockItem();
			}
			_ctx.stop = _input.LT(-1);
			setState(297);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,28,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new BlockItemListNContext(new BlockItemListContext(_parentctx, _parentState));
					((BlockItemListNContext)_localctx).list = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_blockItemList);
					setState(291);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(292);
					((BlockItemListNContext)_localctx).item = blockItem();
					((BlockItemListNContext)_localctx).list.ign = ((BlockItemListNContext)_localctx).item.ign;
					}
					} 
				}
				setState(299);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,28,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class BlockItemContext extends ParserRuleContext {
		public boolean ign;
		public StatementContext e;
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public DeclarationContext declaration() {
			return getRuleContext(DeclarationContext.class,0);
		}
		public BlockItemContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_blockItem; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).enterBlockItem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).exitBlockItem(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CPPVisitor ) return ((CPPVisitor<? extends T>)visitor).visitBlockItem(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockItemContext blockItem() throws RecognitionException {
		BlockItemContext _localctx = new BlockItemContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_blockItem);
		try {
			setState(304);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,29,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(300);
				((BlockItemContext)_localctx).e = statement();
				((BlockItemContext)_localctx).ign =  ((BlockItemContext)_localctx).e.ign;
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(303);
				declaration();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionStatementContext extends ParserRuleContext {
		public boolean ign;
		public ExpressionStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expressionStatement; }
	 
		public ExpressionStatementContext() { }
		public void copyFrom(ExpressionStatementContext ctx) {
			super.copyFrom(ctx);
			this.ign = ctx.ign;
		}
	}
	public static class ExprEmptyContext extends ExpressionStatementContext {
		public TerminalNode Semi() { return getToken(CPPParser.Semi, 0); }
		public ExprEmptyContext(ExpressionStatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).enterExprEmpty(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).exitExprEmpty(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CPPVisitor ) return ((CPPVisitor<? extends T>)visitor).visitExprEmpty(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExprMeaningfulContext extends ExpressionStatementContext {
		public AssignmentExpressionContext e;
		public TerminalNode Semi() { return getToken(CPPParser.Semi, 0); }
		public AssignmentExpressionContext assignmentExpression() {
			return getRuleContext(AssignmentExpressionContext.class,0);
		}
		public ExprMeaningfulContext(ExpressionStatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).enterExprMeaningful(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).exitExprMeaningful(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CPPVisitor ) return ((CPPVisitor<? extends T>)visitor).visitExprMeaningful(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionStatementContext expressionStatement() throws RecognitionException {
		ExpressionStatementContext _localctx = new ExpressionStatementContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_expressionStatement);
		try {
			setState(311);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LeftParen:
			case Plus:
			case Minus:
			case Not:
			case ID:
			case Number:
			case StringLiteral:
				_localctx = new ExprMeaningfulContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(306);
				((ExprMeaningfulContext)_localctx).e = assignmentExpression();
				setState(307);
				match(Semi);
				}
				break;
			case Semi:
				_localctx = new ExprEmptyContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(309);
				match(Semi);
				((ExprEmptyContext)_localctx).ign =  true;
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IfBlockContext extends ParserRuleContext {
		public AssignmentExpressionContext condition;
		public CodeBlockContext ifCode;
		public CodeBlockContext elseCode;
		public TerminalNode If() { return getToken(CPPParser.If, 0); }
		public TerminalNode LeftParen() { return getToken(CPPParser.LeftParen, 0); }
		public TerminalNode RightParen() { return getToken(CPPParser.RightParen, 0); }
		public AssignmentExpressionContext assignmentExpression() {
			return getRuleContext(AssignmentExpressionContext.class,0);
		}
		public List<CodeBlockContext> codeBlock() {
			return getRuleContexts(CodeBlockContext.class);
		}
		public CodeBlockContext codeBlock(int i) {
			return getRuleContext(CodeBlockContext.class,i);
		}
		public TerminalNode Else() { return getToken(CPPParser.Else, 0); }
		public IfBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifBlock; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).enterIfBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).exitIfBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CPPVisitor ) return ((CPPVisitor<? extends T>)visitor).visitIfBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IfBlockContext ifBlock() throws RecognitionException {
		IfBlockContext _localctx = new IfBlockContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_ifBlock);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(313);
			match(If);
			setState(314);
			match(LeftParen);
			setState(315);
			((IfBlockContext)_localctx).condition = assignmentExpression();
			setState(316);
			match(RightParen);
			setState(317);
			((IfBlockContext)_localctx).ifCode = codeBlock();
			setState(320);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,31,_ctx) ) {
			case 1:
				{
				setState(318);
				match(Else);
				setState(319);
				((IfBlockContext)_localctx).elseCode = codeBlock();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IterationBlockContext extends ParserRuleContext {
		public Token name;
		public AssignmentExpressionContext e1;
		public CodeBlockContext code;
		public ForConditionContext forCond;
		public TerminalNode LeftParen() { return getToken(CPPParser.LeftParen, 0); }
		public TerminalNode RightParen() { return getToken(CPPParser.RightParen, 0); }
		public TerminalNode While() { return getToken(CPPParser.While, 0); }
		public AssignmentExpressionContext assignmentExpression() {
			return getRuleContext(AssignmentExpressionContext.class,0);
		}
		public CodeBlockContext codeBlock() {
			return getRuleContext(CodeBlockContext.class,0);
		}
		public TerminalNode For() { return getToken(CPPParser.For, 0); }
		public ForConditionContext forCondition() {
			return getRuleContext(ForConditionContext.class,0);
		}
		public TerminalNode Do() { return getToken(CPPParser.Do, 0); }
		public IterationBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_iterationBlock; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).enterIterationBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).exitIterationBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CPPVisitor ) return ((CPPVisitor<? extends T>)visitor).visitIterationBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IterationBlockContext iterationBlock() throws RecognitionException {
		IterationBlockContext _localctx = new IterationBlockContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_iterationBlock);
		try {
			setState(341);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case While:
				enterOuterAlt(_localctx, 1);
				{
				setState(322);
				((IterationBlockContext)_localctx).name = match(While);
				setState(323);
				match(LeftParen);
				setState(324);
				((IterationBlockContext)_localctx).e1 = assignmentExpression();
				setState(325);
				match(RightParen);
				setState(326);
				((IterationBlockContext)_localctx).code = codeBlock();
				}
				break;
			case For:
				enterOuterAlt(_localctx, 2);
				{
				setState(328);
				((IterationBlockContext)_localctx).name = match(For);
				setState(329);
				match(LeftParen);
				setState(330);
				((IterationBlockContext)_localctx).forCond = forCondition();
				setState(331);
				match(RightParen);
				setState(332);
				((IterationBlockContext)_localctx).code = codeBlock();
				}
				break;
			case Do:
				enterOuterAlt(_localctx, 3);
				{
				setState(334);
				((IterationBlockContext)_localctx).name = match(Do);
				setState(335);
				((IterationBlockContext)_localctx).code = codeBlock();
				setState(336);
				match(While);
				setState(337);
				match(LeftParen);
				setState(338);
				((IterationBlockContext)_localctx).e1 = assignmentExpression();
				setState(339);
				match(RightParen);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ForConditionContext extends ParserRuleContext {
		public ForDeclarationContext d;
		public ForExpressionContext e1;
		public ForExpressionContext e2;
		public AssignmentExpressionContext assignExpr;
		public List<TerminalNode> Semi() { return getTokens(CPPParser.Semi); }
		public TerminalNode Semi(int i) {
			return getToken(CPPParser.Semi, i);
		}
		public ForDeclarationContext forDeclaration() {
			return getRuleContext(ForDeclarationContext.class,0);
		}
		public List<ForExpressionContext> forExpression() {
			return getRuleContexts(ForExpressionContext.class);
		}
		public ForExpressionContext forExpression(int i) {
			return getRuleContext(ForExpressionContext.class,i);
		}
		public AssignmentExpressionContext assignmentExpression() {
			return getRuleContext(AssignmentExpressionContext.class,0);
		}
		public ForConditionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forCondition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).enterForCondition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).exitForCondition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CPPVisitor ) return ((CPPVisitor<? extends T>)visitor).visitForCondition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForConditionContext forCondition() throws RecognitionException {
		ForConditionContext _localctx = new ForConditionContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_forCondition);
		int _la;
		try {
			setState(365);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,39,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(344);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Char) | (1L << Double) | (1L << Float) | (1L << Int) | (1L << Long) | (1L << Short) | (1L << Void) | (1L << ID))) != 0)) {
					{
					setState(343);
					((ForConditionContext)_localctx).d = forDeclaration();
					}
				}

				setState(346);
				match(Semi);
				setState(348);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LeftParen) | (1L << Plus) | (1L << Minus) | (1L << Not) | (1L << ID) | (1L << Number) | (1L << StringLiteral))) != 0)) {
					{
					setState(347);
					((ForConditionContext)_localctx).e1 = forExpression(0);
					}
				}

				setState(350);
				match(Semi);
				setState(352);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LeftParen) | (1L << Plus) | (1L << Minus) | (1L << Not) | (1L << ID) | (1L << Number) | (1L << StringLiteral))) != 0)) {
					{
					setState(351);
					((ForConditionContext)_localctx).e2 = forExpression(0);
					}
				}

				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(355);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LeftParen) | (1L << Plus) | (1L << Minus) | (1L << Not) | (1L << ID) | (1L << Number) | (1L << StringLiteral))) != 0)) {
					{
					setState(354);
					((ForConditionContext)_localctx).assignExpr = assignmentExpression();
					}
				}

				setState(357);
				match(Semi);
				setState(359);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LeftParen) | (1L << Plus) | (1L << Minus) | (1L << Not) | (1L << ID) | (1L << Number) | (1L << StringLiteral))) != 0)) {
					{
					setState(358);
					((ForConditionContext)_localctx).e1 = forExpression(0);
					}
				}

				setState(361);
				match(Semi);
				setState(363);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LeftParen) | (1L << Plus) | (1L << Minus) | (1L << Not) | (1L << ID) | (1L << Number) | (1L << StringLiteral))) != 0)) {
					{
					setState(362);
					((ForConditionContext)_localctx).e2 = forExpression(0);
					}
				}

				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ForExpressionContext extends ParserRuleContext {
		public ForExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forExpression; }
	 
		public ForExpressionContext() { }
		public void copyFrom(ForExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ForExprNextLevelContext extends ForExpressionContext {
		public AssignmentExpressionContext assignmentExpression() {
			return getRuleContext(AssignmentExpressionContext.class,0);
		}
		public ForExprNextLevelContext(ForExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).enterForExprNextLevel(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).exitForExprNextLevel(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CPPVisitor ) return ((CPPVisitor<? extends T>)visitor).visitForExprNextLevel(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ForExpr1Context extends ForExpressionContext {
		public ForExpressionContext forExprList;
		public AssignmentExpressionContext assignExpr;
		public TerminalNode Comma() { return getToken(CPPParser.Comma, 0); }
		public ForExpressionContext forExpression() {
			return getRuleContext(ForExpressionContext.class,0);
		}
		public AssignmentExpressionContext assignmentExpression() {
			return getRuleContext(AssignmentExpressionContext.class,0);
		}
		public ForExpr1Context(ForExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).enterForExpr1(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).exitForExpr1(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CPPVisitor ) return ((CPPVisitor<? extends T>)visitor).visitForExpr1(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForExpressionContext forExpression() throws RecognitionException {
		return forExpression(0);
	}

	private ForExpressionContext forExpression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ForExpressionContext _localctx = new ForExpressionContext(_ctx, _parentState);
		ForExpressionContext _prevctx = _localctx;
		int _startState = 56;
		enterRecursionRule(_localctx, 56, RULE_forExpression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new ForExprNextLevelContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(368);
			assignmentExpression();
			}
			_ctx.stop = _input.LT(-1);
			setState(375);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,40,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new ForExpr1Context(new ForExpressionContext(_parentctx, _parentState));
					((ForExpr1Context)_localctx).forExprList = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_forExpression);
					setState(370);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(371);
					match(Comma);
					setState(372);
					((ForExpr1Context)_localctx).assignExpr = assignmentExpression();
					}
					} 
				}
				setState(377);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,40,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class ForDeclarationContext extends ParserRuleContext {
		public TypeContext t;
		public InitialValueDeclaratorContext initDecl;
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public InitialValueDeclaratorContext initialValueDeclarator() {
			return getRuleContext(InitialValueDeclaratorContext.class,0);
		}
		public ForDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).enterForDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).exitForDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CPPVisitor ) return ((CPPVisitor<? extends T>)visitor).visitForDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForDeclarationContext forDeclaration() throws RecognitionException {
		ForDeclarationContext _localctx = new ForDeclarationContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_forDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(378);
			((ForDeclarationContext)_localctx).t = type();
			setState(379);
			((ForDeclarationContext)_localctx).initDecl = initialValueDeclarator();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StructContext extends ParserRuleContext {
		public Token n;
		public StructDeclarationListContext list;
		public TerminalNode LeftBrace() { return getToken(CPPParser.LeftBrace, 0); }
		public TerminalNode RightBrace() { return getToken(CPPParser.RightBrace, 0); }
		public TerminalNode ID() { return getToken(CPPParser.ID, 0); }
		public StructDeclarationListContext structDeclarationList() {
			return getRuleContext(StructDeclarationListContext.class,0);
		}
		public StructContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_struct; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).enterStruct(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).exitStruct(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CPPVisitor ) return ((CPPVisitor<? extends T>)visitor).visitStruct(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StructContext struct() throws RecognitionException {
		StructContext _localctx = new StructContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_struct);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(381);
			match(T__1);
			setState(382);
			((StructContext)_localctx).n = match(ID);
			setState(383);
			match(LeftBrace);
			setState(385);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Char) | (1L << Double) | (1L << Float) | (1L << Int) | (1L << Long) | (1L << Short) | (1L << Void) | (1L << ID))) != 0)) {
				{
				setState(384);
				((StructContext)_localctx).list = structDeclarationList(0);
				}
			}

			setState(387);
			match(RightBrace);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StructDeclarationListContext extends ParserRuleContext {
		public StructDeclarationListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_structDeclarationList; }
	 
		public StructDeclarationListContext() { }
		public void copyFrom(StructDeclarationListContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class StructDec1Context extends StructDeclarationListContext {
		public StructDeclarationListContext fields;
		public DeclarationContext decl;
		public StructDeclarationListContext structDeclarationList() {
			return getRuleContext(StructDeclarationListContext.class,0);
		}
		public DeclarationContext declaration() {
			return getRuleContext(DeclarationContext.class,0);
		}
		public StructDec1Context(StructDeclarationListContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).enterStructDec1(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).exitStructDec1(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CPPVisitor ) return ((CPPVisitor<? extends T>)visitor).visitStructDec1(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StructDecNextLevelContext extends StructDeclarationListContext {
		public DeclarationContext declaration() {
			return getRuleContext(DeclarationContext.class,0);
		}
		public StructDecNextLevelContext(StructDeclarationListContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).enterStructDecNextLevel(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).exitStructDecNextLevel(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CPPVisitor ) return ((CPPVisitor<? extends T>)visitor).visitStructDecNextLevel(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StructDeclarationListContext structDeclarationList() throws RecognitionException {
		return structDeclarationList(0);
	}

	private StructDeclarationListContext structDeclarationList(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		StructDeclarationListContext _localctx = new StructDeclarationListContext(_ctx, _parentState);
		StructDeclarationListContext _prevctx = _localctx;
		int _startState = 62;
		enterRecursionRule(_localctx, 62, RULE_structDeclarationList, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new StructDecNextLevelContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(390);
			declaration();
			}
			_ctx.stop = _input.LT(-1);
			setState(396);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,42,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new StructDec1Context(new StructDeclarationListContext(_parentctx, _parentState));
					((StructDec1Context)_localctx).fields = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_structDeclarationList);
					setState(392);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(393);
					((StructDec1Context)_localctx).decl = declaration();
					}
					} 
				}
				setState(398);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,42,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class JumpStatementContext extends ParserRuleContext {
		public JumpStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_jumpStatement; }
	 
		public JumpStatementContext() { }
		public void copyFrom(JumpStatementContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class JumpContinueContext extends JumpStatementContext {
		public TerminalNode Continue() { return getToken(CPPParser.Continue, 0); }
		public TerminalNode Semi() { return getToken(CPPParser.Semi, 0); }
		public JumpContinueContext(JumpStatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).enterJumpContinue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).exitJumpContinue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CPPVisitor ) return ((CPPVisitor<? extends T>)visitor).visitJumpContinue(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class JumpReturnContext extends JumpStatementContext {
		public AssignmentExpressionContext returnValue;
		public TerminalNode Return() { return getToken(CPPParser.Return, 0); }
		public TerminalNode Semi() { return getToken(CPPParser.Semi, 0); }
		public AssignmentExpressionContext assignmentExpression() {
			return getRuleContext(AssignmentExpressionContext.class,0);
		}
		public JumpReturnContext(JumpStatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).enterJumpReturn(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).exitJumpReturn(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CPPVisitor ) return ((CPPVisitor<? extends T>)visitor).visitJumpReturn(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class JumpBreakContext extends JumpStatementContext {
		public TerminalNode Break() { return getToken(CPPParser.Break, 0); }
		public TerminalNode Semi() { return getToken(CPPParser.Semi, 0); }
		public JumpBreakContext(JumpStatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).enterJumpBreak(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).exitJumpBreak(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CPPVisitor ) return ((CPPVisitor<? extends T>)visitor).visitJumpBreak(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JumpStatementContext jumpStatement() throws RecognitionException {
		JumpStatementContext _localctx = new JumpStatementContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_jumpStatement);
		int _la;
		try {
			setState(408);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Continue:
				_localctx = new JumpContinueContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(399);
				match(Continue);
				setState(400);
				match(Semi);
				}
				break;
			case Break:
				_localctx = new JumpBreakContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(401);
				match(Break);
				setState(402);
				match(Semi);
				}
				break;
			case Return:
				_localctx = new JumpReturnContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(403);
				match(Return);
				setState(405);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LeftParen) | (1L << Plus) | (1L << Minus) | (1L << Not) | (1L << ID) | (1L << Number) | (1L << StringLiteral))) != 0)) {
					{
					setState(404);
					((JumpReturnContext)_localctx).returnValue = assignmentExpression();
					}
				}

				setState(407);
				match(Semi);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TranslationUnitContext extends ParserRuleContext {
		public ExternalDefinitionContext externalDefinition() {
			return getRuleContext(ExternalDefinitionContext.class,0);
		}
		public TranslationUnitContext translationUnit() {
			return getRuleContext(TranslationUnitContext.class,0);
		}
		public TranslationUnitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_translationUnit; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).enterTranslationUnit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).exitTranslationUnit(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CPPVisitor ) return ((CPPVisitor<? extends T>)visitor).visitTranslationUnit(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TranslationUnitContext translationUnit() throws RecognitionException {
		return translationUnit(0);
	}

	private TranslationUnitContext translationUnit(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		TranslationUnitContext _localctx = new TranslationUnitContext(_ctx, _parentState);
		TranslationUnitContext _prevctx = _localctx;
		int _startState = 66;
		enterRecursionRule(_localctx, 66, RULE_translationUnit, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(411);
			externalDefinition();
			}
			_ctx.stop = _input.LT(-1);
			setState(417);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,45,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new TranslationUnitContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_translationUnit);
					setState(413);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(414);
					externalDefinition();
					}
					} 
				}
				setState(419);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,45,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class ExternalDefinitionContext extends ParserRuleContext {
		public FunctionContext function() {
			return getRuleContext(FunctionContext.class,0);
		}
		public StructContext struct() {
			return getRuleContext(StructContext.class,0);
		}
		public ExternalDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_externalDefinition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).enterExternalDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).exitExternalDefinition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CPPVisitor ) return ((CPPVisitor<? extends T>)visitor).visitExternalDefinition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExternalDefinitionContext externalDefinition() throws RecognitionException {
		ExternalDefinitionContext _localctx = new ExternalDefinitionContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_externalDefinition);
		try {
			setState(422);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Char:
			case Double:
			case Float:
			case Int:
			case Long:
			case Short:
			case Void:
			case ID:
				enterOuterAlt(_localctx, 1);
				{
				setState(420);
				function();
				}
				break;
			case T__1:
				enterOuterAlt(_localctx, 2);
				{
				setState(421);
				struct();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionContext extends ParserRuleContext {
		public TypeContext returnType;
		public DeclaratorContext definition;
		public CodeBlockContext code;
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public DeclaratorContext declarator() {
			return getRuleContext(DeclaratorContext.class,0);
		}
		public CodeBlockContext codeBlock() {
			return getRuleContext(CodeBlockContext.class,0);
		}
		public FunctionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_function; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).enterFunction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CPPListener ) ((CPPListener)listener).exitFunction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CPPVisitor ) return ((CPPVisitor<? extends T>)visitor).visitFunction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionContext function() throws RecognitionException {
		FunctionContext _localctx = new FunctionContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_function);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(424);
			((FunctionContext)_localctx).returnType = type();
			setState(425);
			((FunctionContext)_localctx).definition = declarator(0);
			setState(426);
			((FunctionContext)_localctx).code = codeBlock();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 2:
			return postfixExpression_sempred((PostfixExpressionContext)_localctx, predIndex);
		case 3:
			return argumentExpressionList_sempred((ArgumentExpressionListContext)_localctx, predIndex);
		case 6:
			return multiplicativeExpression_sempred((MultiplicativeExpressionContext)_localctx, predIndex);
		case 7:
			return additiveExpression_sempred((AdditiveExpressionContext)_localctx, predIndex);
		case 8:
			return relationalExpression_sempred((RelationalExpressionContext)_localctx, predIndex);
		case 9:
			return equalityExpression_sempred((EqualityExpressionContext)_localctx, predIndex);
		case 10:
			return logicalAndExpression_sempred((LogicalAndExpressionContext)_localctx, predIndex);
		case 11:
			return logicalOrExpression_sempred((LogicalOrExpressionContext)_localctx, predIndex);
		case 17:
			return declarator_sempred((DeclaratorContext)_localctx, predIndex);
		case 18:
			return functionArgs_sempred((FunctionArgsContext)_localctx, predIndex);
		case 22:
			return blockItemList_sempred((BlockItemListContext)_localctx, predIndex);
		case 28:
			return forExpression_sempred((ForExpressionContext)_localctx, predIndex);
		case 31:
			return structDeclarationList_sempred((StructDeclarationListContext)_localctx, predIndex);
		case 33:
			return translationUnit_sempred((TranslationUnitContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean postfixExpression_sempred(PostfixExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 2);
		case 1:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean argumentExpressionList_sempred(ArgumentExpressionListContext _localctx, int predIndex) {
		switch (predIndex) {
		case 2:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean multiplicativeExpression_sempred(MultiplicativeExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 3:
			return precpred(_ctx, 3);
		case 4:
			return precpred(_ctx, 2);
		case 5:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean additiveExpression_sempred(AdditiveExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 6:
			return precpred(_ctx, 2);
		case 7:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean relationalExpression_sempred(RelationalExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 8:
			return precpred(_ctx, 4);
		case 9:
			return precpred(_ctx, 3);
		case 10:
			return precpred(_ctx, 2);
		case 11:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean equalityExpression_sempred(EqualityExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 12:
			return precpred(_ctx, 2);
		case 13:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean logicalAndExpression_sempred(LogicalAndExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 14:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean logicalOrExpression_sempred(LogicalOrExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 15:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean declarator_sempred(DeclaratorContext _localctx, int predIndex) {
		switch (predIndex) {
		case 16:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean functionArgs_sempred(FunctionArgsContext _localctx, int predIndex) {
		switch (predIndex) {
		case 17:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean blockItemList_sempred(BlockItemListContext _localctx, int predIndex) {
		switch (predIndex) {
		case 18:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean forExpression_sempred(ForExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 19:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean structDeclarationList_sempred(StructDeclarationListContext _localctx, int predIndex) {
		switch (predIndex) {
		case 20:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean translationUnit_sempred(TranslationUnitContext _localctx, int predIndex) {
		switch (predIndex) {
		case 21:
			return precpred(_ctx, 1);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\38\u01af\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\3\2\5\2L\n\2\3\2\3\2\3\3\3\3\3\3\6\3S\n"+
		"\3\r\3\16\3T\3\3\3\3\3\3\3\3\5\3[\n\3\3\4\3\4\3\4\3\4\3\4\3\4\5\4c\n\4"+
		"\3\4\3\4\3\4\3\4\7\4i\n\4\f\4\16\4l\13\4\3\5\3\5\3\5\3\5\3\5\3\5\7\5t"+
		"\n\5\f\5\16\5w\13\5\3\6\3\6\3\6\3\6\5\6}\n\6\3\7\3\7\3\7\5\7\u0082\n\7"+
		"\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\7\b\u0090\n\b\f\b\16"+
		"\b\u0093\13\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\7\t\u009e\n\t\f\t\16"+
		"\t\u00a1\13\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n"+
		"\3\n\7\n\u00b2\n\n\f\n\16\n\u00b5\13\n\3\13\3\13\3\13\3\13\3\13\3\13\3"+
		"\13\3\13\3\13\7\13\u00c0\n\13\f\13\16\13\u00c3\13\13\3\f\3\f\3\f\3\f\3"+
		"\f\3\f\7\f\u00cb\n\f\f\f\16\f\u00ce\13\f\3\r\3\r\3\r\3\r\3\r\3\r\7\r\u00d6"+
		"\n\r\f\r\16\r\u00d9\13\r\3\16\3\16\3\16\3\16\3\16\5\16\u00e0\n\16\3\17"+
		"\3\17\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\21\5\21\u00ed\n\21\3\22"+
		"\3\22\5\22\u00f1\n\22\3\23\3\23\3\23\3\23\3\23\3\23\5\23\u00f9\n\23\3"+
		"\23\3\23\3\23\5\23\u00fe\n\23\3\23\7\23\u0101\n\23\f\23\16\23\u0104\13"+
		"\23\3\24\3\24\3\24\3\24\3\24\3\24\7\24\u010c\n\24\f\24\16\24\u010f\13"+
		"\24\3\25\3\25\3\25\3\26\3\26\3\26\3\26\3\26\3\26\3\26\5\26\u011b\n\26"+
		"\3\27\3\27\5\27\u011f\n\27\3\27\3\27\3\30\3\30\3\30\3\30\3\30\3\30\3\30"+
		"\7\30\u012a\n\30\f\30\16\30\u012d\13\30\3\31\3\31\3\31\3\31\5\31\u0133"+
		"\n\31\3\32\3\32\3\32\3\32\3\32\5\32\u013a\n\32\3\33\3\33\3\33\3\33\3\33"+
		"\3\33\3\33\5\33\u0143\n\33\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34"+
		"\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\5\34\u0158\n\34\3\35"+
		"\5\35\u015b\n\35\3\35\3\35\5\35\u015f\n\35\3\35\3\35\5\35\u0163\n\35\3"+
		"\35\5\35\u0166\n\35\3\35\3\35\5\35\u016a\n\35\3\35\3\35\5\35\u016e\n\35"+
		"\5\35\u0170\n\35\3\36\3\36\3\36\3\36\3\36\3\36\7\36\u0178\n\36\f\36\16"+
		"\36\u017b\13\36\3\37\3\37\3\37\3 \3 \3 \3 \5 \u0184\n \3 \3 \3!\3!\3!"+
		"\3!\3!\7!\u018d\n!\f!\16!\u0190\13!\3\"\3\"\3\"\3\"\3\"\3\"\5\"\u0198"+
		"\n\"\3\"\5\"\u019b\n\"\3#\3#\3#\3#\3#\7#\u01a2\n#\f#\16#\u01a5\13#\3$"+
		"\3$\5$\u01a9\n$\3%\3%\3%\3%\3%\2\20\6\b\16\20\22\24\26\30$&.:@D&\2\4\6"+
		"\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\62\64\668:<>@BDFH\2\4\3"+
		"\2).\7\2\6\6\b\b\n\n\f\r\17\20\2\u01c4\2K\3\2\2\2\4Z\3\2\2\2\6\\\3\2\2"+
		"\2\bm\3\2\2\2\n|\3\2\2\2\f\u0081\3\2\2\2\16\u0083\3\2\2\2\20\u0094\3\2"+
		"\2\2\22\u00a2\3\2\2\2\24\u00b6\3\2\2\2\26\u00c4\3\2\2\2\30\u00cf\3\2\2"+
		"\2\32\u00df\3\2\2\2\34\u00e1\3\2\2\2\36\u00e3\3\2\2\2 \u00ec\3\2\2\2\""+
		"\u00f0\3\2\2\2$\u00f8\3\2\2\2&\u0105\3\2\2\2(\u0110\3\2\2\2*\u011a\3\2"+
		"\2\2,\u011c\3\2\2\2.\u0122\3\2\2\2\60\u0132\3\2\2\2\62\u0139\3\2\2\2\64"+
		"\u013b\3\2\2\2\66\u0157\3\2\2\28\u016f\3\2\2\2:\u0171\3\2\2\2<\u017c\3"+
		"\2\2\2>\u017f\3\2\2\2@\u0187\3\2\2\2B\u019a\3\2\2\2D\u019c\3\2\2\2F\u01a8"+
		"\3\2\2\2H\u01aa\3\2\2\2JL\5D#\2KJ\3\2\2\2KL\3\2\2\2LM\3\2\2\2MN\7\2\2"+
		"\3N\3\3\2\2\2O[\7\61\2\2P[\7\62\2\2QS\7\64\2\2RQ\3\2\2\2ST\3\2\2\2TR\3"+
		"\2\2\2TU\3\2\2\2U[\3\2\2\2VW\7\24\2\2WX\5\32\16\2XY\7\25\2\2Y[\3\2\2\2"+
		"ZO\3\2\2\2ZP\3\2\2\2ZR\3\2\2\2ZV\3\2\2\2[\5\3\2\2\2\\]\b\4\1\2]^\5\4\3"+
		"\2^j\3\2\2\2_`\f\4\2\2`b\7\24\2\2ac\5\b\5\2ba\3\2\2\2bc\3\2\2\2cd\3\2"+
		"\2\2di\7\25\2\2ef\f\3\2\2fg\7\3\2\2gi\7\61\2\2h_\3\2\2\2he\3\2\2\2il\3"+
		"\2\2\2jh\3\2\2\2jk\3\2\2\2k\7\3\2\2\2lj\3\2\2\2mn\b\5\1\2no\5\32\16\2"+
		"ou\3\2\2\2pq\f\3\2\2qr\7(\2\2rt\5\32\16\2sp\3\2\2\2tw\3\2\2\2us\3\2\2"+
		"\2uv\3\2\2\2v\t\3\2\2\2wu\3\2\2\2x}\5\6\4\2yz\5\f\7\2z{\5\n\6\2{}\3\2"+
		"\2\2|x\3\2\2\2|y\3\2\2\2}\13\3\2\2\2~\u0082\7\36\2\2\177\u0082\7\37\2"+
		"\2\u0080\u0082\7%\2\2\u0081~\3\2\2\2\u0081\177\3\2\2\2\u0081\u0080\3\2"+
		"\2\2\u0082\r\3\2\2\2\u0083\u0084\b\b\1\2\u0084\u0085\5\n\6\2\u0085\u0091"+
		"\3\2\2\2\u0086\u0087\f\5\2\2\u0087\u0088\7 \2\2\u0088\u0090\5\n\6\2\u0089"+
		"\u008a\f\4\2\2\u008a\u008b\7!\2\2\u008b\u0090\5\n\6\2\u008c\u008d\f\3"+
		"\2\2\u008d\u008e\7\"\2\2\u008e\u0090\5\n\6\2\u008f\u0086\3\2\2\2\u008f"+
		"\u0089\3\2\2\2\u008f\u008c\3\2\2\2\u0090\u0093\3\2\2\2\u0091\u008f\3\2"+
		"\2\2\u0091\u0092\3\2\2\2\u0092\17\3\2\2\2\u0093\u0091\3\2\2\2\u0094\u0095"+
		"\b\t\1\2\u0095\u0096\5\16\b\2\u0096\u009f\3\2\2\2\u0097\u0098\f\4\2\2"+
		"\u0098\u0099\7\36\2\2\u0099\u009e\5\16\b\2\u009a\u009b\f\3\2\2\u009b\u009c"+
		"\7\37\2\2\u009c\u009e\5\16\b\2\u009d\u0097\3\2\2\2\u009d\u009a\3\2\2\2"+
		"\u009e\u00a1\3\2\2\2\u009f\u009d\3\2\2\2\u009f\u00a0\3\2\2\2\u00a0\21"+
		"\3\2\2\2\u00a1\u009f\3\2\2\2\u00a2\u00a3\b\n\1\2\u00a3\u00a4\5\20\t\2"+
		"\u00a4\u00b3\3\2\2\2\u00a5\u00a6\f\6\2\2\u00a6\u00a7\7\32\2\2\u00a7\u00b2"+
		"\5\20\t\2\u00a8\u00a9\f\5\2\2\u00a9\u00aa\7\34\2\2\u00aa\u00b2\5\20\t"+
		"\2\u00ab\u00ac\f\4\2\2\u00ac\u00ad\7\33\2\2\u00ad\u00b2\5\20\t\2\u00ae"+
		"\u00af\f\3\2\2\u00af\u00b0\7\35\2\2\u00b0\u00b2\5\20\t\2\u00b1\u00a5\3"+
		"\2\2\2\u00b1\u00a8\3\2\2\2\u00b1\u00ab\3\2\2\2\u00b1\u00ae\3\2\2\2\u00b2"+
		"\u00b5\3\2\2\2\u00b3\u00b1\3\2\2\2\u00b3\u00b4\3\2\2\2\u00b4\23\3\2\2"+
		"\2\u00b5\u00b3\3\2\2\2\u00b6\u00b7\b\13\1\2\u00b7\u00b8\5\22\n\2\u00b8"+
		"\u00c1\3\2\2\2\u00b9\u00ba\f\4\2\2\u00ba\u00bb\7/\2\2\u00bb\u00c0\5\22"+
		"\n\2\u00bc\u00bd\f\3\2\2\u00bd\u00be\7\60\2\2\u00be\u00c0\5\22\n\2\u00bf"+
		"\u00b9\3\2\2\2\u00bf\u00bc\3\2\2\2\u00c0\u00c3\3\2\2\2\u00c1\u00bf\3\2"+
		"\2\2\u00c1\u00c2\3\2\2\2\u00c2\25\3\2\2\2\u00c3\u00c1\3\2\2\2\u00c4\u00c5"+
		"\b\f\1\2\u00c5\u00c6\5\24\13\2\u00c6\u00cc\3\2\2\2\u00c7\u00c8\f\3\2\2"+
		"\u00c8\u00c9\7#\2\2\u00c9\u00cb\5\24\13\2\u00ca\u00c7\3\2\2\2\u00cb\u00ce"+
		"\3\2\2\2\u00cc\u00ca\3\2\2\2\u00cc\u00cd\3\2\2\2\u00cd\27\3\2\2\2\u00ce"+
		"\u00cc\3\2\2\2\u00cf\u00d0\b\r\1\2\u00d0\u00d1\5\26\f\2\u00d1\u00d7\3"+
		"\2\2\2\u00d2\u00d3\f\3\2\2\u00d3\u00d4\7$\2\2\u00d4\u00d6\5\26\f\2\u00d5"+
		"\u00d2\3\2\2\2\u00d6\u00d9\3\2\2\2\u00d7\u00d5\3\2\2\2\u00d7\u00d8\3\2"+
		"\2\2\u00d8\31\3\2\2\2\u00d9\u00d7\3\2\2\2\u00da\u00e0\5\30\r\2\u00db\u00dc"+
		"\5\n\6\2\u00dc\u00dd\5\34\17\2\u00dd\u00de\5\32\16\2\u00de\u00e0\3\2\2"+
		"\2\u00df\u00da\3\2\2\2\u00df\u00db\3\2\2\2\u00e0\33\3\2\2\2\u00e1\u00e2"+
		"\t\2\2\2\u00e2\35\3\2\2\2\u00e3\u00e4\5\"\22\2\u00e4\u00e5\5 \21\2\u00e5"+
		"\u00e6\7\'\2\2\u00e6\37\3\2\2\2\u00e7\u00ed\5$\23\2\u00e8\u00e9\5$\23"+
		"\2\u00e9\u00ea\7)\2\2\u00ea\u00eb\5\32\16\2\u00eb\u00ed\3\2\2\2\u00ec"+
		"\u00e7\3\2\2\2\u00ec\u00e8\3\2\2\2\u00ed!\3\2\2\2\u00ee\u00f1\t\3\2\2"+
		"\u00ef\u00f1\7\61\2\2\u00f0\u00ee\3\2\2\2\u00f0\u00ef\3\2\2\2\u00f1#\3"+
		"\2\2\2\u00f2\u00f3\b\23\1\2\u00f3\u00f9\7\61\2\2\u00f4\u00f5\7\24\2\2"+
		"\u00f5\u00f6\5$\23\2\u00f6\u00f7\7\25\2\2\u00f7\u00f9\3\2\2\2\u00f8\u00f2"+
		"\3\2\2\2\u00f8\u00f4\3\2\2\2\u00f9\u0102\3\2\2\2\u00fa\u00fb\f\3\2\2\u00fb"+
		"\u00fd\7\24\2\2\u00fc\u00fe\5&\24\2\u00fd\u00fc\3\2\2\2\u00fd\u00fe\3"+
		"\2\2\2\u00fe\u00ff\3\2\2\2\u00ff\u0101\7\25\2\2\u0100\u00fa\3\2\2\2\u0101"+
		"\u0104\3\2\2\2\u0102\u0100\3\2\2\2\u0102\u0103\3\2\2\2\u0103%\3\2\2\2"+
		"\u0104\u0102\3\2\2\2\u0105\u0106\b\24\1\2\u0106\u0107\5(\25\2\u0107\u010d"+
		"\3\2\2\2\u0108\u0109\f\3\2\2\u0109\u010a\7(\2\2\u010a\u010c\5(\25\2\u010b"+
		"\u0108\3\2\2\2\u010c\u010f\3\2\2\2\u010d\u010b\3\2\2\2\u010d\u010e\3\2"+
		"\2\2\u010e\'\3\2\2\2\u010f\u010d\3\2\2\2\u0110\u0111\5\"\22\2\u0111\u0112"+
		"\7\61\2\2\u0112)\3\2\2\2\u0113\u011b\5,\27\2\u0114\u0115\5\62\32\2\u0115"+
		"\u0116\b\26\1\2\u0116\u011b\3\2\2\2\u0117\u011b\5\64\33\2\u0118\u011b"+
		"\5\66\34\2\u0119\u011b\5B\"\2\u011a\u0113\3\2\2\2\u011a\u0114\3\2\2\2"+
		"\u011a\u0117\3\2\2\2\u011a\u0118\3\2\2\2\u011a\u0119\3\2\2\2\u011b+\3"+
		"\2\2\2\u011c\u011e\7\30\2\2\u011d\u011f\5.\30\2\u011e\u011d\3\2\2\2\u011e"+
		"\u011f\3\2\2\2\u011f\u0120\3\2\2\2\u0120\u0121\7\31\2\2\u0121-\3\2\2\2"+
		"\u0122\u0123\b\30\1\2\u0123\u0124\5\60\31\2\u0124\u012b\3\2\2\2\u0125"+
		"\u0126\f\3\2\2\u0126\u0127\5\60\31\2\u0127\u0128\b\30\1\2\u0128\u012a"+
		"\3\2\2\2\u0129\u0125\3\2\2\2\u012a\u012d\3\2\2\2\u012b\u0129\3\2\2\2\u012b"+
		"\u012c\3\2\2\2\u012c/\3\2\2\2\u012d\u012b\3\2\2\2\u012e\u012f\5*\26\2"+
		"\u012f\u0130\b\31\1\2\u0130\u0133\3\2\2\2\u0131\u0133\5\36\20\2\u0132"+
		"\u012e\3\2\2\2\u0132\u0131\3\2\2\2\u0133\61\3\2\2\2\u0134\u0135\5\32\16"+
		"\2\u0135\u0136\7\'\2\2\u0136\u013a\3\2\2\2\u0137\u0138\7\'\2\2\u0138\u013a"+
		"\b\32\1\2\u0139\u0134\3\2\2\2\u0139\u0137\3\2\2\2\u013a\63\3\2\2\2\u013b"+
		"\u013c\7\13\2\2\u013c\u013d\7\24\2\2\u013d\u013e\5\32\16\2\u013e\u013f"+
		"\7\25\2\2\u013f\u0142\5,\27\2\u0140\u0141\7\t\2\2\u0141\u0143\5,\27\2"+
		"\u0142\u0140\3\2\2\2\u0142\u0143\3\2\2\2\u0143\65\3\2\2\2\u0144\u0145"+
		"\7\21\2\2\u0145\u0146\7\24\2\2\u0146\u0147\5\32\16\2\u0147\u0148\7\25"+
		"\2\2\u0148\u0149\5,\27\2\u0149\u0158\3\2\2\2\u014a\u014b\7\22\2\2\u014b"+
		"\u014c\7\24\2\2\u014c\u014d\58\35\2\u014d\u014e\7\25\2\2\u014e\u014f\5"+
		",\27\2\u014f\u0158\3\2\2\2\u0150\u0151\7\23\2\2\u0151\u0152\5,\27\2\u0152"+
		"\u0153\7\21\2\2\u0153\u0154\7\24\2\2\u0154\u0155\5\32\16\2\u0155\u0156"+
		"\7\25\2\2\u0156\u0158\3\2\2\2\u0157\u0144\3\2\2\2\u0157\u014a\3\2\2\2"+
		"\u0157\u0150\3\2\2\2\u0158\67\3\2\2\2\u0159\u015b\5<\37\2\u015a\u0159"+
		"\3\2\2\2\u015a\u015b\3\2\2\2\u015b\u015c\3\2\2\2\u015c\u015e\7\'\2\2\u015d"+
		"\u015f\5:\36\2\u015e\u015d\3\2\2\2\u015e\u015f\3\2\2\2\u015f\u0160\3\2"+
		"\2\2\u0160\u0162\7\'\2\2\u0161\u0163\5:\36\2\u0162\u0161\3\2\2\2\u0162"+
		"\u0163\3\2\2\2\u0163\u0170\3\2\2\2\u0164\u0166\5\32\16\2\u0165\u0164\3"+
		"\2\2\2\u0165\u0166\3\2\2\2\u0166\u0167\3\2\2\2\u0167\u0169\7\'\2\2\u0168"+
		"\u016a\5:\36\2\u0169\u0168\3\2\2\2\u0169\u016a\3\2\2\2\u016a\u016b\3\2"+
		"\2\2\u016b\u016d\7\'\2\2\u016c\u016e\5:\36\2\u016d\u016c\3\2\2\2\u016d"+
		"\u016e\3\2\2\2\u016e\u0170\3\2\2\2\u016f\u015a\3\2\2\2\u016f\u0165\3\2"+
		"\2\2\u01709\3\2\2\2\u0171\u0172\b\36\1\2\u0172\u0173\5\32\16\2\u0173\u0179"+
		"\3\2\2\2\u0174\u0175\f\3\2\2\u0175\u0176\7(\2\2\u0176\u0178\5\32\16\2"+
		"\u0177\u0174\3\2\2\2\u0178\u017b\3\2\2\2\u0179\u0177\3\2\2\2\u0179\u017a"+
		"\3\2\2\2\u017a;\3\2\2\2\u017b\u0179\3\2\2\2\u017c\u017d\5\"\22\2\u017d"+
		"\u017e\5 \21\2\u017e=\3\2\2\2\u017f\u0180\7\4\2\2\u0180\u0181\7\61\2\2"+
		"\u0181\u0183\7\30\2\2\u0182\u0184\5@!\2\u0183\u0182\3\2\2\2\u0183\u0184"+
		"\3\2\2\2\u0184\u0185\3\2\2\2\u0185\u0186\7\31\2\2\u0186?\3\2\2\2\u0187"+
		"\u0188\b!\1\2\u0188\u0189\5\36\20\2\u0189\u018e\3\2\2\2\u018a\u018b\f"+
		"\3\2\2\u018b\u018d\5\36\20\2\u018c\u018a\3\2\2\2\u018d\u0190\3\2\2\2\u018e"+
		"\u018c\3\2\2\2\u018e\u018f\3\2\2\2\u018fA\3\2\2\2\u0190\u018e\3\2\2\2"+
		"\u0191\u0192\7\7\2\2\u0192\u019b\7\'\2\2\u0193\u0194\7\5\2\2\u0194\u019b"+
		"\7\'\2\2\u0195\u0197\7\16\2\2\u0196\u0198\5\32\16\2\u0197\u0196\3\2\2"+
		"\2\u0197\u0198\3\2\2\2\u0198\u0199\3\2\2\2\u0199\u019b\7\'\2\2\u019a\u0191"+
		"\3\2\2\2\u019a\u0193\3\2\2\2\u019a\u0195\3\2\2\2\u019bC\3\2\2\2\u019c"+
		"\u019d\b#\1\2\u019d\u019e\5F$\2\u019e\u01a3\3\2\2\2\u019f\u01a0\f\3\2"+
		"\2\u01a0\u01a2\5F$\2\u01a1\u019f\3\2\2\2\u01a2\u01a5\3\2\2\2\u01a3\u01a1"+
		"\3\2\2\2\u01a3\u01a4\3\2\2\2\u01a4E\3\2\2\2\u01a5\u01a3\3\2\2\2\u01a6"+
		"\u01a9\5H%\2\u01a7\u01a9\5> \2\u01a8\u01a6\3\2\2\2\u01a8\u01a7\3\2\2\2"+
		"\u01a9G\3\2\2\2\u01aa\u01ab\5\"\22\2\u01ab\u01ac\5$\23\2\u01ac\u01ad\5"+
		",\27\2\u01adI\3\2\2\2\61KTZbhju|\u0081\u008f\u0091\u009d\u009f\u00b1\u00b3"+
		"\u00bf\u00c1\u00cc\u00d7\u00df\u00ec\u00f0\u00f8\u00fd\u0102\u010d\u011a"+
		"\u011e\u012b\u0132\u0139\u0142\u0157\u015a\u015e\u0162\u0165\u0169\u016d"+
		"\u016f\u0179\u0183\u018e\u0197\u019a\u01a3\u01a8";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}