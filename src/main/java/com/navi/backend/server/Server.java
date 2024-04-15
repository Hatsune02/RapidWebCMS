package com.navi.backend.server;

import com.navi.backend.client.Client;
import com.sun.net.httpserver.HttpServer;

import javax.swing.*;
import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {

    public static void main(String[] args) {
        try {
            // Crear servidor en el puerto 80
            HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
            // Asignar manejador para las solicitudes
            server.createContext("/", new Handler());
            // Iniciar servidor
            server.start();
            System.out.println("Servidor iniciado en el puerto 8000...");

            String expr = """
                
                <accion nombre="AGREGAR_COMPONENTE">
                	<parametros>
                          		<parametro nombre="ID">
                              		[ab]
                        		</parametro>
                          		<parametro nombre="PAGINA">
                              		[CENTRAR]
                        		</parametro>
                          		<parametro nombre="CLASE">
                              		[TITULO]
                        		</parametro>
                	</parametros>
                 	<atributos>
                    		<atributo nombre="TEXTO">
                			[Este es el texto que aparece en el titulo :) ]
                		</atributo>
                            <atributo nombre="COLOR">
                			[#5A5A5A]
                		</atributo>
                	</atributos>
                </accion>
                
                """;

        } catch (IOException e) {
            System.err.println("Error al iniciar el servidor: " + e.getMessage());
        }
    }
}