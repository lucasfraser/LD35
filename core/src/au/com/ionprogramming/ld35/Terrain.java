package au.com.ionprogramming.ld35;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by Sam on 17/04/2016.
 */
public class Terrain {

    private ShapeRenderer shapeRenderer;
    private SimplexNoise noise;
    private float[] terrainPoints;

    public Terrain(World world){
        shapeRenderer = new ShapeRenderer();
        noise = new SimplexNoise(5, 0.5f, 0.01f);
        terrainPoints = noise.generateLevelPoints(100, Gdx.graphics.getWidth(), 400);
        for(int x = 0; x < 99; x++){
            PolygonShape p = new PolygonShape();
            p.set(new float[]{terrainPoints[x*2], terrainPoints[x*2 + 1], terrainPoints[x*2 + 2], terrainPoints[x*2 + 3],
            terrainPoints[x*2 + 2], 0, terrainPoints[x*2], 0});

            BodyDef bodyDef = new BodyDef();
            bodyDef.position.set(0, 0);
            bodyDef.type = BodyDef.BodyType.StaticBody;
            Body body = world.createBody(bodyDef);
            body.createFixture(p, 0.0f);

            p.dispose();
        }
    }


    public void render(){
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(0.5f, 1f, 0.5f, 1f);
        shapeRenderer.polygon(terrainPoints);
        shapeRenderer.end();
    }
}
