package au.com.ionprogramming.ld35;

import com.badlogic.gdx.physics.box2d.World;


public class Logic {

    public Player getPlayer() {
        return player;
    }

    private Player player;

    public Logic(World world, Lighting lighting){
        player = new Player(true, 600, 400, 64, 64, world, lighting, false, Images.forklift, false);
        Renderer.entities.add(player);

    }



    public void update(World world){

    }

}
