package com.combatgame;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        com.combatgame.view.SelectionScene selectionScene = new com.combatgame.view.SelectionScene(stage);
        stage.setScene(selectionScene.createScene());
        stage.setTitle("Combat Game");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
