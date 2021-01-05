package photo.mosaic;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

public class FileChooser {
    private final JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView());
    private final int minNumTiles = 100;
    private JPanel errorPanel;

    public FileChooser(JPanel errorPanel) {
        this.errorPanel = errorPanel;
    }

    public void chooseFile(JTextField textField) {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png", "gif", "jpeg");
        fileChooser.setFileFilter(filter);
        int file = fileChooser.showOpenDialog(null);
        if (file == JFileChooser.APPROVE_OPTION)

        {
            // set the label to the path of the selected file
            textField.setText(fileChooser.getSelectedFile().getAbsolutePath());
        }
    }

    public void chooseFolder(JTextField textField) {
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int folder = fileChooser.showOpenDialog(null);
        if (folder == JFileChooser.APPROVE_OPTION && fileChooser.getSelectedFile().list().length >= minNumTiles)
        {
            // set the label to the path of the selected file
            textField.setText(fileChooser.getSelectedFile().getAbsolutePath());
        }
        else
        {
            JOptionPane.showMessageDialog(errorPanel, "Please select a folder with at least 100 images.");
        }
    }
}
