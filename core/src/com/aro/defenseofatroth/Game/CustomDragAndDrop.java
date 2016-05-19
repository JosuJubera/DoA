package com.aro.defenseofatroth.Game;

import com.aro.defenseofatroth.MainClass;
import com.aro.defenseofatroth.Screens.Hud;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
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
            if (validTargets.contains(this,true)){ //Es un target valido
                getActor().remove();//Deja de dibujarse
                int tipo=(Integer) payload.getObject(); //Obtenemos el tipo de torre
                towerFactory.obtenerBasicTower(getActor().getX(),getActor().getY()); //Pnemos la torre
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
            payload.setObject(getActor().getUserObject()); //Se establece el mismo ID que el actor
            //TODO que la imagen de la torre corresponda payload.setDragActor(getActor()) podira ir, ambos son image
            //Imagen de la torre
            Image dragging=new Image(MainClass.getManager().get("torre.png", Texture.class));
            payload.setDragActor(dragging);
            //Imagen torre Valida
            Image validPosition=new Image(MainClass.getManager().get("torre.png", Texture.class));
            validPosition.setColor(Color.GREEN);
            payload.setValidDragActor(validPosition);
            //Imagen torre Invalida
            Image inValidPosition=new Image(MainClass.getManager().get("torre.png", Texture.class));
            validPosition.setColor(Color.RED);
            payload.setInvalidDragActor(inValidPosition);
            return payload;
        }
    }
    private DragAndDrop dragAndDrop; //Usado para el draganddrop
    private Array<Image> sources; //Los actores fuente. estos estan en el HUD
    private TowerFactory towerFactory; //Factoria de torres
    private Array<Vector2> posiciones; //Posiciones donde se prodra crear una torre
    private Array<Image> validImageTargets; //Imagenes que representan las zonas disponibles
    private Array<DragAndDrop.Target> validTargets; //Objetivos VALIDOS del drag&drop
    private Stage stage; //Escenario MUNDO para poner los huecos
    private Hud hud; //Atencion!!! Ñapa a la vista!!! Para conjer el oro


    public CustomDragAndDrop(){
        dragAndDrop=new DragAndDrop();
        validImageTargets =new Array<Image>();
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
        //Se crean las imagenes de las posiciones
        for (int i = 0; i < posiciones.size; i++){
            Image target = new Image(MainClass.getManager().get("target.png", Texture.class));
            target.setPosition(posiciones.get(i).x,posiciones.get(i).y);
            validImageTargets.add(target);
            stage.addActor(target);
        }
        //Se crean los sources del drag&drop a partir del HUD
        for (int i = 0; i < sources.size; i++) {
            dragAndDrop.addSource(new CustomSource(sources.get(i)));
        }
        //Se crean los targets del drag&drop a partir de las imagenes
        for (int i = 0; i < validImageTargets.size; i++) {
            CustomTarget aux=new CustomTarget(validImageTargets.get(i));
            validTargets.add(aux); //Para saber si son validos
            dragAndDrop.addTarget(aux);
        }



        validImageTargets.clear();
    }

    public DragAndDrop getDragAndDrop() {
        return dragAndDrop;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
