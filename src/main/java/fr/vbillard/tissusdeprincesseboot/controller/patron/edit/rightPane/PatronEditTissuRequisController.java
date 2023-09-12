package fr.vbillard.tissusdeprincesseboot.controller.patron.edit.rightPane;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXToggleButton;
import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.components.LaizeLongueurOptionCell;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.ShowAlert;
import fr.vbillard.tissusdeprincesseboot.controller.utils.fx_custom_element.GlyphIconUtil;
import fr.vbillard.tissusdeprincesseboot.controller.utils.path.PathEnum;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuRequisDto;
import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;
import fr.vbillard.tissusdeprincesseboot.model.TissuRequis;
import fr.vbillard.tissusdeprincesseboot.model.TissuRequisLaizeOption;
import fr.vbillard.tissusdeprincesseboot.model.enums.GammePoids;
import fr.vbillard.tissusdeprincesseboot.model.enums.TypeTissuEnum;
import fr.vbillard.tissusdeprincesseboot.service.MatiereService;
import fr.vbillard.tissusdeprincesseboot.service.TissageService;
import fr.vbillard.tissusdeprincesseboot.service.TissuRequisLaizeOptionService;
import fr.vbillard.tissusdeprincesseboot.service.TissuRequisService;
import fr.vbillard.tissusdeprincesseboot.utils.model_to_string.EntityToString;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static fr.vbillard.tissusdeprincesseboot.controller.utils.FxUtils.setSelection;
import static fr.vbillard.tissusdeprincesseboot.controller.utils.FxUtils.setSelectionFromChoiceBoxModale;

@Component
public class PatronEditTissuRequisController implements IController {
  @FXML
  public JFXCheckBox legerCbx;
  @FXML
  public JFXCheckBox moyenCbx;
  @FXML
  public JFXCheckBox lourdCbx;
  @FXML
  public JFXButton addQuantiteBtn;
  @FXML
  public JFXButton typeBtn;
  @FXML
  public JFXButton tissageBtn;
  @FXML
  public JFXButton matiereBtn;
  @FXML
  public JFXComboBox<String> typeField;
  @FXML
  public Label tissageLbl;
  @FXML
  public Label matiereLbl;
  @FXML
  public JFXToggleButton doublureToggle;
  @FXML
  public GridPane longueurLaizeGrid;
  @FXML
  public JFXButton addMatiereButton;
  @FXML
  public JFXButton addTissageButton;

  private final TissuRequisService tissuRequisService;
  private final TissuRequisLaizeOptionService tissuRequisLaizeOptionService;
  private final MatiereService matiereService;
  private final TissageService tissageService;


  private StageInitializer initializer;
  private TissuRequisDto tissuRequis;

  private List<String> tissageValuesSelected = new ArrayList<>();
  private List<String> matiereValuesSelected = new ArrayList<>();

  public PatronEditTissuRequisController(TissuRequisService tissuRequisService,
      TissuRequisLaizeOptionService tissuRequisLaizeOptionService, MatiereService matiereService,
      TissageService tissageService) {
    this.tissuRequisService = tissuRequisService;
    this.tissuRequisLaizeOptionService = tissuRequisLaizeOptionService;
    this.matiereService = matiereService;
    this.tissageService = tissageService;
  }

  @FXML
  private void initialize() {
	    addQuantiteBtn.setGraphic(GlyphIconUtil.plusCircleTiny());
	    addMatiereButton.setGraphic(GlyphIconUtil.plusCircleTiny());
	    addTissageButton.setGraphic(GlyphIconUtil.plusCircleTiny());
  }

  @Override
  public void setStageInitializer(StageInitializer initializer, FxData data) {
    this.initializer = initializer;
	if (data == null || data.getTissuRequis() == null) {
		throw new IllegalData();
	}
    this.tissuRequis = data.getTissuRequis();
    setLongueurLaizeGrid();

    typeField.setItems(FXCollections.observableArrayList(TypeTissuEnum.labels()));
	typeField.setValue(
			tissuRequis.getTypeTissu() == null ? TypeTissuEnum.NON_RENSEIGNE.label : tissuRequis.getTypeTissu().getLabel());


    List<String> matieres = null;
    if (tissuRequis.getMatiere() != null) {
      matieres = tissuRequis.getMatiere();
    }
    setSelection(matieres, matiereValuesSelected, matiereLbl);

    List<String> tissages = null;
    if (tissuRequis.getTissage() != null) {
      tissages = tissuRequis.getTissage();
    }

    setSelection(tissages, tissageValuesSelected, tissageLbl);

    legerCbx.setSelected(tissuRequis.getGammePoids().contains(GammePoids.LEGER));
    moyenCbx.setSelected(tissuRequis.getGammePoids().contains(GammePoids.MOYEN));
    lourdCbx.setSelected(tissuRequis.getGammePoids().contains(GammePoids.LOURD));

    doublureToggle.setSelected(tissuRequis.isDoublure());

  }

  private void setLongueurLaizeGrid() {
    longueurLaizeGrid.getChildren().removeIf(
        n -> !n.getStyleClass().contains("first-row-middle-column") && !n.getStyleClass().contains("first-row"));

    List<TissuRequisLaizeOption> tissuRequisLaizeOptionList =
        tissuRequisLaizeOptionService.getTissuRequisLaizeOptionByRequisId(tissuRequis.getId());

    longueurLaizeGrid.getRowConstraints().clear();
    longueurLaizeGrid.getRowConstraints().add(new RowConstraints(40));

    for (int i = 0; i < tissuRequisLaizeOptionList.size(); ) {
      TissuRequisLaizeOption trlo = tissuRequisLaizeOptionList.get(i);

      LaizeLongueurOptionCell laizeBox = new LaizeLongueurOptionCell(trlo.getLaize());
      LaizeLongueurOptionCell longueurBox = new LaizeLongueurOptionCell(trlo.getLongueur());

      HBox btnBox = new HBox();
      btnBox.setAlignment(Pos.CENTER);
      btnBox.setSpacing(5);

      optionBox(laizeBox, longueurBox, btnBox, trlo);

      longueurLaizeGrid.addRow(++i, btnBox, longueurBox, laizeBox);
      longueurLaizeGrid.getRowConstraints().add(new RowConstraints(40));

    }
  }

