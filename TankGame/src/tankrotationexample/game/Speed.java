package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Speed extends GameObject implements PowerUp{

    public Speed(float x, float y, BufferedImage img) {
        super(x, y, img);
    }

    @Override
    public void power(Tank t, GameWorld gw) {

    }

    @Override
    public void drawImage(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g.drawImage(this.img,(int)x,(int)y,null);
        g2d.setColor(Color.YELLOW);
        g2d.drawRect((int)x,(int)y,this.img.getWidth(), this.img.getHeight());
    }

    @Override
    public void collidedWith(GameObject ob2, GameWorld gameWorld) {

    }
}
