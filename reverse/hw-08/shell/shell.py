rax = 0x3546364654ACBBC7
rbx = 0x11D2F874308A73ED

for i in range(8):
    z = rbx % 256
    t = rax % 256
    rbx //= 256
    rax //= 256
    print(chr((z ^ (~t)) % 256), end='')  # случай нечетного бита
    print(chr(z ^ t))  # общий случай четного бита

# *7&d21k$

# nc forkbomb.ru 11538
