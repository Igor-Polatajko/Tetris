package model;


import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class BlocksGenerator {


    private int[][][] blocksInfo = {
            {
                    {1, 0, 0, 0},
                    {1, 0, 0, 0},
                    {1, 0, 0, 0},
                    {1, 0, 0, 0}
            },
            {
                    {1, 1, 0, 0},
                    {0, 1, 1, 0},
                    {0, 0, 0, 0},
                    {0, 0, 0, 0}
            },
            {
                    {1, 1, 0, 0},
                    {1, 1, 0, 0},
                    {0, 0, 0, 0},
                    {0, 0, 0, 0}
            },
            {
                    {1, 1, 0, 0},
                    {1, 0, 0, 0},
                    {1, 0, 0, 0},
                    {0, 0, 0, 0}
            }

    };

    Color[] blockColors = {Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN};


    private int tileSize;
    private int indent;
    private int gameFieldHeight;

    private Callback callback;

    private Block currentBlock;
    private Block nextBlock;

    private Timer timer = new Timer();
    private TimerTask updateViewTask;

    private ArrayList<Block> blocksOnGameField = new ArrayList<>();


    public interface Callback {
        void drawBlock(Block block);

        void drawNextBlock(Block block);
    }

    public BlocksGenerator(int tileSize, int indent, int gameFieldHeight) {
        this.tileSize = tileSize;
        this.indent = indent;
        this.gameFieldHeight = gameFieldHeight;
    }


    public void generator(Callback callback) {
        this.callback = callback;

        updateViewTask = new TimerTask() {
            @Override
            public void run() {
                if (nextBlock == null || checkCollisions()) {
                    if (nextBlock != null) {
                        blocksOnGameField.add(currentBlock);
                    }
                    createNewBlock();
                }
                updateBlockPosition();

            }
        };

        timer.scheduleAtFixedRate(updateViewTask, 0, 500);
    }

    private void updateBlockPosition() {
        currentBlock.moveDown(1);
    }

    private boolean checkCollisions() {
        boolean collision = false;

        if (blocksOnGameField.size() == 0) {
            for (Rectangle rect : currentBlock.getCoords()) {
                if (rect.getY() >= gameFieldHeight - tileSize) {
                    collision = true;
                    break;
                }
            }
        }
        else{
            for (Block block : blocksOnGameField) {
                for (Rectangle blRect : block.getCoords()) {
                    for (Rectangle rect : currentBlock.getCoords()) {
                        if (rect.getY() >= gameFieldHeight - tileSize || rect.getY() >= blRect.getY() - 35) {
                            collision = true;
                            break;
                        }
                    }
                }
            }
        }
        return collision;
    }

    private void createNewBlock() {

        if (nextBlock == null) {
            nextBlock = getBlock();
        }

        nextBlock.moveUp(4);
        currentBlock = nextBlock;
        nextBlock = getBlock();

        Block nextBlockToDrawNow = nextBlock.getClone();
        nextBlockToDrawNow.moveLeft(indent - 1);

        callback.drawBlock(currentBlock);
        callback.drawNextBlock(nextBlockToDrawNow);
    }


    private Block getBlock() {
        Random rand = new Random();
        int blockType = rand.nextInt(blocksInfo.length);

        Block block = Block.generateBlock(blocksInfo[blockType], blockColors[blockType], tileSize, indent);
        return block;
    }

}

