from functools import reduce

classesNumber = int(input())

confusionMatrix = [[] for _ in range(classesNumber)]

for i in range(classesNumber):
    confusionMatrix[i] = list(map(int, input().split()))


def transpose(m):
    return [[m[j][i] for j in range(classesNumber)] for i in range(classesNumber)]


def divide(numerator, denominator):
    return 0 if denominator == 0 else numerator / denominator


confusionMatrix = transpose(confusionMatrix)
elementsCount = sum(map(sum, confusionMatrix))
#
#
# Predicted & actual classes count
predicted = list(map(sum, confusionMatrix))
actual = list(reduce(lambda acc, row: map(sum, zip(acc, row)), confusionMatrix))
#
#
# Data processing
true_positive = [confusionMatrix[i][i] for i in range(classesNumber)]

false_positive = [predicted[i] - true_positive[i] for i in range(classesNumber)]

false_negative = [actual[i] - true_positive[i] for i in range(classesNumber)]

true_negative = [elementsCount - true_positive[i] - false_positive[i] - false_negative[i]
                 for i in range(classesNumber)]
#
#
# Recall and precision
recall = [divide(true_positive[i], true_positive[i] + false_negative[i])
          for i in range(classesNumber)]

precision = [divide(true_positive[i], true_positive[i] + false_positive[i])
             for i in range(classesNumber)]
#
#
#
f1_measure = [divide(2 * recall[i] * precision[i], recall[i] + precision[i])
              for i in range(classesNumber)]
#
#
# Weight precision and recall
weight_precision = \
    sum(
        [divide(true_positive[i] * actual[i], predicted[i]) for i in range(classesNumber)]
    ) / elementsCount

weight_recall = sum(true_positive) / elementsCount
#
#
# macro & micro F-measures
macro_F_measure = divide(2 * weight_precision * weight_recall, weight_precision + weight_recall)

micro_F_measure = \
    sum(
        map(lambda classIndex: actual[classIndex] * f1_measure[classIndex], range(classesNumber))
    ) / elementsCount
#
#
#
print(macro_F_measure, micro_F_measure, sep='\n')
