package com.modeling.lab1lab2.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import com.modeling.lab1lab2.controllers.base.ProcessableController;
import com.modeling.lab1lab2.entities.Converter;
import com.modeling.lab1lab2.helpers.GuiHelper;

import java.io.IOException;
import java.util.Map;

public class ConverterController extends ProcessableController<Converter> {

    public ConverterController() {
        super(Converter::new);
    }

    @Override
    public void handleResult(String s) {
        output_TF.setText(output_TF.getText() + s);
    }

    @FXML
    protected void tact_B_action() throws Throwable {
        if (!processable.isProcessed()) {
            stop_B_action();
            processable.process(input_TF.getText());
        }

        processable.tact();
    }

    @FXML
    protected void start_B_action() throws Throwable {
        stop_B_action();
        processable.process(input_TF.getText());
        processable.start(delayGetter.get());
    }

    @Override
    protected void stop_B_action() {
        super.stop_B_action();
        output_TF.setText("");
    }

    @FXML
    private void setInput_B_action() throws IOException {
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("/components/input/input.fxml"));
        final Parent input = loader.load();
        final InputController controller = loader.getController();
        final Stage stage = new Stage();
        controller.setStage(stage);
        controller.setInput(input_TF.getText());
        stage.initOwner(GuiHelper.getPrimaryStage());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setTitle("Мастер-фломастер ввода");
        stage.setScene(new Scene(input));
        stage.showAndWait();

        if (controller.isAcceptable()) {
            input_TF.setText(controller.getInput());
        }
    }

    @FXML
    private void tableMapping_B_action() {

    }

    public Map<Character, Double> getDigitalMap() {
        return processable.getDigitalMap();
    }

    public String getInput() {
        return input_TF.getText();
    }

    public String getOutput() {
        return output_TF.getText();
    }

}
