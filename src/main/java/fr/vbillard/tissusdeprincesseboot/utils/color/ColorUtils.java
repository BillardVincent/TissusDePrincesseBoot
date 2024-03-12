package fr.vbillard.tissusdeprincesseboot.utils.color;

import fr.vbillard.tissusdeprincesseboot.model.ColorEntity;
import javafx.scene.paint.Color;

public class ColorUtils {

    private ColorUtils() {
    }

    public static LabColor colorFxToLab(Color c) {
        if (c == null || Color.TRANSPARENT.equals(c)) {
            return null;
        }
        RGBColor rgb = colorFxToRgb(c);
        XYZColor xyz = convertRGBtoXYZ(rgb);
        return convertXYZtoCIELab(xyz);

    }

    public static LabColor rgbToLab(RGBColor rgb) {
        if (rgb == null) {
            return null;
        }
        XYZColor xyz = convertRGBtoXYZ(rgb);
        return convertXYZtoCIELab(xyz);

    }


    public static RGBColor colorFxToRgb(Color color) {
        if (color == null || Color.TRANSPARENT.equals(color)) {
            return null;
        }
        return new RGBColor(color.getRed(), color.getGreen(), color.getBlue());
    }

    public static Color entityToColor(ColorEntity entity) {
        if (entity == null) {
            return null;
        }
        return Color.rgb(entity.getRed(), entity.getGreen(), entity.getBlue());
    }

    public static ColorEntity colorToEntity(Color color) {
        if (color == null || color.equals(Color.TRANSPARENT)) {
            return null;
        }

        ColorEntity e = new ColorEntity();
        e.setRed((int) Math.round(color.getRed() * 255.0f));
        e.setGreen((int) Math.round(color.getGreen() * 255.0f));
        e.setBlue((int) Math.round(color.getBlue() * 255.0f));
        LabColor lab = colorFxToLab(color);
        e.setL(lab.getL());
        e.setA(lab.getA());
        e.setB(lab.getB());
        return e;
    }


    public static double distance(Color target, Color control) {
        if (target == null || control == null) {
            return 1000;
        }
        LabColor firstLab = colorFxToLab(target);
        LabColor secondLab = colorFxToLab(control);
        double lDiff = firstLab.getL() - secondLab.getL();
        double aDiff = firstLab.getA() - secondLab.getA();
        double bDiff = firstLab.getB() - secondLab.getB();

        return Math.sqrt((lDiff * lDiff) + (aDiff * aDiff) + (bDiff * bDiff));
    }


    private static LabColor convertXYZtoCIELab(XYZColor xyz) {
        double var_X = xyz.X / 95.047D;
        double var_Y = xyz.Y / 100.0D;
        double var_Z = xyz.Z / 108.883D;
        var_X = pivotXYZ(var_X);
        var_Y = pivotXYZ(var_Y);
        var_Z = pivotXYZ(var_Z);
        double L = Math.max(0.0D, 116.0D * var_Y - 16.0D);
        double a = 500.0D * (var_X - var_Y);
        double b = 200.0D * (var_Y - var_Z);
        return new LabColor(L, a, b);
    }


    private static XYZColor convertRGBtoXYZ(RGBColor color) {
        double var_R = color.getRed();
        double var_G = color.getGreen();
        double var_B = color.getBlue();
        var_R = unPivotRGB(var_R);
        var_G = unPivotRGB(var_G);
        var_B = unPivotRGB(var_B);
        var_R *= 100.0D;
        var_G *= 100.0D;
        var_B *= 100.0D;
        double X = var_R * 0.4124564D + var_G * 0.3575761D + var_B * 0.1804375D;
        double Y = var_R * 0.2126729D + var_G * 0.7151522D + var_B * 0.072175D;
        double Z = var_R * 0.0193339D + var_G * 0.119192D + var_B * 0.9503041D;
        return new XYZColor(X, Y, Z);
    }

    private static double unPivotRGB(double n) {
        if (n > 0.04045D) {
            n = Math.pow((n + 0.055D) / 1.055D, 2.4D);
        } else {
            n /= 12.92D;
        }

        return n;
    }

    private static double pivotXYZ(double n) {
        if (n > 0.008856D) {
            n = Math.pow(n, 0.3333333333333333D);
        } else {
            n = 7.787037D * n + 0.13793103448275862D;
        }

        return n;
    }
}




