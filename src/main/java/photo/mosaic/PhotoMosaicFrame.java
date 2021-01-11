package photo.mosaic;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class PhotoMosaicFrame extends JFrame {
    private JPanel errorPanel = new JPanel();
    private final PhotoMosaicFileChooser fileChooser;
    private Controller controller;
//    private PhotoMosaicThread thread;
    private JTextField backgroundImagePath;
    private JTextField tilesPath;
    private BufferedImage photoMosaic;
    private JLabel pictureLabel;

    public PhotoMosaicFrame(PhotoMosaicFileChooser fileChooser, Controller controller) {
        super();

        this.fileChooser = fileChooser;
        this.controller = controller;
//        this.thread = thread;

        setSize(1000, 800);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Photo Mosaic Creator");
        setLayout(new BorderLayout());

        JPanel filesPanel = new JPanel();
        setUpFilesPanel(filesPanel);
        add(filesPanel, BorderLayout.NORTH);

        fileChooser.setUIElements(errorPanel, backgroundImagePath, tilesPath);
        controller.setErrorPanel(errorPanel);
        add(errorPanel);

        pictureLabel = new JLabel();
        add(pictureLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        setUpButtonPanel(buttonPanel);
        add(buttonPanel, BorderLayout.SOUTH);

//        thread.setImagePanel(imagePanel);
    }

    private void setUpFilesPanel(JPanel filesPanel) {
        filesPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        JLabel backgroundImageLabel = new JLabel("Please select a background image: ");
        constraints.gridx = 0;
        constraints.gridy = 0;
        filesPanel.add(backgroundImageLabel, constraints);

        backgroundImagePath = new JTextField();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 10;
        filesPanel.add(backgroundImagePath, constraints);

        JButton browseBackgroundImage = new JButton("Browse");
        browseBackgroundImage.addActionListener(actionEvent -> fileChooser.chooseFile());
        constraints.gridx = 10;
        constraints.gridy = 1;
        filesPanel.add(browseBackgroundImage, constraints);

        JLabel tilesLabel = new JLabel("Please select a folder with the tiles you would like (100 minimum): ");
        constraints.gridx = 0;
        constraints.gridy = 2;
        filesPanel.add(tilesLabel, constraints);

        tilesPath = new JTextField();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 10;
        filesPanel.add(tilesPath, constraints);

        JButton browseTiles = new JButton("Browse");
        browseTiles.addActionListener(actionEvent -> fileChooser.chooseFolder());
        constraints.gridx = 10;
        constraints.gridy = 3;
        filesPanel.add(browseTiles, constraints);

        JButton generateMosaic = new JButton("Generate Photo Mosaic");
        generateMosaic.addActionListener(actionEvent -> generateNewMosaic());
        constraints.gridx = 0;
        constraints.gridy = 4;
        filesPanel.add(generateMosaic, constraints);
    }

    private void setUpButtonPanel(JPanel buttonPanel) {
        JButton save = new JButton("Save");
        save.addActionListener(actionEvent -> fileChooser.save(photoMosaic));
        buttonPanel.add(save);
    }

    private void generateNewMosaic() {
        photoMosaic = null;
        if (backgroundImagePath.getText().equals("") || tilesPath.getText().equals("")) {
            JOptionPane.showMessageDialog(errorPanel,
                    "Please choose a background image and tiles.");
        }
        else if (!fileChooser.pathsAreValid()) {
            JOptionPane.showMessageDialog(errorPanel,
                    "Please choose valid files for the background image and tiles.");
        }
        else {
            displayInImagePanel();
        }
    }

    private void displayInImagePanel() {
//        PhotoMosaicThread thread = new PhotoMosaicThread(this, imagePanel, controller, photoMosaic);
//        thread.start();
//        thread.setShouldUpdate(true);
        JLabel loading = new JLabel("Something great is coming your way!");
        photoMosaic = controller.getPhotoMosaic();
        pictureLabel.setIcon(new ImageIcon(photoMosaic));
//        thread.setShouldUpdate(false);
    }
}
