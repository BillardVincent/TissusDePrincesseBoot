package fr.vbillard.tissusdeprincesseboot.controller.patron.edit.rightPane;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.patron.edit.PatronEditController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.ShowAlert;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.PatronVersionDto;
import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;
import fr.vbillard.tissusdeprincesseboot.service.PatronVersionService;
import fr.vbillard.tissusdeprincesseboot.utils.model_to_string.EntityToString;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import org.springframework.stereotype.Component;

@Component
public class PatronEditPatronVersionController implements IController {

  @FXML
  private JFXTextField nomField;
  @FXML
  private JFXButton ajouterTissuBtn;
  @FXML
  private JFXButton ajouterFournitureBtn;

  private final PatronVersionService patronVersionService;


  private StageInitializer initializer;
  private FxData data;

  public PatronEditPatronVersionController(PatronVersionService patronVersionService) {
    this.patronVersionService = patronVersionService;

  }

  @Override
  public void setStageInitializer(StageInitializer initializer, FxData data) {
    this.initializer = initializer;
    this.data = data;

    if(data == null || data.getPatronVersion() == null){
      throw new IllegalData();
    }

    nomField.setText(data.getPatronVersion().getNom());
  }

  @FXML
  public void handleDupliquer() {
    patronVersionService.duplicate(data.getPatronVersion().getId());
  }

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
}
