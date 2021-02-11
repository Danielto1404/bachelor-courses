from functools import reduce

m = int(input())

ones_inputs = []
function_ones_count = 0

for i in range(1 << m):
    value = int(input())
    function_ones_count += value
    if value == 1:
        binary = format(i, 'b').zfill(m)[::-1]
        ones_inputs.append(binary)


def weights_and_bias(binary_input):
    weights = list(map(lambda x: -1 if x == '0' else 1, binary_input))
    and_bias = [reduce(lambda acc, x: acc - 1 if x == 1 else acc, weights, 0.5)]
    return weights + and_bias


or_bias = -0.5

if function_ones_count == 0:
    print(1, 1, sep='\n')
    print(*[0] * m, -1)
else:
    print(2)
    print(function_ones_count, 1)
    for binary_argument in ones_inputs:
        print(*weights_and_bias(binary_argument))
    print(*[1] * function_ones_count, or_bias)
