package fr.vbillard.tissusdeprincesseboot.controller.patron.edit.accordion;

import java.util.List;

import org.springframework.boot.context.properties.bind.validation.ValidationBindHandler;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.jfoenix.controls.JFXButton;

import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.patron.edit.PatronEditController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxUtils;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.fx_custom_element.GlyphIconUtil;
import fr.vbillard.tissusdeprincesseboot.controller.utils.path.PathEnum;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureRequiseDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.PatronVersionDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuRequisDto;
import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;
import fr.vbillard.tissusdeprincesseboot.model.FournitureRequise;
import fr.vbillard.tissusdeprincesseboot.model.TissuRequis;
import fr.vbillard.tissusdeprincesseboot.service.FournitureRequiseService;
import fr.vbillard.tissusdeprincesseboot.service.PatronVersionService;
import fr.vbillard.tissusdeprincesseboot.service.TissuRequisService;
import fr.vbillard.tissusdeprincesseboot.utils.Constants;
import fr.vbillard.tissusdeprincesseboot.utils.DevInProgressService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

@Component
@Scope("prototype")
public class PatronVersionAccordionController implements IController {

  @FXML
  public JFXButton addTissuButton;
  @FXML
  public JFXButton addFournitureButton;
  @FXML
  public VBox tissuVbox;
  @FXML
  public Accordion fournitureAccordion;

  private final TissuRequisService tissuRequisService;
  private final FournitureRequiseService fournitureRequiseService;
  private final PatronVersionService patronVersionService;

  private StageInitializer initializer;
  private PatronEditController parentController;
  private PatronVersionDto patronVersionDto;

  public PatronVersionAccordionController(TissuRequisService tissuRequisService,
      FournitureRequiseService fournitureRequiseService, PatronVersionService patronVersionService) {
    this.tissuRequisService = tissuRequisService;
    this.fournitureRequiseService = fournitureRequiseService;
    this.patronVersionService = patronVersionService;
  }

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

      JFXButton btn = new JFXButton();
      btn.setPrefWidth(400);
      btn.setPadding(new Insets(10));
      HBox hBox = new HBox();
      btn.setGraphic(hBox);
      Label lbl = new Label("Tissu : ");
      //Label lbl2 = new Label(tissuRequisDto.getGammePoids());
      lbl.setTextFill(Constants.colorSecondary);
      hBox.getChildren().addAll(lbl);
      btn.setOnAction(e -> parentController.displayRightPane(tissuRequisDto));
      tissuVbox.getChildren().add(btn);

    }

    List<FournitureRequiseDto> fournitureRequiseList =
        fournitureRequiseService.getAllFournitureRequiseDtoByVersion(patronVersionDto.getId());

    for (FournitureRequiseDto fournitureRequiseDto : fournitureRequiseList) {
      /*
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

       */
    }

  }

  @FXML
  public void handleAjouterFourniture(){
    fournitureRequiseService.createNewForPatron(patronVersionDto.getId());
    parentController.reload(patronVersionDto.getId());
  }


  @FXML
  public void handleAjouterTissu(){
    tissuRequisService.createNewForPatron(patronVersionDto.getId());
    parentController.reload(patronVersionDto.getId());
  }

  @FXML
  private void initialize() {
    addTissuButton.setGraphic(GlyphIconUtil.plusCircleTiny());
    addFournitureButton.setGraphic(GlyphIconUtil.plusCircleTiny());
  }

}
