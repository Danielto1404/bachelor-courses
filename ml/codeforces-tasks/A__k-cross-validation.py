# TIME LIMIT for some undefined cases even in PyPy

objects, classes, blocksNumber = map(int, input().split())

blocks = [[] for _ in range(blocksNumber)]
typeElementsMap = {}

startBlock = 0


def updateTypeElementsMap(typeKey, value):
    values = typeElementsMap.get(typeKey, [])
    typeElementsMap[typeKey] = values + [value]


def fillTypeElements(elements):
    global startBlock
    for e in elements:
        blocks[startBlock].append(e)
        startBlock = (startBlock + 1) % blocksNumber


for (i, elementType) in enumerate(map(int, input().split())):
    updateTypeElementsMap(elementType, i)

for typeElements in typeElementsMap.values():
    fillTypeElements(typeElements)

for block in blocks:
    print(len(block), *list(map(lambda x: x + 1, block)))
