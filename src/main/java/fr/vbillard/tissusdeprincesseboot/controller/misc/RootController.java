package fr.vbillard.tissusdeprincesseboot.controller.misc;

import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.jfoenix.controls.JFXButton;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IController;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureRequiseDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.PatronDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.ProjetDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuRequisDto;
import fr.vbillard.tissusdeprincesseboot.controller.utils.fx_custom_element.CustomIcon;
import fr.vbillard.tissusdeprincesseboot.model.FournitureRequise;
import fr.vbillard.tissusdeprincesseboot.model.FournitureUsed;
import fr.vbillard.tissusdeprincesseboot.model.Projet;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.TissuRequis;
import fr.vbillard.tissusdeprincesseboot.model.TissuUsed;
import fr.vbillard.tissusdeprincesseboot.service.FournitureRequiseService;
import fr.vbillard.tissusdeprincesseboot.service.FournitureService;
import fr.vbillard.tissusdeprincesseboot.service.FournitureUsedService;
import fr.vbillard.tissusdeprincesseboot.service.TissuRequisService;
import fr.vbillard.tissusdeprincesseboot.service.TissuUsedService;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.controller.utils.path.PathEnum;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

@Component
public class RootController implements IController {

	private static final String SELECTED = "mainmenu-element-selected";

	private static final Logger LOGGER = LogManager.getLogger(RootController.class);
	  
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
	private FournitureRequiseDto fournitureRequiseSelected;
	private final TissuUsedService tissuUsedService;
	private final ModelMapper mapper;
	private CustomIcon customIcon;
	private final TissuRequisService tissuRequisService;
	private final FournitureRequiseService fournitureRequiseService;
	private final FournitureUsedService fournitureUsedService;
	private final FournitureService fournitureService;

