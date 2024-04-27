package fr.vbillard.tissusdeprincesseboot.controller.components;

import fr.vbillard.tissusdeprincesseboot.controller.utils.ClassCssUtils;
import fr.vbillard.tissusdeprincesseboot.controller.validators.MaxLenghtValidator;
import fr.vbillard.tissusdeprincesseboot.controller.validators.Validator;
import javafx.application.Platform;
import javafx.scene.control.Label;
import lombok.Getter;
import org.apache.logging.log4j.util.Strings;

@Getter
public class ConstrainSizeFieldComponent extends LabeledFieldComponent {

    private Label errorLabel;

    ConstrainSizeFieldComponent(String name, String content, int labelSize, int fieldSize, int textSize) {
        super(name, content, labelSize, fieldSize);
        Validator maxLengthValidator = new MaxLenghtValidator(textField, name, textSize);
        validators.add(maxLengthValidator);

        textField.focusedProperty().addListener((observable, oldValue, newValue) -> Platform.runLater(() -> {
            if ((!maxLengthValidator.validate() || isFocused()) && !getChildren().contains(errorLabel)) {
                fieldWithErrorLabel.getChildren().add(errorLabel);
            } else {
                fieldWithErrorLabel.getChildren().remove(errorLabel);
            }
        }));

        errorLabel = new Label(Strings.EMPTY + content.length() + "/" + textSize);
        errorLabel.setPrefWidth(fieldWithErrorLabel.getWidth());
        errorLabel.textProperty().addListener((observable, oldValue, newValue) -> setErrorLengthCss(maxLengthValidator.validate()));

        setFillHeight(true);

    }

    private void setErrorLengthCss(boolean lengthIsOk) {
        if (lengthIsOk) {
            errorLabel.getStyleClass().removeIf(s -> s.equals(ClassCssUtils.TEXT_ERROR));
        } else if (!errorLabel.getStyleClass().contains(ClassCssUtils.TEXT_ERROR)) {
            errorLabel.getStyleClass().add(ClassCssUtils.TEXT_ERROR);
        }
    }
}
