import random

object_count, feature_count = map(int, input().split())


class TrainObject:
    def __init__(self, feature_vector, regression_value):
        self.feature_vector = feature_vector + [1]
        self.regression_value = regression_value


train_objects = []

for _ in range(object_count):
    data = input().split()
    train_objects.append(TrainObject(feature_vector=list(map(int, data[:-1])),
                                     regression_value=int(data[-1])))


def get_random_objects(max_count):
    n = min(max_count, object_count)
    random.shuffle(train_objects)
    return train_objects[:n]


# Simple vector operations:
def multiply_by_constant(k, vector):
    return [k * x for x in vector]


def subtract_vectors(a, b):
    return [x - y for (x, y) in zip(a, b)]


def scalar_vector_multiplication(a, b):
    return [x * y for (x, y) in zip(a, b)]


def transpose(matrix):
    n = len(matrix)
    m = len(matrix[0])
    return [[matrix[i][j] for i in range(n)] for j in range(m)]


# Gradient descent functions and helpers
def create_initial_weights():
    return [1 / object_count for _ in range(feature_count + 1)]


def multiply_matrix_by_vector(m, v):
    return [sum(scalar_vector_multiplication(row, v)) for row in m]


def calc_grad(weights, dots):
    feature_vectors = list(
        map(
            lambda o: getattr(o, 'feature_vector'), dots
        )
    )
    regression_values = list(
        map(
            lambda o: getattr(o, 'regression_value'), dots
        )
    )

    diffs = subtract_vectors(
        regression_values,
        multiply_matrix_by_vector(
            feature_vectors,
            weights
        )
    )

    gradient_vector = multiply_matrix_by_vector(
        transpose(feature_vectors),
        multiply_by_constant(
            -2,
            diffs),
    )

    regularization_vector_parameter = multiply_matrix_by_vector(feature_vectors, gradient_vector)

    shift = sum(
        scalar_vector_multiplication(
            regularization_vector_parameter, regularization_vector_parameter
        )
    )

    mu = 0 if shift == 0 else -sum(scalar_vector_multiplication(diffs, regularization_vector_parameter)) / shift

    return multiply_by_constant(mu, gradient_vector)


def gradient_descent_weights(iterations=2000):
    weights = create_initial_weights()

    for k in range(iterations):
        weights = subtract_vectors(
            weights,
            calc_grad(
                weights=weights,
                dots=get_random_objects(max_count=10)

            )
        )

    return weights


print(*gradient_descent_weights(), sep='\n')
