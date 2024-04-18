package com.navi.backend.webController;

import com.navi.backend.webController.objs.WebSite;

import javax.swing.*;

import static com.navi.backend.webController.Actions.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class WebSitesController {

    public void createSite(ArrayList<Parameter> parameters){
        boolean valid = true;
        String id = "", cUser = "", cDate = "", eDate = "", eUser = "";
        for(Parameter p : parameters){
            if(p.getName().equals("ID")) id = p.getValue();
            else if(p.getName().equals("USUARIO_CREACION")) cUser = p.getValue();
            else if(p.getName().equals("FECHA_CREACION")) cDate = p.getValue();
            else if(p.getName().equals("FECHA_MODIFICACION")) eDate = p.getValue();
            else if(p.getName().equals("USUARIO_MODIFICACION")) eUser = p.getValue();
            else{
                System.out.println("Invalid Parameter : " + p.getName() + ", linea: " + p.getLine() + ", columna: " + p.getCol());
                ERRORS.add("Invalid Parameter : " + p.getName() + ", linea: " + p.getLine() + ", columna: " + p.getCol());
                valid = false;
                break;
            }
        }
        if(valid && !id.isEmpty() && !cUser.isEmpty()){
            if(cDate.isEmpty()){
                LocalDate d = LocalDate.now();
                cDate = d.toString();
            }
            if(eUser.isEmpty()) eUser = cUser;
            getSites.add(new WebSite(id, cUser, cDate, eDate, eUser));
            siteIDs.add(id);
            HTMLController.createWebSite(id);
            String response = "Creating Site ID: " + id + ", usuario_creacion: " + cUser + ", date: " + cDate +
                    ", ModificationDate: " + eDate + ", ModificationUser: " + eUser;
            RESPONSES.add(response);
        }
        else {
            ERRORS.add("Parameters incomplete");
        }

    }
    public void deleteSite(ArrayList<Parameter> parameters){
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
            int option = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar este sitio web: "+id+" ?", "Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if(option == JOptionPane.YES_OPTION){
                String finalId = id;
                String path = searchFile(id);
                HTMLController.deleteWebPage(path);
                getSites.removeIf(site -> site.getId().equals(finalId));
                siteIDs.removeIf(i -> i.equals(finalId));
                String response = "DELETE Site ID: " + id;
                RESPONSES.add(response);
            }
            else RESPONSES.add("Canceling delete Site ID: " + id);

        }
    }
}
