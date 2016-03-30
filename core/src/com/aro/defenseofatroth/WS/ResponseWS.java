package com.aro.defenseofatroth.WS;

/**
 * Created by Javier on 30/03/2016.
 */
public class ResponseWS {

    private boolean exito;
    private String error;
    private Object data;

    public boolean getExito() {
        return exito;
    }

    public void setExito(boolean exito) {
        this.exito = exito;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
