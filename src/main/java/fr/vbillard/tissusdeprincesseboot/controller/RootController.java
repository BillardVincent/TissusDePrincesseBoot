package fr.vbillard.tissusdeprincesseboot.controller;

import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.util.Strings;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.jfoenix.controls.JFXButton;

import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IController;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.PatronDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.ProjetDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuRequisDto;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.TissuSpecification;
import fr.vbillard.tissusdeprincesseboot.fx_custom_element.CustomIcon;
import fr.vbillard.tissusdeprincesseboot.model.Projet;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.TissuRequis;
import fr.vbillard.tissusdeprincesseboot.model.TissuUsed;
import fr.vbillard.tissusdeprincesseboot.service.TissuRequisService;
import fr.vbillard.tissusdeprincesseboot.service.TissuUsedService;
import fr.vbillard.tissusdeprincesseboot.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.utils.path.PathEnum;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

@Component
public class RootController implements IController {

	private static final String SELECTED = "mainmenu-element-selected";

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
	private JFXButton researchButton;
	@FXML
	private VBox test;

	private List<HBox> menuElements;
	private StageInitializer initializer;
	private TissuRequisDto tissuRequisSelected;
	private ProjetDto projetSelected;
	private TissuUsedService tissuUsedService;
	private ModelMapper mapper;
	private CustomIcon customIcon;
	private TissuRequisService tissuRequisService;

	public RootController(TissuUsedService tissuUsedService, ModelMapper mapper, CustomIcon customIcon,
			TissuRequisService tissuRequisService) {
		this.tissuUsedService = tissuUsedService;
		this.mapper = mapper;
		this.customIcon = customIcon;
		this.tissuRequisService = tissuRequisService;
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
		mainWindow.getChildren().add(initializer.displayPane(PathEnum.PATRON_LIST, fxData));
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
	public void displayFourniture() {
		displayFourniture(null);
	}

	public void displayFourniture(Specification spec) {
		FxData fxData = new FxData();
		fxData.setSpecification(spec);
		beforeDisplay(fournitureMenu);
		searchPane.getChildren().add(initializer.displayPane(PathEnum.FOURNITURE_SEARCH, fxData));
		mainWindow.getChildren().add(initializer.displayPane(PathEnum.FOURNITURES, fxData));
	}

	public void displayFournituresDetails(FournitureDto fourniture) {
		beforeDisplay(fournitureMenu);
		FxData fxData = new FxData();
		fxData.setFourniture(fourniture);
		mainWindow.getChildren().add(initializer.displayPane(PathEnum.FOURNITURES_DETAILS, fxData));
	}

	public void displayTissusEdit(FournitureDto fourniture) {
		beforeDisplay(fournitureMenu);
		FxData fxData = new FxData();
		fxData.setFourniture(fourniture);
		mainWindow.getChildren().add(initializer.displayPane(PathEnum.FOURNITURES_EDIT, fxData));
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
		researchButton.setVisible(true);
	}

	@FXML
	public void deleteSelected() {
		deleteSelectedButton.setVisible(false);
		researchButton.setVisible(false);
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
		researchButton.setVisible(false);
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

		displayProjetEdit(projetSelected);
		deleteSelected();
	}

	private FxData displaySetLongueurDialog(int longueurRequiseRestante, TissuDto tissuSelected) {
		FxData fxData = new FxData();
		fxData.setLongueurRequise(longueurRequiseRestante);
		fxData.setTissu(tissuSelected);
		return initializer.displayModale(PathEnum.SET_LONGUEUR, fxData, Strings.EMPTY);
	}

	@FXML
	private void createResearch() {
		if (tissuRequisSelected != null) {
			displayTissu(tissuRequisService.getTissuSpecification(tissuRequisSelected));
		}
	}
}
