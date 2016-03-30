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
        /*public static final String GET_FOTOS = "getFotosPorUsuario";
        public static final String INSERTAR_FOTO = "insertFoto";
        public static final String ACEPTAR_FOTO = "aceptaFoto";
        public static final String DESCARTAR_FOTO = "descartaFoto";*/
    }

    public com.aro.defenseofatroth.WS.User login( String user_str, String pass_str) {
        com.aro.defenseofatroth.WS.User user = null;
        SoapObject request = new SoapObject (NAMESPACE, METHOD_NAMES.LOGIN);
        request.addProperty("nombre", user_str);
        request.addProperty("pass", pass_str);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = false;
        envelope.setOutputSoapObject(request);

        HttpTransportSE transporte = new HttpTransportSE(URL_SERVER);
        try {
            transporte.call(SOAP_ACTION_PREFIX + METHOD_NAMES.LOGIN, envelope);
            String json = envelope.getResponse().toString();
            com.aro.defenseofatroth.WS.ResponseWS response = new Gson().fromJson(json, com.aro.defenseofatroth.WS.ResponseWS.class);
            user = new Gson().fromJson(response.getData().toString(), com.aro.defenseofatroth.WS.User.class);
        } catch (Exception e) {System.out.println(e.getMessage());}
        return user;
    }

    public com.aro.defenseofatroth.WS.ResponseWS createUser(String user_str, String pass_str, String mail_str) {
        com.aro.defenseofatroth.WS.ResponseWS response=null;
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
            response = new Gson().fromJson(json, com.aro.defenseofatroth.WS.ResponseWS.class);
            //TODO CAMBIAR EL PARSEO DE DATA
            //user = new Gson().fromJson(response.getData().toString(), User.class);
        } catch (Exception e) {}
        return response;
    }
}