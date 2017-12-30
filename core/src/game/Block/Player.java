package game.Block;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import game.Main;

/**
 * Created by barti on 10.12.2017.
 */
public class Player extends Sprite{

    private Body body;
    private TextureRegion marioStand;

    public Player(World world, TextureAtlas atlas){
        super(atlas.findRegion("small_mario"));
        body = null;
        createBox2DMario(world);
        marioStand = new TextureRegion(getTexture(), 0, 0, 16, 16);
        setBounds(0, 0, 16 / Main.PPM, 16 / Main.PPM);
        setRegion(marioStand);
    }

    private void createBox2DMario(World world){
        BodyDef bdef = new BodyDef();
        bdef.position.set(32f / Main.PPM, 32f / Main.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / Main.PPM);

        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);
    }

    public void update(float delta){
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
    }

    public void move(){
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && body.getLinearVelocity().x < 1)
            body.setLinearVelocity(new Vector2(body.getLinearVelocity().x + 0.1f, body.getLinearVelocity().y));

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && body.getLinearVelocity().x > -1)
            body.setLinearVelocity(new Vector2(body.getLinearVelocity().x - 0.1f, body.getLinearVelocity().y));

        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
            body.applyLinearImpulse(new Vector2(0, 4f), body.getWorldCenter(), true);
    }

    public Body getBody(){
        return body;
    }

}
