package game.Block;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by barti on 10.12.2017.
 */
public class Player{

    private final Body body;
    private final Fixture foot;
    private Sprite sprite;
    private boolean contact;

    public Player(World world){

        contact = true;

        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set(4f, 6.5f);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1f, 1f);

        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        fdef.friction = 0.1f;
        fdef.density = 58f;

        PolygonShape footShape = new PolygonShape();
        footShape.setAsBox(.5f, .05f, new Vector2(0f, -0.95f), 0);

        FixtureDef footfdef = new FixtureDef();
        footfdef.shape = footShape;
        footfdef.friction = 0.1f;
        footfdef.density = 2f;

        body = world.createBody(bdef);
        body.createFixture(fdef);
        foot = body.createFixture(footfdef);
        body.setFixedRotation(true);

        shape.dispose();
        footShape.dispose();

        sprite = new Sprite(new Texture("mariosmall.png"), 14, 0, 14, 16);
        sprite.setSize(2, 2);
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
        body.setUserData(sprite);
    }

    public void draw(SpriteBatch batch){
        sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2,  body.getPosition().y - sprite.getHeight() / 2);
        sprite.draw(batch);
    }

    public void move(){
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            if(body.getLinearVelocity().x < 12)
                body.setLinearVelocity(new Vector2(body.getLinearVelocity().x + 0.5f, body.getLinearVelocity().y));
        }

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            if(body.getLinearVelocity().x > -12)
                body.setLinearVelocity(new Vector2(body.getLinearVelocity().x - 0.5f, body.getLinearVelocity().y));
        }

        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            if(contact)
                body.applyLinearImpulse(new Vector2(0, 550f), body.getWorldCenter(), true);
        }
    }

    public void setContact(boolean contact){
        this.contact = contact;
    }

    public boolean isContact(){
        return contact;
    }

    public Fixture getPlayerFoot(){
        return foot;
    }

    public Body getBody(){
        return body;
    }

}
