package fr.vbillard.tissusdeprincesseboot.controller.patron;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.util.Strings;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.IController;
import fr.vbillard.tissusdeprincesseboot.controller.RootController;
import fr.vbillard.tissusdeprincesseboot.controller.pictureHelper.PatronPictureHelper;
import fr.vbillard.tissusdeprincesseboot.dtosFx.PatronDto;
import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuRequisDto;
import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuVariantDto;
import fr.vbillard.tissusdeprincesseboot.fxCustomElement.GlyphIconUtil;
import fr.vbillard.tissusdeprincesseboot.fxCustomElement.IntegerSpinner;
import fr.vbillard.tissusdeprincesseboot.model.Patron;
import fr.vbillard.tissusdeprincesseboot.model.Photo;
import fr.vbillard.tissusdeprincesseboot.model.enums.GammePoids;
import fr.vbillard.tissusdeprincesseboot.model.enums.TypeTissuEnum;
import fr.vbillard.tissusdeprincesseboot.service.ImageService;
import fr.vbillard.tissusdeprincesseboot.service.MatiereService;
import fr.vbillard.tissusdeprincesseboot.service.PatronService;
import fr.vbillard.tissusdeprincesseboot.service.TissageService;
import fr.vbillard.tissusdeprincesseboot.service.TissuRequisService;
import fr.vbillard.tissusdeprincesseboot.service.TissuVariantService;
import fr.vbillard.tissusdeprincesseboot.utils.DevInProgressService;
import fr.vbillard.tissusdeprincesseboot.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.utils.FxUtils;
import fr.vbillard.tissusdeprincesseboot.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

@Component
public class PatronEditController implements IController {

	@FXML
	private JFXTextField marqueField;
	@FXML
	private JFXTextField modeleField;
	@FXML
	private JFXTextField typeVetementField;
	@FXML
	private JFXButton addTissuButton;
	@FXML
	private JFXButton addFournitureButton;
	@FXML
	private VBox tissuEtFournitureContainer;
	@FXML
	private VBox conseilleContainer;
	@FXML
	private GridPane tissusPatronListGrid;
	@FXML
	private JFXButton generateReferenceButton;
	@FXML
	private JFXTextField referenceField;
	@FXML
	public ImageView imagePane;

	private StageInitializer initializer;
	private RootController root;
	private PatronDto patron;
	// private TissuRequisDto tissuRequisDto;
	private final TissuRequisService tissuRequisService;
	private final TissuVariantService tissuVariantService;
	private final MatiereService matiereService;
	private final TissageService tissageService;
	private final ImageService imageService;
	private PatronPictureHelper pictureUtils;
	private Optional<Photo> pictures;
	private final ModelMapper mapper;
	private boolean okClicked = false;
	private StageInitializer mainApp;
	// private ObservableList<TissuRequisDto> listTissuRequis;
	private boolean unregistredPatron;
	private final PatronService patronService;
	private int longueur;
	private int laize;
	// private HBox tissuRequisDisplayHbox;
	private TissuVariantDto variantSelected;
	boolean editingVariant = false;
	private ObservableList<TissuVariantDto> tvList;
	private VBox bottomRightVbox;

	public PatronEditController(RootController root, ImageService imageService, PatronPictureHelper pictureUtils,
			TissuRequisService tissuRequisService, TissuVariantService tissuVariantService,
			MatiereService matiereService, TissageService tissageService, ModelMapper mapper,
			PatronService patronService) {

		this.tissuRequisService = tissuRequisService;
		this.tissuVariantService = tissuVariantService;
		this.matiereService = matiereService;
		this.tissageService = tissageService;
		this.mapper = mapper;
		this.patronService = patronService;
		this.pictureUtils = pictureUtils;
		this.imageService = imageService;
		this.root = root;
	}

