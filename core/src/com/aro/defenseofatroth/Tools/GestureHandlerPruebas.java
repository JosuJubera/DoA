package com.aro.defenseofatroth.Tools;

import com.aro.defenseofatroth.Game.Tower;
import com.aro.defenseofatroth.Game.TowerFactory;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;

import java.util.Iterator;

/**
 * Esta clase la tengo para las pruebas del lvl 1, que el otro eta modificau y no va
 * Created by Sergio on 09/04/2016.
 */
public class GestureHandlerPruebas implements GestureDetector.GestureListener {

    private static final float CAMERA_ZOOM_MAX = 1.0f; // Lo lejos que esta el zoom
    private static final float CAMERA_ZOOM_MIN = 0.3f; // Lo creca que esta el zoom

    private OrthographicCamera camera;
    float velX, velY;
    boolean flinging = false;
    float initialScale = 1;
    TowerFactory towerFactory; //Ñapa inside!

    public void setTowerFactory(TowerFactory towerFactory) {
        this.towerFactory = towerFactory;
    }

    public GestureHandlerPruebas(OrthographicCamera camera){
        this.camera=camera;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        //addMessage("touchDown: x(" + x + ") y(" + y + ") pointer(" + pointer + ") button(" + button +")");
        flinging = false;
        initialScale = camera.zoom;
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        if (towerFactory!=null){
            Iterator<Tower> it=towerFactory.getTowers().iterator();
            while (it.hasNext()){
                Tower aux=it.next();
                aux.ocultar();
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        //addMessage("fling: velX(" + velocityX + ") velY(" + velocityY + ") button(" + button +")");
        flinging = true;
        velX = camera.zoom * velocityX * 0.5f;
        velY = camera.zoom * velocityY * 0.5f;
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        //translate mueve la camara segun esas coordenadas (igual que camera.x+=x)

        if (camera.position.x - deltaX * camera.zoom <= (-50 / 7 * camera.zoom * camera.zoom - 8885 / 7 * camera.zoom + 17895 / 7)         // max x
                && (camera.position.y + deltaY * camera.zoom) <= (-50 / 7 * camera.zoom * camera.zoom - 4965/7 * camera.zoom + 10055 / 7)  // max y
                && (camera.position.x - deltaX * camera.zoom) >= (50 / 7 * camera.zoom * camera.zoom + 8885 / 7 * camera.zoom + 25 / 7)    // min x
                && (camera.position.y + deltaY * camera.zoom) >= (50 / 7 * camera.zoom * camera.zoom + 4965 / 7 * camera.zoom + 25 / 7)){  // min y

            camera.position.add(-deltaX * camera.zoom, deltaY * camera.zoom, 0);
        }
//			System.out.println("pan" + camera.position + " zoom " + camera.zoom);
        return true;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        //addMessage("panStop: x(" + x + ") y(" + y + ") pointer(" + pointer + ") button(" + button +")");
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {

        float zoomPrevio = camera.zoom;

        float ratio = initialDistance / distance;
        float nextZoom = initialScale * ratio;
        if ((nextZoom <= CAMERA_ZOOM_MAX) && (nextZoom >= CAMERA_ZOOM_MIN)) {
            if (nextZoom > zoomPrevio){

                if ((camera.position.x < 1280) && (camera.position.y < 720)) {
                    if (camera.position.x - camera.viewportWidth * nextZoom/2 < 0) {

                        camera.position.set((50 / 7 * nextZoom * nextZoom + 8885 / 7 * nextZoom + 25 / 7),
                                camera.position.y, 0);
                    }
                    if (camera.position.y - camera.viewportHeight * nextZoom/2 < 0) {

                        camera.position.set(camera.position.x,
                                (50 / 7 * nextZoom * nextZoom + 4965 / 7 * nextZoom + 25 / 7), 0);
                    }

                }

                if ((camera.position.x < 1280) && (camera.position.y > 720)) {
                    if (camera.position.x - camera.viewportWidth * nextZoom/2 < 0) {

                        camera.position.set((50 / 7 * nextZoom * nextZoom + 8885 / 7 * nextZoom + 25 / 7),
                                camera.position.y, 0);
                    }
                    if (camera.position.y + camera.viewportHeight * nextZoom/2 > 1220) {

                        camera.position.set(camera.position.x,
                                (-50 / 7 * nextZoom * nextZoom - 4965/7 * nextZoom + 10055 / 7), 0);
                    }
                }

                if ((camera.position.x > 1280) && (camera.position.y < 720)) {
                    if (camera.position.x + camera.viewportWidth * nextZoom/2 > 2175 ) {

                        camera.position.set((-50 / 7 * nextZoom * nextZoom - 8885 / 7 * nextZoom + 17895 / 7),
                                camera.position.y, 0);
                    }
                    if (camera.position.y - camera.viewportHeight * nextZoom/2 < 0) {

                        camera.position.set(camera.position.x,
                                (50 / 7 * nextZoom * nextZoom + 4965 / 7 * nextZoom + 25 / 7), 0);
                    }
                }

                if ((camera.position.x > 1280) && (camera.position.y > 720)) {
                    if (camera.position.x + camera.viewportWidth * nextZoom/2 > 2175) {

                        camera.position.set((-50 / 7 * nextZoom * nextZoom - 8885 / 7 * nextZoom + 17895 / 7),
                                camera.position.y, 0);
                    }
                    if (camera.position.y + camera.viewportHeight * nextZoom/2 > 1220){

                        camera.position.set(camera.position.x,
                                (-50 / 7 * nextZoom * nextZoom - 4965 / 7 * nextZoom + 10055 / 7), 0);
                    }
                }
            }
            camera.zoom = nextZoom;
        }

//			System.out.println((camera.position) + " zoom " + camera.zoom);
        return true;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        //addMessage("pinch: initialP1(" + initialPointer1 + ") initialP2(" + initialPointer2 + ") p1(" + pointer1 + ") p2(" + pointer2 +")");
        return false;
    }
}
