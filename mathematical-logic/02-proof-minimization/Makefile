PARSING_MODULE    = Parsing
LOGIC_MODULE      = Logic

LEXER             = Lexer.x
GRAMMAR           = Grammar.y

SOURCES           = Main.hs $(LOGIC_MODULE)/*.hs
GENERATED         = $(PARSING_MODULE)/Lexer.hs $(PARSING_MODULE)/Grammar.hs

EXECUTABLE        = minimization

.PHONY: all run pack clean

all: minimization clean

run:
	./$(EXECUTABLE)

minimization: $(GENERATED) $(SOURCES)
	ghc -o $(EXECUTABLE) Main.hs

$(GENERATED):
	alex $(PARSING_MODULE)/$(LEXER)
	happy $(PARSING_MODULE)/$(GRAMMAR)

pack: $(GENERATED)
	zip $(EXECUTABLE).zip $(SOURCES) $(PARSING_MODULE)/*.hs Makefile

clean:
	rm -f *.o *.hi  $(LOGIC_MODULE)/*.o    \
		        $(LOGIC_MODULE)/*.hi   \				 		        $(PARSING_MODULE)/*.o  \
			$(PARSING_MODULE)/*.hi \
			$(GENERATED)
