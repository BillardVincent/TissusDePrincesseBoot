package fr.vbillard.tissusdeprincesseboot.controller.utils.fx_custom_element;

import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

@Component
public class CustomSpinner {

	private CustomSpinner(){

	}

	public static TextFormatter<Integer> getFormatter() {
		NumberFormat format = NumberFormat.getIntegerInstance();
		UnaryOperator<TextFormatter.Change> filter = c -> {
			if (c.isContentChange()) {
				ParsePosition parsePosition = new ParsePosition(0);
				format.parse(c.getControlNewText(), parsePosition);
				if (/* parsePosition.getIndex() == 0 || */ parsePosition.getIndex() < c.getControlNewText().length()) {
					return null;
				}
			}
			return c;
		};

		return new TextFormatter<>(new IntegerStringConverter(), 0, filter);
	}

	public static void setSpinner(JFXTextField textField) {
		textField.setTextFormatter(getFormatter());
		textField.setAlignment(Pos.CENTER_RIGHT);
		setSpinnerFocused(textField);

	}
	
	public static TextFormatter<Float> getLongFormatter() {
		DecimalFormat format = new DecimalFormat("#.0");
		UnaryOperator<TextFormatter.Change> filter = c -> {
			if (c.isContentChange()) {
				ParsePosition parsePosition = new ParsePosition(0);
				boolean match = Pattern.matches("\\d+.?\\d{0,2}", c.getControlNewText());				
				format.parse(c.getControlNewText(), parsePosition);
				if (!match) {
					return null;
				}
			}
			return c;
		};

		return new TextFormatter<>(new FloatStringConverter(), 0f, filter);

	}

	public static void setLongSpinner(JFXTextField textField) {
		textField.setTextFormatter(getLongFormatter());
		textField.setAlignment(Pos.CENTER_RIGHT);
		setSpinnerFocused(textField);
	}

	public static void setSpinnerFocused(JFXTextField textField){
		textField.focusedProperty().addListener((observable, oldValue, newValue) -> Platform.runLater(() -> {
			if (textField.isFocused() && !textField.getText().isEmpty()) {
				textField.selectAll();
			}
		}));
	}

}
