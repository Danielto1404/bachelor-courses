// Generated from /Users/daniilkorolev/Documents/GitHub/3-rd-year/translation-methods/04-parser-generator/src/grammar/KingRules.g4 by ANTLR 4.9
package grammar.antlr;

import configurator.rules.*;
import configurator.rules.atoms.*;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class KingRulesLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.9", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		SkipedChars=1, LT_BRACE=2, RT_BRACE=3, L_CURLY=4, R_CURLY=5, L_BRACE=6, 
		R_BRACE=7, OR=8, SEMICOLON=9, COMMA=10, PACKAGE=11, GRAMMAR=12, REGEXP=13, 
		SKIP_NAME=14, ARROW=15, DOT=16, IDENTIFIER=17, TERMINAL_NAME=18, CURLY_CODE=19, 
		LOWERCASE=20, CAPITALIZED=21, ARG_CODE=22;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"SkipedChars", "LT_BRACE", "RT_BRACE", "L_CURLY", "R_CURLY", "L_BRACE", 
			"R_BRACE", "OR", "SEMICOLON", "COMMA", "PACKAGE", "GRAMMAR", "REGEXP", 
			"SKIP_NAME", "ARROW", "DOT", "IDENTIFIER", "TERMINAL_NAME", "CURLY_CODE", 
			"LOWERCASE", "CAPITALIZED", "ARG_CODE", "CODE"
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


	public KingRulesLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "KingRules.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\30\u00a1\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\3\2"+
		"\6\2\63\n\2\r\2\16\2\64\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3"+
		"\7\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3"+
		"\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3\16\7\16]\n\16\f\16\16\16`\13\16"+
		"\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3\21\3\21\3\22\3\22"+
		"\3\23\6\23q\n\23\r\23\16\23r\3\24\3\24\3\24\3\24\3\25\3\25\7\25{\n\25"+
		"\f\25\16\25~\13\25\3\26\3\26\7\26\u0082\n\26\f\26\16\26\u0085\13\26\3"+
		"\27\3\27\3\27\3\27\7\27\u008b\n\27\f\27\16\27\u008e\13\27\3\27\7\27\u0091"+
		"\n\27\f\27\16\27\u0094\13\27\3\27\3\27\3\27\3\27\5\27\u009a\n\27\3\30"+
		"\7\30\u009d\n\30\f\30\16\30\u00a0\13\30\3^\2\31\3\3\5\4\7\5\t\6\13\7\r"+
		"\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25"+
		")\26+\27-\30/\2\3\2\t\5\2\13\f\17\17\"\"\4\2C\\aa\3\2c|\5\2\62;C\\c|\3"+
		"\2C\\\5\2*+}}\177\177\4\2}}\177\177\2\u00a8\2\3\3\2\2\2\2\5\3\2\2\2\2"+
		"\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2"+
		"\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2"+
		"\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2"+
		"\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\3\62\3\2\2\2\58\3\2\2\2\7:\3\2\2\2"+
		"\t<\3\2\2\2\13>\3\2\2\2\r@\3\2\2\2\17B\3\2\2\2\21D\3\2\2\2\23F\3\2\2\2"+
		"\25H\3\2\2\2\27J\3\2\2\2\31R\3\2\2\2\33Z\3\2\2\2\35c\3\2\2\2\37h\3\2\2"+
		"\2!k\3\2\2\2#m\3\2\2\2%p\3\2\2\2\'t\3\2\2\2)x\3\2\2\2+\177\3\2\2\2-\u0099"+
		"\3\2\2\2/\u009e\3\2\2\2\61\63\t\2\2\2\62\61\3\2\2\2\63\64\3\2\2\2\64\62"+
		"\3\2\2\2\64\65\3\2\2\2\65\66\3\2\2\2\66\67\b\2\2\2\67\4\3\2\2\289\7>\2"+
		"\29\6\3\2\2\2:;\7@\2\2;\b\3\2\2\2<=\7}\2\2=\n\3\2\2\2>?\7\177\2\2?\f\3"+
		"\2\2\2@A\7*\2\2A\16\3\2\2\2BC\7+\2\2C\20\3\2\2\2DE\7~\2\2E\22\3\2\2\2"+
		"FG\7=\2\2G\24\3\2\2\2HI\7.\2\2I\26\3\2\2\2JK\7r\2\2KL\7c\2\2LM\7e\2\2"+
		"MN\7m\2\2NO\7c\2\2OP\7i\2\2PQ\7g\2\2Q\30\3\2\2\2RS\7i\2\2ST\7t\2\2TU\7"+
		"c\2\2UV\7o\2\2VW\7o\2\2WX\7c\2\2XY\7t\2\2Y\32\3\2\2\2Z^\7$\2\2[]\13\2"+
		"\2\2\\[\3\2\2\2]`\3\2\2\2^_\3\2\2\2^\\\3\2\2\2_a\3\2\2\2`^\3\2\2\2ab\7"+
		"$\2\2b\34\3\2\2\2cd\7u\2\2de\7m\2\2ef\7k\2\2fg\7r\2\2g\36\3\2\2\2hi\7"+
		"?\2\2ij\7@\2\2j \3\2\2\2kl\7\60\2\2l\"\3\2\2\2mn\5)\25\2n$\3\2\2\2oq\t"+
		"\3\2\2po\3\2\2\2qr\3\2\2\2rp\3\2\2\2rs\3\2\2\2s&\3\2\2\2tu\5\t\5\2uv\5"+
		"/\30\2vw\5\13\6\2w(\3\2\2\2x|\t\4\2\2y{\t\5\2\2zy\3\2\2\2{~\3\2\2\2|z"+
		"\3\2\2\2|}\3\2\2\2}*\3\2\2\2~|\3\2\2\2\177\u0083\t\6\2\2\u0080\u0082\t"+
		"\5\2\2\u0081\u0080\3\2\2\2\u0082\u0085\3\2\2\2\u0083\u0081\3\2\2\2\u0083"+
		"\u0084\3\2\2\2\u0084,\3\2\2\2\u0085\u0083\3\2\2\2\u0086\u0087\5#\22\2"+
		"\u0087\u0088\5!\21\2\u0088\u008c\5#\22\2\u0089\u008b\n\7\2\2\u008a\u0089"+
		"\3\2\2\2\u008b\u008e\3\2\2\2\u008c\u008a\3\2\2\2\u008c\u008d\3\2\2\2\u008d"+
		"\u009a\3\2\2\2\u008e\u008c\3\2\2\2\u008f\u0091\n\7\2\2\u0090\u008f\3\2"+
		"\2\2\u0091\u0094\3\2\2\2\u0092\u0090\3\2\2\2\u0092\u0093\3\2\2\2\u0093"+
		"\u0095\3\2\2\2\u0094\u0092\3\2\2\2\u0095\u0096\5#\22\2\u0096\u0097\5!"+
		"\21\2\u0097\u0098\5#\22\2\u0098\u009a\3\2\2\2\u0099\u0086\3\2\2\2\u0099"+
		"\u0092\3\2\2\2\u009a.\3\2\2\2\u009b\u009d\n\b\2\2\u009c\u009b\3\2\2\2"+
		"\u009d\u00a0\3\2\2\2\u009e\u009c\3\2\2\2\u009e\u009f\3\2\2\2\u009f\60"+
		"\3\2\2\2\u00a0\u009e\3\2\2\2\f\2\64^r|\u0083\u008c\u0092\u0099\u009e\3"+
		"\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}