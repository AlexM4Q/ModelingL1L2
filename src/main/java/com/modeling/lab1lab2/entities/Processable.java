package com.modeling.lab1lab2.entities;

public abstract class Processable {

    /**
     * Получатель символов
     */
    protected final ProcessHandler handler;

    /**
     * Флаг предобработки входной строки
     */
    protected boolean isProcessed;
    /**
     * Массив символов входой строки
     */
    protected char[] buffer;
    /**
     * Индекс символа массива входной строки
     */
    protected int pointer;

    public Processable(final ProcessHandler handler) {
        this.handler = handler;
    }

    public void clear() {
        isProcessed = false;
        buffer = null;
        pointer = 0;
    }

    public abstract void process(final String input, final Object... params);

    /**
     * Старт процесса обработки входной строки
     *
     * @param pause пауза между шагами
     */
    public void start(final long pause) throws Throwable {
        for (int i = 0; buffer != null && i < buffer.length + 1; i++) {
            tact();

            if (pause > 0) {
                Thread.sleep(pause);
            }
        }
    }

    public abstract void tact() throws Throwable;

    public boolean isProcessed() {
        return isProcessed;
    }

}
