package com.navi.backend.webController;

import com.navi.backend.webController.objs.WComponent;
import com.navi.backend.webController.objs.WPage;

import javax.swing.*;
import java.util.ArrayList;

import static com.navi.backend.webController.Actions.*;

public class WebComponentController {

    public void createComponent(ActionPA action){
        String id = "", page = "", clase = "";
        boolean valid = true;
        for(Parameter p : action.getParameters()){
            switch (p.getName()) {
                case "ID" -> id = p.getValue();
                case "PAGINA" -> page = p.getValue();
                case "CLASE" -> clase = p.getValue();
                default -> {
                    System.out.println("Invalid Parameter : " + p.getName() + ", linea: " + p.getLine() + ", columna: " + p.getCol());
                    RESPONSES.add("Invalid Parameter : " + p.getName() + ", linea: " + p.getLine() + ", columna: " + p.getCol());
                    valid = false;
                }
            }
        }
        switch (clase) {
            case "TITULO", "PARRAFO" -> {
                String text = "", align = "", color = "";
                for (Attribute a : action.getAttributes()) {
                    switch (a.getName()) {
                        case "TEXTO" -> text = a.getValue();
                        case "ALINEACION" -> {
                            if(a.getValue().equals("CENTRAR")) align = "center";
                            else if(a.getValue().equals("IZQUIERDA")) align = "left";
                            else if(a.getValue().equals("DERECHA")) align = "right";
                            else if(a.getValue().equals("JUSTIFICAR")) align = "justify";
                            else {
                                RESPONSES.add("Invalid ALINEACION : " + a.getName() + ", line: " + a.getLine() + ", col: " + a.getCol());
                            }
                        }
                        case "COLOR" -> color = a.getValue();
                        default -> {
                            System.out.println("Invalid Attribute : " + a.getName() + ", line: " + a.getLine() + ", col: " + a.getCol());
                            RESPONSES.add("Invalid Attribute : " + a.getName() + ", line: " + a.getLine() + ", col: " + a.getCol());
                            valid = false;
                        }
                    }
                }
                if (valid && !text.isEmpty()) {
                    String component;
                    StringBuilder style = new StringBuilder();
                    if (!align.isEmpty()) style.append("text-align: ").append(align).append("; ");
                    if (!color.isEmpty()) style.append("color: ").append(color).append("; ");

                    if (clase.equals("TITULO")) component = "<h1 style=\"" + style + "\">" + text + "</h1>";
                    else component = "<p style=\"" + style + "\">" + text + "</p>";
                    WComponent c = new WComponent(id, page, clase, text, align, color, "", "", "", "", component);
                    addComponentToLists(id, page, component, c);
                    RESPONSES.add("Creating Component ID: " + id + " pagina: " + page + " clase: " + clase);
                }
            }
            case "IMAGEN" -> {
                String url = "", align = "", height = "", width = "";
                for (Attribute a : action.getAttributes()) {
                    switch (a.getName()) {
                        case "ORIGEN" -> url = a.getValue();
                        case "ALINEACION" -> {
                            if(a.getValue().equals("CENTRAR")) align = "center";
                            else if(a.getValue().equals("IZQUIERDA")) align = "left";
                            else if(a.getValue().equals("DERECHA")) align = "right";
                            else if(a.getValue().equals("JUSTIFICAR")) align = "justify";
                            else {
                                RESPONSES.add("Invalid ALINEACION : " + a.getName() + ", line: " + a.getLine() + ", col: " + a.getCol());
                            }
                        }
                        case "ALTURA" -> height = a.getValue();
                        case "ANCHO" -> width = a.getValue();
                        default -> {
                            System.out.println("Invalid Attribute : " + a.getName() + ", linea: " + a.getLine() + ", columna: " + a.getCol());
                            valid = false;
                            RESPONSES.add("Invalid Attribute : " + a.getName() + ", linea: " + a.getLine() + ", columna: " + a.getCol());
                        }
                    }
                }
                if (valid && !url.isEmpty() && !height.isEmpty() && !width.isEmpty()) {
                    String component;
                    StringBuilder style = new StringBuilder();
                    style.append("height: ").append(height).append("; width: ").append(width).append("; ");
                    if (!align.isEmpty()) style.append("text-align: ").append(align).append("; ");
                    component = "<img src=\"" + url + "\" style=\"" + style + "\">";
                    WComponent c = new WComponent(id, page, clase, "", align, "", url, height, width, "", component);
                    addComponentToLists(id, page, component, c);
                    RESPONSES.add("Creating Component ID: " + id + " pagina: " + page + " clase: " + clase);
                }
            }
            case "VIDEO" -> {
                String url = "", height = "", width = "";
                for (Attribute a : action.getAttributes()) {
                    switch (a.getName()) {
                        case "ORIGEN" -> url = a.getValue();
                        case "ALTURA" -> height = a.getValue();
                        case "ANCHO" -> width = a.getValue();
                        default -> {
                            System.out.println("Invalid Attribute : " + a.getName() + ", linea: " + a.getLine() + ", columna: " + a.getCol());
                            valid = false;
                            RESPONSES.add("Invalid Attribute : " + a.getName() + ", linea: " + a.getLine() + ", columna: " + a.getCol());
                        }
                    }
                }
                if (valid && !url.isEmpty() && !height.isEmpty() && !width.isEmpty()) {
                    String component;
                    component = "<video height=\"" + height + "\" width=\"" + width + "\">\n<source src\"" + url + "\" type=\"video/mp4\">\n</video>";
                    WComponent c = new WComponent(id, page, clase, "", "", "", url, height, width, "", component);
                    addComponentToLists(id, page, component, c);
                    RESPONSES.add("Creating Component ID: " + id + " pagina: " + page + " clase: " + clase);
                }
            }
            case "MENU" -> {
                String father = "", labels = "";
                for (Attribute a : action.getAttributes()) {
                    if (a.getName().equals("PADRE")) father = a.getValue();
                    else if (a.getName().equals("ETIQUETAS")) labels = a.getValue();
                    else {
                        System.out.println("Invalid Attribute : " + a.getName() + ", linea: " + a.getLine() + ", columna: " + a.getCol());
                        valid = false;
                        break;
                    }
                }
                if(valid){
                    String component = "    <nav>\n        <ul>\n";
                    for(var p: getPages){
                        if(p.getFather().equals(father)) {
                            if(!labels.isEmpty()){
                                if(p.getLabels().contains(labels)){
                                    component += "\t\t\t<li><a href =\""+ searchPageWWW(p.getId()) +"\">"+p.getId()+"</a></li>\n";
                                }
                            }
                            else{
                                component += "\t\t\t<li><a href =\""+ searchPageWWW(p.getId()) +"\">"+p.getId()+"</a></li>\n";
                            }
                        }
                    }
                    component += "        </ul>\n    </nav>";

                    WComponent c = new WComponent(id, page, clase, "", "", "", "", "", "", labels, component);
                    addComponentToLists(id, page, component, c);
                    RESPONSES.add("Creating Component ID: " + id + " pagina: " + page + " clase: " + clase);
                }
            }
            default -> RESPONSES.add("Invalid Attribute : " + clase);
        }
    }

