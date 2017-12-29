package game.Block;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import game.Main;

/**
 * Created by barti on 10.12.2017.
 */
public class Player extends Sprite{

    private final Body body;

    public Player(World world){
        BodyDef bdef = new BodyDef();
        bdef.position.set(32f / Main.PPM, 32f / Main.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5 / Main.PPM);

        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);
        /*shape.setPosition(new Vector2(0, -14 / 100f));
        body.createFixture(fdef).setUserData(this);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2 / 100f, 6 / 100f), new Vector2(2 / 100f, 6 / 100f));
        fdef.shape = head;
        body.createFixture(fdef).setUserData(this);
*/
        setTexture(new Texture("mariosmall.png"));
        setRegion(14, 0, 14, 16);

        setPosition(20, 20);
    }


    public void move(){
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            if(body.getLinearVelocity().x < 1)
                body.setLinearVelocity(new Vector2(body.getLinearVelocity().x + 0.1f, body.getLinearVelocity().y));
        }

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            if(body.getLinearVelocity().x > -1)
                body.setLinearVelocity(new Vector2(body.getLinearVelocity().x - 0.1f, body.getLinearVelocity().y));
        }
    }

    public Body getBody(){
        return body;
    }

}
