package photo.mosaic;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Controller {
    private FileChooser fileChooser;
    private ArrayList<TileImage> tiles = new ArrayList<>();
    private ArrayList<TileInBackgroundImage> backgroundImageList = new ArrayList<>();
    private Random rand = new Random();
    private BufferedImage backgroundImage;
    private Pixelator tilePixelator;
    private Pixelator backgroundPixelator;

    public Controller(FileChooser fileChooser) {
        this.fileChooser = fileChooser;
    }

    public BufferedImage getPhotoMosaic() {
        tilePixelator = new Pixelator();
        backgroundPixelator = new Pixelator(fileChooser.getNumTiles());
        setBackgroundImage(fileChooser.getBackgroundImagePath());
        int[][] backgroundPixels = getBackgroundPixels();
        setTiles();
        setBackgroundImageList(backgroundPixels);
        while (backgroundImageList.size() != 0) {
            int randomPixel = rand.nextInt(backgroundImageList.size());
            int backgroundPixelColor = backgroundImageList.get(randomPixel).getColor();
            double closestColor = 0;
            int closestPixel = 0;
            for (int ix = 0; ix < tiles.size(); ix++) {
                if (Math.abs(backgroundPixelColor - tiles.get(ix).getMeanColor()) < closestColor ||
                    closestColor == 0) {
                    closestColor = tiles.get(ix).getMeanColor();
                    closestPixel = ix;
                }
            }
            backgroundImageList.get(randomPixel).setTile(tiles.get(closestPixel).getTile());
            addTileToBackgroundImage(backgroundImageList.get(randomPixel), randomPixel);
            tiles.remove(closestPixel);
            backgroundImageList.remove(randomPixel);
         }
         return backgroundImage;
    }

    private void addTileToBackgroundImage(TileInBackgroundImage tile, int currentPixel) {
        BufferedImage resizedTile = getResizedTile(tile.getTile());
        addImage(backgroundImage,
                resizedTile,
                (float) 0.5,
                backgroundImageList.get(currentPixel).getRow() * backgroundPixelator.getWidthPixel(),
                backgroundImageList.get(currentPixel).getCol() * backgroundPixelator.getHeightPixel());
    }

    private BufferedImage getResizedTile(BufferedImage tile) {
        int widthPixel = backgroundPixelator.getWidthPixel();
        int heightPixel = backgroundPixelator.getHeightPixel();
        Image resizedImg = tile.getScaledInstance(widthPixel, heightPixel, java.awt.Image.SCALE_SMOOTH);
        BufferedImage bufferedImg = new BufferedImage(widthPixel, heightPixel, BufferedImage.TYPE_INT_RGB);
        bufferedImg.getGraphics().drawImage(resizedImg, 0, 0 , null);
        return bufferedImg;
    }

    /**
     * prints the contents of tile on bgImage with the given opaque value.
     */
    private void addImage(BufferedImage bgImage, BufferedImage tile,
                          float opaque, int pixelX, int pixelY) {
        Graphics2D g2d = bgImage.createGraphics();
        g2d.setComposite(
                AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opaque));
        g2d.drawImage(tile, pixelX, pixelY, null);
        g2d.dispose();
    }


    private void setBackgroundImageList(int[][] backgroundPixels) {
        backgroundImageList.clear();
        for (int row = 0; row < backgroundPixels.length; row++) {
            for (int col = 0; col < backgroundPixels[row].length; col++) {
                backgroundImageList.add(new TileInBackgroundImage(row, col, backgroundPixels[row][col]));
            }
        }
    }

    private int[][] getBackgroundPixels() {
        return backgroundPixelator.pixelate(backgroundImage);
    }

    private void setTiles() {
        tiles.clear();
        File directory = new File(fileChooser.getTilesPath());
        File[] tilesPath = directory.listFiles();
        for (File filePath : tilesPath) {
            TileImage tile = tilePixelator.pixelate(filePath);
            tiles.add(tile);
        }
    }

    public void setBackgroundImage(String imagePath) {
        try {
            backgroundImage = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
