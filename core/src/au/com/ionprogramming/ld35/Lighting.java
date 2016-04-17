package au.com.ionprogramming.ld35;

import box2dLight.DirectionalLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Lighting {

    public RayHandler getRayHandler() {
        return rayHandler;
    }

    private RayHandler rayHandler;

    public DirectionalLight getD() {
        return d;
    }

    private DirectionalLight d;

    private PointLight forkLight;

    public Lighting(Physics phys){

        rayHandler = new RayHandler(phys.getWorld());

        rayHandler.setAmbientLight(new Color(0.2f, 0.2f, 0.2f, 0.3f));


//
        addPointLight(10, 10, 30, new Color(1,0,0,1), true, phys.getWorld());
//
        addPointLight(40, 25, 30, new Color(0,1,0,1), true, phys.getWorld());
//
        addPointLight(20, 15, 30, new Color(0,0,1,1), true, phys.getWorld());

        forkLight = new PointLight(rayHandler, 256, Color.ORANGE, 50, 0, 0);
//
//        addPointLight(2, 45, 30, new Color(61, 0, 142, 255), true, phys.getWorld());
         d = new DirectionalLight(rayHandler, 512, new Color(1f, 0.2f, 0.6f, 0.3f), 300);


        rayHandler.setShadows(true);

    }

    int blinkTime = 0;
    int blinkOffset = 20;
    boolean on = true;
    public void render(SpriteBatch b, Logic l){
        blinkTime++;
        if(blinkTime > blinkOffset){
            on = !on;
            blinkTime = 0;
        }
        if(on){
            forkLight.setColor(Color.ORANGE);
        }
        else{
            forkLight.setColor(0, 0, 0, 0);
        }
        forkLight.setPosition(l.getPlayer().getLoc().x + 30*(float)Math.cos(l.getPlayer().getBody().getAngle()+Math.PI/2), l.getPlayer().getLoc().y + 30*(float)Math.sin(l.getPlayer().getBody().getAngle()+Math.PI/2));
        rayHandler.setCombinedMatrix(b.getProjectionMatrix().mul(b.getTransformMatrix()));
        rayHandler.updateAndRender();
    }

    public void addPointLight(float x, float y, float raduis, Color col, boolean isSolid, World w){

        if(isSolid){
            Body body;
            Vector2 size = new Vector2(0.5f, 0.5f);
            PolygonShape shape = new PolygonShape();
            shape.setAsBox(size.x, size.y);

            BodyDef bodyDef = new BodyDef();

            bodyDef.position.set(x , y);

            bodyDef.type = BodyDef.BodyType.StaticBody;

            body = w.createBody(bodyDef);

            body.createFixture(shape, 0.0f);

            shape.dispose();

        }

        new PointLight(rayHandler, 256, col, raduis, x, y);

    }


}
