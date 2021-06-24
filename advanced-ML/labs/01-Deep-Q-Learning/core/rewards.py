import numpy as np


def naive_reward(state, next_state, reward):
    return reward


def velocity_scaler(state, next_state, reward):
    """
    Adds to reward bonus for high velocity.
    """
    _, velocity = next_state
    return reward + 100 * abs(velocity)


def velocity_potentials(state, next_state, reward):
    """
    Adds to rewards bonus for continuous velocity increasing.
    """
    _, velocity = state
    _, next_velocity = next_state
    return reward + 1000 * (0.99 * abs(next_velocity) - abs(velocity))


def clip_atari_reward(state, next_state, reward):
    return np.sign(reward)


def lunar_reward(state, next_state, reward):
    x, y, vx, vy, _, w, _, _ = state
    x1, y1, vx1, vy1, _, w1, is_left, is_right = next_state

    if is_left or is_right:
        reward += 10

    dvx = abs(vx1) - abs(vx)
    dvy = abs(vy1) - abs(vy)

    return reward - 100 * (dvx + dvy) - 20 * abs(w1)
