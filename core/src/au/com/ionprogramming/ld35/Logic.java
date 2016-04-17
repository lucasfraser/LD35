package au.com.ionprogramming.ld35;

import com.badlogic.gdx.physics.box2d.World;


public class Logic {

    public Forklift getPlayer() {
        return player;
    }

    private Forklift player;

    public Logic(World world, Lighting lighting){
        player = new Forklift(20, 500, 64, 64, world, lighting, Images.forkliftRegion);
    }



    public void update(World world){
        player.update();
    }

}
