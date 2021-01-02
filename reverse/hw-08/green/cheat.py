from z3 import z3, solve


def check0(num):
    v1 = num + 35881
    return v1 == (-144734034 ^ (4286 * (4294932349 + 1167)))


def check1(a1):
    v1 = (4294960452 + a1)
    return v1 == (1182247522 ^ (52074 * (37454 + 4294907139)))


def check2(a1):
    v1 = (4294927469 + 4294923120)
    return (7606 * (a1 + 46494)) == (-3648869 ^ v1)


def check3(a1):
    v1 = (4294956380 + 59384)
    return (13921 * (34208 + a1)) == (-414417428 ^ v1)


a = z3.BitVec("a", 32)
b = z3.BitVec("b", 32)
c = z3.BitVec("c", 32)
d = z3.BitVec("d", 32)
solve(check0(a), check1(-b), check2(-c), check3(-d))

key = list(map(hex, [52253, 48220, 46021, 63976]))
print(*key)

flag = b'cc1d-bc5c-b3c5-f9e8'
