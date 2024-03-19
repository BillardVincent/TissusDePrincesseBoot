package fr.vbillard.tissusdeprincesseboot.controller.patron.edit.rightPane;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.patron.edit.accordion.PatronVersionAccordionController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.ShowAlert;
import fr.vbillard.tissusdeprincesseboot.controller.utils.fx_custom_element.GlyphIconUtil;
import fr.vbillard.tissusdeprincesseboot.controller.utils.path.PathEnum;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureRequiseDto;
import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;
import fr.vbillard.tissusdeprincesseboot.model.TypeFourniture;
import fr.vbillard.tissusdeprincesseboot.model.enums.Unite;
import fr.vbillard.tissusdeprincesseboot.service.FournitureRequiseService;
import fr.vbillard.tissusdeprincesseboot.service.TypeFournitureService;
import fr.vbillard.tissusdeprincesseboot.utils.DevInProgressService;
import fr.vbillard.tissusdeprincesseboot.utils.model_to_string.EntityToString;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import org.springframework.stereotype.Component;

import static fr.vbillard.tissusdeprincesseboot.controller.utils.FxUtils.buildComboBox;
import static fr.vbillard.tissusdeprincesseboot.controller.utils.FxUtils.safePropertyToString;
import static fr.vbillard.tissusdeprincesseboot.controller.utils.fx_custom_element.CustomSpinner.setLongSpinner;

@Component
public class PatronEditFournitureRequiseController implements IController {

  @FXML
  public JFXButton addTypeButton;
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
  @FXML
  public JFXComboBox<String> typeCbx;

  private final TypeFournitureService typeFournitureService;
  private final FournitureRequiseService fournitureRequiseService;
  private PatronVersionAccordionController parent;

  private StageInitializer initializer;
  private FournitureRequiseDto dto;

  public PatronEditFournitureRequiseController(TypeFournitureService typeFournitureService,
      FournitureRequiseService fournitureRequiseService) {
    this.typeFournitureService = typeFournitureService;
    this.fournitureRequiseService = fournitureRequiseService;

  }

  @Override
  public void setStageInitializer(StageInitializer initializer, FxData data) {

    if (data == null || data.getFournitureRequise() == null || data.getParentController() == null) {
      throw new IllegalData();
    }
    this.parent = (PatronVersionAccordionController)data.getParentController();

    this.initializer = initializer;
    dto = data.getFournitureRequise();
    setLongSpinner(dimPriSpinner);
    setLongSpinner(dimSecSpinnerMax);
    setLongSpinner(dimSecSpinnerMin);
    addTypeButton.setGraphic(GlyphIconUtil.plusCircleTiny());


    boolean dtoIsNotNew = dto.getId() != 0;

    dimPriSpinner.setVisible(dtoIsNotNew);
    uniteChBx.setVisible(dtoIsNotNew);

    dimSecSpinnerMin.setVisible(dtoIsNotNew);
    dimSecSpinnerMax.setVisible(dtoIsNotNew);
    dimSecUniteMax.setVisible(dtoIsNotNew);
    dimSecUniteMin.setVisible(dtoIsNotNew);

    buildComboBox(typeFournitureService.getAllValues(), dto.getTypeNameProperty(), null, typeCbx);

    if (dtoIsNotNew) {
      dimPriSpinner.setText(safePropertyToString(dto.getQuantiteProperty()));

      if (dto.getType() != null) {
        dimPriLbl.setText(dto.getType().getIntitulePrincipale());
        buildComboBox(Unite.getValuesByDimension(dto.getType().getDimensionPrincipale()), dto.getUnite().getLabel(),
            dto.getType().getDimensionPrincipale().getDefault().getLabel(), uniteChBx);
        dimPriSpinner.setVisible(true);
        uniteChBx.setVisible(true);

        if (dto.getType().getDimensionSecondaire() != null) {

          dimSecSpinnerMin.setText(safePropertyToString(dto.getQuantiteSecondaireMinProperty()));
          dimSecSpinnerMax.setText(safePropertyToString(dto.getQuantiteSecondaireMaxProperty()));

          dimSecLbl.setText(dto.getType().getIntituleSecondaire());

          dimSecUniteMax.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            dimSecUniteMin.setText(newValue);
          });
          
          buildComboBox(Unite.getValuesByDimension(dto.getType().getDimensionSecondaire()),
                  dto.getType().getUniteSecondaireConseillee().getLabel(),
                  dto.getType().getDimensionSecondaire().getDefault().getLabel(), dimSecUniteMax);

          dimSecSpinnerMin.setVisible(true);
          dimSecSpinnerMax.setVisible(true);
          dimSecUniteMax.setVisible(true);
          dimSecUniteMin.setVisible(true);


        }
      }
    }

    typeCbx.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

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
            typeFourniture.getDimensionSecondaire().getDefault().getLabel(), dimSecUniteMax);

        dimSecSpinnerMin.setVisible(true);
        dimSecSpinnerMax.setVisible(true);
        dimSecUniteMax.setVisible(true);
        dimSecUniteMin.setVisible(true);

      }
    });

  }

  @FXML
  public void handleDupliquer() {
    DevInProgressService.notImplemented();
  }

  public void handleSave() {

    dto.setType(typeFournitureService.findTypeFourniture(typeCbx.getValue()));

    dto.setUnite(Unite.getEnum(uniteChBx.getValue()));
    dto.setQuantite(Float.parseFloat(dimPriSpinner.getText()));

    dto.setUniteSecondaire(Unite.getEnum(dimSecUniteMax.getValue()));
    dto.setQuantiteSecondaireMin(Float.parseFloat(dimSecSpinnerMin.getText()));
    dto.setQuantiteSecondaireMax(Float.parseFloat(dimSecSpinnerMax.getText()));

    fournitureRequiseService.saveOrUpdate(dto);

  }

  public void handleDelete() {

    ButtonType result =
        ShowAlert.suppression(initializer.getPrimaryStage(), EntityToString.PATRON_VERSION, dto.getTypeName())
            .orElse(ButtonType.CANCEL);

    if (ButtonType.OK == result) {
      fournitureRequiseService.delete(dto.getId());
      parent.reload();

    }
  }
  
  @FXML
  public void handleAddType() {
    initializer.displayModale(PathEnum.TYPE_FOURNITURE, null, "Fourniture");

    buildComboBox(typeFournitureService.getAllValues(), dto.getTypeNameProperty(), null, typeCbx);

  }

}
