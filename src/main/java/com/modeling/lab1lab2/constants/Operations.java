package com.modeling.lab1lab2.constants;

import java.util.function.BiFunction;

public enum Operations {

    PLUS((x, y) -> x + y, '+'),
    MINUS((x, y) -> x - y, '-'),
    MULTIPLY((x, y) -> x * y, '*'),
    DIVIDE((x, y) -> x / y, '/'),
    POW(Math::pow, '^');

    private final BiFunction<Double, Double, Double> operation;

    private final char code;

    Operations(final BiFunction<Double, Double, Double> operation, final char code) {
        this.operation = operation;
        this.code = code;
    }

    public BiFunction<Double, Double, Double> getOperation() {
        return operation;
    }

    public char getCode() {
        return code;
    }

}
