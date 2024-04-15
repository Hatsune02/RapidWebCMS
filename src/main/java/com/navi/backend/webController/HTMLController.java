package com.navi.backend.webController;
import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;

public class HTMLController {
    public static void createWebSite(String id) {
        File dir = new File("web_sites/"+id);
        if (!dir.exists()) {
            dir.mkdir();
        }

        File index = new File("web_sites/"+ id + "/index.html");
        String contenidoHTML = createHTMLEmpty(id);
        try (FileWriter writer = new FileWriter(index)) {
            writer.write(contenidoHTML);
            System.out.println("Archivo index.html creado en la carpeta del sitio web.");
        } catch (IOException e) {
            System.err.println("Error al crear el archivo index.html: " + e.getMessage());
        }
    }

    public static void createWebPage(String id, String path, String title, String site) {
        File dir = new File("web_sites/"+path);
        if (!dir.exists()) {
            dir.mkdir();
        }
        if(title.isEmpty()) title = site;
        File index = new File("web_sites/"+path+ "/"+id+".html");
        String contenidoHTML = createHTMLEmpty(title);
        try (FileWriter writer = new FileWriter(index)) {
            writer.write(contenidoHTML);
            System.out.println("Archivo index.html creado en la carpeta del sitio web.");
        } catch (IOException e) {
            System.err.println("Error al crear el archivo index.html: " + e.getMessage());
        }
    }

    public static void addComponent(String path, String component) {
        try {
            Path file = Paths.get("web_sites/"+path+".html");
            byte[] contenido = Files.readAllBytes(file);
            String contenidoStr = new String(contenido, StandardCharsets.UTF_8);

            // Encontrar la posición del cierre del tag </body>
            int indexBody = contenidoStr.indexOf("</body>");
            if (indexBody != -1) {
                String newContent = contenidoStr.substring(0, indexBody) + component + contenidoStr.substring(indexBody);

                Files.write(file, newContent.getBytes(StandardCharsets.UTF_8));
            } else {
                System.err.println("Error: no se encontró el tag </body> en el archivo HTML.");
            }
        } catch (IOException e) {
            System.err.println("Error al leer o escribir el archivo HTML: " + e.getMessage());
        }
    }

    private static String createHTMLEmpty(String title) {
        return  "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <title>"+title+"</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "</body>\n" +
                "</html>";
    }
}
