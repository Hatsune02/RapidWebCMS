package com.navi.backend.XMLParserLexer;

import lombok.*;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class TError {
    String lexeme, type, description;
    int line, col;

    @Override
    public String toString() {
        return
                "Lexema: " + lexeme +
                ", Tipo: " + type +
                ", Description: " + description +
                ", Linea: " + line +
                ", Columna:" + col;
    }
}
