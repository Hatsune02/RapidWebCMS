package com.navi;
import com.navi.backend.webController.*;
import com.navi.backend.webController.objs.WebSite;

import java.io.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        /*String expr = """
                
                <accion nombre="AGREGAR_COMPONENTE">
                	<parametros>
                          		<parametro nombre="ID">
                              		[ab]
                        		</parametro>
                          		<parametro nombre="PAGINA">
                              		[CENTRAR]
                        		</parametro>
                          		<parametro nombre="CLASE">
                              		[TITULO]
                        		</parametro>
                	</parametros>
                 	<atributos>
                    		<atributo nombre="TEXTO">
                			[Este es el texto que aparece en el titulo :) ]
                		</atributo>
                            <atributo nombre="COLOR">
                			[#5A5A5A]
                		</atributo>
                	</atributos>
                </accion>
                
                """;
        XMLLexer lex = new XMLLexer(new StringReader(expr));
        XMLParser parser = new XMLParser(lex);
        try {
            parser.parse();
        } catch (Exception e) {
            System.out.println(e);
        }*/

        Actions actions = new Actions();

        ArrayList<Parameter> parameters = new ArrayList<>();
        ArrayList<Parameter> p1 = new ArrayList<>();
        ArrayList<Parameter> p2 = new ArrayList<>();
        ArrayList<Parameter> p3 = new ArrayList<>();
        ArrayList<Parameter> p4 = new ArrayList<>();

        ArrayList<Label> l = new ArrayList<>();
        ArrayList<Attribute> a = new ArrayList<>();
        ArrayList<Attribute> b = new ArrayList<>();

        parameters.add(new Parameter("ID","_sitio",1,1));
        parameters.add(new Parameter("USUARIO_CREACION","xd",1,1));
        p2.add(new Parameter("ID","_pagina1",1,1));
        p2.add(new Parameter("SITIO","_sitio",1,1));
        p2.add(new Parameter("USUARIO_CREACION","xd",1,1));
        p2.add(new Parameter("TITULO","pag1",1,1));

        p1.add(new Parameter("ID","_pagina2",1,1));
        p1.add(new Parameter("SITIO","_sitio",1,1));
        p1.add(new Parameter("USUARIO_CREACION","xd",1,1));
        p1.add(new Parameter("TITULO","pag2",1,1));
        p1.add(new Parameter("PADRE","_pagina1",1,1));


        p3.add(new Parameter("ID","_comp1",1,1));
        p3.add(new Parameter("PAGINA","_pagina2",1,1));
        p3.add(new Parameter("CLASE","TITULO",1,1));

        p4.add(new Parameter("ID","_sitio",1,1));
        //p4.add(new Parameter("PAGINA","_pagina1",1,1));

        a.add(new Attribute("TEXTO","Tremendo titulo xd",1,1));

        b.add(new Attribute("TEXTO","TITULO EDITADO",1,1));

        actions.createSite(parameters);
        /*actions.createPage(new ActionPE(p2,l));
        actions.createPage(new ActionPE(p1,l));
        actions.createComponent(new ActionPA(p3, a));
        actions.editComponent(new ActionPA(p3, b));
        actions.editPage(new ActionPE(p1,l));*/
        //actions.deleteComponent(p4);
        //actions.deletePage(p4);
        //actions.deleteSite(p4);

    }
}