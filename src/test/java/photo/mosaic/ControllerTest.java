package photo.mosaic;

import org.junit.Test;

import java.awt.image.BufferedImage;
import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ControllerTest {
    @Test
    public void getPhotoMosaic() throws IOException {
        //given
        PhotoMosaicFileChooser fileChooser = mock(PhotoMosaicFileChooser.class);
        Controller controller = new Controller(fileChooser);
        doReturn("PhotoMosaicPics/car-race-438467_1280.jpg").when(fileChooser).getBackgroundImagePath();
        doReturn("PhotoMosaicPics/Tiles").when(fileChooser).getTilesPath();
        doReturn(100).when(fileChooser).getNumTiles();

        //when
        BufferedImage photoMosaic = controller.getPhotoMosaic();

        //then
        assertNotNull(photoMosaic);
    }
}