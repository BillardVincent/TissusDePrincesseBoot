package fr.vbillard.tissusdeprincesseboot.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;

import java.util.Collection;
import java.util.Map;

public class Utils {
	public static final String COMMA = ", ";
	public static final String N_A = "N/A";

	public static final String SEPARATOR = " - ";
	public static final String OR = " ou ";

	private Utils(){}


	public static boolean isEmpty(Object... objs) {
		for (Object o : objs) {
			if (o == null || (o instanceof String s && StringUtils.isBlank(s))
					|| (o instanceof Collection<?> c && c.isEmpty())
					|| (o instanceof Map<?,?> m && m.isEmpty())) {
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
