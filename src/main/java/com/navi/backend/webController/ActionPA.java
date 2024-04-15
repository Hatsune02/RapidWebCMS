package com.navi.backend.webController;
import lombok.*;

import java.util.ArrayList;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class ActionPA {
    private ArrayList<Parameter> parameters;
    private ArrayList<Attribute> attributes;

    @Override
    public String toString() {
        return "ActionPA{" +
                "parameters=" + parameters +
                ", attributes=" + attributes +
                '}';
    }
}
