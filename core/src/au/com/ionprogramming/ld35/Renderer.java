package au.com.ionprogramming.ld35;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;

public class Renderer {


    public Renderer(Physics physics, Lighting lighting){
    }

    public void render(World world, SpriteBatch b, Logic logic) {
        logic.getPlayer().render(b);
    }
}
