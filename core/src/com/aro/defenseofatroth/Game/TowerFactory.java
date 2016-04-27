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


    public BasicTower obtenerBasicTower(Vector2 posicion){
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
        fixtureDef.filter.categoryBits = Tower.TORRE_SENSOR_BIT; //su categoria
        fixtureDef.filter.maskBits = Enemy.ENEMY_BIT; //con quien choca
        cuerpo.createFixture(fixtureDef);
        shape.dispose();
        torre.setCuerpo(cuerpo);
        torre.setEstado(1);
        torre.setPosicion(posicion);
        torre.setProyectileFactory(proyectileFactory); //Le añadimos la factoria de proyectiles
        torre.setTiempoEntreAtaques(1000f); //Tiempo en milisegundos entre ataques
        torre.setTiempoSiguienteAtaque(1000f); //Tiempo pal siguiente ataque
        textureLoader.getEscenario().addActor(torre); //lo añadimos al stage para que se dibuje
        return torre;
    }

}
