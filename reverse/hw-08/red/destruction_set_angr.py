import angr

import claripy


def main():
    p = angr.Project('./destruction_set.elf')

    hex_code = claripy.BVS('code', 256)
    skip = claripy.BVV('--skipintro')

    state = p.factory.entry_state(
        args=["./destruction_set.elf", skip],
        add_options={angr.options.LAZY_SOLVES},
        stdin=hex_code
    )

    sm = p.factory.simulation_manager(state)

    sm.explore(find=(0x131A + 0x400000))

    skip_value = sm.found[0].solver.eval(hex_code, cast_to=bytes)
    print(skip_value)

    # for f in sm.found:
    #     print(f.posix)


if __name__ == '__main__':
    main()
