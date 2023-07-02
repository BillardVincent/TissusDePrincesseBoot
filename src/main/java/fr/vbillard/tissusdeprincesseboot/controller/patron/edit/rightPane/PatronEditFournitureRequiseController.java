package fr.vbillard.tissusdeprincesseboot.controller.patron.edit.rightPane;

import static fr.vbillard.tissusdeprincesseboot.controller.utils.FxUtils.buildComboBox;
import static fr.vbillard.tissusdeprincesseboot.controller.utils.FxUtils.buildSpinner;

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
  private JFXTextField nomField;
  @FXML
  private JFXButton ajouterTissuBtn;
  @FXML
  private JFXButton ajouterFournitureBtn;
  @FXML
  private GridPane topGrid;
  @FXML
  private JFXButton validateBtn;

  private final FournitureRequiseService fournitureRequiseService;
  private final TypeFournitureService typeFournitureService;

  private StageInitializer initializer;
  private FxData data;

  public PatronEditFournitureRequiseController(FournitureRequiseService fournitureRequiseService,
      TypeFournitureService typeFournitureService) {
    this.fournitureRequiseService = fournitureRequiseService;
    this.typeFournitureService = typeFournitureService;

  }

  @Override
  public void setStageInitializer(StageInitializer initializer, FxData data) {
    this.initializer = initializer;
    this.data = data;

    FournitureRequiseDto dto = data.getFournitureRequise();

    //nomField.setText("Fourniture Requise test");

    boolean dtoIsNotNew = dto.getId() != 0;

    topGrid.add(new Label("Type"), 0, 0);

    Label intitulePrincipal = new Label();
    JFXTextField dimensionPrincipaleSpinner = buildSpinner(0f);
    dimensionPrincipaleSpinner.setVisible(dtoIsNotNew);
    JFXComboBox<String> uniteChBx = new JFXComboBox<>();
    uniteChBx.setVisible(dtoIsNotNew);

    topGrid.add(intitulePrincipal, 0, 1);
    topGrid.add(dimensionPrincipaleSpinner, 1, 1);
    topGrid.add(uniteChBx, 2, 1);

    Label intituleSecondaire = new Label();
    Label entre = new Label();
    JFXTextField dimensionMinSpinner = buildSpinner(0f);
    dimensionMinSpinner.setVisible(dtoIsNotNew);
    Label et = new Label();
    JFXTextField dimensionMaxSpinner = buildSpinner(0f);
    dimensionMaxSpinner.setVisible(dtoIsNotNew);
    JFXComboBox<String> uniteSecChBx = new JFXComboBox<>();
    uniteSecChBx.setVisible(dtoIsNotNew);

    topGrid.add(intituleSecondaire, 0, 2);
    topGrid.add(entre, 1, 2);
    topGrid.add(dimensionMinSpinner, 1, 3);
    topGrid.add(et, 2, 2);
    topGrid.add(dimensionMaxSpinner, 2, 3);
    topGrid.add(uniteSecChBx, 3, 3);

    JFXComboBox<String> typeChBx =
        buildComboBox(typeFournitureService.getAllValues(), dto.getTypeNameProperty());

    if (dtoIsNotNew) {
      dimensionPrincipaleSpinner.setText(Float.toString(dto.getQuantite()));

      if (dto.getType() != null) {
        intitulePrincipal.setText(dto.getType().getIntitulePrincipale());
        buildComboBox(Unite.getValuesByDimension(dto.getType().getDimensionPrincipale()),
            dto.getUnite().getLabel(),
            dto.getType().getDimensionPrincipale().getDefault().getLabel(), uniteChBx);
        dimensionPrincipaleSpinner.setVisible(true);
        uniteChBx.setVisible(true);

        if (dto.getType().getDimensionSecondaire() != null) {

          dimensionMinSpinner.setText(Float.toString(dto.getQuantiteSecondaireMin()));
          dimensionMaxSpinner.setText(Float.toString(dto.getQuantiteSecondaireMax()));

          intituleSecondaire.setText(dto.getType().getIntituleSecondaire());
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
      intitulePrincipal.setText(typeFourniture.getIntitulePrincipale());

      buildComboBox(Unite.getValuesByDimension(typeFourniture.getDimensionPrincipale()),
          typeFourniture.getUnitePrincipaleConseillee().getLabel(),
          typeFourniture.getDimensionPrincipale().getDefault().getLabel(), uniteChBx);
      dimensionPrincipaleSpinner.setVisible(true);
      uniteChBx.setVisible(true);

      if (typeFourniture.getDimensionSecondaire() != null) {

        intituleSecondaire.setText(typeFourniture.getIntituleSecondaire());

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

    topGrid.add(typeChBx, 1, 0);
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
