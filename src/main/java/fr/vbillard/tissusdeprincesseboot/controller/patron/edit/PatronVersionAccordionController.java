package fr.vbillard.tissusdeprincesseboot.controller.patron.edit;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.path.PathEnum;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureRequiseDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.PatronVersionDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuRequisDto;
import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;
import fr.vbillard.tissusdeprincesseboot.service.FournitureRequiseService;
import fr.vbillard.tissusdeprincesseboot.service.TissuRequisService;
import fr.vbillard.tissusdeprincesseboot.utils.Constants;
import fr.vbillard.tissusdeprincesseboot.utils.DevInProgressService;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Pane;

@Component
@Scope("prototype")
public class PatronVersionAccordionController implements IController {

  @FXML
  Accordion tissuAccordion;
  @FXML
  Accordion fournitureAccordion;

  private StageInitializer initializer;
  private PatronEditController parentController;
  private PatronVersionDto patronVersionDto;

  private TissuRequisService tissuRequisService;
  private FournitureRequiseService fournitureRequiseService;

  @Override
  public void setStageInitializer(StageInitializer initializer, FxData data) {
    this.initializer = initializer;
    if (data == null || !(data.getParentController() instanceof PatronEditController)
        || data.getPatronVersion() == null) {
      throw new IllegalData();
    }
    this.parentController = (PatronEditController) data.getParentController();
    this.patronVersionDto = data.getPatronVersion();

    List<TissuRequisDto> tissuRequisList = tissuRequisService.getAllTissuRequisDtoByPatron(patronVersionDto.getId());

    for (TissuRequisDto tissuRequisDto : tissuRequisList) {
      TitledPane pane = new TitledPane();
      pane.setText("Tissu : " + tissuRequisDto.getGammePoids());
      pane.setTextFill(Constants.colorSecondary);
      FxData fxData = new FxData();
      fxData.setTissuRequis(tissuRequisDto);
      fxData.setParentController(parentController);
      Pane content = initializer.displayPane(PathEnum.TISSU_REQUIS_ACCORDION, fxData);
      pane.setContent(content);
      pane.expandedProperty().addListener((obs, oldValue, newValue) -> {
        if (newValue) {
          parentController.displayRightPane(tissuRequisDto);
        }
      });
      tissuAccordion.getPanes().add(pane);

    }

    List<FournitureRequiseDto> fournitureRequiseList =
        fournitureRequiseService.getAllFournitureRequiseDtoByVersion(patronVersionDto.getId());

    for (FournitureRequiseDto fournitureRequiseDto : fournitureRequiseList) {
      TitledPane pane = new TitledPane();
      pane.setText("Fourniture : " + fournitureRequiseDto.getTypeName());
      pane.setTextFill(Constants.colorSecondary);
      FxData fxData = new FxData();
      fxData.setFournitureRequise(fournitureRequiseDto);
      fxData.setParentController(parentController);
      Pane content = initializer.displayPane(PathEnum.FOURNITURE_REQUISE_ACCORDION, fxData);
      pane.setContent(content);
      pane.expandedProperty().addListener((obs, oldValue, newValue) -> {
        if (newValue) {
          parentController.displayRightPane(fournitureRequiseDto);
        }
      });
      fournitureAccordion.getPanes().add(pane);
    }

  }


  @FXML
  private void initialize() {

  }
}
