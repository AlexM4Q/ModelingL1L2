package com.modeling.lab1lab2.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TabPane;
import com.modeling.lab1lab2.constants.Mode;
import com.modeling.lab1lab2.controllers.base.AbstractController;

import java.util.function.Supplier;

public class MainController extends AbstractController {

    @FXML
    private TabPane tabs_TP;
    @FXML
    private ConverterController converterController;
    @FXML
    private CalculatorController calculatorController;

    @FXML
    private CheckBox autoMove_CB;
    @FXML
    private Slider delay_S;
    @FXML
    private Label delay_L;

    @FXML
    public void initialize() {
        final Supplier<Long> longGetter = () -> (long) delay_S.getValue();
        converterController.setDelayGetter(longGetter);
        calculatorController.setDelayGetter(longGetter);
        delay_S.valueProperty().addListener((observable, oldValue, newValue) -> delay_L.setText(String.valueOf(newValue.longValue())));

        calculatorController.setDigitalMapGetter(() -> converterController.getDigitalMap());

        tabs_TP.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() == 1) {
                calculatorController.initMaps();
                calculatorController.setInput(converterController.getInput());
                calculatorController.setOutput(converterController.getOutput());
            }
        });
    }

    @FXML
    private void modeAuto_RB_action() {
        converterController.setMode(Mode.AUTO);
        calculatorController.setMode(Mode.AUTO);
    }

    @FXML
    private void modeTact_RB_action() {
        converterController.setMode(Mode.TACT);
        calculatorController.setMode(Mode.TACT);
    }

}
