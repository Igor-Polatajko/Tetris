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

    private double minX;
    private double minY;


    private Block(ArrayList<Rectangle> coords, int width, int height, int tileSize) {
        this.coords = coords;
        this.width = width;
        this.height = height;
        this.tileSize = tileSize;
        calcMinCoords(true);
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

    public int getHeight() {
        return height;
    }

    public Block getClone() {
        return new Block(this);
    }


    public ArrayList<Rectangle> getCoords() {
        return coords;
    }

    public void move(BlockMoveDirections direction, int distance) {
        switch (direction) {
            case LEFT:
                for (Rectangle rect : coords) {
                    rect.setX(rect.getX() - tileSize * distance);
                }
                calcMinCoords(true);
                break;
            case RIGHT:
                for (Rectangle rect : coords) {
                    rect.setX(rect.getX() + tileSize * distance);
                }
                calcMinCoords(true);
                break;
            case UP:
                for (Rectangle rect : coords) {
                    rect.setY(rect.getY() - tileSize * distance);
                }
                calcMinCoords(false);
                break;
            case DOWN:
                for (Rectangle rect : coords) {
                    rect.setY(rect.getY() + tileSize * distance);
                }
                calcMinCoords(false);
                break;
        }

    }

    public void rotate() {
        for (Rectangle rect : coords) {
            double prevX = rect.getX();
            rect.setX(minX + (height * tileSize - (rect.getY() - minY)));
            rect.setY(minY + (prevX - minX));
        }


        height = width + height;
        width = height - width;
        height = height - width;
    }

    private void calcMinCoords(boolean calcX){
       if(calcX) {
           minX = coords.get(0).getX();
       }
        minY = coords.get(0).getY();


        for (Rectangle rect : coords) {
            if (rect.getX() < minX && calcX) {
                minX = rect.getX();
            }
            if (rect.getY() < minY) {
                minY = rect.getY();
            }
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
