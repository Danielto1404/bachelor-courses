// Generated from /Users/daniilkorolev/Documents/GitHub/3-rd-year/translation-methods/04-parser-generator/src/grammar/KingRules.g4 by ANTLR 4.9
package grammar.antlr;

import configurator.rules.*;
import configurator.rules.atoms.*;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class KingRulesParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.9", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		SkipedChars=1, LT_BRACE=2, RT_BRACE=3, L_CURLY=4, R_CURLY=5, L_BRACE=6, 
		R_BRACE=7, OR=8, SEMICOLON=9, COMMA=10, PACKAGE=11, GRAMMAR=12, REGEXP=13, 
		SKIP_NAME=14, ARROW=15, DOT=16, IDENTIFIER=17, TERMINAL_NAME=18, CURLY_CODE=19, 
		LOWERCASE=20, CAPITALIZED=21, ARG_CODE=22;
	public static final int
		RULE_mainGrammar = 0, RULE_grammarRules = 1, RULE_grammarName = 2, RULE_packageName = 3, 
		RULE_rules = 4, RULE_singleRule = 5, RULE_terminalRule = 6, RULE_syntaxRule = 7, 
		RULE_allRules = 8, RULE_ruleLine = 9, RULE_atoms = 10, RULE_atomRule = 11, 
		RULE_terminal = 12, RULE_syntax = 13, RULE_skipRule = 14, RULE_arg = 15, 
		RULE_takenArgs = 16, RULE_takenArgsList = 17, RULE_args = 18, RULE_argsList = 19;
	private static String[] makeRuleNames() {
		return new String[] {
			"mainGrammar", "grammarRules", "grammarName", "packageName", "rules", 
			"singleRule", "terminalRule", "syntaxRule", "allRules", "ruleLine", "atoms", 
			"atomRule", "terminal", "syntax", "skipRule", "arg", "takenArgs", "takenArgsList", 
			"args", "argsList"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, "'<'", "'>'", "'{'", "'}'", "'('", "')'", "'|'", "';'", "','", 
			"'package'", "'grammar'", null, "'skip'", "'=>'", "'.'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "SkipedChars", "LT_BRACE", "RT_BRACE", "L_CURLY", "R_CURLY", "L_BRACE", 
			"R_BRACE", "OR", "SEMICOLON", "COMMA", "PACKAGE", "GRAMMAR", "REGEXP", 
			"SKIP_NAME", "ARROW", "DOT", "IDENTIFIER", "TERMINAL_NAME", "CURLY_CODE", 
			"LOWERCASE", "CAPITALIZED", "ARG_CODE"
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
	public String getGrammarFileName() { return "KingRules.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public KingRulesParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class MainGrammarContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(KingRulesParser.EOF, 0); }
		public GrammarRulesContext grammarRules() {
			return getRuleContext(GrammarRulesContext.class,0);
		}
		public MainGrammarContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mainGrammar; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KingRulesListener ) ((KingRulesListener)listener).enterMainGrammar(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KingRulesListener ) ((KingRulesListener)listener).exitMainGrammar(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KingRulesVisitor ) return ((KingRulesVisitor<? extends T>)visitor).visitMainGrammar(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MainGrammarContext mainGrammar() throws RecognitionException {
		MainGrammarContext _localctx = new MainGrammarContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_mainGrammar);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(41);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==GRAMMAR) {
				{
				setState(40);
				grammarRules();
				}
			}

			setState(43);
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

	public static class GrammarRulesContext extends ParserRuleContext {
		public GrammarNameContext grammarName() {
			return getRuleContext(GrammarNameContext.class,0);
		}
		public PackageNameContext packageName() {
			return getRuleContext(PackageNameContext.class,0);
		}
		public RulesContext rules() {
			return getRuleContext(RulesContext.class,0);
		}
		public GrammarRulesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_grammarRules; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KingRulesListener ) ((KingRulesListener)listener).enterGrammarRules(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KingRulesListener ) ((KingRulesListener)listener).exitGrammarRules(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KingRulesVisitor ) return ((KingRulesVisitor<? extends T>)visitor).visitGrammarRules(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GrammarRulesContext grammarRules() throws RecognitionException {
		GrammarRulesContext _localctx = new GrammarRulesContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_grammarRules);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(45);
			grammarName();
			setState(46);
			packageName();
			setState(47);
			rules();
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

	public static class GrammarNameContext extends ParserRuleContext {
		public String name;
		public Token n;
		public TerminalNode GRAMMAR() { return getToken(KingRulesParser.GRAMMAR, 0); }
		public TerminalNode CAPITALIZED() { return getToken(KingRulesParser.CAPITALIZED, 0); }
		public GrammarNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_grammarName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KingRulesListener ) ((KingRulesListener)listener).enterGrammarName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KingRulesListener ) ((KingRulesListener)listener).exitGrammarName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KingRulesVisitor ) return ((KingRulesVisitor<? extends T>)visitor).visitGrammarName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GrammarNameContext grammarName() throws RecognitionException {
		GrammarNameContext _localctx = new GrammarNameContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_grammarName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(49);
			match(GRAMMAR);
			setState(50);
			((GrammarNameContext)_localctx).n = match(CAPITALIZED);
			 ((GrammarNameContext)_localctx).name =  (((GrammarNameContext)_localctx).n!=null?((GrammarNameContext)_localctx).n.getText():null); 
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

	public static class PackageNameContext extends ParserRuleContext {
		public String name;
		public Token n;
		public TerminalNode LT_BRACE() { return getToken(KingRulesParser.LT_BRACE, 0); }
		public TerminalNode PACKAGE() { return getToken(KingRulesParser.PACKAGE, 0); }
		public TerminalNode RT_BRACE() { return getToken(KingRulesParser.RT_BRACE, 0); }
		public TerminalNode IDENTIFIER() { return getToken(KingRulesParser.IDENTIFIER, 0); }
		public PackageNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_packageName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KingRulesListener ) ((KingRulesListener)listener).enterPackageName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KingRulesListener ) ((KingRulesListener)listener).exitPackageName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KingRulesVisitor ) return ((KingRulesVisitor<? extends T>)visitor).visitPackageName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PackageNameContext packageName() throws RecognitionException {
		PackageNameContext _localctx = new PackageNameContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_packageName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(53);
			match(LT_BRACE);
			setState(54);
			match(PACKAGE);
			setState(55);
			((PackageNameContext)_localctx).n = match(IDENTIFIER);
			setState(56);
			match(RT_BRACE);
			 ((PackageNameContext)_localctx).name =  (((PackageNameContext)_localctx).n!=null?((PackageNameContext)_localctx).n.getText():null); 
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

	public static class RulesContext extends ParserRuleContext {
		public List<SingleRuleContext> singleRule() {
			return getRuleContexts(SingleRuleContext.class);
		}
		public SingleRuleContext singleRule(int i) {
			return getRuleContext(SingleRuleContext.class,i);
		}
		public RulesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rules; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KingRulesListener ) ((KingRulesListener)listener).enterRules(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KingRulesListener ) ((KingRulesListener)listener).exitRules(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KingRulesVisitor ) return ((KingRulesVisitor<? extends T>)visitor).visitRules(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RulesContext rules() throws RecognitionException {
		RulesContext _localctx = new RulesContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_rules);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(60); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(59);
				singleRule();
				}
				}
				setState(62); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << SKIP_NAME) | (1L << IDENTIFIER) | (1L << TERMINAL_NAME))) != 0) );
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

	public static class SingleRuleContext extends ParserRuleContext {
		public TerminalRuleContext terminalRule() {
			return getRuleContext(TerminalRuleContext.class,0);
		}
		public SyntaxRuleContext syntaxRule() {
			return getRuleContext(SyntaxRuleContext.class,0);
		}
		public SkipRuleContext skipRule() {
			return getRuleContext(SkipRuleContext.class,0);
		}
		public SingleRuleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_singleRule; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KingRulesListener ) ((KingRulesListener)listener).enterSingleRule(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KingRulesListener ) ((KingRulesListener)listener).exitSingleRule(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KingRulesVisitor ) return ((KingRulesVisitor<? extends T>)visitor).visitSingleRule(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SingleRuleContext singleRule() throws RecognitionException {
		SingleRuleContext _localctx = new SingleRuleContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_singleRule);
		try {
			setState(67);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case TERMINAL_NAME:
				enterOuterAlt(_localctx, 1);
				{
				setState(64);
				terminalRule();
				}
				break;
			case IDENTIFIER:
				enterOuterAlt(_localctx, 2);
				{
				setState(65);
				syntaxRule();
				}
				break;
			case SKIP_NAME:
				enterOuterAlt(_localctx, 3);
				{
				setState(66);
				skipRule();
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

	public static class TerminalRuleContext extends ParserRuleContext {
		public TerminalRule rule;
		public Token TERMINAL_NAME;
		public ArgsListContext inheritedAttrs;
		public ArgsListContext returnAttrs;
		public Token REGEXP;
		public Token CURLY_CODE;
		public TerminalNode TERMINAL_NAME() { return getToken(KingRulesParser.TERMINAL_NAME, 0); }
		public TerminalNode ARROW() { return getToken(KingRulesParser.ARROW, 0); }
		public TerminalNode REGEXP() { return getToken(KingRulesParser.REGEXP, 0); }
		public TerminalNode CURLY_CODE() { return getToken(KingRulesParser.CURLY_CODE, 0); }
		public List<ArgsListContext> argsList() {
			return getRuleContexts(ArgsListContext.class);
		}
		public ArgsListContext argsList(int i) {
			return getRuleContext(ArgsListContext.class,i);
		}
		public TerminalRuleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_terminalRule; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KingRulesListener ) ((KingRulesListener)listener).enterTerminalRule(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KingRulesListener ) ((KingRulesListener)listener).exitTerminalRule(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KingRulesVisitor ) return ((KingRulesVisitor<? extends T>)visitor).visitTerminalRule(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TerminalRuleContext terminalRule() throws RecognitionException {
		TerminalRuleContext _localctx = new TerminalRuleContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_terminalRule);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(69);
			((TerminalRuleContext)_localctx).TERMINAL_NAME = match(TERMINAL_NAME);
			setState(71);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==L_BRACE) {
				{
				setState(70);
				((TerminalRuleContext)_localctx).inheritedAttrs = argsList();
				}
			}

			setState(73);
			match(ARROW);
			setState(75);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==L_BRACE) {
				{
				setState(74);
				((TerminalRuleContext)_localctx).returnAttrs = argsList();
				}
			}

			setState(77);
			((TerminalRuleContext)_localctx).REGEXP = match(REGEXP);
			setState(79);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==CURLY_CODE) {
				{
				setState(78);
				((TerminalRuleContext)_localctx).CURLY_CODE = match(CURLY_CODE);
				}
			}

			 ((TerminalRuleContext)_localctx).rule =  new TerminalRule
			          ( (((TerminalRuleContext)_localctx).TERMINAL_NAME!=null?((TerminalRuleContext)_localctx).TERMINAL_NAME.getText():null),
			            (((TerminalRuleContext)_localctx).CURLY_CODE!=null?((TerminalRuleContext)_localctx).CURLY_CODE.getText():null),
			            (((TerminalRuleContext)_localctx).REGEXP!=null?((TerminalRuleContext)_localctx).REGEXP.getText():null),
			            (((TerminalRuleContext)_localctx).inheritedAttrs!=null?_input.getText(((TerminalRuleContext)_localctx).inheritedAttrs.start,((TerminalRuleContext)_localctx).inheritedAttrs.stop):null) == null ? null : ((TerminalRuleContext)_localctx).inheritedAttrs.arguments,
			            (((TerminalRuleContext)_localctx).returnAttrs!=null?_input.getText(((TerminalRuleContext)_localctx).returnAttrs.start,((TerminalRuleContext)_localctx).returnAttrs.stop):null)    == null ? null : ((TerminalRuleContext)_localctx).returnAttrs.arguments
			          );
			      
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

	public static class SyntaxRuleContext extends ParserRuleContext {
		public String name;
		public Token IDENTIFIER;
		public ArgsListContext inheritedAttrs;
		public ArgsListContext returnAttrs;
		public TerminalNode IDENTIFIER() { return getToken(KingRulesParser.IDENTIFIER, 0); }
		public TerminalNode ARROW() { return getToken(KingRulesParser.ARROW, 0); }
		public AllRulesContext allRules() {
			return getRuleContext(AllRulesContext.class,0);
		}
		public List<ArgsListContext> argsList() {
			return getRuleContexts(ArgsListContext.class);
		}
		public ArgsListContext argsList(int i) {
			return getRuleContext(ArgsListContext.class,i);
		}
		public SyntaxRuleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_syntaxRule; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KingRulesListener ) ((KingRulesListener)listener).enterSyntaxRule(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KingRulesListener ) ((KingRulesListener)listener).exitSyntaxRule(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KingRulesVisitor ) return ((KingRulesVisitor<? extends T>)visitor).visitSyntaxRule(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SyntaxRuleContext syntaxRule() throws RecognitionException {
		SyntaxRuleContext _localctx = new SyntaxRuleContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_syntaxRule);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(83);
			((SyntaxRuleContext)_localctx).IDENTIFIER = match(IDENTIFIER);
			setState(85);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==L_BRACE) {
				{
				setState(84);
				((SyntaxRuleContext)_localctx).inheritedAttrs = argsList();
				}
			}

			setState(87);
			match(ARROW);
			setState(89);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==L_BRACE) {
				{
				setState(88);
				((SyntaxRuleContext)_localctx).returnAttrs = argsList();
				}
			}

			setState(91);
			allRules(0);
			 ((SyntaxRuleContext)_localctx).name =  (((SyntaxRuleContext)_localctx).IDENTIFIER!=null?((SyntaxRuleContext)_localctx).IDENTIFIER.getText():null); 
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

	public static class AllRulesContext extends ParserRuleContext {
		public RuleLineContext ruleLine() {
			return getRuleContext(RuleLineContext.class,0);
		}
		public AllRulesContext allRules() {
			return getRuleContext(AllRulesContext.class,0);
		}
		public TerminalNode OR() { return getToken(KingRulesParser.OR, 0); }
		public AllRulesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_allRules; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KingRulesListener ) ((KingRulesListener)listener).enterAllRules(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KingRulesListener ) ((KingRulesListener)listener).exitAllRules(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KingRulesVisitor ) return ((KingRulesVisitor<? extends T>)visitor).visitAllRules(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AllRulesContext allRules() throws RecognitionException {
		return allRules(0);
	}

	private AllRulesContext allRules(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		AllRulesContext _localctx = new AllRulesContext(_ctx, _parentState);
		AllRulesContext _prevctx = _localctx;
		int _startState = 16;
		enterRecursionRule(_localctx, 16, RULE_allRules, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(95);
			ruleLine();
			}
			_ctx.stop = _input.LT(-1);
			setState(102);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new AllRulesContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_allRules);
					setState(97);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(98);
					match(OR);
					setState(99);
					ruleLine();
					}
					} 
				}
				setState(104);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
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

	public static class RuleLineContext extends ParserRuleContext {
		public Token code;
		public AtomsContext atoms() {
			return getRuleContext(AtomsContext.class,0);
		}
		public TerminalNode CURLY_CODE() { return getToken(KingRulesParser.CURLY_CODE, 0); }
		public RuleLineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ruleLine; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KingRulesListener ) ((KingRulesListener)listener).enterRuleLine(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KingRulesListener ) ((KingRulesListener)listener).exitRuleLine(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KingRulesVisitor ) return ((KingRulesVisitor<? extends T>)visitor).visitRuleLine(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RuleLineContext ruleLine() throws RecognitionException {
		RuleLineContext _localctx = new RuleLineContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_ruleLine);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(105);
			atoms();
			setState(107);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
			case 1:
				{
				setState(106);
				((RuleLineContext)_localctx).code = match(CURLY_CODE);
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

	public static class AtomsContext extends ParserRuleContext {
		public List<AtomRuleContext> atomRule() {
			return getRuleContexts(AtomRuleContext.class);
		}
		public AtomRuleContext atomRule(int i) {
			return getRuleContext(AtomRuleContext.class,i);
		}
		public AtomsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_atoms; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KingRulesListener ) ((KingRulesListener)listener).enterAtoms(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KingRulesListener ) ((KingRulesListener)listener).exitAtoms(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KingRulesVisitor ) return ((KingRulesVisitor<? extends T>)visitor).visitAtoms(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AtomsContext atoms() throws RecognitionException {
		AtomsContext _localctx = new AtomsContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_atoms);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(110); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(109);
					atomRule();
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(112); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
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

	public static class AtomRuleContext extends ParserRuleContext {
		public SyntaxContext syntax() {
			return getRuleContext(SyntaxContext.class,0);
		}
		public TerminalContext terminal() {
			return getRuleContext(TerminalContext.class,0);
		}
		public AtomRuleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_atomRule; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KingRulesListener ) ((KingRulesListener)listener).enterAtomRule(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KingRulesListener ) ((KingRulesListener)listener).exitAtomRule(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KingRulesVisitor ) return ((KingRulesVisitor<? extends T>)visitor).visitAtomRule(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AtomRuleContext atomRule() throws RecognitionException {
		AtomRuleContext _localctx = new AtomRuleContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_atomRule);
		try {
			setState(116);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IDENTIFIER:
				enterOuterAlt(_localctx, 1);
				{
				setState(114);
				syntax();
				}
				break;
			case TERMINAL_NAME:
				enterOuterAlt(_localctx, 2);
				{
				setState(115);
				terminal();
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

	public static class TerminalContext extends ParserRuleContext {
		public TerminalNode TERMINAL_NAME() { return getToken(KingRulesParser.TERMINAL_NAME, 0); }
		public TakenArgsListContext takenArgsList() {
			return getRuleContext(TakenArgsListContext.class,0);
		}
		public TerminalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_terminal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KingRulesListener ) ((KingRulesListener)listener).enterTerminal(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KingRulesListener ) ((KingRulesListener)listener).exitTerminal(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KingRulesVisitor ) return ((KingRulesVisitor<? extends T>)visitor).visitTerminal(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TerminalContext terminal() throws RecognitionException {
		TerminalContext _localctx = new TerminalContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_terminal);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(118);
			match(TERMINAL_NAME);
			setState(120);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
			case 1:
				{
				setState(119);
				takenArgsList();
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

	public static class SyntaxContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(KingRulesParser.IDENTIFIER, 0); }
		public TakenArgsListContext takenArgsList() {
			return getRuleContext(TakenArgsListContext.class,0);
		}
		public SyntaxContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_syntax; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KingRulesListener ) ((KingRulesListener)listener).enterSyntax(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KingRulesListener ) ((KingRulesListener)listener).exitSyntax(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KingRulesVisitor ) return ((KingRulesVisitor<? extends T>)visitor).visitSyntax(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SyntaxContext syntax() throws RecognitionException {
		SyntaxContext _localctx = new SyntaxContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_syntax);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(122);
			match(IDENTIFIER);
			setState(124);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
			case 1:
				{
				setState(123);
				takenArgsList();
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

	public static class SkipRuleContext extends ParserRuleContext {
		public SkipRule rule;
		public Token TERMINAL_NAME;
		public Token REGEXP;
		public TerminalNode SKIP_NAME() { return getToken(KingRulesParser.SKIP_NAME, 0); }
		public TerminalNode TERMINAL_NAME() { return getToken(KingRulesParser.TERMINAL_NAME, 0); }
		public TerminalNode ARROW() { return getToken(KingRulesParser.ARROW, 0); }
		public TerminalNode REGEXP() { return getToken(KingRulesParser.REGEXP, 0); }
		public SkipRuleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_skipRule; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KingRulesListener ) ((KingRulesListener)listener).enterSkipRule(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KingRulesListener ) ((KingRulesListener)listener).exitSkipRule(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KingRulesVisitor ) return ((KingRulesVisitor<? extends T>)visitor).visitSkipRule(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SkipRuleContext skipRule() throws RecognitionException {
		SkipRuleContext _localctx = new SkipRuleContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_skipRule);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(126);
			match(SKIP_NAME);
			setState(127);
			((SkipRuleContext)_localctx).TERMINAL_NAME = match(TERMINAL_NAME);
			setState(128);
			match(ARROW);
			setState(129);
			((SkipRuleContext)_localctx).REGEXP = match(REGEXP);
			 ((SkipRuleContext)_localctx).rule =  new SkipRule((((SkipRuleContext)_localctx).TERMINAL_NAME!=null?((SkipRuleContext)_localctx).TERMINAL_NAME.getText():null), (((SkipRuleContext)_localctx).REGEXP!=null?((SkipRuleContext)_localctx).REGEXP.getText():null)); 
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

	public static class ArgContext extends ParserRuleContext {
		public Argument argument;
		public Token type;
		public Token name;
		public List<TerminalNode> IDENTIFIER() { return getTokens(KingRulesParser.IDENTIFIER); }
		public TerminalNode IDENTIFIER(int i) {
			return getToken(KingRulesParser.IDENTIFIER, i);
		}
		public TerminalNode CAPITALIZED() { return getToken(KingRulesParser.CAPITALIZED, 0); }
		public ArgContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arg; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KingRulesListener ) ((KingRulesListener)listener).enterArg(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KingRulesListener ) ((KingRulesListener)listener).exitArg(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KingRulesVisitor ) return ((KingRulesVisitor<? extends T>)visitor).visitArg(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArgContext arg() throws RecognitionException {
		ArgContext _localctx = new ArgContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_arg);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(132);
			((ArgContext)_localctx).type = _input.LT(1);
			_la = _input.LA(1);
			if ( !(_la==IDENTIFIER || _la==CAPITALIZED) ) {
				((ArgContext)_localctx).type = (Token)_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(133);
			((ArgContext)_localctx).name = match(IDENTIFIER);
			 ((ArgContext)_localctx).argument =  new Argument ((((ArgContext)_localctx).type!=null?((ArgContext)_localctx).type.getText():null), (((ArgContext)_localctx).name!=null?((ArgContext)_localctx).name.getText():null)); 
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

	public static class TakenArgsContext extends ParserRuleContext {
		public StringBuilder nodeArgs;
		public TakenArgsContext acc;
		public Token ARG_CODE;
		public TerminalNode ARG_CODE() { return getToken(KingRulesParser.ARG_CODE, 0); }
		public TerminalNode COMMA() { return getToken(KingRulesParser.COMMA, 0); }
		public TakenArgsContext takenArgs() {
			return getRuleContext(TakenArgsContext.class,0);
		}
		public TakenArgsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_takenArgs; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KingRulesListener ) ((KingRulesListener)listener).enterTakenArgs(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KingRulesListener ) ((KingRulesListener)listener).exitTakenArgs(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KingRulesVisitor ) return ((KingRulesVisitor<? extends T>)visitor).visitTakenArgs(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TakenArgsContext takenArgs() throws RecognitionException {
		return takenArgs(0);
	}

	private TakenArgsContext takenArgs(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		TakenArgsContext _localctx = new TakenArgsContext(_ctx, _parentState);
		TakenArgsContext _prevctx = _localctx;
		int _startState = 32;
		enterRecursionRule(_localctx, 32, RULE_takenArgs, _p);

		    ((TakenArgsContext)_localctx).nodeArgs =  new StringBuilder();
		  
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(137);
			((TakenArgsContext)_localctx).ARG_CODE = match(ARG_CODE);
			 _localctx.nodeArgs.append((((TakenArgsContext)_localctx).ARG_CODE!=null?((TakenArgsContext)_localctx).ARG_CODE.getText():null)); 
			}
			_ctx.stop = _input.LT(-1);
			setState(146);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new TakenArgsContext(_parentctx, _parentState);
					_localctx.acc = _prevctx;
					_localctx.acc = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_takenArgs);
					setState(140);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(141);
					match(COMMA);
					setState(142);
					((TakenArgsContext)_localctx).ARG_CODE = match(ARG_CODE);
					 ((TakenArgsContext)_localctx).nodeArgs =  ((TakenArgsContext)_localctx).acc.nodeArgs; _localctx.nodeArgs.append(", ").append((((TakenArgsContext)_localctx).ARG_CODE!=null?((TakenArgsContext)_localctx).ARG_CODE.getText():null)); 
					}
					} 
				}
				setState(148);
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

	public static class TakenArgsListContext extends ParserRuleContext {
		public String toString;
		public TakenArgsContext takenArgs;
		public TerminalNode L_BRACE() { return getToken(KingRulesParser.L_BRACE, 0); }
		public TakenArgsContext takenArgs() {
			return getRuleContext(TakenArgsContext.class,0);
		}
		public TerminalNode R_BRACE() { return getToken(KingRulesParser.R_BRACE, 0); }
		public TakenArgsListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_takenArgsList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KingRulesListener ) ((KingRulesListener)listener).enterTakenArgsList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KingRulesListener ) ((KingRulesListener)listener).exitTakenArgsList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KingRulesVisitor ) return ((KingRulesVisitor<? extends T>)visitor).visitTakenArgsList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TakenArgsListContext takenArgsList() throws RecognitionException {
		TakenArgsListContext _localctx = new TakenArgsListContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_takenArgsList);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(149);
			match(L_BRACE);
			setState(150);
			((TakenArgsListContext)_localctx).takenArgs = takenArgs(0);
			setState(151);
			match(R_BRACE);
			 ((TakenArgsListContext)_localctx).toString =  "(" + ((TakenArgsListContext)_localctx).takenArgs.nodeArgs.toString() + ")"; 
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

	public static class ArgsContext extends ParserRuleContext {
		public ArrayList<Argument> arguments;
		public ArgsContext acc;
		public ArgContext arg;
		public ArgContext arg() {
			return getRuleContext(ArgContext.class,0);
		}
		public TerminalNode COMMA() { return getToken(KingRulesParser.COMMA, 0); }
		public ArgsContext args() {
			return getRuleContext(ArgsContext.class,0);
		}
		public ArgsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_args; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KingRulesListener ) ((KingRulesListener)listener).enterArgs(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KingRulesListener ) ((KingRulesListener)listener).exitArgs(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KingRulesVisitor ) return ((KingRulesVisitor<? extends T>)visitor).visitArgs(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArgsContext args() throws RecognitionException {
		return args(0);
	}

	private ArgsContext args(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ArgsContext _localctx = new ArgsContext(_ctx, _parentState);
		ArgsContext _prevctx = _localctx;
		int _startState = 36;
		enterRecursionRule(_localctx, 36, RULE_args, _p);

		    ((ArgsContext)_localctx).arguments =  new ArrayList<>();
		  
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(155);
			((ArgsContext)_localctx).arg = arg();
			 _localctx.arguments.add(((ArgsContext)_localctx).arg.argument); 
			}
			_ctx.stop = _input.LT(-1);
			setState(165);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new ArgsContext(_parentctx, _parentState);
					_localctx.acc = _prevctx;
					_localctx.acc = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_args);
					setState(158);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(159);
					match(COMMA);
					setState(160);
					((ArgsContext)_localctx).arg = arg();
					 ((ArgsContext)_localctx).arguments =  ((ArgsContext)_localctx).acc.arguments; _localctx.arguments.add(((ArgsContext)_localctx).arg.argument); 
					}
					} 
				}
				setState(167);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
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

	public static class ArgsListContext extends ParserRuleContext {
		public ArrayList<Argument> arguments;
		public ArgsContext acc;
		public TerminalNode L_BRACE() { return getToken(KingRulesParser.L_BRACE, 0); }
		public TerminalNode R_BRACE() { return getToken(KingRulesParser.R_BRACE, 0); }
		public ArgsContext args() {
			return getRuleContext(ArgsContext.class,0);
		}
		public ArgsListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_argsList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KingRulesListener ) ((KingRulesListener)listener).enterArgsList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KingRulesListener ) ((KingRulesListener)listener).exitArgsList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KingRulesVisitor ) return ((KingRulesVisitor<? extends T>)visitor).visitArgsList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArgsListContext argsList() throws RecognitionException {
		ArgsListContext _localctx = new ArgsListContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_argsList);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(168);
			match(L_BRACE);
			setState(169);
			((ArgsListContext)_localctx).acc = args(0);
			setState(170);
			match(R_BRACE);
			 ((ArgsListContext)_localctx).arguments =  ((ArgsListContext)_localctx).acc.arguments; 
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
		case 8:
			return allRules_sempred((AllRulesContext)_localctx, predIndex);
		case 16:
			return takenArgs_sempred((TakenArgsContext)_localctx, predIndex);
		case 18:
			return args_sempred((ArgsContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean allRules_sempred(AllRulesContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean takenArgs_sempred(TakenArgsContext _localctx, int predIndex) {
		switch (predIndex) {
		case 1:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean args_sempred(ArgsContext _localctx, int predIndex) {
		switch (predIndex) {
		case 2:
			return precpred(_ctx, 1);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\30\u00b0\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\3\2\5\2,\n\2\3\2\3\2\3\3\3\3\3\3\3\3\3"+
		"\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\6\6\6?\n\6\r\6\16\6@\3\7\3\7"+
		"\3\7\5\7F\n\7\3\b\3\b\5\bJ\n\b\3\b\3\b\5\bN\n\b\3\b\3\b\5\bR\n\b\3\b\3"+
		"\b\3\t\3\t\5\tX\n\t\3\t\3\t\5\t\\\n\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n"+
		"\3\n\7\ng\n\n\f\n\16\nj\13\n\3\13\3\13\5\13n\n\13\3\f\6\fq\n\f\r\f\16"+
		"\fr\3\r\3\r\5\rw\n\r\3\16\3\16\5\16{\n\16\3\17\3\17\5\17\177\n\17\3\20"+
		"\3\20\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22"+
		"\3\22\3\22\3\22\7\22\u0093\n\22\f\22\16\22\u0096\13\22\3\23\3\23\3\23"+
		"\3\23\3\23\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\7\24\u00a6\n\24"+
		"\f\24\16\24\u00a9\13\24\3\25\3\25\3\25\3\25\3\25\3\25\2\5\22\"&\26\2\4"+
		"\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(\2\3\4\2\23\23\27\27\2\u00ac"+
		"\2+\3\2\2\2\4/\3\2\2\2\6\63\3\2\2\2\b\67\3\2\2\2\n>\3\2\2\2\fE\3\2\2\2"+
		"\16G\3\2\2\2\20U\3\2\2\2\22`\3\2\2\2\24k\3\2\2\2\26p\3\2\2\2\30v\3\2\2"+
		"\2\32x\3\2\2\2\34|\3\2\2\2\36\u0080\3\2\2\2 \u0086\3\2\2\2\"\u008a\3\2"+
		"\2\2$\u0097\3\2\2\2&\u009c\3\2\2\2(\u00aa\3\2\2\2*,\5\4\3\2+*\3\2\2\2"+
		"+,\3\2\2\2,-\3\2\2\2-.\7\2\2\3.\3\3\2\2\2/\60\5\6\4\2\60\61\5\b\5\2\61"+
		"\62\5\n\6\2\62\5\3\2\2\2\63\64\7\16\2\2\64\65\7\27\2\2\65\66\b\4\1\2\66"+
		"\7\3\2\2\2\678\7\4\2\289\7\r\2\29:\7\23\2\2:;\7\5\2\2;<\b\5\1\2<\t\3\2"+
		"\2\2=?\5\f\7\2>=\3\2\2\2?@\3\2\2\2@>\3\2\2\2@A\3\2\2\2A\13\3\2\2\2BF\5"+
		"\16\b\2CF\5\20\t\2DF\5\36\20\2EB\3\2\2\2EC\3\2\2\2ED\3\2\2\2F\r\3\2\2"+
		"\2GI\7\24\2\2HJ\5(\25\2IH\3\2\2\2IJ\3\2\2\2JK\3\2\2\2KM\7\21\2\2LN\5("+
		"\25\2ML\3\2\2\2MN\3\2\2\2NO\3\2\2\2OQ\7\17\2\2PR\7\25\2\2QP\3\2\2\2QR"+
		"\3\2\2\2RS\3\2\2\2ST\b\b\1\2T\17\3\2\2\2UW\7\23\2\2VX\5(\25\2WV\3\2\2"+
		"\2WX\3\2\2\2XY\3\2\2\2Y[\7\21\2\2Z\\\5(\25\2[Z\3\2\2\2[\\\3\2\2\2\\]\3"+
		"\2\2\2]^\5\22\n\2^_\b\t\1\2_\21\3\2\2\2`a\b\n\1\2ab\5\24\13\2bh\3\2\2"+
		"\2cd\f\3\2\2de\7\n\2\2eg\5\24\13\2fc\3\2\2\2gj\3\2\2\2hf\3\2\2\2hi\3\2"+
		"\2\2i\23\3\2\2\2jh\3\2\2\2km\5\26\f\2ln\7\25\2\2ml\3\2\2\2mn\3\2\2\2n"+
		"\25\3\2\2\2oq\5\30\r\2po\3\2\2\2qr\3\2\2\2rp\3\2\2\2rs\3\2\2\2s\27\3\2"+
		"\2\2tw\5\34\17\2uw\5\32\16\2vt\3\2\2\2vu\3\2\2\2w\31\3\2\2\2xz\7\24\2"+
		"\2y{\5$\23\2zy\3\2\2\2z{\3\2\2\2{\33\3\2\2\2|~\7\23\2\2}\177\5$\23\2~"+
		"}\3\2\2\2~\177\3\2\2\2\177\35\3\2\2\2\u0080\u0081\7\20\2\2\u0081\u0082"+
		"\7\24\2\2\u0082\u0083\7\21\2\2\u0083\u0084\7\17\2\2\u0084\u0085\b\20\1"+
		"\2\u0085\37\3\2\2\2\u0086\u0087\t\2\2\2\u0087\u0088\7\23\2\2\u0088\u0089"+
		"\b\21\1\2\u0089!\3\2\2\2\u008a\u008b\b\22\1\2\u008b\u008c\7\30\2\2\u008c"+
		"\u008d\b\22\1\2\u008d\u0094\3\2\2\2\u008e\u008f\f\3\2\2\u008f\u0090\7"+
		"\f\2\2\u0090\u0091\7\30\2\2\u0091\u0093\b\22\1\2\u0092\u008e\3\2\2\2\u0093"+
		"\u0096\3\2\2\2\u0094\u0092\3\2\2\2\u0094\u0095\3\2\2\2\u0095#\3\2\2\2"+
		"\u0096\u0094\3\2\2\2\u0097\u0098\7\b\2\2\u0098\u0099\5\"\22\2\u0099\u009a"+
		"\7\t\2\2\u009a\u009b\b\23\1\2\u009b%\3\2\2\2\u009c\u009d\b\24\1\2\u009d"+
		"\u009e\5 \21\2\u009e\u009f\b\24\1\2\u009f\u00a7\3\2\2\2\u00a0\u00a1\f"+
		"\3\2\2\u00a1\u00a2\7\f\2\2\u00a2\u00a3\5 \21\2\u00a3\u00a4\b\24\1\2\u00a4"+
		"\u00a6\3\2\2\2\u00a5\u00a0\3\2\2\2\u00a6\u00a9\3\2\2\2\u00a7\u00a5\3\2"+
		"\2\2\u00a7\u00a8\3\2\2\2\u00a8\'\3\2\2\2\u00a9\u00a7\3\2\2\2\u00aa\u00ab"+
		"\7\b\2\2\u00ab\u00ac\5&\24\2\u00ac\u00ad\7\t\2\2\u00ad\u00ae\b\25\1\2"+
		"\u00ae)\3\2\2\2\22+@EIMQW[hmrvz~\u0094\u00a7";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}