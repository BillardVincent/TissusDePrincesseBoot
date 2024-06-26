package fr.vbillard.tissusdeprincesseboot.controller.patron.edit.accordion;

import com.jfoenix.controls.JFXButton;
import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.patron.edit.PatronEditController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.fx_custom_element.GlyphIconUtil;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureRequiseDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.PatronVersionDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuRequisDto;
import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;
import fr.vbillard.tissusdeprincesseboot.service.FournitureRequiseService;
import fr.vbillard.tissusdeprincesseboot.service.TissuRequisService;
import fr.vbillard.tissusdeprincesseboot.utils.Constants;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.apache.logging.log4j.util.Strings;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

@Component
@Scope(SCOPE_PROTOTYPE)
public class PatronVersionAccordionController implements IController {

  @FXML
  public JFXButton addTissuButton;
  @FXML
  public JFXButton addFournitureButton;
  @FXML
  public VBox tissuVbox;
  @FXML
  public VBox fournitureVbox;
  @FXML
  public ScrollPane wrapperPane;

  private final TissuRequisService tissuRequisService;
  private final FournitureRequiseService fournitureRequiseService;

  private StageInitializer initializer;
  private PatronEditController parentController;
  private PatronVersionDto patronVersionDto;

  public PatronVersionAccordionController(TissuRequisService tissuRequisService,
      FournitureRequiseService fournitureRequiseService) {
    this.tissuRequisService = tissuRequisService;
    this.fournitureRequiseService = fournitureRequiseService;
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

    wrapperPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
    wrapperPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

    reload();

  }

  public void reload() {
    List<TissuRequisDto> tissuRequisList = tissuRequisService.getAllTissuRequisDtoByPatron(patronVersionDto.getId());
    tissuVbox.getChildren().clear();
    fournitureVbox.getChildren().clear();
    parentController.displayRightPane(patronVersionDto);

    for (TissuRequisDto tissuRequisDto : tissuRequisList) {

      JFXButton btn = new JFXButton();
      btn.setPrefWidth(400);
      btn.setPadding(new Insets(10));
      HBox hBox = new HBox();
      btn.setGraphic(hBox);
      Label lbl = new Label("Tissu : " +
              (tissuRequisDto.getTypeTissu() != null ? tissuRequisDto.getTypeTissu().getLabel() : Strings.EMPTY));
      lbl.setTextFill(Constants.colorSecondary);
      hBox.getChildren().addAll(lbl);
      btn.setOnAction(e -> parentController.displayRightPane(tissuRequisDto, this));
      tissuVbox.getChildren().add(btn);

    }

    List<FournitureRequiseDto> fournitureRequiseList =
        fournitureRequiseService.getAllFournitureRequiseDtoByVersion(patronVersionDto.getId());

    for (FournitureRequiseDto fournitureRequiseDto : fournitureRequiseList) {

      JFXButton btn = new JFXButton();
      btn.setPrefWidth(400);
      btn.setPadding(new Insets(10));
      HBox hBox = new HBox();
      btn.setGraphic(hBox);
      Label lbl = new Label("Fourniture : "+
              (fournitureRequiseDto.getType() != null ? fournitureRequiseDto.getTypeName() : Strings.EMPTY));
      //Label lbl2 = new Label(tissuRequisDto.getGammePoids());
      lbl.setTextFill(Constants.colorSecondary);
      hBox.getChildren().addAll(lbl);
      btn.setOnAction(e -> parentController.displayRightPane(fournitureRequiseDto, this));
      fournitureVbox.getChildren().add(btn);

    }
  }

  @FXML
  public void handleAjouterFourniture() {
    fournitureRequiseService.createNewForPatron(patronVersionDto.getId());
    parentController.reload(patronVersionDto.getId());
  }


  @FXML
  public void handleAjouterTissu() {
    tissuRequisService.createNewForPatron(patronVersionDto.getId());
    parentController.reload(patronVersionDto.getId());
  }

  @FXML
  public void initialize() {
    addTissuButton.setGraphic(GlyphIconUtil.plusCircleTiny());
    addFournitureButton.setGraphic(GlyphIconUtil.plusCircleTiny());
  }

}
