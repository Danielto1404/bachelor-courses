objectsCount, classesCount, blocksCount = map(int, input().split())

blocks = [[] for _ in range(blocksCount)]
elements = []

startBlock = 0

for (i, elementType) in enumerate(map(int, input().split())):
    elements.append((i, elementType))

# sorting by element type
for e in sorted(elements, key=lambda x: x[1]):
    blocks[startBlock].append(e[0])
    startBlock = (startBlock + 1) % blocksCount

for block in blocks:
    print(len(block), *list(map(lambda x: x + 1, block)))
