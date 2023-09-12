package fr.vbillard.tissusdeprincesseboot.controller.patron.edit.rightPane;

import com.jfoenix.controls.JFXButton;
import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IController;
import fr.vbillard.tissusdeprincesseboot.service.PatronService;
import fr.vbillard.tissusdeprincesseboot.service.PatronVersionService;
import fr.vbillard.tissusdeprincesseboot.utils.DevInProgressService;
import javafx.fxml.FXML;
import org.springframework.stereotype.Component;

@Component
public class PatronEditPatronController implements IController {

  @FXML
  private JFXButton ajouterVersionBtn;

  private StageInitializer initializer;
  private FxData data;
  private final PatronService patronService;
  private final PatronVersionService patronVersionService;

  public PatronEditPatronController(PatronService patronService, PatronVersionService patronVersionService) {
    this.patronService = patronService;
    this.patronVersionService = patronVersionService;
  }

  @Override
  public void setStageInitializer(StageInitializer initializer, FxData data) {
    this.initializer = initializer;
    this.data = data;
  }

  @FXML
  public void handleDupliquer() {
    DevInProgressService.notImplemented();
  }

  @FXML
  public void handleSave(){
  }

  @FXML
  private void initialize() {

  }

}
