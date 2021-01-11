package photo.mosaic;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PixelInBackgroundImage {
    private int pixelInImageWidth;
    private int pixelInImageHeight;
    private Color color;
    private BufferedImage tile;

    public PixelInBackgroundImage(int row, int col, Color color) {
        this.pixelInImageWidth = row;
        this.pixelInImageHeight = col;
        this.color = color;
    }

    public BufferedImage getTile() {
        return tile;
    }

    public int getPixelInImageWidth() {
        return pixelInImageWidth;
    }

    public int getPixelInImageHeight() {
        return pixelInImageHeight;
    }

    public Color getColor() {
        return color;
    }

    public void setTile(BufferedImage tile){
        this.tile = tile;
    }
}
