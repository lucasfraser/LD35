package au.com.ionprogramming.ld35;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;

public class Player extends Entity {

    public Player(boolean moving, float x, float y, float width, float height, World world, Lighting lighting, boolean lockRotation, TextureRegion texture, boolean rounded){
        super(moving, x, y, width, height, world, lighting, texture, rounded);

    }
}
