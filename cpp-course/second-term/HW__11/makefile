override CXXFLAGS += -std=c++17

MAIN_OBJ 	 := Test.o
CXX      	 ?= g++

all: Test clean

Test: $(MAIN_OBJ)
	${CXX} ${CXXFLAGS} -o $@ $^
	valgrind --leak-check=full --log-file="leaks_log.txt" --error-limit=no ./Test


clean:
	-rm *o Test