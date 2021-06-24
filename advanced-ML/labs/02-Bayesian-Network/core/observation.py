from pgmpy.factors.discrete import TabularCPD, DiscreteFactor


class ObservationsParser:
    """
    Provides common parser interface for building queries in Bayesian Network.
    """

    def __init__(self, observations_sep=',', value_sep='='):
        """
        :param observations_sep: variables separator (default: ",")
        :param value_sep: variable name - value separator (default: "=")
        """
        self.observations_sep = observations_sep
        self.value_sep = value_sep

    @staticmethod
    def delete_spaces(string: str):
        return string.replace(' ', '')

    def parse_observation(self, observation: str):
        """
        Unpack string to given value separator format.

        :param observation: observation string.
        :return: tuple (observed_variable_name, observed_variable_value).
        """
        variable, value = observation.split(self.value_sep)
        return variable, value

    def parse_observations(self, observations: str) -> [tuple]:
        """
        Parses list of given variable observations.

        :param observations: string observations in predefined parser format.
        :return: list of tuples [(observed_variable_name, observed_variable_value)].
        """
        observations = self.delete_spaces(observations)
        if not observations:
            return []
        return list(map(self.parse_observation, observations.split(self.observations_sep)))


class ObservationSet:
    """
    Provides common interface for storing evidence variables.

    Reduce operation encapsulates  work with evidence variables for different cpds,
    reducing them to the observation variables context.
    """

    """
    Singleton for empty observation set.
    """
    __empty = None

    def __init__(self, observations: str, nodes: set, parser=ObservationsParser()):
        """
        :param observations: list of observed variables.
        :param nodes: list of all graph nodes. Used for validation observations in __init__ method.
        :param parser: instance of ObservationParser class.
        """
        self.observations_str = observations
        self.parser = parser

        self.values_dict = dict(self.parser.parse_observations(observations))
        self.scope = self.values_dict.keys()

        for v in self.scope:
            if v not in nodes:
                raise Exception('Variable "{}" not in graph.'.format(v))

    def isEmpty(self) -> bool:
        """
        Checks whether in set no observed variables.
        """
        return not bool(self.scope)

    @classmethod
    def empty(cls):
        """
        Returns singleton for empty observation set.
        """
        if not cls.__empty:
            cls.__empty = ObservationSet(observations='', nodes=set())
        return cls.__empty

    def reduce(self, cpd) -> DiscreteFactor:
        """
        Reduces cpd context to observed variables.

        :param cpd: TabularCPD or DiscreteFactor for existing node in Bayesian Network.
        :return: Reduced to the context of the CPD observables without normalization.
        """

        if isinstance(cpd, TabularCPD):
            cpd = cpd.to_factor()

        observed_scope = set(cpd.scope()) & self.scope
        observed_variables = [(variable, self.values_dict[variable]) for variable in observed_scope]
        return cpd.reduce(values=observed_variables, inplace=False)
