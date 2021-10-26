package fr.vbillard.tissusdeprincesseboot.controlers;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;

import de.jensd.fx.glyphs.GlyphIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.dtosFx.PatronDto;
import fr.vbillard.tissusdeprincesseboot.dtosFx.ProjetDto;
import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuDto;
import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuRequisDto;
import fr.vbillard.tissusdeprincesseboot.exception.NoSelectionException;
import fr.vbillard.tissusdeprincesseboot.fxCustomElements.MaterialElements;
import fr.vbillard.tissusdeprincesseboot.fxCustomElements.TissuRequisToggleButton;
import fr.vbillard.tissusdeprincesseboot.model.Patron;
import fr.vbillard.tissusdeprincesseboot.model.Photo;
import fr.vbillard.tissusdeprincesseboot.model.Preference;
import fr.vbillard.tissusdeprincesseboot.model.Projet;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.TissuRequis;
import fr.vbillard.tissusdeprincesseboot.model.TissuUsed;
import fr.vbillard.tissusdeprincesseboot.model.enums.ImageFormat;
import fr.vbillard.tissusdeprincesseboot.model.enums.ProjectStatus;
import fr.vbillard.tissusdeprincesseboot.model.enums.UnitePoids;
import fr.vbillard.tissusdeprincesseboot.services.ImageService;
import fr.vbillard.tissusdeprincesseboot.services.PatronService;
import fr.vbillard.tissusdeprincesseboot.services.PreferenceService;
import fr.vbillard.tissusdeprincesseboot.services.ProjetService;
import fr.vbillard.tissusdeprincesseboot.services.TissuService;
import fr.vbillard.tissusdeprincesseboot.services.TissuUsedService;
import fr.vbillard.tissusdeprincesseboot.utils.Articles;
import fr.vbillard.tissusdeprincesseboot.utils.Constants;
import fr.vbillard.tissusdeprincesseboot.utils.DevInProgressService;
import fr.vbillard.tissusdeprincesseboot.utils.EntityToString;
import fr.vbillard.tissusdeprincesseboot.utils.ModelUtils;
import fr.vbillard.tissusdeprincesseboot.utils.ShowAlert;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.imgscalr.Scalr;
import org.modelmapper.ModelMapper;

@Component
public class MainOverviewController {

	// ----------- FXML Tissu Liste -------------

	@FXML
	private TableView<TissuDto> tissuTable;
	@FXML
	private TableColumn<TissuDto, String> referenceColonne;
	@FXML
	private TableColumn<TissuDto, String> descriptionColonne;
	@FXML
	private TableColumn<TissuDto, String> matiereColonne;

	// ----------- FXML Tissu détails -------------

	@FXML
	private Label referenceLabel;
	@FXML
	private Label longueurLabel;
	@FXML
	private Label longueurRestanteLabel;

	@FXML
	private Label laizeLabel;
	@FXML
	private Label matiereLabel;
	@FXML
	private Label descriptionLabel;
	@FXML
	private Label poidsLabel;
	@FXML
	private Label typeLabel;
	@FXML
	private Label lieuDachatLabel;
	@FXML
	private Label decatiLabel;
	@FXML
	private Label unitePoidsLabel;
	@FXML
	private Label tissageLabel;
	@FXML
	private Label chuteLabel;
	@FXML
	private Button filtreTissuButton;
	@FXML
	private Button filtreResetTissuButton;
	@FXML
	private TextField filtreTissuTxt;
	@FXML
	private Button addTissuButton;
	@FXML
	private Button editTissuButton;
	@FXML
	private Button deleteTissuButton;
	@FXML
	private Button generateCouponButton;
	@FXML
	private Button searchPatronButton;
	@FXML
	private Button addInProjectButton;
	@FXML
	private Button archiveTissuButton;
	@FXML
	private Button addTissuPictureButton;

	// ----------- FXML Patron Liste -------------

	@FXML
	private TableView<PatronDto> patronTable;
	@FXML
	private TableColumn<PatronDto, String> marqueColonne;
	@FXML
	private TableColumn<PatronDto, String> referencePatronColonne;
	@FXML
	private TableColumn<PatronDto, String> modeleColonne;

