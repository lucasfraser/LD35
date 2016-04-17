package au.com.ionprogramming.ld35;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.awt.*;

import java.io.IOException;


public class Game extends ApplicationAdapter {

//      try {
//			HighScore.addScore("In-Game Test", 99999);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

    private static Physics physics;
    private static Renderer renderer;
    private static Lighting lighting;
    private static SoundHandler sound;
    private static Logic logic;

    public static int level = 1;
    public static float levelPercent = 0;

    public static boolean INTRO = false;
    public static boolean TITLE_SCREEN = false;

    private SpriteBatch batch;
    private ShapeRenderer shape;

    private static Terrain terrain;

    @Override
    public void create () {

        Images.loadImages();

        batch = new SpriteBatch();
        shape = new ShapeRenderer();



//        sound.play("sounds/song1.mp3", true);
        initLevel();

    }

    public static void initLevel(){
        physics = new Physics();
        physics.getWorld().setContactListener(new DropBall());
        lighting = new Lighting(physics);
        logic = new Logic(physics.getWorld(), lighting);
        renderer = new Renderer(physics, lighting);
        sound = new SoundHandler();

        terrain = new Terrain(physics.getWorld(), level);

//        logic.getPlayer().body.setTransform(new Vector2(20, 500), 0);
//        logic.getPlayer().setForkAngle(0);
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

            Gdx.gl.glClearColor(0.1f, 0.1f, 0.4f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            batch.begin();
            batch.draw(Images.bg, 0, 0);
            batch.end();
            terrain.render();
            logic.update(physics.getWorld());

            batch.begin();
            renderer.render(physics.getWorld(), batch, logic);
            batch.end();

            shape.begin(ShapeRenderer.ShapeType.Filled);
            shape.setColor(com.badlogic.gdx.graphics.Color.RED);
            logic.getPlayer().renderTri(shape);
            shape.end();

            lighting.render(batch, logic);
//            physics.render(batch);

            physics.doPhysicsStep(Gdx.graphics.getDeltaTime());
        }
    }


    public static void lose(){
        try {
            HighScore.addScore(System.getProperty("user.name"), Game.level+Game.levelPercent);
        } catch (IOException e) {
            e.printStackTrace();
        }
        initLevel();
    }

}
