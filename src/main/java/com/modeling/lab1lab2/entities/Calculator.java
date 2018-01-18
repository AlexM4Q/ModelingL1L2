package com.modeling.lab1lab2.entities;

import com.modeling.lab1lab2.constants.Functions;
import com.modeling.lab1lab2.constants.Operations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Calculator extends Processable {

    /**
     * Стек
     */
    protected final List<Double> stack;

    /**
     * Карта соответствий переменная/значение
     */
    private Map<Character, Double> digitalMap;

    public Calculator(final ProcessHandler handler) {
        super(handler);
        this.stack = new ArrayList<>();
    }

    /**
     * Очистка состояния
     */
    @Override
    public void clear() {
        super.clear();
        stack.clear();
        digitalMap = null;
    }

    @Override
    public void process(final String input, final Object... params) {
        clear();

        buffer = input.toCharArray();
        //noinspection unchecked
        digitalMap = (Map<Character, Double>) params[0];

        isProcessed = true;
    }

    @Override
    public void tact() throws Throwable {
        // Проверка конца процесса
        if (pointer == buffer.length) {
            handleOutput(stack.get(0));

            return;
        }

        final char c = buffer[pointer++];
        if (c == ' ') {
            tact();
        }

        if (digitalMap.containsKey(c)) {
            Double value = digitalMap.get(c);
            stack.add(value);
            handleStack(value);
            return;
        }

        for (Operations operation : Operations.values()) {
            if (operation.getCode() == c) {
                Double last = stack.remove(stack.size() - 1);
                Double preLast = stack.remove(stack.size() - 1);
                Double operationResult = operation.getOperation().apply(preLast, last);
                stack.add(operationResult);
                handleStack(operationResult);
                return;
            }
        }

        for (Functions function : Functions.values()) {
            if (function.getCode() == c) {
                Double last = stack.remove(stack.size() - 1);
                Double functionResult = function.getFunction().apply(last);
                stack.add(functionResult);
                handleStack(functionResult);
                return;
            }
        }
    }

    private void handleOutput(final double result) {
        handler.handleResult(String.valueOf(result));
    }

    private void handleStack(final double element) {
        handler.handleStack(String.valueOf(element));
    }

}
