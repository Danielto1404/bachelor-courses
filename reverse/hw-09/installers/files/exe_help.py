# uncompyle6 version 3.7.4
# Python bytecode 3.8 (3413)
# Decompiled from: Python 2.7.17 (default, Sep 30 2020, 13:38:04)
# [GCC 7.5.0]
# Warning: this version of Python has problems handling the Python 3 "byte" type in constants properly.

# Embedded file name: E:\AetherEternity\PycharmProjects\spbctfpytask\pyinstallerwin.py
# Compiled at: 2020-12-01 17:15:10
# Size of source mod 2**32: 13 bytes
import base64


def decode_flag():
    global flag

    decoded_bytes = []

    for ch in flag[::-1].split('==')[:-1]:
        decoded_bytes.append(base64.b64decode(ch + '=='))

    return str.join('', map(lambda byte: byte.decode('utf-8'), decoded_bytes))


flag = b'==Qf==QT==AN==gU==QO==AM==gU==AU==wX==AT==AN==Qb==gU==AM==gT==wX==AN==wX==wM==wS==QM==AT==wX==wN==wU==QV==gS==wX==wd==AM==wV==we==gR==AV==wQ==gY==AU==wU'
res = b''
text = input('Please enter the flag:\n')
for i in text:
    res += base64.b64encode(i.encode())
if flag == res[::-1]:
    print('Flag is corect')
else:
    print('Flag is incorect')

print(decode_flag())

# Flag:= SPbCTF{W0w_JUS7_L1K3_4_N0Rm4L_PR09R4M}
