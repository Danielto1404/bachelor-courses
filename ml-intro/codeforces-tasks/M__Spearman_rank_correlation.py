from collections import defaultdict

n = int(input())

xs, ys, points = [], [], []
xs_ranks, ys_ranks = defaultdict(lambda: 0), defaultdict(lambda: 0)

for i in range(n):
    x, y = map(int, input().split())
    xs.append(x)
    ys.append(y)
    points.append((x, y))

xs.sort()
ys.sort()

for i in range(n):
    x, y = xs[i], ys[i]
    xs_ranks[x] = i
    ys_ranks[y] = i

rank, correlation = 0, 0

for (x, y) in points:
    rank += (xs_ranks[x] - ys_ranks[y]) ** 2

correlation = 1 - 6 * rank / (n ** 3 - n)
print(correlation)
