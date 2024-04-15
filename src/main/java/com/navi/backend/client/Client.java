package com.navi.backend.client;
import java.io.*;
import java.net.*;
public class Client {

    public boolean sendXML(String xml) throws IOException {
        try {
            URL url = new URL("http://localhost:8000"); // URL del servidor
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST"); // Método de la solicitud
            connection.setRequestProperty("Content-Type", "text/xml"); // Tipo de contenido
            connection.setDoOutput(true);

            // Enviar el XML al servidor
            OutputStream os = connection.getOutputStream();
            os.write(xml.getBytes());
            os.flush();
            os.close();

            // Verificar la respuesta del servidor
            int responseCode = connection.getResponseCode();
            return responseCode == HttpURLConnection.HTTP_OK;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
