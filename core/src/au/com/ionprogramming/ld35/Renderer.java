package au.com.ionprogramming.ld35;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

public class Renderer {

    public static ArrayList<Entity> entities = new ArrayList<Entity>();

    public Renderer(Physics physics, Lighting lighting){
    }

    public void render(World world, SpriteBatch b){

        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            for(int i = 0; i < entities.size(); i++){
                entities.get(i).update();
                entities.get(i).render(b);
            }

    }

}
