def check(name, serial):
    name = bytes(name, 'utf-8')

    if len(name) != 12:
        print(name)
        print(len(name))
        return False

    valid = [
        int.from_bytes(name[:4], 'big'),
        int.from_bytes(name[4:8], 'big'),
        int.from_bytes(name[8:], 'big')
    ]

    print(valid)

    valid[0] ^= valid[2]
    valid[2] ^= valid[0]
    valid[0] ^= valid[2]

    res =  '{:08x}-{:08x}-{:08x}'.format(*valid)
    print(res)

    return serial == res

name = input('What is your name?\n> ')
serial = input('... and your serial?\n> ')
print('Your serial is {}'.format('valid!' if check(name, serial) else 'invalid...'))

# Answer:= 32303137-4354465f-5350625f
