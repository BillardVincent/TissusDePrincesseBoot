package fr.vbillard.tissusdeprincesseboot.controller.utils;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.utils.fx_custom_element.CustomSpinner;
import fr.vbillard.tissusdeprincesseboot.controller.utils.path.PathEnum;
import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.common.CharacterSearch;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.common.NumericSearch;
import fr.vbillard.tissusdeprincesseboot.model.Quantite;
import fr.vbillard.tissusdeprincesseboot.model.UserPref;
import fr.vbillard.tissusdeprincesseboot.model.enums.Unite;
import fr.vbillard.tissusdeprincesseboot.utils.Constants;
import fr.vbillard.tissusdeprincesseboot.utils.Utils;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.List;
import java.util.StringJoiner;

import static fr.vbillard.tissusdeprincesseboot.controller.utils.fx_custom_element.CustomSpinner.setSpinnerFocused;

@Component
public class FxUtils {

    private static final String ILLEGAL_NUMBER = "Les valeurs ne doivent pas être négatives. La valeur minimale doit "
            + "être inférieure ou égale à la valeur maximale";
    private static final String AUCUN_FILTRE = "Aucun filtre";
    private static final String CHOIX = "Choix";
    private static final char X = 'X';
    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");

    private static StageInitializer initializer;

    public FxUtils(StageInitializer initializer) {
        FxUtils.initializer = initializer;
    }

    // ---------------------------------------------
    // Sefe Properties
    // ----------------------------------------------

    public static String safePropertyToString(IntegerProperty property) {
        return property == null ? "0" : Integer.toString(property.getValue());
    }

    public static String safePropertyToString(FloatProperty property) {
        return property == null ? "0" : DECIMAL_FORMAT.format(property.getValue());
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
    // TextField field manipulation
    // ----------------------------------------------

    public static char textFieldToFirstCharOrX(TextField field) {
        if (field != null && field.getText() != null && !field.getText().trim().isEmpty()) {
            return field.getText().toUpperCase().charAt(0);
        }
        return X;
    }

    public static char textFieldToFirstCharOrX(ComboBox<String> field) {
        if (field != null && field.getValue() != null && !field.getValue().trim().isEmpty()) {
            return field.getValue().toUpperCase().charAt(0);
        }
        return X;
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
            result = new NumericSearch<>();
            result.setLessThanEqual(Integer.parseInt(textField.getText()));
        }
        return result;
    }

    // ---------------------------------------------
    // Textfield to numbers
    // ----------------------------------------------

    public static int intFromJFXTextField(JFXTextField field) {
        int value = 0;
        if (field != null && !field.getText().isEmpty()) {
            value = Integer.parseInt(field.getText());
        }
        return value;
    }

    public static float floatFromJFXTextField(JFXTextField field) {
        float value = 0f;
        if (field != null && !field.getText().isEmpty()) {
            value = Float.parseFloat(field.getText());
        }
        return value;
    }

    // ---------------------------------------------
    // Set numeric search
    // ----------------------------------------------

    public static NumericSearch<Integer> setNumericSearch(JFXTextField min, JFXTextField max) {
        return setNumericSearch(min, max, 0);
    }

