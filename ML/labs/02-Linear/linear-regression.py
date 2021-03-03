import itertools

import numpy as np
import numpy.linalg as LA
import matplotlib.pyplot as plt

root_path = '/Users/daniilkorolev/Documents/GitHub/3rd-year/ml/labs/02-Linear/datasets'
data_set_number = 3
file_path = root_path + "/" + str(data_set_number) + ".txt"


# Helper function to extract values
def read_np_matrix_array(start, count, lines=None, dtype=np.int, sep=' '):
    return np.array(
        (
            [np.fromstring(line, dtype=dtype, sep=sep) for line in lines[start: start + count]]
        )
    )


def log_new_method(method_name):
    print('\n\n', '~~~~~~~~~~~~~~~~~~~~~~', '\n', method_name, ':', '\n')


def log_error(error, dataset_name):
    print('\n', 'NRMSE: =', error, 'for', dataset_name, 'dataset')


def log_weights(weights):
    print('\n', 'Weights :=', weights)


#
#
# Reading from dataset from file
with open(file_path, 'r') as file:
    lines = file.readlines()

    # Reading training objects:
    # Indices
    features_count = np.int(lines[0])
    train_objects_count = np.int(lines[1])
    start_objects_index = 2
    #
    #
    # Arrays
    train_dataset_features = read_np_matrix_array(start=start_objects_index,
                                                  count=train_objects_count,
                                                  lines=lines,
                                                  dtype=np.int,
                                                  sep=' ')
    train_dataset_values = train_dataset_features[:, -1]
    train_dataset_features = train_dataset_features[:, :-1]

    #
    #
    # Reading validation objects:
    # Indices
    validation_objects_count = int(lines[start_objects_index + train_objects_count])
    start_validation_objects_index = start_objects_index + train_objects_count + 1
    #
    #
    # Arrays
    test_dataset_features = read_np_matrix_array(start=start_validation_objects_index,
                                                 count=validation_objects_count,
                                                 lines=lines,
                                                 dtype=np.int,
                                                 sep=' ')
    test_dataset_values = test_dataset_features[:, -1]
    test_dataset_features = test_dataset_features[:, :-1]


def append_ones_column(matrix):
    ones_column = np.ones((matrix.shape[0], 1))
    #
    matrix_with_bias_unit = np.append(matrix, ones_column, axis=1)
    return matrix_with_bias_unit


def nrmse(weights, features_vectors, actual_values):
    #
    features_with_bias_unit = append_ones_column(features_vectors)
    #
    rmse = np.sqrt(
        np.mean(
            (features_with_bias_unit @ weights.T - actual_values) ** 2
        )
    )

    return rmse / (actual_values.max() - actual_values.min())


#
#
#
#
# Least squares
def least_squares_weights():
    #
    ones_row = np.ones((1, train_objects_count))
    #
    train_dataset_features_with_ones = np.append(train_dataset_features.T,
                                                 ones_row,
                                                 axis=0)

    weights = train_dataset_values @ LA.pinv(train_dataset_features_with_ones)

    return weights


#
#
# Get weights
LSW = least_squares_weights()
#
#
# Errors
train_LSW_NRMSE = nrmse(weights=LSW,
                        features_vectors=train_dataset_features,
                        actual_values=train_dataset_values)

test_LSW_NRMSE = nrmse(weights=LSW,
                       features_vectors=test_dataset_features,
                       actual_values=test_dataset_values)
#
#
#
# Logs
log_new_method('Least squares')
log_weights(LSW)
log_error(train_LSW_NRMSE, 'train')
log_error(test_LSW_NRMSE, 'test')

#
#
#
#
#
# Gradient descent

iterations = 1000


def get_random_train_object():
    index = np.random.randint(0, train_objects_count)

    train_features = train_dataset_features[index, :]
    train_value = train_dataset_values[index]

    return train_features.reshape((1, train_features.shape[0])), train_value.reshape((1, 1))


def gradient(weights, feature_vectors, value_vectors):
    """

    :param weights: np.array(k+1, ) where k is number of features

    """
    feature_vectors_with_bias_unit = append_ones_column(feature_vectors)

    predictions = np.dot(feature_vectors_with_bias_unit, weights.T).T

    difference = value_vectors - predictions

    gradient_vector = -2 * np.dot(difference, feature_vectors_with_bias_unit)

    gradient_vector /= LA.norm(gradient_vector)

    return gradient_vector


def generate_initial_weights():
    return np.fromiter(
        (1 / train_objects_count
         for _ in range(features_count + 1)),
        dtype=np.double
    ).reshape(1, features_count + 1)


def gradient_descent_weights(iterations, learning_rate):
    weights = generate_initial_weights()

    train_cost_history_local = []
    test_cost_history_local = []

    for it in range(iterations):
        weights = \
            weights - learning_rate * gradient(weights=weights,
                                               feature_vectors=train_dataset_features,
                                               value_vectors=train_dataset_values)

        train_nrmse = nrmse(weights=weights,
                            features_vectors=train_dataset_features,
                            actual_values=train_dataset_values)
        train_cost_history_local.append(train_nrmse)

        test_cost_history_local.append(nrmse(weights=weights,
                                             features_vectors=test_dataset_features,
                                             actual_values=test_dataset_values))

        print(r'iteration: {} | loss on train: = {}'.format(it, train_nrmse))

    return weights, train_cost_history_local, test_cost_history_local


