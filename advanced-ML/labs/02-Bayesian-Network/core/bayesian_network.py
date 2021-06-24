import numpy
import pandas as pd
import pgmpy.models
from pgmpy.factors import factor_product
from pgmpy.factors.discrete import DiscreteFactor
from pgmpy.readwrite import BIFReader

from core.observation import ObservationSet, ObservationsParser


class BayesianNetwork:
    """
    BayesianNetwork BIF model.
    Provides interface for finding joint probability of given observed variables.

    Calculates joint probabilities via pgmpy library (DiscreteFactors, TabularCPD).
    """

    def __init__(self, bif_path, observations_parser=ObservationsParser()):
        """
        Creates Bayesian Network with given dataset path.

        :param bif_path: path to .bif dataset
        :param observations_parser:
        """
        self.bif_path = bif_path
        self.observations_parser = observations_parser

        self.graph = None
        self.nodes = None
        self.compiled = False

    def warn_on_not_compiled(self):
        """
        :raise Exception about not compiled model in case there are request to the objects related with dataset graph.
        """
        if not self.compiled:
            raise Exception('Please run compile function before calling any functions related to graph objects')

    def compile(self):
        """
        Preprocess model:
          1. read graph object from given bif_path.
          2. sets nodes with graph nodes.

        :return: Preprocessed model.
        """
        self.graph = pgmpy.models.BayesianModel = BIFReader(self.bif_path).get_model()
        self.nodes = set(self.graph.nodes)
        self.compiled = True
        return self

    def product_cpds(self, observation_set: ObservationSet) -> DiscreteFactor:
        """
        Multiplies the given to the context of the observed CPD variables of the graph nodes.

        :param observation_set: set of observed variables.
        :return: product of CPD of all nodes of the graph.
        """
        self.warn_on_not_compiled()

        def reduce_cpd(node):
            return observation_set.reduce(self.graph.get_cpds(node))

        return factor_product(*map(reduce_cpd, self.nodes))

    def joint_probability(self, observations: str = '') -> numpy.float64:
        """
        Calculates joint probability of given observations via product nodes CPD with initial reducing them to observed variables context.

        :param observations: observable variables with values for which the joint probability is to be calculated.
        :return: P(variable1=value1, variable2=value2, ... )
        """
        self.warn_on_not_compiled()
        observation_set = ObservationSet(observations, self.nodes, self.observations_parser)
        return self.product_cpds(observation_set).values.sum()

    def joint_sampling_probability(self, observations: str = '', n: int = 10000) -> numpy.float64:
        """
        Calculates joint probability of given observations via sampling states according to their probabilities.

        :param observations: observable variables with values for which the joint probability is to be calculated.
        :param n: amount of samples.
        :return: P(variable1=value1, variable2=value2, ... )
        """
        self.warn_on_not_compiled()
        observation_set = ObservationSet(observations, self.nodes, self.observations_parser)

        if observation_set.isEmpty():
            return numpy.float64(1)

        variables, values = map(list, zip(*observation_set.values_dict.items()))
        distribution = self.product_cpds(ObservationSet.empty())
        samples: pd.DataFrame = distribution.sample(n)[variables]
        count = len(samples[samples[variables] == values].dropna())
        return numpy.float64(count / n)
