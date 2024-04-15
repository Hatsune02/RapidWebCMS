package com.navi.backend.webController.objs;
import lombok.*;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class WComponent {
    private String id, page, clase;
    private String text, align, color, url, height, width, labels;

}
