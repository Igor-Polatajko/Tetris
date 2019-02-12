package model;


import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

import java.util.ArrayList;
import java.util.Random;

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

    public BlocksGenerator(int tileSize, int indent){
        this.tileSize = tileSize;
        this.indent = indent;
    }

    public Block getBlock() {
        Random rand = new Random();
        int blockType = rand.nextInt(blocksInfo.length - 1);

        Block block = Block.generateBlock(blocksInfo[blockType], blockColors[blockType], tileSize, indent);
        return block;
    }


}

