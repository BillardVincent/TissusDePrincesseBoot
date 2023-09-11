package fr.vbillard.tissusdeprincesseboot.controller.components;

import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.function.UnaryOperator;

import javafx.scene.control.TextFormatter;
import javafx.util.converter.IntegerStringConverter;

public class IntegerSpinner extends CustomSpinner<Integer>{

  @Override
  public TextFormatter<Integer> getFormatter() {
    NumberFormat format = NumberFormat.getIntegerInstance();
    UnaryOperator<TextFormatter.Change> filter = c -> {
      if (c.isContentChange()) {
        ParsePosition parsePosition = new ParsePosition(0);
        format.parse(c.getControlNewText(), parsePosition);
        if (/* parsePosition.getIndex() == 0 || */ parsePosition.getIndex() < c.getControlNewText().length() || parsePosition.getIndex() > 8) {
          return null;
        }
      }
      return c;
    };

    return new TextFormatter<>(new IntegerStringConverter(), 0, filter);  }
}
