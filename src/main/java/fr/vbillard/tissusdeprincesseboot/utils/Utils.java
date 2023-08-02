package fr.vbillard.tissusdeprincesseboot.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;

import java.util.Collection;
import java.util.Map;

public class Utils {
	
	public static final String PROTOTYPE = "prototype";
	
	public static final String COMMA = ", ";

	public static final String SEPARATOR = " - ";
	public static final String OR = " ou ";


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

	public static boolean notNullAndNotZero(Integer value){
		return value != null && value != 0;
	}

	public static boolean notNullAndMoreThanZero(Integer value){
		return value != null && value > 0;
	}

	public static boolean notNullAndMoreThanZero(Float value) {return value != null && value > 0; }
	public static boolean notNullAndLessThanZero(Float value) {return value != null && value < 0; }
	public static boolean notNullAndNotZero(Float value) {return value != null && value != 0; }

	public static boolean notNullAndMoreThanEqual(Float value1, Float value2){
		return value1 != null && value2 != null && value1 >= value2;
	}

	public static void appendWithSeparator(StringBuilder sb, String separator, String value){
		if (sb.length() > 1 ){
			sb.append(separator);
		}
		sb.append(value);
	}
}
