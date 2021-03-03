import os
from collections import defaultdict
from functools import reduce
from sklearn.metrics import accuracy_score
from matplotlib import pyplot as plt

#
#
# Helper constants
from math import log, log10

LEGIT = "legit"
SPAM = "spam"
NUMBER_OF_PARTS = 10
PARTS_RANGE = range(NUMBER_OF_PARTS)
INF = float('inf')


def get_class(filename):
    if LEGIT in filename:
        return LEGIT
    else:
        return SPAM


#
#
#
# N_GRAM_DECODE function
def n_gram_decode(text, n):
    return list(map(lambda i: str.join(" ", [text[j] for j in range(i, i + n)]), range(len(text) - n + 1)))


#
#
#
# Reading input files
def read_file(filename) -> (str, str, str):
    with open(filename, "r") as f:
        subject = f.readline().split()[1:]
        f.readline()  # skip empty line
        body = f.readline().split()
        return subject, body, get_class(filename=filename)


def read_part(i: int, include_subject=False):
    package_name = "messages/part{}".format(i)
    files = []

    for path in ["{}/{}".format(package_name, file_name) for file_name in os.listdir(package_name)]:
        subject, body, c = read_file(path)

        if include_subject:
            files.append((subject, c))
            files.append((body, c))
        else:
            files.append((body + subject, c))

    return MessagePart(files=files)


#
#
#
# Class representing one folder
class MessagePart(object):
    def __init__(self, files):
        self.files = files

    def class_types(self):
        return list(map(lambda file: file[1], self.files))


#
#
# Class representing numeric values of data in files
class BayesTrainModel(object):
    def __init__(self, train_data):
        # Counter for number of classes (a.k.a SPAM / LEGIT) in given train data
        self.classes_count = defaultdict(lambda: 0)

        # Frequency counter for given word in given class type
        self.frequencies = defaultdict(lambda: 0)

        # Length of train messages
        self.files_count = len(train_data)

        for (message, message_type) in train_data:
            self.classes_count[message_type] += 1

            for word in message:
                self.frequencies[message_type, word] += 1

    def class_probability(self, class_type):
        return self.classes_count.setdefault(class_type, 0) / self.files_count


#
#
# Class representing k-fold cross validation data split
class CrossValidationData(object):
    def __init__(self, train_data_parts: [MessagePart], test_data_part: MessagePart):
        self.train = reduce(lambda acc, part: acc + part.files, train_data_parts, [])
        self.test = test_data_part.files


#
#
# Helper functions for make cross validation
def leave_one_out_split(split_index: int, split_array) -> CrossValidationData:
    return CrossValidationData(train_data_parts=split_array[:split_index] + split_array[split_index + 1:],
                               test_data_part=split_array[split_index]
                               )


def make_prediction(model: BayesTrainModel, test_message: [int], lambda_legit, lambda_spam, alpha) -> \
        (str, float, float):
    #
    legit_count = model.classes_count[LEGIT]
    spam_count = model.classes_count[SPAM]

    probability_legit = log(lambda_legit * model.class_probability(LEGIT))
    probability_spam = log(lambda_spam * model.class_probability(SPAM))

    for word in test_message:
        probability_legit += log(
            (model.frequencies.get((LEGIT, word), 0) + alpha) / (legit_count + 2 * alpha)
        )

        probability_spam += log(
            (model.frequencies.get((SPAM, word), 0) + alpha) / (spam_count + 2 * alpha)
        )

    return LEGIT if probability_legit > probability_spam else SPAM, probability_legit, probability_spam


def find_best_alpha(alpha_values) -> int:
    best_a = INF
    best_accuracy = 0

    for alpha in alpha_values:

        accuracy = 0

        for (model, cvd) in zip(bayes_train_models, cross_validation_data):

            predictions = []
            actual = []

            for (message, message_type) in cvd.test:
                prediction, _, _ = make_prediction(model=model,
                                                   test_message=message,
                                                   lambda_legit=1,
                                                   lambda_spam=1,
                                                   alpha=alpha)
                predictions.append(prediction)
                actual.append(message_type)

            accuracy += accuracy_score(predictions, actual) / NUMBER_OF_PARTS

        if accuracy > best_accuracy:
            best_accuracy = accuracy
            best_a = alpha

        print("Alpha:= {}, accuracy:= {}".format(alpha, accuracy))

    return best_a


