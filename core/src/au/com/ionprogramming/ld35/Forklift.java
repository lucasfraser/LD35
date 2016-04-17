package au.com.ionprogramming.ld35;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;

import java.io.IOException;


public class Forklift {

	protected Vector2 loc = new Vector2(0, 0);
	protected Vector2 size;
    protected Body body;
    protected Body bodyFront;
    protected Body forkBody;
    protected Body bodyBack;
    protected Body triangle;
    protected TextureRegion tex;
    protected RevoluteJointDef revFront;

    protected float forkAngle;


    public Forklift(float x, float y, float width, float height, World world, Lighting lighting, TextureRegion texture){

        forkAngle = 0;
		tex = texture;

        loc = new Vector2(x, y);

		size = new Vector2(width, height);

		PolygonShape box = new PolygonShape();
		box.setAsBox(size.x/2, size.y/2 - 6);
//		box.set(new float[]{0 - size.x/2, size.y / 6- size.y/2,   0 - size.x/2, -20/*size.y - size.y/2*/,    size.x - size.x/2, -20/*size.y- size.y/2*/,    size.x - size.x/2, size.y/6- size.y/2,     ((size.x / 6) * 5) - size.x/2, 0- size.y/2,      (size.x / 6) - size.x/2, 0- size.y/2     });

        PolygonShape forkShape = new PolygonShape();
        forkShape.setAsBox(15, 3, new Vector2(15, 0), 0);

        PolygonShape tri = new PolygonShape();
        tri.set(new float[]{-14, -14, 0, 14, 14, -14});

		CircleShape wheel = new CircleShape();
		wheel.setRadius(width/8);

		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(loc.x + width/2 , loc.y + height / 2);
		bodyDef.type = BodyDef.BodyType.DynamicBody;

		BodyDef bodyDefFront = new BodyDef();
		bodyDefFront.position.set(loc.x + width/2 , loc.y + height / 2);
		bodyDefFront.type = BodyDef.BodyType.DynamicBody;

		BodyDef bodyDefBack = new BodyDef();
		bodyDefBack.position.set(loc.x + width/2 , loc.y + height / 2);
		bodyDefBack.type = BodyDef.BodyType.DynamicBody;


        BodyDef bodyDefTri = new BodyDef();
        bodyDefTri.position.set(100, 620);
        bodyDefTri.type = BodyDef.BodyType.DynamicBody;

//        bodyDefFork.gravityScale = 0;

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = box;
		fixtureDef.density = 0.5f;
		fixtureDef.friction = 0.8f;
		fixtureDef.restitution = 0.01f;

        FixtureDef fixtureDefWheel = new FixtureDef();
        fixtureDefWheel.shape = wheel;
        fixtureDefWheel.density =10f;
        fixtureDefWheel.friction = 100f;
        fixtureDefWheel.restitution = 0.01f;

        FixtureDef fixtureDefTri = new FixtureDef();
        fixtureDefTri.shape = tri;
        fixtureDefTri.density = 10000f;
        fixtureDefTri.friction = 1000f;
        fixtureDefTri.restitution = 0.01f;
        fixtureDefTri.filter.categoryBits = 0x0002; //i am a triangle

        FixtureDef fixtureDefForks = new FixtureDef();
        fixtureDefForks.shape = forkShape;
        fixtureDefForks.density = 200f;
        fixtureDefForks.friction = 1000f;
        fixtureDefForks.restitution = 0.01f;
        fixtureDefForks.filter.maskBits = 0x0002;//i will collide with triangles

		body = world.createBody(bodyDef);
		bodyFront = world.createBody(bodyDefFront);
        bodyBack = world.createBody(bodyDefBack);
		body.createFixture(fixtureDef);
		bodyFront.createFixture(fixtureDefWheel);
		bodyBack.createFixture(fixtureDefWheel);

        BodyDef bodyDefFork = new BodyDef();
        bodyDefFork.position.set(body.getPosition().x + 32*(float)(Math.sqrt(2)*Math.cos(body.getAngle()+Math.PI/4)) , body.getPosition().y + 32*(float)(Math.sqrt(2)*Math.sin(body.getAngle()+Math.PI/4)));
        bodyDefFork.type = BodyDef.BodyType.KinematicBody;

        forkBody = world.createBody(bodyDefFork);
        forkBody.createFixture(fixtureDefForks);

        triangle = world.createBody(bodyDefTri);
        triangle.createFixture(fixtureDefTri);

		revFront = new RevoluteJointDef();
		revFront.collideConnected = false;
		revFront.bodyA = body;
		revFront.bodyB = bodyFront;
		revFront.localAnchorA.set(size.x/3, -size.y/2 + 4);
		revFront.localAnchorB.set(bodyFront.getLocalCenter());
        revFront.lowerAngle = 1;
        revFront.upperAngle = 0;

//        forkJoint = new RevoluteJointDef();
//        forkJoint.collideConnected = false;
//        forkJoint.bodyA = body;
//        forkJoint.bodyB = forkBody;
//        forkJoint.localAnchorA.set(size.x/2+2, size.y/2-5);
//        forkJoint.localAnchorB.set(-15, 0);
//        forkJoint.enableLimit = true;
//        forkJoint.upperAngle = (float)Math.PI/2;
//        forkJoint.lowerAngle = (float)-Math.PI/2;


		RevoluteJointDef revBack = new RevoluteJointDef();
		revBack.collideConnected = false;
		revBack.bodyA = body;
		revBack.bodyB = bodyBack;
		revBack.localAnchorA.set(-size.x/3, -size.y/2 + 4);
		revBack.localAnchorB.set(bodyBack.getLocalCenter());

		world.createJoint(revFront);
        world.createJoint(revBack);
//        world.createJoint(forkJoint);
    }


