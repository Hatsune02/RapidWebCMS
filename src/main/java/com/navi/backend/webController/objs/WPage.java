package com.navi.backend.webController.objs;
import lombok.*;

import java.util.*;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class WPage {
    private String id, cUser, cDate, eDate, eUser, title, site, father;
    private Map<String, WPage> pages;
    private ArrayList<WComponent> wComponents;

    public WPage(String id, String cUser, String cDate, String eDate, String eUser, String title, String site, String father) {
        this.id = id;
        this.cUser = cUser;
        this.cDate = cDate;
        this.eDate = eDate;
        this.eUser = eUser;
        this.title = title;
        this.site = site;
        this.father = father;
        this.pages = new HashMap<>();
        this.wComponents = new ArrayList<>();
    }
}
