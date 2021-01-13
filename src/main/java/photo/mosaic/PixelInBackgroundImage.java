package photo.mosaic;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PixelInBackgroundImage {
    //Pixel in the background image that this PixelInBackgroundImage represents
    private final int pixelInImageWidth;
    private final int pixelInImageHeight;

    //Color of this pixel
    private final Color color;

    //Tile that will go in this pixel
    private BufferedImage tile;

    public PixelInBackgroundImage(int width, int height, Color color) {
        this.pixelInImageWidth = width;
        this.pixelInImageHeight = height;
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
