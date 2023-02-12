from math import sqrt

n_range = range(int(input()))

xs = []
ys = []

for _ in n_range:
    x, y = map(int, input().split())
    xs.append(x)
    ys.append(y)


def mean(values: []) -> float:
    return sum(values) / len(values)


mean_x = mean(xs)
mean_y = mean(ys)

numerator = sum(
    (x - mean_x) * (y - mean_y) for (x, y) in zip(xs, ys)
)
denominator = sqrt(
    sum((x - mean_x) ** 2 for x in xs) *
    sum((y - mean_y) ** 2 for y in ys)
)

pearson_correlation = 0 if denominator == 0 else numerator / denominator

print(pearson_correlation)
