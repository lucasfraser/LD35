package au.com.ionprogramming.ld35;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;


public class Forklift {

	protected Vector2 loc = new Vector2(0, 0);
	protected Vector2 size;
    protected Body body;
	protected Body bodyFront;
	protected TextureRegion tex;
	protected RevoluteJointDef revFront;


    public Forklift(float x, float y, float width, float height, World world, Lighting lighting, TextureRegion texture){

		tex = texture;

        loc = new Vector2(x, y);

		size = new Vector2(width, height);

		PolygonShape box = new PolygonShape();
		box.setAsBox(size.x/2, size.y/2 - 6);
//		box.set(new float[]{0 - size.x/2, size.y / 6- size.y/2,   0 - size.x/2, -20/*size.y - size.y/2*/,    size.x - size.x/2, -20/*size.y- size.y/2*/,    size.x - size.x/2, size.y/6- size.y/2,     ((size.x / 6) * 5) - size.x/2, 0- size.y/2,      (size.x / 6) - size.x/2, 0- size.y/2     });

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

		body = world.createBody(bodyDef);
		bodyFront = world.createBody(bodyDefFront);
		Body bodyBack = world.createBody(bodyDefBack);
		body.createFixture(fixtureDef);
		bodyFront.createFixture(fixtureDefWheel);
		bodyBack.createFixture(fixtureDefWheel);

		revFront = new RevoluteJointDef();
		revFront.collideConnected = false;
		revFront.bodyA = body;
		revFront.bodyB = bodyFront;
		revFront.localAnchorA.set(size.x/3, -size.y/2 + 4);
		revFront.localAnchorB.set(bodyFront.getLocalCenter());
        revFront.lowerAngle = 1;
        revFront.upperAngle = 0;

		RevoluteJointDef revBack = new RevoluteJointDef();
		revBack.collideConnected = false;
		revBack.bodyA = body;
		revBack.bodyB = bodyBack;
		revBack.localAnchorA.set(-size.x/3, -size.y/2 + 4);
		revBack.localAnchorB.set(bodyBack.getLocalCenter());

		world.createJoint(revFront);
		world.createJoint(revBack);
    }


	public void update(){
        loc = body.getPosition();
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
			bodyFront.applyAngularImpulse(400000, true);

        }
        else if(Gdx.input.isKeyPressed(Input.Keys.D)){
			bodyFront.applyAngularImpulse(-400000, true);
        }
		else{
            bodyFront.setAngularVelocity(0);
		}

	}

	public void render(SpriteBatch batch){
		batch.draw(tex, body.getPosition().x - size.x / 2, body.getPosition().y - size.y / 2, 32,32, 64, 64, 1, 1, (float)Math.toDegrees(body.getAngle()));
	}


	public Vector2 getLoc() {
		return loc;
	}

	public Body getBody() {
		return body;
	}

}
