import angr


def main():
    p = angr.Project('./100libs.elf')

    state = p.factory.entry_state(args=["./100libs.elf"])

    sm = p.factory.simulation_manager(state)

    sm.explore(find=(0x300D + 0x400000))

    found = sm.found[0]

    return found.posix.dumps(0)


if __name__ == '__main__':
    print(main())

b'spbctf{im_to0_y0un6_7o_d13_s4id_t3h_n00b_r3v3rs3r}'
