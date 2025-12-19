package com.combatgame.view;

import com.combatgame.model.Character;
import com.combatgame.model.Weapon;
import com.combatgame.model.WeaponRegistry;
import com.combatgame.model.CharacterFactory;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class GameArena {
    private Stage stage;
    private Character player1;
    private Character player2;
    private Pane gameRoot;
    private ProgressBar p1Health;
    private ProgressBar p2Health;
    private Label p1NameLabel;
    private Label p2NameLabel;
    private boolean running = false;
    private List<String> input = new ArrayList<>();

    public GameArena(Stage stage, Character p1, Character p2) {
        this.stage = stage;
        this.player1 = p1;
        this.player2 = p2;
    }

    public Scene createScene() {
        javafx.scene.layout.StackPane root = new javafx.scene.layout.StackPane();
        root.getStyleClass().add("root");

        gameRoot = new Pane();
        gameRoot.setPrefSize(800, 600);

        try {
            javafx.scene.image.Image bgImage = new javafx.scene.image.Image(getClass().getResource("/background.png").toExternalForm());
            javafx.scene.image.ImageView bgView = new javafx.scene.image.ImageView(bgImage);
            bgView.setFitWidth(800);
            bgView.setFitHeight(600);
            gameRoot.getChildren().add(bgView);
        } catch (Exception e) {
            System.err.println("Could not load background image: " + e.getMessage());
            gameRoot.setStyle("-fx-background-color: #222;");
        }

        Line divider = new Line(400, 0, 400, 600);
        divider.setStroke(Color.WHITE);
        divider.setStrokeWidth(2);
        divider.setOpacity(0.3);

        player1.setPosition(100, 500);
        player2.setPosition(700, 500);

        gameRoot.getChildren().addAll(divider, player1.getVisual(), player2.getVisual());

        javafx.scene.layout.BorderPane hud = new javafx.scene.layout.BorderPane();
        hud.setPadding(new javafx.geometry.Insets(20));
        hud.setPickOnBounds(false);

        VBox p1Box = new VBox(5);
        p1NameLabel = new Label(player1.getName() + " (" + player1.getType() + ")"); 
        p1NameLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #4CAF50;");
        p1Health = new ProgressBar(1.0);
        p1Health.setPrefWidth(200);
        p1Health.setStyle("-fx-accent: #4CAF50;");
        p1Box.getChildren().addAll(p1NameLabel, p1Health);

        VBox p2Box = new VBox(5);
        p2Box.setAlignment(javafx.geometry.Pos.TOP_RIGHT);
        p2NameLabel = new Label(player2.getName() + " (" + player2.getType() + ")");
        p2NameLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #2196F3;");
        p2Health = new ProgressBar(1.0);
        p2Health.setPrefWidth(200);
        p2Health.setStyle("-fx-accent: #2196F3;");
        p2Box.getChildren().addAll(p2NameLabel, p2Health);

        hud.setLeft(p1Box);
        hud.setRight(p2Box);

        root.getChildren().addAll(gameRoot, hud);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        scene.setOnKeyPressed(e -> {
            String code = e.getCode().toString();
            if (!input.contains(code)) input.add(code);
        });
        
        scene.setOnKeyReleased(e -> {
            String code = e.getCode().toString();
            input.remove(code);
        });

        return scene;
    }

    public void startGameLoop() {
        running = true;
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!running) return;
                update(now);
            }
        };
        timer.start();
    }

    private List<com.combatgame.model.Projectile> projectiles = new ArrayList<>();

    private void update(long now) {

        player1.applyGravity();
        player1.checkGroundCollision(600);
        
        player2.applyGravity();
        player2.checkGroundCollision(600);

        double p1dx = 0;
        if (input.contains("W")) player1.jump();
        if (input.contains("S")) { }
        if (input.contains("A")) { p1dx = -5; player1.setRotation(180); }
        if (input.contains("D")) { p1dx = 5; player1.setRotation(0); }
        if (p1dx != 0) movePlayer(player1, p1dx, 0);

        double p2dx = 0;
        if (input.contains("UP")) player2.jump();
        if (input.contains("DOWN")) {  }
        if (input.contains("LEFT")) { p2dx = -5; player2.setRotation(180); }
        if (input.contains("RIGHT")) { p2dx = 5; player2.setRotation(0); }
        if (p2dx != 0) movePlayer(player2, p2dx, 0);

        if (input.contains("DIGIT1")) { player1.setWeapon(WeaponRegistry.getWeapon(0)); input.remove("DIGIT1"); }
        if (input.contains("DIGIT2")) { player1.setWeapon(WeaponRegistry.getWeapon(1)); input.remove("DIGIT2"); }
        if (input.contains("DIGIT3")) { player1.setWeapon(WeaponRegistry.getWeapon(2)); input.remove("DIGIT3"); }
        if (input.contains("DIGIT4")) { player1.setWeapon(WeaponRegistry.getWeapon(3)); input.remove("DIGIT4"); }
        if (input.contains("DIGIT5")) { player1.setWeapon(WeaponRegistry.getWeapon(4)); input.remove("DIGIT5"); }

        if (input.contains("I")) { player2.setWeapon(WeaponRegistry.getWeapon(0)); input.remove("I"); }
        if (input.contains("O")) { player2.setWeapon(WeaponRegistry.getWeapon(1)); input.remove("O"); }
        if (input.contains("P")) { player2.setWeapon(WeaponRegistry.getWeapon(2)); input.remove("P"); }
        if (input.contains("K")) { player2.setWeapon(WeaponRegistry.getWeapon(3)); input.remove("K"); }
        if (input.contains("M")) { player2.setWeapon(WeaponRegistry.getWeapon(4)); input.remove("M"); }

        if (input.contains("Q")) { switchCharacterCycle(1, -1); input.remove("Q"); }
        if (input.contains("E")) { switchCharacterCycle(1, 1); input.remove("E"); }

        if (input.contains("U")) { switchCharacterCycle(2, -1); input.remove("U"); }
        if (input.contains("J")) { switchCharacterCycle(2, 1); input.remove("J"); }

        long currentTime = System.currentTimeMillis();
        if (input.contains("F")) {
            int direction = player1.getRotation() == 180 ? -1 : 1;
            shoot(player1, direction, currentTime);
        }
        if (input.contains("L")) {
            int direction = player2.getRotation() == 180 ? -1 : 1;
            shoot(player2, direction, currentTime);
        }

        updateProjectiles();

        updateUI();
    }

    private void movePlayer(Character player, double dx, double dy) {
        double newX = player.getX() + dx;

        if (newX < 0) newX = 0;
        if (newX > 800 - player.getWidth()) newX = 800 - player.getWidth();

        player.setPosition(newX, player.getY());

        player.getVisual().setRotate(player.getRotation());
    }



    private void shoot(Character player, int direction, long currentTime) {
        if (player.getWeapon().canShoot(currentTime)) {
            Weapon w = player.getWeapon();
            double startX = player.getX() + (direction == 1 ? player.getWidth() : 0);
            double startY = player.getY() + player.getHeight() / 2;

            javafx.scene.Node visual;
            
            if (w.getName().equals("Dagger")) {
                try {
                    javafx.scene.image.ImageView iv = new javafx.scene.image.ImageView(new javafx.scene.image.Image(getClass().getResource("/knife.png").toExternalForm()));
                    iv.setFitWidth(60);
                    iv.setPreserveRatio(true);

                    if (direction == -1) {
                        iv.setScaleX(-1);
                    }
                    visual = iv;
                } catch (Exception e) {
                   visual = new javafx.scene.shape.Circle(0, 0, 5, Color.RED);
                }
            } else if (w.getName().equals("Arrow")) {
                try {
                    javafx.scene.image.ImageView iv = new javafx.scene.image.ImageView(new javafx.scene.image.Image(getClass().getResource("/arrow.png").toExternalForm()));
                    iv.setFitWidth(80);
                    iv.setPreserveRatio(true);

                    if (direction == -1) {
                        iv.setScaleX(-1);
                    }
                    visual = iv;
                } catch (Exception e) {
                   visual = new javafx.scene.shape.Circle(0, 0, 5, Color.GREEN);
                }
            } else if (w.getName().equals("Fireball")) {
                try {
                    javafx.scene.image.ImageView iv = new javafx.scene.image.ImageView(new javafx.scene.image.Image(getClass().getResource("/fireball.png").toExternalForm()));
                    iv.setFitWidth(80);
                    iv.setPreserveRatio(true);

                    if (direction == -1) {
                        iv.setScaleX(-1);
                    }
                    visual = iv;
                } catch (Exception e) {
                   visual = new javafx.scene.shape.Circle(0, 0, 6, Color.PURPLE);
                }
            } else if (w.getName().equals("Boulder")) {
                try {
                    javafx.scene.image.ImageView iv = new javafx.scene.image.ImageView(new javafx.scene.image.Image(getClass().getResource("/boulder.png").toExternalForm()));
                    iv.setFitWidth(70);
                    iv.setPreserveRatio(true);

                    if (direction == -1) {
                        iv.setScaleX(-1);
                    }
                    visual = iv;
                } catch (Exception e) {
                   visual = new javafx.scene.shape.Circle(0, 0, 8, Color.GRAY);
                }
            } else if (w.getName().equals("Sword")) {

                javafx.scene.shape.Rectangle sword = new javafx.scene.shape.Rectangle(50, 10);
                sword.setFill(Color.SILVER);
                sword.setStroke(Color.DARKGRAY);
                sword.setStrokeWidth(2);
                if (direction == -1) {
                    sword.setRotate(180);
                }
                visual = sword;
            } else {
                 visual = new javafx.scene.shape.Circle(0, 0, 5, Color.YELLOW);
            }

            double visualHeight = 0;
            if (visual instanceof javafx.scene.image.ImageView) {
                javafx.scene.image.ImageView iv = (javafx.scene.image.ImageView) visual;
                visualHeight = iv.getBoundsInLocal().getHeight();
            } else if (visual instanceof javafx.scene.shape.Circle) {
                visualHeight = ((javafx.scene.shape.Circle) visual).getRadius() * 2;
            }
            double centeredY = startY - (visualHeight / 2);

            com.combatgame.model.Projectile p = new com.combatgame.model.Projectile(
                startX, centeredY, w.getProjectileSpeed(), w.getDamage(), direction, player, visual
            );
            projectiles.add(p);
            gameRoot.getChildren().add(p.getVisual());
        }
    }

    private void updateProjectiles() {
        List<com.combatgame.model.Projectile> toRemove = new ArrayList<>();
        for (com.combatgame.model.Projectile p : projectiles) {
            p.update();

            if (p.getX() < 0 || p.getX() > 800) {
                p.setActive(false);
                toRemove.add(p);
            }

            if (p.isActive()) {
                if (p.getOwner() != player1 && checkCollision(p, player1)) {
                    player1.takeDamage(p.getDamage());
                    p.setActive(false);
                    toRemove.add(p);
                } else if (p.getOwner() != player2 && checkCollision(p, player2)) {
                    player2.takeDamage(p.getDamage());
                    p.setActive(false);
                    toRemove.add(p);
                }
            }
        }
        
        projectiles.removeAll(toRemove);
        for (com.combatgame.model.Projectile p : toRemove) {
            gameRoot.getChildren().remove(p.getVisual());
        }
    }

    private boolean checkCollision(com.combatgame.model.Projectile p, Character c) {
        return p.getVisual().getBoundsInParent().intersects(c.getVisual().getBoundsInParent());
    }

    private void updateUI() {
        p1Health.setProgress((double) player1.getHealth() / player1.getMaxHealth());
        p2Health.setProgress((double) player2.getHealth() / player2.getMaxHealth());

        if (player1.isDead()) endGame(player2);
        else if (player2.isDead()) endGame(player1);
    }

    private void endGame(Character winner) {
        running = false;
        
        VBox overlay = new VBox(20);
        overlay.getStyleClass().add("game-over-overlay");
        overlay.setMaxSize(400, 300);

        Label winLabel = new Label(winner.getName() + " WINS!");
        winLabel.getStyleClass().add("win-label");
        
        javafx.scene.control.Button restartBtn = new javafx.scene.control.Button("Play Again");
        restartBtn.getStyleClass().add("button");
        restartBtn.setOnAction(e -> {
             stage.setScene(new SelectionScene(stage).createScene());
        });

        overlay.getChildren().addAll(winLabel, restartBtn);
        
        ((javafx.scene.layout.StackPane) gameRoot.getParent()).getChildren().add(overlay);
    }

    private void switchCharacterCycle(int playerNum, int direction) {
        Character currentChar = (playerNum == 1) ? player1 : player2;
        String currentType = currentChar.getType();

        int currentIndex = CharacterFactory.getCharacterIndex(currentType);
        String[] allTypes = CharacterFactory.getAllCharacterTypes();
        int nextIndex = (currentIndex + direction + allTypes.length) % allTypes.length;
        String nextType = allTypes[nextIndex];

        Character newChar = CharacterFactory.createCharacter(nextType);
        currentChar.copyStateTo(newChar);

        gameRoot.getChildren().remove(currentChar.getVisual());
        
        if (playerNum == 1) {
            player1 = newChar;
            p1NameLabel.setText(player1.getName() + " (" + player1.getType() + ")");
        } else {
            player2 = newChar;
            p2NameLabel.setText(player2.getName() + " (" + player2.getType() + ")");
        }
        
        gameRoot.getChildren().add(newChar.getVisual());
        newChar.getVisual().setRotate(newChar.getRotation());
    }
}
