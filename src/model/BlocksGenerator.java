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
            },
            {
                    {1, 0, 0, 0},
                    {1, 1, 0, 0},
                    {1, 0, 0, 0},
                    {0, 0, 0, 0}
            }

    };

    private enum GameState {
        IN_GAME, PAUSE
    }

    private Color[] blockColors = {Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN, Color.MAGENTA};


    private GameState currentGameState = GameState.IN_GAME;
    private GameState priviousGameState = GameState.IN_GAME;

    private int tileSize;
    private int indent;
    private int gameFieldHeight;
    private int gameFieldWidth;

    private Callback callback;

    private Block currentBlock;
    private Block nextBlock;
    private boolean firstBlock = true;

    private Timer timer = new Timer();
    private TimerTask blockMoveTask;

    private ArrayList<Block> blocksOnGameField = new ArrayList<>();


    public interface Callback {
        void drawBlock(Block block);

        void drawNextBlock(Block block);

        void updateScore();

        void gameOver();

        void clear(ArrayList<Rectangle> rectangles);

        void pause();

        void resumeGame();
    }

    public BlocksGenerator(int tileSize, int indent, int gameFieldHeight, int gameFieldWidth) {
        this.tileSize = tileSize;
        this.indent = indent;
        this.gameFieldHeight = gameFieldHeight;
        this.gameFieldWidth = gameFieldWidth;
    }


    public void generator(Callback callback) {
        this.callback = callback;

        blockMoveTask = new TimerTask() {
            @Override
            public void run() {
                if (currentGameState == GameState.IN_GAME) {
                    if (priviousGameState != GameState.IN_GAME) {
                        priviousGameState = GameState.IN_GAME;
                        callback.resumeGame();
                    }

                    if (nextBlock == null || checkCollisions()) {
                        if (nextBlock != null) {
                            blocksOnGameField.add(currentBlock);
                        }
                        createNewBlock();
                        if (checkGameOver()) {
                            callback.gameOver();
                            timer.cancel();
                        }
                        else if (!firstBlock) {
                            callback.updateScore();
                        }
                        firstBlock = false;
                        clearFullRows();
                    }

                    updateBlockPosition();
                }
                else {
                    if (priviousGameState != GameState.PAUSE) {
                        priviousGameState = GameState.PAUSE;
                        callback.pause();
                    }
                }
            }
        };

        timer.scheduleAtFixedRate(blockMoveTask, 0, 350);
    }

    public void moveCurrentBlock(KeyCode keyCode) {

        switch (keyCode) {
            case A:
            case LEFT:
                if (currentGameState == GameState.PAUSE) {
                    break;
                }
                if (checkSidesBorders(BlockMoveDirections.LEFT) && checkSideBlocks(BlockMoveDirections.LEFT)) {
                    currentBlock.move(BlockMoveDirections.LEFT, 1);
                }
                break;
            case D:
            case RIGHT:
                if (currentGameState == GameState.PAUSE) {
                    break;
                }
                if (checkSidesBorders(BlockMoveDirections.RIGHT) && checkSideBlocks(BlockMoveDirections.RIGHT)) {
                    currentBlock.move(BlockMoveDirections.RIGHT, 1);
                }
                break;
            case W:
            case UP:
                if (currentGameState == GameState.PAUSE) {
                    break;
                }
                currentBlock.rotate();
                alignIfOutOfBounds();
                break;
            case S:
            case DOWN:
                if (currentGameState == GameState.PAUSE) {
                    break;
                }
                if (!checkCollisions()) {
                    currentBlock.move(BlockMoveDirections.DOWN, 1);
                }
                break;
            case SPACE:
                if (currentGameState == GameState.IN_GAME) {
                    currentGameState = GameState.PAUSE;
                }
                else {
                    currentGameState = GameState.IN_GAME;
                }
                break;
        }
    }

    private void alignIfOutOfBounds() {
        boolean needLeftAlignment = false;
        boolean needRightAlignment = false;
        for (Rectangle rect : currentBlock.getCoords()) {
            if (rect.getX() < 0) {
                needLeftAlignment = true;
            }
            if (rect.getX() > gameFieldWidth - tileSize) {
                needRightAlignment = true;
            }
        }

        if (needLeftAlignment) {
            currentBlock.move(BlockMoveDirections.RIGHT, 1);
        }
        else if (needRightAlignment) {
            currentBlock.move(BlockMoveDirections.LEFT, 1);
        }
        else {
            return;
        }

        alignIfOutOfBounds();
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

    private boolean checkSideBlocks(BlockMoveDirections moveDirection) {
        boolean mayMove;

        if (moveDirection != null) {
            for (Block block : blocksOnGameField) {
                for (Rectangle rect : block.getCoords()) {
                    for (Rectangle cbRect : currentBlock.getCoords()) {
                        if (rect.getY() == cbRect.getY()) {
                            mayMove = rect.getX() != cbRect.getX() +
                                    (moveDirection == BlockMoveDirections.LEFT ? -tileSize :
                                            (moveDirection == BlockMoveDirections.RIGHT) ? tileSize : 0);
                            if (!mayMove) {
                                return false;
                            }
                        }
                    }
                }
            }
        }

        return true;
    }

    private boolean checkCollisions() {
        boolean collision = false;


        for (Rectangle rect : currentBlock.getCoords()) {
            if (rect.getY() >= gameFieldHeight - tileSize) {
                collision = true;
                break;
            }
        }

        for (Block block : blocksOnGameField) {
            for (Rectangle blRect : block.getCoords()) {
                for (Rectangle rect : currentBlock.getCoords()) {
                    if (rect.getY() == blRect.getY() - tileSize) {
                        if (rect.getX() == blRect.getX()) {
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

    private void clearFullRows() {
        boolean isRowFull = false;
        int rowBlocksCounter;

        for (int y = gameFieldHeight - tileSize; y >= 0; y -= tileSize) {
            for (int x = 0; x <= gameFieldWidth - tileSize; x += tileSize) {
                rowBlocksCounter = 0;
                for (Block block : blocksOnGameField) {
                    for (Rectangle rect : block.getCoords()) {
                        if (rect.getY() == y) {
                            rowBlocksCounter++;
                        }
                    }
                }
                isRowFull = rowBlocksCounter == gameFieldWidth / tileSize;
            }
            if (isRowFull) {
                ArrayList<Rectangle> rectangles = new ArrayList<>();
                for (Block block : blocksOnGameField) {
                    for (Rectangle rect : block.getCoords()) {
                        if (rect.getY() == y) {
                            rectangles.add(rect);
                        }
                        if (rect.getY() < y) {
                            rect.setY(rect.getY() + tileSize);
                        }
                    }
                }

                for (Block block : blocksOnGameField) {
                    block.getCoords().removeAll(rectangles);
                }
                callback.clear(rectangles);
                y += tileSize;
            }
        }
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

