package com.navi.backend.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.*;

public class Handler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Aquí implementa la lógica para manejar la solicitud
        String metodo = exchange.getRequestMethod();
        if (metodo.equals("GET")) {
            // Procesar solicitud GET
            String parametros = exchange.getRequestURI().getQuery();
            System.out.println("Parámetros GET: " + parametros);
            enviarRespuesta(exchange, "Respuesta a solicitud GET");
        }
        else if ("POST".equals(exchange.getRequestMethod())) {
            // Leer el XML enviado por el cliente
            InputStream is = exchange.getRequestBody();
            StringBuilder xmlBuilder = new StringBuilder();
            int i;
            while ((i = is.read()) != -1) {
                xmlBuilder.append((char) i);
            }
            String xml = xmlBuilder.toString();
            is.close();

            // Procesar el XML (aquí puedes implementar tu lógica específica)
            String respuesta = "XML recibido y procesado correctamente";
            // Enviar la respuesta al cliente
            enviarRespuesta(exchange, respuesta);

        } else {
            // Método no permitido
            exchange.sendResponseHeaders(405, -1);
        }
    }

    private void enviarRespuesta(HttpExchange exchange, String mensaje) throws IOException {
        enviarRespuesta(exchange, mensaje, 200);
    }

    private void enviarRespuesta(HttpExchange exchange, String mensaje, int codigoRespuesta) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "text/plain");
        exchange.sendResponseHeaders(codigoRespuesta, mensaje.length());
        OutputStream os = exchange.getResponseBody();
        os.write(mensaje.getBytes());
        os.close();
    }
}
