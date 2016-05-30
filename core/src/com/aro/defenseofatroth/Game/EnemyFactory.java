package com.aro.defenseofatroth.Game;

import com.aro.defenseofatroth.BarraVida;
import com.aro.defenseofatroth.Levels.Level1;
import com.aro.defenseofatroth.Levels.Level2;
import com.aro.defenseofatroth.Tools.Constants;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

/**
 * Esta clase se encarga de generar los diferentes enemigos que hay en el juego. Esta clase utiliza
 * diferentes Pools para los enemios.
 * Created by Sergio on 17/04/2016.
 */
public class EnemyFactory implements ObjectPool<Enemy> {
    protected Array<Vector2> ruta; //La ruta que seguiran
    private Pool<BasicTank> basicTankPool; //Pool de los tankes basicos
    private Pool<HeavyTank> heavyTankPool; //Pool de los tankes pesados
    private Pool<MotorTank> motorTankPool; //Pool de los tankes motos
    private TextureLoader textureLoader;
    Array<Enemy> enemies;

    public Array<Enemy> getEnemies() {
        return enemies;
    }

    public TextureLoader getTextureLoader() {
        return textureLoader;
    }

    public void setTextureLoader(TextureLoader textureLoader) {
        this.textureLoader = textureLoader;
    }

    /**
     * Establece la ruta que seguiran las unidades
     * @param ruta array con la ruta
     */
    public void setRuta(Array<Vector2> ruta){
        this.ruta=ruta;
    }

    //A una mala esto puede ir en otra clase
    public void crearPools(){
        enemies=new Array<Enemy>();
        final EnemyFactory niapa=this; //referencia a si mismo, usado por los generadores de los pools
        //Creamos el pools de BasicTank, hay que redefirnir el metodo newObject y añadir la informacion del mundo y del cuerpo
        basicTankPool=new Pool<BasicTank>() {
            @Override
            protected BasicTank newObject() {
                BasicTank basicTank=new BasicTank();
                //Creamos el cuerpo de Box2d
                BodyDef cuerpoDef=new BodyDef();
                cuerpoDef.type = BodyDef.BodyType.DynamicBody;
                Body cuerpo=textureLoader.getMundo().createBody(cuerpoDef);
                cuerpo.setUserData(basicTank); //Añadimos un puntero al cuerpo con la informacion del tanke
                FixtureDef fixtureDef=new FixtureDef();
                CircleShape shape =  new CircleShape(); //El shape tambien puede ser un cuadrado, si eso se camia aki
                shape.setRadius(50f);
                fixtureDef.shape = shape;
                fixtureDef.filter.categoryBits = Enemy.ENEMY_BIT; //su categoria
                fixtureDef.filter.maskBits = (short) (Tower.TORRE_SENSOR_BIT | Proyectile.PROYECTILE_BIT); //con quien choca
                cuerpo.createFixture(fixtureDef);
                shape.dispose();
                //añadimos los datos en el basictank
                basicTank.setCuerpo(cuerpo);
                basicTank.setTextura(textureLoader.obtenerInfanteria());
                basicTank.setAnimacionMuerte(textureLoader.obtenerAnimaInfanteria());
                basicTank.setAnimationTime(0);
                basicTank.setPoolOrigen(niapa); //añadimos el pool de origen para limpiarlo mas adelante
                basicTank.setBarraVida(new BarraVida(textureLoader.obtenerBarraRoja(),textureLoader.obtenerBarraVerde()));
                return basicTank;
            }
        };

        motorTankPool=new Pool<MotorTank>() {
            @Override
            protected MotorTank newObject() {
                MotorTank motorTank=new MotorTank();
                //Creamos el cuerpo de Box2d
                BodyDef cuerpoDef=new BodyDef();
                cuerpoDef.type = BodyDef.BodyType.DynamicBody;
                Body cuerpo=textureLoader.getMundo().createBody(cuerpoDef);
                cuerpo.setUserData(motorTank); //Añadimos un puntero al cuerpo con la informacion del tanke
                FixtureDef fixtureDef=new FixtureDef();
                CircleShape shape =  new CircleShape(); //El shape tambien puede ser un cuadrado, si eso se camia aki
                shape.setRadius(50f);
                fixtureDef.shape = shape;
                fixtureDef.filter.categoryBits = Enemy.ENEMY_BIT; //su categoria
                fixtureDef.filter.maskBits = (short) (Tower.TORRE_SENSOR_BIT | Proyectile.PROYECTILE_BIT); //con quien choca
                cuerpo.createFixture(fixtureDef);
                shape.dispose();
                //añadimos los datos en el basictank
                motorTank.setCuerpo(cuerpo);
                motorTank.setTextura(textureLoader.obtenerSprinter());
                motorTank.setAnimacionMuerte(textureLoader.obtenerAnimaSprinter());
                motorTank.setAnimationTime(0);
                motorTank.setPoolOrigen(niapa); //añadimos el pool de origen para limpiarlo mas adelante
                motorTank.setBarraVida(new BarraVida(textureLoader.obtenerBarraRoja(),textureLoader.obtenerBarraVerde()));
                return motorTank;
            }
        };

        heavyTankPool=new Pool<HeavyTank>() {
            @Override
            protected HeavyTank newObject() {
                HeavyTank heavyTank=new HeavyTank();
                //Creamos el cuerpo de Box2d
                BodyDef cuerpoDef=new BodyDef();
                cuerpoDef.type = BodyDef.BodyType.DynamicBody;
                Body cuerpo=textureLoader.getMundo().createBody(cuerpoDef);
                cuerpo.setUserData(heavyTank); //Añadimos un puntero al cuerpo con la informacion del tanke
                FixtureDef fixtureDef=new FixtureDef();
                CircleShape shape =  new CircleShape(); //El shape tambien puede ser un cuadrado, si eso se camia aki
                shape.setRadius(50f);
                fixtureDef.shape = shape;
                fixtureDef.filter.categoryBits = Enemy.ENEMY_BIT; //su categoria
                fixtureDef.filter.maskBits = (short) (Tower.TORRE_SENSOR_BIT | Proyectile.PROYECTILE_BIT); //con quien choca
                cuerpo.createFixture(fixtureDef);
                shape.dispose();
                //añadimos los datos en el basictank
                heavyTank.setCuerpo(cuerpo);
                heavyTank.setTextura(textureLoader.obtenerCamion());
                heavyTank.setAnimacionMuerte(textureLoader.obtenerAnimaCamion());
                heavyTank.setAnimationTime(0);
                heavyTank.setPoolOrigen(niapa); //añadimos el pool de origen para limpiarlo mas adelante
                heavyTank.setBarraVida(new BarraVida(textureLoader.obtenerBarraRoja(),textureLoader.obtenerBarraVerde()));
                return heavyTank;
            }
        };
    }

