package com.aro.defenseofatroth.Screens;

import com.aro.defenseofatroth.MainClass;
import com.badlogic.gdx.Screen;

/**
 * Created by Juber on 11/03/2016.
 */
public abstract class BaseScreen implements Screen {

    protected MainClass game;

    public BaseScreen(MainClass game) {
        this.game = game;
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {}

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        game.dispose();
    }
}
