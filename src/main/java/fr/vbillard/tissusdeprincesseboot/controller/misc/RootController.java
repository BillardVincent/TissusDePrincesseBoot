package fr.vbillard.tissusdeprincesseboot.controller.misc;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.components.aside.Aside;
import fr.vbillard.tissusdeprincesseboot.controller.components.aside.AsideController;
import fr.vbillard.tissusdeprincesseboot.controller.components.aside.search.FournitureSearchComponent;
import fr.vbillard.tissusdeprincesseboot.controller.utils.ClassCssUtils;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.ShowAlert;
import fr.vbillard.tissusdeprincesseboot.controller.utils.path.PathEnum;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureRequiseDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.PatronDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.ProjetDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuRequisDto;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.FournitureSpecification;
import fr.vbillard.tissusdeprincesseboot.model.FournitureRequise;
import fr.vbillard.tissusdeprincesseboot.model.FournitureUsed;
import fr.vbillard.tissusdeprincesseboot.model.Patron;
import fr.vbillard.tissusdeprincesseboot.model.Projet;
import fr.vbillard.tissusdeprincesseboot.model.Rangement;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.TissuRequis;
import fr.vbillard.tissusdeprincesseboot.model.TissuUsed;
import fr.vbillard.tissusdeprincesseboot.service.FournitureRequiseService;
import fr.vbillard.tissusdeprincesseboot.service.FournitureService;
import fr.vbillard.tissusdeprincesseboot.service.FournitureUsedService;
import fr.vbillard.tissusdeprincesseboot.service.TissuRequisLaizeOptionService;
import fr.vbillard.tissusdeprincesseboot.service.TissuRequisService;
import fr.vbillard.tissusdeprincesseboot.service.TissuUsedService;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class RootController implements IController {

    private static final Logger LOGGER = LogManager.getLogger(RootController.class);

    @FXML
    public HBox rangementMenu;
    @FXML
    public Pane mainWindow;
    @FXML
    public Pane searchPane;
    @FXML
    public Pane selectedElement;
    @FXML
    public HBox tissuMenu;
    @FXML
    public HBox fournitureMenu;
    @FXML
    public HBox patronMenu;
    @FXML
    public HBox projetMenu;
    @FXML
    public JFXButton deleteSelectedButton;
    @FXML
    public JFXButton researchButton;
    @FXML
    public VBox test;
    @FXML
    public Pane asideContainer;
    // TODO changer le bloc Aside pour le rendre + dynamique :
    //  - bloc "selection" facultatif
    //  - scroll bar sur la recherche. Menu collapsable?

    //private Aside aside;
    private final AsideController asideController;
    private List<HBox> menuElements;
    private StageInitializer initializer;
    private TissuRequisDto tissuRequisSelected;
    private ProjetDto projetSelected;
    private FournitureRequiseDto fournitureRequiseSelected;
    private TissuDto tissuSelected;
    private FournitureDto fournitureSelected;
    private PatronDto patronSelected;
    private final TissuRequisLaizeOptionService tissuRequisLaizeOptionService;
    private final TissuUsedService tissuUsedService;
    private final ModelMapper mapper;
    private final TissuRequisService tissuRequisService;
    private final FournitureRequiseService fournitureRequiseService;
    private final FournitureUsedService fournitureUsedService;
    private final FournitureService fournitureService;

    public RootController(AsideController asideController, FournitureService fournitureService, FournitureUsedService fournitureUsedService,
            TissuUsedService tissuUsedService, ModelMapper mapper, TissuRequisService tissuRequisService,
            FournitureRequiseService fournitureRequiseService,
            TissuRequisLaizeOptionService tissuRequisLaizeOptionService) {
        this.asideController = asideController;
        this.tissuRequisLaizeOptionService = tissuRequisLaizeOptionService;
        this.tissuUsedService = tissuUsedService;
        this.mapper = mapper;
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

    public void displayTissu(Specification<Tissu> spec) {
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
    public void displayRangement() {
        displayRangement(null);
    }

    public void displayRangement(Specification<Rangement> spec) {
        FxData fxData = new FxData();
        fxData.setSpecification(spec);
        displayRangementTree(fxData);
    }

    public void displayRangementTree(FxData data) {
        beforeDisplay(rangementMenu);
        mainWindow.getChildren().add(initializer.displayPane(PathEnum.RANGEMENT_TREE, data));
    }

    @FXML
    public void displayProjets() {
        displayProjets(null);
    }

    public void displayProjets(Specification<Projet> spec) {
        if (canChange(projetMenu)) {
            beforeDisplay(projetMenu);
            FxData fxData = new FxData();
            fxData.setSpecification(spec);
            searchPane.getChildren().add(initializer.displayPane(PathEnum.PROJET_SEARCH, fxData));
            mainWindow.getChildren().add(initializer.displayPane(PathEnum.PROJET_LIST, fxData));
        }
    }

    public void displayProjetEdit(ProjetDto projet) {
        if (canChange(projetMenu)) {
            beforeDisplay(projetMenu);
            FxData fxData = new FxData();
            fxData.setProjet(projet);
            mainWindow.getChildren().add(initializer.displayPane(PathEnum.PROJET_EDIT, fxData));
        }
    }

    @FXML
    public void displayPatrons() {
        displayPatrons(null);
    }

    public void displayPatrons(Specification<Patron> spec) {
        if (canChange(patronMenu)) {
            FxData fxData = new FxData();
            fxData.setSpecification(spec);
            beforeDisplay(patronMenu);
            searchPane.getChildren().add(initializer.displayPane(PathEnum.PATRON_SEARCH, fxData));
            mainWindow.getChildren().add(initializer.displayPane(PathEnum.PATRON_LIST, fxData));
        }
    }

    public void displayPatronDetails(PatronDto patron) {
        if (canChange(patronMenu)) {
            beforeDisplay(patronMenu);
            FxData fxData = new FxData();
            fxData.setPatron(patron);
            mainWindow.getChildren().add(initializer.displayPane(PathEnum.PATRON_DETAILS, fxData));
        }
    }

    public void displayPatronEdit(PatronDto patron) {
        if (canChange(patronMenu)) {
            beforeDisplay(patronMenu);
            FxData fxData = new FxData();
            fxData.setPatron(patron);
            mainWindow.getChildren().add(initializer.displayPane(PathEnum.PATRON_EDIT, fxData));
        }
    }

    @FXML
    public void displayFourniture() {
        displayFourniture(null);
    }

    public void displayFourniture(FournitureSpecification spec) {
        if(canChange(fournitureMenu)) {
            beforeDisplay(fournitureMenu);
            FxData fxData = new FxData();
            if (spec == null){
                spec = new FournitureSpecification();
            }
            fxData.setSpecification(spec);
            asideController.setSearch(spec);
            mainWindow.getChildren().add(initializer.displayPane(PathEnum.FOURNITURES, fxData));
        }
    }

    public void displayFournituresDetails(FournitureDto fourniture) {
        if (canChange(fournitureMenu)) {
            beforeDisplay(fournitureMenu);
            FxData fxData = new FxData();
            fxData.setFourniture(fourniture);
            mainWindow.getChildren().add(initializer.displayPane(PathEnum.FOURNITURES_DETAILS, fxData));
        }
    }

    public void displayFournitureEdit(FournitureDto fourniture) {
        if (canChange(fournitureMenu)) {
            beforeDisplay(fournitureMenu);
            FxData fxData = new FxData();
            fxData.setFourniture(fourniture);
            mainWindow.getChildren().add(initializer.displayPane(PathEnum.FOURNITURES_EDIT, fxData));
        }
    }

    public void displayTissuSelected(FxData fxData) {
        if (canChange(rangementMenu)) {
            beforeDisplay(rangementMenu);

            tissuSelected = fxData.getTissu();
            selectedElement.getChildren().add(initializer.displayPane(PathEnum.TISSU_SELECTED, fxData));
            mainWindow.getChildren().add(initializer.displayPane(PathEnum.RANGEMENT_TREE, fxData));

            deleteSelectedButton.setVisible(true);
            researchButton.setVisible(true);
        }
    }

    public void displayFournitureSelected(FxData fxData) {
        if (canChange(rangementMenu)) {
            beforeDisplay(rangementMenu);
            fournitureSelected = fxData.getFourniture();

            selectedElement.getChildren().add(initializer.displayPane(PathEnum.FOURNITURE_SELECTED, fxData));
            mainWindow.getChildren().add(initializer.displayPane(PathEnum.RANGEMENT_TREE, fxData));

            deleteSelectedButton.setVisible(true);
            researchButton.setVisible(true);
        }
    }

    public void displayPatronSelected(FxData fxData) {
        if (canChange(rangementMenu)) {
            beforeDisplay(rangementMenu);

            patronSelected = fxData.getPatron();
            selectedElement.getChildren().add(initializer.displayPane(PathEnum.PATRON_SELECTED, fxData));

            mainWindow.getChildren().add(initializer.displayPane(PathEnum.RANGEMENT_TREE, fxData));
            deleteSelectedButton.setVisible(true);
            researchButton.setVisible(true);
        }
    }

    public void displayTissuRequisSelected(FxData fxData) {
        if (canChange(tissuMenu)) {
            beforeDisplay(tissuMenu);

            projetSelected = fxData.getProjet();

            tissuRequisSelected = fxData.getTissuRequis();
            selectedElement.getChildren().add(initializer.displayPane(PathEnum.TISSU_REQUIS_SELECTED, fxData));
            mainWindow.getChildren().add(initializer.displayPane(PathEnum.TISSUS));

            deleteSelectedButton.setVisible(true);
            researchButton.setVisible(true);
        }
    }

    public void displayFournitureRequiseSelected(FxData fxData) {
        if (canChange(fournitureMenu)) {
            beforeDisplay(fournitureMenu);

            projetSelected = fxData.getProjet();

            fournitureRequiseSelected = fxData.getFournitureRequise();
            asideController.getAside().addSelectionPane(initializer.displayPane(PathEnum.FOURNITURE_REQUIS_SELECTED, fxData));
            mainWindow.getChildren().add(initializer.displayPane(PathEnum.FOURNITURES));

            deleteSelectedButton.setVisible(true);
            researchButton.setVisible(true);
        }
    }

    @FXML
    public void deleteSelected() {
        deleteSelectedButton.setVisible(false);
        researchButton.setVisible(false);
        tissuRequisSelected = null;
        fournitureRequiseSelected = null;
        projetSelected = null;
        tissuSelected = null;
        fournitureSelected = null;
        patronSelected = null;

        selectedElement.getChildren().clear();
    }

    private boolean canChange(HBox menuToSelect){
        boolean changeMenu = !menuToSelect.getStyleClass().contains(ClassCssUtils.SELECTED);
        boolean hasSelection = tissuSelected != null || fournitureSelected != null || patronSelected != null ||
                tissuRequisSelected != null || fournitureRequiseSelected != null || projetSelected != null;
        if (changeMenu && hasSelection){
            if (ButtonType.OK == ShowAlert.confirmation(initializer.getPrimaryStage(), "Action en cours", "Vous avez une selection", "Annuler pour conserver la sélection.\nValider pour aller vers la page sélectionnée et perdre la sélection").orElse(
                    ButtonType.CANCEL)) {
                deleteSelected();
            } else {
                return false;
            }

        }
        return true;
    }

    private void beforeDisplay(HBox menuToSelect) {

        mainWindow.getChildren().clear();
        searchPane.getChildren().clear();
        LOGGER.info("menu selected = {}", menuToSelect.getId());
        for (HBox hb : menuElements) {
            if (hb != null && hb.getStyleClass() != null && !hb.getStyleClass().isEmpty()) {
                hb.getStyleClass().removeIf(style -> style.equals(ClassCssUtils.SELECTED));
            }
        }
        menuToSelect.getStyleClass().add(ClassCssUtils.SELECTED);

    }

    @FXML
    public void displayPref() {
        initializer.displayModale(PathEnum.PREF, null, Strings.EMPTY);
    }

    @Override
    public void setStageInitializer(StageInitializer initializer, FxData data) {
        this.initializer = initializer;
        menuElements = Arrays.asList(tissuMenu, fournitureMenu, patronMenu, projetMenu, rangementMenu);
        deleteSelectedButton.setVisible(false);
        researchButton.setVisible(false);

        // TODO a suppr ------------ TEST d'icones -----------------------
        // testIcons();

        asideController.getAside();
        asideContainer.getChildren().add(asideController.getAside());
        asideController.getAside().setButtons(e -> createResearch(), e -> deleteSelected());
    }

    public boolean hasTissuRequisSelected() {
        return tissuRequisSelected != null;
    }

    public void addToSelected(TissuDto tissuSelected) {
        int longueurRequiseRestante = tissuRequisLaizeOptionService.getLongueurMinByRequis(tissuSelected.getId());
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
        if (projetSelected.getFournitureUsed() != null
                && projetSelected.getFournitureUsed().get(fournitureRequiseSelected) != null) {
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
    public void createResearch() {
        if (tissuRequisSelected != null) {
            displayTissu(tissuRequisService.getTissuSpecification(tissuRequisSelected));
        } else if (fournitureRequiseSelected != null) {
            displayFourniture(fournitureRequiseService.getFournitureSpecification(fournitureRequiseSelected));
        }
    }

    public boolean hasFournitureRequiseSelected() {
        return fournitureRequiseSelected != null;
    }

    /**
     * A supprimer plus tard Test pour l'affichage des icones
     */
    private void testIcons() {
        FlowPane pane = new FlowPane(5, 5);
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
