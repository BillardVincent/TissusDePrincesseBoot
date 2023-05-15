package fr.vbillard.tissusdeprincesseboot.controller.components;

import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import javafx.scene.control.TextFormatter;
import javafx.util.converter.FloatStringConverter;

public class FloatSpinner extends CustomSpinner<Float> {

  @Override
  public TextFormatter<Float> getFormatter() {
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
}
