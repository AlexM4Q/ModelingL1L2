package com.modeling.lab1lab2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.modeling.lab1lab2.helpers.GuiHelper;

public class App extends Application {

    public static void main(final String[] args) {
        launch(App.class, args);
    }

    @Override
    public void start(final Stage primaryStage) throws Exception {
        GuiHelper.setPrimaryStage(primaryStage);

        final Parent root = FXMLLoader.load(getClass().getResource("/components/main/main.fxml"));
        primaryStage.setTitle("Converter");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

}