	@FXML
	private void initialize() {
		addTissuButton.setGraphic(GlyphIconUtil.plusCircleNormal());
		addFournitureButton.setGraphic(GlyphIconUtil.plusCircleNormal());

		generateReferenceButton.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.MAGIC));
		generateReferenceButton.setTooltip(new Tooltip("Générer une référence automatiquement"));
	}

	private void setDisabledButton() {
		unregistredPatron = (patron == null || patron.getIdProperty() == null || patron.getId() == 0);
		addTissuButton.setDisable(unregistredPatron);
		addFournitureButton.setDisable(unregistredPatron);
	}

	/**
	 * Charge les tissusRequis, en fonction du patron sélectionné. tableau sous le
	 * patron : tissusRequis.toString() - boutons
	 */
	private void loadTissuRequisForPatron() {
		tissusPatronListGrid.getChildren().clear();
		patron.setTissusRequis(tissuRequisService.getAllTissuRequisByPatron(patron.getId()).stream()
				.map(tr -> mapper.map(tr, TissuRequisDto.class)).collect(Collectors.toList()));

		if (patron.getTissusRequisProperty() != null && patron.getTissusRequis() != null) {

			for (int i = 0; i < patron.getTissusRequis().size(); i++) {

				TissuRequisDto tissu = patron.getTissusRequis().get(i);

				JFXButton editButton = new JFXButton();
				editButton.setGraphic(GlyphIconUtil.editNormal());
				editButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> displayTissuRequis(tissu));

				JFXButton deleteButton = new JFXButton();
				deleteButton.setGraphic(GlyphIconUtil.suppressNormal());
				deleteButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> deleteTissuRequis(tissu));

				HBox hbox = new HBox(editButton, deleteButton);
				hbox.setSpacing(10);
				hbox.setAlignment(Pos.CENTER_LEFT);
				tissusPatronListGrid.add(new Label(tissu.toString()), 0, i);
				tissusPatronListGrid.add(hbox, 1, i);
			}
		}
	}

	/**
	 * Détails d'un tissu requis, pour création ou édition
	 *
	 * @param tissu
	 */
	private void displayTissuRequis(TissuRequisDto tissu) {
		variantSelected = new TissuVariantDto();
		longueur = tissu.getLongueur();
		laize = tissu.getLaize();
		tissuEtFournitureContainer.getChildren().clear();
		tvList = FXCollections.observableArrayList(new ArrayList<TissuVariantDto>());
		bottomRightVbox = new VBox();

		Label titre = new Label("Tissus recommandés : ");

		GridPane topGrid = new GridPane();
		topGrid.setVgap(10);
		topGrid.setHgap(20);

		topGrid.setPadding(new Insets(10, 0, 10, 0));
		topGrid.add(new Label("Longeur"), 0, 0);
		topGrid.add(new Label("Laize"), 0, 1);
		topGrid.add(new Label("Gamme de poids"), 0, 2);

		JFXTextField longueurSpinner = new JFXTextField();
		longueurSpinner.setText(FxUtils.safePropertyToString(tissu.getLongueurProperty()));
		longueurSpinner.setTextFormatter(IntegerSpinner.getFormatter());

		topGrid.add(longueurSpinner, 1, 0);

		JFXTextField laizeSpinner = new JFXTextField();
		longueurSpinner.setText(FxUtils.safePropertyToString(tissu.getLaizeProperty()));
		laizeSpinner.setTextFormatter(IntegerSpinner.getFormatter());

		topGrid.add(laizeSpinner, 1, 1);

		JFXComboBox<String> gammePoidsChBx = new JFXComboBox<String>();
		gammePoidsChBx.setItems(FXCollections.observableArrayList(GammePoids.labels()));
		gammePoidsChBx.setValue(tissu.getGammePoidsProperty() == null || tissu.getGammePoids() == null
				|| tissu.getGammePoids().equals("") ? GammePoids.NON_RENSEIGNE.label : tissu.getGammePoids());
		topGrid.add(gammePoidsChBx, 1, 2);

		JFXButton validateBtn = new JFXButton("Valider");
		validateBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
			tissu.setGammePoids(gammePoidsChBx.getValue());
			tissu.setLaize(Integer.valueOf(laizeSpinner.getText()));
			tissu.setLongueur(Integer.valueOf(longueurSpinner.getText()));
			tissu.setGammePoids(gammePoidsChBx.getValue());
			saveTissuRequis(tissu);
		});

		JFXButton cancelBtn = new JFXButton("Annuler");
		cancelBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> tissuEtFournitureContainer.getChildren().clear());

		JFXButton deleteBtn = new JFXButton("Supprimer");
		deleteBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> deleteTissuRequis(tissu));

		HBox hboxBtn = new HBox(validateBtn, cancelBtn, deleteBtn);
		hboxBtn.setSpacing(10);
		hboxBtn.setAlignment(Pos.CENTER);
		hboxBtn.setPadding(new Insets(20, 20, 20, 20));

		tissuEtFournitureContainer.getChildren().addAll(titre, topGrid, hboxBtn, bottomRightVbox);

		if (tissu != null) {
			tvList = FXCollections.observableArrayList(tissuVariantService.getVariantByTissuRequis(tissu));
		}

		loadBottomRightVbox(tissu);

	}

	private void loadBottomRightVbox(TissuRequisDto tissu) {
		bottomRightVbox.getChildren().clear();

		bottomRightVbox.getChildren().addAll(new Separator(Orientation.HORIZONTAL), new Label("Possibilités :"));

		if (tvList != null && tvList.size() > 0) {
			GridPane bottomGrid = new GridPane();
			bottomGrid.setPadding(new Insets(5, 0, 5, 0));
			bottomRightVbox.getChildren().add(bottomGrid);
			bottomGrid.getColumnConstraints().addAll(new ColumnConstraints(300), new ColumnConstraints(100));

			for (int i = 0; i < tvList.size(); i++) {
				TissuVariantDto tv = tvList.get(i);
				JFXButton editButton = new JFXButton();
				editButton.setGraphic(GlyphIconUtil.editNormal());
				editButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
					editingVariant = true;
					DevInProgressService.notImplemented(mainApp);
				});

				JFXButton deleteButton = new JFXButton();
				deleteButton.setGraphic(GlyphIconUtil.suppressNormal());
				deleteButton.addEventHandler(MouseEvent.MOUSE_CLICKED,
						e -> DevInProgressService.notImplemented(mainApp));
				HBox btns = new HBox(editButton, deleteButton);
				btns.setAlignment(Pos.CENTER_RIGHT);
				btns.setSpacing(10);

				bottomGrid.add(new Label(Utils.safeString(tv.getTypeTissu()) + " " + Utils.safeString(tv.getMatiere())
						+ " " + Utils.safeString(tv.getTissage())), 0, i * 2);
				bottomGrid.add(btns, 1, i * 2);

				if (i != tvList.size() - 1) {
					HBox hbox = new HBox(new Label("-------------   OU   --------------  "));
					hbox.setAlignment(Pos.CENTER);
					bottomGrid.add(hbox, 0, i * 2 + 1, 2, 1);
					// displayTissuRequis(tissu);
				}
			}
		}

		if (tissu != null && tissu.getId() != 0) {

			JFXComboBox<String> typeField = new JFXComboBox<String>();
			typeField.setItems(FXCollections.observableArrayList(TypeTissuEnum.labels()));
			typeField.setValue(variantSelected.getTypeTissuProperty() == null ? TypeTissuEnum.NON_RENSEIGNE.label
					: variantSelected.getTypeTissu());

			JFXComboBox<String> matiereField = new JFXComboBox<String>();
			matiereField.setItems(FXCollections.observableArrayList(
					matiereService.getAll().stream().map(m -> m.getValue()).collect(Collectors.toList())));
			matiereField.setValue(
					variantSelected.getMatiereProperty() == null ? Strings.EMPTY : variantSelected.getMatiere());

			JFXComboBox<String> tissageField = new JFXComboBox<String>();
			tissageField.setItems(FXCollections.observableArrayList(
					tissageService.getAll().stream().map(t -> t.getValue()).collect(Collectors.toList())));
			tissageField.setValue(
					variantSelected.getTissageProperty() == null ? Strings.EMPTY : variantSelected.getTissage());

			JFXButton addTvBtn = new JFXButton();
			addTvBtn.setGraphic(GlyphIconUtil.plusCircleNormal());
			addTvBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
				variantSelected.setMatiere(matiereField.getValue());
				variantSelected.setTissage(tissageField.getValue());
				variantSelected.setTypeTissu(typeField.getValue());
				variantSelected.setTissuRequisId(tissu.getId());
				variantSelected = tissuVariantService.saveOrUpdate(variantSelected);
				if (variantSelected.getId() == 0) {
					tvList.add(variantSelected);
				}
				editingVariant = false;
				loadTissuRequisForPatron();
				displayTissuRequis(tissu);
			});
			HBox hboxTissuVariant = new HBox(typeField, matiereField, tissageField, addTvBtn);
			hboxTissuVariant.setSpacing(10);
			hboxTissuVariant.setAlignment(Pos.CENTER);
			hboxTissuVariant.setPadding(new Insets(20, 20, 20, 20));
			bottomRightVbox.getChildren().addAll(new Separator(Orientation.HORIZONTAL), hboxTissuVariant);
		}
	}

	public void saveTissuRequis(TissuRequisDto tissu) {
		boolean edit = tissu.getId() != 0;
		TissuRequisDto tissuReturned = tissuRequisService.createOrUpdate(tissu, patron);
		if (!edit) {
			patron.getTissusRequis().add(tissu);
		}
		loadTissuRequisForPatron();
		displayTissuRequis(tissuReturned);

	}

	private void deleteTissuRequis(TissuRequisDto tissu) {
		tissuRequisService.delete(tissu);
		loadTissuRequisForPatron();
	}

	/**
	 * Returns true if the user clicked OK, false otherwise.
	 *
	 * @return
	 */
	public boolean isOkClicked() {
		return okClicked;
	}

	@FXML
	private void handleSavePatron() {
		if (isInputValid()) {

			patron.setReference(referenceField.getText());
			patron.setMarque(marqueField.getText());
			patron.setModele(modeleField.getText());
			patron.setTypeVetement(typeVetementField.getText());

			patron = patronService.create(patron);
			setDisabledButton();

			okClicked = true;

		}
	}

	@FXML
	private void handleSaveAndQuitPatron() {
		if (isInputValid()) {

			handleSavePatron();
			root.displayPatronDetails(patron);

		}
	}

	@FXML
	private void handleCancel() {
		if (patron.getId() != 0) {
			root.displayPatronDetails(patron);
		} else {
			root.displayPatrons();
		}

	}

	@FXML
	private void handleGenerateReference() {
		StringBuilder sb = new StringBuilder();
		sb.append(marqueField.getText().trim().isEmpty() ? "X" : marqueField.getText().toUpperCase().charAt(0))
				.append(modeleField.getText().trim().isEmpty() ? "X" : modeleField.getText().toUpperCase().charAt(0))
				.append(typeVetementField.getText().trim().isEmpty() ? "X"
						: typeVetementField.getText().toUpperCase().charAt(0))
				.append("-");
		boolean ref = true;
		int refNb = 0;
		while (ref) {
			refNb++;
			ref = patronService.existByReference(sb.toString() + refNb);
		}
		referenceField.setText(sb.append(refNb).toString());
	}

	@FXML
	private void handleTissuListedit() {

		displayTissuRequis(new TissuRequisDto());
	}

	@FXML
	private void handleFournitureListedit() {
		DevInProgressService.notImplemented(mainApp);

	}

	/**
	 * Validates the user input in the text fields.
	 *
	 * @return true if the input is valid
	 */
	private boolean isInputValid() {
		String errorMessage = "";

		if (referenceField.getText() == null || referenceField.getText().length() == 0) {
			errorMessage += "Référence non renseignée.\n";
		}
		if (marqueField.getText() == null || marqueField.getText().length() == 0) {
			errorMessage += "Marque non renseignée.\n";
		}
		if (modeleField.getText() == null || modeleField.getText().length() == 0) {
			errorMessage += "Modèle non renseigné.\n";
		}
		if (typeVetementField.getText() == null || typeVetementField.getText().length() == 0) {
			errorMessage += "Type non renseigné.\n";
		}

		if (errorMessage.length() == 0) {
			return true;
		} else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Champ(s) invalide(s)");
			alert.setHeaderText("Merci de corriger :");
			alert.setContentText(errorMessage);

			alert.showAndWait();

			return false;
		}
	}

	@Override
	public void setStageInitializer(StageInitializer initializer, FxData data) {
		this.initializer = initializer;

		if (data == null || data.getPatron() == null) {
			patron = mapper.map(new Patron("", "", "", "", "", null), PatronDto.class);
			setDisabledButton();

		} else {
			patron = data.getPatron();

			setDisabledButton();

			referenceField.setText(patron.getReferenceProperty() == null ? "" : patron.getReference());
			marqueField.setText(patron.getMarqueProperty() == null ? "" : patron.getMarque());
			modeleField.setText(patron.getModeleProperty() == null ? "" : patron.getModele());
			typeVetementField.setText(patron.getTypeVetementProperty() == null ? "" : patron.getTypeVetement());

			pictureUtils.setPane(imagePane, patron);

			loadTissuRequisForPatron();
		}
	}

	@FXML
	private void addPicture() {
		patronService.saveOrUpdate(mapper.map(patron, Patron.class));

		pictureUtils.addPictureLocal();

	}

	@FXML
	private void addPictureWeb() {
		patronService.saveOrUpdate(mapper.map(patron, Patron.class));

		pictureUtils.addPictureWeb();

	}

	@FXML
	private void pictureExpend() {

	}

	/*
	 * txtInput.setEditable(false); txtInput.setMouseTransparent(true);
	 * txtInput.setFocusTraversable(false);
	 */
}
