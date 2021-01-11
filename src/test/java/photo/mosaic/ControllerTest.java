package photo.mosaic;

import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ControllerTest {
    @Test
    public void getPhotoMosaic() throws IOException {
        //given
        PhotoMosaicFileChooser fileChooser = mock(PhotoMosaicFileChooser.class);
        Controller controller = new Controller(fileChooser);
        doReturn("Images/PhotoMosaicPics/BackgroundImage.jpg").when(fileChooser).getBackgroundImagePath();
        doReturn("Images/PhotoMosaicPics/Tiles").when(fileChooser).getTilesPath();
        doReturn(100).when(fileChooser).getNumTiles();

        //when
        BufferedImage photoMosaic = controller.getPhotoMosaic();

        //then
        assertNotNull(photoMosaic);
        ImageIO.write(photoMosaic, "png", new File("Images/PhotoMosaicPics/PhotoMosaicTest.png"));
    }
}