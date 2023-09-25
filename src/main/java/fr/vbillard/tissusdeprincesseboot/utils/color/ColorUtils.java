package fr.vbillard.tissusdeprincesseboot.utils.color;

import fr.vbillard.tissusdeprincesseboot.model.ColorEntity;
import javafx.scene.paint.Color;
import org.apache.commons.imaging.color.*;

public class ColorUtils {

    private static CIELab converter = CIELab.getInstance();

    private ColorUtils(){}

    public static LabColor adobeToLab(Color c){
        int rgb = convertRGBtoRGB((int)Math.round(c.getRed()* 255.0f),
        (int)Math.round(c.getGreen()* 255.0f),(int)Math.round(c.getBlue()* 255.0f));

        ColorXyz xyz = convertRGBtoXYZ(rgb);

        ColorCieLab lab = convertXYZtoCIELab(xyz);

        return new LabColor((float)lab.L, (float)lab.a, (float)lab.b);

    }

    public static Color entityToColor(ColorEntity entity){
        return Color.rgb(entity.getRed(),entity.getGreen(), entity.getBlue());
    }

    public static ColorEntity colorToEntity(Color color){
        ColorEntity e = new ColorEntity();
        e.setRed((int)Math.round(color.getRed()* 255.0f));
        e.setGreen((int)Math.round(color.getGreen()* 255.0f));
        e.setBlue((int)Math.round(color.getBlue()* 255.0f));
        LabColor lab = rgbToLab(color);
        e.setL(lab.getL());
        e.setA(lab.getA());
        e.setB(lab.getB());
        return e;
    }

    public static LabColor rgbToLab(Color color) {
        if (color == null){
            return null;
        }
        float[] lab =  converter.fromRGB( new float[]{(float)color.getRed()*250f, (float)color.getGreen()*250f, (float)color.getBlue()*250f});
        return new LabColor(lab[0], lab[1], lab[2]);
    }

    public static LabColor rgbToLab(RGBColor color) {
        if (color == null){
            return null;
        }
        float[] lab =  converter.fromRGB( new float[]{color.getRed()*250f, color.getGreen()*250f, color.getBlue()*250f});
        return new LabColor(lab[0], lab[1], lab[2]);
    }


    public static double distance(Color target, Color control) {
        if (target == null || control ==null){
            return 1000;
        }
        LabColor firstLab = adobeToLab(target);
        LabColor secondLab = adobeToLab(control);
        double lDiff = firstLab.getL() - secondLab.getL();
        double aDiff = firstLab.getA() - secondLab.getA();
        double bDiff = firstLab.getB() - secondLab.getB();

        return Math.sqrt((lDiff * lDiff) + (aDiff * aDiff) + (bDiff * bDiff));
    }

    public static RGBColor colorToRgb(Color value) {
        return new RGBColor((int)Math.round(value.getRed()*250), (int)Math.round(value.getGreen() *250),
                (int)Math.round(value.getBlue() *250));
    }


    //------------------------------------------------------------------------------
    //
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


        private static final double REF_X = 95.047D;
        private static final double REF_Y = 100.0D;
        private static final double REF_Z = 108.883D;
        private static final double XYZ_m = 7.787037D;
        private static final double XYZ_t0 = 0.008856D;



        public static ColorCieLab convertXYZtoCIELab(ColorXyz xyz) {
            return convertXYZtoCIELab(xyz.X, xyz.Y, xyz.Z);
        }

        public static ColorCieLab convertXYZtoCIELab(double X, double Y, double Z) {
            double var_X = X / 95.047D;
            double var_Y = Y / 100.0D;
            double var_Z = Z / 108.883D;
            var_X = pivotXYZ(var_X);
            var_Y = pivotXYZ(var_Y);
            var_Z = pivotXYZ(var_Z);
            double L = Math.max(0.0D, 116.0D * var_Y - 16.0D);
            double a = 500.0D * (var_X - var_Y);
            double b = 200.0D * (var_Y - var_Z);
            return new ColorCieLab(L, a, b);
        }

