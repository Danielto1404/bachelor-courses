# uncompyle6 version 3.7.4
# Python bytecode 3.8 (3413)
# Decompiled from: Python 2.7.17 (default, Sep 30 2020, 13:38:04)
# [GCC 7.5.0]
# Warning: this version of Python has problems handling the Python 3 "byte" type in constants properly.

# Embedded file name: pyinstallerelf.py
# Compiled at: 2020-12-01 17:15:10
# Size of source mod 2**32: 13 bytes
import os

flag = b'(d=\x01eq\x0c8V1\x10\x03!\x0f 6c\x19fgS\x0f8*?<=%1rfa\x00\x0f\x11;'
res = b''
index = 0
text = input('Insert flag to continue playing:\n').encode()
prefix = text[:6]

for i in text[6:]:
    res += bytes([i ^ prefix[(index % 6)]])
    index += 1
if res == flag:
    print('Enjoy!')
else:
    print('That is definitely not a flag.')

## Finding flag
flag = b'(d=\x01eq\x0c8V1\x10\x03!\x0f 6c\x19fgS\x0f8*?<=%1rfa\x00\x0f\x11;'
res = b''
index = 0

prefix = "SPbCTF".encode()
for i in flag:
    res += bytes([i ^ prefix[(index % 6)]])
    index += 1
else:
    print(res)

# Flag:= SPbCTF{4_B17_h4rDEr_Bu7_571Lllll_fe451bLE}
