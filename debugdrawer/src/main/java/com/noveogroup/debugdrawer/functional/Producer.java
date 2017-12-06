package com.noveogroup.debugdrawer.functional;

@FunctionalInterface
public interface Producer<T> {
    T produce();
}