    public BasicTank obtenerTankeBasico(/*int nivel //Por si queremos que sean mas fuertes*/){
        //Añadimos los datos al tanke. Ojo, solo añadimos aquellos que se resetean
        BasicTank aux=basicTankPool.obtain();
        aux.setRuta(ruta); //Ciudadooooo NO HAY QUE MODIFICAR EL ARRAY o se modificaran TODAS las rutas de TODOS los enemigos
        aux.setVida(100);
        aux.setVidaMaxima(101);
        aux.setVelocidadM(70);
        aux.setViva(true);
        aux.setPosicion(ruta.get(0));
        aux.setPosicionEnRuta(1); //qieremos ir al 2º punto, el 1º es el origen!
        enemies.add(aux); //Se añade al array de enemigos
        aux.setMoney(50); //Dinero que  deja al morir
        textureLoader.getEscenario().addActor(aux); //lo añadimos al stage para que se dibuje
        return aux;
    }

    public HeavyTank obtenerTankePesado(/*int nivel //Por si queremos que sean mas fuertes*/){
        //Añadimos los datos al tanke. Ojo, solo añadimos aquellos que se resetean
        HeavyTank aux = heavyTankPool.obtain();
        aux.setRuta(ruta); //Ciudadooooo NO HAY QUE MODIFICAR EL ARRAY o se modificaran TODAS las rutas de TODOS los enemigos
        aux.setVida(250);
        aux.setVidaMaxima(251);
        aux.setVelocidadM(30);
        aux.setViva(true);
        aux.setPosicion(ruta.get(0));
        aux.setPosicionEnRuta(1); //qieremos ir al 2º punto, el 1º es el origen!
        enemies.add(aux); //Se añade al array de enemigos
        aux.setMoney(80); //Dinero que  deja al morir
        textureLoader.getEscenario().addActor(aux); //lo añadimos al stage para que se dibuje
        return aux;
    }

    public MotorTank obtenerTankeMotor(/*int nivel //Por si queremos que sean mas fuertes*/){
        //Añadimos los datos al tanke. Ojo, solo añadimos aquellos que se resetean
        MotorTank aux = motorTankPool.obtain();
        aux.setRuta(this.ruta); //Ciudadooooo NO HAY QUE MODIFICAR EL ARRAY o se modificaran TODAS las rutas de TODOS los enemigos
        aux.setVida(50);
        aux.setVidaMaxima(51);
        aux.setVelocidadM(120);
        aux.setViva(true);
        aux.setPosicion(this.ruta.get(0));
        aux.setPosicionEnRuta(1); //qieremos ir al 2º punto, el 1º es el origen!
        enemies.add(aux); //Se añade al array de enemigos
        aux.setMoney(20); //Dinero que  deja al morir
        textureLoader.getEscenario().addActor(aux); //lo añadimos al stage para que se dibuje
        return aux;
    }

    @Override
    public void remove(Enemy freeObject) {
        enemies.removeValue(freeObject, true); //Lo quitamos del array
        if (freeObject instanceof BasicTank){ //es un tanke basico, limpiamos de su pool
            basicTankPool.free((BasicTank) freeObject);
        }
        if (freeObject instanceof HeavyTank){ //es un tanke basico, limpiamos de su pool
            heavyTankPool.free((HeavyTank) freeObject);
        }
        if (freeObject instanceof MotorTank){ //es un tanke basico, limpiamos de su pool
            motorTankPool.free((MotorTank) freeObject);
        }
        //Hacer esto con cada nueva clase de enemigo que se cree

    }
}
