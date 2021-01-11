package photo.mosaic;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TileImage {
    private Color meanColor;
    private BufferedImage tile;

    public BufferedImage getTile() {
        return tile;
    }

    public Color getMeanColor() {
        return meanColor;
    }

    public TileImage(Color meanColor, BufferedImage tile) {
        this.meanColor = meanColor;
        this.tile = tile;
    }
}
