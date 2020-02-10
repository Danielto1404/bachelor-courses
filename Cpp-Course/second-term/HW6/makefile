override CXXFLAGS += -std=c++17

LIB_NAME     := mylib
MAIN_OBJ 	 := Test.o
OBJS     	 := String.o
CXX      	 ?= g++

vpath %.cpp

all: Test clean

Test: $(MAIN_OBJ) $(LIB_NAME).a
	${CXX} ${CXXFLAGS} -o $@ $^
	valgrind --gen-suppressions=all --leak-check=full --show-leak-kinds=all \
	--track-origins=yes --log-file="leaks_log.txt" --error-limit=no ./Test

mylib.a: $(OBJS)
	ar rcs $@ $^

clean:
	rm -rf *o *a Test