    public void deleteComponent(ArrayList<Parameter> parameters){
        String id = "", page = "";
        boolean valid = true;
        for(Parameter p : parameters){
            switch (p.getName()) {
                case "ID" -> id = p.getValue();
                case "PAGINA" -> page = p.getValue();
                default -> {
                    System.out.println("Invalid Parameter : " + p.getName() + ", line: " + p.getLine() + ", col: " + p.getCol());
                    RESPONSES.add("Invalid Parameter : " + p.getName() + ", line: " + p.getLine() + ", col: " + p.getCol());
                    valid = false;
                }
            }
        }
        if(valid && !id.isEmpty() && !page.isEmpty()){
            int option = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar esta pagina web:"+id+" ?", "Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if(option == JOptionPane.YES_OPTION){
                deleteComponentFromLists(id, page);
                RESPONSES.add("DELETE component ID: "+id+", page: "+page);
            }
            else RESPONSES.add("Canceling delete component ID: "+id+", page: "+page);

        }
    }
    public void editComponent(ActionPA action){
        String id = "", page = "", clase = "";
        boolean valid = true;
        for(Parameter p : action.getParameters()){
            switch (p.getName()) {
                case "ID" -> id = p.getValue();
                case "PAGINA" -> page = p.getValue();
                case "CLASE" -> clase = p.getValue();
                default -> {
                    System.out.println("Invalid Parameter : " + p.getName() + ", line: " + p.getLine() + ", col: " + p.getCol());
                    RESPONSES.add("Invalid Parameter : " + p.getName() + ", line: " + p.getLine() + ", col: " + p.getCol());
                    valid = false;
                }
            }
        }
        if(validComponent(id) && validPage(page) && validClass(clase)){
            switch (clase) {
                case "TITULO", "PARRAFO" -> {
                    String text = "", align = "", color = "";
                    for (Attribute a : action.getAttributes()) {
                        switch (a.getName()) {
                            case "TEXTO" -> text = a.getValue();
                            case "ALINEACION" -> {
                                if(a.getValue().equals("CENTRAR")) align = "center";
                                else if(a.getValue().equals("IZQUIERDA")) align = "left";
                                else if(a.getValue().equals("DERECHA")) align = "right";
                                else if(a.getValue().equals("JUSTIFICAR")) align = "justify";
                                else {
                                    RESPONSES.add("Invalid ALINEACION : " + a.getName() + ", line: " + a.getLine() + ", col: " + a.getCol());
                                }
                            }
                            case "COLOR" -> color = a.getValue();
                            default -> {
                                System.out.println("Invalid Attribute : " + a.getName() + ", linea: " + a.getLine() + ", col: " + a.getCol());
                                RESPONSES.add("Invalid Attribute : " + a.getName() + ", linea: " + a.getLine() + ", col: " + a.getCol());
                                valid = false;
                            }
                        }
                    }
                    if (valid && !text.isEmpty()) {
                        String component;
                        StringBuilder style = new StringBuilder();
                        if (!align.isEmpty()) style.append("text-align: ").append(align).append("; ");
                        if (!color.isEmpty()) style.append("color: ").append(color).append("; ");

                        if (clase.equals("TITULO")) component = "<h1 style=\"" + style + "\">" + text + "</h1>";
                        else component = "<p style=\"" + style + "\">" + text + "</p>";
                        WComponent c = new WComponent(id, page, clase, text, align, color, "", "", "", "", component);
                        editComponentsFromLists(id, c, page);
                    }
                }
                case "IMAGEN" -> {
                    String url = "", align = "", height = "", width = "";
                    for (Attribute a : action.getAttributes()) {
                        switch (a.getName()) {
                            case "ORIGEN" -> url = a.getValue();
                            case "ALINEACION" -> {
                                if(a.getValue().equals("CENTRAR")) align = "center";
                                else if(a.getValue().equals("IZQUIERDA")) align = "left";
                                else if(a.getValue().equals("DERECHA")) align = "right";
                                else if(a.getValue().equals("JUSTIFICAR")) align = "justify";
                                else {
                                    RESPONSES.add("Invalid ALINEACION : " + a.getName() + ", line: " + a.getLine() + ", col: " + a.getCol());
                                }
                            }
                            case "ALTURA" -> height = a.getValue();
                            case "ANCHO" -> width = a.getValue();
                            default -> {
                                System.out.println("Invalid Attribute : " + a.getName() + ", linea: " + a.getLine() + ", columna: " + a.getCol());
                                RESPONSES.add("Invalid Attribute : " + a.getName() + ", linea: " + a.getLine() + ", columna: " + a.getCol());
                                valid = false;
                            }
                        }
                    }
                    if (valid && !url.isEmpty() && !height.isEmpty() && !width.isEmpty()) {
                        String component;
                        StringBuilder style = new StringBuilder();
                        style.append("height: ").append(height).append("; width: ").append(width).append("; ");
                        if (!align.isEmpty()) style.append("text-align: ").append(align).append("; ");
                        component = "<img src=\"" + url + "\" style=\"" + style + "\">";
                        WComponent c = new WComponent(id, page, clase, "", align, "", url, height, width, "", component);
                        editComponentsFromLists(id, c, page);
                        RESPONSES.add("Editing Component ID: " + id + " pagina: " + page + " clase: " + clase);
                    }
                }
                case "VIDEO" -> {
                    String url = "", height = "", width = "";
                    for (Attribute a : action.getAttributes()) {
                        switch (a.getName()) {
                            case "ORIGEN" -> url = a.getValue();
                            case "ALTURA" -> height = a.getValue();
                            case "ANCHO" -> width = a.getValue();
                            default -> {
                                System.out.println("Invalid Attribute : " + a.getName() + ", linea: " + a.getLine() + ", columna: " + a.getCol());
                                RESPONSES.add("Invalid Attribute : " + a.getName() + ", linea: " + a.getLine() + ", columna: " + a.getCol());
                                valid = false;
                            }
                        }
                    }
                    if (valid && !url.isEmpty() && !height.isEmpty() && !width.isEmpty()) {
                        String component;
                        component = "<video height=\"" + height + "\" width=\"" + width + "\">\n<source src\"" + url + "\" type=\"video/mp4\">\n</video>";
                        WComponent c = new WComponent(id, page, clase, "", "", "", url, height, width, "", component);
                        editComponentsFromLists(id, c, page);
                        RESPONSES.add("Editing Component ID: " + id + " pagina: " + page + " clase: " + clase);
                    }
                }
                case "MENU" -> {
                    String father = "", labels = "";
                    for (Attribute a : action.getAttributes()) {
                        if (a.getName().equals("PADRE")) father = a.getValue();
                        else if (a.getName().equals("ETIQUETAS")) labels = a.getValue();
                        else {
                            System.out.println("Invalid Attribute : " + a.getName() + ", linea: " + a.getLine() + ", columna: " + a.getCol());
                            valid = false;
                            break;
                        }
                    }
                    if(valid){
                        String component = "    <nav>\n        <ul>\n";
                        for(var p: getPages){
                            if(p.getFather().equals(father)) {
                                if(!labels.isEmpty()){
                                    if(p.getLabels().contains(labels)){
                                        component += "\t\t\t<li><a href =\""+ searchPageWWW(p.getId()) +"\">"+p.getId()+"</a></li>\n";
                                    }
                                }
                                else{
                                    component += "\t\t\t<li><a href =\""+ searchPageWWW(p.getId()) +"\">"+p.getId()+"</a></li>\n";
                                }
                            }
                        }
                        component += "        </ul>\n    </nav>";

                        WComponent c = new WComponent(id, page, clase, "", "", "", "", "", "", labels, component);
                        editComponentsFromLists(id, c, page);
                        RESPONSES.add("Editing Component ID: " + id + " pagina: " + page + " clase: " + clase);
                    }
                }
                default -> RESPONSES.add("Invalid Attribute : " + clase);
            }
        }
    }

