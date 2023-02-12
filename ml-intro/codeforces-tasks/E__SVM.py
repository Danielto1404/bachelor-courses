from random import shuffle, randint

object_count = int(input())

kernel_distances = []
classes = []

for _ in range(object_count):
    data = list(map(int, input().split()))
    kernel_distances.append(data[:-1])
    classes.append(data[-1])

max_lambda_value = int(input())


# Simple vector operations:
def multiply_by_constant(k, vector):
    return [k * x for x in vector]


def subtract_vectors(a, b):
    return [x - y for (x, y) in zip(a, b)]


def scalar_vector_multiplication(a, b):
    return [x * y for (x, y) in zip(a, b)]


def smart_division(a, b):
    return 0 if b == 0 else a / b


#
# Class representing SMO algorithm
class SVM:
    def __init__(self, C, kernel_matrix, class_values):
        #
        # Ranges & objects count
        self.train_objects_count = len(class_values)
        self.object_range = range(self.train_objects_count)
        #
        #
        # Max lambda value
        self.max_lambda_value = C
        #
        # Kernel and lambdas init
        self.kernel_matrix = kernel_matrix
        self.class_values = class_values
        #
        #
        self.shift_value = 0
        self.lambdas = [0 for _ in self.object_range]

    def calculate_L_H(self, i, j):
        lambda_i, lambda_j = self.lambdas[i], self.lambdas[j]
        if self.class_values[i] == self.class_values[j]:
            L = max(0, lambda_i + lambda_j - self.max_lambda_value)
            H = min(self.max_lambda_value, lambda_i + lambda_j)
        else:
            L = max(0, lambda_j - lambda_i)
            H = min(self.max_lambda_value, self.max_lambda_value + lambda_j - lambda_i)
        return L, H

    def get_random_int(self, i):
        j = randint(0, self.train_objects_count - 1)
        while i == j:
            j = randint(0, self.train_objects_count - 1)

        return j

    def calculate_error(self, i):
        return sum(
            scalar_vector_multiplication(
                scalar_vector_multiplication(
                    self.lambdas,
                    self.class_values
                ),
                self.kernel_matrix[i]
            )) - self.class_values[i]

    def get_shift_value(self, i):
        return self.class_values[i] - sum(
            scalar_vector_multiplication(
                scalar_vector_multiplication(
                    self.lambdas,
                    self.class_values
                ),
                self.kernel_matrix[i]
            )
        )

    def calculate_shift_value(self, eps):
        self.shift_value = 0
        idx = None
        # Find index to calculate shift value
        for i in self.object_range:
            if eps < self.lambdas[i] and self.lambdas[i] + eps < self.max_lambda_value:
                idx = i
                break

        if not (idx is None):
            self.shift_value = self.get_shift_value(idx)
            return

        count = 0
        for i in self.object_range:
            if eps < self.lambdas[i]:
                self.shift_value += self.get_shift_value(i)
                count += 1
        self.shift_value = smart_division(self.shift_value, count)

    def find_lambdas(self, iterations, eps):

        indices = list(range(self.train_objects_count))

        for _ in range(iterations):

            shuffle(indices)

            for index_picker in range(self.train_objects_count):
                i = indices[index_picker]
                j = indices[self.get_random_int(index_picker)]

                error_on_i_lambda = self.calculate_error(i)
                error_on_j_lambda = self.calculate_error(j)
                prev_lambda_j = self.lambdas[j]

                L, H = self.calculate_L_H(i, j)

                if H - L < eps:
                    continue

                eta = 2 * self.kernel_matrix[i][j] - (self.kernel_matrix[i][i] + self.kernel_matrix[j][j])

                possible_new_lambda_j = prev_lambda_j + self.class_values[j] * (
                        error_on_j_lambda - error_on_i_lambda) / eta

                new_lambda_j = min(max(L, possible_new_lambda_j), H)

                self.lambdas[j] = new_lambda_j
                self.lambdas[i] += self.class_values[i] * self.class_values[j] * (prev_lambda_j - new_lambda_j)

        self.calculate_shift_value(eps=eps)


svm = SVM(C=max_lambda_value,
          kernel_matrix=kernel_distances,
          class_values=classes)

svm.find_lambdas(iterations=2000, eps=1e-8)
print(*svm.lambdas, svm.shift_value, sep='\n')
