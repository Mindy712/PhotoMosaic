package photo.mosaic;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.CountDownLatch;

public class Frame extends JFrame {
    private JPanel errorPanel = new JPanel();
    private final FileChooser fileChooser;
    private Controller controller;
    private JTextField backgroundImagePath;
    private JTextField tilesPath;
    private BufferedImage photoMosaic;
    private JPanel imagePanel;

    public Frame(FileChooser fileChooser, Controller controller) {
        super();

        this.fileChooser = fileChooser;
        this.controller = controller;

        setSize(1000, 800);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Photo Mosaic Creator");
        setLayout(new BorderLayout());

        JPanel filesPanel = new JPanel();
        setUpFilesPanel(filesPanel);
        add(filesPanel, BorderLayout.NORTH);

        fileChooser.setErrorPanel(errorPanel);
        add(errorPanel);

        imagePanel = new JPanel();
        imagePanel.setLayout(new BorderLayout());
        add(imagePanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        setUpButtonPanel(buttonPanel);
        add(buttonPanel, BorderLayout.SOUTH);
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
        browseBackgroundImage.addActionListener(actionEvent -> fileChooser.chooseFile(backgroundImagePath));
        constraints.gridx = 10;
        constraints.gridy = 1;
        filesPanel.add(browseBackgroundImage, constraints);

        JLabel tilesLabel = new JLabel("Please select a folder with the tiles you would like (1000 minimum): ");
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
        browseTiles.addActionListener(actionEvent -> fileChooser.chooseFolder(tilesPath));
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
        buttonPanel.setLayout(new GridLayout());
        JButton regenerate = new JButton("Regenerate");
        regenerate.addActionListener(actionEvent -> regenerate());
        buttonPanel.add(regenerate);
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

    private void regenerate() {
        if (photoMosaic == null) {
            JOptionPane.showMessageDialog(errorPanel,
                    "First generate a photo mosaic.");
        }
        else {
            displayInImagePanel();
        }
    }

    private void displayInImagePanel() {
        JLabel loading = new JLabel("Something great is coming your way!");
        imagePanel.add(loading, BorderLayout.CENTER);
        repaint();
        photoMosaic = controller.getPhotoMosaic();
        JLabel pictureLabel = new JLabel(new ImageIcon(photoMosaic));
        imagePanel.remove(loading);
        imagePanel.add(pictureLabel, BorderLayout.CENTER);
        repaint();
    }
}
