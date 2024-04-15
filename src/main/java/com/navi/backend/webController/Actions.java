package com.navi.backend.webController;

import com.navi.backend.webController.objs.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class Actions {
    public static ArrayList<WebSite> getSites = new ArrayList<>();
    public static ArrayList<WPage> getPages = new ArrayList<>();
    public static ArrayList<WComponent> getComponents = new ArrayList<>();
    public static ArrayList<String> ids = new ArrayList<>();
    private WebPagesController pagesController = new WebPagesController();
    private WebComponentController componentController = new WebComponentController();
    private WebSitesController sitesController = new WebSitesController();

    public void createSite(ArrayList<Parameter> parameters){
        sitesController.createSite(parameters);
    }
    public void deleteSite(ArrayList<Parameter> parameters){
        sitesController.deleteSite(parameters);
    }
    public void createPage(ActionPE action){
        pagesController.createPage(action);
    }
    public void deletePage(ArrayList<Parameter> parameters){
        pagesController.deletePage(parameters);
    }
    public void editPage(ActionPE action){
        pagesController.editPage(action);
    }
    public void createComponent(ActionPA action){
        componentController.createComponent(action);
    }
    public void deleteComponent(ActionPA action){
        componentController.deleteComponent(action);
    }
    public void editComponent(ActionPA action){
        componentController.editComponent(action);
    }

    public static boolean validateParameters(ArrayList<Parameter> parameters){
        int id = 0, createU = 0, dateCreate = 0, editU = 0, dateEdit = 0,
                title = 0, site=0, father=0, page=0, clase=0;
        String regex = "[:_\\-$]?[:_\\-$a-zA-Z0-9]+";
        for(Parameter p : parameters){
            if(p.getName().equals("ID")){
                id++;
                if(!p.getValue().matches(regex)) return false;
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
                if(site > 1) return false;
            }
            if(p.getName().equals("PADRE")){
                father++;
                if(father > 1) return false;
            }
            if(p.getName().equals("PAGINA")){
                page++;
                if(page > 1) return false;
            }
            if(p.getName().equals("CLASE")){
                clase++;
                if(clase > 1) return false;
            }
        }

        return true;
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
        return searchPage(father, site+"/"+route+"/"+route);
    }

}