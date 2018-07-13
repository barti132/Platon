package game.Character;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import game.Main;

public class Turtle extends Enemy {

    public enum State{WALKING, SHELL}
    public State currentState;
    public State previousState;
    private float stateTime;
    private final Animation walkAnimation;
    private boolean setToDestroy;
    private boolean destroyed;
    private TextureRegion shell;

    public Turtle(World world, TextureAtlas atlas, MapObject object){
        super(world, object);

        Array<TextureRegion> frames = new Array<TextureRegion>();
        frames.add(new TextureRegion(atlas.findRegion("turtle"), 32, 0, 16, 24));
        frames.add(new TextureRegion(atlas.findRegion("turtle"), 48, 0, 16, 24));
        shell = new TextureRegion(atlas.findRegion("turtle"), 64, 0, 16, 24);
        walkAnimation = new Animation(0.2f, frames);
        currentState = previousState = State.WALKING;

        setBounds(getX(), getY(), 16 / Main.PPM, 24 / Main.PPM);
    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / Main.PPM);
        fdef.filter.categoryBits = Main.ENEMY_BIT;
        fdef.filter.maskBits = Main.BRICK_BIT | Main.COIN_BIT | Main.GROUND_BIT | Main.MARIO_BIT | Main.OBJECT_BIT | Main.ENEMY_BIT;

        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);

        PolygonShape head = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(-5, 8).scl(1 / Main.PPM);
        vertice[1] = new Vector2(5, 8).scl(1 / Main.PPM);
        vertice[2] = new Vector2(-3, 3).scl(1 / Main.PPM);
        vertice[3] = new Vector2(3, 3).scl(1 / Main.PPM);
        head.set(vertice);

        fdef.shape = head;
        fdef.restitution = 0.5f;
        fdef.filter.categoryBits = Main.ENEMY_HEAD_BIT;
        body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void hitOnHead() {
        if(currentState != State.SHELL)
            currentState = State.SHELL;
            velocity.x = 0;

    }

    public TextureRegion getFrame(float delta){
        TextureRegion region;

        switch (currentState){
            case SHELL:
                region = shell;
                break;
            case WALKING:
                default:
                    region = walkAnimation.getKeyFrame(stateTime, true);
                    break;
        }

        if(velocity.x > 0 && region.isFlipX() == false)
            region.flip(true, false);

        if(velocity.x < 0 && region.isFlipX() == true)
            region.flip(true, false);

        stateTime = (currentState == previousState) ? (stateTime + delta) : 0;
        previousState = currentState;

        return region;
    }

    @Override
    public void update(float delta) {
        setRegion(getFrame(delta));
        if(currentState == State.SHELL && stateTime > 5){
            currentState = State.WALKING;
            velocity.x = 1;
        }

        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - 8 / Main.PPM);
        body.setLinearVelocity(velocity);
    }
}
