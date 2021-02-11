// Generated from /Users/daniilkorolev/Documents/GitHub/3-rd-year/translation-methods/03-automatic-parser-generators/src/grammar/CPP.g4 by ANTLR 4.9
package antlr;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class CPPLexer extends Lexer {
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
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "Break", "Char", "Continue", "Double", "Else", "Float", 
			"If", "Int", "Long", "Return", "Short", "Void", "While", "For", "Do", 
			"LeftParen", "RightParen", "LeftBracket", "RightBracket", "LeftBrace", 
			"RightBrace", "Less", "LessEqual", "Greater", "GreaterEqual", "Plus", 
			"Minus", "Star", "Div", "Mod", "And", "Or", "Not", "Colon", "Semi", "Comma", 
			"Assign", "StarAssign", "DivAssign", "ModAssign", "PlusAssign", "MinusAssign", 
			"Equal", "NotEqual", "ID", "Letter", "Digit", "Number", "DecimalNumber", 
			"FractionalNumber", "Sign", "DigitSequence", "StringLiteral", "SChar", 
			"Whitespace", "Newline", "BlockComment", "LineComment"
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


	public CPPLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "CPP.g4"; }

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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\28\u0174\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3"+
		"\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7"+
		"\3\7\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\13\3\13"+
		"\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3"+
		"\16\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\20\3"+
		"\20\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\23\3\23\3\24\3\24\3\25\3\25\3"+
		"\26\3\26\3\27\3\27\3\30\3\30\3\31\3\31\3\32\3\32\3\32\3\33\3\33\3\34\3"+
		"\34\3\34\3\35\3\35\3\36\3\36\3\37\3\37\3 \3 \3!\3!\3\"\3\"\3\"\3#\3#\3"+
		"#\3$\3$\3%\3%\3&\3&\3\'\3\'\3(\3(\3)\3)\3)\3*\3*\3*\3+\3+\3+\3,\3,\3,"+
		"\3-\3-\3-\3.\3.\3.\3/\3/\3/\3\60\3\60\3\60\7\60\u011e\n\60\f\60\16\60"+
		"\u0121\13\60\3\61\3\61\3\62\3\62\3\63\3\63\5\63\u0129\n\63\3\64\6\64\u012c"+
		"\n\64\r\64\16\64\u012d\3\65\5\65\u0131\n\65\3\65\3\65\3\65\3\65\3\65\5"+
		"\65\u0138\n\65\3\66\3\66\3\67\6\67\u013d\n\67\r\67\16\67\u013e\38\38\7"+
		"8\u0143\n8\f8\168\u0146\138\38\38\39\39\3:\6:\u014d\n:\r:\16:\u014e\3"+
		":\3:\3;\3;\5;\u0155\n;\3;\5;\u0158\n;\3;\3;\3<\3<\3<\3<\7<\u0160\n<\f"+
		"<\16<\u0163\13<\3<\3<\3<\3<\3<\3=\3=\3=\3=\7=\u016e\n=\f=\16=\u0171\13"+
		"=\3=\3=\3\u0161\2>\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r"+
		"\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33"+
		"\65\34\67\359\36;\37= ?!A\"C#E$G%I&K\'M(O)Q*S+U,W-Y.[/]\60_\61a\2c\2e"+
		"\62g\2i\2k\2m\63o\64q\2s\65u\66w\67y8\3\2\b\5\2C\\aac|\3\2\62;\4\2--/"+
		"/\6\2\f\f\17\17$$^^\4\2\13\13\"\"\4\2\f\f\17\17\2\u017a\2\3\3\2\2\2\2"+
		"\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2"+
		"\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2"+
		"\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2"+
		"\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2"+
		"\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2"+
		"\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2"+
		"K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2W\3"+
		"\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\2]\3\2\2\2\2_\3\2\2\2\2e\3\2\2\2\2m\3\2\2"+
		"\2\2o\3\2\2\2\2s\3\2\2\2\2u\3\2\2\2\2w\3\2\2\2\2y\3\2\2\2\3{\3\2\2\2\5"+
		"}\3\2\2\2\7\u0084\3\2\2\2\t\u008a\3\2\2\2\13\u008f\3\2\2\2\r\u0098\3\2"+
		"\2\2\17\u009f\3\2\2\2\21\u00a4\3\2\2\2\23\u00aa\3\2\2\2\25\u00ad\3\2\2"+
		"\2\27\u00b1\3\2\2\2\31\u00b6\3\2\2\2\33\u00bd\3\2\2\2\35\u00c3\3\2\2\2"+
		"\37\u00c8\3\2\2\2!\u00ce\3\2\2\2#\u00d2\3\2\2\2%\u00d5\3\2\2\2\'\u00d7"+
		"\3\2\2\2)\u00d9\3\2\2\2+\u00db\3\2\2\2-\u00dd\3\2\2\2/\u00df\3\2\2\2\61"+
		"\u00e1\3\2\2\2\63\u00e3\3\2\2\2\65\u00e6\3\2\2\2\67\u00e8\3\2\2\29\u00eb"+
		"\3\2\2\2;\u00ed\3\2\2\2=\u00ef\3\2\2\2?\u00f1\3\2\2\2A\u00f3\3\2\2\2C"+
		"\u00f5\3\2\2\2E\u00f8\3\2\2\2G\u00fb\3\2\2\2I\u00fd\3\2\2\2K\u00ff\3\2"+
		"\2\2M\u0101\3\2\2\2O\u0103\3\2\2\2Q\u0105\3\2\2\2S\u0108\3\2\2\2U\u010b"+
		"\3\2\2\2W\u010e\3\2\2\2Y\u0111\3\2\2\2[\u0114\3\2\2\2]\u0117\3\2\2\2_"+
		"\u011a\3\2\2\2a\u0122\3\2\2\2c\u0124\3\2\2\2e\u0128\3\2\2\2g\u012b\3\2"+
		"\2\2i\u0137\3\2\2\2k\u0139\3\2\2\2m\u013c\3\2\2\2o\u0140\3\2\2\2q\u0149"+
		"\3\2\2\2s\u014c\3\2\2\2u\u0157\3\2\2\2w\u015b\3\2\2\2y\u0169\3\2\2\2{"+
		"|\7\60\2\2|\4\3\2\2\2}~\7u\2\2~\177\7v\2\2\177\u0080\7t\2\2\u0080\u0081"+
		"\7w\2\2\u0081\u0082\7e\2\2\u0082\u0083\7v\2\2\u0083\6\3\2\2\2\u0084\u0085"+
		"\7d\2\2\u0085\u0086\7t\2\2\u0086\u0087\7g\2\2\u0087\u0088\7c\2\2\u0088"+
		"\u0089\7m\2\2\u0089\b\3\2\2\2\u008a\u008b\7e\2\2\u008b\u008c\7j\2\2\u008c"+
		"\u008d\7c\2\2\u008d\u008e\7t\2\2\u008e\n\3\2\2\2\u008f\u0090\7e\2\2\u0090"+
		"\u0091\7q\2\2\u0091\u0092\7p\2\2\u0092\u0093\7v\2\2\u0093\u0094\7k\2\2"+
		"\u0094\u0095\7p\2\2\u0095\u0096\7w\2\2\u0096\u0097\7g\2\2\u0097\f\3\2"+
		"\2\2\u0098\u0099\7f\2\2\u0099\u009a\7q\2\2\u009a\u009b\7w\2\2\u009b\u009c"+
		"\7d\2\2\u009c\u009d\7n\2\2\u009d\u009e\7g\2\2\u009e\16\3\2\2\2\u009f\u00a0"+
		"\7g\2\2\u00a0\u00a1\7n\2\2\u00a1\u00a2\7u\2\2\u00a2\u00a3\7g\2\2\u00a3"+
		"\20\3\2\2\2\u00a4\u00a5\7h\2\2\u00a5\u00a6\7n\2\2\u00a6\u00a7\7q\2\2\u00a7"+
		"\u00a8\7c\2\2\u00a8\u00a9\7v\2\2\u00a9\22\3\2\2\2\u00aa\u00ab\7k\2\2\u00ab"+
		"\u00ac\7h\2\2\u00ac\24\3\2\2\2\u00ad\u00ae\7k\2\2\u00ae\u00af\7p\2\2\u00af"+
		"\u00b0\7v\2\2\u00b0\26\3\2\2\2\u00b1\u00b2\7n\2\2\u00b2\u00b3\7q\2\2\u00b3"+
		"\u00b4\7p\2\2\u00b4\u00b5\7i\2\2\u00b5\30\3\2\2\2\u00b6\u00b7\7t\2\2\u00b7"+
		"\u00b8\7g\2\2\u00b8\u00b9\7v\2\2\u00b9\u00ba\7w\2\2\u00ba\u00bb\7t\2\2"+
		"\u00bb\u00bc\7p\2\2\u00bc\32\3\2\2\2\u00bd\u00be\7u\2\2\u00be\u00bf\7"+
		"j\2\2\u00bf\u00c0\7q\2\2\u00c0\u00c1\7t\2\2\u00c1\u00c2\7v\2\2\u00c2\34"+
		"\3\2\2\2\u00c3\u00c4\7x\2\2\u00c4\u00c5\7q\2\2\u00c5\u00c6\7k\2\2\u00c6"+
		"\u00c7\7f\2\2\u00c7\36\3\2\2\2\u00c8\u00c9\7y\2\2\u00c9\u00ca\7j\2\2\u00ca"+
		"\u00cb\7k\2\2\u00cb\u00cc\7n\2\2\u00cc\u00cd\7g\2\2\u00cd \3\2\2\2\u00ce"+
		"\u00cf\7h\2\2\u00cf\u00d0\7q\2\2\u00d0\u00d1\7t\2\2\u00d1\"\3\2\2\2\u00d2"+
		"\u00d3\7f\2\2\u00d3\u00d4\7q\2\2\u00d4$\3\2\2\2\u00d5\u00d6\7*\2\2\u00d6"+
		"&\3\2\2\2\u00d7\u00d8\7+\2\2\u00d8(\3\2\2\2\u00d9\u00da\7]\2\2\u00da*"+
		"\3\2\2\2\u00db\u00dc\7_\2\2\u00dc,\3\2\2\2\u00dd\u00de\7}\2\2\u00de.\3"+
		"\2\2\2\u00df\u00e0\7\177\2\2\u00e0\60\3\2\2\2\u00e1\u00e2\7>\2\2\u00e2"+
		"\62\3\2\2\2\u00e3\u00e4\7>\2\2\u00e4\u00e5\7?\2\2\u00e5\64\3\2\2\2\u00e6"+
		"\u00e7\7@\2\2\u00e7\66\3\2\2\2\u00e8\u00e9\7@\2\2\u00e9\u00ea\7?\2\2\u00ea"+
		"8\3\2\2\2\u00eb\u00ec\7-\2\2\u00ec:\3\2\2\2\u00ed\u00ee\7/\2\2\u00ee<"+
		"\3\2\2\2\u00ef\u00f0\7,\2\2\u00f0>\3\2\2\2\u00f1\u00f2\7\61\2\2\u00f2"+
		"@\3\2\2\2\u00f3\u00f4\7\'\2\2\u00f4B\3\2\2\2\u00f5\u00f6\7(\2\2\u00f6"+
		"\u00f7\7(\2\2\u00f7D\3\2\2\2\u00f8\u00f9\7~\2\2\u00f9\u00fa\7~\2\2\u00fa"+
		"F\3\2\2\2\u00fb\u00fc\7#\2\2\u00fcH\3\2\2\2\u00fd\u00fe\7<\2\2\u00feJ"+
		"\3\2\2\2\u00ff\u0100\7=\2\2\u0100L\3\2\2\2\u0101\u0102\7.\2\2\u0102N\3"+
		"\2\2\2\u0103\u0104\7?\2\2\u0104P\3\2\2\2\u0105\u0106\7,\2\2\u0106\u0107"+
		"\7?\2\2\u0107R\3\2\2\2\u0108\u0109\7\61\2\2\u0109\u010a\7?\2\2\u010aT"+
		"\3\2\2\2\u010b\u010c\7\'\2\2\u010c\u010d\7?\2\2\u010dV\3\2\2\2\u010e\u010f"+
		"\7-\2\2\u010f\u0110\7?\2\2\u0110X\3\2\2\2\u0111\u0112\7/\2\2\u0112\u0113"+
		"\7?\2\2\u0113Z\3\2\2\2\u0114\u0115\7?\2\2\u0115\u0116\7?\2\2\u0116\\\3"+
		"\2\2\2\u0117\u0118\7#\2\2\u0118\u0119\7?\2\2\u0119^\3\2\2\2\u011a\u011f"+
		"\5a\61\2\u011b\u011e\5a\61\2\u011c\u011e\5c\62\2\u011d\u011b\3\2\2\2\u011d"+
		"\u011c\3\2\2\2\u011e\u0121\3\2\2\2\u011f\u011d\3\2\2\2\u011f\u0120\3\2"+
		"\2\2\u0120`\3\2\2\2\u0121\u011f\3\2\2\2\u0122\u0123\t\2\2\2\u0123b\3\2"+
		"\2\2\u0124\u0125\t\3\2\2\u0125d\3\2\2\2\u0126\u0129\5g\64\2\u0127\u0129"+
		"\5i\65\2\u0128\u0126\3\2\2\2\u0128\u0127\3\2\2\2\u0129f\3\2\2\2\u012a"+
		"\u012c\5c\62\2\u012b\u012a\3\2\2\2\u012c\u012d\3\2\2\2\u012d\u012b\3\2"+
		"\2\2\u012d\u012e\3\2\2\2\u012eh\3\2\2\2\u012f\u0131\5m\67\2\u0130\u012f"+
		"\3\2\2\2\u0130\u0131\3\2\2\2\u0131\u0132\3\2\2\2\u0132\u0133\7\60\2\2"+
		"\u0133\u0138\5m\67\2\u0134\u0135\5m\67\2\u0135\u0136\7\60\2\2\u0136\u0138"+
		"\3\2\2\2\u0137\u0130\3\2\2\2\u0137\u0134\3\2\2\2\u0138j\3\2\2\2\u0139"+
		"\u013a\t\4\2\2\u013al\3\2\2\2\u013b\u013d\5c\62\2\u013c\u013b\3\2\2\2"+
		"\u013d\u013e\3\2\2\2\u013e\u013c\3\2\2\2\u013e\u013f\3\2\2\2\u013fn\3"+
		"\2\2\2\u0140\u0144\7$\2\2\u0141\u0143\5q9\2\u0142\u0141\3\2\2\2\u0143"+
		"\u0146\3\2\2\2\u0144\u0142\3\2\2\2\u0144\u0145\3\2\2\2\u0145\u0147\3\2"+
		"\2\2\u0146\u0144\3\2\2\2\u0147\u0148\7$\2\2\u0148p\3\2\2\2\u0149\u014a"+
		"\n\5\2\2\u014ar\3\2\2\2\u014b\u014d\t\6\2\2\u014c\u014b\3\2\2\2\u014d"+
		"\u014e\3\2\2\2\u014e\u014c\3\2\2\2\u014e\u014f\3\2\2\2\u014f\u0150\3\2"+
		"\2\2\u0150\u0151\b:\2\2\u0151t\3\2\2\2\u0152\u0154\7\17\2\2\u0153\u0155"+
		"\7\f\2\2\u0154\u0153\3\2\2\2\u0154\u0155\3\2\2\2\u0155\u0158\3\2\2\2\u0156"+
		"\u0158\7\f\2\2\u0157\u0152\3\2\2\2\u0157\u0156\3\2\2\2\u0158\u0159\3\2"+
		"\2\2\u0159\u015a\b;\2\2\u015av\3\2\2\2\u015b\u015c\7\61\2\2\u015c\u015d"+
		"\7,\2\2\u015d\u0161\3\2\2\2\u015e\u0160\13\2\2\2\u015f\u015e\3\2\2\2\u0160"+
		"\u0163\3\2\2\2\u0161\u0162\3\2\2\2\u0161\u015f\3\2\2\2\u0162\u0164\3\2"+
		"\2\2\u0163\u0161\3\2\2\2\u0164\u0165\7,\2\2\u0165\u0166\7\61\2\2\u0166"+
		"\u0167\3\2\2\2\u0167\u0168\b<\2\2\u0168x\3\2\2\2\u0169\u016a\7\61\2\2"+
		"\u016a\u016b\7\61\2\2\u016b\u016f\3\2\2\2\u016c\u016e\n\7\2\2\u016d\u016c"+
		"\3\2\2\2\u016e\u0171\3\2\2\2\u016f\u016d\3\2\2\2\u016f\u0170\3\2\2\2\u0170"+
		"\u0172\3\2\2\2\u0171\u016f\3\2\2\2\u0172\u0173\b=\2\2\u0173z\3\2\2\2\20"+
		"\2\u011d\u011f\u0128\u012d\u0130\u0137\u013e\u0144\u014e\u0154\u0157\u0161"+
		"\u016f\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}