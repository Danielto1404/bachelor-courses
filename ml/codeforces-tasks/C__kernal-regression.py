from math import sqrt, pi, e, cos
from operator import attrgetter


class Object:
    def __init__(self, regressionValue, feature_vector):
        self.regressionValue = regressionValue
        self.features = feature_vector
        self.distance = float('inf')


objects = []


def applySubtractThenFunction(function):
    def combinator(pair):
        return function(pair[0] - pair[1])

    return combinator


#
# Distance functions map
distanceFunctions = \
    {
        'manhattan': lambda xs, ys: sum(
            map(
                applySubtractThenFunction(abs), zip(xs, ys)
            )
        ),

        'euclidean': lambda xs, ys: sqrt(
            sum(
                map(
                    applySubtractThenFunction(lambda x: x ** 2), zip(xs, ys)
                )
            )
        ),

        'chebyshev': lambda xs, ys: max(
            map(
                applySubtractThenFunction(abs), zip(xs, ys)
            )
        )
    }
#
#
# Kernel functions map
kernelFunctions = \
    {
        "uniform": lambda x: 1 / 2 if abs(x) < 1 else 0,

        "triangular": lambda x: 1 - abs(x) if abs(x) < 1 else 0,

        "epanechnikov": lambda x: 3 / 4 * (1 - x ** 2) if abs(x) < 1 else 0,

        "quartic": lambda x: 15 / 16 * ((1 - x ** 2) ** 2) if abs(x) < 1 else 0,

        "triweight": lambda x: 35 / 32 * ((1 - x ** 2) ** 3) if abs(x) < 1 else 0,

        "tricube": lambda x: 70 / 81 * ((1 - abs(x) ** 3) ** 3) if abs(x) < 1 else 0,

        "gaussian": lambda x: 1 / sqrt(2 * pi) * e ** (-1 / 2 * x ** 2),

        "cosine": lambda x: pi / 4 * cos(pi / 2 * x) if abs(x) < 1 else 0,

        "logistic": lambda x: 1 / (2 + e ** x + e ** -x),

        "sigmoid": lambda x: 2 / (pi * (e ** x + e ** -x))
    }
#
#
# Parse objects count and attributes count
objectsCount, attributesCount = map(int, input().split())
#
#
# Parse given objects
for _ in range(objectsCount):
    inputObjectParams = input().split()

    value = int(inputObjectParams[-1])
    features = list(map(int, inputObjectParams[:-1]))

    objects.append(Object(regressionValue=value, feature_vector=features))
#
#
# Parse request object
requestObject = Object(regressionValue=None, feature_vector=list(map(int, input().split())))
#
#
# Parse distance & kernel functions
distanceFunction, kernelFunction = distanceFunctions[input()], kernelFunctions[input()]
#
#
# For each object update distance value to request object
# And then sort
for obj in objects:
    obj.distance = distanceFunction(obj.features, requestObject.features)

objects.sort(key=attrgetter("distance"))
#
#
# Parse window property
windowType, windowValue = input(), int(input())
if windowType == 'variable':
    windowValue = objects[windowValue].distance


def meanValue(objs):
    return sum(map(attrgetter('regressionValue'), objs)) / len(objs)


def calculateRegression():
    global objects, windowValue

    if windowValue == 0:
        #
        #
        filteredObjects = list(filter(lambda o: o.distance == 0, objects))
        #
        # In case we have no zero-located objects we should calculate mean distance for all objects
        if len(filteredObjects) == 0:
            filteredObjects = objects

        return meanValue(filteredObjects)

    else:

        numerator = sum(
            map(
                lambda o: o.regressionValue * kernelFunction(o.distance / windowValue), objects
            )
        )

        denominator = sum(
            map(
                lambda o: kernelFunction(o.distance / windowValue), objects
            )
        )

        return meanValue(objects) if denominator == 0 else numerator / denominator


regression = calculateRegression()
print(regression)
