package photo.mosaic;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Controller {
    private PhotoMosaicFileChooser fileChooser;
    private BufferedImage backgroundImage;
    private Pixelator pixelator;
    private JPanel errorPanel;

    public Controller(PhotoMosaicFileChooser fileChooser) {
        this.fileChooser = fileChooser;
    }

    public void setErrorPanel(JPanel errorPanel) {
        this.errorPanel = errorPanel;
    }

    public BufferedImage getPhotoMosaic() {
        pixelator = new Pixelator();
        pixelator.setErrorPanel(errorPanel);
        setBackgroundImage(fileChooser.getBackgroundImagePath());
        PixelInBackgroundImage[][] backgroundPixels = getBackgroundPixels();
        TileImage[] tiles = setTiles(fileChooser.getNumTiles());

        //For each pixel in background photo, find the tile that best matches its color
        for (int width = 0; width < backgroundPixels.length; width++) {
            for (int height = 0; height < backgroundPixels[width].length; height++) {
                Color backgroundPixelColor = backgroundPixels[width][height].getColor();
                double closestEuclideanDistance = 0.0;
                double euclideanDistance;
                int closestTile = 0;
                for (int ix = 0; ix < tiles.length; ix++) {
                    euclideanDistance = getEuclideanDistance(backgroundPixelColor, tiles[ix].getAverageColor());
                    if (euclideanDistance < closestEuclideanDistance || closestEuclideanDistance == 0.0) {
                        closestEuclideanDistance = euclideanDistance;
                        closestTile = ix;
                    }
                }
                backgroundPixels[width][height].setTile(tiles[closestTile].getTile());
                addTileToBackgroundImage(backgroundPixels[width][height]);
            }
         }
         return backgroundImage;
    }

    private void setBackgroundImage(String imagePath) {
        backgroundImage = pixelator.resizeBackgroundImage(imagePath);
    }

    private PixelInBackgroundImage[][] getBackgroundPixels() {
        return pixelator.pixelateBgImage(backgroundImage);
    }

    private TileImage[] setTiles(int numTiles) {
        TileImage[] tiles = new TileImage[numTiles];
        File directory = new File(fileChooser.getTilesPath());
        File[] tilesPath = directory.listFiles();
        int tileUpTo = 0;
        for (int ix = 0; ix < tilesPath.length; ix++) {
            if (isImageFile(tilesPath[ix])) {
                TileImage tile = pixelator.pixelateTile(tilesPath[ix]);
                tiles[tileUpTo] = tile;
                tileUpTo++;
            }
        }
        return tiles;
    }

    private boolean isImageFile(File file) {
        if (file.getName().endsWith(".jpg") ||
                file.getName().endsWith(".png") ||
                file.getName().endsWith(".gif") ||
                file.getName().endsWith(".jpeg"))
        {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * @param backgroundPixelColor
     * @param tileColor
     *
     * Measure similarities of two colors
     * @return a smaller number for more similar colors and a larger for less similar
     */
    private double getEuclideanDistance(Color backgroundPixelColor, Color tileColor) {
        double reds = (tileColor.getRed() - backgroundPixelColor.getRed()) *
                (tileColor.getRed() - backgroundPixelColor.getRed());
        double greens = (tileColor.getGreen() - backgroundPixelColor.getGreen()) *
                (tileColor.getGreen() - backgroundPixelColor.getGreen());
        double blues = (tileColor.getBlue() - backgroundPixelColor.getBlue()) *
                (tileColor.getBlue() - backgroundPixelColor.getBlue());
        return Math.sqrt(reds + greens + blues);
    }

    private void addTileToBackgroundImage(PixelInBackgroundImage pixel) {
        BufferedImage resizedTile = getResizedTile(pixel.getTile());
        addImage(backgroundImage,
                resizedTile,
                (float) 1.0,
                pixel.getPixelInImageWidth() * pixelator.getWidthOfPixelBg(),
                pixel.getPixelInImageHeight() * pixelator.getHeightOfPixelBg());
    }

    /**
     * @param tile
     * @return resized tile that is the size of one pixel in the background image
     */
    private BufferedImage getResizedTile(BufferedImage tile) {
        int widthPixel = pixelator.getWidthOfPixelBg();
        int heightPixel = pixelator.getHeightOfPixelBg();
        Image resizedImg = tile.getScaledInstance(widthPixel, heightPixel, java.awt.Image.SCALE_SMOOTH);
        BufferedImage bufferedImg = new BufferedImage(widthPixel, heightPixel, BufferedImage.TYPE_INT_RGB);
        bufferedImg.getGraphics().drawImage(resizedImg, 0, 0 , null);
        return bufferedImg;
    }

    /**
     * @param bgImage
     * @param tile
     * @param opaque
     * @param pixelX
     * @param pixelY
     *
     * Prints the contents of tile on bgImage with the given opaque value
     * Begins at the pixelX and pixelY specified
     */
    private void addImage(BufferedImage bgImage, BufferedImage tile,
                          float opaque, int pixelX, int pixelY) {
        Graphics2D g2d = bgImage.createGraphics();
        g2d.setComposite(
                AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opaque));
        g2d.drawImage(tile, pixelX, pixelY, null);
        g2d.dispose();
    }
}
