package com.navi.backend.webController;
import com.navi.backend.webController.objs.WComponent;

import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.ArrayList;

public class HTMLController {
    public static void createWebSite(String id) {
        File dir = new File("/var/www/html/"+id);
        if (!dir.exists()) {
            dir.mkdir();
        }
        System.out.println(dir.getAbsolutePath());
        File index = new File("/var/www/html/"+ id + "/index.html");
        String html = createHTMLWebSite(id);
        try (FileWriter writer = new FileWriter(index)) {
            writer.write(html);
            System.out.println("Archivo index.html creado en la carpeta del sitio web.");
        } catch (IOException e) {
            System.err.println("Error al crear el archivo index.html: " + e.getMessage());
        }
        ApacheController.createSite(id);
    }
    public static void createWebPage(String id, String path, String title, String site) {
        File dir = new File("web_sites/"+path);
        if (!dir.exists()) {
            dir.mkdir();
        }
        if(title.isEmpty()) title = site;
        File index = new File("/var/www/html/"+path+ "/"+id+".html");
        String contenidoHTML = createHTMLEmpty(title);
        try (FileWriter writer = new FileWriter(index)) {
            writer.write(contenidoHTML);
            System.out.println("Archivo index.html creado en la carpeta de la pagina web.");
        } catch (IOException e) {
            System.err.println("Error al crear el archivo index.html: " + e.getMessage());
        }
    }
    public static void editWebPage(String path, String title, String site) {
        try {
            Path file = Paths.get("web_sites/"+path+".html");
            byte[] contenido = Files.readAllBytes(file);
            String contenidoStr = new String(contenido, StandardCharsets.UTF_8);

            if(title.isEmpty()) title = site;
            int indexTituloInicio = contenidoStr.indexOf("<title>");
            int indexTituloFin = contenidoStr.indexOf("</title>", indexTituloInicio);

            // Reemplazar el contenido del título con el nuevo título
            if (indexTituloInicio != -1 && indexTituloFin != -1) {
                String nuevoContenido = contenidoStr.substring(0, indexTituloInicio + 7) +
                        title + contenidoStr.substring(indexTituloFin);

                Files.write(file, nuevoContenido.getBytes(StandardCharsets.UTF_8));
            } else {
                System.err.println("No se encontró la etiqueta <title> en el archivo HTML.");
            }
        } catch (IOException e) {
            System.err.println("Error al leer o escribir el archivo HTML: " + e.getMessage());
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
                String newContent = contenidoStr.substring(0, indexBody) +"  "+ component +"\n"+ contenidoStr.substring(indexBody);

                Files.write(file, newContent.getBytes(StandardCharsets.UTF_8));
            } else {
                System.err.println("Error: no se encontró el tag </body> en el archivo HTML.");
            }
        } catch (IOException e) {
            System.err.println("Error al leer o escribir el archivo HTML: " + e.getMessage());
        }
    }
    public static void editComponent(String pageID, String path, ArrayList<WComponent> components) {
        try {
            Path file = Paths.get("web_sites/"+path+".html");
            byte[] contenido = Files.readAllBytes(file);
            String contenidoStr = new String(contenido, StandardCharsets.UTF_8);

            int indexBodyInicio = contenidoStr.indexOf("<body>");
            int indexBodyFin = contenidoStr.indexOf("</body>");
            StringBuilder newBody = new StringBuilder(contenidoStr.substring(0, indexBodyInicio + 6)).append("\n");
            for (var comp : components) {
                if(comp.getPage().equals(pageID)) newBody.append("  ").append(comp.getLabelHTML());
            }
            newBody.append("\n").append(contenidoStr.substring(indexBodyFin));

            Files.write(file, newBody.toString().getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            System.err.println("Error al leer o escribir el archivo HTML: " + e.getMessage());
        }
    }

    public static void deleteWebPage(String path) {
        File file = new File("web_sites/"+path);
        deleteFile(file);
    }
    public static void deleteFile(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    deleteFile(f);
                }
            }
        }
        if (!file.delete()) {
            System.err.println("No se pudo eliminar el archivo o carpeta: " + file.getAbsolutePath());
        }
    }

    private static String createHTMLEmpty(String title) {
        return  "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <title>"+title+"</title>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            font-family: Arial, sans-serif;\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "            background-color: #f0f0f0;\n" +
                "        }\n" +
                "        header {\n" +
                "            background-color: #333;\n" +
                "            color: #fff;\n" +
                "            padding: 10px 20px;\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "        section {\n" +
                "            padding: 20px;\n" +
                "            font-size: 24px;\n" +
                "            color: #333;\n" +
                "        }\n" +
                "        footer {\n" +
                "            background-color: #333;\n" +
                "            color: #fff;\n" +
                "            text-align: center;\n" +
                "            padding: 10px 0;\n" +
                "            position: absolute;\n" +
                "            bottom: 0;\n" +
                "            width: 100%;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "</body>\n" +
                "</html>";
    }
    private static String createHTMLWebSite(String title) {
        return  "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>"+title+"</title>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            font-family: Arial, sans-serif;\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "            background-color: #f0f0f0;\n" +
                "        }\n" +
                "        header {\n" +
                "            background-color: #333;\n" +
                "            color: #fff;\n" +
                "            padding: 10px 20px;\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "        section {\n" +
                "            padding: 20px;\n" +
                "            font-size: 24px;\n" +
                "            color: #333;\n" +
                "        }\n" +
                "        footer {\n" +
                "            background-color: #333;\n" +
                "            color: #fff;\n" +
                "            text-align: center;\n" +
                "            padding: 10px 0;\n" +
                "            position: absolute;\n" +
                "            bottom: 0;\n" +
                "            width: 100%;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <header>\n" +
                "        <h1>Bienvenido a nuestro sitio web: "+title+"</h1>\n" +
                "    </header>\n" +
                "    <section>\n" +
                "        <p>¡Gracias por visitarnos! Aquí encontrarás información interesante sobre nuestro servicio.</p>\n" +
                "    </section>\n" +
                "    <footer>\n" +
                "        <p>Derechos reservados &copy; 2024</p>\n" +
                "    </footer>\n" +
                "</body>\n" +
                "</html>";
    }
}
