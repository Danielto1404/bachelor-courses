from z3 import *

# for i in range(36):
#     print(f'print(chr(x{i}), end="")')

for i in range(64):
    print(f'x{i} = BitVec(\'x{i}\', 8)')

x0 = BitVec('x0', 8)
x1 = BitVec('x1', 8)
x2 = BitVec('x2', 8)
x3 = BitVec('x3', 8)
x4 = BitVec('x4', 8)
x5 = BitVec('x5', 8)
x6 = BitVec('x6', 8)
x7 = BitVec('x7', 8)
x8 = BitVec('x8', 8)
x9 = BitVec('x9', 8)
x10 = BitVec('x10', 8)
x11 = BitVec('x11', 8)
x12 = BitVec('x12', 8)
x13 = BitVec('x13', 8)
x14 = BitVec('x14', 8)
x15 = BitVec('x15', 8)
x16 = BitVec('x16', 8)
x17 = BitVec('x17', 8)
x18 = BitVec('x18', 8)
x19 = BitVec('x19', 8)
x20 = BitVec('x20', 8)
x21 = BitVec('x21', 8)
x22 = BitVec('x22', 8)
x23 = BitVec('x23', 8)
x24 = BitVec('x24', 8)
x25 = BitVec('x25', 8)
x26 = BitVec('x26', 8)
x27 = BitVec('x27', 8)
x28 = BitVec('x28', 8)
x29 = BitVec('x29', 8)
x30 = BitVec('x30', 8)
x31 = BitVec('x31', 8)
x32 = BitVec('x32', 8)
x33 = BitVec('x33', 8)
x34 = BitVec('x34', 8)
x35 = BitVec('x35', 8)

solver = Solver()

solver.add(x0 == 102)
solver.add(2 * (x1 + 31) == 278)
solver.add(7 * (x2 + 1) == 686)
solver.add(3 * x3 == 309)
solver.add(2 * x4 == 246)
solver.add(25 * (x5 - 8) == 2425)
solver.add(x6 == 116)
solver.add(x7 == 95)
solver.add((x8 + 6) == 105)
solver.add(x9 == 3104 / 32)
solver.add(2 * (x10 + 15) == 250)
solver.add(2 * (x11 - 34) == 122)
solver.add((x12 - 42) == 56)
solver.add((x13 + 83) == 184)
solver.add(83 * x14 == 7885)
solver.add(26 * (x15 + 63) == 4238)
solver.add(21 * (x16 - 35 + 72) == 3108)
solver.add(82 * (x17 + 34) == 11808)
solver.add(43 * x18 == 4343)
solver.add(21 * (x19 - 7) == 1848)
solver.add(61 * (x20 - 22) == 4636)
# solver.add(24 / 19 * (x21 + 2) == 155)
solver.add(6 * x22 == 570)
solver.add(71 * (x23 - 24) == 5680)
solver.add(x24 == 97)
solver.add(23 * (x25 + 4) == 2622)
solver.add(x26 == 100)
solver.add(x27 == 95)
solver.add(3 * x28 == 294)
solver.add(x29 == 117)
solver.add(x30 == 116)
solver.add(x31 == 95)
solver.add(126 * (x32 - 3) == 14616)
solver.add(2 * (x33 + 4) == 216)
solver.add(x34 == 121)
solver.add(x35 == 125)

solver.check()
print(solver.model())

x21 = 121
x11 = 95
x17 = 110
x33 = 104
x15 = 100
x1 = 108
x4 = 123
x10 = 110
x32 = 119
x22 = 95
x35 = 125
x34 = 121
x31 = 95
x30 = 116
x29 = 117
x28 = 98
x27 = 95
x26 = 100
x25 = 110
x24 = 97
x23 = 104
x20 = 98
x19 = 95
x18 = 101
x16 = 111
x14 = 95
x13 = 101
x12 = 98
x9 = 97
x8 = 99
x7 = 95
x6 = 116
x5 = 105
x3 = 103
x2 = 97
x0 = 102

print(chr(x0), end="")
print(chr(x1), end="")
print(chr(x2), end="")
print(chr(x3), end="")
print(chr(x4), end="")
print(chr(x5), end="")
print(chr(x6), end="")
print(chr(x7), end="")
print(chr(x8), end="")
print(chr(x9), end="")
print(chr(x10), end="")
print(chr(x11), end="")
print(chr(x12), end="")
print(chr(x13), end="")
print(chr(x14), end="")
print(chr(x15), end="")
print(chr(x16), end="")
print(chr(x17), end="")
print(chr(x18), end="")
print(chr(x19), end="")
print(chr(x20), end="")
print(chr(x21), end="")
print(chr(x22), end="")
print(chr(x23), end="")
print(chr(x24), end="")
print(chr(x25), end="")
print(chr(x26), end="")
print(chr(x27), end="")
print(chr(x28), end="")
print(chr(x29), end="")
print(chr(x30), end="")
print(chr(x31), end="")
print(chr(x32), end="")
print(chr(x33), end="")
print(chr(x34), end="")
print(chr(x35), end="")

flag = b'flag{it_can_be_done_by_hand_but_why}'
