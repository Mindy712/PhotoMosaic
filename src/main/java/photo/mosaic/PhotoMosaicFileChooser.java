package photo.mosaic;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Allow users to choose a file from their system directory
 */
public class PhotoMosaicFileChooser {
    private final JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView());
    private final int minNumTiles = 100;
    private JPanel errorPanel;
    private String backgroundImagePath;
    private String tilesPath;
    private int numTiles;
    private JTextField filePath;
    private JTextField folderPath;

    public void setUIElements(JPanel errorPanel, JTextField filePath, JTextField folderPath) {
        this.errorPanel = errorPanel;
        this.filePath = filePath;
        this.folderPath = folderPath;
    }

    /**
     * Choose file for background image. Only allows image files
     */
    public void chooseFile() {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png", "gif", "jpeg");
        fileChooser.setFileFilter(filter);
        int file = fileChooser.showOpenDialog(null);
        if (file == JFileChooser.APPROVE_OPTION)

        {
            // set the label to the path of the selected file
            filePath.setText(fileChooser.getSelectedFile().getAbsolutePath());
            setBackgroundImagePath(fileChooser.getSelectedFile().getAbsolutePath());
        }
    }

    /**
     * Choose directory for tiles. Only allows directories that have a minimum of 100 photos
     */
    public void chooseFolder() {
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int folder = fileChooser.showOpenDialog(null);
        if (folder == JFileChooser.APPROVE_OPTION &&
                fileChooser.getSelectedFile().list().length >= minNumTiles &&
                directoryHas100Images(new File(fileChooser.getSelectedFile().getAbsolutePath())))
        {
            // set the label to the path of the selected file
            folderPath.setText(fileChooser.getSelectedFile().getAbsolutePath());
            setTilesPath(fileChooser.getSelectedFile().getAbsolutePath());
        }
        else
        {
            JOptionPane.showMessageDialog(errorPanel, "Please select a folder with at least 100 images.");
        }
    }

    /**
     * @param tileDirectory
     * Checks if given directory contains at least 100 images
     * @return true if there are at least 100 images, false if not
     */
    private boolean directoryHas100Images(File tileDirectory) {
        int total = 0;
        for (File file : tileDirectory.listFiles()) {
            if (file.isFile() &&
                    (file.getName().endsWith(".jpg") ||
                    file.getName().endsWith(".png") ||
                    file.getName().endsWith(".gif") ||
                    file.getName().endsWith(".jpeg")))
            {
                total++;
            }
        }
        if (total >= 100) {
            numTiles = total;
            return true;
        }
        else {
            return false;
        }
    }

    public String getBackgroundImagePath() {
        return backgroundImagePath;
    }

    public String getTilesPath() {
        return tilesPath;
    }

    public int getNumTiles() {
        return numTiles;
    }

    private void setBackgroundImagePath(String backgroundImagePath) {
        this.backgroundImagePath = backgroundImagePath;
    }

    private void setTilesPath(String tilesPath) {
        this.tilesPath = tilesPath;
    }

    /**
     * Checks if the paths chosen are still valid
     * Needed in case user changed them from when they chose the files, or if they inputted them manually
     * @return
     */
    public boolean pathsAreValid() {
        backgroundImagePath = filePath.getText();
        File bg = new File(backgroundImagePath);

        tilesPath = folderPath.getText();
        File tiles = new File(tilesPath);

        if (bg.isFile() && tiles.isDirectory() && directoryHas100Images(tiles)) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * @param photoMosaic
     * Save the photoMosaic where the user specifies, as a png.
     */
    public void save(BufferedImage photoMosaic) {
        if (photoMosaic == null) {
            JOptionPane.showMessageDialog(errorPanel, "You need a photo mosaic to save first.");
        }
        else {
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Image", ".png");
            fileChooser.setFileFilter(filter);
            int locationToSave = fileChooser.showSaveDialog(null);

            if (locationToSave == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();

                try {
                    String fileName = selectedFile.getAbsolutePath();
                    //If there is already any extension, remove it
                    if (fileName.contains(".")) {
                        fileName = fileName.substring(0, fileName.indexOf("."));
                    }
                    selectedFile = new File(fileName + ".png");
                    ImageIO.write(photoMosaic, "png", selectedFile);
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(errorPanel, "Something went wrong.\nPlease try again.");
                    e.printStackTrace();
                }
            }
        }
    }
}
