package com.navi;

import com.navi.backend.flexycup.*;

import java.io.StringReader;

public class Main {
    public static void main(String[] args) {
        String expr = """
                <ACCIones>
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
                </acciones>
                """;
        XMLLexer lex = new XMLLexer(new StringReader(expr));
        XMLParser parser = new XMLParser(lex);
        try {
            parser.parse();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}