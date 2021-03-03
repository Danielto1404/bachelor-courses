from collections import defaultdict

k, n = int(input()), int(input())

conditional_mean, ys_count = defaultdict(lambda: 0), defaultdict(lambda: 0)

mean_of_squares, conditional_mean_squared = 0, 0

for _ in range(n):
    x, y = map(int, input().split())
    conditional_mean[x] += y / n
    mean_of_squares += y ** 2 / n
    ys_count[x] += 1 / n

for (x, count) in ys_count.items():
    conditional_mean_squared += conditional_mean[x] ** 2 / count

print(mean_of_squares - conditional_mean_squared)
