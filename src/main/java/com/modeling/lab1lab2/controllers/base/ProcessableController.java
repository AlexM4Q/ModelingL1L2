package com.modeling.lab1lab2.controllers.base;

import com.modeling.lab1lab2.constants.Mode;
import com.modeling.lab1lab2.entities.ProcessHandler;
import com.modeling.lab1lab2.entities.Processable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.function.Function;
import java.util.function.Supplier;

public abstract class ProcessableController<T extends Processable> extends AbstractController implements ProcessHandler {

    @FXML
    protected TextField output_TF;
    @FXML
    protected TextField input_TF;
    @FXML
    protected TextArea stack_TA;
    @FXML
    protected Button tact_B;
    @FXML
    protected Button start_B;

    protected Supplier<Long> delayGetter;

    protected final T processable;

    protected ProcessableController(final Function<ProcessHandler, T> creator) {
        this.processable = creator.apply(this);
    }

    @Override
    public void handleStack(String s) {
        stack_TA.setText(s);
    }

    public void setDelayGetter(final Supplier<Long> delayGetter) {
        this.delayGetter = delayGetter;
    }

    public void setMode(final Mode mode) {
        switch (mode) {
            case AUTO:
                tact_B.setDisable(true);
                start_B.setDisable(false);
                break;
            case TACT:
                tact_B.setDisable(false);
                start_B.setDisable(true);
                break;
        }
    }

    @FXML
    protected void stop_B_action() {
        processable.clear();
        stack_TA.clear();
    }

}
