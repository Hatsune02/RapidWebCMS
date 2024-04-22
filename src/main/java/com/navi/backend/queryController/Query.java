package com.navi.backend.queryController;

import com.navi.backend.webController.Actions;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Query {

    public static void consultVisitsSites(ArrayList<SitePageCounter> paths){
        System.out.println("consultVisitsSites");
        String logFilePath = "/var/log/apache2/sites-log.log"; // Ruta al archivo de log de Apache

        try (BufferedReader br = new BufferedReader(new FileReader(logFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("GET")) {
                    String[] fields = line.split(" ");
                    String site = fields[0].substring(4, fields[0].length()-4);

                    for (var p: paths){
                        if(p.getName().equals(site)){
                            p.plus();
                            break;
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de log: " + e.getMessage());
        }
    }
    public static void consultVisitsPages(ArrayList<SitePageCounter> paths){
        System.out.println("consultVisitsPages");
        String logFilePath = "/var/log/apache2/sites-log.log"; // Ruta al archivo de log de Apache

        try (BufferedReader br = new BufferedReader(new FileReader(logFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("GET")) {
                    String[] fields = line.split(" ");

                    String site = fields[0].substring(4, fields[0].length()-4);
                    String pages = fields[2];
                    if(pages.endsWith("/")) pages = pages.substring(0, pages.length()-1);
                    String path = site + pages;
                    for (var p: paths){
                        if(p.getName().equals(path)){
                            p.plus();
                            break;
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de log: " + e.getMessage());
        }
    }
    public static void consultPopularPages(String path, JTextArea txtArea){
        System.out.println("consultPopularPages");
        String logFilePath = "/var/log/apache2/sites-log.log"; // Ruta al archivo de log de Apache
        ArrayList<SitePageCounter> paths = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(logFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("GET")) {
                    String[] fields = line.split(" ");
                    String site = fields[0].substring(4, fields[0].length()-4);
                    String pages = fields[2].substring(1);
                    if(pages.endsWith("/")) pages = pages.substring(0, pages.length()-1);
                    if(!pages.isEmpty() && !pages.equals("favicon.ico")){
                        if(site.equals(path)){
                            boolean create = true;
                            for(var p: paths){
                                if(p.getName().equals(pages)){
                                    p.plus();
                                    create = false;
                                    break;
                                }
                            }
                            if(create) {
                                paths.add(new SitePageCounter(pages, 1));
                            }
                        }
                    }
                }
            }
            Collections.sort(paths);
            txtArea.append("Consulting popular pages\n");
            int count = 0;
            for(var p: paths){
                count++;
                txtArea.append(count + ") "+ p.obtainName());
                if(count == 10) break;
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de log: " + e.getMessage());
        }
    }
    public static void consultComponents(SitePageCounter path, int type, JTextArea txtArea){
        System.out.println("consultComponents");
        String[] pages = path.getName().split("/");
        String page = pages[pages.length-1];
        String clase;
        if(type == 1) clase = "TITULO";
        else if(type == 2) clase = "PARRAFO";
        else if(type == 3) clase = "IMAGEN";
        else if(type == 4) clase = "VIDEO";
        else if(type == 5) clase = "MENU";
        else clase = "TODOS";

        int count = 0;
        for(var c: Actions.getComponents){
            if(c.getId().equals(page)){
                if(type == 6){
                    count++;
                }
                else if(c.getClase().equals(clase)){
                    count++;
                }
            }
        }
        txtArea.append("Consulting components\n");
        txtArea.append(clase + ": "+ count);
    }
    public static void logReader(){
        String logFilePath = "/var/log/apache2/sites-log.log"; // Ruta al archivo de log de Apache

        try (BufferedReader br = new BufferedReader(new FileReader(logFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("GET")) {
                    String[] fields = line.split(" ");

                    String site = fields[0].substring(4, fields[0].length()-4);
                    String pages = fields[2].substring(1);


                    System.out.println("IP: " + site);
                    System.out.println("PAGES: " + pages);
                    System.out.println("--------------------");
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de log: " + e.getMessage());
        }
    }

}
