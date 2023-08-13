package tankrotationexample.game;

import tankrotationexample.GameConstants;
import tankrotationexample.Resources;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author anthony-pc
 */
public class Tank extends GameObject{


    private GameObject currentShell;
    private boolean isPickedUp = false;
    private boolean defaultShell = true;
    private int tankId;
    private float screenX;
    private float screenY;
    private float vx;
    private float vy;
    private float angle;
    private long coolDown = 500;
    private long timeLastShot = 0;
    private int lives = 3;
    private int health = 100;
    private float R = 5;
    private float ROTATIONSPEED = 3.0f;


    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean shootPressed;


    List<Shell> ammo = new ArrayList<>(50);



    Tank(float x, float y, float vx, float vy, float angle, int tankId, BufferedImage img) {
        super(x, y, img);
        this.vx = vx;
        this.vy = vy;
        this.angle = angle;
        this.tankId = tankId;
    }

    void setX(float x){ this.x = x; }

    void setY(float y) { this. y = y;}

    void toggleUpPressed() {
        this.UpPressed = true;
    }

    void toggleDownPressed() {
        this.DownPressed = true;
    }

    void toggleRightPressed() {
        this.RightPressed = true;
    }

    void toggleLeftPressed() {
        this.LeftPressed = true;
    }

    public void toggleShootPressed() {
        this.shootPressed = true;
    }

    void unToggleUpPressed() {
        this.UpPressed = false;
    }

    void unToggleDownPressed() {
        this.DownPressed = false;
    }

    void unToggleRightPressed() {
        this.RightPressed = false;
    }

    void unToggleLeftPressed() {
        this.LeftPressed = false;
    }

    public void unToggleShootPressed() {
        this.shootPressed = false;
    }

    void update(GameWorld gw) {
        if (this.UpPressed) {
            this.moveForwards();
        }

        if (this.DownPressed) {
            this.moveBackwards();
        }

        if (this.LeftPressed) {
            this.rotateLeft();
        }

        if (this.RightPressed) {
            this.rotateRight();
        }

        if (this.shootPressed && (this.timeLastShot + coolDown) < System.currentTimeMillis()) {
            this.timeLastShot = System.currentTimeMillis();

            if (defaultShell){
                Bullet b = new Bullet(setBulletStartX(), setBulletStartY(), getAngle(), getId(), Resources.getSprite("bullet"), 5);
                gw.addGameObject(b);
                gw.addAnimation(new Animation(b.x, b.y, Resources.getAnimation("regularmuzzle")));
                this.ammo.add(b);
                Resources.getSound("bulletsound").playSound();
            } else if (!defaultShell) {

                setDefaultShell(this.currentShell, gw);

            }

        }

//        this.ammo.forEach(bullet -> this.ammo.removeIf(b -> b.update()) );


        this.ammo.forEach(shell -> shell.update());
        this.ammo.removeIf(shell -> shell.borderIsHit());

//        for (int i = 0; i < this.ammo.size(); i++) {
//            if (this.ammo.get(i).update()){
//                this.ammo.remove(b);
//            }
////            this.ammo.get(i).update();
////            this.ammo.removeIf(b -> b.update());
//        }


        centerScreen();

    }

    private int setBulletStartX() {
        float cx = 29f * (float) Math.cos(Math.toRadians(angle));
        return (int) x + this.img.getWidth() / 2 + (int) cx - 7;
    }

    private int setBulletStartY() {
        float cy = 29f * (float) Math.sin(Math.toRadians(angle));
        return (int) y + this.img.getHeight() / 2 + (int) cy - 7;
    }

    private void rotateLeft() {
        this.angle -= this.ROTATIONSPEED;
    }

    private void rotateRight() {
        this.angle += this.ROTATIONSPEED;
    }

    private void moveBackwards() {
        vx =  Math.round(R * Math.cos(Math.toRadians(angle)));
        vy =  Math.round(R * Math.sin(Math.toRadians(angle)));
        x -= vx;
        y -= vy;
        checkBorder();
        centerScreen();
        this.hitbox.setLocation((int) x, (int) y);
    }

    private void moveForwards() {
        vx = Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkBorder();
        centerScreen();
        this.hitbox.setLocation((int) x, (int) y);
    }


    private void checkBorder() {
        if (x < 30) {
            x = 30;
        }
        if (x >= GameConstants.WORLD_WIDTH - 88) {
            x = GameConstants.WORLD_WIDTH - 88;
        }
        if (y < 40) {
            y = 40;
        }
        if (y >= GameConstants.WORLD_HEIGHT - 80) {
            y = GameConstants.WORLD_HEIGHT - 80;
        }
    }