    public boolean validComponent(String id){
        for(var WComponent : getComponents){
            if(WComponent.getId().equals(id)) return true;
        }
        System.out.println("Component not found");
        return false;
    }
    public boolean validPage(String page){
        for(var WComponent : getComponents){
            if(WComponent.getPage().equals(page)) return true;
        }
        System.out.println("ERROR: Different page");
        return false;
    }
    public boolean validClass(String clase){
        for(var WComponent : getComponents){
            if(WComponent.getClase().equals(clase)) return true;
        }
        System.out.println("ERROR: Different class");
        return false;
    }

    private void addComponentToLists(String id, String page, String component, WComponent c) {
        getComponents.add(c);
        for (WPage p: getPages){
            if(p.getId().equals(page)){
                p.getWComponents().add(c);
            }
        }
        String path = searchPage(page);
        HTMLController.addComponent(path, component);
        compIDs.add(id);
    }
    public void editComponentsFromLists(String id, WComponent c, String page){
        for(var comp : getComponents){
            if(comp.getId().equals(id)) {
                getComponents.set(getComponents.indexOf(comp), c);
                break;
            }
        }
        for (WPage p: getPages){
            if(p.getId().equals(page)){
                for(var comp : p.getWComponents()){
                    if(comp.getId().equals(id)) {
                        p.getWComponents().set(p.getWComponents().indexOf(comp), c);
                        break;
                    }
                }
                break;
            }
        }
        String path = searchPage(page);
        HTMLController.editComponent(page, path, getComponents);
    }
    public void deleteComponentFromLists(String id, String page){
        for(var comp : getComponents){
            if(comp.getId().equals(id)) {
                getComponents.remove(comp);
                break;
            }
        }
        for (WPage p: getPages){
            if(p.getId().equals(page)){
                for(var comp : p.getWComponents()){
                    if(comp.getId().equals(id)) {
                        p.getWComponents().remove(comp);
                        break;
                    }
                }
                break;
            }
        }
        compIDs.removeIf(i -> i.equals(id));
        String path = searchPage(page);
        HTMLController.editComponent(page, path, getComponents);
    }
}
