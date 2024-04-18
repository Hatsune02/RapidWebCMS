package com.navi.backend.webController;

import java.io.*;

public class ApacheController {
    private static final String IP = "192.168.100.226";
    public static void createSite(String site){
        String pathConfig = "/etc/apache2/sites-available/"+site+".conf";
        String config = "<VirtualHost *:80>\n" +
                "    ServerName "+site+".com\n" +
                "    ServerAdmin webmaster@localhost\n" +
                "    ServerAlias www."+site+".com\n" +
                "    DocumentRoot /var/www/html/"+site+"\n" +
                "    DirectoryIndex index.html\n" +
                "</VirtualHost>";
        try {
            File file = new File(pathConfig);
            FileWriter writer = new FileWriter(file);
            writer.write(config);
            writer.close();
            System.out.println("Archivo creado correctamente en: " + pathConfig);
        } catch (IOException e) {
            System.err.println("Error al crear el archivo: " + e.getMessage());
        }

        try {
            String name = site + ".conf";
            ProcessBuilder pb = new ProcessBuilder("a2ensite", name);
            Process process = pb.start();
            process.waitFor();
            ProcessBuilder pb2 = new ProcessBuilder("service", "apache2", "reload");
            Process process2 = pb2.start();
            process2.waitFor();

            System.out.println("Archivo habilitado correctamente: " + site+".conf");

            FileWriter writer = new FileWriter("/etc/hosts", true);
            writer.write("\n" + IP + "\t" + "www."+site+".com");
            writer.close();

            System.out.println("Se agregó la entrada al archivo hosts.");
        } catch (IOException | InterruptedException e) {
            System.err.println("Error al habilitar el archivo: " + e.getMessage());
        }
    }
}
