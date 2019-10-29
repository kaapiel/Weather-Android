package br.com.cielo.weather.service;

import org.apache.commons.codec.binary.Base64;

public class BuildAuthBase64 {

    public String getAuthBase64(String user, String pass) {
        String authString = user + ":" + pass;
        byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
        return new String(authEncBytes);
    }

}