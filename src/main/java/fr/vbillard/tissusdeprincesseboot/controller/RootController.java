package fr.vbillard.tissusdeprincesseboot.controller;

import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.jfoenix.controls.JFXButton;

import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.PatronDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.ProjetDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuRequisDto;
import fr.vbillard.tissusdeprincesseboot.fx_custom_element.CustomIcon;
import fr.vbillard.tissusdeprincesseboot.model.Projet;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.TissuRequis;
import fr.vbillard.tissusdeprincesseboot.model.TissuUsed;
import fr.vbillard.tissusdeprincesseboot.service.TissuUsedService;
import fr.vbillard.tissusdeprincesseboot.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.utils.PathEnum;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

@Component
public class RootController implements IController {

	@FXML
	private Pane mainWindow;
	@FXML
	private Pane searchPane;
	@FXML
	private Pane selectedElement;
	@FXML
	private HBox tissuMenu;
	@FXML
	private HBox fournitureMenu;
	@FXML
	private HBox patronMenu;
	@FXML
	private HBox projetMenu;
	@FXML
	private JFXButton deleteSelectedButton;

	@FXML
	private VBox test;

	private static final String SELECTED = "mainmenu-element-selected";

	private List<HBox> menuElements;
	private StageInitializer initializer;
	private TissuRequisDto tissuRequisSelected;
	private ProjetDto projetSelected;
	private TissuUsedService tissuUsedService;
	private ModelMapper mapper;
	private CustomIcon customIcon;

	public RootController(TissuUsedService tissuUsedService, ModelMapper mapper, CustomIcon customIcon) {
		this.tissuUsedService = tissuUsedService;
		this.mapper = mapper;
		this.customIcon = customIcon;
	}

	@FXML
	public void displayTissus() {
		displayTissu(null);
	}

	public void displayTissu(Specification spec) {
		FxData fxData = new FxData();
		fxData.setSpecification(spec);
		beforeDisplay(tissuMenu);
		searchPane.getChildren().add(initializer.displayPane(PathEnum.TISSU_SEARCH, fxData));
		mainWindow.getChildren().add(initializer.displayPane(PathEnum.TISSUS, fxData));
	}

	public void displayTissusDetails(TissuDto tissu) {
		beforeDisplay(tissuMenu);
		FxData fxData = new FxData();
		fxData.setTissu(tissu);
		mainWindow.getChildren().add(initializer.displayPane(PathEnum.TISSUS_DETAILS, fxData));
	}

	public void displayTissusEdit(TissuDto tissu) {
		beforeDisplay(tissuMenu);
		FxData fxData = new FxData();
		fxData.setTissu(tissu);
		mainWindow.getChildren().add(initializer.displayPane(PathEnum.TISSUS_EDIT, fxData));
	}

	@FXML
	public void displayProjets() {
		displayProjets(null);
	}

	public void displayProjets(Specification spec) {
		FxData fxData = new FxData();
		fxData.setSpecification(spec);
		beforeDisplay(projetMenu);
		searchPane.getChildren().add(initializer.displayPane(PathEnum.PROJET_SEARCH));
		mainWindow.getChildren().add(initializer.displayPane(PathEnum.PROJET_LIST, fxData));
	}

	public void displayProjetEdit(ProjetDto projet) {
		beforeDisplay(projetMenu);
		FxData fxData = new FxData();
		fxData.setProjet(projet);
		mainWindow.getChildren().add(initializer.displayPane(PathEnum.PROJET_EDIT, fxData));
	}

	@FXML
	public void displayPatrons() {
		displayPatrons(null);
	}

	public void displayPatrons(Specification spec) {
		FxData fxData = new FxData();
		fxData.setSpecification(spec);
		beforeDisplay(patronMenu);
		searchPane.getChildren().add(initializer.displayPane(PathEnum.PATRON_SEARCH, fxData));
		mainWindow.getChildren().add(initializer.displayPane(PathEnum.PATRON_LIST));
	}

	public void displayPatronDetails(PatronDto patron) {
		beforeDisplay(patronMenu);
		FxData fxData = new FxData();
		fxData.setPatron(patron);
		mainWindow.getChildren().add(initializer.displayPane(PathEnum.PATRON_DETAILS, fxData));
	}

	public void displayPatronEdit(PatronDto patron) {
		beforeDisplay(patronMenu);
		FxData fxData = new FxData();
		fxData.setPatron(patron);
		mainWindow.getChildren().add(initializer.displayPane(PathEnum.PATRON_EDIT, fxData));
	}

	@FXML
	public void displayMatiereEdit() {
		beforeDisplay(null);
		mainWindow.getChildren().add(initializer.displayPane(PathEnum.MATIERE));
	}

	@FXML
	public void displayTissageEdit() {
		beforeDisplay(null);
		mainWindow.getChildren().add(initializer.displayPane(PathEnum.TISSAGE));
	}

