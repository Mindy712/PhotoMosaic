package photo.mosaic;

import java.awt.image.BufferedImage;

public class Pixelator {
    int rows = 50;
    int cols = 50;

    public Pixelator() {
    }

    public Pixelator(int numPics) {
        this.rows = (int) Math.ceil(Math.sqrt(numPics));
        this.cols = (int) Math.floor(Math.sqrt(numPics));
    }

    public int[][] pixelate(BufferedImage image) {
        int[][] pixels = new int[rows][cols];

        for (int row = 0; row < rows; row++){
            for (int col = 0; col < cols; col++) {
                pixels[row][col] = image.getRGB(row, col);
            }
        }

        return pixels;
    }

}
