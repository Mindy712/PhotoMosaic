package photo.mosaic;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TileImage {
    //Average color of this tile
    private final Color averageColor;

    //Image that is this this tile
    private final BufferedImage tile;

    public BufferedImage getTile() {
        return tile;
    }

    public Color getAverageColor() {
        return averageColor;
    }

    public TileImage(Color meanColor, BufferedImage tile) {
        this.averageColor = meanColor;
        this.tile = tile;
    }
}
