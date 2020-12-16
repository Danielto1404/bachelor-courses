import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.*;
import java.lang.Object;
import java.util.Scanner;

public class RunParser {
    public static void main(String[] args) throws Exception {
        String inputFile = null;
        if ( args.length>0 ) inputFile = args[0];
        InputStream is = System.in;
        if ( inputFile!=null ) {
            is = new FileInputStream(inputFile);
        }

        Scanner s = new Scanner(new File("./input.in"));
        String expr="";
        while (s.hasNext())
            expr += (s.nextLine() + "\n");

		CPP_GrammarParser parser = new CPP_GrammarParser(null); // share single parser instance
  		parser.setBuildParseTree(false);          // don't need trees

        ANTLRInputStream input = new ANTLRInputStream(expr+"\n");
        CPP_GrammarLexer lexer = new CPP_GrammarLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        parser.setInputStream(tokens); // notify parser of new token stream
        System.out.println(parser.stat().str);
    }
}