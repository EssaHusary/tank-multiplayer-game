package tankrotationexample.game;

import tankrotationexample.Resources;

import java.awt.image.BufferedImage;

public class Bullet extends Shell{

    public Bullet(float x, float y, float angle, int idOfTank, BufferedImage img, int damage) {
        super(x, y, angle, idOfTank, img, damage);
    }

    @Override
    public Shell createNewShell(float x, float y, float angle) {
        return null;
    }


    @Override
    public void collidedWith(GameObject other, GameWorld gameWorld) {
        if (other instanceof Wall){

            Resources.getSound("explosion").playSound();
            gameWorld.addAnimation(new Animation(this.x, this.y, Resources.getAnimation("bullet")));
            this.hasCollided = true;
            if (((Wall) other).getType() == 1){
                ((Wall) other).changeState();
                if (((Wall) other).getState() == 0){
                    gameWorld.removeGameObject(other);
                }
            }

        } else if (other instanceof Tank && !(((Tank) other).getId() == this.getTankId())) {
            ((Tank) other).setHealth(((Tank) other).getHealth() - 5);
            Resources.getSound("explosion").playSound();
            gameWorld.addAnimation(new Animation(this.x, this.y, Resources.getAnimation("bullet")));
            this.hasCollided = true;
        }
    }
}
