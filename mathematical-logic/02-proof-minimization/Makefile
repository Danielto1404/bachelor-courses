all:
	alex StringLex.x
	happy Parser.y
	ghc -O2 -XBangPatterns Main.hs
run:
	./Main
clean:
	rm *.o
	rm *.hi
