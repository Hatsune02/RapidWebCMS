package com.navi.backend.webController;

import com.navi.backend.webController.objs.*;
import static com.navi.backend.webController.Actions.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class WebPagesController {
    public void createPage(ActionPE action){
        System.out.println("Creating Page");
        boolean valid = true;
        String id = "", cUser = "", cDate = "", eDate = "", eUser = "", title="", site="", father="";
        for(Parameter p : action.getParameters()){
            if(p.getName().equals("ID")) id = p.getValue();
            else if(p.getName().equals("USUARIO_CREACION")) cUser = p.getValue();
            else if(p.getName().equals("FECHA_CREACION")) cDate = p.getValue();
            else if(p.getName().equals("FECHA_MODIFICACION")) eDate = p.getValue();
            else if(p.getName().equals("TITULO")) title = p.getValue();
            else if(p.getName().equals("SITIO")) site = p.getValue();
            else if(p.getName().equals("PADRE")) father = p.getValue();
            else{
                System.out.println("Invalid Parameter : " + p.getName() + ", linea: " + p.getLine() + ", columna: " + p.getCol());
                valid = false;
                break;
            }
        }
        if(valid && !id.isEmpty() && !site.isEmpty()){
            if(!ids.contains(id)){
                if(cDate.isEmpty()){
                    LocalDate d = LocalDate.now();
                    cDate = d.toString();
                }
                for(WebSite s: getSites){
                    //if(s.getId().equals(site)) s.getWPages().add(new WPage(id, cUser, cDate, eDate, eUser, title, site, father, new ArrayList<>(), new ArrayList<>()));
                }
                getPages.add(new WPage(id, cUser, cDate, eDate, eUser, title, site, father));
                String path = site + "/" + searchPage(father, id);
                ids.add(id);
                HTMLController.createWebPage(id, path, title, site);
            }
        }
    }
    public void deletePage(ArrayList<Parameter> parameters){
        System.out.println("Deleting Page");
        boolean valid = true;
        String id = "";
        for(Parameter p : parameters){
            if(p.getName().equals("ID")) id = p.getValue();
            else{
                System.out.println("Invalid Parameter : " + p.getName() + ", linea: " + p.getLine() + ", columna: " + p.getCol());
                valid = false;
                break;
            }
        }
        if(valid && !id.isEmpty()){
            String finalId = id;
            boolean delete = getSites.removeIf(site -> site.getId().equals(finalId));
            if(delete) System.out.println("Site deleted successfully");
            else System.out.println("Site deletion failed, id incorrect");
            ids.removeIf(i -> i.equals(finalId));
        }
    }
    public void editPage(ActionPE action){
        System.out.println("Editing Page");
        boolean valid = true;
        String id = "", cUser = "", cDate = "", eDate = "", eUser = "", title="", site="", father="";
        for(Parameter p : action.getParameters()){
            if(p.getName().equals("ID")) id = p.getValue();
            else if(p.getName().equals("USUARIO_CREACION")) cUser = p.getValue();
            else if(p.getName().equals("FECHA_CREACION")) cDate = p.getValue();
            else if(p.getName().equals("FECHA_MODIFICACION")) eDate = p.getValue();
            else if(p.getName().equals("TITULO")) title = p.getValue();
            else if(p.getName().equals("SITIO")) site = p.getValue();
            else if(p.getName().equals("PADRE")) father = p.getValue();
            else{
                System.out.println("Invalid Parameter : " + p.getName() + ", linea: " + p.getLine() + ", columna: " + p.getCol());
                valid = false;
                break;
            }
        }
        if(valid && !id.isEmpty() && !site.isEmpty()){
            if(cDate.isEmpty()){
                LocalDate d = LocalDate.now();
                cDate = d.toString();
            }
            for(WebSite s: getSites){
                //if(s.getId().equals(site)) s.getWPages().add(new WPage(id, cUser, cDate, eDate, eUser, title, site, father, new ArrayList<>(), new ArrayList<>()));
            }
            getPages.add(new WPage(id, cUser, cDate, eDate, eUser, title, site, father));
        }
    }

}
