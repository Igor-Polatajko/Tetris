package controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import model.Block;
import model.BlocksGenerator;
import model.Score;

import java.util.ArrayList;


public class GameFieldController {

    @FXML
    private Pane gameFieldPane;

    @FXML
    private Pane nextBlockPane;

    @FXML
    private Label scoreLabel;


    private int tileSize = 35;
    private int indent = 4;
    private BlocksGenerator blocksGenerator;
    private SceneController sceneController = SceneController.getObj();

    private ArrayList<KeyCode> keyEvents = new ArrayList<>();

    private Score score = Score.getObj();

    @FXML
    protected void initialize() {
        blocksGenerator = new BlocksGenerator(tileSize, indent, (int) gameFieldPane.getPrefHeight(), (int) gameFieldPane.getPrefWidth(), keyEvents);
        drawNet();

        blocksGenerator.generator(new BlocksGenerator.Callback() {
            @Override
            public void drawBlock(Block block) {
                Platform.runLater(() -> gameFieldPane.getChildren().addAll(block.getCoords()));
            }

            @Override
            public void drawNextBlock(Block block) {
                Platform.runLater(() -> {
                            nextBlockPane.getChildren().clear();
                            nextBlockPane.getChildren().addAll(block.getCoords());
                        }
                );
            }

            @Override
            public void updateScore() {
                score.update();
                Platform.runLater(() -> scoreLabel.setText("" + score));
            }

            @Override
            public void gameOver() {
                Platform.runLater(() -> sceneController.setSceneState(SceneStates.GAME_OVER));
            }
        });

    }

    public void setListeners(Scene scene) {
        scene.setOnKeyPressed(ae -> {
            if (!keyEvents.contains(ae.getCode())) {
                keyEvents.add(ae.getCode());
            }
        });
        scene.setOnKeyReleased(ae -> {
            if (keyEvents.contains(ae.getCode())) {
                keyEvents.remove(ae.getCode());
            }
        });
    }

    private void drawNet() {
        int rows = (int) (gameFieldPane.getPrefHeight() / tileSize + 1);
        int cols = (int) (gameFieldPane.getPrefWidth() / tileSize + 1);

        for (int i = 0; i < cols; i++) {
            Line line = new Line(i * tileSize, 0, i * tileSize, gameFieldPane.getPrefHeight());
            gameFieldPane.getChildren().add(line);
        }

        for (int i = 0; i < rows; i++) {
            Line line = new Line(0, i * tileSize, gameFieldPane.getPrefWidth(), i * tileSize);
            gameFieldPane.getChildren().add(line);
        }
    }

}
