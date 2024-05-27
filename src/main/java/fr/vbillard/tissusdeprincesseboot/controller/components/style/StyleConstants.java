package fr.vbillard.tissusdeprincesseboot.controller.components.style;

import javafx.scene.paint.Color;

public class StyleConstants {

    private StyleConstants(){}

    public static final StyleColor COLOR_PRIMARY = new StyleColor(Color.web("#9c27b0"), Color.WHITE, StyleColorEnum.PRIMARY);
    public static final StyleColor PRIMARY_DARK =  new StyleColor(Color.web("#6a0080"), Color.WHITE, StyleColorEnum.PRIMARY_DARK);
    public static final StyleColor PRIMARY_LIGHT =  new StyleColor(Color.web("#d05ce3"), Color.BLACK, StyleColorEnum.PRIMARY_LIGHT);
    public static final StyleColor COLOR_SECONDARY = new StyleColor(Color.web("#b0273c"), Color.WHITE, StyleColorEnum.SECONDARY);
    public static final StyleColor SECONDARY_DARK =  new StyleColor(Color.web("#790017"), Color.WHITE, StyleColorEnum.SECONDARY_DARK);
    public static final StyleColor SECONDARY_LIGHT =  new StyleColor(Color.web("#e65b66"), Color.BLACK, StyleColorEnum.SECONDARY_LIGHT);
    public static final StyleColor GREY_BACKGROUND = new StyleColor(Color.web("#d0d0d0"), Color.BLACK, StyleColorEnum.GREY_BACKGROUND);
    public static final StyleColor LIGHT_BACKGROUND = new StyleColor(Color.web("#e0e0e0"), Color.BLACK, StyleColorEnum.GREY_BACKGROUND);

    public static final Color SHADOW = Color.web("#909090");
}
