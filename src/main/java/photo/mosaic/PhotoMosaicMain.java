package photo.mosaic;

public class PhotoMosaicMain {
    public static void main(String[] args) {
        FileChooser fileChooser = new FileChooser();
        Controller controller = new Controller(fileChooser);
        Frame frame = new Frame(fileChooser, controller);

        frame.setVisible(true);
    }
}
