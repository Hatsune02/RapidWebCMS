package com.navi.backend.CMSParserLexer;
import java_cup.runtime.*;
import java.util.ArrayList;
import static com.navi.backend.CMSParserLexer.sym.*;
import com.navi.backend.XMLParserLexer.*;
import com.navi.UI.*;
%% //separador de area

%public
%class CMSLexer
%cup
%line
%column

/* no case-sensitive */
a = [aA]
//b = [bB]
c = [cC]
d = [dD]
e = [eE]
//f = [fF]
g = [gG]
//h = [hH]
i = [iI]
//j = [jJ]
//k = [kK]
l = [lL]
m = [mM]
n = [nN]
o = [oO]
p = [pP]
//q = [qQ]
r = [rR]
s = [sS]
t = [tT]
u = [uU]
v = [vV]
//w = [wW]
//x = [xX]
//y = [yY]
//z = [zZ]

consultar = {c}{o}{n}{s}{u}{l}{t}{a}{r}
visitas_sitio = {v}{i}{s}{i}{t}{a}{s}"_"{s}{i}{t}{i}{o}
visitas_pagina = {v}{i}{s}{i}{t}{a}{s}"_"{p}{a}{g}{i}{n}{a}
paginas_populares = {p}{a}{g}{i}{n}{a}{s}"_"{p}{o}{p}{u}{l}{a}{r}{e}{s}
componente = {c}{o}{m}{p}{o}{n}{e}{n}{t}{e}
todos = {t}{o}{d}{o}{s}

id = [_$-][_\-$a-zA-Z0-9]+

alf = [a-zA-z0-9]+

LineTerminator = \r|\n|\r\n
WhiteSpace = {LineTerminator} | [ \t\f]

%{
    private Symbol symbol(int type){
        return new Symbol(type, yyline+1,yycolumn+1);
    }
    private Symbol symbol(int type, Object value){
        return new Symbol(type, yyline+1, yycolumn+1, value);
    }
    private Symbol error(){
        //System.out.println("Error en linea: "+(yyline+1)+", columna: "+(yycolumn+1));
        TError err = new TError(yytext(), "Error Léxico", "Símbolo inválido", yyline+1, yycolumn+1);
        DashBoard.ERRORS.add(err);
        return symbol(UNKNOWN, yytext());
    }
%}

%%
//Reglas lexicas
    {consultar}                          { return symbol(CONSULTAR, yytext());        }
    {visitas_sitio}                      { return symbol(VISITAS_SITIO, yytext());          }
    {visitas_pagina}                     { return symbol(VISITAS_PAGINA, yytext());     }
    {paginas_populares}                  { return symbol(PAGINAS_P, yytext());      }
    {componente}                         { return symbol(COMPONENTE, yytext());      }
    {todos}                              { return symbol(TODOS, yytext());           }
    {id}                                 { return symbol(ID, yytext()); }
    "TITULO"                             { return symbol(TITULO, yytext());         }
    "PARRAFO"                            { return symbol(PARRAFO, yytext());         }
    "IMAGEN"                             { return symbol(IMAGEN, yytext());         }
    "VIDEO"                              { return symbol(VIDEO, yytext());         }
    "MENU"                               { return symbol(MENU, yytext());         }
    "."                                  { return symbol(PUNTO, yytext());         }
    ","                                  { return symbol(COMA, yytext());         }
    ";"                                  { return symbol(P_COMA, yytext());         }
    "\""                                 { return symbol(COMILLAS, yytext());         }
    {alf}                                { return error();   }

    {WhiteSpace}                         { /**/ }

[^]                                      { return error(); }


<<EOF>>             {return symbol(EOF); }