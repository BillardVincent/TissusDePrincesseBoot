package fr.vbillard.tissusdeprincesseboot.utils;

import java.util.List;
import java.util.StringJoiner;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.common.CharacterSearch;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.common.NumericSearch;
import fr.vbillard.tissusdeprincesseboot.fx_custom_element.CustomSpinner;
import fr.vbillard.tissusdeprincesseboot.model.UserPref;
import fr.vbillard.tissusdeprincesseboot.utils.path.PathEnum;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.Label;

@Component
public class FxUtils {

	private static final String ILLEGAL_NUMBER = "Les valeurs ne doivent pas être négatives. La valeur minimale doit être strictement inférieure à la valeur maximale";
	private static final String AUCUN_FILTRE = "Aucun filtre";
	private static final String CHOIX = "Choix";
	private static StageInitializer initializer;

	public FxUtils(StageInitializer initializer) {
		this.initializer = initializer;
	}

	// ---------------------------------------------
	// Sefe Properties
	// ----------------------------------------------

	public static String safePropertyToString(IntegerProperty property) {
		return property == null ? "0" : Integer.toString(property.getValue());
	}

	public static String safePropertyToString(FloatProperty property) {
		return property == null ? "0" : Float.toString(property.getValue());
	}

	public static String safePropertyToString(StringProperty property) {
		return property == null ? Strings.EMPTY : property.getValue();
	}

	// ---------------------------------------------
	// Join values
	// ----------------------------------------------

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

	// ---------------------------------------------
	// TextField to Search
	// ----------------------------------------------

	public static CharacterSearch textFieldToCharacterSearch(JFXTextField textField) {
		CharacterSearch result = null;
		if (!textField.getText().isEmpty()) {
			result = new CharacterSearch();
			result.setContains(textField.getText());
		}
		return result;
	}