        public static ColorXyz convertCIELabtoXYZ(ColorCieLab cielab) {
            return convertCIELabtoXYZ(cielab.L, cielab.a, cielab.b);
        }

        public static ColorXyz convertCIELabtoXYZ(double L, double a, double b) {
            double var_Y = (L + 16.0D) / 116.0D;
            double var_X = a / 500.0D + var_Y;
            double var_Z = var_Y - b / 200.0D;
            var_Y = unPivotXYZ(var_Y);
            var_X = unPivotXYZ(var_X);
            var_Z = unPivotXYZ(var_Z);
            double X = 95.047D * var_X;
            double Y = 100.0D * var_Y;
            double Z = 108.883D * var_Z;
            return new ColorXyz(X, Y, Z);
        }

        public static ColorHunterLab convertXYZtoHunterLab(ColorXyz xyz) {
            return convertXYZtoHunterLab(xyz.X, xyz.Y, xyz.Z);
        }

        public static ColorHunterLab convertXYZtoHunterLab(double X, double Y, double Z) {
            double L = 10.0D * Math.sqrt(Y);
            double a = Y == 0.0D ? 0.0D : 17.5D * ((1.02D * X - Y) / Math.sqrt(Y));
            double b = Y == 0.0D ? 0.0D : 7.0D * ((Y - 0.847D * Z) / Math.sqrt(Y));
            return new ColorHunterLab(L, a, b);
        }

        public static ColorXyz convertHunterLabtoXYZ(ColorHunterLab cielab) {
            return convertHunterLabtoXYZ(cielab.L, cielab.a, cielab.b);
        }

        public static ColorXyz convertHunterLabtoXYZ(double L, double a, double b) {
            double var_Y = L / 10.0D;
            double var_X = a / 17.5D * L / 10.0D;
            double var_Z = b / 7.0D * L / 10.0D;
            double Y = Math.pow(var_Y, 2.0D);
            double X = (var_X + Y) / 1.02D;
            double Z = -(var_Z - Y) / 0.847D;
            return new ColorXyz(X, Y, Z);
        }

        public static int convertXYZtoRGB(ColorXyz xyz) {
            return convertXYZtoRGB(xyz.X, xyz.Y, xyz.Z);
        }

        public static int convertXYZtoRGB(double X, double Y, double Z) {
            double var_X = X / 100.0D;
            double var_Y = Y / 100.0D;
            double var_Z = Z / 100.0D;
            double var_R = var_X * 3.2404542D + var_Y * -1.5371385D + var_Z * -0.4985314D;
            double var_G = var_X * -0.969266D + var_Y * 1.8760108D + var_Z * 0.041556D;
            double var_B = var_X * 0.0556434D + var_Y * -0.2040259D + var_Z * 1.0572252D;
            var_R = pivotRGB(var_R);
            var_G = pivotRGB(var_G);
            var_B = pivotRGB(var_B);
            double R = var_R * 255.0D;
            double G = var_G * 255.0D;
            double B = var_B * 255.0D;
            return convertRGBtoRGB(R, G, B);
        }

        public static ColorXyz convertRGBtoXYZ(int rgb) {
            int r = 255 & rgb >> 16;
            int g = 255 & rgb >> 8;
            int b = 255 & rgb >> 0;
            double var_R = (double)r / 255.0D;
            double var_G = (double)g / 255.0D;
            double var_B = (double)b / 255.0D;
            var_R = unPivotRGB(var_R);
            var_G = unPivotRGB(var_G);
            var_B = unPivotRGB(var_B);
            var_R *= 100.0D;
            var_G *= 100.0D;
            var_B *= 100.0D;
            double X = var_R * 0.4124564D + var_G * 0.3575761D + var_B * 0.1804375D;
            double Y = var_R * 0.2126729D + var_G * 0.7151522D + var_B * 0.072175D;
            double Z = var_R * 0.0193339D + var_G * 0.119192D + var_B * 0.9503041D;
            return new ColorXyz(X, Y, Z);
        }

