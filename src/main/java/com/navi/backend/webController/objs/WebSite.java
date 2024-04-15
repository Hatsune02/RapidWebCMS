package com.navi.backend.webController.objs;
import lombok.*;

import java.util.*;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class WebSite {
    private String id, cUser, cDate, eDate, eUser;
    private Map<String, WPage> wPages;

    public WebSite(String id, String cUser, String cDate, String eDate, String eUser) {
        this.id = id;
        this.cUser = cUser;
        this.cDate = cDate;
        this.eDate = eDate;
        this.eUser = eUser;
        this.wPages = new HashMap<>();
    }
}
