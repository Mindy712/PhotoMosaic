package photo.mosaic;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

//explain
public class Pixelator {
    private ArrayList<Integer> reds = new ArrayList<>();
    private ArrayList<Integer> greens = new ArrayList<>();
    private ArrayList<Integer> blues = new ArrayList<>();
    private JPanel errorPanel;
    private int widthOfPixelBg;
    private int heightOfPixelBg;
    private final int pixelsInWidthBg = 50;
    private final int pixelsInHeightBg = 50;

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

    public PixelInBackgroundImage[][] pixelateBgImage(BufferedImage image) {
        PixelInBackgroundImage[][] pixels = getBgImageArray(image);
        return pixels;
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

    private Color getAverage(BufferedImage image, int width, int height, int offsetWidth, int offsetHeight) {
        int[][] pixels = new int[width][height];

        for (int x = 0; x < width; x++){
            for (int y = 0; y < height; y++) {
                pixels[x][y] = image.getRGB(x + (width * offsetWidth), y + (height * offsetHeight));
            }
            
        }

        return calculateAverageColor(pixels);
    }

    private Color calculateAverageColor(int[][] pixels) {
        reds.clear();
        greens.clear();
        blues.clear();
        for (int row = 0; row < pixels.length; row ++) {
            for (int col = 0; col < pixels[row].length; col++) {
                Color color = new Color(pixels[row][col]);
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
}
