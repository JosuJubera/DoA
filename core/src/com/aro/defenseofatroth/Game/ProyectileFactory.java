package com.aro.defenseofatroth.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Pool;

/**
 * Esta clase genera los proyectiles de las diferentes torres. Posee diferentes Pools para mejorar
 * el rendimiento. Los proyectiles son generados por las torres.
 * Created by Sergio on 17/04/2016.
 */
public class ProyectileFactory implements ObjectPool<Proyectile> {
    private Pool<BasicTowerProyectile> basicTowerProyectilePool;
    private Pool<MissileProyectile> missileProyectilePool;
    private TextureLoader textureLoader;

    public TextureLoader getTextureLoader() {
        return textureLoader;
    }

    public void setTextureLoader(TextureLoader textureLoader) {
        this.textureLoader = textureLoader;
    }

    public void crearPools(){
        final ProyectileFactory niapa=this;
        basicTowerProyectilePool=new Pool<BasicTowerProyectile>() {
            @Override
            protected BasicTowerProyectile newObject() {
                BasicTowerProyectile proyectile=new BasicTowerProyectile();
                BodyDef cuerpoDef=new BodyDef();
                cuerpoDef.type = BodyDef.BodyType.DynamicBody;
                cuerpoDef.bullet=true;
                Body cuerpo=textureLoader.getMundo().createBody(cuerpoDef);
                cuerpo.setUserData(proyectile); //Añadimos un puntero al cuerpo con la informacion del tanke
                FixtureDef fixtureDef=new FixtureDef();
                CircleShape shape =  new CircleShape(); //El shape tambien puede ser un cuadrado, si eso se camia aki
                shape.setRadius(75f);
                fixtureDef.shape = shape;
                fixtureDef.isSensor=true;
                fixtureDef.filter.categoryBits = Proyectile.PROYECTILE_BIT; //su categoria
                fixtureDef.filter.maskBits = Enemy.ENEMY_BIT; //con quien choca
                cuerpo.createFixture(fixtureDef);
                shape.dispose();
                proyectile.setCuerpo(cuerpo);
                proyectile.setTextura(textureLoader.obtenerProyectilBasicTower());
                proyectile.setPoolOrigen(niapa);
                proyectile.setPosicion(new Vector2(0, 0));
                proyectile.setVelocidad(new Vector2(0, 0));
                return proyectile;
            }
        };
        missileProyectilePool=new Pool<MissileProyectile>() {
            @Override
            protected MissileProyectile newObject() {
                MissileProyectile proyectile=new MissileProyectile();
                BodyDef cuerpoDef=new BodyDef();
                cuerpoDef.type = BodyDef.BodyType.DynamicBody;
                cuerpoDef.bullet=true;
                Body cuerpo=textureLoader.getMundo().createBody(cuerpoDef);
                cuerpo.setUserData(proyectile); //Añadimos un puntero al cuerpo con la informacion del tanke
                FixtureDef fixtureDef=new FixtureDef();
                CircleShape shape =  new CircleShape(); //El shape tambien puede ser un cuadrado, si eso se camia aki
                shape.setRadius(50f);
                fixtureDef.shape = shape;
                fixtureDef.isSensor=true;
                fixtureDef.filter.categoryBits = Proyectile.PROYECTILE_BIT; //su categoria
                fixtureDef.filter.maskBits = Enemy.ENEMY_BIT; //con quien choca
                cuerpo.createFixture(fixtureDef);
                shape.dispose();
                proyectile.setCuerpo(cuerpo);
                proyectile.setTextura(textureLoader.obtenerMissile());
                proyectile.setPoolOrigen(niapa);
                proyectile.setPosicion(new Vector2(0, 0));
                proyectile.setVelocidad(new Vector2(0, 0));
                return proyectile;
            }
        };
    }

    @Override
    public void remove(Proyectile freeObject) {
        if (freeObject instanceof BasicTowerProyectile){
            basicTowerProyectilePool.free((BasicTowerProyectile) freeObject);
        }
        if (freeObject instanceof MissileProyectile){
            missileProyectilePool.free((MissileProyectile) freeObject);
        }
    }

    public BasicTowerProyectile obtenerProyectilTorreBasica(Enemy objetivo,Vector2 posicion,int daño){
        BasicTowerProyectile aux=basicTowerProyectilePool.obtain();
        aux.setPosicion(posicion);
        aux.setEnemigo(objetivo);
        aux.setVelocidadM(200);
        aux.setDaino(daño);
        textureLoader.getEscenario().addActor(aux);
        return aux;
    }

    public MissileProyectile obtenerMisil(Enemy objetivo,Vector2 posicion,int daño){
        MissileProyectile aux=missileProyectilePool.obtain();
        aux.setPosicion(posicion);
        aux.setEnemigo(objetivo);
        aux.setVelocidadM(200);
        aux.setDaino(daño);
        textureLoader.getEscenario().addActor(aux);
        return aux;
    }
}