#
#
# Log new method
log_new_method('Gradient descent')
#
#
# Get weights
gradient_weights, train_gradient_cost_history, test_gradient_cost_history = \
    gradient_descent_weights(iterations=iterations,
                             learning_rate=0.003)
#
#
# Errors

train_gradient_NRMSE = nrmse(weights=gradient_weights,
                             features_vectors=train_dataset_features,
                             actual_values=train_dataset_values)

test_gradient_NRMSE = nrmse(weights=gradient_weights,
                            features_vectors=test_dataset_features,
                            actual_values=test_dataset_values)
#
#
#
# Logs
log_weights(gradient_weights)
log_error(train_gradient_NRMSE, 'train')
log_error(test_gradient_NRMSE, 'test')
#
#
#
#
#
#
# Logs new methods
log_new_method('Simulated annealing')


def temperature_function(iteration_number, k=1, initial=1000):
    return initial * k / iteration_number


def probability_of_transition(delta_energy, temperature):
    return np.exp(-delta_energy / temperature)


def isTransition(delta_energy, temperature):
    probability = probability_of_transition(delta_energy, temperature)

    if random_from_0_to_1() < probability:
        return True

    return False


def random_from_0_to_1():
    return np.random.random(1)[0]


def random_addition_value(temperature, min_temperature_coefficient):
    return np.random.randint(
        -temperature * min_temperature_coefficient, temperature * min_temperature_coefficient
    )


def make_transition(old_weights):
    return np.fromiter(
        (
            w + np.random.randint(-50, 50)
            for w in old_weights[0]),
        dtype=np.double
    ).reshape(1, features_count + 1)


def energy_function(weights, feature_vectors, value_vectors):
    return nrmse(weights=weights,
                 features_vectors=feature_vectors,
                 actual_values=value_vectors)


def simulated_annealing():
    #
    temperature = 1000
    min_temp_coefficient = 10000
    minTemperature = temperature / min_temp_coefficient

    weights = generate_initial_weights()
    energy = energy_function(weights=weights,
                             feature_vectors=train_dataset_features,
                             value_vectors=train_dataset_values)

    train_cost_history = []
    test_cost_history = []

    for iteration in itertools.count(start=1):

        n_weights = make_transition(old_weights=weights)

        n_energy = energy_function(weights=n_weights,
                                   feature_vectors=train_dataset_features,
                                   value_vectors=train_dataset_values)

        if n_energy < energy:
            energy = n_energy
            weights = n_weights
        else:
            if isTransition(delta_energy=n_energy - energy, temperature=temperature):
                energy = n_energy
                weights = n_weights

        train_cost_history.append(nrmse(weights=weights,
                                        features_vectors=train_dataset_features,
                                        actual_values=train_dataset_values))

        test_cost_history.append(nrmse(weights=weights,
                                       features_vectors=test_dataset_features,
                                       actual_values=test_dataset_values))

        temperature = temperature_function(iteration)

        if temperature < minTemperature:
            break

    return weights, train_cost_history, test_cost_history


#
#
# Get weights
annealing_weights, train_annealing_cost_history, test_annealing_cost_history = simulated_annealing()
#
#
# Errors

train_annealing_NRMSE = nrmse(weights=annealing_weights,
                              features_vectors=train_dataset_features,
                              actual_values=train_dataset_values)
#
test_annealing_NRMSE = nrmse(weights=annealing_weights,
                             features_vectors=test_dataset_features,
                             actual_values=test_dataset_values)
#
#
#
# Logs
log_weights(annealing_weights)
log_error(train_annealing_NRMSE, 'train')
log_error(test_annealing_NRMSE, 'test')
#
#
#
#
#
#
# Drawing graphics
xs = range(0, iterations)
#
plt.xlabel("Iterations")
plt.ylabel("NRMSE")
#
#
#
# LSW
plt.plot(xs, [train_LSW_NRMSE] * iterations, label='Train LSW')
plt.plot(xs, [test_LSW_NRMSE] * iterations, label='Test LSW')
#
#
#
# Gradient
plt.plot(xs, train_gradient_cost_history[:iterations], label='Train gradient descent')
plt.plot(xs, test_gradient_cost_history[:iterations], label='Test gradient descent')
#
#
#
# Simulated annealing
predicate_value = 10

xs, ys = [], []
for (x, y) in filter(lambda pair: pair[1] < predicate_value, enumerate(train_annealing_cost_history[:iterations])):
    xs.append(x)
    ys.append(y)

plt.plot(xs, ys, label='Train annealing')

xs, ys = [], []
for (x, y) in filter(lambda pair: pair[1] < predicate_value, enumerate(test_annealing_cost_history[:iterations])):
    xs.append(x)
    ys.append(y)
plt.plot(xs, ys, label='Test annealing')

plt.legend()
plt.show()
