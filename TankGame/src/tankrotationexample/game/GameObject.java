package tankrotationexample.game;

import tankrotationexample.Resources;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GameObject {

    protected float x, y;
    protected BufferedImage img;
    protected Rectangle hitbox;
    protected boolean hasCollided = false;

    public GameObject(float x, float y, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.img = img;
        this.hitbox = new Rectangle((int) x, (int) y, this.img.getWidth(), this.img.getHeight());
    }


    public Rectangle getHitBox() {
        return this.hitbox.getBounds();
    }



    public static GameObject gameObjectFactory(String type, float x, float y){

        return switch (type){
            case "2" -> new Wall(x, y, Resources.getSprite("breakable"), 2, 1);
            case "3" -> new Wall(x, y, Resources.getSprite("unbreakable1"), 2, 2);
            case "4" -> new Wall(x, y, Resources.getSprite("unbreakable2"), 2, 2);
            case "5" -> new Health(x, y, Resources.getSprite("health"));
            case "6" -> new MiniNukes(x, y, Resources.getSprite("mini-nuke-shells"));
            case "7" -> new MonsterTruck(x, y, Resources.getSprite("monster-tank"));
            case "8" -> new Plasma(x, y, Resources.getSprite("plasma-shells"));
            case "9" -> new Shotgun(x, y, Resources.getSprite("shotgun"));
            case "10" -> new Speed(x, y, Resources.getSprite("speed"));
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };

    }

    public abstract void drawImage(Graphics g);

    public abstract void collidedWith(GameObject ob2, GameWorld gameWorld);
}
