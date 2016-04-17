package au.com.ionprogramming.ld35;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;


public abstract class Entity {

	protected Vector2 loc = new Vector2(0, 0);
	protected Vector2 size;
    protected Body body;
	protected Texture tex;

	protected boolean flipX = false;
	protected boolean flipY = false;



	protected Sound sound;

    public Entity(boolean moving, float x, float y, float width, float height, World world, Lighting lighting, Texture texture, boolean rounded){

		tex = texture;

        loc = new Vector2(x, y);

		size = new Vector2(width, height);

		PolygonShape shape = new PolygonShape();
        if(moving){
            shape.setAsBox(size.x / 2 - 0.1f*size.x, size.y / 2 - 0.1f*size.y);
        }
        else {
            shape.setAsBox(size.x / 2, size.y / 2);
        }
		PolygonShape round = null;
		if(rounded) {
			round = new PolygonShape();
			round.set(new float[]{0 - size.x/2, size.y / 6- size.y/2,   0 - size.x/2, size.y- size.y/2,    size.x - size.x/2, size.y- size.y/2,    size.x - size.x/2, size.y/6- size.y/2,     ((size.x / 3) * 2) - size.x/2, 0- size.y/2,      (size.x / 3) - size.x/2, 0- size.y/2     });
		}

		BodyDef bodyDef = new BodyDef();

		bodyDef.position.set(loc.x + width/2 , loc.y + height / 2);

        if(moving){
		    bodyDef.type = BodyDef.BodyType.DynamicBody;

			FixtureDef fixtureDef = new FixtureDef();
			if(rounded){
				fixtureDef.shape = round;
			}
			else {
				fixtureDef.shape = shape;
			}
			fixtureDef.density = 0.5f;
			fixtureDef.friction = 0.4f;
			fixtureDef.restitution = 0.3f;

			body = world.createBody(bodyDef);

			Fixture fixture = body.createFixture(fixtureDef);
        }
        else {
            bodyDef.type = BodyDef.BodyType.StaticBody;

			body = world.createBody(bodyDef);

			body.createFixture(shape, 0.0f);
       }

		shape.dispose();
    }


	public void update(){
        loc = body.getPosition();
	}

	public void render(SpriteBatch batch){
		batch.draw(tex, body.getPosition().x - size.x / 2, body.getPosition().y - size.y / 2);
	}


	public Vector2 getLoc() {
		return loc;
	}

	public void setLoc(Vector2 loc) {
		this.loc = loc;
	}

	public Vector2 getSize() {
		return size;
	}

	public void setSize(Vector2 size) {
		this.size = size;
	}

	public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
	}

	public Texture getTex() {
		return tex;
	}

	public void setTex(Texture tex) {
		this.tex = tex;
	}

}
