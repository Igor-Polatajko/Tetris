package model;


import javafx.scene.input.KeyCode;
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

    private Color[] blockColors = {Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN};


    private int tileSize;
    private int indent;
    private int gameFieldHeight;
    private int gameFieldWidth;
    private ArrayList<KeyCode> keyEvents;

    private Callback callback;

    private Block currentBlock;
    private Block nextBlock;
    private boolean isMovedFromKB = false;
    private boolean firstBlock = true;

    private Timer timer = new Timer();
    private TimerTask updateViewTask;

    private ArrayList<Block> blocksOnGameField = new ArrayList<>();


    public interface Callback {
        void drawBlock(Block block);

        void drawNextBlock(Block block);

        void updateScore();

        void gameOver();
    }

    public BlocksGenerator(int tileSize, int indent, int gameFieldHeight, int gameFieldWidth, ArrayList<KeyCode> keyEvents) {
        this.tileSize = tileSize;
        this.indent = indent;
        this.gameFieldHeight = gameFieldHeight;
        this.keyEvents = keyEvents;
        this.gameFieldWidth = gameFieldWidth;
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
                    if (checkGameOver()) {
                        callback.gameOver();
                    }
                    else if (!firstBlock) {
                        callback.updateScore();
                    }
                    firstBlock = false;
                }
                if (keyEvents.isEmpty() || isMovedFromKB) {
                    updateBlockPosition();
                    isMovedFromKB = false;
                }
                else {
                    moveCurrentBlock();
                    isMovedFromKB = true;
                }

            }
        };

        timer.scheduleAtFixedRate(updateViewTask, 0, 200);
    }

    private void moveCurrentBlock() {
        for (KeyCode keyCode : keyEvents) {
            switch (keyCode) {
                case A:
                case LEFT:
                    if (checkSidesBorders(BlockMoveDirections.LEFT)) {
                        currentBlock.move(BlockMoveDirections.LEFT, 1);
                    }
                    break;
                case D:
                case RIGHT:
                    if (checkSidesBorders(BlockMoveDirections.RIGHT)) {
                        currentBlock.move(BlockMoveDirections.RIGHT, 1);
                    }
                    break;
                case TAB:
                case R:
                    currentBlock.rotate();
                    break;
            }
        }
    }

    private void updateBlockPosition() {
        currentBlock.move(BlockMoveDirections.DOWN, 1);
    }

    private boolean checkSidesBorders(BlockMoveDirections moveDirection) {
        boolean mayMove = true;

        if (moveDirection != null) {
            for (Rectangle rect : currentBlock.getCoords()) {
                if (moveDirection == BlockMoveDirections.LEFT) {
                    if (rect.getX() < tileSize) {
                        mayMove = false;
                    }
                }
                if (moveDirection == BlockMoveDirections.RIGHT) {
                    if (rect.getX() > gameFieldWidth - 2 * tileSize) {
                        mayMove = false;
                    }
                }
            }
        }

        return mayMove;
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
        else {
            for (Block block : blocksOnGameField) {
                for (Rectangle blRect : block.getCoords()) {
                    for (Rectangle rect : currentBlock.getCoords()) {
                        if (rect.getY() == blRect.getY() - 35) {
                            if (rect.getX() == blRect.getX()) {
                                collision = true;
                                break;
                            }
                        }
                        if (rect.getY() == gameFieldHeight - tileSize) {
                            collision = true;
                            break;
                        }
                    }
                }
            }
        }
        return collision;
    }

    private boolean checkGameOver() {
        boolean gameOver = false;

        for (Block block : blocksOnGameField) {
            for (Rectangle rect : block.getCoords()) {
                if (rect.getY() < 0) {
                    gameOver = true;
                }
            }
        }
        return gameOver;
    }

    private void createNewBlock() {

        if (nextBlock == null) {
            nextBlock = getBlock();
        }

        nextBlock.move(BlockMoveDirections.UP, 4);
        currentBlock = nextBlock;
        nextBlock = getBlock();

        Block nextBlockToDrawNow = nextBlock.getClone();
        nextBlockToDrawNow.move(BlockMoveDirections.LEFT, indent - 1);

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

