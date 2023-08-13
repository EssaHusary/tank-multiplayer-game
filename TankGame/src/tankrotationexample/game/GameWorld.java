/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tankrotationexample.game;


import tankrotationexample.GameConstants;
import tankrotationexample.Launcher;
import tankrotationexample.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * @author anthony-pc
 */
public class GameWorld extends JPanel implements Runnable {

    private BufferedImage world;

    private Tank t1;

    private Tank t2;
    private Launcher lf;
    private long tick = 0;

    private List<GameObject> gameObjects = new ArrayList<>(500);

    private List<Animation> anims = new ArrayList<>(20);

    private Sound bgMusic;

    /**
     * 
     * @param lf
     */
    public GameWorld(Launcher lf) {
        this.lf = lf;
    }

    public GameWorld() {}

    @Override
    public void run() {
        try {
            //this.resetGame();
            bgMusic = Resources.getSound("backgroundmusic");
            bgMusic.setVolume(0.5f);
            bgMusic.setLooping();
            bgMusic.playSound();
            while (true) {
                this.tick++;
                this.t1.update(this); // update tank
                this.t2.update(this); // update tank
//                if (t1.getHitBox().intersects(this.t2.getHitBox())) {
//                    System.out.println("tanks collided");
////                    this.t1.setX(this.t1.getX() - 100);
////                    this.t1.setY(this.t1.getX() + 100);
////                    this.t2.setX(this.t1.getX() + 100);
////                    this.t2.setY(this.t1.getX() - 100);
//                    this.t1.setX(750);
//                    this.t1.setY(675);
//                    this.t2.setX(1050);
//                    this.t2.setY(675);
//                }

                this.checkCollisions();
                this.anims.forEach(a -> a.update());
                this.gameObjects.removeIf(g -> g.hasCollided);
                this.anims.removeIf(a -> !a.isRunning);
                this.repaint();   // redraw game
                
                /*
                 * Sleep for 1000/144 ms (~6.9ms). This is done to have our 
                 * loop run at a fixed rate per/sec. 
                */
                Thread.sleep(1000 / 144);

                /*
                 * simulate an end game event
                 * we will do this with by ending the game when ~8 seconds has passed.
                 * This will need to be changed since the will always close after 8 seconds
                 */
//                if (this.tick >= 144 * 8) {
//                    this.lf.setFrame("end");
//                    return;
//                }

            }
        } catch (InterruptedException ignored) {
            System.out.println(ignored);
        }
    }

    /**
     * Reset game to its initial state.
     */
    public void resetGame() {
        this.tick = 0;
        this.t1.setX(300);
        this.t1.setY(300);
    }


    public void checkCollisions(){


        for (int i = 0; i < this.gameObjects.size(); i++) {
            GameObject ob1 = this.gameObjects.get(i);
            if (ob1 instanceof Wall || ob1 instanceof PowerUp) continue;
            for (int j = 0; j < this.gameObjects.size(); j++) {
                if (i == j) continue;
                GameObject ob2 = this.gameObjects.get(j);

                //if (ob2 instanceof Bullet) continue;
                if(ob1.getHitBox().intersects(ob2.getHitBox())){
                    //getGameObject(ob2);
                    ob1.collidedWith(ob2, this);
                }
            }
        }


//        for (int i = 0; i < this.gameObjects.size(); i++) {
//            GameObject ob1 = this.gameObjects.get(i);
//            if (ob1 instanceof Wall || ob1 instanceof PowerUp) continue;
//            for (int j = 0; j < this.gameObjects.size(); j++) {
//                if (i == j) continue;
//                GameObject ob2 = this.gameObjects.get(j);
//
//                //if (ob2 instanceof Bullet) continue;
//                if(ob1.getHitBox().intersects(ob2.getHitBox())){
//                    ob1.collidedWith(ob2);
//                    if (ob2 instanceof PowerUp && !(ob1 instanceof Shell)) {
//                        ob2.hasCollided = true;
//                        Resources.getSound("powerup").playSound();
//
//                    } else if (ob2 instanceof Wall && !(ob1 instanceof Tank)) {
//
//                        if (ob1 instanceof Shell) {
//
//
//                            Resources.getSound("explosion").playSound();
//                            Bullet b = (Bullet) ob1;
//                            this.anims.add(new Animation(b.x, b.y, Resources.getAnimation("bullet")));
//                            ob1.hasCollided = true;
//                        }
//                        if (((Wall) ob2).getType() == 1){
//                            ((Wall) ob2).changeState();
//                            if (((Wall) ob2).getState() == 0){
//                                gameObjects.remove(ob2);
//                            }
//                        }
//
//
//                    } else if (ob2 instanceof Shell && !(ob1 instanceof Shell)){
//                        if (ob1 instanceof Tank){
//
//                            if ( ((Tank)ob1).getId() == ((Shell)ob2).getTankId() ) {
//
//                                continue;
//                            }
//
//                        }
//
//                        ob2.hasCollided = true;
//                        Bullet b = (Bullet) ob2;
//                        this.anims.add(new Animation(b.x, b.y, Resources.getAnimation("bullet")));
//                        Resources.getSound("explosion").playSound();
//                    }
//                }
//            }
//        }



    }

