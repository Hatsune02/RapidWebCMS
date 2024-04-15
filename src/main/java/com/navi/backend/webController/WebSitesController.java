package com.navi.backend.webController;

import com.navi.backend.webController.objs.WebSite;
import static com.navi.backend.webController.Actions.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class WebSitesController {

    public void createSite(ArrayList<Parameter> parameters){
        System.out.println("Creating Site");
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
                valid = false;
                break;
            }
        }
        if(valid && !id.isEmpty()){
            if(!ids.contains(id)){
                if(cDate.isEmpty()){
                    LocalDate d = LocalDate.now();
                    cDate = d.toString();
                }
                getSites.add(new WebSite(id, cUser, cDate, eDate, eUser));
                ids.add(id);
                HTMLController.createWebSite(id);
            }
            else System.out.println("Id ya existe");
        }

    }
    public void deleteSite(ArrayList<Parameter> parameters){
        System.out.println("Deleting Site");
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
}
