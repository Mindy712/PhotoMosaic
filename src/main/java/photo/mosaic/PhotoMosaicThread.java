package photo.mosaic;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class PhotoMosaicThread extends Thread {
    private JPanel imagePanel;
    private Controller controller;
    private BufferedImage photoMosaic;
    private JFrame frame;

    private boolean shouldUpdate;

    public PhotoMosaicThread(JFrame frame, JPanel imagePanel, Controller controller, BufferedImage photoMosaic) {
        this.frame = frame;
        this.imagePanel = imagePanel;
        this.controller = controller;
        this.photoMosaic = photoMosaic;
    }

    public void run() {
        JLabel loading = new JLabel("Something great is coming your way!");
        imagePanel.add(loading, BorderLayout.CENTER);
        frame.repaint();
        photoMosaic = controller.getPhotoMosaic();
        JLabel pictureLabel = new JLabel(new ImageIcon(photoMosaic));
        imagePanel.remove(loading);
        imagePanel.add(pictureLabel, BorderLayout.CENTER);
        frame.repaint();
    }

    public void setImagePanel(JPanel imagePanel) {
        this.imagePanel = imagePanel;
    }

    public void setShouldUpdate(boolean shouldUpdate) {
        this.shouldUpdate = shouldUpdate;
    }
}