    /**
     * Load all resources for Tank Wars Game. Set all Game Objects to their
     * initial state as well.
     */
    public void InitializeGame() {
        this.world = new BufferedImage(GameConstants.WORLD_WIDTH,
                GameConstants.WORLD_HEIGHT,
                BufferedImage.TYPE_INT_RGB);


        try(BufferedReader mapReader = new BufferedReader((new InputStreamReader(GameWorld.class.getClassLoader().getResourceAsStream("map/map.csv"))))){


            for (int i = 0; mapReader.ready(); i++){
                String[] gameObjects = mapReader.readLine().split(",");
                for (int j = 0; j < gameObjects.length; j++){
                    String objectType = gameObjects[j];
                    if(Objects.equals("0", objectType)) continue;
                    this.gameObjects.add(GameObject.gameObjectFactory(objectType, j*30, i*30));

                }
            }

        } catch (IOException e) {

            e.printStackTrace();

        }


        t1 = new Tank(65, 45, 0, 0, (short) 90, 1, Resources.getSprite("tank1"));
        TankControl tc1 = new TankControl(t1, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);
        this.lf.getJf().addKeyListener(tc1);

        t2 = new Tank(1807, 1350, 0, 0, (short) 270, 2, Resources.getSprite("tank2"));
        TankControl tc2 = new TankControl(t2, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER);
        this.lf.getJf().addKeyListener(tc2);

        this.gameObjects.add(t1);
        this.gameObjects.add(t2);

    }


    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Graphics2D buffer = world.createGraphics();
        drawFloor(buffer);
        //this.gameObjects.forEach(gObj -> gObj.drawImage(buffer));   // same as 'for' loop below
        for (int i = 0; i < gameObjects.size(); i++){
            this.gameObjects.get(i).drawImage(buffer);
        }
        this.t1.drawImage(buffer);
        this.t2.drawImage(buffer);

        //this.anims.forEach(a -> a.drawImage(buffer));
        for (int i = 0; i < anims.size(); i++) {
            this.anims.get(i).drawImage(buffer);
        }
        drawSplitscreens(g2, world);
        drawMiniMap(g2, world);
    }

    void drawFloor(Graphics2D buffer){
        for (int i = 0; i < GameConstants.WORLD_WIDTH; i += 256){
            for (int j = 0; j < GameConstants.WORLD_HEIGHT; j += 256){
                buffer.drawImage(Resources.getSprite("floor"), i, j, null);
            }
        }
    }


    void drawMiniMap(Graphics2D g, BufferedImage world){
        BufferedImage mm = world.getSubimage(0, 0, GameConstants.WORLD_WIDTH, GameConstants.WORLD_HEIGHT);
        AffineTransform at = new AffineTransform();
        at.translate(GameConstants.GAME_SCREEN_WIDTH/2f - (GameConstants.WORLD_WIDTH*.2f)/2f,
                GameConstants.GAME_SCREEN_HEIGHT - (GameConstants.WORLD_HEIGHT*.2f));
        at.scale(.2, .2);
        g.drawImage(mm, at, null);
    }


    void drawSplitscreens(Graphics2D g, BufferedImage world){
        BufferedImage lh = world.getSubimage(t1.getScreenX(), t1.getScreenY(), GameConstants.GAME_SCREEN_WIDTH/2,
                GameConstants.GAME_SCREEN_HEIGHT);
        BufferedImage rh = world.getSubimage(t2.getScreenX(), t2.getScreenY(), GameConstants.GAME_SCREEN_WIDTH/2,
                GameConstants.GAME_SCREEN_HEIGHT);

        g.drawImage(lh, 0, 0, null);
        g.drawImage(rh, GameConstants.GAME_SCREEN_WIDTH/2, 0, null);
    }


    public void addGameObject(GameObject b) { this.gameObjects.add(b); }

    public void removeGameObject(GameObject obj) { this.gameObjects.remove(obj); }

    public void addAnimation(Animation anim) {
        this.anims.add(anim);
    }

    //public GameObject getGameObject(GameObject obj) { return obj; }

    //public GameObject returnGameObject() { return getGameObject(); }
}
