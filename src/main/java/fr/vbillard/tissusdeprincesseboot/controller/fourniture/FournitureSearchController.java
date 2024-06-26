package fr.vbillard.tissusdeprincesseboot.controller.fourniture;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.components.FloatSpinner;
import fr.vbillard.tissusdeprincesseboot.controller.components.IntegerSpinner;
import fr.vbillard.tissusdeprincesseboot.controller.misc.RootController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxUtils;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.ShowAlert;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.FournitureSpecification;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.TissuSpecification;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.common.CharacterSearch;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.common.NumericSearch;
import fr.vbillard.tissusdeprincesseboot.model.AbstractSimpleValueEntity;
import fr.vbillard.tissusdeprincesseboot.model.TypeFourniture;
import fr.vbillard.tissusdeprincesseboot.model.enums.Unite;
import fr.vbillard.tissusdeprincesseboot.service.TypeFournitureService;
import fr.vbillard.tissusdeprincesseboot.utils.color.ColorUtils;
import fr.vbillard.tissusdeprincesseboot.utils.color.RGBColor;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static fr.vbillard.tissusdeprincesseboot.controller.utils.FxUtils.*;

@Component
public class FournitureSearchController implements IController {

    private static final String AUCUN_FILTRE = "Aucun filtre";
    private static final String CHOIX = "Choix";
    private FournitureSpecification specification;

    @FXML
    public JFXTextField referenceField;
    @FXML
    public JFXButton typeButton;
    @FXML
    public Label typeLbl;
    @FXML
    public JFXButton tissageButton;
    @FXML
    public JFXTextField descriptionField;
    @FXML
    public JFXTextField lieuDachatField;
    @FXML
    public JFXCheckBox margeCbx;
    @FXML
    public JFXCheckBox margeSecCbx;
    @FXML
    public IntegerSpinner margeField;
    @FXML
    public IntegerSpinner margeSecField;
    @FXML
    public JFXComboBox<String> typeField;
    @FXML
    public FloatSpinner dimSecMin;
    @FXML
    public JFXComboBox<String> uniteSecCombo;
    @FXML
    public FloatSpinner dimSecMax;
    @FXML
    public JFXCheckBox longueurUtilisableCBox;
    @FXML
    public FloatSpinner dimPrimMin;
    @FXML
    public FloatSpinner dimPrimMax;
    @FXML
    public JFXComboBox<String> unitePrimCombo;
    @FXML
    public JFXCheckBox archiveTrue;
    @FXML
    public JFXCheckBox archiveFalse;
    @FXML
    public ColorPicker colorPicker;

    private final RootController root;
    private StageInitializer initializer;

    private boolean okClicked = false;

    private final TypeFournitureService typeFournitureService;
    private List<String> typeValuesSelected = new ArrayList<>();


    public FournitureSearchController(TypeFournitureService typeFournitureService, RootController root) {
        this.typeFournitureService = typeFournitureService;
        this.root = root;
    }

    @Override
    public void setStageInitializer(StageInitializer initializer, FxData data) {
        this.initializer = initializer;

        margeCbx.selectedProperty().addListener((observable, oldValue, newValue) -> margeField.setDisable(!newValue));
        margeSecCbx.selectedProperty().addListener((observable, oldValue, newValue) -> margeSecField.setDisable(!newValue));

        typeLbl.setText(AUCUN_FILTRE);

        FxUtils.setToggleColor(margeCbx, margeSecCbx, longueurUtilisableCBox);
        setData(data);

    }

