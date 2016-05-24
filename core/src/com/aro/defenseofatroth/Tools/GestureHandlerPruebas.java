package com.aro.defenseofatroth.Tools;

import com.aro.defenseofatroth.Levels.Level1;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Esta clase la tengo para las pruebas del lvl 1, que el otro eta modificau y no va
 * Created by Sergio on 09/04/2016.
 */
public class GestureHandlerPruebas implements GestureDetector.GestureListener {

    private static final float CAMERA_ZOOM_MAX = 3.0f; // Lo lejos que esta el zoom
    private static final float CAMERA_ZOOM_MIN = 0.3f; // Lo creca que esta el zoom

    private OrthographicCamera camera;
    float velX, velY;
    boolean flinging = false;
    float initialScale = 1;

    public GestureHandlerPruebas(/*Vector2 vec,*/OrthographicCamera camera){

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
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        //atencion, ñapa gorda, tapense los ojos. Esta ñapa puede herir su sensibilidad
        Vector3 pantallacorr=new Vector3(x,y,0);
        pantallacorr=camera.unproject(pantallacorr);

        Level1.niapa.setDestino(new Vector2(pantallacorr.x,pantallacorr.y));
        return true;
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

/*        if (camera.position.x - deltaX * camera.zoom <= (200 / 189 * camera.zoom * camera.zoom - 121760 / 189 * camera.zoom + 121160 / 63)              // 1730
                && (camera.position.y + deltaY * camera.zoom) <= (-200 / 189 * camera.zoom * camera.zoom - 67240 / 189 * camera.zoom + 67840 / 63)      // 970
                && (camera.position.x - deltaX * camera.zoom) >= (-200 / 189 * camera.zoom * camera.zoom + 121760 / 189 * camera.zoom - 121160 / 63)    // -1730
                && (camera.position.y + deltaY * camera.zoom) >= (200 / 189 * camera.zoom * camera.zoom + 67240 / 189 * camera.zoom - 67840 / 63)){     // -970
*/
            camera.position.add(-deltaX * camera.zoom, deltaY * camera.zoom, 0);
//        }
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

                if ((camera.position.x < 0) && (camera.position.y < 0)) {
                    if (camera.position.x - camera.viewportWidth * nextZoom/2 < -1920) {

                        camera.position.set((-200 / 189 * nextZoom * nextZoom + 121760 / 189 * nextZoom - 121160 / 63),
                                camera.position.y, 0);
                    }
                    if (camera.position.y - camera.viewportHeight * nextZoom/2 < -1080) {

                        camera.position.set(camera.position.x,
                                (200 / 189 * nextZoom * nextZoom + 67240 / 189 * nextZoom - 67840 / 63), 0);
                    }

                }

                if ((camera.position.x < 0) && (camera.position.y > 0)) {
                    if (camera.position.x - camera.viewportWidth * nextZoom/2 < -1920) {

                        camera.position.set((-200 / 189 * nextZoom * nextZoom + 121760 / 189 * nextZoom - 121160 / 63),
                                camera.position.y, 0);
                    }
                    if (camera.position.y + camera.viewportHeight * nextZoom/2 > 1080) {

                        camera.position.set(camera.position.x,
                                (-200 / 189 * nextZoom * nextZoom - 67240 / 189 * nextZoom + 67840 / 63), 0);
                    }
                }

                if ((camera.position.x > 0) && (camera.position.y < 0)) {
                    if (camera.position.x + camera.viewportWidth * nextZoom/2 > 1920) {

                        camera.position.set((200 / 189 * nextZoom * nextZoom - 121760 / 189 * nextZoom + 121160 / 63),
                                camera.position.y, 0);
                    }
                    if (camera.position.y - camera.viewportHeight * nextZoom/2 < -1080) {

                        camera.position.set(camera.position.x,
                                (200 / 189 * nextZoom * nextZoom + 67240 / 189 * nextZoom - 67840 / 63), 0);
                    }
                }

                if ((camera.position.x > 0) && (camera.position.y > 0)) {
                    if (camera.position.x + camera.viewportWidth * nextZoom/2 > 1920) {

                        camera.position.set((200 / 189 * nextZoom * nextZoom - 121760 / 189 * nextZoom + 121160 / 63),
                                camera.position.y, 0);
                    }
                    if (camera.position.y + camera.viewportHeight * nextZoom/2 > 1080){

                        camera.position.set(camera.position.x,
                                (-200 / 189 * nextZoom * nextZoom - 67240 / 189 * nextZoom + 67840 / 63), 0);
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
