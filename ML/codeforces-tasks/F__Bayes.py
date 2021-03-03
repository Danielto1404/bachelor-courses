#
#
# Helper function for reading data
def next_int():
    return int(input())


def next_list(offset):
    input_data = input().split()
    return list(map(int, input_data[:offset])) + input_data[offset:]


def count_words(words):
    counter = {}
    for w in words:
        counter.setdefault(w, 0)
        counter[w] += 1
    return counter


class Message:
    def __init__(self, mark, words_count, words):
        self.mark = mark
        self.words_count = words_count
        self.words = words
        self.words_count = count_words(words)

    def __str__(self):
        return "Mark:= " + str(self.mark) + " | " + "Words:= " + str(self.words)


#
#
# Reading data
number_of_classes = next_int()
number_of_classes_range = range(number_of_classes)

lambdas = next_list(offset=number_of_classes)

alpha = next_int()

train_messages_count = next_int()

train_messages = []
test_messages = []

distinct_words_set = set()

for _ in range(train_messages_count):
    data = next_list(offset=2)
    train_messages.append(Message(mark=data[0] - 1,
                                  words_count=data[1],
                                  words=data[2:]
                                  )
                          )
    distinct_words_set = distinct_words_set.union(set(data[2:]))

queries = next_int()

for _ in range(queries):
    data = next_list(offset=1)
    test_messages.append(Message(mark=None,
                                 words_count=data[0],
                                 words=list(set(data[1:]).intersection(distinct_words_set))
                                 )
                         )
#
#
# Distinct words in train dataset
distinct_words_count_range = range(len(distinct_words_set))
distinct_words_set = list(zip(distinct_words_count_range, distinct_words_set))
#
#
# Count classes and filling probabilities of given classes.
classes_count_map = [0] * number_of_classes

for m in train_messages:
    classes_count_map[m.mark] += 1

probability_of_classes = list(map(lambda x: x / train_messages_count, classes_count_map))

#
#
# Filling conditional probabilities of words.
conditional_probabilities = [[0 for _ in distinct_words_count_range] for _ in number_of_classes_range]

for c in number_of_classes_range:
    for m in train_messages:
        if c != m.mark:
            continue

        for (i, distinct) in distinct_words_set:
            if m.words_count.__contains__(distinct):
                conditional_probabilities[c][i] += 1

for i in number_of_classes_range:
    conditional_probabilities[i] = list(
        map(
            lambda x: (x + alpha) / (classes_count_map[i] + 2 * alpha),
            conditional_probabilities[i]
        )
    )

conditional_probabilities_of_messages = []
support_multipliers = []

multiplier_constant = 1e100

for m in test_messages:

    # P(M = m | C = c)
    probabilities = [1 if x > 0 else 0 for x in classes_count_map]
    multipliers_count = [0] * number_of_classes

    for c in number_of_classes_range:

        for (i, word) in distinct_words_set:

            if probabilities[c] == 0:
                break

            if probabilities[c] < 1 / multiplier_constant:
                probabilities[c] *= multiplier_constant
                multipliers_count[c] += 1

            word_probability = conditional_probabilities[c][i]

            if m.words_count.__contains__(word):
                probabilities[c] *= word_probability
            else:
                probabilities[c] *= (1 - word_probability)

    conditional_probabilities_of_messages.append(probabilities)
    support_multipliers.append(multipliers_count)

#
#
# Filling conditional probabilities of classes
conditional_probabilities_of_classes = []


def calculate_conditional_probability_of_class(query, class_mark):
    # P(C = c)
    class_probability = probability_of_classes[class_mark]

    # P(M = m | C = c)
    conditional_probabilities_of_message = conditional_probabilities_of_messages[query][class_mark]
    if conditional_probabilities_of_message == 0:
        return 0

    max_count = max(support_multipliers[query])

    result = class_probability * lambdas[class_mark] * conditional_probabilities_of_message

    for _ in range(support_multipliers[query][class_mark], max_count):
        result *= multiplier_constant

    return result


for query in range(queries):
    probabilities = [0] * number_of_classes
    for c in number_of_classes_range:
        probabilities[c] = calculate_conditional_probability_of_class(query=query,
                                                                      class_mark=c) / sum(
            [
                calculate_conditional_probability_of_class(query=query,
                                                           class_mark=ci)
                for ci in number_of_classes_range
            ]
        )

    # P(C = c | M = m)
    conditional_probabilities_of_classes.append(probabilities)

for row in conditional_probabilities_of_classes:
    print(*row)
