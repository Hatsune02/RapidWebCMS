package com.navi.backend.webController;

import com.navi.backend.webController.objs.*;

import java.util.ArrayList;

public class Actions {
    public static ArrayList<WebSite> getSites = new ArrayList<>();
    public static ArrayList<WPage> getPages = new ArrayList<>();
    public static ArrayList<WComponent> getComponents = new ArrayList<>();
    public static ArrayList<String> siteIDs = new ArrayList<>();
    public static ArrayList<String> pageIDs = new ArrayList<>();
    public static ArrayList<String> compIDs = new ArrayList<>();
    public static ArrayList<String> ERRORS = new ArrayList<>();
    public static ArrayList<String> RESPONSES = new ArrayList<>();
    private WebPagesController pagesController = new WebPagesController();
    private WebComponentController componentController = new WebComponentController();
    private WebSitesController sitesController = new WebSitesController();

    public void createSite(ArrayList<Parameter> parameters){
        if(validateParameters(parameters, true, "site")) sitesController.createSite(parameters);
    }
    public void deleteSite(ArrayList<Parameter> parameters){
        if(validateParameters(parameters, false, "site")) sitesController.deleteSite(parameters);
    }
    public void createPage(ActionPE action){
        if(validateParameters(action.getParameters(), true, "page")) pagesController.createPage(action);
    }
    public void deletePage(ArrayList<Parameter> parameters){
        if(validateParameters(parameters, false,"page")) pagesController.deletePage(parameters);
    }
    public void editPage(ActionPE action){
        if(validateParameters(action.getParameters(), false,"page")) pagesController.editPage(action);
    }
    public void createComponent(ActionPA action){
        if(validateParameters(action.getParameters(), true,"comp") && validateAttributes(action.getAttributes())) componentController.createComponent(action);
    }
    public void deleteComponent(ArrayList<Parameter> parameters){
        if(validateParameters(parameters, false,"comp")) componentController.deleteComponent(parameters);
    }
    public void editComponent(ActionPA action){
        if(validateParameters(action.getParameters(), false,"comp") && validateAttributes(action.getAttributes())) componentController.editComponent(action);
    }

    public static boolean validateParameters(ArrayList<Parameter> parameters, boolean create, String entity){
        int id = 0, createU = 0, dateCreate = 0, editU = 0, dateEdit = 0,
                title = 0, site=0, father=0, page=0, clase=0;
        for(Parameter p : parameters){
            if(p.getName().equals("ID")){
                id++;
                switch (entity) {
                    case "site" -> {
                        if (errorID(create, p, siteIDs)) return false;
                    }
                    case "page" -> {
                        if (errorID(create, p, pageIDs)) return false;
                    }
                    case "comp" -> {
                        if (errorID(create, p, compIDs)) return false;
                    }
                }
                if(id > 1) return false;
            }
            if(p.getName().equals("USUARIO_CREACION")){
                createU++;
                if(createU > 1) return false;
            }
            if(p.getName().equals("FECHA_CREACION")){
                dateCreate++;
                if(dateCreate > 1) return false;
            }
            if(p.getName().equals("FECHA_MODIFICACION")){
                dateEdit++;
                if(dateEdit > 1) return false;
            }
            if(p.getName().equals("USUARIO_MODIFICACION")){
                editU++;
                if(editU > 1) return false;
            }
            if(p.getName().equals("TITULO")){
                title++;
                if(title > 1) return false;
            }
            if(p.getName().equals("SITIO")){
                site++;
                if(errorID(false, p, siteIDs)) return false;
                if(site > 1) return false;
            }
            if(p.getName().equals("PADRE")){
                father++;
                if(errorID(false, p, pageIDs)) return false;
                if(father > 1) return false;
            }
            if(p.getName().equals("PAGINA")){
                page++;
                if(errorID(false, p, pageIDs)) return false;
                if(page > 1) return false;
            }
            if(p.getName().equals("CLASE")){
                clase++;
                if(clase > 1) return false;
            }
        }

        return true;
    }

    private static boolean errorID(boolean create, Parameter p, ArrayList<String> listIds) {
        if(!p.getValue().matches("[_\\-$][_\\-$a-zA-Z0-9]+")) {
            System.out.println("Not a valid ID: " + p.getValue() + ", line:" + p.getLine() + ", col: " + p.getCol());
            ERRORS.add("Not a valid ID: " + p.getValue() + ", line:" + p.getLine() + ", col: " + p.getCol());
            return true;
        }
        if(create && listIds.contains(p.getValue())){
            System.out.println("Repeated ID: " + p.getValue() + ", line:" + p.getLine() + ", col: " + p.getCol());
            ERRORS.add("Repeated ID: " + p.getValue() + ", line:" + p.getLine() + ", col: " + p.getCol());
            return true;
        }
        if(!create && !listIds.contains(p.getValue())){
            System.out.println("ID not found: " + p.getValue() + ", line:" + p.getLine() + ", col: " + p.getCol());
            ERRORS.add("ID not found: " + p.getValue() + ", line:" + p.getLine() + ", col: " + p.getCol());
            return true;
        }
        return false;
    }

    public static boolean validateAttributes(ArrayList<Attribute> attributes){
        int text=0, align=0, color=0, url=0, height=0, width=0, labels=0;
        for(Attribute a : attributes){
            if(a.getName().equals("TEXTO")){
                text++;
                if(text > 1) return false;
            }
            if(a.getName().equals("ALINEACION")){
                align++;
                if(align > 1) return false;
            }
            if(a.getName().equals("COLOR")){
                color++;
                if(color > 1) return false;
            }
            if(a.getName().equals("ORIGEN")){
                url++;
                if(url > 1) return false;
            }
            if(a.getName().equals("ALTURA")){
                height++;
                if(height > 1) return false;
            }
            if(a.getName().equals("ANCHO")){
                width++;
                if(width > 1) return false;
            }
            if(a.getName().equals("ETIQUETAS")){
                labels++;
                if(labels > 1) return false;
            }
        }

        return true;
    }
    public static String searchPage(String father, String route){
        if(father.isEmpty()) return route;
        for(WPage s: getPages){
            if(s.getId().equals(father)) return searchPage(s.getFather(), father+"/"+route);
        }
        return "";
    }
    public static String searchPage(String route){
        String father = "", site = "";
        for(WPage s: getPages){
            if(s.getId().equals(route)){
                father = s.getFather();
                site = s.getSite();
            }
        }
        return site+"/"+ searchPage(father, route+"/"+route);
    }
    public static String searchFile(String route){
        String father = "", site = "";
        for(WPage s: getPages){
            if(s.getId().equals(route)){
                father = s.getFather();
                site = s.getSite();
            }
        }
        return site+"/"+ searchPage(father, route);
    }
}