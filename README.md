# Photo Mosaic Generator
The idea and algorithm for this project came from 
https://robertheaton.com/2018/11/03/programming-project-4-photomosaics/.

This project creates a photo mosaic, using the images
that the user chooses. The user must pick a background
image, and at least 100 tiles. They can also optionally 
choose how opaque they would like the tiles to be.

The user interface looks like this:
![User Interface](Images/ReadmeImages/PhotoMosaicGUI.png)

Once the user chooses the background image and tiles
they want to use, they can press the button to 
generate the photo mosaic.

The user interface will then display the following:
![Photo Mosaic Loading](Images/ReadmeImages/PhotoMosaicLoading.png)

Finally, when the photo mosaic is complete, it
displays in the user interface like this:
![Photo Mosaic Displaying](Images/ReadmeImages/PhotoMosaicInGUI.png)

The user can choose to save the photo mosaic if they
would like.

The above image has an opacity of 10. The same image
with an opacity of 7 looks like:
![Photo Mosaic Opacity 7](Images/ReadmeImages/PhotoMosaicOpacity7.png)

This is the original image (for reference):
![Background Picture](Images/ReadmeImages/BackgroundImage.jpg)

Another example of a photo mosaic generated by this
application is:
![Photo Mosaic Example 2](Images/ReadmeImages/PhotoMosaic2.png)

For convenience of being able to test out this application,
I have included a folder [here](Images/PhotoMosaicPics) that
has the background image I used, and the folder of
100 tiles.

### Note:
For users running the executable of this 
application on MacOS, there may be security
 limitations regarding the local files that can be accessed
from the application. If this issue is encountered,
it can be circumvented by running the jar as root.
(See [this article](https://stackoverflow.com/questions/18176228/run-jar-with-root-privileges-on-mac-os-x-by-one-click)
for ways to do that.)