	public RootController(FournitureService fournitureService, FournitureUsedService fournitureUsedService,
			TissuUsedService tissuUsedService,
			ModelMapper mapper, CustomIcon customIcon,
			TissuRequisService tissuRequisService, FournitureRequiseService fournitureRequiseService) {
		this.tissuUsedService = tissuUsedService;
		this.mapper = mapper;
		this.customIcon = customIcon;
		this.tissuRequisService = tissuRequisService;
		this.fournitureRequiseService = fournitureRequiseService;
		this.fournitureUsedService = fournitureUsedService;
		this.fournitureService = fournitureService;
		
		LOGGER.info("Lancement de l'application");
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
		searchPane.getChildren().add(initializer.displayPane(PathEnum.PROJET_SEARCH, fxData));
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

	public void displayFournitureEdit(FournitureDto fourniture) {
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
		projetSelected = fxData.getProjet();

		beforeDisplay(tissuMenu);
		tissuRequisSelected = fxData.getTissuRequis();
		selectedElement.getChildren().add(initializer.displayPane(PathEnum.TISSU_REQUIS_SELECTED, fxData));
		mainWindow.getChildren().add(initializer.displayPane(PathEnum.TISSUS));

		deleteSelectedButton.setVisible(true);
		researchButton.setVisible(true);
	}

	public void displayFournitureSelected(FxData fxData) {
		projetSelected = fxData.getProjet();

		beforeDisplay(fournitureMenu);
		fournitureRequiseSelected = fxData.getFournitureRequise();
		selectedElement.getChildren().add(initializer.displayPane(PathEnum.FOURNITURE_REQUIS_SELECTED, fxData));
		mainWindow.getChildren().add(initializer.displayPane(PathEnum.FOURNITURES));

		deleteSelectedButton.setVisible(true);
		researchButton.setVisible(true);
	}

	@FXML
	public void deleteSelected() {
		deleteSelectedButton.setVisible(false);
		researchButton.setVisible(false);
		tissuRequisSelected = null;
		fournitureRequiseSelected = null;
		projetSelected = null;
		selectedElement.getChildren().clear();
	}

	private void beforeDisplay(HBox menuToSelect) {
		mainWindow.getChildren().clear();
		searchPane.getChildren().clear();
		LOGGER.info("menu selected = " +menuToSelect.getId());
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
		//TODO a suppr
		testIcons();
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
		tissuUsed.setRequis(mapper.map(tissuRequisSelected, TissuRequis.class));
		tissuUsed.setTissu(mapper.map(tissuSelected, Tissu.class));
		tissuUsed.setLongueur(data.getLongueurRequise());
		tissuUsedService.saveOrUpdate(tissuUsed);

		displayProjetEdit(projetSelected);
		deleteSelected();
	}
	
	public void addToSelected(FournitureDto fournitureSelected) {
		float longueurRequiseRestante = fournitureRequiseSelected.getQuantite();
		if (projetSelected.getFournitureUsed() != null && projetSelected.getFournitureUsed().get(fournitureRequiseSelected) != null) {
			for (int id : projetSelected.getFournitureUsed().get(fournitureRequiseSelected)) {
				longueurRequiseRestante -= fournitureUsedService.getById(id).getQuantite();
			}
		}

		FxData data = displaySetQuantiteDialog(longueurRequiseRestante, fournitureSelected);

		FournitureUsed fournitureUsed = new FournitureUsed();
		fournitureUsed.setProjet(mapper.map(projetSelected, Projet.class));
		fournitureUsed.setRequis(mapper.map(fournitureRequiseSelected, FournitureRequise.class));
		fournitureUsed.setFourniture(fournitureService.convert(fournitureSelected));
		fournitureUsed.setQuantite(data.getQuantiteRequise());
		fournitureUsedService.saveOrUpdate(fournitureUsed);

		displayProjetEdit(projetSelected);
		deleteSelected();
	}

	private FxData displaySetLongueurDialog(int longueurRequiseRestante, TissuDto tissuSelected) {
		FxData fxData = new FxData();
		fxData.setLongueurRequise(longueurRequiseRestante);
		fxData.setTissu(tissuSelected);
		return initializer.displayModale(PathEnum.SET_LONGUEUR, fxData, Strings.EMPTY);
	}
	
	private FxData displaySetQuantiteDialog(float quantiteRequiseRestante, FournitureDto fournitureSelected) {
		FxData fxData = new FxData();
		fxData.setQuantiteRequise(quantiteRequiseRestante);
		fxData.setFourniture(fournitureSelected);
		return initializer.displayModale(PathEnum.SET_QUANTITE, fxData, Strings.EMPTY);
	}

	@FXML
	private void createResearch() {
		if (tissuRequisSelected != null) {
			displayTissu(tissuRequisService.getTissuSpecification(tissuRequisSelected));
		} else if (fournitureRequiseSelected != null){
			displayFourniture(fournitureRequiseService.getFournitureSpecification(fournitureRequiseSelected));
		}
	}

	public boolean hasFournitureRequiseSelected() {
		return fournitureRequiseSelected != null;

	}
	
	private void testIcons() {
		FlowPane pane = new FlowPane(5,5);
		 ScrollPane s1 = new ScrollPane();
		 s1.setPrefSize(1600, 800);
		 s1.setContent(pane);
		pane.setPrefWrapLength(1580.0);
		mainWindow.getChildren().add(s1);
		for (MaterialDesignIcon icn : MaterialDesignIcon.values()) {
			MaterialDesignIconView icnView = new MaterialDesignIconView(icn);
			icnView.setSize("2em");
			pane.getChildren().add(new VBox(icnView, new Label(icn.toString())));
		}
		for (FontAwesomeIcon icn : FontAwesomeIcon.values()) {
			FontAwesomeIconView icnView = new FontAwesomeIconView(icn);
			icnView.setSize("2em");
			pane.getChildren().add(new VBox(icnView, new Label(icn.toString())));
		}
	}
}