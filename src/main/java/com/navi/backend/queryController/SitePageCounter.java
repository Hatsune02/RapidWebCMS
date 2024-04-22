package com.navi.backend.queryController;
import lombok.*;

@Setter @Getter @Builder
@NoArgsConstructor @AllArgsConstructor
public class SitePageCounter implements Comparable<SitePageCounter>{
    private String name;
    private int count;
    public int plus(){
        return count++;
    }

    @Override
    public String toString() {
        return name + ": " + count+"\n";
    }

    @Override
    public int compareTo(SitePageCounter o) {
        return o.count - this.count;
    }
    public String obtainName() {
        String[] fields = name.split("/");

        if (fields.length >= 2) return fields[fields.length - 1] + ", visitas: " + count+"\n";
        else return name + ", visitas: " + count+"\n";
    }
}
