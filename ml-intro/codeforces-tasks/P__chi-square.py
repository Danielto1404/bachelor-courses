from collections import defaultdict


def main():
    _, _ = map(int, input().split())

    n = int(input())
    n_range = range(n)

    points_count = defaultdict(lambda: 0)
    rows = defaultdict(lambda: 0)
    columns = defaultdict(lambda: 0)

    for _ in n_range:
        row, column = map(int, input().split())
        points_count[(row, column)] += 1
        rows[row] += 1
        columns[column] += 1 / n

    chi_square_value = 0

    for ((row, column), value) in points_count.items():
        expected = rows[row] * columns[column]
        if expected == 0:
            continue
        chi_square_value += ((value - 2 * expected) * value) / expected

    chi_square_value += n
    print(chi_square_value)


if __name__ == '__main__':
    main()
