package com.combatgame.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.combatgame.model.*;

public class SelectionScene {

    private Stage stage;
    private GameArena gameArena;

    public SelectionScene(Stage stage) {
        this.stage = stage;
    }

    public Scene createScene() {
        VBox root = new VBox(30);
        root.setAlignment(Pos.CENTER);
        root.getStyleClass().add("root");

        Label title = new Label("Select Your Fighters");
        title.getStyleClass().add("title-label");

        javafx.scene.layout.HBox playersContainer = new javafx.scene.layout.HBox(40);
        playersContainer.setAlignment(Pos.CENTER);

        VBox p1Card = new VBox(15);
        p1Card.getStyleClass().add("card");
        p1Card.setAlignment(Pos.CENTER);
        Label p1Title = new Label("PLAYER 1");
        p1Title.getStyleClass().add("card-title");
        javafx.scene.control.TextField p1NameInput = new javafx.scene.control.TextField("Player 1");
        Label p1Label = new Label("Controls: WASD + F");
        ComboBox<String> p1Combo = new ComboBox<>();
        p1Combo.getItems().addAll("Wizard", "Archer", "Rogue", "Bowler");
        p1Combo.getSelectionModel().selectFirst();
        p1Card.getChildren().addAll(p1Title, p1NameInput, p1Label, p1Combo);

        VBox p2Card = new VBox(15);
        p2Card.getStyleClass().add("card");
        p2Card.setAlignment(Pos.CENTER);
        Label p2Title = new Label("PLAYER 2");
        p2Title.getStyleClass().add("card-title");
        javafx.scene.control.TextField p2NameInput = new javafx.scene.control.TextField("Player 2");
        Label p2Label = new Label("Controls: Arrows + L");
        ComboBox<String> p2Combo = new ComboBox<>();
        p2Combo.getItems().addAll("Wizard", "Archer", "Rogue", "Bowler");
        p2Combo.getSelectionModel().selectFirst();
        p2Card.getChildren().addAll(p2Title, p2NameInput, p2Label, p2Combo);

        playersContainer.getChildren().addAll(p1Card, p2Card);

        Button startButton = new Button("Start Game");
        startButton.getStyleClass().add("button");
        startButton.setOnAction(e -> {
            String p1Type = p1Combo.getValue();
            String p2Type = p2Combo.getValue();
            String p1Name = p1NameInput.getText();
            String p2Name = p2NameInput.getText();
            startGame(p1Type, p2Type, p1Name, p2Name);
        });

        root.getChildren().addAll(title, playersContainer, startButton);
        
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        return scene;
    }

    private void startGame(String p1Type, String p2Type, String p1Name, String p2Name) {
        com.combatgame.model.Character p1 = createCharacter(p1Type);
        p1.setName(p1Name);
        
        com.combatgame.model.Character p2 = createCharacter(p2Type);
        p2.setName(p2Name);
        
        gameArena = new GameArena(stage, p1, p2);
        stage.setScene(gameArena.createScene());
        gameArena.startGameLoop();
    }

    private com.combatgame.model.Character createCharacter(String type) {
        switch (type) {
            case "Wizard": return new Wizard();
            case "Archer": return new Archer();
            case "Rogue": return new Rogue();
            case "Bowler": return new Bowler();
            default: return new Wizard();
        }
    }
}
