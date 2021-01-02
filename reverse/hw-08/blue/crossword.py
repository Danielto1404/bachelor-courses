# import pwn
from z3 import *


def process_equations(equtation):
    # * eq is list of strings
    # example:
    # [
    #   "99 x1 =",
    #   "x2 2 1 * x1 - =",
    #   "x3 9487 x1 x2 * - ="
    # ]
    # write your solution here and return answer as string
    solver = z3.Solver()
    var_names = set()
    for j in equtation:
        s = j.split()
        stack = []
        for i in s:
            if i == '+':
                x = stack[len(stack) - 1]
                stack.pop()
                y = stack[len(stack) - 1]
                stack.pop()
                stack.append(x + y)
            elif i == '*':
                x = stack[len(stack) - 1]
                stack.pop()
                y = stack[len(stack) - 1]
                stack.pop()
                stack.append(x * y)
            elif i == '=':
                x = stack[len(stack) - 1]
                stack.pop()
                y = stack[len(stack) - 1]
                stack.pop()
                stack.append(x == y)
            elif i == '-':
                x = stack[len(stack) - 1]
                stack.pop()
                y = stack[len(stack) - 1]
                stack.pop()
                stack.append(x - y)
            else:
                if i[0] != '-' and (i[0] > '9' or i[0] < '0'):
                    stack.append(Int(i))
                    var_names.add(int(i[1:]))
                else:
                    stack.append(int(i))
        solver.add(stack[0])
    solver.check()
    m = solver.model()
    var_names = list(var_names)
    var_names.sort()

    res = ""
    for x in var_names:
        x = 'x' + str(x)
        num = m[Int(x)].as_long()
        res += chr(num)
    return res


def solve(lines):
    # print("Equations:", lines)
    result = process_equations(lines)
    print(result)
    # print("Result:", result)


def make_lines():
    lines = []
    while True:
        line = input()
        if line == "":
            return lines
        if line.startswith("Crossword"):
            continue

        lines.append(line)


if __name__ == "__main__":
    while True:
        solve(make_lines())

flag = b'spbctf{z3_is_too_3asy_f0r_me}'
