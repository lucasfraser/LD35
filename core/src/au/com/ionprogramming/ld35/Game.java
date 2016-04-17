package au.com.ionprogramming.ld35;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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

    public static boolean INTRO = true;
    public static boolean TITLE_SCREEN = false;

    private SpriteBatch batch;
    private SpriteBatch lightBatch;


    @Override
    public void create () {

        Images.loadImages();

        batch = new SpriteBatch();
        lightBatch = new SpriteBatch();

        physics = new Physics();
        lighting = new Lighting(physics);
        renderer = new Renderer(physics, lighting);
        sound = new SoundHandler();
//        sound.play("sounds/song1.mp3", true);



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



            batch.begin();
            renderer.render(physics.getWorld(), batch);
            batch.end();

            lighting.render(batch);
            physics.render(batch);

            physics.doPhysicsStep(Gdx.graphics.getDeltaTime());
        }

    }

}
