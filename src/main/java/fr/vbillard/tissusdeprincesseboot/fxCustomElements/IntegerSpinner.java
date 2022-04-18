package fr.vbillard.tissusdeprincesseboot.fxCustomElements;

import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.function.UnaryOperator;

import org.springframework.stereotype.Component;

import com.jfoenix.controls.JFXTextField;

import javafx.geometry.Pos;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.IntegerStringConverter;

@Component
public class IntegerSpinner {

	public static TextFormatter<Integer> getFormatter() {
		NumberFormat format = NumberFormat.getIntegerInstance();
		UnaryOperator<TextFormatter.Change> filter = c -> {
			if (c.isContentChange()) {
				ParsePosition parsePosition = new ParsePosition(0);
				format.parse(c.getControlNewText(), parsePosition);
				if (parsePosition.getIndex() == 0 || parsePosition.getIndex() < c.getControlNewText().length()) {
					return null;
				}
			}
			return c;
		};

		TextFormatter<Integer> numberFormater = new TextFormatter<Integer>(new IntegerStringConverter(), 0, filter);

		return numberFormater;
	}

	public static void setSpinner(JFXTextField textField) {
		textField.setTextFormatter(getFormatter());
		textField.setAlignment(Pos.CENTER_RIGHT);

	}

}