        public static ColorCmy convertRGBtoCMY(int rgb) {
            int R = 255 & rgb >> 16;
            int G = 255 & rgb >> 8;
            int B = 255 & rgb >> 0;
            double C = 1.0D - (double)R / 255.0D;
            double M = 1.0D - (double)G / 255.0D;
            double Y = 1.0D - (double)B / 255.0D;
            return new ColorCmy(C, M, Y);
        }

        public static int convertCMYtoRGB(ColorCmy cmy) {
            double R = (1.0D - cmy.C) * 255.0D;
            double G = (1.0D - cmy.M) * 255.0D;
            double B = (1.0D - cmy.Y) * 255.0D;
            return convertRGBtoRGB(R, G, B);
        }

        public static ColorCmyk convertCMYtoCMYK(ColorCmy cmy) {
            double C = cmy.C;
            double M = cmy.M;
            double Y = cmy.Y;
            double var_K = 1.0D;
            if (C < var_K) {
                var_K = C;
            }

            if (M < var_K) {
                var_K = M;
            }

            if (Y < var_K) {
                var_K = Y;
            }

            if (var_K == 1.0D) {
                C = 0.0D;
                M = 0.0D;
                Y = 0.0D;
            } else {
                C = (C - var_K) / (1.0D - var_K);
                M = (M - var_K) / (1.0D - var_K);
                Y = (Y - var_K) / (1.0D - var_K);
            }

            return new ColorCmyk(C, M, Y, var_K);
        }

        public static ColorCmy convertCMYKtoCMY(ColorCmyk cmyk) {
            return convertCMYKtoCMY(cmyk.C, cmyk.M, cmyk.Y, cmyk.K);
        }

        public static ColorCmy convertCMYKtoCMY(double C, double M, double Y, double K) {
            C = C * (1.0D - K) + K;
            M = M * (1.0D - K) + K;
            Y = Y * (1.0D - K) + K;
            return new ColorCmy(C, M, Y);
        }

        public static int convertCMYKtoRGB(int c, int m, int y, int k) {
            double C = (double)c / 255.0D;
            double M = (double)m / 255.0D;
            double Y = (double)y / 255.0D;
            double K = (double)k / 255.0D;
            return convertCMYtoRGB(convertCMYKtoCMY(C, M, Y, K));
        }

        public static ColorHsl convertRGBtoHSL(int rgb) {
            int R = 255 & rgb >> 16;
            int G = 255 & rgb >> 8;
            int B = 255 & rgb >> 0;
            double var_R = (double)R / 255.0D;
            double var_G = (double)G / 255.0D;
            double var_B = (double)B / 255.0D;
            double var_Min = Math.min(var_R, Math.min(var_G, var_B));
            boolean maxIsR = false;
            boolean maxIsG = false;
            double var_Max;
            if (var_R >= var_G && var_R >= var_B) {
                var_Max = var_R;
                maxIsR = true;
            } else if (var_G > var_B) {
                var_Max = var_G;
                maxIsG = true;
            } else {
                var_Max = var_B;
            }

            double del_Max = var_Max - var_Min;
            double L = (var_Max + var_Min) / 2.0D;
            double H;
            double S;
            if (del_Max == 0.0D) {
                H = 0.0D;
                S = 0.0D;
            } else {
                if (L < 0.5D) {
                    S = del_Max / (var_Max + var_Min);
                } else {
                    S = del_Max / (2.0D - var_Max - var_Min);
                }

                double del_R = ((var_Max - var_R) / 6.0D + del_Max / 2.0D) / del_Max;
                double del_G = ((var_Max - var_G) / 6.0D + del_Max / 2.0D) / del_Max;
                double del_B = ((var_Max - var_B) / 6.0D + del_Max / 2.0D) / del_Max;
                if (maxIsR) {
                    H = del_B - del_G;
                } else if (maxIsG) {
                    H = 0.3333333333333333D + del_R - del_B;
                } else {
                    H = 0.6666666666666666D + del_G - del_R;
                }

                if (H < 0.0D) {
                    ++H;
                }

                if (H > 1.0D) {
                    --H;
                }
            }

            return new ColorHsl(H, S, L);
        }

