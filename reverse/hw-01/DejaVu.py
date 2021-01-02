import sys

verify = [521, 339, 1028, 365, 1132, 352, 833]

flag = ''
for c in [ chr((337 + verify[i]) // 13 - i) for i in range(0, 7)]:
    flag += c

print(flag)

if len(flag) != len(verify):
    sys.exit(-1)

for i in range(0, 7):
    char = ord(flag[i])
    char = (char + i) * 13 - 337
    if char != verify[i]:
        sys.exit(-1)

print('Correct')
