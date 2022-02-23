package fr.vbillard.tissusdeprincesseboot.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;

import java.util.Collection;
import java.util.Map;

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
