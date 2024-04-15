package com.navi.backend.webController;
import lombok.*;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class Parameter {
    private String name, value;
    int line, col;

    @Override
    public String toString() {
        return "Parameter{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", line=" + line +
                ", col=" + col +
                '}';
    }
}
