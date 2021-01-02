import angr

import claripy


def main():
    p = angr.Project('./mips_mops.elf')

    input_size = 0x22

    argv1 = claripy.BVS("argv1", input_size * 8)

    state = p.factory.entry_state(args=["./mips_mops.elf", argv1])

    sm = p.factory.simulation_manager(state)

    sm.explore(find=(0x1308 + 0x400000))

    found = sm.found[0]

    return found.solver.eval(argv1, cast_to=bytes)


if __name__ == '__main__':
    print(main())

b'Tpbctf{yvur_seriPl_is_LO^E_mips}\x00\x00'
flag = b'spbctf{your_serial_is_LOVE_mips}'
