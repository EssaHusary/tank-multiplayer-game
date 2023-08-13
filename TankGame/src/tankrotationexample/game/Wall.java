package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Wall extends GameObject {

    private int state, type;  /* "state" indicates how far from being broken a wall is. "type" is the type of wall.
                                 type 1 = breakable, type 2 = unbreakable */

    public Wall(float x, float y, BufferedImage img, int state, int type) {
        super(x, y, img);
        this.state = state;
        this.type = type;
    }

    public int getType() {
        return this.type;
    }

    public int getState() {
        return this.state;
    }

    public void changeState(){
        this.state--;
    }

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