def find_best_lambda(legit_lambda_values, alpha):
    best_lambda = 1
    best_accuracy = 0

    lambda_degrees = []
    accuracy_scores = []

    for legit_lambda in legit_lambda_values:

        accuracy = 0
        legit_false_negative_count = 0

        all_predictions = []
        all_actual = []

        for (model, cvd) in zip(bayes_train_models, cross_validation_data):

            predictions = []
            actual = []

            for (message, message_type) in cvd.test:
                prediction, _, _ = make_prediction(model=model,
                                                   test_message=message,
                                                   lambda_legit=legit_lambda,
                                                   lambda_spam=1,
                                                   alpha=alpha)
                predictions.append(prediction)
                actual.append(message_type)

            accuracy += accuracy_score(predictions, actual) / NUMBER_OF_PARTS
            all_predictions.extend(predictions)
            all_actual.extend(actual)

        for x, y in zip(all_predictions, all_actual):
            if x == SPAM and y == LEGIT:
                legit_false_negative_count += 1

        if legit_false_negative_count == 0:
            best_lambda = legit_lambda

        degree = int(log10(legit_lambda))
        lambda_degrees.append(degree)
        accuracy_scores.append(accuracy)

        print()
        print("Legit messages classified as SPAM number:= {}".format(legit_false_negative_count))
        print("Legit lambda 1e{}, accuracy:= {}".format(degree, accuracy))

    return best_lambda, lambda_degrees, accuracy_scores


#
#
# Read all message part for cross validation and training
parts = [read_part(i, include_subject=False) for i in PARTS_RANGE]
#
#
# Making bayes models and cross validation splits
cross_validation_data = [leave_one_out_split(i, parts) for i in PARTS_RANGE]
bayes_train_models = list(map(lambda data: BayesTrainModel(train_data=data.train), cross_validation_data))
#
#
# Finding best alpha
alphas = [10 ** i for i in range(-10, 1)] + [5, 10, 25, 50]
best_alpha = find_best_alpha(alpha_values=alphas)
print("\n~~~~~~~~~~\nBest alpha: = {}".format(best_alpha))

#
#
# Finding best lambda
lambdas = [10 ** i for i in range(0, 301, 20)]
best_legit_lambda, legit_lambda_xs, accuracy_ys = find_best_lambda(legit_lambda_values=lambdas, alpha=best_alpha)
print("\n~~~~~~~~~~\nBest lambda 1e{}".format(int(log10(best_legit_lambda))))


#
#
# Plot for LEGIT messages classified as SPAM
def plot_legit_lambda_accuracy_graphic(xs, ys):
    plt.figure(figsize=(16, 9))
    plt.grid(linestyle='--')
    plt.plot(xs, ys, linestyle='-', marker='.', color='green', label='Accuracy')
    plt.xlabel('Шраф за легитимные сообщения 10^x')
    plt.ylabel('Значение точности')
    plt.legend()
    plt.show()


#
#
# Plot ROC Curve
def plot_ROC_curve():
    data = reduce(lambda acc, part: acc + part.files, parts, [])
    model = BayesTrainModel(train_data=data)

    roc_data = []

    for (message, message_type) in data:
        _, legit_prob, _ = make_prediction(model=model,
                                           test_message=message,
                                           lambda_legit=best_legit_lambda,
                                           lambda_spam=1,
                                           alpha=best_alpha)

        roc_data.append((legit_prob, message_type))

    roc_data.sort(reverse=True)

    legit_count = model.classes_count[LEGIT]
    spam_count = model.classes_count[SPAM]

    x_step = 1 / spam_count
    y_step = 1 / legit_count

    x, y = 0, 0

    roc_xs = []
    roc_ys = []

    for d in roc_data:
        if d[1] == SPAM:
            x += x_step
        else:
            y += y_step

        roc_xs.append(x)
        roc_ys.append(y)

    plt.figure(figsize=(16, 9))
    plt.grid(linestyle='--')
    plt.plot(roc_xs, roc_ys, linestyle='-', marker='.', color='green', label='ROC curve')
    plt.xlabel('False positive rate')
    plt.ylabel('True positive rate')
    plt.legend()
    plt.show()


#
#
# Plot graphics
plot_ROC_curve()
plot_legit_lambda_accuracy_graphic(xs=legit_lambda_xs, ys=accuracy_ys)
