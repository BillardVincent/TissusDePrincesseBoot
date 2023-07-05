package fr.vbillard.tissusdeprincesseboot.controller.patron.edit.rightPane;

import static fr.vbillard.tissusdeprincesseboot.controller.utils.FxUtils.buildComboBox;
import static fr.vbillard.tissusdeprincesseboot.controller.utils.FxUtils.buildSpinner;
import static fr.vbillard.tissusdeprincesseboot.controller.utils.fx_custom_element.CustomSpinner.setSpinner;

import org.springframework.stereotype.Component;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.patron.edit.PatronEditController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.ShowAlert;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureRequiseDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.PatronVersionDto;
import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;
import fr.vbillard.tissusdeprincesseboot.model.TypeFourniture;
import fr.vbillard.tissusdeprincesseboot.model.enums.Unite;
import fr.vbillard.tissusdeprincesseboot.service.FournitureRequiseService;
import fr.vbillard.tissusdeprincesseboot.service.PatronVersionService;
import fr.vbillard.tissusdeprincesseboot.service.TypeFournitureService;
import fr.vbillard.tissusdeprincesseboot.utils.DevInProgressService;
import fr.vbillard.tissusdeprincesseboot.utils.model_to_string.EntityToString;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

@Component
public class PatronEditFournitureRequiseController implements IController {

  @FXML
  public JFXButton addTypeBtn;
  @FXML
  public JFXTextField dimPriSpinner;
  @FXML
  public Label dimSecLbl;
  @FXML
  public Label dimPriLbl;
  @FXML
  public JFXComboBox<String> uniteChBx;
  @FXML
  public JFXTextField dimSecSpinnerMin;
  @FXML
  public Label dimSecUniteMin;
  @FXML
  public JFXTextField dimSecSpinnerMax;
  @FXML
  public JFXComboBox<String> dimSecUniteMax;

  private final TypeFournitureService typeFournitureService;

  private StageInitializer initializer;
  private FxData data;

  public PatronEditFournitureRequiseController(TypeFournitureService typeFournitureService) {
    this.typeFournitureService = typeFournitureService;

  }

  @Override
  public void setStageInitializer(StageInitializer initializer, FxData data) {
    this.initializer = initializer;
    this.data = data;

    FournitureRequiseDto dto = data.getFournitureRequise();
    setSpinner(dimPriSpinner);
    //setSpinner(dimSecSpinner);
    //nomField.setText("Fourniture Requise test");

    boolean dtoIsNotNew = dto.getId() != 0;

    dimPriSpinner.setVisible(dtoIsNotNew);
    uniteChBx.setVisible(dtoIsNotNew);

    Label entre = new Label();
    JFXTextField dimensionMinSpinner = buildSpinner(0f);
    dimensionMinSpinner.setVisible(dtoIsNotNew);
    Label et = new Label();
    JFXTextField dimensionMaxSpinner = buildSpinner(0f);
    dimensionMaxSpinner.setVisible(dtoIsNotNew);
    JFXComboBox<String> uniteSecChBx = new JFXComboBox<>();
    uniteSecChBx.setVisible(dtoIsNotNew);


    JFXComboBox<String> typeChBx =
        buildComboBox(typeFournitureService.getAllValues(), dto.getTypeNameProperty());

    if (dtoIsNotNew) {
      dimPriSpinner.setText(Float.toString(dto.getQuantite()));

      if (dto.getType() != null) {
        dimPriLbl.setText(dto.getType().getIntitulePrincipale());
        buildComboBox(Unite.getValuesByDimension(dto.getType().getDimensionPrincipale()),
            dto.getUnite().getLabel(),
            dto.getType().getDimensionPrincipale().getDefault().getLabel(), uniteChBx);
        dimPriSpinner.setVisible(true);
        uniteChBx.setVisible(true);

        if (dto.getType().getDimensionSecondaire() != null) {

          dimensionMinSpinner.setText(Float.toString(dto.getQuantiteSecondaireMin()));
          dimensionMaxSpinner.setText(Float.toString(dto.getQuantiteSecondaireMax()));

          dimSecLbl.setText(dto.getType().getIntituleSecondaire());
          buildComboBox(Unite.getValuesByDimension(dto.getType().getDimensionSecondaire()),
              dto.getType().getUniteSecondaireConseillee().getLabel(),
              dto.getType().getDimensionSecondaire().getDefault().getLabel(), uniteSecChBx);

          dimensionMinSpinner.setVisible(true);
          dimensionMaxSpinner.setVisible(true);
          uniteSecChBx.setVisible(true);

          entre.setText("entre");
          et.setText("et");
        }
      }
    }

    typeChBx.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

      TypeFourniture typeFourniture = typeFournitureService.findTypeFourniture(newValue);
      dimPriLbl.setText(typeFourniture.getIntitulePrincipale());

      buildComboBox(Unite.getValuesByDimension(typeFourniture.getDimensionPrincipale()),
          typeFourniture.getUnitePrincipaleConseillee().getLabel(),
          typeFourniture.getDimensionPrincipale().getDefault().getLabel(), uniteChBx);
      dimPriSpinner.setVisible(true);
      uniteChBx.setVisible(true);

      if (typeFourniture.getDimensionSecondaire() != null) {

        dimSecLbl.setText(typeFourniture.getIntituleSecondaire());

        buildComboBox(Unite.getValuesByDimension(typeFourniture.getDimensionSecondaire()),
            typeFourniture.getUniteSecondaireConseillee().getLabel(),
            typeFourniture.getDimensionSecondaire().getDefault().getLabel(), uniteSecChBx);

        dimensionMinSpinner.setVisible(true);
        dimensionMaxSpinner.setVisible(true);
        uniteSecChBx.setVisible(true);

        entre.setText("entre");
        et.setText("et");
      }
    });
/*
    validateBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {

      dto.setType(typeFournitureService.findTypeFourniture(typeChBx.getValue()));

      dto.setUnite(Unite.getEnum(uniteChBx.getValue()));
      dto.setQuantite(Float.parseFloat(dimensionPrincipaleSpinner.getText()));

      dto.setUniteSecondaire(Unite.getEnum(uniteSecChBx.getValue()));
      dto.setQuantiteSecondaireMin(Float.parseFloat(dimensionMinSpinner.getText()));
      dto.setQuantiteSecondaireMax(Float.parseFloat(dimensionMaxSpinner.getText()));

      saveRequis(dto, patron);
    });

 */
  }

  @FXML
  public void handleDupliquer() {
    DevInProgressService.notImplemented();
  }

  public void handleSave() {
    DevInProgressService.notImplemented();
  }
  public void handleDelete() {
    DevInProgressService.notImplemented();
  }
  /*
  @FXML
  public void handleSave() {
    PatronVersionDto patronVersionDto = data.getPatronVersion();
    patronVersionDto.setNom(nomField.getText());
    patronVersionService.saveOrUpdate(patronVersionDto);
    ((PatronEditController) data.getParentController()).reload(data.getPatronVersion().getId());

  }

  public void handleDelete() {
    ButtonType result = ShowAlert.suppression(initializer.getPrimaryStage(), EntityToString.PATRON_VERSION,
        data.getPatronVersion().getNom()).orElse(ButtonType.CANCEL);

    if (ButtonType.OK == result){
      patronVersionService.delete(data.getPatronVersion().getId());
      ((PatronEditController) data.getParentController()).reload(-1);

    }
  }

   */
}