	public void update(){
        loc = body.getPosition();

        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            bodyFront.applyAngularImpulse(40000, true);
            bodyBack.applyAngularImpulse(40000, true);
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.D)){
            bodyFront.applyAngularImpulse(-40000, true);
            bodyBack.applyAngularImpulse(-40000, true);
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.Q)){
            bodyFront.applyAngularImpulse(800000, true);
            bodyBack.applyAngularImpulse(800000, true);
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.E)){
            bodyFront.applyAngularImpulse(-800000, true);
            bodyBack.applyAngularImpulse(-800000, true);
        }
		else{
//            bodyFront.setAngularVelocity(0);
//            bodyBack.setAngularVelocity(0);
		}

        if(Gdx.input.isKeyPressed(Input.Keys.UP) && forkAngle < Math.PI/2){
            forkAngle += 0.02f;
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.DOWN) && forkAngle > -Math.PI/2){
            forkAngle -= 0.02f;
        }
        forkBody.setTransform(body.getPosition().x + 32*(float)(Math.sqrt(2)*Math.cos(body.getAngle()+Math.PI/4)) , body.getPosition().y + 32*(float)(Math.sqrt(2)*Math.sin(body.getAngle()+Math.PI/4)), forkAngle + body.getAngle());
        forkBody.setLinearVelocity(body.getLinearVelocity());

        if(body.getPosition().x < 32){
            body.setTransform(new Vector2(32, body.getPosition().y), body.getAngle());
        }
        else if(body.getPosition().x > Gdx.graphics.getWidth() - 32){
            Game.level++;
            Game.initLevel();
        }

        Game.levelPercent = (body.getPosition().x-32) / (Gdx.graphics.getWidth()-64);

        if(Gdx.input.isKeyPressed(Input.Keys.R)){
            Game.initLevel();
        }

    }

	public void render(SpriteBatch batch){
		batch.draw(tex, body.getPosition().x - size.x / 2, body.getPosition().y - size.y / 2, 32,32, 64, 64, 1, 1, (float)Math.toDegrees(body.getAngle()));

        batch.draw(Images.forksRegion, body.getPosition().x + 32*(float)(Math.sqrt(2)*Math.cos(body.getAngle()+Math.PI/4)) , body.getPosition().y + 32*(float)(Math.sqrt(2)*Math.sin(body.getAngle()+Math.PI/4)), 0, 0, 30, 6, 1, 1, (float)Math.toDegrees(forkBody.getAngle()));
	}


	public Vector2 getLoc() {
		return loc;
	}

	public Body getBody() {
		return body;
	}

    public void lose(){
        try {
			HighScore.addScore(System.getProperty("user.name"), Game.level+Game.levelPercent);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

}