        public static int convertHSLtoRGB(ColorHsl hsl) {
            return convertHSLtoRGB(hsl.H, hsl.S, hsl.L);
        }

        public static int convertHSLtoRGB(double H, double S, double L) {
            double R;
            double G;
            double B;
            if (S == 0.0D) {
                R = L * 255.0D;
                G = L * 255.0D;
                B = L * 255.0D;
            } else {
                double var_2;
                if (L < 0.5D) {
                    var_2 = L * (1.0D + S);
                } else {
                    var_2 = L + S - S * L;
                }

                double var_1 = 2.0D * L - var_2;
                R = 255.0D * convertHuetoRGB(var_1, var_2, H + 0.3333333333333333D);
                G = 255.0D * convertHuetoRGB(var_1, var_2, H);
                B = 255.0D * convertHuetoRGB(var_1, var_2, H - 0.3333333333333333D);
            }

            return convertRGBtoRGB(R, G, B);
        }

        private static double convertHuetoRGB(double v1, double v2, double vH) {
            if (vH < 0.0D) {
                ++vH;
            }

            if (vH > 1.0D) {
                --vH;
            }

            if (6.0D * vH < 1.0D) {
                return v1 + (v2 - v1) * 6.0D * vH;
            } else if (2.0D * vH < 1.0D) {
                return v2;
            } else {
                return 3.0D * vH < 2.0D ? v1 + (v2 - v1) * (0.6666666666666666D - vH) * 6.0D : v1;
            }
        }

        public static ColorHsv convertRGBtoHSV(int rgb) {
            int R = 255 & rgb >> 16;
            int G = 255 & rgb >> 8;
            int B = 255 & rgb >> 0;
            double var_R = (double)R / 255.0D;
            double var_G = (double)G / 255.0D;
            double var_B = (double)B / 255.0D;
            double var_Min = Math.min(var_R, Math.min(var_G, var_B));
            boolean maxIsR = false;
            boolean maxIsG = false;
            double var_Max;
            if (var_R >= var_G && var_R >= var_B) {
                var_Max = var_R;
                maxIsR = true;
            } else if (var_G > var_B) {
                var_Max = var_G;
                maxIsG = true;
            } else {
                var_Max = var_B;
            }

            double del_Max = var_Max - var_Min;
            double H;
            double S;
            if (del_Max == 0.0D) {
                H = 0.0D;
                S = 0.0D;
            } else {
                S = del_Max / var_Max;
                double del_R = ((var_Max - var_R) / 6.0D + del_Max / 2.0D) / del_Max;
                double del_G = ((var_Max - var_G) / 6.0D + del_Max / 2.0D) / del_Max;
                double del_B = ((var_Max - var_B) / 6.0D + del_Max / 2.0D) / del_Max;
                if (maxIsR) {
                    H = del_B - del_G;
                } else if (maxIsG) {
                    H = 0.3333333333333333D + del_R - del_B;
                } else {
                    H = 0.6666666666666666D + del_G - del_R;
                }

                if (H < 0.0D) {
                    ++H;
                }

                if (H > 1.0D) {
                    --H;
                }
            }

            return new ColorHsv(H, S, var_Max);
        }

        public static int convertHSVtoRGB(ColorHsv HSV) {
            return convertHSVtoRGB(HSV.H, HSV.S, HSV.V);
        }

