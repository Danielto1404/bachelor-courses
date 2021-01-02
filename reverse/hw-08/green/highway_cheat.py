array = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
index = 0
ans = ""
f = 0

log_file = open('highway_log', 'r')
address = log_file.readline()

while f != 1:
    address = log_file.readline().strip()
    if address == '0':
        f = 1
    if address == '0x80484ee':  # outbuf[j] = k; break; -> need to upd index -> index:= 0
        ans += (array[index - 1])
        index = 0
    if address == '0x80484dc':  # if ( (unsigned __int8)base64_table[k] == flagBuffer[j] ) -> index++
        index += 1

print(ans)

flag = b'angr+can+do+something'
