package tankrotationexample.game;

import tankrotationexample.Resources;

import java.awt.image.BufferedImage;

public class Plasma extends Shell implements PowerUp{

    public Plasma(float x, float y, BufferedImage img) {
        super(x, y, img);
    }

    @Override
    public Shell createNewShell(float x, float y, float angle) {
        return new Plasma (x, y, angle, this.getTankId(), Resources.getSprite("plasma"), 15);
    }

    public Plasma(float x, float y, float angle, int idOfTank, BufferedImage img, int damage){
        super(x, y, angle, idOfTank, img, damage);

    }

    @Override
    public void collidedWith(GameObject ob2, GameWorld gameWorld) {
        if (ob2 instanceof Wall){

            Resources.getSound("explosionplasma").playSound();
            gameWorld.addAnimation(new Animation(this.x, this.y, Resources.getAnimation("plasma")));
            this.hasCollided = true;
            if (((Wall) ob2).getType() == 1){
                ((Wall) ob2).changeState();
                if (((Wall) ob2).getState() == 0){
                    gameWorld.removeGameObject(ob2);
                }
            }

        } else if (ob2 instanceof Tank && !(((Tank) ob2).getId() == this.getTankId())) {
            power((Tank) ob2,gameWorld);
            Resources.getSound("explosionplasma").playSound();
            gameWorld.addAnimation(new Animation(this.x, this.y, Resources.getAnimation("plasma")));
            this.hasCollided = true;
        }
    }

    @Override
    public void power(Tank t, GameWorld gw) {

        t.setHealth(t.getHealth() - this.damage);
        Resources.getSound("explosionplasma").playSound();
        gw.addAnimation(new Animation(this.x, this.y, Resources.getAnimation("plasma")));
        this.hasCollided = true;
//        Shell p = new Plasma(t.getX(), t.getY(), t.getAngle(), t.getId(), Resources.getSprite("plasma-shells"));
//        gw.addAnimation(new Animation(p.x, p.y, Resources.getAnimation("plasmamuzzle")));

    }


//    @Override
//    public void drawImage(Graphics g) {
//        Graphics2D g2d = (Graphics2D) g;
//        g.drawImage(this.img,(int)x,(int)y,null);
//        g2d.setColor(Color.YELLOW);
//        g2d.drawRect((int)x,(int)y,this.img.getWidth(), this.img.getHeight());
//    }

}
