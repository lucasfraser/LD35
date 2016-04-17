package au.com.ionprogramming.ld35;

import com.badlogic.gdx.physics.box2d.World;


public class Logic {

    public Player getPlayer() {
        return player;
    }

    private Player player;

    public Logic(World world, Lighting lighting){
        player = new Player(true, 10, 10, 25, 25, world, lighting, false, Images.IPL, false);

    }



    public void update(World world){

        System.out.println(player.getLoc());
        player.update();
    }

}
