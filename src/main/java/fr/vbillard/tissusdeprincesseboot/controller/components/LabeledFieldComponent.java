package fr.vbillard.tissusdeprincesseboot.controller.components;

import com.jfoenix.controls.JFXTextField;
import fr.vbillard.tissusdeprincesseboot.controller.components.style.StyleUtils;
import fr.vbillard.tissusdeprincesseboot.controller.utils.ClassCssUtils;
import fr.vbillard.tissusdeprincesseboot.controller.validators.Validator;
import fr.vbillard.tissusdeprincesseboot.controller.validators.ValidatorUtils;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Getter;

import java.util.List;

@Getter
public class LabeledFieldComponent extends HBox {

    protected JFXTextField textField;
    protected VBox fieldWithErrorLabel;
    protected Label fieldName;
    protected List<Validator> validators;

    public LabeledFieldComponent(String name, String content, int labelSize, int fieldSize){

        fieldName.setText(name);

        textField = new JFXTextField(content);
        fieldWithErrorLabel = new VBox();

        fieldWithErrorLabel.getChildren().add(textField);

        textField.focusedProperty().addListener((observable, oldValue, newValue) -> Platform.runLater(() ->
            setErrorStyle(!ValidatorUtils.validate(validators))
        ));

        StyleUtils.style(this, labelSize, fieldSize);

        setFillHeight(true);

    }

    private void setErrorStyle(boolean isValid) {
        if(isValid){
            textField.getStyleClass().removeIf(s -> s.equals(ClassCssUtils.BORDER_ERROR));
        } else if (!textField.getStyleClass().contains(ClassCssUtils.BORDER_ERROR)){
            textField.getStyleClass().add(ClassCssUtils.BORDER_ERROR);
        }
    }

    public String getText(){
        return textField.getText();
    }
}
