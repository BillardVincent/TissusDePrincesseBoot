package fr.vbillard.tissusdeprincesseboot.model.enums;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public enum DimensionEnum {
    LONGUEUR("longueur", List.of(Unite.CM, Unite.MM, Unite.M, Unite.NON_RENSEIGNE)),
    POIDS("poids", List.of(Unite.G, Unite.KG, Unite.NON_RENSEIGNE)),
    SURFACE("surface", List.of(Unite.M2, Unite.NON_RENSEIGNE)),
    NON_RENSEIGNE("N/A", List.of(Unite.NON_RENSEIGNE)),
    NOMBRE("nombre", List.of(Unite.UNITE)),
    VOLUME("volume", List.of(Unite.L, Unite.ML, Unite.NON_RENSEIGNE));

    private final String label;
    private final List<Unite> unites;

    DimensionEnum(String label, List<Unite> unites) {
        this.label = label;
        this.unites = unites;
    }

    public static List<String> labels() {
        List<String> list = new ArrayList<>();
        for (DimensionEnum e : DimensionEnum.values()) {
            list.add(e.label);
        }
        return list;
    }

    public Unite getDefault() {
        for (Unite e : getUnites()) {
            if (e.getFacteur() == 1)
                return e;
        }
        return Unite.NON_RENSEIGNE;
    }

    public static DimensionEnum getEnum(String code) {
        for (DimensionEnum e : DimensionEnum.values()) {
            if (e.label.equals(code))
                return e;
        }
        return null;
    }

    public static boolean unitsAreInSameDimension(Unite unite1, Unite unite2){
        for (DimensionEnum d : DimensionEnum.values()){
            if (d.getUnites().contains(unite1) && d.getUnites().contains(unite2)){
                return true;
            }
        }
        return false;
    }
}
