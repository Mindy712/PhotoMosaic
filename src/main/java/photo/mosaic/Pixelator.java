package photo.mosaic;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Pixelator class takes an image and gets the RGB values for the pixels of that image
 */
public class Pixelator {
    private JPanel errorPanel;

    //width and height of a pixel in the background image
    private int widthOfPixelBg;
    private int heightOfPixelBg;

    //number of pixels in both the width and height of the background image
    private final int pixelsInWidthBg = 50;
    private final int pixelsInHeightBg = 50;

    //Arraylists used to store the reds, greens, and blues of all the pixels in the image
    //Used to find the average color
    private final ArrayList<Integer> reds = new ArrayList<>();
    private final ArrayList<Integer> greens = new ArrayList<>();
    private final ArrayList<Integer> blues = new ArrayList<>();

    public void setErrorPanel(JPanel errorPanel) {
        this.errorPanel = errorPanel;
    }

    public int getWidthOfPixelBg() {
        return widthOfPixelBg;
    }

    private void setWidthOfPixelBg(int widthOfPixelBg) {
        this.widthOfPixelBg = widthOfPixelBg;
    }

    public int getHeightOfPixelBg() {
        return heightOfPixelBg;
    }

    private void setHeightOfPixelBg(int heightOfPixelBg) {
        this.heightOfPixelBg = heightOfPixelBg;
    }

    /**
     * @param imagePath
     *
     * Resize background image based on how many pixels there are and the size of the pixels.
     * Needed because otherwise a full pixel cannot necessarily be made and then the side/bottom of the image
     *      won't be pixelated.
     * @return resized background image
     */
    public BufferedImage resizeBackgroundImage(String imagePath) {
        BufferedImage bufferedImg = null;
        try {
            BufferedImage image = ImageIO.read(new File(imagePath));
            int widthOfBgImage = image.getWidth();
            int heightOfBgImage = image.getHeight();
            setWidthOfPixelBg(widthOfBgImage / pixelsInWidthBg);
            setHeightOfPixelBg(heightOfBgImage / pixelsInHeightBg);
            int width = widthOfPixelBg * pixelsInWidthBg;
            int height = heightOfPixelBg * pixelsInHeightBg;
            Image resizedImg = image.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
            bufferedImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            bufferedImg.getGraphics().drawImage(resizedImg, 0, 0 , null);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(errorPanel, "Something went wrong.\nPlease try again.");
            e.printStackTrace();
        }
        return bufferedImg;
    }

    public PixelInBackgroundImage[][] pixelateBgImage(BufferedImage image) {
        return getBgImageArray(image);
    }

    public TileImage pixelateTile(File imageFile) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(imageFile);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(errorPanel, "Something went wrong.\nPlease try again.");
            e.printStackTrace();
        }
        int width = image.getWidth();
        int height = image.getHeight();
        Color average = getAverage(image, width, height, 0, 0);

        return new TileImage(average, image);
    }

    /**
     * @param image - background image
     * @return Array of PixelInBackroungImage - the background image pixelated into pixelsInWidthBg x pixelsInHeightBg
     */
    private PixelInBackgroundImage[][] getBgImageArray(BufferedImage image) {
        PixelInBackgroundImage[][] pixels = new PixelInBackgroundImage[pixelsInWidthBg][pixelsInHeightBg];

        for (int pixelUpToWidth = 0; pixelUpToWidth < pixelsInWidthBg; pixelUpToWidth++){
            for (int pixelUpToHeight = 0; pixelUpToHeight < pixelsInHeightBg; pixelUpToHeight++) {
                Color color = getAverage(image, widthOfPixelBg, heightOfPixelBg, pixelUpToWidth, pixelUpToHeight);
                pixels[pixelUpToWidth][pixelUpToHeight] = new PixelInBackgroundImage(pixelUpToWidth, pixelUpToHeight, color);
            }
        }
        return pixels;
    }

    /**
     * @param image - image to get average color
     * @param width - width of image to get average of, or width of pixel if only want part of the image
     * @param height - height of image to get average of, or width of pixel if only want part of the image
     * @param offsetWidth - offset in image of where to get the average from (needed when looking for only part)
     * @param offsetHeight - offset in image of where to get the average from (needed when looking for only part)
     * @return Color that is the average color of the given image/image part
     */
    private Color getAverage(BufferedImage image, int width, int height, int offsetWidth, int offsetHeight) {
        int[][] pixels = new int[width][height];

        for (int x = 0; x < width; x++){
            for (int y = 0; y < height; y++) {
                pixels[x][y] = image.getRGB(x + (width * offsetWidth), y + (height * offsetHeight));
            }
            
        }

        return calculateAverageColor(pixels);
    }

    /**
     * @param pixels - array of pixels with color to get average from
     *
     * Calculates the average reds, greens, and blues
     * @return Color based on the averages calculated
     */
    private Color calculateAverageColor(int[][] pixels) {
        reds.clear();
        greens.clear();
        blues.clear();
        for (int[] width : pixels) {
            for (int height : width) {
                Color color = new Color(height);
                reds.add(color.getRed());
                greens.add(color.getGreen());
                blues.add(color.getBlue());
            }
        }

        int total = reds.size();
        int sumReds = 0;
        int sumGreens = 0;
        int sumBlues = 0;

        for (int ix = 0; ix < total; ix++) {
            sumReds += reds.get(ix);
            sumGreens += greens.get(ix);
            sumBlues += blues.get(ix);
        }

        int red = sumReds / total;
        int green = sumGreens / total;
        int blue = sumBlues / total;

        return new Color(red, green, blue);
    }
}