	public static CharacterSearch textFieldToCharacterSearchMultiple(JFXTextField textField) {
		CharacterSearch result = null;
		if (!textField.getText().isEmpty()) {
			result = new CharacterSearch();
			result.setContainsMultiple(textField.getText());
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

	// ---------------------------------------------
	// Textfield to numbers
	// ----------------------------------------------

	public static int intFromJFXTextField(JFXTextField field) {
		int value = 0;
		if (!field.getText().isEmpty()) {
			value = Integer.parseInt(field.getText());
		}
		return value;
	}

	public static float floatFromJFXTextField(JFXTextField field) {
		float value = 0f;
		if (!field.getText().isEmpty()) {
			value = Float.parseFloat(field.getText());
		}
		return value;
	}

	// ---------------------------------------------
	// Set numeric search
	// ----------------------------------------------

	public static NumericSearch<Integer> setNumericSearch(JFXTextField min, JFXTextField max) {
		return setNumericSearch(min, min, 0);
	}

	public static NumericSearch<Integer> setNumericSearch(JFXTextField min, JFXTextField max, float marge) {

		int minValueBeforeMarge = intFromJFXTextField(min);
		int maxValueBeforeMarge = intFromJFXTextField(max);

		if (minValueBeforeMarge < 0 || maxValueBeforeMarge < 0
				|| (maxValueBeforeMarge != 0 && maxValueBeforeMarge >= maxValueBeforeMarge)) {
			throw new IllegalData(ILLEGAL_NUMBER);
		}

		NumericSearch<Integer> search = null;

		int minValue = Math.round(minValueBeforeMarge - minValueBeforeMarge * marge);
		if (minValue < 0) {
			minValue = 0;
		}

		int maxValue = Math.round(maxValueBeforeMarge + maxValueBeforeMarge * marge + 0.5f);

		if (maxValue > 0) {
			search = new NumericSearch<>(null);
			search.setLessThanEqual(maxValue);

		}

		if (minValue > 0) {
			if (search == null) {
				search = new NumericSearch<>(null);
			}
			search.setGreaterThanEqual(minValue);

		}
		return search;
	}

	public static NumericSearch<Float> setNumericFloatSearch(JFXTextField min, JFXTextField max) {
		return setNumericFloatSearch(min, max, 0);
	}

	public static NumericSearch<Float> setNumericFloatSearch(JFXTextField min, JFXTextField max, float marge) {

		float minValueBeforeMarge = floatFromJFXTextField(min);
		float maxValueBeforeMarge = floatFromJFXTextField(max);

		return setNumericFloatSearch(minValueBeforeMarge, maxValueBeforeMarge, marge);
	}

	public static NumericSearch<Float> setNumericFloatSearch(float minValueBeforeMarge, float maxValueBeforeMarge,
			float marge) {

		if (minValueBeforeMarge < 0 || maxValueBeforeMarge < 0
				|| (maxValueBeforeMarge != 0 && maxValueBeforeMarge >= maxValueBeforeMarge)) {
			throw new IllegalData(ILLEGAL_NUMBER);
		}

		NumericSearch<Float> search = null;

		float minValue = minValueBeforeMarge - minValueBeforeMarge * marge;
		if (minValue < 0) {
			minValue = 0;
		}
		float maxValue = maxValueBeforeMarge + maxValueBeforeMarge * marge;

		if (maxValue > 0) {
			search = new NumericSearch<>(null);
			search.setLessThanEqual(maxValue);
		}

		if (minValue > 0) {
			if (search == null) {
				search = new NumericSearch<>(null);
			}
			search.setGreaterThanEqual(minValue);
		}

		return search;
	}

	// ---------------------------------------------
	// Build numeric search from boxes
	// ----------------------------------------------

	public static NumericSearch<Integer> NumericSearch(JFXCheckBox lourdCBox, JFXCheckBox moyenCBox,
			JFXCheckBox legerCBox, UserPref pref) {

		NumericSearch<Integer> poidsSearch = null;

		if (!lourdCBox.isSelected() || !moyenCBox.isSelected() || !legerCBox.isSelected()) {
			if (!lourdCBox.isSelected()) {
				poidsSearch = new NumericSearch<Integer>(null);
				if (moyenCBox.isSelected()) {
					poidsSearch.setLessThanEqual(pref.margeHauteMoyen());
					if (!legerCBox.isSelected()) {
						poidsSearch.setGreaterThanEqual(pref.margeBasseMoyen());
					}
				} else if (legerCBox.isSelected()) {
					poidsSearch.setLessThanEqual(pref.margeHauteLeger());

				}
			} else if (!legerCBox.isSelected()) {
				poidsSearch = new NumericSearch<Integer>(null);

				if (moyenCBox.isSelected()) {
					poidsSearch.setGreaterThanEqual(pref.margeBasseMoyen());
				} else if (lourdCBox.isSelected()) {
					poidsSearch.setGreaterThanEqual(pref.margeBasseLourd());
				}
			}
		}
		return poidsSearch;
	}

	// ---------------------------------------------
	// Component builder helper
	// ----------------------------------------------

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

	// ---------------------------------------------
	// ???
	// ----------------------------------------------

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

	// ---------------------------------------------
	// Set from search
	// ----------------------------------------------

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

	// ---------------------------------------------
	// Component builder helper
	// ----------------------------------------------

	public static JFXComboBox<String> buildComboBox(List<String> values, StringProperty valueSelected) {
		return buildComboBox(values, valueSelected, Strings.EMPTY);
	}

	public static JFXComboBox<String> buildComboBox(List<String> values, StringProperty valueSelected,
			String defaultSelection) {
		return buildComboBox(values, valueSelected,defaultSelection, new JFXComboBox<String>());
		
	}
	
	public static JFXComboBox<String> buildComboBox(List<String> values, StringProperty valueSelected,
			String defaultSelection, JFXComboBox<String> comboBox) {
		comboBox.setItems(FXCollections.observableArrayList(values));
		comboBox.setValue( (valueSelected == null || Strings.isEmpty(valueSelected.get())) ?
				defaultSelection : valueSelected.get());
		return comboBox;	
	}

	public static JFXTextField buildSpinner(IntegerProperty value) {
		JFXTextField spinner = new JFXTextField();
		spinner.setTextFormatter(CustomSpinner.getFormatter());
		spinner.setText(FxUtils.safePropertyToString(value));
		return spinner;
	}
	
	public static JFXTextField buildSpinner(FloatProperty value) {
		JFXTextField spinner = new JFXTextField();
		spinner.setTextFormatter(CustomSpinner.getLongFormatter());
		spinner.setText(FxUtils.safePropertyToString(value));
		return spinner;
	}

}
