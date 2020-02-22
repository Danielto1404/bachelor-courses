override CXXFLAGS += -std=c++17
override LDFLAGS  += -lm

CXX          := g++
OBJS         := hello.o here.o bye.o
MAIN_OBJ     := main.o
STATIC_RES   := static_res
DYNAMIC_RES  := dynamic_res

DEFINE_NAME  := -DNAME=\"Danielto\"
VERSION      := 5
MINOR        := 1
RELEASE      := 10
LIB_NAME     := mylib
STATIC_LIB   := ${LIB_NAME}.a
DYNAMIC_NAME := lib${LIB_NAME}.dylib
DYNAMIC_LIB  := lib$(LIB_NAME).$(VERSION).$(MINOR).$(RELEASE).dylib

vpath %.cpp sources

$(MAIN_OBJ): main.cpp
	${CXX} ${CXXFLAGS} -c -o $@ $^

hello.o: hello.cpp
	${CXX} ${CXXFLAGS} ${DEFINE_NAME} -c -o $@ $^

here.o: here.cpp
	${CXX} ${CXXFLAGS} -c -o $@ $^

bye.o: bye.cpp
	${CXX} ${CXXFLAGS} -c -o $@ $^


dynamic: $(DYNAMIC_RES) $(DYNAMIC_LIB)
	@echo "\n<Testing dynamic lib>\n"
	./dynamic_res
	@echo "============"
	rm -f $(OBJS) $(DYNAMIC_RES) $(MAIN_OBJ)

$(DYNAMIC_RES): $(MAIN_OBJ) $(DYNAMIC_LIB)
	$(CXX) $(CXXFLAGS) -o $(DYNAMIC_RES) $(MAIN_OBJ) -L. -l${LIB_NAME}

$(DYNAMIC_LIB): $(OBJS)
	ln -sf ${DYNAMIC_LIB} ${DYNAMIC_NAME}
	${CXX} -dynamiclib -o ${DYNAMIC_LIB} $^


static: $(STATIC_LIB) $(STATIC_RES)
	@echo "\n<Testing static lib>\n"
	./static_res
	@echo "============"
	rm -f $(OBJS) $(STATIC_RES) $(MAIN_OBJ)

$(STATIC_LIB): $(OBJS)
	ar rcs $@ $^

$(STATIC_RES): $(MAIN_OBJ) $(STATIC_LIB)
	${CXX} ${CXXFLAGS} -o $@ $^


clean:
	rm -f $(STATIC_LIB) $(DYNAMIC_LIB) $(DYNAMIC_NAME)