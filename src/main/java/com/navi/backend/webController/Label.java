package com.navi.backend.webController;

import lombok.*;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class Label {
    private String value;
    int line, col;

    @Override
    public String toString() {
        return "Label{" +
                "value='" + value + '\'' +
                ", line=" + line +
                ", col=" + col +
                '}';
    }
}