	public void displaySelected(FxData fxData) {
		tissuRequisSelected = fxData.getTissuRequis();
		projetSelected = fxData.getProjet();
		selectedElement.getChildren().add(initializer.displayPane(PathEnum.TISSU_REQUIS_SELECTED, fxData));
		beforeDisplay(tissuMenu);
		mainWindow.getChildren().add(initializer.displayPane(PathEnum.TISSUS));
		deleteSelectedButton.setVisible(true);
	}

	@FXML
	public void deleteSelected() {
		deleteSelectedButton.setVisible(false);
		tissuRequisSelected = null;
		projetSelected = null;
		selectedElement.getChildren().clear();
	}

	private void beforeDisplay(HBox menuToSelect) {
		mainWindow.getChildren().clear();
		searchPane.getChildren().clear();
		for (HBox hb : menuElements) {
			if (hb != null && hb.getStyleClass() != null && !hb.getStyleClass().isEmpty()) {
				hb.getStyleClass().removeIf(style -> style.equals(SELECTED));
			}
		}
		if (menuToSelect != null) {
			menuToSelect.getStyleClass().add(SELECTED);
		}
	}

	@FXML
	public void displayPref() {
		initializer.displayModale(PathEnum.PREF, null, "");
	}

	@Override
	public void setStageInitializer(StageInitializer initializer, FxData data) {
		this.initializer = initializer;
		menuElements = Arrays.asList(tissuMenu, fournitureMenu, patronMenu, projetMenu);
		deleteSelectedButton.setVisible(false);

		/*
		 * JFXComboBox<JFXCheckBox> box = new JFXComboBox<JFXCheckBox>() {
		 * 
		 * }; JFXCheckBox label1 = new JFXCheckBox("Test1"); JFXCheckBox label2 = new
		 * JFXCheckBox("Test2"); JFXCheckBox label3 = new JFXCheckBox("Test3");
		 * JFXCheckBox label4 = new JFXCheckBox("Test4"); label1.setDisable(true);
		 * label1.setOpacity(1); label2.setDisable(true); label2.setOpacity(1);
		 * 
		 * label3.setDisable(true); label3.setOpacity(1);
		 * 
		 * label4.setDisable(true); label4.setOpacity(1);
		 * 
		 * List<JFXCheckBox> checkList = Arrays.asList(label1, label2, label3, label4);
		 * 
		 * box.setItems(FXCollections.observableArrayList(checkList)); Label label = new
		 * Label();
		 * 
		 * box.setOnAction(new EventHandler<ActionEvent>() {
		 * 
		 * @Override public void handle(ActionEvent event) { if
		 * (!box.getSelectionModel().isEmpty()) {
		 * box.getValue().setSelected(!box.getValue().isSelected());
		 * label.setText(checkList.stream().filter(c -> c.isSelected()).map(c ->
		 * c.getText()) .collect(Collectors.joining(" - ")));
		 * box.setStyle("-fx-text-fill: red");
		 * 
		 * } else { box.getSelectionModel().clearSelection();
		 * 
		 * }
		 * 
		 * } });
		 * 
		 * box.setButtonCell(new ListCell<JFXCheckBox>() {
		 * 
		 * @Override protected void updateItem(JFXCheckBox item, boolean empty) {
		 * super.updateItem(item, empty); if (empty || item == null) { // styled like
		 * -fx-prompt-text-fill: setText("Aucun sélectionné");
		 * 
		 * } else { setText(checkList.stream().filter(c -> c.isSelected()).map(c ->
		 * c.getText()) .collect(Collectors.joining(" - ")));
		 * 
		 * } }
		 * 
		 * });
		 * 
		 * test.getChildren().addAll(label, box);
		 */

	}

	public boolean hasTissuRequisSelected() {
		return tissuRequisSelected != null;
	}

	public void addToSelected(TissuDto tissuSelected) {
		int longueurRequiseRestante = tissuRequisSelected.getLongueur();
		if (projetSelected.getTissuUsed() != null && projetSelected.getTissuUsed().get(tissuRequisSelected) != null) {
			for (int id : projetSelected.getTissuUsed().get(tissuRequisSelected)) {
				longueurRequiseRestante -= tissuUsedService.getById(id).getLongueur();
			}
		}

		FxData data = displaySetLongueurDialog(longueurRequiseRestante, tissuSelected);

		TissuUsed tissuUsed = new TissuUsed();
		tissuUsed.setProjet(mapper.map(projetSelected, Projet.class));
		tissuUsed.setTissuRequis(mapper.map(tissuRequisSelected, TissuRequis.class));
		tissuUsed.setTissu(mapper.map(tissuSelected, Tissu.class));
		tissuUsed.setLongueur(data.getLongueurRequise());
		tissuUsedService.saveOrUpdate(tissuUsed);

		deleteSelected();

		displayProjetEdit(projetSelected);

	}

	private FxData displaySetLongueurDialog(int longueurRequiseRestante, TissuDto tissuSelected) {
		FxData fxData = new FxData();
		fxData.setLongueurRequise(longueurRequiseRestante);
		fxData.setTissu(tissuSelected);
		fxData = initializer.displayModale(PathEnum.SET_LONGUEUR, fxData, "");
		return fxData;
	}
}
