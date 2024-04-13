package com.navi.backend.flexycup;

import lombok.*;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class TError {
    private String lexeme, type, description;
    private int line, col;

    @Override
    public String toString() {
        return
                "Lexema: " + lexeme +
                ", Tipo: " + type +
                "\nDescription: " + description +
                ", Linea: " + line +
                ", Columna:" + col;
    }
}
