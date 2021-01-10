package photo.mosaic;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Pixelator {
    private int rows = 50;
    private int cols = 50;
    private int heightPixel;
    private int widthPixel;

    public Pixelator() {
    }

    public Pixelator(int numPics) {
        setRowsAndCols(numPics);
    }

    private void setRowsAndCols(int numPics) {
        if (numPics / 2 != 0) {
            numPics--;
        }
        this.rows = (int) Math.ceil(Math.sqrt(numPics));
        this.cols = (int) Math.floor(Math.sqrt(numPics));
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int[][] pixelate(BufferedImage image) {
        setHeightPixel(image);
        setWidthPixel(image);

        int[][] pixels = getPixels(image);
        return pixels;
    }

    public TileImage pixelate(File imageFile) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        setHeightPixel(image);
        setWidthPixel(image);
        int[][] pixels = getPixels(image);
        double average = getAverage(pixels);

        return new TileImage(average, image);
    }

    private void setHeightPixel(BufferedImage image) {
        int bgHeight = image.getHeight();
        heightPixel = bgHeight / cols;
    }

    private void setWidthPixel(BufferedImage image) {
        int bgWidth = image.getWidth();
        widthPixel = bgWidth / rows;
    }


    public int getHeightPixel() {
        return heightPixel;
    }

    public int getWidthPixel() {
        return widthPixel;
    }

    private int[][] getPixels(BufferedImage image) {
        int[][] pixels = new int[rows][cols];

        for (int row = 0; row < rows; row++){
            for (int col = 0; col < cols; col++) {
                pixels[row][col] = image.getRGB((row * widthPixel) + (widthPixel / 2),
                        (col * heightPixel) + (heightPixel / 2));
            }
        }
        return pixels;
    }

    private double getAverage(int[][] pixels) {
        double total = 0.0;
        double sum = 0.0;
        for (int row = 0; row < pixels.length; row ++) {
            for (int col = 0; col < pixels[row].length; col++) {
                sum += pixels[row][col];
                total++;
            }
        }
        return sum / total;
    }

}
