package com.aro.defenseofatroth.Screens;

import com.aro.defenseofatroth.MainClass;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MenuScreen extends BaseScreen {

	protected MainClass game;

	private static int VIRTUAL_WIDTH = 800;
	private static int VIRTUAL_HEIGHT = 600;

	private SpriteBatch batch;
	private Texture title;

	private Viewport viewport;

	private Stage stage;

	private BitmapFont font;
	private Skin skin;

	public boolean musica = false;

	public MenuScreen(MainClass game) {
		super(game);
		this.game = game;
		create();
		render(Gdx.graphics.getDeltaTime());
	}

	public void create () {

		VIRTUAL_WIDTH = Gdx.app.getGraphics().getWidth();
		VIRTUAL_HEIGHT = Gdx.app.getGraphics().getHeight();
		viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);

		batch = new SpriteBatch();
		title = new Texture("title.png");
		stage = new Stage(viewport);

		//Create a font
		font = new BitmapFont();
		skin = new Skin();
		skin.add("default", font);

		//Create a texture
		Pixmap pixmap = new Pixmap(Gdx.graphics.getWidth() /4,(int)Gdx.graphics.getHeight()/10, Pixmap.Format.RGB888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		skin.add("background",new Texture(pixmap));

		//Create a button style
		TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
		textButtonStyle.up = skin.newDrawable("background", Color.GRAY);
		textButtonStyle.down = skin.newDrawable("background", Color.DARK_GRAY);
		textButtonStyle.checked = skin.newDrawable("background", Color.DARK_GRAY);
		textButtonStyle.over = skin.newDrawable("background", Color.LIGHT_GRAY);
		textButtonStyle.font = skin.getFont("default");
		skin.add("default", textButtonStyle);


		TextButton unjugador = new TextButton("Un_jugador", skin); // Use the initialized skin
		unjugador.setPosition(VIRTUAL_WIDTH /2 - VIRTUAL_WIDTH /8 , VIRTUAL_HEIGHT* 3 /8);
		stage.addActor(unjugador);


		TextButton ranking = new TextButton("Ranking", skin); // Use the initialized skin
		ranking.setPosition(VIRTUAL_WIDTH /2 - VIRTUAL_WIDTH /8 , VIRTUAL_HEIGHT * 2 /8);
		stage.addActor(ranking);

		// Single player button listener
		unjugador.addListener( new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				stage.addAction(Actions.sequence(Actions.fadeOut(0.2f), Actions.run(new Runnable() {
					@Override
					public void run() {
						((Game) Gdx.app.getApplicationListener()).setScreen(new SinglePlayScreen(game, musica));
					}
				})));
			};
		});


		// Ranking button listener
		ranking.addListener( new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new RankingScreen(game));
			};
		});
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void hide() {

	}



	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act(Math.min(delta, 1 / 60f));
		stage.draw();
		batch.begin();
		batch.draw(title, VIRTUAL_WIDTH /2 - VIRTUAL_WIDTH /8, VIRTUAL_HEIGHT * 5 /8);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
	}

	@Override
	public void dispose() {

		game.dispose();
		batch.dispose();
		title.dispose();
		stage.dispose();
		font.dispose();
		skin.dispose();
	}
}
