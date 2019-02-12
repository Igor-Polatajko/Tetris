package model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

import java.util.ArrayList;

public class Block {
    private ArrayList<Rectangle> coords;
    private int width;
    private int tileSize;

    private Block(ArrayList<Rectangle> coords, int width, int tileSize){
        this.coords = coords;
        this.width = width;
        this.tileSize = tileSize;
    }

    public static Block generateBlock(int[][] blockInfo, Color color, int tileSize ,int indent){
        ArrayList<Rectangle> blockCoords = new ArrayList<>();
        int width = 1 ;

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
                }
            }
        }
        return new Block(blockCoords, width, tileSize);
    }

    public int getWidth() {
        return width;
    }

    public ArrayList<Rectangle> getCoords() {
        return coords;
    }

    public void moveLeft(int distance){
        for(Rectangle rect : coords){
            rect.setX(rect.getX() - tileSize * distance);
        }
    }

    public void rightLeft(int distance){

    }
}
