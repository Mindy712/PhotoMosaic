package photo.mosaic;

import java.awt.image.BufferedImage;
import java.io.File;

public class TileInBackgroundImage {
    private int row;
    private int col;
    private int color;
    private BufferedImage tile;

    public TileInBackgroundImage(int row, int col, int color) {
        this.row = row;
        this.col = col;
        this.color = color;
    }

    public BufferedImage getTile() {
        return tile;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getColor() {
        return color;
    }

    public void setTile(BufferedImage tile){
        this.tile = tile;
    }
}