        public static int convertHSVtoRGB(double H, double S, double V) {
            double R;
            double G;
            double B;
            if (S == 0.0D) {
                R = V * 255.0D;
                G = V * 255.0D;
                B = V * 255.0D;
            } else {
                double var_h = H * 6.0D;
                if (var_h == 6.0D) {
                    var_h = 0.0D;
                }

                double var_i = Math.floor(var_h);
                double var_1 = V * (1.0D - S);
                double var_2 = V * (1.0D - S * (var_h - var_i));
                double var_3 = V * (1.0D - S * (1.0D - (var_h - var_i)));
                double var_r;
                double var_g;
                double var_b;
                if (var_i == 0.0D) {
                    var_r = V;
                    var_g = var_3;
                    var_b = var_1;
                } else if (var_i == 1.0D) {
                    var_r = var_2;
                    var_g = V;
                    var_b = var_1;
                } else if (var_i == 2.0D) {
                    var_r = var_1;
                    var_g = V;
                    var_b = var_3;
                } else if (var_i == 3.0D) {
                    var_r = var_1;
                    var_g = var_2;
                    var_b = V;
                } else if (var_i == 4.0D) {
                    var_r = var_3;
                    var_g = var_1;
                    var_b = V;
                } else {
                    var_r = V;
                    var_g = var_1;
                    var_b = var_2;
                }

                R = var_r * 255.0D;
                G = var_g * 255.0D;
                B = var_b * 255.0D;
            }

            return convertRGBtoRGB(R, G, B);
        }

        public static int convertCMYKtoRGB_Adobe(int sc, int sm, int sy, int sk) {
            int red = 255 - (sc + sk);
            int green = 255 - (sm + sk);
            int blue = 255 - (sy + sk);
            return convertRGBtoRGB(red, green, blue);
        }

        private static double cube(double f) {
            return f * f * f;
        }

        private static double square(double f) {
            return f * f;
        }

        public static int convertCIELabtoARGBTest(int cieL, int cieA, int cieB) {
            double R = ((double)cieL * 100.0D / 255.0D + 16.0D) / 116.0D;
            double G = (double)cieA / 500.0D + R;
            double B = R - (double)cieB / 200.0D;
            G = unPivotXYZ(G);
            R = unPivotXYZ(R);
            B = unPivotXYZ(B);
            double X = 95.047D * G;
            double Y = 100.0D * R;
            double Z = 108.883D * B;
            double var_X = X / 100.0D;
            double var_Y = Y / 100.0D;
            double var_Z = Z / 100.0D;
            double var_R = var_X * 3.2406D + var_Y * -1.5372D + var_Z * -0.4986D;
            double var_G = var_X * -0.9689D + var_Y * 1.8758D + var_Z * 0.0415D;
            double var_B = var_X * 0.0557D + var_Y * -0.204D + var_Z * 1.057D;
            var_R = pivotRGB(var_R);
            var_G = pivotRGB(var_G);
            var_B = pivotRGB(var_B);
            R = var_R * 255.0D;
            G = var_G * 255.0D;
            B = var_B * 255.0D;
            return convertRGBtoRGB(R, G, B);
        }

        private static int convertRGBtoRGB(double R, double G, double B) {
            int red = (int)Math.round(R);
            int green = (int)Math.round(G);
            int blue = (int)Math.round(B);
            red = Math.min(255, Math.max(0, red));
            green = Math.min(255, Math.max(0, green));
            blue = Math.min(255, Math.max(0, blue));
            return -16777216 | red << 16 | green << 8 | blue << 0;
        }

        private static int convertRGBtoRGB(int red, int green, int blue) {
            red = Math.min(255, Math.max(0, red));
            green = Math.min(255, Math.max(0, green));
            blue = Math.min(255, Math.max(0, blue));
            return -16777216 | red << 16 | green << 8 | blue << 0;
        }

        public static ColorCieLch convertCIELabtoCIELCH(ColorCieLab cielab) {
            return convertCIELabtoCIELCH(cielab.L, cielab.a, cielab.b);
        }

        public static ColorCieLch convertCIELabtoCIELCH(double L, double a, double b) {
            double atanba = Math.atan2(b, a);
            double h = atanba > 0.0D ? Math.toDegrees(atanba) : Math.toDegrees(atanba) + 360.0D;
            double C = Math.sqrt(square(a) + square(b));
            return new ColorCieLch(L, C, h);
        }

