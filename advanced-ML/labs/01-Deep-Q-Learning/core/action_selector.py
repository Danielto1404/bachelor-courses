import torch


class ActionSelector:
    def __init__(self, model, atari_mode=False, device=None):
        super(ActionSelector, self).__init__()
        self.device = device
        self.model = model
        self.atari_mode = atari_mode

    @torch.no_grad()
    def choose_action(self, state):
        """
        :return: (best action, Q-value for best action)
        """
        tensor_state = torch.FloatTensor(state, device=self.device)

        if self.atari_mode:
            tensor_state = tensor_state.unsqueeze(0)

        q_values = self.model(tensor_state)
        action = torch.argmax(q_values).item()

        return action

    def __str__(self):
        return """
Action selector for:

{}
""".format(str(self.model))

    def __repr__(self):
        return str(self)


def load_selector(path, atari_mode=False) -> ActionSelector:
    return ActionSelector(model=torch.load(path, map_location=torch.device('cpu')),
                          atari_mode=atari_mode)


def load_atari_selector(path) -> ActionSelector:
    return load_selector(path, atari_mode=True)
