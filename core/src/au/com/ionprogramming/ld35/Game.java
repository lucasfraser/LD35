package au.com.ionprogramming.ld35;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;

import java.io.IOException;

public class Game extends ApplicationAdapter {

//      try {
//			HighScore.addScore("In-Game Test", 99999);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

    private Physics physics;
    private Renderer renderer;
    private Lighting lighting;
    private SoundHandler sound;
    private Logic logic;

    public static boolean INTRO = false;
    public static boolean TITLE_SCREEN = false;

    private SpriteBatch batch;

    private Terrain terrain;

    @Override
    public void create () {

        Images.loadImages();

        batch = new SpriteBatch();

        physics = new Physics();
        lighting = new Lighting(physics);
        logic = new Logic(physics.getWorld(), lighting);
        renderer = new Renderer(physics, lighting);
        sound = new SoundHandler();
//        sound.play("sounds/song1.mp3", true);

        terrain = new Terrain(physics.getWorld());
    }

    @Override
    public void render () {

        if(INTRO){
            batch.begin();
            IntroScreen.render(batch);
            batch.end();
        }
        else if(TITLE_SCREEN){
            batch.begin();
//            Menu.render(batch);
            batch.end();
        }
        else{

            Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            terrain.render();
            logic.update(physics.getWorld());

            batch.begin();
            renderer.render(physics.getWorld(), batch, logic);
            batch.end();

            lighting.render(batch, logic);
//            physics.render(batch);

            physics.doPhysicsStep(Gdx.graphics.getDeltaTime());
        }

    }

}
