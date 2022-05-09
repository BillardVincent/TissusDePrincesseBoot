package fr.vbillard.tissusdeprincesseboot.controller;

import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.jfoenix.controls.JFXButton;

import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.dtosFx.PatronDto;
import fr.vbillard.tissusdeprincesseboot.dtosFx.ProjetDto;
import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuDto;
import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuRequisDto;
import fr.vbillard.tissusdeprincesseboot.fxCustomElements.CustomIcon;
import fr.vbillard.tissusdeprincesseboot.model.Projet;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.TissuRequis;
import fr.vbillard.tissusdeprincesseboot.model.TissuUsed;
import fr.vbillard.tissusdeprincesseboot.services.TissuUsedService;
import fr.vbillard.tissusdeprincesseboot.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.utils.PathEnum;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

@Component
public class RootController implements IController {

	@FXML
	private Pane mainWindow;
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
		beforeDisplay(tissuMenu);
		mainWindow.getChildren().add(initializer.displayPane(PathEnum.TISSUS));
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
		beforeDisplay(projetMenu);
		mainWindow.getChildren().add(initializer.displayPane(PathEnum.PROJET_LIST));
	}

	public void displayProjetEdit(ProjetDto projet) {
		beforeDisplay(projetMenu);
		FxData fxData = new FxData();
		fxData.setProjet(projet);
		mainWindow.getChildren().add(initializer.displayPane(PathEnum.PROJET_EDIT, fxData));
	}

	@FXML
	public void displayPatrons() {
		beforeDisplay(patronMenu);
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
		for (HBox hb : menuElements) {
			if (hb != null && hb.getStyleClass() != null && !hb.getStyleClass().isEmpty()) {
				hb.getStyleClass().removeIf(style -> style.equals(SELECTED));
			}
		}
		if (menuToSelect != null) {
			menuToSelect.getStyleClass().add(SELECTED);
		}
	}

	@Override
	public void setStageInitializer(StageInitializer initializer, FxData data) {
		this.initializer = initializer;
		menuElements = Arrays.asList(tissuMenu, fournitureMenu, patronMenu, projetMenu);
		deleteSelectedButton.setVisible(false);

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
