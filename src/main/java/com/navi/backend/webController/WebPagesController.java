package com.navi.backend.webController;

import com.navi.backend.webController.objs.*;

import javax.swing.*;

import static com.navi.backend.webController.Actions.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class WebPagesController {
    public void createPage(ActionPE action){
        boolean valid = true;
        String id = "", cUser = "", cDate = "", eDate = "", eUser = "", title="", site="", father="";
        for(Parameter p : action.getParameters()){
            switch (p.getName()) {
                case "ID" -> id = p.getValue();
                case "USUARIO_CREACION" -> cUser = p.getValue();
                case "FECHA_CREACION" -> cDate = p.getValue();
                case "FECHA_MODIFICACION" -> eDate = p.getValue();
                case "USUARIO_MODIFICACION" -> eUser = p.getValue();
                case "TITULO" -> title = p.getValue();
                case "SITIO" -> site = p.getValue();
                case "PADRE" -> father = p.getValue();
                default -> {
                    System.out.println("Invalid Parameter : " + p.getName() + ", linea: " + p.getLine() + ", columna: " + p.getCol());
                    ERRORS.add("Invalid Parameter : " + p.getName() + ", line: " + p.getLine() + ", col: " + p.getCol());
                    valid = false;
                }
            }
        }
        if(valid && !id.isEmpty() && !site.isEmpty() && !cUser.isEmpty()){
            if(cDate.isEmpty()){
                LocalDate d = LocalDate.now();
                cDate = d.toString();
            }
            if(eUser.isEmpty()) eUser = cUser;
            for(WebSite s: getSites){
                //if(s.getId().equals(site)) s.getWPages().add(new WPage(id, cUser, cDate, eDate, eUser, title, site, father, new ArrayList<>(), new ArrayList<>()));
            }
            getPages.add(new WPage(id, cUser, cDate, eDate, eUser, title, site, father));
            String path = site + "/" + searchPage(father, id);
            pageIDs.add(id);
            HTMLController.createWebPage(id, path, title, site);
            String response = "Creating Page ID: " + id + ", usuario_creacion: " + cUser + ", date: " + cDate +
                    ", ModificationDate: " + eDate + ", ModificationUser: " + eUser + ", title: " + title + ", site: " + site + ", father: " + father;
            RESPONSES.add(response);
        }
    }
    public void deletePage(ArrayList<Parameter> parameters){
        boolean valid = true;
        String id = "";
        for(Parameter p : parameters){
            if(p.getName().equals("ID")) id = p.getValue();
            else{
                System.out.println("Invalid Parameter : " + p.getName() + ", linea: " + p.getLine() + ", columna: " + p.getCol());
                ERRORS.add("Invalid Parameter : " + p.getName() + ", line: " + p.getLine() + ", col: " + p.getCol());
                valid = false;
            }
        }
        if(valid && !id.isEmpty()){
            int option = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar esta pagina web:"+id+" ?", "Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if(option == JOptionPane.YES_OPTION){
                String finalId = id;
                String path = searchFile(id);
                HTMLController.deleteWebPage(path);
                getPages.removeIf(p -> p.getId().equals(finalId));
                pageIDs.removeIf(i -> i.equals(finalId));
                String response = "DELETE Page ID: " + id;
                RESPONSES.add(response);
            }
            else RESPONSES.add("Canceling delete Page ID: " + id);

        }
    }
    public void editPage(ActionPE action){
        boolean valid = true;
        String id = "", cUser = "", cDate = "", eDate = "", eUser = "", title="", site="", father="";
        for(Parameter p : action.getParameters()){
            switch (p.getName()) {
                case "ID" -> id = p.getValue();
                case "USUARIO_CREACION" -> cUser = p.getValue();
                case "FECHA_CREACION" -> cDate = p.getValue();
                case "FECHA_MODIFICACION" -> eDate = p.getValue();
                case "USUARIO_MODIFICACION" -> eUser = p.getValue();
                case "TITULO" -> title = p.getValue();
                case "SITIO" -> site = p.getValue();
                case "PADRE" -> father = p.getValue();
                default -> {
                    System.out.println("Invalid Parameter : " + p.getName() + ", linea: " + p.getLine() + ", columna: " + p.getCol());
                    ERRORS.add("Invalid Parameter : " + p.getName() + ", linea: " + p.getLine() + ", columna: " + p.getCol());
                    valid = false;
                }
            }
        }
        if(valid && !id.isEmpty() && validateSite(site) && validFather(father)){
            var p = getPage(id);
            if(!title.isEmpty()) p.setTitle(title);
            if(!cUser.isEmpty()) p.setCUser(cUser);
            if(!cDate.isEmpty()) p.setCDate(cDate);
            if(!eUser.isEmpty()) p.setEUser(eUser);
            else p.setEUser(cUser);
            if(eDate.isEmpty()){
                LocalDate d = LocalDate.now();
                p.setEDate(d.toString());
            }
            else p.setEDate(eDate);

            for(var wPage : getPages){
                if(wPage.getId().equals(id)) {
                    getPages.set(getPages.indexOf(wPage), p);
                    break;
                }
            }
            String path = searchPage(id);
            HTMLController.editWebPage(path, title, site);

            String response = "Editing Page ID: " + id + ", usuario_creacion: " + cUser + ", date: " + cDate +
                    ", ModificationDate: " + eDate + ", ModificationUser: " + eUser + ", title: " + title + ", site: " + site + ", father: " + father;
            RESPONSES.add(response);
        }
    }
    public WPage getPage(String id){
        for(WPage p : getPages){
            if(p.getId().equals(id)) return p;
        }
        return null;
    }
    public boolean validFather(String father){
        for(var w : getPages){
            if(w.getFather().equals(father)) return true;
        }
        System.out.println("ERROR: Different father");
        return false;
    }
    public boolean validateSite(String site){
        for(var w : getPages){
            if(w.getSite().equals(site)) return true;
        }
        System.out.println("ERROR: Different father");
        return false;
    }
}
