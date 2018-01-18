package com.modeling.lab1lab2.entities;

public interface ProcessHandler {

    /**
     * @param s Сообщение для результа
     */
    void handleResult(String s);

    /**
     * @param s Сообщение для стека
     */
    void handleStack(String s);

}
