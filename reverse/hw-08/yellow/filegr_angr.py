import angr


def main():
    p = angr.Project('./filegr.elf')

    simFile = angr.SimFile("mySim", size=0x24 * 8)

    state = p.factory.entry_state(args=["./filegr.elf", "mySim"])

    simFile.set_state(state)

    state.fs.insert('/root/filgerAns', simFile)

    sm = p.factory.simulation_manager(state)

    sm.explore(find=(0x1C01 + 0x400000))

    found = sm.found[0]

    return found.posix.dumps(3)


if __name__ == '__main__':
    print(main())


flag = b'spbctf{4n6r____15_f0r_r34lly_l4zy_r3v3r53r5}'