        public static ColorCieLab convertCIELCHtoCIELab(ColorCieLch cielch) {
            return convertCIELCHtoCIELab(cielch.L, cielch.C, cielch.h);
        }

        public static ColorCieLab convertCIELCHtoCIELab(double L, double C, double H) {
            double a = Math.cos(degree_2_radian(H)) * C;
            double b = Math.sin(degree_2_radian(H)) * C;
            return new ColorCieLab(L, a, b);
        }

        public static double degree_2_radian(double degree) {
            return degree * 3.141592653589793D / 180.0D;
        }

        public static double radian_2_degree(double radian) {
            return radian * 180.0D / 3.141592653589793D;
        }

        public static ColorCieLuv convertXYZtoCIELuv(ColorXyz xyz) {
            return convertXYZtoCIELuv(xyz.X, xyz.Y, xyz.Z);
        }

        public static ColorCieLuv convertXYZtoCIELuv(double X, double Y, double Z) {
            double var_U = 4.0D * X / (X + 15.0D * Y + 3.0D * Z);
            double var_V = 9.0D * Y / (X + 15.0D * Y + 3.0D * Z);
            double var_Y = Y / 100.0D;
            var_Y = pivotXYZ(var_Y);
            double ref_U = 0.19783982482140777D;
            double ref_V = 0.46833630293240974D;
            double L = 116.0D * var_Y - 16.0D;
            double u = 13.0D * L * (var_U - 0.19783982482140777D);
            double v = 13.0D * L * (var_V - 0.46833630293240974D);
            return new ColorCieLuv(L, u, v);
        }

        public static ColorXyz convertCIELuvtoXYZ(ColorCieLuv cielch) {
            return convertCIELuvtoXYZ(cielch.L, cielch.u, cielch.v);
        }

        public static ColorXyz convertCIELuvtoXYZ(double L, double u, double v) {
            double var_Y = (L + 16.0D) / 116.0D;
            var_Y = unPivotXYZ(var_Y);
            double ref_U = 0.19783982482140777D;
            double ref_V = 0.46833630293240974D;
            double var_U = u / (13.0D * L) + 0.19783982482140777D;
            double var_V = v / (13.0D * L) + 0.46833630293240974D;
            double Y = var_Y * 100.0D;
            double X = -(9.0D * Y * var_U) / ((var_U - 4.0D) * var_V - var_U * var_V);
            double Z = (9.0D * Y - 15.0D * var_V * Y - var_V * X) / (3.0D * var_V);
            return new ColorXyz(X, Y, Z);
        }

        public static ColorDin99Lab convertCIELabToDIN99bLab(ColorCieLab cie) {
            return convertCIELabToDIN99bLab(cie.L, cie.a, cie.b);
        }

        public static ColorDin99Lab convertCIELabToDIN99bLab(double L, double a, double b) {
            double FAC_1 = 100.0D / Math.log(2.58D);
            double kE = 1.0D;
            double kCH = 1.0D;
            double ang = Math.toRadians(16.0D);
            double L99 = 1.0D * FAC_1 * Math.log(1.0D + 0.0158D * L);
            double a99 = 0.0D;
            double b99 = 0.0D;
            if (a != 0.0D || b != 0.0D) {
                double e = a * Math.cos(ang) + b * Math.sin(ang);
                double f = 0.7D * (b * Math.cos(ang) - a * Math.sin(ang));
                double G = Math.sqrt(e * e + f * f);
                if (G != 0.0D) {
                    double k = Math.log(1.0D + 0.045D * G) / (0.045D * G);
                    a99 = k * e;
                    b99 = k * f;
                }
            }

            return new ColorDin99Lab(L99, a99, b99);
        }

        public static ColorCieLab convertDIN99bLabToCIELab(ColorDin99Lab dinb) {
            return convertDIN99bLabToCIELab(dinb.L99, dinb.a99, dinb.b99);
        }

