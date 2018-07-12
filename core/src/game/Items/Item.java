package game.Items;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import game.Character.Player;
import game.Main;

public abstract class Item extends Sprite {
    final World world;
    private final TextureAtlas atlas;
    Vector2 velocity;
    private boolean toDestroy;
    private boolean destroyed;
    Body body;

    Item(World world, TextureAtlas atlas, float x, float y){
        this.world = world;
        this.atlas = atlas;
        setPosition(x, y);
        setBounds(getX(), getY(), 16 / Main.PPM, 16 / Main.PPM);
        defineItem();
        toDestroy = false;
        destroyed = false;
    }

    protected abstract void defineItem();
    public abstract void use(Player player);

    public void update(float delta){
        if(toDestroy && !destroyed){
            world.destroyBody(body);
            destroyed = true;
        }
    }

    public void draw(Batch batch){
        if(!destroyed)
            super.draw(batch);
    }

    void destroy(){
        toDestroy = true;
    }

    public void reverseVelocity(boolean x, boolean y){
        if(x)
            velocity.x = -velocity.x;

        if(y)
            velocity.y = -velocity.y;
    }
}
