package photo.mosaic;

import java.awt.image.BufferedImage;
import java.io.File;

public class TileImage {
    private double meanColor;
    private BufferedImage tile;

    public BufferedImage getTile() {
        return tile;
    }

    public double getMeanColor() {
        return meanColor;
    }

    public TileImage(double meanColor, BufferedImage tile) {
        this.meanColor = meanColor;
        this.tile = tile;
    }
}
