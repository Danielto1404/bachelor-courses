import angr


def invoke():
    p = angr.Project('/Users/daniilkorolev/Desktop/08/08/green/bin/bin.elf')

    state = p.factory.entry_state(args=["/Users/daniilkorolev/Desktop/08/08/green/bin/bin.elf"],
                                  add_options={angr.options.LAZY_SOLVES})

    sm = p.factory.simulation_manager(state)

    sm.explore(find=lambda s: b"You win!" in s.posix.dumps(1))

    found = sm.found[0]

    return found.posix.dumps(0)


if __name__ == '__main__':
    print(invoke())