    private void setData(FxData data) {
        if (data != null && data.getSpecification() != null
                && data.getSpecification() instanceof FournitureSpecification) {

            margeField.setText("0");
            margeSecField.setText("0");
            margeCbx.setSelected(false);
            margeSecCbx.setSelected(false);

            specification = (FournitureSpecification) data.getSpecification();

            List<String> types = null;
            if (specification.getType() != null) {
                types = specification.getType().stream().map(AbstractSimpleValueEntity::getValue).collect(Collectors.toList());
            }
            FxUtils.setSelection(types, typeValuesSelected, typeLbl);

            if (specification.getDescription() != null
                    && Strings.isNotBlank(specification.getDescription().getContainsMultiple())) {
                descriptionField.setText(specification.getDescription().getContainsMultiple());
            }

            FxUtils.setTextFieldFromCharacterSearch(referenceField, specification.getReference());

            FxUtils.setTextFieldMaxFromNumericSearch(dimPrimMax,
                    specification.getQuantite());
            FxUtils.setTextFieldMinFromNumericSearch(dimPrimMin,
                    specification.getQuantite());
            FxUtils.setTextFieldMaxFromNumericSearch(dimSecMax,
                    specification.getQuantiteSecondaire());
            FxUtils.setTextFieldMinFromNumericSearch(dimSecMin,
                    specification.getQuantiteSecondaire());

            List<String> typesSearch = null;
            if (specification.getType() != null) {
                typesSearch = specification.getType().stream().map(AbstractSimpleValueEntity::getValue)
                        .collect(Collectors.toList());
            }
            FxUtils.setSelection(typesSearch, typeValuesSelected, typeLbl);

			if (specification.getColor() != null){
				colorPicker.setValue(Color.color(specification.getColor().getRed(), specification.getColor().getGreen(), specification.getColor().getBlue()));
			} else {
				colorPicker.setValue(Color.TRANSPARENT);
			}

        } else {
            specification = new FournitureSpecification();
            typeLbl.setText(AUCUN_FILTRE);
            typeValuesSelected = new ArrayList<>();
            dimPrimMax.setText(Strings.EMPTY);
            dimSecMax.setText(Strings.EMPTY);
            descriptionField.setText(Strings.EMPTY);
			colorPicker.setValue(Color.TRANSPARENT);

        }
    }

    @FXML
    public void initialize() {

    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    public void handleCancel() {
        FxData data = new FxData();
        data.setSpecification(new TissuSpecification());
        setData(data);
    }

    @FXML
    public void handleOk() {

        List<TypeFourniture> type = typeFournitureService.findTypeFourniture(typeValuesSelected);

        Unite unitePrim = Unite.getEnum(unitePrimCombo.getValue());

        int marge = margeField.isDisable() ? 0 : intFromJFXTextField(margeField);
        int margeSec = margeSecField.isDisable() ? 0 : intFromJFXTextField(margeSecField);

        NumericSearch<Float> dimPrimTemp = setNumericFloatSearch(
                Unite.convertir(floatFromJFXTextField(dimPrimMin), unitePrim),
                Unite.convertir(floatFromJFXTextField(dimPrimMax), unitePrim), marge);

        NumericSearch<Float> dimPrim = null;
        NumericSearch<Float> dimPrimDispo = null;

        if (longueurUtilisableCBox.isSelected()) {
            dimPrim = dimPrimTemp;
        } else {
            dimPrimDispo = dimPrimTemp;
        }

        Unite uniteSec = Unite.getEnum(uniteSecCombo.getValue());

        NumericSearch<Float> dimSec = setNumericFloatSearch(
                Unite.convertir(floatFromJFXTextField(dimSecMin), uniteSec),
                Unite.convertir(floatFromJFXTextField(dimSecMax), uniteSec), margeSec);

        CharacterSearch description = textFieldToCharacterSearchMultiple(descriptionField);

        CharacterSearch reference = textFieldToCharacterSearch(referenceField);

		RGBColor color = ColorUtils.colorFxToRgb(colorPicker.getValue());

		specification = FournitureSpecification.builder().reference(reference).description(description).type(type)
                .quantite(dimPrim).quantiteDisponible(dimPrimDispo).quantiteSecondaire(dimSec).color(color).build();

        root.displayFourniture(specification);
    }

    @FXML
    public void utilisableHelp() {
        ShowAlert.information(initializer.getPrimaryStage(), "Aide", "Quantité utilisable",
                "Définit si la recherche doit porter sur la quantité de fourniture en stock (\"Utilisable\" décoché) ou sur "
                        + "la quantité de fourniture disponible (\"Utilisable\" coché)");
    }

    @FXML
    public void choiceType() {
        setSelectionFromChoiceBoxModale(typeFournitureService.getAllValues(), typeValuesSelected, typeLbl, true);
    }

}
