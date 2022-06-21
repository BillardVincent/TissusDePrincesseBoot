package fr.vbillard.tissusdeprincesseboot.utils;

import java.util.StringJoiner;

import org.apache.logging.log4j.util.Strings;

import com.jfoenix.controls.JFXTextField;

import fr.vbillard.tissusdeprincesseboot.filtre.specification.common.CharacterSearch;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.common.NumericSearch;
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

	public static String joinValues(FxData data) {
		if (data == null || data.getListValues() == null) {
			return null;
		}
		StringJoiner sj = new StringJoiner(", ");
		for (String s : data.getListValues()) {
			sj.add(s);
		}
		return sj.toString();
	}

	public static CharacterSearch textFieldToCharacterSearch(JFXTextField textField) {
		CharacterSearch result = null;
		if (!textField.getText().isEmpty()) {
			result = new CharacterSearch();
			result.setContains(textField.getText());
		}
		return result;
	}

	public static NumericSearch<Integer> textFieldToMaxNumericSearch(JFXTextField textField) {
		NumericSearch<Integer> result = null;
		if (!textField.getText().isEmpty()) {
			result = new NumericSearch<Integer>();
			result.setLessThanEqual(Integer.parseInt(textField.getText()));
		}
		return result;
	}

}
