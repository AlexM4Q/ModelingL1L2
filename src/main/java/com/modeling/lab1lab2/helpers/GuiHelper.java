package com.modeling.lab1lab2.helpers;

import javafx.stage.Stage;

public final class GuiHelper {

    private static Stage primaryStage;

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void setPrimaryStage(Stage primaryStage) {
        GuiHelper.primaryStage = primaryStage;
    }

}