    public static NumericSearch<Integer> setNumericSearch(JFXTextField min, JFXTextField max, float marge) {

        int minValueBeforeMarge = intFromJFXTextField(min);
        int maxValueBeforeMarge = intFromJFXTextField(max);

        if (minValueBeforeMarge < 0 || maxValueBeforeMarge < 0
                || (maxValueBeforeMarge != 0 && minValueBeforeMarge > maxValueBeforeMarge)) {
            throw new IllegalData(ILLEGAL_NUMBER);
        }

        NumericSearch<Integer> search = null;

        int minValue = Math.round(minValueBeforeMarge - minValueBeforeMarge * marge);
        if (minValue < 0) {
            minValue = 0;
        }

        if (maxValueBeforeMarge > 0) {
            int maxValue = Math.round(maxValueBeforeMarge + maxValueBeforeMarge * marge + 0.5f);
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

    public static NumericSearch<Float> setNumericFloatSearch(Float minValueBeforeMarge, Float maxValueBeforeMarge,
            float marge) {

        if (Utils.notNullAndLessThanZero(minValueBeforeMarge) || Utils.notNullAndLessThanZero(maxValueBeforeMarge)
                || (Utils.notNullAndNotZero(maxValueBeforeMarge) && Utils.notNullAndMoreThanEqual(minValueBeforeMarge,
                maxValueBeforeMarge))) {
            throw new IllegalData(ILLEGAL_NUMBER);
        }

        NumericSearch<Float> search = null;

        if (maxValueBeforeMarge != null) {
            float maxValue = maxValueBeforeMarge + maxValueBeforeMarge * marge;
            if (maxValue > 0) {
                search = new NumericSearch<>(null);
                search.setLessThanEqual(maxValue);
            }
        }

        if (minValueBeforeMarge != null) {
            float minValue = minValueBeforeMarge - minValueBeforeMarge * marge;
            if (minValue > 0) {
                if (search == null) {
                    search = new NumericSearch<>(null);
                }
                search.setGreaterThanEqual(minValue);
            }
        }

        return search;
    }

    // ---------------------------------------------
    // Build numeric search from boxes
    // ----------------------------------------------

    public static NumericSearch<Integer> numericSearch(JFXCheckBox lourdCBox, JFXCheckBox moyenCBox,
            JFXCheckBox legerCBox, UserPref pref) {

        NumericSearch<Integer> poidsSearch = null;

        if (!lourdCBox.isSelected() || !moyenCBox.isSelected() || !legerCBox.isSelected()) {
            if (!lourdCBox.isSelected()) {
                poidsSearch = new NumericSearch<>(null);
                if (moyenCBox.isSelected()) {
                    poidsSearch.setLessThanEqual(pref.margeHauteMoyen());
                    if (!legerCBox.isSelected()) {
                        poidsSearch.setGreaterThanEqual(pref.margeBasseMoyen());
                    }
                } else if (legerCBox.isSelected()) {
                    poidsSearch.setLessThanEqual(pref.margeHauteLeger());

                }
            } else if (!legerCBox.isSelected()) {
                poidsSearch = new NumericSearch<>(null);

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
            Label lbl, boolean allSelectedEqualsNull) {
        FxData data = new FxData();
        data.setAllSelectedEqualsNull(allSelectedEqualsNull);
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
    // RadioButton
    // ----------------------------------------------

    public static Boolean getBooleanFromRadioButtons(JFXRadioButton trueButton, JFXRadioButton falseButton,
            JFXRadioButton undetermined) {
        if (trueButton.isSelected()) {
            if (falseButton.isSelected() || undetermined.isSelected()) {
                throw new IllegalData("Un seul radio bouton doit être sélectionné");
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

    public static <T extends Number & Comparable<? super T>> void setTextFieldMaxFromNumericSearch(JFXTextField field,
            NumericSearch<T> numericSearch) {
        if (numericSearch != null && numericSearch.getLessThanEqual() != null
                && !numericSearch.getLessThanEqual().equals(0)) {
            // LessThanEqual est stocké avec Value +1
            field.setText(String.valueOf(numericSearch.getLessThanEqual().intValue() - 1));
        }
    }

    public static <T extends Number & Comparable<? super T>> void setTextFieldMinFromNumericSearch(JFXTextField field,
            NumericSearch<T> numericSearch) {
        if (numericSearch != null && numericSearch.getGreaterThanEqual() != null
                && !numericSearch.getGreaterThanEqual().equals(0)) {
            field.setText(String.valueOf(numericSearch.getGreaterThanEqual()));
        }
    }

    public static void setToggleGroupFromBoolean(Boolean search, JFXRadioButton trueBtn, JFXRadioButton falseBtn,
            JFXRadioButton undetermined) {
        trueBtn.setSelected(BooleanUtils.isTrue(search));
        falseBtn.setSelected(BooleanUtils.isFalse(search));
        undetermined.setSelected(search == null);

    }

    // ---------------------------------------------
    // Component builder helper
    // ----------------------------------------------

    public static JFXComboBox<String> buildComboBox(List<String> values, StringProperty valueSelected) {
        return buildComboBox(values, valueSelected, Strings.EMPTY);
    }

    public static JFXComboBox<String> buildComboBox(List<String> values, StringProperty valueSelected,
            String defaultSelection) {
        return buildComboBox(values, valueSelected, defaultSelection, new JFXComboBox<>());

    }

    public static JFXComboBox<String> buildComboBox(List<String> values, StringProperty valueSelected,
            String defaultSelection, JFXComboBox<String> comboBox) {
        comboBox.setItems(FXCollections.observableArrayList(values));
        comboBox.setValue((valueSelected == null || Strings.isEmpty(valueSelected.get())) ?
                defaultSelection : valueSelected.get());
        return comboBox;
    }

    public static JFXComboBox<String> buildComboBox(List<String> values, String valueSelected,
            String defaultSelection, JFXComboBox<String> comboBox) {
        comboBox.setItems(FXCollections.observableArrayList(values));
        comboBox.setValue((valueSelected == null || Strings.isEmpty(valueSelected)) ?
                defaultSelection : valueSelected);
        return comboBox;
    }

    public static JFXTextField buildSpinner(IntegerProperty value) {
        JFXTextField spinner = new JFXTextField();
        setSpinnerFocused(spinner);
        spinner.setTextFormatter(CustomSpinner.getFormatter());
        spinner.setText(FxUtils.safePropertyToString(value));
        return spinner;
    }

    public static JFXTextField buildSpinner(FloatProperty value) {
        return buildSpinner(value == null ? 0 : value.getValue());
    }

    public static JFXTextField buildSpinner(Float value) {
        JFXTextField spinner = new JFXTextField();
        setSpinnerFocused(spinner);
        spinner.setTextFormatter(CustomSpinner.getLongFormatter());
        spinner.setText(DECIMAL_FORMAT.format(value));
        return spinner;
    }

    public static void setToggleGroup(ToggleGroup toggleGroup, JFXRadioButton selected, JFXRadioButton... otherRadio) {
        selected.setToggleGroup(toggleGroup);
        selected.setSelected(true);
        for (JFXRadioButton radioButton : otherRadio) {
            radioButton.setToggleGroup(toggleGroup);
        }
    }

    public static void setToggleColor(ButtonBase... toggles) {
        for (ButtonBase t : toggles) {
            if (t instanceof JFXRadioButton radioButton) {
                radioButton.setSelectedColor((Color) Constants.colorSecondary);
            } else if (t instanceof JFXCheckBox checkBox) {
                checkBox.setCheckedColor(Constants.colorSecondary);
            }
        }

    }

    public static String setTextFromQuantity(Quantite quantity) {
        return quantity == null ? Strings.EMPTY : setTextFromQuantity(quantity.getQuantite(), quantity.getUnite());
    }

    public static String setTextFromQuantity(Float quantite, Unite unite) {
        if (quantite == null) {
            return Strings.EMPTY;
        }

        String value = quantite % 1 != 0 ? DECIMAL_FORMAT.format(quantite) : String.valueOf(quantite.intValue());

        return unite != null ? value + " " + unite.getAbbreviation() : value;
    }

    // ---------------------------------------------
    // Listeners
    // ----------------------------------------------

    /**
     * Si une valeur observable change, la BooleanProperty passe à true
     *
     * @param values
     *         TextField.textProperty(), ToggleButton.selectedProperty(), ComboBox.valueProperty()
     * @param hasChanged
     *         BooleanProperty à observer
     */
    public static void onChangeListener(ObservableValue<?>[] values, BooleanProperty hasChanged) {
        for (ObservableValue<?> ov : values) {
            ov.addListener((observable, oldValue, newValue) -> {
                if (oldValue != null && newValue != null && !oldValue.equals(newValue)) {
                    hasChanged.setValue(true);
                }
            });
        }
    }

}
