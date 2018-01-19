package com.modeling.lab1lab2.constants;

import java.util.function.Function;

public enum Functions {

    SIN(Math::sin, "sin", "Синус", 'а'),
    COS(Math::cos, "cos", "Косинус", 'б'),
    ARCSIN(Math::asin, "arcsin", "Арксинус", 'в'),
    ARCCOS(Math::cos, "arccos", "Арккосинус", 'г'),
    TG(Math::tan, "tg", "Тангенс", 'д'),
    CTG(x -> 1 / Math.tan(x), "ctg", "Котангенс", 'е'),
    ACRTG(Math::atan, "arctg", "Арктангенс", 'ё'),
    ARCCTG(x -> Math.atan(-x) + Math.PI / 2, "arcctg", "Арккотангенс", 'ж');

    private final Function<Double, Double> function;

    private final String name;

    private final String rusName;

    private final char code;

    Functions(final Function<Double, Double> function, final String name, final String rusName, final char code) {
        this.function = function;
        this.name = name;
        this.rusName = rusName;
        this.code = code;
    }

    public Function<Double, Double> getFunction() {
        return function;
    }

    public String getName() {
        return name;
    }

    public String getRusName() {
        return rusName;
    }

    public char getCode() {
        return Character.toUpperCase(code);
    }

}
