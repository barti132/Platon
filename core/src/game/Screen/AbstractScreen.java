package game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by barti on 06.12.2017.
 */
abstract class AbstractScreen implements Screen{
    OrthographicCamera camera;
    final SpriteBatch batch;

    AbstractScreen(){
        createCamera();
        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);
    }

    private void createCamera(){
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth() / 18, Gdx.graphics.getHeight() / 18);
        camera.update();
    }

    @Override
    public void show(){

    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(30 / 255f, 102 / 255f, 158 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void resize(int width, int height){

    }

    @Override
    public void pause(){

    }

    @Override
    public void resume(){

    }

    @Override
    public void hide(){

    }

    @Override
    public void dispose(){

    }
}