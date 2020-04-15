package com.simba.base.utils;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/15
 * @Desc :
 */
public class Tuple<F, L> {
    public F _1;
    public L _2;

    public Tuple(F f, L l) {
        _1 = f;
        _2 = l;
    }

    @Override
    public String toString() {
        return _2.toString();
    }
}
