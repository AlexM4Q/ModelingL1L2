package com.modeling.lab1lab2.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.modeling.lab1lab2.controllers.base.AbstractController;

public class InputController extends AbstractController {

    /**
     * Поле ввода
     */
    @FXML
    private TextField input_TF;

    /**
     * Объект окна представления
     * Используется для закрытия окна по кнопке
     */
    private Stage stage;

    /**
     * Позиция картеки поля ввода
     */
    private int caretPosition;

    /**
     * Флаг принятости ввода (нажат ли OK)
     */
    private boolean isAcceptable;

    public InputController() {
        this.caretPosition = 0;
        this.isAcceptable = false;
    }

    public void setStage(final Stage stage) {
        this.stage = stage;
    }

    public boolean isAcceptable() {
        return isAcceptable;
    }

    /**
     * @return текст с формы ввода
     */
    public String getInput() {
        return input_TF.getText();
    }

    /**
     * Установть текст
     */
    public void setInput(final String input) {
        input_TF.setText(input);
    }

    /**
     * Закрытие окна
     */
    private void close() {
        if (stage != null) {
            stage.close();
        }
    }

    /**
     * Обновить позицию коретки
     */
    private void updatePositionCaret() {
        input_TF.requestFocus();
        input_TF.positionCaret(caretPosition);
    }

    /**
     * Сдвинуть картеку в начало
     */
    @FXML
    private void home_B_action() {
        caretPosition = 0;
        updatePositionCaret();
    }

    /**
     * Сдвинуть каретку влево
     */
    @FXML
    private void left_B_action() {
        caretPosition = caretPosition > 0 ? caretPosition - 1 : 0;
        updatePositionCaret();
    }

    /**
     * Сдвинуть каретку вправо
     */
    @FXML
    private void right_B_action() {
        caretPosition = caretPosition < input_TF.getLength() ? caretPosition + 1 : input_TF.getLength();
        updatePositionCaret();
    }

    /**
     * Сдвинуть картеку в конец
     */
    @FXML
    private void end_B_action() {
        caretPosition = input_TF.getLength();
        updatePositionCaret();
    }

    /**
     * Очистить поле ввода
     */
    @FXML
    private void clear_B_action() {
        input_TF.setText("");
    }

    /**
     * Удалить символ слва от каретки
     */
    @FXML
    private void backspace_B_action() {
        if (caretPosition > 0) {
            input_TF.setText(new StringBuilder(input_TF.getText()).deleteCharAt(caretPosition - 1).toString());
            updatePositionCaret();
        }
    }

    /**
     * Обрабатывает нажатие по любой кнопке ввода (симвло/функция)
     */
    @FXML
    private void input_B_action(ActionEvent event) {
        input_TF.appendText(((Button) event.getSource()).getText());
    }

    /**
     * Принятие ввода и закрытие окна
     */
    @FXML
    private void ok_B_action() {
        isAcceptable = true;
        close();
    }

    /**
     * Отклонение ввода и закрытие окна
     */
    @FXML
    private void cancel_B_action() {
        isAcceptable = false;
        close();
    }
}
