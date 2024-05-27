package fr.vbillard.tissusdeprincesseboot.controller.components.style;

import fr.vbillard.tissusdeprincesseboot.controller.components.LabelValue;
import fr.vbillard.tissusdeprincesseboot.controller.components.LabeledFieldComponent;
import fr.vbillard.tissusdeprincesseboot.controller.components.cards.Card;
import fr.vbillard.tissusdeprincesseboot.controller.components.cards.CardElement;
import fr.vbillard.tissusdeprincesseboot.controller.utils.ClassCssUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class StyleUtils {

    private StyleUtils(){}

    //private static Font font = Font.loadFont("file:resources/fonts/TenaliRamakrishna-Regular.ttf", 45);

    private static final String FONT_FAMILY = "verdana";

    public static void styleTitle1(Label label, boolean cardTitle){
        StyleColor styleColor = StyleConstants.COLOR_PRIMARY;
        if (cardTitle) {
            label.setBackground(new Background(
                    new BackgroundFill(styleColor.color(), new CornerRadii(6, 6, 0, 0, false), Insets.EMPTY)));
        }else{
            label.setBackground(new Background(new BackgroundFill(styleColor.color(), CornerRadii.EMPTY, Insets.EMPTY)));
        }
        label.setTextFill(styleColor.contrast());
        label.setFont(Font.font(FONT_FAMILY, FontWeight.BOLD, 14));
    }

    public static void styleTitle2(Label label){
        StyleColor styleColor = StyleConstants.COLOR_PRIMARY;
        label.setTextFill(styleColor.color());
        label.setFont(Font.font(FONT_FAMILY, FontWeight.BOLD, 14));
    }

    public static void styleTitle3(Label label){
        StyleColor styleColor = StyleConstants.COLOR_SECONDARY;
        label.setTextFill(styleColor.color());
        label.setFont(Font.font(FONT_FAMILY, FontWeight.BOLD, 13));
    }

    public static void styleTitle4(Label label){
        StyleColor styleColor = StyleConstants.PRIMARY_LIGHT;
        label.setTextFill(styleColor.color());
        label.setFont(Font.font(FONT_FAMILY, FontWeight.BOLD, 12));
    }

    public static void styleTitle5(Label label){
        label.setFont(Font.font(FONT_FAMILY, FontWeight.BOLD, 12));
    }

    public  static void styleLabel(Label label){
        StyleColor styleColor = StyleConstants.PRIMARY_DARK;
        label.setTextFill(styleColor.color());
        label.setFont(Font.font(FONT_FAMILY, FontWeight.NORMAL, 13));
    }

    public static void style(Card card){

        card.getImage().setPreserveRatio(true);
        card.getImage().setPickOnBounds(true);
        card.getTitreBox().setFillWidth(true);
        card.getTitreBox().setAlignment(Pos.CENTER);
        card.getImageBox().setFillWidth(true);
        card.getImageBox().setAlignment(Pos.CENTER);

        card.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(6, false), Insets.EMPTY)));
        styleTitle1(card.getTitreLbl(), true);
        card.setFillWidth(true);
        card.getTitreLbl().setAlignment(Pos.CENTER);
        card.getTitreLbl().prefWidthProperty().bind(card.widthProperty());
        card.getSousTitreBox().setFillWidth(true);
        card.getSousTitreBox().setSpacing(5);
        card.getSousTitreBox().setAlignment(Pos.CENTER);
        card.getContent().setSpacing(5);
        card.getContent().setAlignment(Pos.TOP_CENTER);
        card.getContent().setFillWidth(true);
        card.getFooter().setFillWidth(true);
        card.setEffect(new DropShadow(BlurType.GAUSSIAN, StyleConstants.SHADOW, 10, 0.5, 0.0, 0.0));
    }

    public static void style(CardElement cardElement){
        styleTitle4(cardElement.getTitreLbl());
        cardElement.getTitreLbl().setAlignment(Pos.CENTER);
        cardElement.getTitreLbl().setTextFill(StyleConstants.PRIMARY_LIGHT.color());
        cardElement.getContent().setSpacing(5);
        cardElement.getContent().setAlignment(Pos.CENTER);
        cardElement.getContent().setFillWidth(true);
    }

    public static void style(LabelValue labelValue) {
        styleLabel(labelValue.getLabel());
    }

    public static void style(LabeledFieldComponent labeledFieldComponent, int labelSize, int fieldSize){
        if (labelSize == 0 && fieldSize == 0) {
            fieldSize = labelSize = 1;
        }
        else if (labelSize == 0) labelSize = fieldSize;
        else if (fieldSize == 0) fieldSize = labelSize;
        double fieldNameSize = labeledFieldComponent.getWidth() * labelSize / (labelSize+fieldSize);
        double fieldWithErrorLabelSize = labeledFieldComponent.getWidth() * fieldSize / (labelSize+fieldSize);

        labeledFieldComponent.getFieldName().setPrefWidth(fieldNameSize);
        labeledFieldComponent.getFieldName().getStyleClass().add(ClassCssUtils.TITLE_ACC_2);

        labeledFieldComponent.getFieldWithErrorLabel().setPrefWidth(fieldWithErrorLabelSize);
        labeledFieldComponent.getFieldWithErrorLabel().setFillWidth(true);
        labeledFieldComponent.getFieldWithErrorLabel().setSpacing(5);
        labeledFieldComponent.getFieldWithErrorLabel().setAlignment(Pos.CENTER);
        labeledFieldComponent.getTextField().setPrefWidth(labeledFieldComponent.getFieldWithErrorLabel().getWidth());

    }

}
