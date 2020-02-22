override CXXFLAGS += -std=c++17

LIB_NAME     := mylib
MAIN_OBJ 	 := Test.o
OBJS     	 := Fraction.o
CXX      	 ?= g++

all: Test clean

Test: $(MAIN_OBJ) $(LIB_NAME).a
	${CXX} ${CXXFLAGS} -o $@ $^
	valgrind --leak-check=full 	--log-file="leaks_log.txt" ./Test

mylib.a: $(OBJS)
	ar rcs $@ $^

clean:
	rm -rf *o *a Test