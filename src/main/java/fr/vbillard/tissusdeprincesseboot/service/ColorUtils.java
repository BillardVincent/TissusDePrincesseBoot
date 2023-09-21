package fr.vbillard.tissusdeprincesseboot.service;

import fr.vbillard.tissusdeprincesseboot.model.ColorEntity;
import javafx.scene.paint.Color;

public class ColorUtils {

    private ColorUtils(){}

    public static Color entityToColor(ColorEntity entity){
        return Color.rgb(Math.round(entity.getRed()), Math.round(entity.getGreen()), Math.round(entity.getBlue()));
    }

    public static ColorEntity colorToEntity(Color color){
        ColorEntity e = new ColorEntity();
        e.setRed((float)color.getRed());
        e.setGreen((float)color.getGreen());
        e.setBlue((float)color.getBlue());
        return e;
    }

}
