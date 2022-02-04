package fr.vbillard.tissusdeprincesseboot.fxCustomElements;

import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.function.UnaryOperator;

import org.springframework.stereotype.Component;

import javafx.scene.control.TextFormatter;
import javafx.util.converter.IntegerStringConverter;

@Component
public class IntegerSpinner {
	
	public TextFormatter<Integer> getFormatter(){
		NumberFormat format = NumberFormat.getIntegerInstance();
		UnaryOperator<TextFormatter.Change> filter = c -> {
		    if (c.isContentChange()) {
		        ParsePosition parsePosition = new ParsePosition(0);
		        // NumberFormat evaluates the beginning of the text
		        format.parse(c.getControlNewText(), parsePosition);
		        if (parsePosition.getIndex() == 0 ||
		                parsePosition.getIndex() < c.getControlNewText().length()) {
		            // reject parsing the complete text failed
		            return null;
		        }
		    }
		    return c;
		};
		
		TextFormatter<Integer> priceFormatter = new TextFormatter<Integer>(
		        new IntegerStringConverter(), 0, filter);
		
		return priceFormatter;
	}
	
	
}