    private void centerScreen(){
        this.screenX = this.x - GameConstants.GAME_SCREEN_WIDTH / 4f;
        this.screenY = this.y - GameConstants.GAME_SCREEN_HEIGHT / 2f;

        if (this.screenX < 0) screenX = 0;
        if (this.screenY < 0) screenY = 0;

        if (this.screenX > GameConstants.WORLD_WIDTH - GameConstants.GAME_SCREEN_WIDTH / 2f){
            this.screenX = GameConstants.WORLD_WIDTH - GameConstants.GAME_SCREEN_WIDTH / 2f;
        }
        if (this.screenY > GameConstants.WORLD_HEIGHT - GameConstants.GAME_SCREEN_HEIGHT){
            this.screenY = GameConstants.WORLD_HEIGHT - GameConstants.GAME_SCREEN_HEIGHT;
        }
    }


    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle;
    }


    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);
        g2d.setColor(Color.RED);

        g2d.drawRect((int)x,(int)y,this.img.getWidth(), this.img.getHeight());
        //this.ammo.forEach(bullet -> bullet.drawImage(g));
        for (int i = 0; i < ammo.size(); i++){
            ammo.get(i).drawImage(g);
        }
        g2d.setColor(Color.GREEN);
        g2d.drawRect((int) x, (int) y - 20, 100, 15);
        if (this.health < 60 && this.health >= 30) {
            g2d.setColor(Color.YELLOW);
        } else if (this.health < 30) {
            g2d.setColor(Color.RED);
        }
        g2d.fillRect((int) x, (int) y - 20, this.health, 15);

        g2d.setColor(Color.GREEN);
        for (int i = 0; i < this.lives; i++) {
            g2d.drawOval((int) x + (i * 15), (int) y + 65, 10, 10);
            g2d.fillOval((int) x + (i * 15), (int) y + 65, 10, 10);
        }


    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public float getAngle() { return this.angle; }

    public int getScreenX(){
        return (int) screenX;
    }

    public int getScreenY(){
        return (int) screenY;
    }

    public int getId() {
        return tankId;
    }

    public int getHealth(){
        return this.health;
    }

    public void setHealth(int health){
        this.health = health;
    }


    @Override
    public void collidedWith(GameObject other, GameWorld gameWorld){

        if (other instanceof Bullet){
//            if (!(this.getId() == ((Bullet) other).getTankId())){
//                this.health = this.health - 5;
//            }
        } else if (other instanceof Wall) {
//            this.x = other.x - 10;
//            this.y = other.y - 10;
        } else if (other instanceof Tank) {

        } else if (other instanceof PowerUp) {

            if (other instanceof Shell){
                this.defaultShell = false;
                this.currentShell = other;
            }
//            if (other instanceof Shell && (this.getId() == ((Shell) other).getTankId())){
//                Resources.getSound("powerup").playSound();
//            }

            if (isPickedUp == false){

                other.hasCollided = true;
                isPickedUp = true;
                Resources.getSound("powerup").playSound();

            }
//            else if (isPickedUp) {
//
//                ((PowerUp) other).power(this, gameWorld);
//
//            }


//            other.hasCollided = true;
//            Resources.getSound("powerup").playSound();
//            ((PowerUp) other).power(this, gameWorld);

        }

    }


    public void setDefaultShell(GameObject obj, GameWorld gw){
        if (obj instanceof PowerUp && obj instanceof Shell){
            //obj = new Plasma (setBulletStartX(), setBulletStartY(), angle, getId(), Resources.getSprite("plasma"), 5);
            obj = ((Shell) obj).createNewShell(setBulletStartX(), setBulletStartY(), getAngle());
            gw.addGameObject(obj);
            gw.addAnimation(new Animation(setBulletStartX(), setBulletStartY(), Resources.getAnimation("plasmamuzzle")));
            this.ammo.add((Shell) obj);
            Resources.getSound("plasmasound").playSound();
        }

//        if (obj instanceof Plasma){
//            Plasma p = new Plasma(setBulletStartX(), setBulletStartY(), angle, getId(), Resources.getSprite("plasma"), 15);
//            gw.addGameObject(p);
//            gw.addAnimation(new Animation(setBulletStartX(), setBulletStartY(), Resources.getAnimation("plasmamuzzle")));
//            this.ammo.add(p);
//            Resources.getSound("plasmasound").playSound();
//        }


//        gw.addGameObject(obj);
//        gw.addAnimation(new Animation(setBulletStartX(), setBulletStartY(), Resources.getAnimation("plasmamuzzle")));
//        this.ammo.add((Shell) obj);
//        Resources.getSound("plasmasound").playSound();
    }

}

