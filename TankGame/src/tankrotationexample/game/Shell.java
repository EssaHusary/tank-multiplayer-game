package tankrotationexample.game;

import tankrotationexample.GameConstants;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public abstract class Shell extends GameObject{

    int damage;
    float R = 6;
    float angle;

//    boolean hitBorder;

    private int idOfTank;

    public Shell(float x, float y, float angle, int idOfTank, BufferedImage img, int damage) {
        super(x, y, img);
        this.angle = angle;
        this.idOfTank = idOfTank;
        this.damage = damage;
    }

    public Shell(float x, float y, BufferedImage img) {
        super(x, y, img);
    }

    void update() {

        x += Math.round(R * Math.cos(Math.toRadians(angle)));
        y += Math.round(R * Math.sin(Math.toRadians(angle)));
        this.hitbox.setLocation((int) x, (int) y);   // bullet destroys power up


    }


    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
//        rotation.scale(5, 5);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);
        g2d.setColor(Color.GREEN);
        g2d.drawRect((int)x,(int)y,this.img.getWidth(), this.img.getHeight());

    }

    private boolean checkBorder() {


        if (x < 30) {
            this.hasCollided = true;
            //x = 30;
            return this.hasCollided;
        }
        if (x >= GameConstants.WORLD_WIDTH - 30) {
            hasCollided = true;
            //x = GameConstants.WORLD_WIDTH - 88;
            return this.hasCollided;
        }
        if (y < 30) {
            hasCollided = true;
            //y = 40;
            return this.hasCollided;
        }
        if (y >= GameConstants.WORLD_HEIGHT - 30) {
            hasCollided = true;
            //y = GameConstants.WORLD_HEIGHT - 80;
            return this.hasCollided;
        }

        return false;


    }


    public boolean borderIsHit(){
        return checkBorder();
    }


    public int getTankId() {
        return idOfTank;
    }

    public abstract Shell createNewShell(float x, float y, float angle);

}
