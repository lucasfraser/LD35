package au.com.ionprogramming.ld35;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;

import java.util.ArrayList;

public class Renderer {

    public static ArrayList<Entity> entities = new ArrayList<Entity>();

    private float camWidth = 8;
    private float camHeight = 8;

    private float borderX = 1;
    private float borderY = 1;

    private OrthographicCamera cam;

    private SpriteBatch batch;
    private SpriteBatch bg;

    private SpriteBatch HUDbatch;
    private ShapeRenderer shapeRenderer;

    private Logic logic;

    private Rectangle scissors;
    private Rectangle clipBounds;

    public Renderer(Physics physics, Lighting lighting){

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        cam = new OrthographicCamera(camWidth*(w/h), camHeight);
        cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
        cam.update();

        batch = new SpriteBatch();
        bg = new SpriteBatch();
        HUDbatch  = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        logic = new Logic(physics.getWorld(), lighting);

        scissors = new Rectangle();
    }

    public void render(World world){

        setCamPos(logic.getPlayer());
        cam.update();

        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.setProjectionMatrix(cam.combined);
        batch.setProjectionMatrix(cam.combined);


        batch.begin();

            clipBounds = new Rectangle(cam.position.x - cam.viewportWidth/2, cam.position.y - cam.viewportHeight/2, cam.viewportWidth, cam.viewportHeight);

            ScissorStack.calculateScissors(cam, batch.getTransformMatrix(), clipBounds, scissors);
            ScissorStack.pushScissors(scissors);

            for(int i = 0; i < entities.size(); i++){
                entities.get(i).update();
                entities.get(i).render(shapeRenderer, batch);
            }

            ScissorStack.popScissors();

        batch.end();

        logic.update(world);

    }

    public void setCamPos(Entity focus){
        float xOffset = focus.getBody().getPosition().x - cam.position.x;
        float yOffset = focus.getBody().getPosition().y - cam.position.y;
        if(xOffset < 0){
            xOffset += borderX;
            if(xOffset > 0){
                xOffset = 0;
            }
        }
        else{
            xOffset -= borderX;
            if(xOffset < 0){
                xOffset = 0;
            }
        }
        if(yOffset < 0){
            yOffset += borderY;
            if(yOffset > 0){
                yOffset = 0;
            }
        }
        else{
            yOffset -= borderY;
            if(yOffset < 0){
                yOffset = 0;
            }
        }
        cam.translate(xOffset, yOffset);
    }

    public void setCamBounds(float width, float height){
        cam.viewportHeight = camHeight;
        cam.viewportWidth = camWidth*(width/height);

        HUDbatch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
    }

    public OrthographicCamera getCam() {
        return cam;
    }

}
