package photo.mosaic;

public class PhotoMosaicMain {
    public static void main(String[] args) {
        PhotoMosaicFileChooser fileChooser = new PhotoMosaicFileChooser();
        Controller controller = new Controller(fileChooser);
        PhotoMosaicFrame frame = new PhotoMosaicFrame(fileChooser, controller);

        frame.setVisible(true);
    }
}
