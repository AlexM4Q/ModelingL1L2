package com.modeling.lab1lab2.controllers;

import com.modeling.lab1lab2.controllers.base.ProcessableController;
import com.modeling.lab1lab2.entities.Calculator;
import com.modeling.lab1lab2.entities.Converter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.util.Map;
import java.util.function.Supplier;

public class CalculatorController extends ProcessableController<Calculator> {

    @FXML
    private TextArea digitalMap_TA;
    @FXML
    private TextArea functionMap_TA;
    @FXML
    private Label result_L;

    private Supplier<Map<Character, Double>> digitalMapGetter;

    public CalculatorController() {
        super(Calculator::new);
    }

    @Override
    public void handleResult(String s) {
        result_L.setText(s);
    }

    @FXML
    protected void tact_B_action() throws Throwable {
        if (!processable.isProcessed()) {
            stop_B_action();
            processable.process(output_TF.getText(), digitalMapGetter.get());
        }

        processable.tact();
    }

    @FXML
    protected void start_B_action() throws Throwable {
        stop_B_action();
        final Map<Character, Double> digitalMap = digitalMapGetter.get();
        processable.process(output_TF.getText(), digitalMap);
        processable.start(delayGetter.get());
    }

    @Override
    protected void stop_B_action() {
        super.stop_B_action();
        result_L.setText("");
    }

    public void setDigitalMapGetter(final Supplier<Map<Character, Double>> digitalMapGetter) {
        this.digitalMapGetter = digitalMapGetter;
    }

    public void initMaps() {
        digitalMap_TA.clear();
        functionMap_TA.clear();
        final Map<Character, Double> digitalMap = digitalMapGetter.get();
        digitalMap.forEach((variable, x) -> digitalMap_TA.appendText(variable + " = " + x + "\n"));
        Converter.a1.forEach((name, code) -> functionMap_TA.appendText(code + " = " + name + "\n"));
    }

    public void setInput(final String input) {
        input_TF.setText(input);
    }

    public void setOutput(final String output) {
        output_TF.setText(output);
    }

}
