package core.image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Image {
    private BufferedImage
        img;

    public Image(int width, int height, int type) {
        img = new BufferedImage(width,height, type);
    }
    public Image(BufferedImage img) {
        this.img = img;
    }
    public BufferedImage loadImage(String path){
        try {
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }
    public BufferedImage getBufferedImage() {
        return img;
    }
    public Image copyImage(int srcX, int srcY, int srcWidth, int srcHeight) {
        return new Image(img.getSubimage(srcX, srcY, srcWidth, srcHeight));
    }
}
