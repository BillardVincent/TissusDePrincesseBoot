package fr.vbillard.tissusdeprincesseboot.controller.components;

import com.jfoenix.controls.JFXTextField;
import fr.vbillard.tissusdeprincesseboot.controller.utils.ClassCssUtils;
import fr.vbillard.tissusdeprincesseboot.controller.validators.Validator;
import fr.vbillard.tissusdeprincesseboot.controller.validators.ValidatorUtils;
import javafx.application.Platform;
import javafx.geometry.Pos;
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

    LabeledFieldComponent(String name, String content, int labelSize, int fieldSize){

        if (labelSize == 0) labelSize = 1;
        if (fieldSize == 0) fieldSize = 1;
        double fieldNameSize = getWidth() * labelSize / (labelSize+fieldSize);
        double fieldWithErrorLabelSize = getWidth() * fieldSize / (labelSize+fieldSize);

        fieldName.setText(name);
        fieldName.setPrefWidth(fieldNameSize);
        fieldName.getStyleClass().add(ClassCssUtils.TITLE_ACC_2);

        fieldWithErrorLabel = new VBox();
        fieldWithErrorLabel.setPrefWidth(fieldWithErrorLabelSize);
        fieldWithErrorLabel.setFillWidth(true);
        fieldWithErrorLabel.setSpacing(5);
        fieldWithErrorLabel.setAlignment(Pos.CENTER);
        fieldWithErrorLabel.getChildren().add(textField);

        textField = new JFXTextField(content);
        textField.setPrefWidth(fieldWithErrorLabel.getWidth());
        textField.focusedProperty().addListener((observable, oldValue, newValue) -> Platform.runLater(() ->
            setErrorStyle(!ValidatorUtils.validate(validators))
        ));

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
