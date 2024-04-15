package com.navi.backend.webController;
import lombok.*;

import java.util.ArrayList;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class ActionPE {
    private ArrayList<Parameter> parameters;
    private ArrayList<Label> labels;

    @Override
    public String toString() {
        return "ActionPE{" +
                "parameters=" + parameters +
                ", labels=" + labels +
                '}';
    }
}
