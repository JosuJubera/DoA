package com.aro.defenseofatroth.Game;

import com.aro.defenseofatroth.MainClass;
import com.aro.defenseofatroth.Screens.Hud;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.GameDragAndDrop;
import com.badlogic.gdx.utils.Array;

/**
 * Esta clase lleva el control del drag&drop del juego.
 * Created by Sergio on 19/05/2016.
 */
public class CustomDragAndDrop {
    //Clases que necesita el drag&drop
    private class CustomTarget extends DragAndDrop.Target{

        public CustomTarget(Actor actor) {
            super(actor);
        }

        @Override
        public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
            getActor().setColor(Color.GREEN);
            return true;
        }
        @Override
        public void reset(DragAndDrop.Source source, DragAndDrop.Payload payload) {
            getActor().setColor(Color.WHITE);
        }
        @Override
        public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
            Gdx.app.log("Drag&Drop", "Se ha dropeado en in sitio valido. Coors: "+x+" "+y);
            if (validTargets.contains(this,true)){ //Es un target valido
                getActor().remove();//Deja de dibujarse
                Integer tipo=(Integer) payload.getObject(); //Obtenemos el tipo de torre
                Actor actor=getActor();
                towerFactory.obtenerBasicTower(actor.getX()+actor.getWidth()*0.5f,actor.getY()+actor.getHeight()*0.5f); //Pnemos la torre
                dragAndDrop.removeTarget(this); //Creo k no hace falta, pero bueno
            }

        }
    }
    private class CustomSource extends DragAndDrop.Source{

        public CustomSource(Actor actor) {
            super(actor);
        }

        @Override
        public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
            DragAndDrop.Payload payload = new DragAndDrop.Payload();
            Integer id=(Integer) getActor().getUserObject(); //Se obtiene el tipo de torre
            payload.setObject(id); //Se establece el mismo ID que el actor
            //Imagen de la torre TODO poner la imagen segun el ID
            Image dragging=new Image(MainClass.getManager().get("torre.png", Texture.class));
            dragging.setScale(0.5f);
            payload.setDragActor(dragging);
            //Imagen torre Valida
            Image validPosition=new Image(MainClass.getManager().get("torre.png", Texture.class));
            validPosition.setScale(0.5f);
            validPosition.setColor(Color.GREEN);
            payload.setValidDragActor(validPosition);
            //Imagen torre Invalida
            Image inValidPosition=new Image(MainClass.getManager().get("torre.png", Texture.class));
            inValidPosition.setScale(0.5f);
            inValidPosition.setColor(Color.RED);
            payload.setInvalidDragActor(inValidPosition);
            return payload;
        }
    }
    private DragAndDrop dragAndDrop; //Usado para el draganddrop
    private Array<Image> sources; //Los actores fuente. estos estan en el HUD
    private TowerFactory towerFactory; //Factoria de torres
    private Array<Vector2> posiciones; //Posiciones donde se prodra crear una torre
    private Array<DragAndDrop.Target> validTargets; //Objetivos VALIDOS del drag&drop
    private Stage stage; //Escenario MUNDO para poner los huecos
    private Hud hud; //Atencion!!! Ñapa a la vista!!! Para conjer el oro


    public CustomDragAndDrop(){
        dragAndDrop=new GameDragAndDrop();
        validTargets=new Array<DragAndDrop.Target>();
    }


    public void setHud(Hud hud) {
        this.hud = hud;
    }

    public void setPosiciones(Array<Vector2> posiciones) {
        this.posiciones = posiciones;
    }

    public void setSources(Array<Image> options) {
        this.sources = options;
    }

    public void setTowerFactory(TowerFactory towerFactory) {
        this.towerFactory = towerFactory;
    }

    public void bind(){

        //Se crean los sources del drag&drop a partir del HUD
        for (int i = 0; i < sources.size; i++) {
            dragAndDrop.addSource(new CustomSource(sources.get(i)));
        }

        for (int i = 0; i < posiciones.size; i++) {
            //Se crean las imagenes de las posiciones
            Image target = new Image(TextureLoader.getInstance().obtenerTarget());
            target.setPosition(posiciones.get(i).x,posiciones.get(i).y);
            stage.addActor(target);
            //Se crean los targets del drag&drop a partir de las imagenes
            CustomTarget aux=new CustomTarget(target);
            validTargets.add(aux); //Para saber si son validos
            dragAndDrop.addTarget(aux);
        }
    }

    public DragAndDrop getDragAndDrop() {
        return dragAndDrop;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
