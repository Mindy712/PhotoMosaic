package photo.mosaic;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
    JPanel errorPanel = new JPanel();
    FileChooser fileChooser = new FileChooser(errorPanel);

    public Frame() {
        super();

        setSize(1000, 800);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Photo Mosaic Creator");
        setLayout(new BorderLayout());

        JPanel filesPanel = new JPanel();
        setUpFilesPanel(filesPanel);
        add(filesPanel, BorderLayout.NORTH);

        add(errorPanel);

    }

    private void setUpFilesPanel(JPanel filesPanel) {
        filesPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        JLabel backgroundImageLabel = new JLabel("Please select a background image: ");
        constraints.gridx = 0;
        constraints.gridy = 0;
        filesPanel.add(backgroundImageLabel, constraints);

        JTextField backgroundImagePath = new JTextField();
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

        JLabel tilesLabel = new JLabel("Please select a folder with the tiles you would like (100 minimum): ");
        constraints.gridx = 0;
        constraints.gridy = 2;
        filesPanel.add(tilesLabel, constraints);

        JTextField tilesPath = new JTextField();
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
    }
}
