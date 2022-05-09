package fr.vbillard.tissusdeprincesseboot.utils;

import org.apache.logging.log4j.util.Strings;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class FxUtils {
	private FxUtils() {
	}

	public static String safePropertyToString(IntegerProperty property) {
		return property == null ? "0" : Integer.toString(property.getValue());
	}

	public static String safePropertyToString(StringProperty property) {
		return property == null ? Strings.EMPTY : property.getValue();
	}

}
