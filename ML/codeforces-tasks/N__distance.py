from collections import defaultdict

classes_count = int(input())

n_range = range(int(input()))

class_elements = defaultdict(lambda: [])
all_values = []

for _ in n_range:
    x, y = map(int, input().split())
    all_values.append(x)
    class_elements[y].append(x)


def manhattan_distance(xs: []) -> int:
    distance, n = 0, len(xs)

    for (i, x_value) in enumerate(sorted(xs)):
        distance += (2 * i - n + 1) * x_value

    return 2 * distance


inner_class_distance = sum(
    map(manhattan_distance, class_elements.values())
)

external_class_distance = manhattan_distance(all_values) - inner_class_distance

print(inner_class_distance, external_class_distance, sep='\n')
