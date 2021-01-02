    # uncompyle6 version 3.7.4
# Python bytecode 3.8 (3413)
# Decompiled from: Python 2.7.17 (default, Sep 30 2020, 13:38:04) 
# [GCC 7.5.0]
# Warning: this version of Python has problems handling the Python 3 "byte" type in constants properly.

# Embedded file name: .\main.py
# Compiled at: 2020-05-13 21:31:31
# Size of source mod 2**32: 509 bytes
flag = '35x005x026x034x045x064x0b7x023x056x013x053x053x063x073x073x046x053x083x023x043x056x083x053x056x066x043x013x003x093x043x016x056x036x073x016x083x033x046x036x0d7x0'

def check(text):
    global flag
    res = ''
    for i in text:
        res += hex(int.from_bytes(i.encode(), 'big'))[::-1]
    else:
        return flag == res


if __name__ == '__main__':
    flagin = input('Enter your flag: \n')
    if check(flagin):
        print('[+] You win!')
    else:
        print('[-] You lose!')



# Flag:= SPbCTF{2e155677d5824e85ef41094aec7a83dc}