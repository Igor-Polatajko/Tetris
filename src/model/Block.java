package model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

import java.util.ArrayList;

public class Block {
    private ArrayList<Rectangle> coords;
    private int width;
    private int tileSize;
    private int height;

    private Block(ArrayList<Rectangle> coords, int width, int height, int tileSize) {
        this.coords = coords;
        this.width = width;
        this.height = height;
        this.tileSize = tileSize;
    }

    private Block(Block block) {
        this.coords = getCoordsListCopy(block.coords);
        this.width = block.width;
        this.tileSize = block.tileSize;
    }

    public static Block generateBlock(int[][] blockInfo, Color color, int tileSize, int indent) {
        ArrayList<Rectangle> blockCoords = new ArrayList<>();
        int width = 1;
        int height = 1;

        for (int i = 0; i < blockInfo.length; i++) {
            for (int j = 0; j < blockInfo[0].length; j++) {
                if (blockInfo[i][j] == 1) {
                    Rectangle tile = new Rectangle(tileSize, tileSize);
                    tile.setFill(color);
                    tile.setStroke(Color.BLACK);
                    tile.setStrokeType(StrokeType.INSIDE);
                    tile.setX(j * tileSize + indent * tileSize);
                    tile.setY(i * tileSize);
                    blockCoords.add(tile);
                    width = j;
                    height = i;
                }
            }
        }
        return new Block(blockCoords, width, height, tileSize);
    }

    public int getWidth() {
        return width;
    }

    public int geHeight() {
        return width;
    }

    public Block getClone() {
        return new Block(this);
    }


    public ArrayList<Rectangle> getCoords() {
        return coords;
    }

    public void moveLeft(int distance) {
        for (Rectangle rect : coords) {
            rect.setX(rect.getX() - tileSize * distance);
        }
    }

    public void moveRight(int distance) {
        for (Rectangle rect : coords) {
            rect.setX(rect.getX() + tileSize * distance);
        }
    }

    public void moveDown(int distance) {
        for (Rectangle rect : coords) {
            rect.setY(rect.getY() + tileSize * distance);
        }
    }

    public void moveUp(int distance) {
        for (Rectangle rect : coords) {
            rect.setY(rect.getY() - tileSize * distance);
        }
    }

    private ArrayList<Rectangle> getCoordsListCopy(ArrayList<Rectangle> coords) {
        ArrayList<Rectangle> cloneCoords = new ArrayList<>();

        for (Rectangle rect : coords) {
            Rectangle cloneRect = new Rectangle(rect.getWidth(), rect.getHeight());
            cloneRect.setX(rect.getX());
            cloneRect.setY(rect.getY());
            cloneRect.setFill(rect.getFill());
            cloneRect.setStroke(Color.BLACK);
            cloneCoords.add(cloneRect);
        }

        return cloneCoords;
    }
}
