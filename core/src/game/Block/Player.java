package game.Block;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by barti on 10.12.2017.
 */
public class Player{

    private final Body body;
    private final Fixture foot;
    private Sprite sprite;

    public Player(World world){

        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set(50f, 50f);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1f, 1f, new Vector2(0f, 0f), 0);

        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        fdef.friction = 0.1f;
        fdef.density = 58f;

        PolygonShape footShape = new PolygonShape();
        footShape.setAsBox(0.5f, 0.5f, new Vector2(0f, -1.95f), 0);

        FixtureDef footfdef = new FixtureDef();
        footfdef.shape = footShape;
        footfdef.friction = 0.1f;
        footfdef.density = 2f;

        body = world.createBody(bdef);
        body.createFixture(footfdef);
        foot = body.createFixture(footfdef);
        body.setFixedRotation(true);

        shape.dispose();
        footShape.dispose();
    }

}
