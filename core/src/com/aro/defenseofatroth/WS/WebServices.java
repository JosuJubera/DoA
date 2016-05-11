package com.aro.defenseofatroth.WS;

/**
 * Created by Javier on 30/03/2016.
 */

import com.google.gson.Gson;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
public class WebServices {

    public final String URL_SERVER = "http://ec2-52-24-183-222.us-west-2.compute.amazonaws.com/WS.php";
    public final String NAMESPACE = "http://ec2-52-24-183-222.us-west-2.compute.amazonaws.com/WS.php";
    private static final String SOAP_ACTION_PREFIX ="urn:defenseAro#";

    private static class METHOD_NAMES{
        public static final String LOGIN = "login";
        public static final String CREAR_USUARIO = "crearUsuario";
        public static final String UPDATE_SCORE = "updateScore";
        public static final String GET_RANKING = "getRanking";
        /*public static final String GET_FOTOS = "getFotosPorUsuario";
        public static final String INSERTAR_FOTO = "insertFoto";
        public static final String ACEPTAR_FOTO = "aceptaFoto";
        public static final String DESCARTAR_FOTO = "descartaFoto";*/
    }

    public User login( String mail_str, String pass_str) {
        User user = null;
        SoapObject request = new SoapObject (NAMESPACE, METHOD_NAMES.LOGIN);
        request.addProperty("mail", mail_str);
        request.addProperty("pass", pass_str);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = false;
        envelope.setOutputSoapObject(request);

        HttpTransportSE transporte = new HttpTransportSE(URL_SERVER);
        try {
            transporte.call(SOAP_ACTION_PREFIX + METHOD_NAMES.LOGIN, envelope);
            String json = envelope.getResponse().toString();
            ResponseWS response = new Gson().fromJson(json, ResponseWS.class);
            if(response.getExito()==true) {
                user = new Gson().fromJson(response.getData().toString(), User.class);
            }
        } catch (Exception e) {System.out.println(e.getMessage());}
        return user;
    }

    public ResponseWS createUser(String user_str, String pass_str, String mail_str) {
        ResponseWS response=null;
        SoapObject request = new SoapObject (NAMESPACE, METHOD_NAMES.CREAR_USUARIO);
        request.addProperty("nombre", user_str);
        request.addProperty("pass", pass_str);
        request.addProperty("mail", mail_str);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = false;
        envelope.setOutputSoapObject(request);

        HttpTransportSE transporte = new HttpTransportSE(URL_SERVER);
        try {
            transporte.call(SOAP_ACTION_PREFIX + METHOD_NAMES.CREAR_USUARIO, envelope);
            String json = envelope.getResponse().toString();
            response = new Gson().fromJson(json, ResponseWS.class);
            //TODO CAMBIAR EL PARSEO DE DATA
            //user = new Gson().fromJson(response.getData().toString(), User.class);
        } catch (Exception e) {}
        return response;
    }

    public ResponseWS updateScore(String mail_str, int score){
        ResponseWS response=null;
        SoapObject request = new SoapObject(NAMESPACE,METHOD_NAMES.UPDATE_SCORE);
        request.addProperty("mail",mail_str);
        request.addProperty("score",score);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet=false;
        envelope.setOutputSoapObject(request);

        HttpTransportSE transporte = new HttpTransportSE(URL_SERVER);
        try{
            transporte.call(SOAP_ACTION_PREFIX+METHOD_NAMES.UPDATE_SCORE,envelope);
            String json = envelope.getResponse().toString();
            response = new Gson().fromJson(json, ResponseWS.class);
        }catch(Exception e){}
        return response;
    }

    public ResponseWS getRanking(int limit){
        ResponseWS response=null;
        SoapObject request = new SoapObject(NAMESPACE,METHOD_NAMES.GET_RANKING);
        request.addProperty("limit",limit);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet=false;
        envelope.setOutputSoapObject(request);
        HttpTransportSE transporte = new HttpTransportSE(URL_SERVER);
        try{
            transporte.call(SOAP_ACTION_PREFIX+METHOD_NAMES.GET_RANKING,envelope);
            String json = envelope.getResponse().toString();
            response=new Gson().fromJson(json,ResponseWS.class);
        }catch (Exception e){}
        return response;
    }
}
