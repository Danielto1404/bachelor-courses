### Finding best hyper-params

In this task we should train our model with **[SMAPE](https://en.wikipedia.org/wiki/Symmetric_mean_absolute_percentage_error)** loss function.

- I choose Ridge Linear Regression so additionally i need to optimize lambda parameter.
- Launch this **[script](data/run_all_datasets.bash)** 
  to find best hyper parameters (h = learning rate, lambda = penalty for big values of weights)
                                                        