	// ----------- FXML Patron détails -------------

	@FXML
	private Label referencePatronLabel;
	@FXML
	private Label marquePatronLabel;
	@FXML
	private Label modelPatronLabel;
	@FXML
	private Label typeVetementPatronLabel;
	@FXML
	private Label typeTissuPatronLabel;
	@FXML
	private Label longueurTissuPatronLabel;
	@FXML
	private Label descriptionPatronLabel;
	@FXML
	private Button addPatronButton;
	@FXML
	private Button editPatronButton;
	@FXML
	private Button deletePatronButton;
	@FXML
	private Button createProjectButton;
	@FXML
	private Button filtrePatronButton;
	@FXML
	private Button filtreResetPatronButton;

	// ----------- FXML Projet Main View-------------
	@FXML
	private Pane projetPanel;
	@FXML
	private Label descriptionProjetLabel;
	@FXML
	private Label marqueProjetLabel;
	@FXML
	private Label modelProjetLabel;
	@FXML
	private Label typeVetementProjetLabel;
	@FXML
	private Label projetStatusLabel;
	@FXML
	private VBox projetTissusUsedPanel;
	@FXML
	private Button editProjetDescription;
	@FXML
	private Button editProjetStatus;
	@FXML
	private Button deselectProjetBtn;
	@FXML
	private Label warningUnregistredLabel;

	// ----------- FXML Projet Liste -------------

	@FXML
	private TableView<ProjetDto> projetTable;
	@FXML
	private TableColumn<ProjetDto, String> marqueProjetColonne;
	@FXML
	private TableColumn<ProjetDto, String> statutProjetColonne;
	@FXML
	private TableColumn<ProjetDto, String> modeleProjetColonne;

	// ------------ FXML Projet Details --------------

	@FXML
	private Label descriptionProjetPanLabel;
	@FXML
	private Label marqueProjetPanLabel;
	@FXML
	private Label modelProjePantLabel;
	@FXML
	private Label typeVetementPanProjetLabel;
	@FXML
	private Label projetStatusPanLabel;
	@FXML
	private Button selectProjetPanButton;
	@FXML
	private Button deleteProjetPanButton;
	@FXML
	private Button filtrePatronPanButton;
	@FXML
	private Button filtreResetPatronPanButton;

	// ------------ FXML Photo Panels
	@FXML
	private Pane imagePanel;
	@FXML
	private ImageView imageView;
	@FXML
	private Button previousPicture;
	@FXML
	private Button nextPicture;
	@FXML
	private Button expendPicture;
	@FXML
	private Button deletePicture;
	@FXML
	private Button addPicture;

	// ------------ FXML others
	@FXML
	private ImageView robeImage;
	@FXML
	private Button saveAllBtn;

	private TissuService tissuService;
	private PatronService patronService;
	private StageInitializer mainApp;
	private ProjetDto projetSelected;
	private ProjetDto projetPanSelected;
	private PatronDto patronSelected;
	private TissuDto tissuSelected;
	private TissuRequisDto tissuRequisSelected;
	private TissuUsedService tissuUsedService;
	private ProjetService projetService;
	private ImageService imageService;
	private final ToggleGroup group = new ToggleGroup();
	private PreferenceService preferenceService = new PreferenceService();
	private ModelMapper mapper = new ModelMapper();

	private int photoIndex = 0;
	private List<Photo> photos = new ArrayList<Photo>();

	public MainOverviewController (TissuService tissuService, PatronService patronService, TissuUsedService tissuUsedService, ProjetService projetService, ImageService imageService) {
		this.tissuService = tissuService;
		this.patronService = patronService;
		this.tissuUsedService = tissuUsedService;
		this.imageService = imageService;
		this.projetService = projetService;
	}

