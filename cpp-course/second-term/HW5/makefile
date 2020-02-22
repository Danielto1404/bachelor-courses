override CXXFLAGS += -std=c++17
override LDFLAGS  += -lm -g

LIB_NAME     := mylib
MAIN_OBJ 	 := Test.o
OBJS     	 := ArrayFunctions.o Functions.o Utils.o ResizeTest.o SwapMinTest.o
CXX      	 ?= g++

vpath %.cpp sources

all: Test clean

Test: $(MAIN_OBJ) $(LIB_NAME).a
	${CXX} ${CXXFLAGS} ${LDFLAGS} -o $@ $^
	valgrind --gen-suppressions=all --leak-check=full --show-leak-kinds=all \
	--track-origins=yes --log-file="leaks_log.txt" --error-limit=no ./Test

mylib.a: $(OBJS)
	ar rcs $@ $^

clean:
	rm -rf *o *a Test