package photo.mosaic;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class PhotoMosaicFrame extends JFrame {
    private final JPanel errorPanel = new JPanel();
    private final PhotoMosaicFileChooser fileChooser;
    private final Controller controller;
    private JTextField backgroundImagePath;
    private JTextField tilesPath;
    private JButton generateMosaic;
    private final JLabel pictureLabel;
    private JComboBox<Integer> opacity;
    private BufferedImage photoMosaic;
    private final ImageIcon loadingIndicatorImage;

    public PhotoMosaicFrame(PhotoMosaicFileChooser fileChooser, Controller controller) {
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

        fileChooser.setUIElements(errorPanel, backgroundImagePath, tilesPath);
        controller.setErrorPanel(errorPanel);
        add(errorPanel);

        pictureLabel = new JLabel();
        pictureLabel.setHorizontalAlignment(JLabel.CENTER);
        add(pictureLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        setUpButtonPanel(buttonPanel);
        add(buttonPanel, BorderLayout.SOUTH);

        loadingIndicatorImage = new ImageIcon(getClass().getResource("/ajax-loader.gif"));
    }

    private void setUpFilesPanel(JPanel filesPanel) {
        filesPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        JLabel backgroundImageLabel = new JLabel("*Please select a background image: ");
        constraints.gridx = 0;
        constraints.gridy = 0;
        filesPanel.add(backgroundImageLabel, constraints);

        backgroundImagePath = new JTextField();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 1;
        filesPanel.add(backgroundImagePath, constraints);

        JButton browseBackgroundImage = new JButton("Browse");
        browseBackgroundImage.addActionListener(actionEvent -> fileChooser.chooseFile());
        constraints.gridx = 2;
        constraints.gridy = 1;
        filesPanel.add(browseBackgroundImage, constraints);

        JLabel tilesLabel = new JLabel("*Please select a folder with the tiles you would like (100 minimum): ");
        constraints.gridx = 0;
        constraints.gridy = 2;
        filesPanel.add(tilesLabel, constraints);

        tilesPath = new JTextField();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 3;
        filesPanel.add(tilesPath, constraints);

        JButton browseTiles = new JButton("Browse");
        browseTiles.addActionListener(actionEvent -> fileChooser.chooseFolder());
        constraints.gridx = 2;
        constraints.gridy = 3;
        filesPanel.add(browseTiles, constraints);

        JPanel opacityPanel = new JPanel();
        setUpOpacityPanel(constraints, opacityPanel);
        filesPanel.add(opacityPanel, constraints);

        generateMosaic = new JButton("Generate Photo Mosaic");
        generateMosaic.addActionListener(actionEvent -> generateNewMosaic());
        constraints.gridx = 0;
        constraints.gridy = 5;
        filesPanel.add(generateMosaic, constraints);
    }

    private void setUpOpacityPanel(GridBagConstraints constraints, JPanel opacityPanel) {
        opacityPanel.setLayout(new FlowLayout());
        JLabel opacityLabel = new JLabel("(Optional) Choose opacity of the tiles: " +
                "(1 is least opaque, 10 is most opaque)");
        opacityPanel.add(opacityLabel);

        opacity = new JComboBox<>();
        opacity.addItem(1);
        opacity.addItem(2);
        opacity.addItem(3);
        opacity.addItem(4);
        opacity.addItem(5);
        opacity.addItem(6);
        opacity.addItem(7);
        opacity.addItem(8);
        opacity.addItem(9);
        opacity.addItem(10);
        opacity.setSelectedIndex(9);
        opacityPanel.add(opacity);

        constraints.gridx = 0;
        constraints.gridy = 4;
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
            controller.setOpacity(opacity.getSelectedIndex() + 1);
            PhotoMosaicThread thread = new PhotoMosaicThread(controller,
                                                                this,
                                                                pictureLabel,
                                                                loadingIndicatorImage,
                                                                generateMosaic);
            thread.start();
        }
    }

    public void setPhotoMosaic(BufferedImage photoMosaic) {
        this.photoMosaic = photoMosaic;
    }
}