  private void optionBox(LaizeLongueurOptionCell laizeBox, LaizeLongueurOptionCell longueurBox, HBox btnBox,
      TissuRequisLaizeOption trlo) {

    JFXButton deleteBtn = new JFXButton();
    deleteBtn.setGraphic(GlyphIconUtil.suppressNormal());

    JFXButton btnEdit = new JFXButton();
    btnEdit.setGraphic(GlyphIconUtil.editNormal());

    JFXButton btnValidate = new JFXButton();
    btnValidate.setGraphic(GlyphIconUtil.ok());

    JFXButton btnCancel = new JFXButton();
    btnCancel.setGraphic(GlyphIconUtil.cancelNormal());

    setVisibilityBtnGrid(false, btnEdit, btnValidate, btnCancel);

    deleteBtn.setOnAction((e) -> {
      if (ShowAlert.suppression(initializer.getPrimaryStage(), EntityToString.OPTION_LAIZE, trlo.toString())
          .orElse(ButtonType.CANCEL) == ButtonType.OK) {
        tissuRequisLaizeOptionService.delete(trlo);
        setLongueurLaizeGrid();
      }
    });

    btnValidate.setOnAction(e -> {
      trlo.setLaize(laizeBox.validate());
      trlo.setLongueur(longueurBox.validate());
      tissuRequisLaizeOptionService.saveOrUpdate(trlo);
      setVisibilityBtnGrid(false, btnEdit, btnValidate, btnCancel);
    });

    btnEdit.setOnAction(e -> {
      laizeBox.edit();
      longueurBox.edit();
      setVisibilityBtnGrid(true, btnEdit, btnValidate, btnCancel);
    });

    btnCancel.setOnAction(e -> {
      laizeBox.cancel();
      longueurBox.cancel();
      setVisibilityBtnGrid(false, btnEdit, btnValidate, btnCancel);
    });

    btnBox.getChildren().addAll(deleteBtn, btnEdit, btnValidate, btnCancel);

  }

  private void setVisibilityBtnGrid(boolean editInProgress, JFXButton btnEdit, JFXButton btnValidate,
      JFXButton btnCancel) {
    btnEdit.setVisible(!editInProgress);
    btnValidate.setVisible(editInProgress);
    btnCancel.setVisible(editInProgress);
  }

  @FXML
  public void handleDupliquer() {
    TissuRequis clone = tissuRequisService.duplicate(tissuRequis.getId());
    //TODO
    //((PatronEditController) data.getParentController()).reload(data.getPatronVersion().getId());
  }

  @FXML
  public void handleSave() {

    List<GammePoids> gammePoids = new ArrayList<>();
    if (legerCbx.isSelected()) {
      gammePoids.add(GammePoids.LEGER);
    }
    if (moyenCbx.isSelected()) {
      gammePoids.add(GammePoids.MOYEN);
    }
    if (lourdCbx.isSelected()) {
      gammePoids.add(GammePoids.LOURD);
    }
    if (gammePoids.isEmpty()) {
      gammePoids.add(GammePoids.NON_RENSEIGNE);
    }
    tissuRequis.setGammePoids(gammePoids);

    tissuRequis.setMatiere(matiereValuesSelected);
    tissuRequis.setTypeTissu(TypeTissuEnum.getEnum(typeField.getValue()));
    tissuRequis.setTissage(tissageValuesSelected);

    tissuRequis.setDoublure(doublureToggle.isSelected());
    tissuRequisService.saveOrUpdate(tissuRequis);

    //((PatronEditController) data.getParentController()).reload(data.getPatronVersion().getId());

  }

  public void handleDelete() {
    ButtonType result =
        ShowAlert.suppression(initializer.getPrimaryStage(), EntityToString.PATRON_VERSION, tissuRequis.toString())
            .orElse(ButtonType.CANCEL);

    if (ButtonType.OK == result) {
      tissuRequisService.delete(tissuRequis.getId());
      //TODO
      //((PatronEditController) data.getParentController()).reload(-1);

    }
  }

  public void handleAddQuantity() {
    tissuRequisLaizeOptionService.createForThisRequis(tissuRequis.getId());
    setLongueurLaizeGrid();

  }

  @FXML
  private void handleSelectMatiere() {
    setSelectionFromChoiceBoxModale(matiereService.getAllValues(), matiereValuesSelected, matiereLbl, false);
    tissuRequis.setMatiere(matiereValuesSelected);
    tissuRequisService.saveOrUpdate(tissuRequis);
  }

  @FXML
  private void handleSelectTissage() {
    setSelectionFromChoiceBoxModale(tissageService.getAllValues(), tissageValuesSelected, tissageLbl, false);
    tissuRequis.setTissage(tissageValuesSelected);
    tissuRequisService.saveOrUpdate(tissuRequis);
  }

  
  @FXML
	private void handleAddMatiere() {
		initializer.displayModale(PathEnum.MATIERE, null, "Matière");

	}

	@FXML
	private void handleAddTissage() {
		initializer.displayModale(PathEnum.TISSAGE, null, "Tissage");

	}
 

}
