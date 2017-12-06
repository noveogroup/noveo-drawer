package com.noveogroup.debugdrawer.functional;

@FunctionalInterface
public interface Function<T, R> {
    R accept(T value);
}
