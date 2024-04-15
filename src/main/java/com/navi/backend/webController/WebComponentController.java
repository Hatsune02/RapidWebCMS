package com.navi.backend.webController;

import com.navi.backend.webController.objs.WComponent;
import static com.navi.backend.webController.Actions.*;

public class WebComponentController {

    public void createComponent(ActionPA action){
        String id = "", page = "", clase = "";
        boolean valid = true;
        for(Parameter p : action.getParameters()){
            if(p.getName().equals("ID")) id = p.getValue();
            else if(p.getName().equals("PAGINA")) page = p.getValue();
            else if(p.getName().equals("CLASE")) clase = p.getValue();
            else{
                System.out.println("Invalid Parameter : " + p.getName() + ", linea: " + p.getLine() + ", columna: " + p.getCol());
                valid = false;
                break;
            }
        }
        if(clase.equals("TITULO") || clase.equals("PARRAFO")){
            String text="", align="", color="";
            for(Attribute a : action.getAttributes()){
                if(a.getName().equals("TEXTO")) text = a.getValue();
                else if(a.getName().equals("ALINEACION")) align = a.getValue();
                else if(a.getName().equals("COLOR")) color = a.getValue();
                else{
                    System.out.println("Invalid Attribute : " + a.getName() + ", linea: " + a.getLine() + ", columna: " + a.getCol());
                    valid = false;
                    break;
                }
            }
            if(valid && !text.isEmpty()){
                String component;
                StringBuilder style = new StringBuilder();
                if (!align.isEmpty()) style.append("text-align: ").append(align).append("; ");
                if (!color.isEmpty()) style.append("color: ").append(color).append("; ");

                if(clase.equals("TITULO")) component = "<h1 style=\""+style+"\">" + text + "</h1>";
                else component = "<p style=\""+style+"\">" + text + "</p>";
                WComponent c = new WComponent(id, page, clase, text, align, color,"","","","");
                getComponents.add(c);
                String path = searchPage(page);
                HTMLController.addComponent(path, component);
                ids.add(id);
            }
        }
        else if(clase.equals("IMAGEN")){
            String url="", align="", height="", width="";
            for(Attribute a : action.getAttributes()){
                if(a.getName().equals("ORIGEN")) url = a.getValue();
                else if(a.getName().equals("ALINEACION")) align = a.getValue();
                else if(a.getName().equals("ALTURA")) height = a.getValue();
                else if(a.getName().equals("ANCHO")) width = a.getValue();
                else{
                    System.out.println("Invalid Attribute : " + a.getName() + ", linea: " + a.getLine() + ", columna: " + a.getCol());
                    valid = false;
                    break;
                }
            }
            if(valid && !url.isEmpty() && !height.isEmpty() && !width.isEmpty()){
                String component;
                StringBuilder style = new StringBuilder();
                style.append("height: ").append(height).append("; width: ").append(width).append("; ");
                if (!align.isEmpty()) style.append("text-align: ").append(align).append("; ");
                component = "<img src=\""+url+"\" style=\""+style+"\">";
                WComponent c = new WComponent(id, page, clase, "", align, "",url,height,width,"");
                getComponents.add(c);
                String path = searchPage(page);
                HTMLController.addComponent(path, component);
                ids.add(id);
            }
        }
        else if(clase.equals("VIDEO")){
            String url="", height="", width="";
            for(Attribute a : action.getAttributes()){
                if(a.getName().equals("ORIGEN")) url = a.getValue();
                else if(a.getName().equals("ALTURA")) height = a.getValue();
                else if(a.getName().equals("ANCHO")) width = a.getValue();
                else{
                    System.out.println("Invalid Attribute : " + a.getName() + ", linea: " + a.getLine() + ", columna: " + a.getCol());
                    valid = false;
                    break;
                }
            }
            if(valid && !url.isEmpty() && !height.isEmpty() && !width.isEmpty()){
                String component;
                component = "<video height=\"" + height + "\" width=\"" + width + "\">\n<source src\"" + url + "\" type=\"video/mp4\">\n</video>";
                WComponent c = new WComponent(id, page, clase, "", "", "",url,height,width,"");
                getComponents.add(c);
                String path = searchPage(page);
                HTMLController.addComponent(path, component);
                ids.add(id);
            }
        }
        else if(clase.equals("MENU")){
            String father="", labels="";
            for(Attribute a : action.getAttributes()){
                if(a.getName().equals("PADRE")) father = a.getValue();
                else if(a.getName().equals("ETIQUETAS")) labels = a.getValue();
                else{
                    System.out.println("Invalid Attribute : " + a.getName() + ", linea: " + a.getLine() + ", columna: " + a.getCol());
                    valid = false;
                    break;
                }
            }
        }
        else{
            System.out.println("Invalid Attribute : " + clase);
        }
        System.out.println("Creating Component");
    }
    public void deleteComponent(ActionPA action){
        System.out.println("Deleting Component");
    }
    public void editComponent(ActionPA action){
        System.out.println("Editing Component");
    }
}
