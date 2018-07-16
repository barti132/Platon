package game.Character;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import game.Main;

public class Turtle extends Enemy {

    public static final int KICK_LEFT_SPEED = -2;
    public static final int KICK_RIGHT_SPEED = -2;
    public enum State{WALKING, STANDTING_SHELL, MOVING_SHELL, DEAD}
    public State currentState;
    public State previousState;
    private float stateTime;
    private final Animation walkAnimation;
    private boolean setToDestroy;
    private boolean destroyed;
    private float deadRotationDegrees;
    private TextureRegion shell;

    public Turtle(World world, TextureAtlas atlas, MapObject object){
        super(world, object);

        deadRotationDegrees = 0;
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
        fdef.restitution = 1.5f;
        fdef.filter.categoryBits = Main.ENEMY_HEAD_BIT;
        body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void hitOnHead(Player player) {
        if(currentState != State.STANDTING_SHELL) {
            currentState = State.STANDTING_SHELL;
            velocity.x = 0;
        }

        else
            kick(player.getX() <= this.getX() ? KICK_RIGHT_SPEED : KICK_LEFT_SPEED);
    }

    public TextureRegion getFrame(float delta){
        TextureRegion region;

        switch (currentState){
            case STANDTING_SHELL:
            case MOVING_SHELL:
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
        if(currentState == State.STANDTING_SHELL && stateTime > 5){
            currentState = State.WALKING;
            velocity.x = 1;
        }

        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - 8 / Main.PPM);

        if(currentState == State.DEAD){
            deadRotationDegrees += 3;
            rotate(deadRotationDegrees);
            if(stateTime > 5 && !destroyed){
                world.destroyBody(body);
                destroyed = true;
            }
        }
        else
            body.setLinearVelocity(velocity);
    }

    public void kick(int speed){
        velocity.x = speed;
        currentState = State.MOVING_SHELL;
    }

    public State getCurrentState(){
        return currentState;
    }

    @Override
    public void onEnemyHit(Enemy enemy){
        if(enemy instanceof Turtle){
            if(((Turtle)enemy).currentState == State.MOVING_SHELL && currentState != State.MOVING_SHELL){
                killed();
            }
            else if(currentState == State.MOVING_SHELL && ((Turtle) enemy).currentState == State.WALKING)
                return;
            else
                reverseVelocity(true, false);
        }
        else if(currentState != State.MOVING_SHELL)
            reverseVelocity(true, false);

    }

    public void killed(){
        currentState = State.DEAD;
        Filter filter = new Filter();
        filter.maskBits = Main.NOTHING_BIT;

        for(Fixture fixture : body.getFixtureList())
            fixture.setFilterData(filter);

        body.applyLinearImpulse(new Vector2(0, 5f), body.getWorldCenter(), true);
    }

    public  void draw(Batch batch){
        if(!destroyed)
            super.draw(batch);
    }
}
