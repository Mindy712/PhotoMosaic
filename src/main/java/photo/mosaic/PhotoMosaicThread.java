package photo.mosaic;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class PhotoMosaicThread extends Thread {
    private final Controller controller;
    private final PhotoMosaicFrame frame;
    private final JLabel pictureLabel;
    private final ImageIcon loadingIndicatorImage;
    private final JButton generateMosaic;

    public PhotoMosaicThread(Controller controller,
                             PhotoMosaicFrame frame,
                             JLabel pictureLabel,
                             ImageIcon loadingIndicatorImage,
                             JButton generateMosaic) {
        this.controller = controller;
        this.frame = frame;
        this.pictureLabel = pictureLabel;
        this.loadingIndicatorImage = loadingIndicatorImage;
        this.generateMosaic = generateMosaic;
    }

    public void run() {
        generateMosaic.setEnabled(false);
        pictureLabel.setIcon(loadingIndicatorImage);
        BufferedImage photoMosaic = controller.getPhotoMosaic();
        frame.setPhotoMosaic(photoMosaic);
        ImageIcon imageIcon = new ImageIcon(photoMosaic.getScaledInstance(950, 650, java.awt.Image.SCALE_SMOOTH));
        pictureLabel.setIcon(imageIcon);
        generateMosaic.setEnabled(true);
    }
}
