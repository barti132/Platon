package game.Character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import game.Main;

/**
 * Created by barti on 10.12.2017.
 */
public class Player extends Sprite{

    private Body body;
    private TextureRegion marioStand;
    private enum State {STANDING, FALLING, JUMPING, RUNNING};
    private State currentState;
    private State previousState;
    private Animation marioRun;
    private Animation marioJump;
    private float stateTimer;
    private boolean runningRight;

    public Player(World world, TextureAtlas atlas){
        createBox2DMario(world);
        TextureRegion region = atlas.findRegion("small_mario");
        setBounds(0, 0, 16 / Main.PPM, 16 / Main.PPM);

        marioStand = new TextureRegion(region, 96, 0, 16, 16);
        setRegion(marioStand);

        setTexture(new Texture("mario_and_enemies.png"));
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;
        createAnimation(region);
    }

    private void createAnimation(TextureRegion region){
        Array<TextureRegion> frames = new Array<TextureRegion>();

        for(int i = 0; i < 3; i++)
            frames.add(new TextureRegion(region, i * 16, 0, 16, 16));

        marioRun = new Animation(.1f, frames);
        frames.clear();

        for(int i = 3; i < 5; i++)
            frames.add(new TextureRegion(region, i * 16, 0, 16, 16));

        marioJump = new Animation(.1f, frames);
        frames.clear();
    }

    private void createBox2DMario(World world){
        BodyDef bdef = new BodyDef();
        bdef.position.set(32f / Main.PPM, 32f / Main.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / Main.PPM);
        fdef.filter.categoryBits = Main.MARIO_BIT;
        fdef.filter.maskBits = Main.BRICK_BIT | Main.COIN_BIT | Main.GROUND_BIT | Main.ENEMY_BIT | Main.OBJECT_BIT | Main.ENEMY_HEAD_BIT | Main.ITEM_BIT;

        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2 / Main.PPM, 6 / Main.PPM), new Vector2(2 / Main.PPM, 6 / Main.PPM));
        fdef.shape = head;
        fdef.filter.categoryBits = Main.PLAYER__HEAD_BIT;
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData(this);
    }

    public void update(float delta){
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(delta));
    }

    private TextureRegion getFrame(float delta){
        currentState = getState();
        TextureRegion region;

        switch(currentState){
            case JUMPING:
                region = marioJump.getKeyFrame(stateTimer);
                break;

            case RUNNING:
                region = marioRun.getKeyFrame(stateTimer, true);
                break;

            case FALLING:
            case STANDING:
                default:
                    region = marioStand;
                    break;
        }

        if((body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()){
            region.flip(true, false);
            runningRight = false;
        }

        else if((body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()){
            region.flip(true, false);
            runningRight = true;
        }

        stateTimer = (currentState == previousState) ? (stateTimer + delta) : 0;
        previousState = currentState;
        return region;

    }

    private State getState(){
        if(body.getLinearVelocity().y > 0 || (body.getLinearVelocity().y < 0 && previousState == State.JUMPING))
            return State.JUMPING;

        else if(body.getLinearVelocity().y < 0)
            return State.FALLING;

        else if(body.getLinearVelocity().x != 0)
            return State.RUNNING;

        else
            return State.STANDING;
    }

    public void move(){
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) ? (body.getLinearVelocity().x < 2) : (body.getLinearVelocity().x < 1)))
            body.setLinearVelocity(new Vector2(body.getLinearVelocity().x + 0.1f, body.getLinearVelocity().y));

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) ? (body.getLinearVelocity().x > -2) : (body.getLinearVelocity().x > -1)))
            body.setLinearVelocity(new Vector2(body.getLinearVelocity().x - 0.1f, body.getLinearVelocity().y));

        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && currentState != State.FALLING && currentState != State.JUMPING)
            body.applyLinearImpulse(new Vector2(0, 4f), body.getWorldCenter(), true);
    }

    public Body getBody(){
        return body;
    }

}
