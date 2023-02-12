from collections import defaultdict
from math import log


def main():
    kx, _ = map(int, input().split())
    n = int(input())

    class_elements = defaultdict(lambda: [])
    xs_probabilities = defaultdict(lambda: 0)
    ys_conditional_probabilities = defaultdict(lambda: 0)

    for _ in range(n):
        x, y = map(int, input().split())
        class_elements[x].append(y)
        xs_probabilities[x] += 1 / n

    for (x, ys) in class_elements.items():
        y_probabilities = count_probabilities(ys)
        conditional_entropy = 0
        for probability in y_probabilities.values():
            conditional_entropy -= probability * log(probability)

        ys_conditional_probabilities[x] = conditional_entropy

    entropy = 0
    for x in range(1, kx + 1):
        entropy += xs_probabilities[x] * ys_conditional_probabilities[x]

    print(entropy)


def count_probabilities(values: []) -> defaultdict:
    counter = defaultdict(lambda: 0)
    length = len(values)
    for y in values:
        counter[y] += 1 / length

    return counter


if __name__ == '__main__':
    main()