        public static ColorCieLab convertDIN99bLabToCIELab(double L99b, double a99b, double b99b) {
            double kE = 1.0D;
            double kCH = 1.0D;
            double FAC_1 = 100.0D / Math.log(2.58D);
            double ang = Math.toRadians(16.0D);
            double hef = Math.atan2(b99b, a99b);
            double C = Math.sqrt(a99b * a99b + b99b * b99b);
            double G = (Math.exp(0.045D * C * 1.0D * 1.0D) - 1.0D) / 0.045D;
            double e = G * Math.cos(hef);
            double f = G * Math.sin(hef) / 0.7D;
            double L = (Math.exp(L99b * 1.0D / FAC_1) - 1.0D) / 0.0158D;
            double a = e * Math.cos(ang) - f * Math.sin(ang);
            double b = e * Math.sin(ang) + f * Math.cos(ang);
            return new ColorCieLab(L, a, b);
        }

        public static ColorDin99Lab convertCIELabToDIN99oLab(ColorCieLab cie) {
            return convertCIELabToDIN99oLab(cie.L, cie.a, cie.b);
        }

        public static ColorDin99Lab convertCIELabToDIN99oLab(double L, double a, double b) {
            double kE = 1.0D;
            double kCH = 1.0D;
            double FAC_1 = 100.0D / Math.log(1.39D);
            double ang = Math.toRadians(26.0D);
            double L99o = FAC_1 / 1.0D * Math.log(1.0D + 0.0039D * L);
            double a99o = 0.0D;
            double b99o = 0.0D;
            if (a != 0.0D || b != 0.0D) {
                double eo = a * Math.cos(ang) + b * Math.sin(ang);
                double fo = 0.83D * (b * Math.cos(ang) - a * Math.sin(ang));
                double Go = Math.sqrt(eo * eo + fo * fo);
                double C99o = Math.log(1.0D + 0.075D * Go) / 0.0435D;
                double heofo = Math.atan2(fo, eo);
                double h99o = heofo + ang;
                a99o = C99o * Math.cos(h99o);
                b99o = C99o * Math.sin(h99o);
            }

            return new ColorDin99Lab(L99o, a99o, b99o);
        }

        public static ColorCieLab convertDIN99oLabToCIELab(ColorDin99Lab dino) {
            return convertDIN99oLabToCIELab(dino.L99, dino.a99, dino.b99);
        }

        public static ColorCieLab convertDIN99oLabToCIELab(double L99o, double a99o, double b99o) {
            double kE = 1.0D;
            double kCH = 1.0D;
            double FAC_1 = 100.0D / Math.log(1.39D);
            double ang = Math.toRadians(26.0D);
            double L = (Math.exp(L99o * 1.0D / FAC_1) - 1.0D) / 0.0039D;
            double h99ef = Math.atan2(b99o, a99o);
            double heofo = h99ef - ang;
            double C99 = Math.sqrt(a99o * a99o + b99o * b99o);
            double G = (Math.exp(0.0435D * C99) - 1.0D) / 0.075D;
            double e = G * Math.cos(heofo);
            double f = G * Math.sin(heofo);
            double a = e * Math.cos(ang) - f / 0.83D * Math.sin(ang);
            double b = e * Math.sin(ang) + f / 0.83D * Math.cos(ang);
            return new ColorCieLab(L, a, b);
        }

        private static double pivotRGB(double n) {
            if (n > 0.0031308D) {
                n = 1.055D * Math.pow(n, 0.4166666666666667D) - 0.055D;
            } else {
                n = 12.92D * n;
            }

            return n;
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

        private static double unPivotXYZ(double n) {
            double nCube = Math.pow(n, 3.0D);
            if (nCube > 0.008856D) {
                n = nCube;
            } else {
                n = (n - 0.13793103448275862D) / 7.787037D;
            }

            return n;
        }
    }




