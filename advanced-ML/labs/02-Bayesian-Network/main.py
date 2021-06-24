import sys
import time

import matplotlib.pyplot as plt
import numpy as np
from tqdm import tqdm

from core.bayesian_network import BayesianNetwork


def process_args():
    """
    :return: path to dataset
    """
    args = sys.argv[1:]
    path_to_dataset = 'data/asia.bif'
    if len(args) == 1:
        return args[0]
    return path_to_dataset


def benchmark(callback, *args):
    start = time.time()
    if args is None:
        result = callback()
    else:
        result = callback(*args)
    end = time.time()
    print('computed in: {} seconds'.format(end - start))
    return result


def interact(network: BayesianNetwork):
    """
    Starts interaction loop of queries.

    :param network: Compiled Bayesian network.
    """
    while True:
        query = input('Input query: ')
        probability = benchmark(network.joint_probability, query)
        sample_prob = benchmark(network.joint_sampling_probability, query)
        print("""
        target probability: {}
        sample probability: {}
        """.format(probability, sample_prob))


def probabilities(network: BayesianNetwork, evidence: str, amount_of_samples_range: range, repeat: int):
    """
    :return: (probability via chain rule, [sampling probability], [tim spent on sampling probability calculation)
    """
    probability = network.joint_probability(evidence)
    sample_probabilities, time_spent = [], []
    for n in tqdm(amount_of_samples_range):
        start = time.time()
        result = np.mean([net.joint_sampling_probability(evidence, n) for _ in range(repeat)])
        end = time.time()
        time_spent.append(end - start)
        sample_probabilities.append(result)

    return probability, np.array(sample_probabilities), time_spent, amount_of_samples_range


def plot_graphics(actual_probability, sampling_probabilities, times, samples):
    fig, (prob_plot, error_plot, time_plot) = plt.subplots(3, figsize=(25, 16))

    prob_plot.grid()
    prob_plot.plot(samples, [actual_probability] * len(samples), label='Actual probability')
    prob_plot.plot(samples, sampling_probabilities, label='Sampling probability')
    prob_plot.legend()
    prob_plot.set_xlabel('Amount of samples')
    prob_plot.set_ylabel('Probability')

    error_plot.grid()
    error_plot.plot(samples, np.abs(sampling_probabilities - actual_probability), label='modulo error')
    error_plot.legend()
    error_plot.set_xlabel('Amount of samples')
    error_plot.set_ylabel('Modulo error')
    error_plot.legend()

    time_plot.grid()
    time_plot.plot(samples, times, label='seconds per sample')
    time_plot.set_xlabel('Amount of samples')
    time_plot.set_ylabel('Seconds per sample')
    time_plot.legend()

    plt.savefig('plots/graphics')
    plt.show()


if __name__ == '__main__':
    path = process_args()
    net = benchmark(BayesianNetwork(path).compile)

    interaction = True

    if interaction:
        interact(net)
    else:
        sampling_range = range(10, 50_000, 100)
        observations = 'dysp=no, either=yes'

        plot_graphics(*probabilities(net, observations, sampling_range, 5))
