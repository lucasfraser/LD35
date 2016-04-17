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

    public Terrain(World world, int level){
        shapeRenderer = new ShapeRenderer();
        noise = new SimplexNoise(6, 0.55f, 0.001f*level);
        terrainPoints = noise.generateLevelPoints(100, Gdx.graphics.getWidth(), 400, 1);
        FixtureDef fixtureDefl = new FixtureDef();
        fixtureDefl.density =10f;
        fixtureDefl.friction = 100f;
        fixtureDefl.restitution = 0.01f;

        for(int x = 0; x < 99; x++){
            PolygonShape p = new PolygonShape();
            p.set(new float[]{terrainPoints[x*2], terrainPoints[x*2 + 1], terrainPoints[x*2 + 2], terrainPoints[x*2 + 3],
            terrainPoints[x*2 + 2], 0, terrainPoints[x*2], 0});

            BodyDef bodyDef = new BodyDef();
            bodyDef.position.set(0, 0);
            bodyDef.type = BodyDef.BodyType.StaticBody;
            Body body = world.createBody(bodyDef);

            fixtureDefl.shape = p;

            body.createFixture(fixtureDefl);


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
