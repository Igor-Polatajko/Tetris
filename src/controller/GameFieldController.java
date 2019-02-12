package controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import model.Block;
import model.BlocksGenerator;

public class GameFieldController {

    @FXML
    private Pane gameFieldPane;

    @FXML
    private Pane nextBlockPane;


    private int tileSize = 35;
    int indent = 4;
    private BlocksGenerator blocksGenerator;




    @FXML
    protected void initialize() {
        blocksGenerator = new BlocksGenerator(tileSize, indent, (int)gameFieldPane.getPrefHeight());
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
