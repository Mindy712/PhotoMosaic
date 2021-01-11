package photo.mosaic;

public class PhotoMosaicMain {
    public static void main(String[] args) {
        PhotoMosaicFileChooser fileChooser = new PhotoMosaicFileChooser();
        Controller controller = new Controller(fileChooser);
//        PhotoMosaicThread thread = new PhotoMosaicThread();
        PhotoMosaicFrame frame = new PhotoMosaicFrame(fileChooser, controller);
//        thread.start();

        frame.setVisible(true);
    }
}
