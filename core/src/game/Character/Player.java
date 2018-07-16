package game.Character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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
    private final World world;
    private final TextureRegion marioStand;
    public enum State {STANDING, FALLING, JUMPING, RUNNING, GROWING, DEAD}

    public State currentState;
    private State previousState;

    private Animation marioRun;
    private final TextureRegion marioJump;
    private final TextureRegion marioDead;
    private final TextureRegion bigMarioStand;
    private final TextureRegion bigMarioJump;
    private Animation bigMarioRun;
    private Animation growMario;

    private float stateTimer;
    private boolean runningRight;
    private boolean marioIsBig;
    private boolean runGrowAnimation;
    private boolean timeToDefineBigMario;
    private boolean timeToRedefineMario;
    private boolean marioIsDead;

    public Player(World world, TextureAtlas atlas){
        this.world = world;
        defineSmallMario();
        setBounds(0, 0, 16 / Main.PPM, 16 / Main.PPM);

        marioStand = new TextureRegion(atlas.findRegion("small_mario"), 96, 0, 16, 16);
        bigMarioStand = new TextureRegion(atlas.findRegion("big_mario"), 96, 0, 16, 32);
        marioJump = new TextureRegion(atlas.findRegion("small_mario"), 64, 0, 16, 16);
        bigMarioJump = new TextureRegion(atlas.findRegion("big_mario"), 64, 0, 16, 32);
        marioDead = new TextureRegion(atlas.findRegion("small_mario"), 80, 0, 16, 16);
        setRegion(marioStand);

        setTexture(new Texture("mario_and_enemies.png"));
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;
        createAnimation(atlas);
    }

    private void createAnimation(TextureAtlas atlas){
        Array<TextureRegion> frames = new Array<TextureRegion>();

        for(int i = 0; i < 3; i++)
            frames.add(new TextureRegion(atlas.findRegion("small_mario"), i * 16, 0, 16, 16));

        marioRun = new Animation(.1f, frames);
        frames.clear();

        for(int i = 0; i < 3; i++)
            frames.add(new TextureRegion(atlas.findRegion("big_mario"), i * 16, 0, 16, 32));

        bigMarioRun = new Animation(.1f, frames);
        frames.clear();

        frames.add(new TextureRegion(atlas.findRegion("big_mario"), 224, 0, 16, 32));
        frames.add(new TextureRegion(atlas.findRegion("big_mario"), 96, 0, 16, 32));
        frames.add(new TextureRegion(atlas.findRegion("big_mario"), 224, 0, 16, 32));
        frames.add(new TextureRegion(atlas.findRegion("big_mario"), 96, 0, 16, 32));
        growMario = new Animation(0.2f, frames);
        frames.clear();
    }

    private void defineSmallMario(){
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

    private void defineBigMario(){
        Vector2 currentPosition = body.getPosition();
        world.destroyBody(body);

        BodyDef bdef = new BodyDef();
        bdef.position.set(currentPosition.add(0, 10 / Main.PPM));
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / Main.PPM);
        fdef.filter.categoryBits = Main.MARIO_BIT;
        fdef.filter.maskBits = Main.BRICK_BIT | Main.COIN_BIT | Main.GROUND_BIT | Main.ENEMY_BIT | Main.OBJECT_BIT | Main.ENEMY_HEAD_BIT | Main.ITEM_BIT;
        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);
        shape.setPosition(new Vector2(0, -14 / Main.PPM));
        body.createFixture(fdef).setUserData(this);


        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2 / Main.PPM, 6 / Main.PPM), new Vector2(2 / Main.PPM, 6 / Main.PPM));
        fdef.shape = head;
        fdef.filter.categoryBits = Main.PLAYER__HEAD_BIT;
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData(this);
        timeToDefineBigMario = false;
    }

    private void redefineMario(){
        Vector2 currentPosition = body.getPosition();
        world.destroyBody(body);

        BodyDef bdef = new BodyDef();
        bdef.position.set(currentPosition);
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

        timeToRedefineMario = false;
    }

    public void update(float delta){
        if(marioIsBig)
            setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2 - 6 / Main.PPM);
        else
            setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);

        setRegion(getFrame(delta));
        if(timeToDefineBigMario)
            defineBigMario();

        if(timeToRedefineMario)
            redefineMario();
    }

    private TextureRegion getFrame(float delta){
        currentState = getState();
        TextureRegion region;

        switch(currentState){
            case DEAD:
                region = marioDead;
                break;

            case GROWING:
                region = growMario.getKeyFrame(stateTimer);
                if(growMario.isAnimationFinished(stateTimer))
                    runGrowAnimation = false;
                break;

            case JUMPING:
                region = marioIsBig ? bigMarioJump : marioJump;
                break;

            case RUNNING:
                region = marioIsBig ? bigMarioRun.getKeyFrame(stateTimer, true) : marioRun.getKeyFrame(stateTimer, true);
                break;

            case FALLING:
            case STANDING:
                default:
                    region = marioIsBig ? bigMarioStand : marioStand;
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
        if(marioIsDead)
            return State.DEAD;

        else if(runGrowAnimation)
            return State.GROWING;

        else if(body.getLinearVelocity().y > 0 || (body.getLinearVelocity().y < 0 && previousState == State.JUMPING))
            return State.JUMPING;

        else if(body.getLinearVelocity().y < 0)
            return State.FALLING;

        else if(body.getLinearVelocity().x != 0)
            return State.RUNNING;

        else
            return State.STANDING;
    }

    public void move(){
        if(!marioIsDead) {
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) ? (body.getLinearVelocity().x < 2) : (body.getLinearVelocity().x < 1)))
                body.setLinearVelocity(new Vector2(body.getLinearVelocity().x + 0.1f, body.getLinearVelocity().y));

            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) ? (body.getLinearVelocity().x > -2) : (body.getLinearVelocity().x > -1)))
                body.setLinearVelocity(new Vector2(body.getLinearVelocity().x - 0.1f, body.getLinearVelocity().y));

            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && currentState != State.FALLING && currentState != State.JUMPING)
                body.applyLinearImpulse(new Vector2(0, 4f), body.getWorldCenter(), true);
        }
    }

    public void grow(){
        runGrowAnimation = true;
        marioIsBig = true;
        timeToDefineBigMario = true;
        setBounds(getX(), getY(), getWidth(), getHeight()*2);
        Main.manager.get("audio/powerup.wav", Sound.class).play();
    }

    public void hit(Enemy enemy){

        if(enemy instanceof Turtle && ((Turtle) enemy).getCurrentState() == Turtle.State.STANDTING_SHELL){
            ((Turtle) enemy).kick(this.getX() <= enemy.getX() ? Turtle.KICK_RIGHT_SPEED : Turtle.KICK_LEFT_SPEED);
        }
        else if(marioIsBig){
            marioIsBig = false;
            timeToRedefineMario = true;
            setBounds(getX(), getY(), getWidth(), getHeight() / 2);
            Main.manager.get("audio/powerdown.wav", Sound.class).play();
        }
        else {
            Main.manager.get("audio/mario_music.ogg", Music.class).stop();
            Main.manager.get("audio/mariodie.wav", Sound.class).play();
            marioIsDead = true;
            Filter filter = new Filter();
            filter.maskBits = Main.NOTHING_BIT;

            for(Fixture fixture : body.getFixtureList())
                fixture.setFilterData(filter);

            body.applyLinearImpulse(new Vector2(0, 4f), body.getWorldCenter(), true);
        }
    }

    public Body getBody(){
        return body;
    }

    public boolean isMarioIsBig() {
        return marioIsBig;
    }

    public boolean isMarioIsDead() {
        return marioIsDead;
    }

    public float getStateTimer() {
        return stateTimer;
    }
}
