package controller;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import model.Block;
import model.BlocksGenerator;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class GameFieldController {

    private Timer timer = new Timer();
    private TimerTask updateView = new TimerTask() {
        @Override
        public void run() {

        }
    };

    private int tileSize = 35;
    int indent = 4;
    private BlocksGenerator blocksGenerator = new BlocksGenerator(tileSize, indent);



    @FXML
    private Pane gameFieldPane;

    @FXML
    private Pane nextBlockPane;


    @FXML
    protected void initialize() {
        drawNet();
        drawBlock();
        drawNextBlock();
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



    private void drawBlock(){
        gameFieldPane.getChildren().addAll(blocksGenerator.getBlock().getCoords());
    }

    private void drawNextBlock(){
        Block block = blocksGenerator.getBlock();
        block.moveLeft(indent - 1);
        nextBlockPane.getChildren().addAll(block.getCoords());
    }
}
