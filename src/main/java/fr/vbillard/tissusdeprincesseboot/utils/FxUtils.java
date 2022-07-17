package fr.vbillard.tissusdeprincesseboot.utils;

import java.util.List;
import java.util.StringJoiner;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.common.CharacterSearch;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.common.NumericSearch;
import fr.vbillard.tissusdeprincesseboot.model.UserPref;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;

@Component
public class FxUtils {

	private static final String AUCUN_FILTRE = "Aucun filtre";
	private static final String CHOIX = "Choix";
	private static StageInitializer initializer;

	public FxUtils(StageInitializer initializer) {
		this.initializer = initializer;
	}

	public static String safePropertyToString(IntegerProperty property) {
		return property == null ? "0" : Integer.toString(property.getValue());
	}

	public static String safePropertyToString(StringProperty property) {
		return property == null ? Strings.EMPTY : property.getValue();
	}

	public static String joinValues(FxData data) {
		if (data == null) {
			return null;
		}
		return joinValues(data.getListValues());
	}

	public static String joinValues(List<String> data) {
		if (data == null || data.isEmpty()) {
			return null;
		}
		StringJoiner sj = new StringJoiner(", ");
		for (String s : data) {
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

	public static int intFromJFXTextField(JFXTextField field) {
		int value = 0;
		if (!field.getText().isEmpty()) {
			value = Integer.parseInt(field.getText());
		}
		return value;
	}

	public static NumericSearch<Integer> setNumericSearch(JFXTextField min, JFXTextField max) {

		int minValue = intFromJFXTextField(min);
		int maxValue = intFromJFXTextField(max);

		if (minValue < 0 || maxValue < 0 || (maxValue != 0 && minValue >= maxValue)) {
			throw new IllegalData(
					"Les valeurs ne doivent pas être négatives. La valeur minimale doit être strictement inférieure à la valeur maximale");
		}

		NumericSearch<Integer> search = null;
		if (!max.getText().isEmpty()) {
			int value = Integer.parseInt(max.getText());
			if (value > 0) {
				search = new NumericSearch<>(null);
				search.setLessThanEqual(value);
			}
		}

		if (!min.getText().isEmpty()) {
			int value = Integer.parseInt(min.getText());
			if (value > 0) {
				if (search == null) {
					search = new NumericSearch<>(null);
				}
				search.setGreaterThanEqual(value);
			}
		}
		return search;
	}

	public static void setSelectionFromChoiceBoxModale(List<String> cboxList, List<String> selectionDestination,
			Label lbl) {
		FxData data = new FxData();
		data.setListValues(selectionDestination);
		data.setListDataCBox(cboxList);
		FxData result = initializer.displayModale(PathEnum.CHECKBOX_CHOICE, data, CHOIX);
		if (result != null) {
			setSelection(result.getListValues(), selectionDestination, lbl);
		}
	}

	public static void setSelection(List<String> values, List<String> selectionDestination, Label lbl) {

		selectionDestination.clear();

		if (values != null) {
			selectionDestination.addAll(values);
		}
		lbl.setText(StringUtils.defaultIfEmpty(joinValues(values), AUCUN_FILTRE));
	}

	public static Boolean getBooleanFromRadioButtons(JFXRadioButton trueButton, JFXRadioButton falseButton,
			JFXRadioButton undetermined) {
		if (trueButton.isSelected()) {
			if (falseButton.isSelected() || undetermined.isSelected()) {
				throw new IllegalData("Un seul radio boutton doit être sélectionné");
			}
			return true;
		} else if (falseButton.isSelected()) {
			if (undetermined.isSelected()) {
				throw new IllegalData("Un seul radio boutton doit être sélectionné");
			}
			return false;
		} else if (undetermined.isSelected()) {
			return null;
		}
		throw new IllegalData("Au moins un radio boutton doit être sélectionné");
	}

	public static NumericSearch<Integer> NumericSearch(JFXCheckBox lourdCBox, JFXCheckBox moyenCBox,
			JFXCheckBox legerCBox, JFXCheckBox ncCBox, UserPref pref) {

		NumericSearch<Integer> poidsSearch = null;

		if (!lourdCBox.isSelected() && !moyenCBox.isSelected() && !legerCBox.isSelected() && !ncCBox.isSelected()) {
			if (!lourdCBox.isSelected()) {
				poidsSearch = new NumericSearch<Integer>(null);
				if (moyenCBox.isSelected()) {
					poidsSearch.setLessThanEqual(pref.margeHauteMoyen());
				} else if (legerCBox.isSelected()) {
					poidsSearch.setLessThanEqual(pref.margeHauteLeger());

				}
			}
			if (!legerCBox.isSelected()) {
				if (poidsSearch == null) {
					poidsSearch = new NumericSearch<Integer>(null);
				}
				if (moyenCBox.isSelected()) {
					poidsSearch.setGreaterThanEqual(pref.margeBasseMoyen());
				} else if (lourdCBox.isSelected()) {
					poidsSearch.setGreaterThanEqual(pref.margeBasseLourd());
				}
			}
			if (ncCBox.isSelected()) {
				// TODO vérifier que NC marche en plus d'une sélection ?
				if (poidsSearch == null) {
					poidsSearch = new NumericSearch<Integer>(null);
				}
				poidsSearch.setEquals(0);
			}
		}
		return poidsSearch;
	}

	public static void setTextFieldFromCharacterSearch(JFXTextField field, CharacterSearch charSearch) {
		if (charSearch != null && Strings.isNotBlank(charSearch.getContains())) {
			field.setText(charSearch.getContains());
		}
	}

	public static void setTextFieldMaxFromNumericSearch(JFXTextField field, NumericSearch<Integer> numericSearch) {
		if (numericSearch != null && numericSearch.getLessThanEqual() != null
				&& numericSearch.getLessThanEqual() != 0) {
			field.setText(String.valueOf(numericSearch.getLessThanEqual()));
		}
	}

	public static void setTextFieldMinFromNumericSearch(JFXTextField field, NumericSearch<Integer> numericSearch) {
		if (numericSearch != null && numericSearch.getGreaterThanEqual() != null
				&& numericSearch.getGreaterThanEqual() != 0) {
			field.setText(String.valueOf(numericSearch.getLessThanEqual()));
		}
	}

}
