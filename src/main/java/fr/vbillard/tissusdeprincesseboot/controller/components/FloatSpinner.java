package fr.vbillard.tissusdeprincesseboot.controller.components;

import javafx.scene.control.TextFormatter;
import javafx.util.converter.FloatStringConverter;

import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class FloatSpinner extends CustomSpinner<Float> {

  @Override
  public TextFormatter<Float> getFormatter() {
    DecimalFormat format = new DecimalFormat("#.0");
    UnaryOperator<TextFormatter.Change> filter = c -> {
      if (c.isContentChange()) {
        ParsePosition parsePosition = new ParsePosition(0);
        boolean match = Pattern.matches("\\d+.?\\d{0,2}", c.getControlNewText());
        format.parse(c.getControlNewText(), parsePosition);
        if (!match || parsePosition.getIndex() > 8) {
          return null;
        }
      }
      return c;
    };

    return new TextFormatter<>(new FloatStringConverter(), 0f, filter);
  }
}
