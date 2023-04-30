import snipping_tool.ScreenCapture;
import snipping_tool.SnippingTool;

import javax.swing.UIManager;
import javax.imageio.ImageIO;

import java.awt.Color;
import java.io.File;

public class Main {
    public static void main(String[] args) throws Exception {

        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

        SnippingTool tool = new SnippingTool();
        tool.setSelectionColor(Color.CYAN);

        tool.showAndWait();

        ScreenCapture ss = tool.getScreenCapture();

        if(ss.isImageCaptured()) {
            ImageIO.write(ss.getImage(), "png", new File("image.png"));
        }

        /*
        snapped_tool.ScreenCapture s = new snapped_tool.ScreenCapture();
        s.captureImage();

        if(s.isImageCaptured()) {
            ImageIO.write(s.getImage(), "png", new File("src/image.png"));
        }
         */

    }
}



