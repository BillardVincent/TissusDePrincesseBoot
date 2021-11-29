package fr.vbillard.tissusdeprincesseboot.fxCustomElements;

import com.sun.javafx.font.Glyph;
import de.jensd.fx.glyphs.GlyphIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import fr.vbillard.tissusdeprincesseboot.utils.Constants;
import javafx.scene.paint.Paint;

public class GlyphIconUtil {

    private static final String NORMAL_ICON_SIZE = "1.5em";
    private static final String TINY_ICONE_SIZE = "1em";
    private static final String BIG_ICONE_SIZE = "2em";
    private static final String VERY_BIG_ICONE_SIZE = "4em";


    private static GlyphIcon generateIcon(GlyphIcon icon, String size, Paint color){
        icon.setSize(size);
        if (color != null ) icon.setFill(color);
        return icon;
    }
    public static GlyphIcon plusCircleTiny(){
        return generateIcon(new MaterialDesignIconView(MaterialDesignIcon.PLUS_CIRCLE), TINY_ICONE_SIZE, Constants.colorAdd);
    }

    public static GlyphIcon plusCircleNormal(){
        return generateIcon(new MaterialDesignIconView(MaterialDesignIcon.PLUS_CIRCLE), NORMAL_ICON_SIZE, Constants.colorAdd);
    }

    public static GlyphIcon editNormal(){
        return generateIcon(new FontAwesomeIconView(FontAwesomeIcon.EDIT), NORMAL_ICON_SIZE, null);
    }

    public static GlyphIcon suppressNormal(){
        return generateIcon(new FontAwesomeIconView(FontAwesomeIcon.TIMES_CIRCLE), NORMAL_ICON_SIZE, Constants.colorDelete);
    }

    public static GlyphIcon suppressTiny(){
        return generateIcon(new FontAwesomeIconView(FontAwesomeIcon.TIMES_CIRCLE), TINY_ICONE_SIZE, Constants.colorDelete);
    }

    public static GlyphIcon searchNormal(){
        return generateIcon(new FontAwesomeIconView(FontAwesomeIcon.SEARCH), NORMAL_ICON_SIZE, null);
    }

    public static GlyphIcon searchTiny(){
        return generateIcon(new FontAwesomeIconView(FontAwesomeIcon.SEARCH), TINY_ICONE_SIZE, null);
    }

    public static GlyphIcon cloneNormal(){
        return generateIcon(new FontAwesomeIconView(FontAwesomeIcon.CLONE), NORMAL_ICON_SIZE, null);
    }

    public static GlyphIcon project(){
        return generateIcon(new FontAwesomeIconView(FontAwesomeIcon.TASKS), NORMAL_ICON_SIZE, null);
    }

    public static GlyphIcon playCircle(){
        return generateIcon(new FontAwesomeIconView(FontAwesomeIcon.PLAY_CIRCLE), NORMAL_ICON_SIZE, Constants.colorAccent);
    }

    public static GlyphIcon archive(){
        return generateIcon(new FontAwesomeIconView(FontAwesomeIcon.ARCHIVE), NORMAL_ICON_SIZE, null);
    }

    public static GlyphIcon warning(){
        return generateIcon(new FontAwesomeIconView(FontAwesomeIcon.EXCLAMATION_TRIANGLE), BIG_ICONE_SIZE, Constants.colorWarning);
    }

    public static GlyphIcon picture(){
        return generateIcon(new FontAwesomeIconView(FontAwesomeIcon.PICTURE_ALT), NORMAL_ICON_SIZE, null);
    }

    public static GlyphIcon previousBig(){
        return generateIcon(new FontAwesomeIconView(FontAwesomeIcon.ARROW_CIRCLE_ALT_LEFT), VERY_BIG_ICONE_SIZE, Constants.colorAccent);
    }

    public static GlyphIcon nextBig(){
        return generateIcon(new FontAwesomeIconView(FontAwesomeIcon.ARROW_CIRCLE_ALT_RIGHT), VERY_BIG_ICONE_SIZE, Constants.colorAccent);
    }

    public static GlyphIcon expandPicture(){
        return generateIcon(new FontAwesomeIconView(FontAwesomeIcon.EXPAND), NORMAL_ICON_SIZE, null);
    }
}