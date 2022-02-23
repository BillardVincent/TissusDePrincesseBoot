package fr.vbillard.tissusdeprincesseboot.utils;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.SpinnerValueFactory;
import org.apache.logging.log4j.util.Strings;

public class FxUtils {
    private FxUtils(){}

    public static String safePropertyToString(IntegerProperty property) {
       return property == null ? "0" : Integer.toString(property.getValue());
    }

    public static String safePropertyToString(StringProperty property) {
        return property ==  null ? Strings.EMPTY : property.getValue();
    }

    public static SpinnerValueFactory setSpinner(IntegerProperty property){
        return new SpinnerValueFactory.IntegerSpinnerValueFactory( 0, Integer.MAX_VALUE,
                property == null ? 0 : property.getValue());
    }
}
