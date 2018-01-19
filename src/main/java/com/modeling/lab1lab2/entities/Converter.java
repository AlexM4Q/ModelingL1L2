package com.modeling.lab1lab2.entities;

import com.modeling.lab1lab2.constants.Functions;

import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Converter extends Processable {

    /**
     * Используется для замены функций на кириллические символы
     */
    public static final Map<String, Character> a1;
    /**
     * Хранит все возможные переменные из входной строки
     */
    private static final List<Character> functions;
    /**
     * Таблица решений
     */
    private static final int[][] table;

    static {
        a1 = new LinkedHashMap<>();
        for (Functions function : Functions.values()) {
            a1.put(function.getName(), function.getCode());
        }

        functions = new ArrayList<>();
        for (char c : "abcdefghijklmnopqrstuwxyz".toCharArray()) {
            functions.add(c);
        }

        table = new int[][]{
                {4, 1, 1, 1, 1, 1, 1, 5, 1, 6},
                {2, 2, 2, 1, 1, 1, 1, 2, 1, 6},
                {2, 2, 2, 1, 1, 1, 1, 2, 1, 6},
                {2, 2, 2, 2, 2, 1, 1, 2, 1, 6},
                {2, 2, 2, 2, 2, 1, 1, 2, 1, 6},
                {2, 2, 2, 2, 2, 2, 1, 2, 1, 6},
                {5, 1, 1, 1, 1, 1, 1, 3, 1, 6},
                {},
                {2, 2, 2, 2, 2, 2, 1, 7, 7, 6}
        };
    }

    /**
     * Стек
     */
    protected final List<Character> stack;
    /**
     * Используется для замены чисел на латинские символы
     */
    private final Map<Character, Double> a2;

    public Converter(final ProcessHandler handler) {
        super(handler);
        this.stack = new ArrayList<>();
        this.a2 = new HashMap<>();
    }

    /**
     * Очистка состояния
     */
    @Override
    public void clear() {
        super.clear();
        stack.clear();
        a2.clear();
    }

    /**
     * Предобработка входной строки
     *
     * @param input входная строка
     */
    @Override
    public void process(String input, final Object... params) {
        clear();

        // Замена функций на кириллические символы
        for (Entry<String, Character> entry : a1.entrySet()) {
            input = input.replace(entry.getKey(), entry.getValue().toString());
        }

        // Заполнение словаря переменная/число
        final List<String> numbers = new ArrayList<>();
        final Matcher matcher = Pattern.compile("[0-9]+([.][0-9]+)?").matcher(input);
        while (matcher.find()) {
            numbers.add(matcher.group());
        }
        numbers.sort((o1, o2) -> o2.length() - o1.length());
        for (String number : numbers) {
            final Double value = Double.valueOf(number);
            if (!a2.values().contains(value)) {
                final Character key = functions.get(a2.size());
                a2.put(key, value);
                // Замена чисел на латинские символы
                input = input.replace(number, key.toString());
            }
        }

        // Инициализация массива символов входной строки
        isProcessed = true;
        buffer = input.toCharArray();
        pointer = 0;
    }

    /**
     * Такт процесса обработки входной строки
     */
    @Override
    public void tact() {
        // Проверка конца процесса
        if (pointer == buffer.length) {
            for (int i = stack.size() - 1; i >= 0; i--) {
                handleResult(stack.remove(i));
            }

            return;
        }

        final char c = buffer[pointer++];
        if (c == ' ') {
            tact();
        }

        final int firstInt = getNumber(c);
        branchingTableResult(firstInt, c);
    }

    private void branchingTableResult(int firstInt, char c) {
        final int secondInt = getLastNumber();
        final int f = table[secondInt][firstInt];

        switch (f) {
            case 1:
                stack.add(c);
                handleStack();
                break;
            case 2:
                handleResult(stack.remove(stack.size() - 1));
                branchingTableResult(firstInt, c);
                break;
            case 3:
                stack.remove(stack.size() - 1);
                break;
            case 4:
                return;
            case 5:
                throw new IllegalArgumentException("Ошибка в структуре скобок");
            case 6:
                handleResult(c);
                break;
            case 7:
                throw new IllegalArgumentException("Ошибка: отсутствует \"(\" ");
        }
    }

    private int getLastNumber() {
        if (stack.isEmpty()) {
            return 0;
        }

        return getNumber(stack.get(stack.size() - 1));
    }

    private int getNumber(final char ch) {
        switch (ch) {
            case ' ':
                return 0;
            case '+':
                return 1;
            case '-':
                return 2;
            case '*':
                return 3;
            case '/':
                return 4;
            case '^':
                return 5;
            case '(':
                return 6;
            case ')':
                return 7;
        }

        if (a1.containsValue(ch)) {
            return 8;
        }

        if (functions.contains(Character.toLowerCase(ch))) {
            return 9;
        }

        throw new IllegalArgumentException(String.valueOf(ch));
    }

    private void handleResult(final char ch) {
//        String output = null;
//        for (Entry<String, Character> entry : a1.entrySet()) {
//            if (entry.getValue() == ch) {
//                output = entry.getKey();
//                break;
//            }
//        }
//
//        if (output == null) {
//            output = String.valueOf(ch);
//        }
//
//        handler.handleResult(output);
        handler.handleResult(String.valueOf(ch));
    }

    private void handleStack() {
        final ArrayList<Character> reversedStack = new ArrayList<>(stack);
        Collections.reverse(reversedStack);
        String stackOut = reversedStack.stream().map(Object::toString).collect(Collectors.joining("\n"));
        for (Entry<String, Character> entry : a1.entrySet()) {
            stackOut = stackOut.replace(entry.getValue().toString(), entry.getKey());
        }
        handler.handleStack(stackOut);
    }

    public Map<Character, Double> getDigitalMap() {
        return a2;
    }
}
