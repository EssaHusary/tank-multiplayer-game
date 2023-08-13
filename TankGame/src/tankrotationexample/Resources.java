package tankrotationexample;

import tankrotationexample.game.GameWorld;
import tankrotationexample.game.Sound;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

public class Resources {

    private static Map<String, BufferedImage> sprites = new HashMap<>();
    private static Map<String, Sound> sounds = new HashMap<>();
    private static Map<String, List<BufferedImage>> animations = new HashMap<>();


    private static BufferedImage loadSprite(String path) throws IOException{
        return ImageIO.read(Objects.requireNonNull(GameWorld.class.getClassLoader().getResource(path)));
    }

    private static Sound loadSound(String path) throws UnsupportedAudioFileException, IOException,
            LineUnavailableException {

        AudioInputStream audioStream;
        Clip c;
        Sound s;
        audioStream = AudioSystem.getAudioInputStream(
                Resources.class.getClassLoader().getResource(path));
        c = AudioSystem.getClip();
        c.open(audioStream);
        s = new Sound(c);

        return s;
    }


    private static void initSprites(){

        try {

            Resources.sprites.put("tank1",            loadSprite("tank/tank1.png"));
            Resources.sprites.put("tank2",            loadSprite("tank/tank2.png"));
            Resources.sprites.put("bullet",           loadSprite("bullet/bullet.png"));
            Resources.sprites.put("mini-nuke",        loadSprite("bullet/mininuke.png"));
            Resources.sprites.put("plasma",           loadSprite("bullet/plasma.png"));
            Resources.sprites.put("shotgun-shells",   loadSprite("bullet/shotgunshells.png"));
            Resources.sprites.put("floor",            loadSprite("floor/grass.png"));
            Resources.sprites.put("breakable",        loadSprite("walls/breakable.png"));
            Resources.sprites.put("unbreakable1",     loadSprite("walls/unbreakable1.png"));
            Resources.sprites.put("unbreakable2",     loadSprite("walls/unbreakable2.png"));
            Resources.sprites.put("menu",             loadSprite("menu/title.png"));
            Resources.sprites.put("health",           loadSprite("powerups/health.png"));
            Resources.sprites.put("mini-nuke-shells", loadSprite("powerups/mininukes.png"));
            Resources.sprites.put("monster-tank",     loadSprite("powerups/monstertruck.png"));
            Resources.sprites.put("plasma-shells",    loadSprite("powerups/plasma.png"));
            Resources.sprites.put("shotgun",          loadSprite("powerups/shotgun.png"));
            Resources.sprites.put("speed",            loadSprite("powerups/speed.png"));

        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }


    private static void initSounds(){

        try {

            Resources.sounds.put("bulletsound",       loadSound("sounds/bullet.wav"));
            Resources.sounds.put("explosion",         loadSound("sounds/explosion.wav"));
            Resources.sounds.put("explosion2",        loadSound("sounds/explosion2.wav"));
            Resources.sounds.put("explosion3",        loadSound("sounds/explosion3.wav"));
            Resources.sounds.put("explosionplasma",   loadSound("sounds/explosionplasma.wav"));
            Resources.sounds.put("mininuke1",         loadSound("sounds/mininuke1.wav"));
            Resources.sounds.put("monstertruck",      loadSound("sounds/monstertruck.wav"));
            Resources.sounds.put("plasmasound",       loadSound("sounds/plasma.wav"));
            Resources.sounds.put("powerup",           loadSound("sounds/powerup.wav"));
            Resources.sounds.put("backgroundmusic",   loadSound("sounds/scaryambienceModified.wav"));
            Resources.sounds.put("shotgunsound",      loadSound("sounds/shotgun.wav"));
            Resources.sounds.put("endgamevictory",    loadSound("sounds/victorysound.wav"));


        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    private static void initAnimations(){

        try {
            String baseFileName = "animations/bullet/Explosion_%04d.png";
            List<BufferedImage> temp = new ArrayList<>();
            for (int i = 0; i < 8; i++) {
                String fName = String.format(baseFileName, i);
                temp.add(loadSprite(fName));
            }
            Resources.animations.put("bullet", temp);


            baseFileName = "animations/plasma/Explosion_%04d.png";
            temp = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                String fName = String.format(baseFileName, i);
                temp.add(loadSprite(fName));
            }
            Resources.animations.put("plasma", temp);


            baseFileName = "animations/nuke/Explosion_%04d.png";
            temp = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                String fName = String.format(baseFileName, i);
                temp.add(loadSprite(fName));
            }
            Resources.animations.put("nuke", temp);


            baseFileName = "animations/muzzleflash/Flash_%04d.png";
            temp = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                String fName = String.format(baseFileName, i);
                temp.add(loadSprite(fName));
            }
            Resources.animations.put("regularmuzzle", temp);


            baseFileName = "animations/muzzleplasma/Flash_%04d.png";
            temp = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                String fName = String.format(baseFileName, i);
                temp.add(loadSprite(fName));
            }
            Resources.animations.put("plasmamuzzle", temp);


            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e);
            System.exit(-3);
        }

    }

    public static void loadResources() {
        initSprites();
        initSounds();
        initAnimations();
    }

    public static BufferedImage getSprite(String key){
        return Resources.sprites.get(key);
    }


    public static Sound getSound(String key){
        return Resources.sounds.get(key);
    }

    public static List<BufferedImage> getAnimation(String key) {
        return Resources.animations.get(key);
    }


}