	@FXML
	private void initialize() {
		imagePanel.setVisible(true);

		descriptionColonne.setCellValueFactory(cellData -> cellData.getValue().getDescriptionProperty());
		referenceColonne.setCellValueFactory(cellData -> cellData.getValue().getReferenceProperty());
		matiereColonne.setCellValueFactory(cellData -> cellData.getValue().getMatiereProperty());
		marqueColonne.setCellValueFactory(cellData -> cellData.getValue().getMarqueProperty());
		modeleColonne.setCellValueFactory(cellData -> cellData.getValue().getModeleProperty());
		referencePatronColonne.setCellValueFactory(cellData -> cellData.getValue().getReferenceProperty());
		marqueProjetColonne.setCellValueFactory(cellData -> cellData.getValue().getPatron().getMarqueProperty());
		statutProjetColonne.setCellValueFactory(cellData -> cellData.getValue().getProjectStatusProperty());
		modeleProjetColonne.setCellValueFactory(cellData -> cellData.getValue().getPatron().getModeleProperty());

		showTissuDetails(null);
		showPatronDetails(null);
		showProjetDetails(null);
		showProjetPanDetails(null);

		tissuTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showTissuDetails(newValue));
		patronTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showPatronDetails(newValue));
		projetTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showProjetPanDetails(newValue));

		group.selectedToggleProperty().addListener((ov, toggle, new_toggle) -> {
			if (new_toggle != null) {

				tissuRequisSelected = (TissuRequisDto) group.getSelectedToggle().getUserData();
			} else {
				System.out.println("deselect");
				tissuRequisSelected = null;
			}
		});

		GlyphIcon addProjectIcone = MaterialElements.playCircle();
		addProjectIcone.setDisable(true);
		GlyphIcon selectProjectIcone = MaterialElements.playCircle();
		selectProjectIcone.setDisable(true);

		selectProjetPanButton.setGraphic(selectProjectIcone);
		deleteProjetPanButton.setGraphic(MaterialElements.suppressNormal());
		filtrePatronPanButton.setGraphic(MaterialElements.searchTiny());
		filtreResetPatronPanButton.setGraphic(MaterialElements.suppressTiny());
		addPatronButton.setGraphic(MaterialElements.plusCircleNormal());
		editPatronButton.setGraphic(MaterialElements.editNormal());
		deletePatronButton.setGraphic(MaterialElements.suppressNormal());
		createProjectButton.setGraphic(MaterialElements.playCircle());
		filtrePatronButton.setGraphic(MaterialElements.searchTiny());
		filtreResetPatronButton.setGraphic(MaterialElements.suppressTiny());
		archiveTissuButton.setGraphic(MaterialElements.archive());
		archiveTissuButton.setTooltip(new Tooltip("Archiver ce tissu. Il pourra être retrouvé"));
		generateCouponButton.setGraphic(MaterialElements.cloneNormal());
		generateCouponButton.setTooltip(new Tooltip("Générer un coupon à partir de ce tissu"));
		searchPatronButton.setGraphic(new HBox(MaterialElements.searchNormal(), MaterialElements.project()));
		searchPatronButton.setTooltip(new Tooltip("Rechercher les patrons correspondant à ce tissu"));
		addInProjectButton.setGraphic(addProjectIcone);
		addInProjectButton.setTooltip(new Tooltip("Ajouter le tissu sélectionné au projet"));
		addTissuButton.setGraphic(MaterialElements.plusCircleNormal());
		addTissuButton.setTooltip(new Tooltip("Ajouter un nouveau tissu"));
		editTissuButton.setGraphic(MaterialElements.editNormal());
		editTissuButton.setTooltip(new Tooltip("Editer le tissu sélectionné"));
		deleteTissuButton.setGraphic(MaterialElements.searchNormal());
		deleteTissuButton.setTooltip(new Tooltip("Supprimer le tissu sélectionné. Cette opération est définitive"));
		filtreTissuButton.setGraphic(MaterialElements.searchTiny());
		filtreResetTissuButton.setGraphic(MaterialElements.suppressTiny());
		editProjetDescription.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.EDIT));
		editProjetStatus.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.EDIT));
		deselectProjetBtn.setGraphic(MaterialElements.suppressNormal());
		warningUnregistredLabel.setGraphic(MaterialElements.warning());
		addTissuPictureButton.setGraphic(MaterialElements.picture());
		previousPicture.setGraphic(MaterialElements.previousBig());
		nextPicture.setGraphic(MaterialElements.nextBig());
		addPicture.setGraphic(MaterialElements.plusCircleNormal());
		deletePicture.setGraphic(MaterialElements.suppressNormal());
		expendPicture.setGraphic(MaterialElements.expandPicture());
		setButtons();

		// robeImage.setImage(new
		// Image(getClass().getResourceAsStream("/images/depositphotos_111281678-stock-illustration-dummy-dress-hand-drawing-illustration.png")));
	}

	private void showProjetPanDetails(ProjetDto projetDto) {
		if (projetDto != null) {
			projetPanSelected = projetDto;
			descriptionProjetPanLabel.setText(projetDto.getDescription());
			marqueProjetPanLabel.setText(projetDto.getPatron().getMarque());
			modelProjePantLabel.setText(projetDto.getPatron().getModele());
			typeVetementPanProjetLabel.setText(projetDto.getPatron().getTypeVetement());
			projetStatusPanLabel.setText(projetDto.getProjectStatus());

		} else {
			descriptionProjetPanLabel.setText("");
			marqueProjetPanLabel.setText("");
			modelProjePantLabel.setText("");
			typeVetementPanProjetLabel.setText("");
			projetStatusPanLabel.setText("");
		}
		setButtons();
	}

	public void setMainApp(StageInitializer mainApp) {
		this.mainApp = mainApp;

		tissuTable.setItems(tissuService.getObservableList());
		patronTable.setItems(patronService.getObservableList());
		projetTable.setItems(projetService.getObservableList());
		setButtons();
	}

	private void showTissuDetails(TissuDto tissu) {
		if (tissu != null) {
			tissuSelected = tissu;
			referenceLabel.setText(tissu.getReference());
			longueurLabel.setText(Integer.toString(tissu.getLongueur()));
			longueurRestanteLabel.setText(Integer.toString(tissu.getLongueurRestante()));
			laizeLabel.setText(Integer.toString(tissu.getLaize()));
			matiereLabel.setText(tissu.getMatiere());
			descriptionLabel.setText(tissu.getDescription());
			poidsLabel.setText(Integer.toString(tissu.getPoids()));
			typeLabel.setText(tissu.getType());
			decatiLabel.setText(tissu.isDecati() ? "oui" : "non");
			lieuDachatLabel.setText(tissu.getLieuAchat());
			unitePoidsLabel.setText(tissu.getUnitePoids());
			chuteLabel.setText(tissu.isChute() ? "oui" : "non");
			tissageLabel.setText(tissu.getTissage());

		} else {
			referenceLabel.setText("");
			longueurLabel.setText("");
			longueurRestanteLabel.setText("");
			laizeLabel.setText("");
			matiereLabel.setText("");
			descriptionLabel.setText("");
			poidsLabel.setText("");
			typeLabel.setText("");
			decatiLabel.setText("");
			lieuDachatLabel.setText("");
			unitePoidsLabel.setText("");
			chuteLabel.setText("");
			tissageLabel.setText("");
		}
		setPicturePanel(0);
		setButtons();
	}

	private void showPatronDetails(PatronDto patron) {
		patronSelected = patron;
		if (patron != null) {
			referencePatronLabel.setText(patron.getReference());

			marquePatronLabel.setText(patron.getMarque());
			modelPatronLabel.setText(patron.getModele());
			typeVetementPatronLabel.setText(patron.getTypeVetement());

		} else {
			referencePatronLabel.setText("");
			marquePatronLabel.setText("");
			modelPatronLabel.setText("");
			typeVetementPatronLabel.setText("");
			typeTissuPatronLabel.setText("");
			longueurTissuPatronLabel.setText("");
		}
		setButtons();
	}

	@FXML
	private void saveAll() {
		DevInProgressService.notImplemented(mainApp);
	}

	@FXML
	private void reloadAll() {
		DevInProgressService.notImplemented(mainApp);

	}

	@FXML
	private void handleDeleteTissu() {

		if (tissuTable.getSelectionModel() != null && tissuTable.getSelectionModel().getSelectedItem() != null
				&& tissuTable.getSelectionModel().getSelectedItem().getId() >= 0) {
			
			Optional<ButtonType> option =ShowAlert.suppression(mainApp.getPrimaryStage(), EntityToString.TISSU, tissuTable.getSelectionModel().getSelectedItem().toString());

			if (option.get() == ButtonType.OK) {
				TissuDto selected = tissuTable.getSelectionModel().getSelectedItem();
				tissuService.delete(selected);
				tissuTable.setItems(tissuService.getObservableList());
			} else if (option.get() == ButtonType.CANCEL) {

			}

		} else {
			throw new NoSelectionException(Tissu.class, "Selectionnez un tissu dans la table");
		}
		setButtons();

	}

	@FXML
	private void handleNewTissu() {
		TissuDto tempTissu = mapper.map(
				new Tissu(0, "", 0, 0, "", null, null, 0, UnitePoids.NON_RENSEIGNE, false, "", null, false), TissuDto.class);
		boolean okClicked = mainApp.showTissuEditDialog(tempTissu);
		if (okClicked) {
			tissuTable.setItems(tissuService.getObservableList());
		}
		setButtons();
	}

	@FXML
	private void handleEditTissu() {
		tissuSelected = tissuTable.getSelectionModel().getSelectedItem();
		System.out.println(tissuSelected);
		if (tissuSelected != null) {
			try {
				boolean okClicked = mainApp.showTissuEditDialog(tissuSelected);
				if (okClicked) {
					showTissuDetails(tissuSelected);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			throw new NoSelectionException(Tissu.class, "Selectionnez un tissu dans la table");
		}
		setButtons();
	}

	@FXML
	private void handleAdvancedFilterTissu() {
		DevInProgressService.notImplemented(mainApp);
	}

	@FXML
	private void handleGenerateCoupon() {
		DevInProgressService.notImplemented(mainApp);
	}

	@FXML
	private void handleAddInProject() {
		int longueurRequiseRestante = tissuRequisSelected.getLongueur();
		if (projetSelected.getTissuUsed() != null && projetSelected.getTissuUsed().get(tissuRequisSelected) != null) {
			for (int id : projetSelected.getTissuUsed().get(tissuRequisSelected)) {
				longueurRequiseRestante -= tissuUsedService.getById(id).getLongueur();
			}
		}

		int longueur = mainApp.showSetLongueurDialog(longueurRequiseRestante, tissuSelected);

		TissuUsed tissuUsed = new TissuUsed();
		tissuUsed.setProjet(mapper.map(projetSelected, Projet.class));
		tissuUsed.setTissuRequis(mapper.map(tissuRequisSelected, TissuRequis.class));
		tissuUsed.setTissu(mapper.map(tissuSelected, Tissu.class));
		tissuUsed.setLongueur(longueur);
		tissuUsedService.saveOrUpdate(tissuUsed);

		projetSelected = mapper.map(projetService.getById(projetSelected.getId()), ProjetDto.class);
		showProjetDetails(projetSelected);
	}

	@FXML
	private void handleArchiveTissu() {
		tissuService.archive(tissuSelected);
	}

	@FXML
	private void handleFilterTissu() {
		tissuTable.setItems(tissuService.filter(filtreTissuTxt.getText()));
	}

	@FXML
	private void handleFilterResetTissu() {
		filtreTissuTxt.setText("");
		tissuTable.setItems(tissuService.getObservableList());
	}

	public void refreshList() {
		tissuTable.setItems(tissuService.getObservableList());
		patronTable.setItems(patronService.getObservableList());
	}

	public void projetPanelConfig(Projet projet) {
		DevInProgressService.notImplemented(mainApp);

	}

	@FXML
	private void handleNewPatron() {
		PatronDto tempPatron = new PatronDto();

		boolean okClicked = mainApp.showPatronEditDialog(tempPatron);
		if (okClicked) {
			patronTable.setItems(patronService.getObservableList());
		}
		setButtons();
	}

	@FXML
	private void handleEditPatron() {
		patronSelected = patronTable.getSelectionModel().getSelectedItem();
		if (patronSelected != null) {
			try {
				boolean okClicked = mainApp.showPatronEditDialog(patronSelected);
				if (okClicked) {
					showPatronDetails(patronSelected);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			throw new NoSelectionException(Tissu.class, "Selectionnez un tissu dans la table");
		}
		setButtons();
	}

	@FXML
	private void handleDeletePatron() {
		if (patronTable.getSelectionModel() != null && patronTable.getSelectionModel().getSelectedItem() != null
				&& patronTable.getSelectionModel().getSelectedItem().getId() >= 0) {
			Optional<ButtonType> option = ShowAlert.suppression(mainApp.getPrimaryStage(), EntityToString.PATRON, patronTable.getSelectionModel().getSelectedItem().toString());
			if (option.get() == ButtonType.OK) {
				PatronDto selected = patronTable.getSelectionModel().getSelectedItem();
				patronService.delete(selected);
				patronTable.setItems(patronService.getObservableList());
			}
		} else {
			throw new NoSelectionException(Tissu.class, "Selectionnez "+ModelUtils.generateString(EntityToString.TISSU, Articles.INDEFINI)+ " dans la table");
		}
		setButtons();
	}

	@FXML
	private void handleNewProject() {
		projetPanel.setVisible(true);
		warningUnregistredLabel.setVisible(true);
		if (patronSelected == null) {
			throw new NoSelectionException(Patron.class, "Selectionnez "+ModelUtils.generateString(EntityToString.TISSU, Articles.INDEFINI)+ " dans la table");

		} else {
			showProjetDetails(projetService.newProjetDto(patronSelected));
		}
		setButtons();
	}

	private void showProjetDetails(ProjetDto projet) {
		group.getToggles().clear();
		projetSelected = projet;
		projetTissusUsedPanel.getChildren().clear();

		if (projet != null) {
			if (projet.getProjectStatus() == null)
				projet.setProjectStatus(Constants.NON_ENREGISTRE);

			projetPanel.setVisible(true);
			descriptionProjetLabel.setText(projet.getDescription());
			projetStatusLabel.setText(projet.getProjectStatus());

			if (projet.getPatron() != null) {
				marqueProjetLabel.setText(projet.getPatron().getMarque() == null ? "" : projet.getPatron().getMarque());
				modelProjetLabel.setText(projet.getPatron().getModele() == null ? "" : projet.getPatron().getModele());
				typeVetementProjetLabel.setText(
						projet.getPatron().getTypeVetement() == null ? "" : projet.getPatron().getTypeVetement());
			} else {
				marqueProjetLabel.setText("");
				modelProjetLabel.setText("");
				typeVetementProjetLabel.setText("");
			}

			for (TissuRequisDto tr : projet.getTissuUsed().keySet()) {

				Label tissuRequisLbl = new Label(
						"tissu " + tr.getGammePoids() + " - " + tr.getLongueur() + "cm x " + tr.getLaize() + "cm");
				tissuRequisLbl.setStyle("-fx-font-size: 16");
				VBox vb = new VBox(tissuRequisLbl);
				vb.setPadding(new Insets(5, 5, 5, 5));
				vb.setBorder(new Border(new BorderStroke(Color.GREY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
						BorderWidths.DEFAULT)));

				if (tr.getVariant() != null) {

					TitledPane tp = new TitledPane();

					VBox content = new VBox();

					if (tr.getVariant().size() == 1)
						tp.setText("Tissu conseilé ");
					else if (tr.getVariant().size() > 1) {
						tp.setText("Tissus conseilés :");
					}
					tp.setContent(content);

					for (int i = 0; i < tr.getVariant().size(); i++) {
						String variant = tr.getVariant().get(i);
						content.getChildren().add(new Label(i + 1 + "- " + variant));
					}
					
					tp.setExpanded(false);
					VBox vbox = new VBox();

					int longueurInitiale = tr.getLongueur();
					int longueurFinale = 0;
					for (Integer id : projet.getTissuUsed().get(tr)) {
						TissuUsed tissuUsed = tissuUsedService.getById(id);
						vbox.getChildren().addAll(new Label(tissuUsed.getTissu().getDescription()),
								new Label(tissuUsed.getLongueur() + " cm"));
						longueurFinale += tissuUsed.getLongueur();
					}
					Text lbl = new Text(longueurFinale + "cm /" + longueurInitiale + "cm");
					lbl.setFill(longueurFinale < longueurInitiale ? Constants.colorDelete : Constants.colorAdd);

					vbox.getChildren().add(lbl);

					FontAwesomeIconView plusOne = new FontAwesomeIconView(FontAwesomeIcon.PLUS_CIRCLE);
					plusOne.setFill(Constants.colorAdd);
					TissuRequisToggleButton rb1 = new TissuRequisToggleButton(tr);
					rb1.setDisable(projetSelected.getId() != 0);
					rb1.setToggleGroup(group);
					rb1.setGraphic(plusOne);
					rb1.setUserData(tr);

					Button filter = new Button();
					filter.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent e) {
							filterTissusByTissuRequis(tr);
						}
					});
					filter.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.SEARCH));

					HBox hb = new HBox(rb1, filter);
					vb.getChildren().addAll(tp, vbox, hb);

				}
				projetTissusUsedPanel.getChildren().add(vb);
				projetTissusUsedPanel.setSpacing(10);
			}

		} else {
			descriptionProjetLabel.setText("");
			marqueProjetLabel.setText("");
			modelProjetLabel.setText("");
			typeVetementProjetLabel.setText("");
			projetStatusLabel.setText("");
			projetPanel.setVisible(false);
		}
		setButtons();
	}

	private void filterTissusByTissuRequis(TissuRequisDto tr) {
		DevInProgressService.notImplemented(mainApp);

		// tissuTable.setItems(tissuService.getTissuData().stream().map(t ->
		// ).collect(Collectors.toList()));
	}

	@FXML
	private void handleEditProjectDescription() {
		String newData = mainApp.showTextEditDialog(descriptionProjetLabel.getText(), "description");

		projetSelected.setDescription(newData);
		if (!projetStatusLabel.getText().equals(Constants.NON_ENREGISTRE))
			projetService.saveOrUpdate(projetSelected);
		descriptionProjetLabel.setText(newData);
		setButtons();
	}

	@FXML
	private void handleEditProjectStatus() {
		String newData = mainApp.showChoiceBoxEditDialog(projetStatusLabel.getText(), ProjectStatus.class);

		if (newData.equals(ProjectStatus.EN_COURS.label)) {
			if (!istissuUsedValid()) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.initOwner(mainApp.getPrimaryStage());
				alert.setTitle("Tissus requis");
				alert.setHeaderText("Du tissu semble manquer");
				alert.setContentText(
						"Il manque du tissu pour réaliser ce modèle! Vous pouvez continuer tout de même, ou annuler");
				Optional<ButtonType> option = alert.showAndWait();

				if (option.get() != null && option.get() == ButtonType.OK) {
					projetStatusLabel.setText(newData);
					projetSelected.setProjectStatus(newData);
					projetSelected = projetService.saveOrUpdate(projetSelected);
					warningUnregistredLabel.setVisible(false);
					setButtons();
				}
			}
		} else if (newData.equals(ProjectStatus.TERMINE.label)) {
			System.out.println("ici");
			Map<TissuDto, Integer> tissusToChange = new HashMap<TissuDto, Integer>();

			projetSelected.getTissuUsed().values().forEach(lst -> {
				lst.forEach(val -> {
					TissuUsed tu = tissuUsedService.getById(val);
					Tissu tissu = tu.getTissu();
					int oldValue = tissu.getLongueur();
					tissu.setLongueur(tissu.getLongueur() - tu.getLongueur());
					tissusToChange.put(mapper.map(tissu, TissuDto.class), oldValue);
				});
			});
			mainApp.showTissuEditDialog(tissusToChange);

		}
		if (!newData.equals(Constants.NON_ENREGISTRE)) {
			warningUnregistredLabel.setVisible(false);
			projetStatusLabel.setText(newData);
			projetSelected.setProjectStatus(newData);
			projetSelected = projetService.saveOrUpdate(projetSelected);
			setButtons();
		}
	}

	@FXML
	private void handleDeselectProjet() {
		showProjetDetails(null);
	}

	@FXML
	private void handleSelectProjet() {
		showProjetDetails(projetPanSelected);
	}

	@FXML
	private void handleDeleteProjet() {
		DevInProgressService.notImplemented(mainApp);
	}

	@FXML
	private void handleAddTissuPicture() {
		addPicture();
	}

	@FXML
	private void previousPicture() {
		photoIndex--;
		setPicture();
	}

	@FXML
	private void nextPicture() {
		photoIndex++;
		setPicture();

	}

	@FXML
	private void expendPicture() {
		mainApp.showPictureExpended(photos.get(photoIndex));

	}

	@FXML
	private void deletePicture() {

		Optional<ButtonType> option =ShowAlert.suppression(mainApp.getPrimaryStage(), EntityToString.IMAGE, "");

		if (option.get() != null && option.get() == ButtonType.OK) {
			imageService.delete(photos.get(photoIndex));
			setPicturePanel(photoIndex - 1);
		}
	}

	@FXML
	private void addPicture() {
		Preference pref = preferenceService.getPreferences();
		File file = mainApp.directoryChooser(pref);
		if (file != null)
			try {
				String name = file.getName();
				String extension = name.substring(name.lastIndexOf(".") + 1);
				BufferedImage bufferedImage = ImageIO.read(file);
				bufferedImage = Scalr.resize(bufferedImage, 900);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ImageIO.write(bufferedImage, extension, baos);
				byte[] data = baos.toByteArray();
				Photo image = new Photo();
				image.setData(data);
				image.setNom(name);
				image.setFormat(ImageFormat.valueOf(extension.toUpperCase()));
				image.setTissu(mapper.map(tissuSelected, Tissu.class));
				imageService.saveOrUpdate(image);
				baos.close();
				setPicturePanel(photos.size());

				pref.setPictureLastUploadPath(file.getAbsolutePath());
				preferenceService.savePreferences(pref);
			} catch (IOException e) {
				e.printStackTrace();
			}

	}

	private void setButtons() {
		addTissuButton.setDisable(false);
		editTissuButton.setDisable(tissuSelected == null);
		deleteTissuButton.setDisable(tissuSelected == null);
		generateCouponButton.setDisable(tissuSelected == null);
		searchPatronButton.setDisable(tissuSelected == null);
		addInProjectButton.setDisable(tissuSelected == null || projetSelected == null
				|| projetSelected.getIdProperty() == null || projetSelected.getId() == 0);
		archiveTissuButton.setDisable(tissuSelected == null);
		addTissuPictureButton.setDisable(tissuSelected == null);
		addPatronButton.setDisable(false);
		editPatronButton.setDisable(patronSelected == null);
		deletePatronButton.setDisable(patronSelected == null);
		createProjectButton.setDisable(patronSelected == null || projetSelected != null);
		filtrePatronButton.setDisable(false);
		filtreResetPatronButton.setDisable(false);
		editProjetDescription.setDisable(false);
		editProjetStatus.setDisable(false);
		selectProjetPanButton.setDisable(projetPanSelected == null);
		deleteProjetPanButton.setDisable(projetPanSelected == null);
		nextPicture.setDisable(photoIndex >= photos.size() - 1);
		previousPicture.setDisable(photoIndex <= 0);
		deletePicture.setDisable(photos.size() == 0);
		expendPicture.setDisable(photos.size() == 0);

		group.getToggles().forEach(toggle -> {
			Node node = (Node) toggle;
			node.setDisable(projetSelected == null || projetSelected.getId() == 0);
		});
	}

	private void setPicturePanel(int index) {
		imagePanel.setVisible(tissuSelected != null);
		photoIndex = index <= 0 ? 0 : index;
		if (tissuSelected != null) {
			photos = imageService.getImages(mapper.map(tissuSelected, Tissu.class));
			if (!photos.isEmpty()) {
				setPicture();
			} else {
				imageView.setImage(null);
			}
		} else {
			photos = new ArrayList<Photo>();
		}
	}

	private void setPicture() {
		imageView.setImage(new Image(new ByteArrayInputStream(photos.get(photoIndex).getData())));
		setButtons();

	}

	private boolean istissuUsedValid() {

		for (TissuRequisDto tr : projetSelected.getTissuUsed().keySet()) {
			int longueur = tr.getLongueur();
			for (int id : projetSelected.getTissuUsed().get(tr)) {
				longueur -= tissuUsedService.getById(id).getLongueur();
			}
			if (longueur > 0)
				return false;
		}
		return true;
	}

}
