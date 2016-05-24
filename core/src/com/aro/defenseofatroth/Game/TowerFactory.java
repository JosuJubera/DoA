package com.aro.defenseofatroth.Game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

/**
 * Esta clase es la encargada de generar las diferentes torres que hay en el juego.
 * Created by Sergio on 17/04/2016.
 */
public class TowerFactory {
    private TextureLoader textureLoader;
    private ProyectileFactory proyectileFactory;

    public TextureLoader getTextureLoader() {
        return textureLoader;
    }

    public void setTextureLoader(TextureLoader textureLoader) {
        this.textureLoader = textureLoader;
    }

    public ProyectileFactory getProyectileFactory() {
        return proyectileFactory;
    }

    public void setProyectileFactory(ProyectileFactory proyectileFactory) {
        this.proyectileFactory = proyectileFactory;
    }

    public BasicTower obtenerBasicTower(float x, float y){
        //A diferencia de proyectiles y unidades, aqui no hay pool. T.odo se especifica aqui
        BasicTower torre=new BasicTower();
        torre.setTextura(textureLoader.obtenerBasicTower());
        //Se crea cuerpo
        BodyDef cuerpoDef=new BodyDef();
        cuerpoDef.type = BodyDef.BodyType.StaticBody;
        Body cuerpo=textureLoader.getMundo().createBody(cuerpoDef);
        cuerpo.setUserData(torre); //Añadimos un puntero al cuerpo con la informacion de la torre
        FixtureDef fixtureDef=new FixtureDef();
        CircleShape shape =  new CircleShape(); //Sensor de la torre
        shape.setRadius(BasicTower.ALCANCE); //Radio del sensor (inicial)
        fixtureDef.shape = shape;
        fixtureDef.isSensor=true;
        fixtureDef.filter.categoryBits = Tower.TORRE_SENSOR_BIT; //su categoria
        fixtureDef.filter.maskBits = Enemy.ENEMY_BIT; //con quien choca
        cuerpo.createFixture(fixtureDef);
        shape.dispose();
        torre.setCuerpo(cuerpo);
        torre.setEstado(1);
        torre.setPosicion(new Vector2(x,y));
        torre.setProyectileFactory(proyectileFactory); //Le añadimos la factoria de proyectiles
        torre.setTiempoEntreAtaques(1f); //Tiempo en milisegundos entre ataques
        torre.setTiempoSiguienteAtaque(1f); //Tiempo pal siguiente ataque
        textureLoader.getEscenario().addActor(torre); //lo añadimos al stage para que se dibuje
        torre.ñapa();
        return torre;
    }

    public MissileTower obtenerMissileTower(float x, float y){
        //A diferencia de proyectiles y unidades, aqui no hay pool. T.odo se especifica aqui
        MissileTower torre=new MissileTower();
        torre.setTextura(textureLoader.obtenerBasicTower());
        //Se crea cuerpo
        BodyDef cuerpoDef=new BodyDef();
        cuerpoDef.type = BodyDef.BodyType.StaticBody;
        Body cuerpo=textureLoader.getMundo().createBody(cuerpoDef);
        cuerpo.setUserData(torre); //Añadimos un puntero al cuerpo con la informacion de la torre
        FixtureDef fixtureDef=new FixtureDef();
        CircleShape shape =  new CircleShape(); //Sensor de la torre
        shape.setRadius(MissileTower.ALCANCE); //Radio del sensor (inicial)
        fixtureDef.shape = shape;
        fixtureDef.isSensor=true;
        fixtureDef.filter.categoryBits = Tower.TORRE_SENSOR_BIT; //su categoria
        fixtureDef.filter.maskBits = Enemy.ENEMY_BIT; //con quien choca
        cuerpo.createFixture(fixtureDef);
        shape.dispose();
        torre.setCuerpo(cuerpo);
        torre.setEstado(1);
        torre.setPosicion(new Vector2(x,y));
        torre.setProyectileFactory(proyectileFactory); //Le añadimos la factoria de proyectiles
        torre.setTiempoEntreAtaques(2.5f); //Tiempo en milisegundos entre ataques
        torre.setTiempoSiguienteAtaque(2.5f); //Tiempo pal siguiente ataque
        textureLoader.getEscenario().addActor(torre); //lo añadimos al stage para que se dibuje
        return torre;
    }

}
