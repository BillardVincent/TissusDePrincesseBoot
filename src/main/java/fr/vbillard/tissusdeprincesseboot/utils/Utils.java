package fr.vbillard.tissusdeprincesseboot.utils;

import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import fr.vbillard.tissusdeprincesseboot.model.Photo;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;

public class Utils {

    public static boolean isEmpty(Object... objs) {

        for (Object o : objs) {
            if (o == null || (o instanceof String && StringUtils.isBlank((String) o))
                    || (o instanceof Collection<?> && ((Collection<?>) o).isEmpty())
                    || (o instanceof Map && ((Map<?, ?>) o).isEmpty())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Vérifie si tous les paramètres ne sont pas vides
     *
     * @param objs
     * @return
     */
    public static boolean isNotEmpty(Object... objs) {

        return !isEmpty(objs);
    }
    
    public static String safeString(String value) {
    	return value == null ? Strings.EMPTY : value;
    }
    
}
