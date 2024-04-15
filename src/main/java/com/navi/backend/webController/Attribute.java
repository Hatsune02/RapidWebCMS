package com.navi.backend.webController;

import lombok.*;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class Attribute {
    private String name, value;
    int line, col;

    @Override
    public String toString() {
        return "Attribute{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", line=" + line +
                ", col=" + col +
                '}';
    }
}
