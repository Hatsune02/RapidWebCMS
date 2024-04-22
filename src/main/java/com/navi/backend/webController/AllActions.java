package com.navi.backend.webController;
import lombok.*;

import java.util.*;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class AllActions {
    private Actions actions;
    private int type;
    private ArrayList<Parameter> parameters;
    private ActionPA actionPA;
    private ActionPE actionPE;

    public void execute(){
        if(type == 1){
            actions.createSite(parameters);
        }
        else if(type == 2){
            actions.deleteSite(parameters);
        }
        else if(type == 3){
            actions.createPage(actionPE);
        }
        else if(type == 4){
            actions.deletePage(parameters);
        }
        else if(type == 5){
            actions.editPage(actionPE);
        }
        else if(type == 6){
            actions.createComponent(actionPA);
        }
        else if(type == 7){
            actions.deleteComponent(parameters);
        }
        else if(type == 8){
            actions.editComponent(actionPA);
        }
    }
}
