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


    @Override
    public void create () {

        Images.loadImages();

        physics = new Physics();
        lighting = new Lighting(physics);
        renderer = new Renderer(physics, lighting);
        sound = new SoundHandler();
//        sound.play("sounds/song1.mp3", true);

        batch = new SpriteBatch();


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
            renderer.render(physics.getWorld());
			physics.render(renderer.getCam());
            lighting.render(renderer.getCam());
            physics.doPhysicsStep(Gdx.graphics.getDeltaTime());
        }

    }

    @Override
    public void resize(int width, int height){

        batch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);

        if(width < 640){
            Gdx.graphics.setWindowedMode(640, Gdx.graphics.getHeight());
        }
        if(height < 480){
            Gdx.graphics.setWindowedMode(Gdx.graphics.getWidth(), 480);
        }

        renderer.setCamBounds(width, height);
    }

}
