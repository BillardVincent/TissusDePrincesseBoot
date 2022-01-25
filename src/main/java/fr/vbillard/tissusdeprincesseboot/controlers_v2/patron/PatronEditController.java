package fr.vbillard.tissusdeprincesseboot.controlers_v2.patron;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controlers_v2.IController;
import fr.vbillard.tissusdeprincesseboot.dtosFx.FxDto;
import fr.vbillard.tissusdeprincesseboot.dtosFx.PatronDto;
import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuRequisDto;
import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuVariantDto;
import fr.vbillard.tissusdeprincesseboot.fxCustomElements.GlyphIconUtil;
import fr.vbillard.tissusdeprincesseboot.model.Patron;
import fr.vbillard.tissusdeprincesseboot.model.enums.GammePoids;
import fr.vbillard.tissusdeprincesseboot.services.MatiereService;
import fr.vbillard.tissusdeprincesseboot.services.PatronService;
import fr.vbillard.tissusdeprincesseboot.services.TissageService;
import fr.vbillard.tissusdeprincesseboot.services.TissuRequisService;
import fr.vbillard.tissusdeprincesseboot.services.TissuVariantService;
import fr.vbillard.tissusdeprincesseboot.services.TypeTissuService;
import fr.vbillard.tissusdeprincesseboot.utils.DevInProgressService;
import fr.vbillard.tissusdeprincesseboot.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

@Component
public class PatronEditController implements IController{

	@FXML
	private TextField marqueField;
	@FXML
	private TextField modeleField;
	@FXML
	private TextField typeVetementField;
	@FXML
	private Button addTissuButton;
	@FXML
	private Button addFournitureButton;
	@FXML
	private VBox rightContainer;
	@FXML
	private GridPane tissusPatronListGrid;
	@FXML
	private Button generateReferenceButton;
	@FXML
	private TextField referenceField;

	private StageInitializer initializer;
	private PatronDto patron;
	//private TissuRequisDto tissuRequisDto;
	private final TissuRequisService tissuRequisService;
	private final TissuVariantService tissuVariantService;
	private final MatiereService matiereService;
	private final TissageService tissageService;
	private final TypeTissuService typeTissuService;
	private final ModelMapper mapper;
	private boolean okClicked = false;
	private StageInitializer mainApp;
	//private ObservableList<TissuRequisDto> listTissuRequis;
	private boolean unregistredPatron;
	private final PatronService patronService;
	private int longueur;
	private int laize;
	//private HBox tissuRequisDisplayHbox;
	private TissuVariantDto variantSelected;
	boolean editingVariant = false;
	private ObservableList<TissuVariantDto> tvList;
	private VBox bottomRightVbox;

	public PatronEditController(TissuRequisService tissuRequisService, TissuVariantService tissuVariantService, MatiereService matiereService, TissageService tissageService, TypeTissuService typeTissuService, ModelMapper mapper, PatronService patronService) {

		this.tissuRequisService = tissuRequisService;
		this.tissuVariantService = tissuVariantService;
		this.matiereService = matiereService;
		this.tissageService = tissageService;
		this.typeTissuService = typeTissuService;
		this.mapper = mapper;
		this.patronService = patronService;
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
	 * Charge les tissusRequis, en fonction du patron sélectionné.
	 * tableau sous le patron : tissusRequis.toString() - boutons
	 */
	private void loadTissuRequisForPatron() {
		tissusPatronListGrid.getChildren().clear();
		patron.setTissusRequis(tissuRequisService.getAllTissuRequisByPatron(patron.getId()).stream().map(tr-> mapper.map(tr, TissuRequisDto.class)).collect(Collectors.toList()));

		if (patron.getTissusRequisProperty() != null && patron.getTissusRequis() != null) {

			for (int i = 0; i < patron.getTissusRequis().size(); i++) {

				TissuRequisDto tissu = patron.getTissusRequis().get(i);

				Button editButton = new Button();
				editButton.setGraphic(GlyphIconUtil.editNormal());
				editButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> displayTissuRequis(tissu));

				Button deleteButton = new Button();
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
	 * Détails d'un tissu requis, pour édition
	 *
	 * @param tissu
	 */
	private void displayTissuRequis(TissuRequisDto tissu) {
		variantSelected = new TissuVariantDto();
		longueur = tissu.getLongueur();
		laize = tissu.getLaize();
		/*
		 * if (tissu == null) { tissu = new TissuRequisDto(); }
		 */
		rightContainer.getChildren().clear();
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

		Spinner<Integer> longueurSpinner = new Spinner<Integer>();
		longueurSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE,
				tissu.getLongueurProperty() == null ? 0 : tissu.getLongueur()));
		longueurSpinner.setEditable(true);
		longueurSpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
			longueur = newValue;
		});
		longueurSpinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue) {
				longueurSpinner.increment(0); // won't change value, but will commit editor
				longueur = longueurSpinner.getValue();
			}
		});

		topGrid.add(longueurSpinner, 1, 0);

		Spinner<Integer> laizeSpinner = new Spinner<Integer>();
		laizeSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE,
				tissu.getLaizeProperty() == null ? 0 : tissu.getLaize()));
		laizeSpinner.setEditable(true);
		laizeSpinner.valueProperty().addListener((obs, oldValue, newValue) -> laize = newValue);
		laizeSpinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue) {
				laizeSpinner.increment(0); // won't change value, but will commit editor
				laize = laizeSpinner.getValue();
			}
		});
		topGrid.add(laizeSpinner, 1, 1);

		longueurSpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
			longueur = newValue;
		});
		laizeSpinner.valueProperty().addListener((obs, oldValue, newValue) -> laize = newValue);

		ChoiceBox<String> gammePoidsChBx = new ChoiceBox<String>();
		gammePoidsChBx.setItems(FXCollections.observableArrayList(GammePoids.labels()));
		gammePoidsChBx.setValue(tissu.getGammePoidsProperty() == null || tissu.getGammePoids() == null
				|| tissu.getGammePoids().equals("") ? GammePoids.NON_RENSEIGNE.label : tissu.getGammePoids());
		topGrid.add(gammePoidsChBx, 1, 2);

		Button validateBtn = new Button("Valider");
		validateBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
			tissu.setGammePoids(gammePoidsChBx.getValue());
			tissu.setLaize(laize);
			tissu.setLongueur(longueur);
			tissu.setGammePoids(gammePoidsChBx.getValue());
			saveTissuRequis(tissu);
		});

		Button cancelBtn = new Button("Annuler");
		cancelBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> rightContainer.getChildren().clear());

		Button deleteBtn = new Button("Supprimer");
		deleteBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> deleteTissuRequis(tissu));

		HBox hboxBtn = new HBox(validateBtn, cancelBtn, deleteBtn);
		hboxBtn.setSpacing(10);
		hboxBtn.setAlignment(Pos.CENTER);
		hboxBtn.setPadding(new Insets(20, 20, 20, 20));

		rightContainer.getChildren().addAll(titre, topGrid, hboxBtn, bottomRightVbox);

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
				Button editButton = new Button();
				editButton.setGraphic(GlyphIconUtil.editNormal());
				editButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
					editingVariant = true;
					DevInProgressService.notImplemented(mainApp);
				});

				Button deleteButton = new Button();
				deleteButton.setGraphic(GlyphIconUtil.suppressNormal());
				deleteButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> DevInProgressService.notImplemented(mainApp));
				HBox btns = new HBox(editButton, deleteButton);
				btns.setAlignment(Pos.CENTER_RIGHT);
				btns.setSpacing(10);

				bottomGrid.add(new Label(Utils.safeString(tv.getTypeTissu()) + " " + Utils.safeString(tv.getMatiere()) + " " + Utils.safeString(tv.getTissage())), 0, i * 2);
				bottomGrid.add(btns, 1, i * 2);

				if (i != tvList.size() - 1) {
					HBox hbox = new HBox(new Label("-------------   OU   --------------  "));
					hbox.setAlignment(Pos.CENTER);
					bottomGrid.add(hbox, 0, i * 2 + 1, 2, 1);
					//displayTissuRequis(tissu);
				}
			}
		}

		if (tissu != null && tissu.getId() != 0) {

			ChoiceBox<String> typeField = new ChoiceBox<String>();
			typeField.setItems(FXCollections.observableArrayList(
					typeTissuService.getAll().stream().map(tt -> tt.getValue()).collect(Collectors.toList())));
			typeField.setValue(variantSelected.getTypeTissuProperty() == null ? "" : variantSelected.getTypeTissu());

			ChoiceBox<String> matiereField = new ChoiceBox<String>();
			matiereField.setItems(FXCollections.observableArrayList(
					matiereService.getAll().stream().map(m -> m.getValue()).collect(Collectors.toList())));
			matiereField.setValue(variantSelected.getMatiereProperty() == null ? "" : variantSelected.getMatiere());

			ChoiceBox<String> tissageField = new ChoiceBox<String>();
			tissageField.setItems(FXCollections.observableArrayList(
					tissageService.getAll().stream().map(t -> t.getValue()).collect(Collectors.toList())));
			tissageField.setValue(variantSelected.getTissageProperty() == null ? "" : variantSelected.getTissage());

			Button addTvBtn = new Button();
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

		// DevInProgressService.notImplemented(mainApp);
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
		}
	}

	@FXML
	private void handleCancel() {

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
	public void setStageInitializer(StageInitializer initializer, FxDto... data) {
		this.initializer = initializer;

		if (data.length == 0 || data[0] == null){
			patron = mapper.map(new Patron("", "", "", "", "", null), PatronDto.class);
			setDisabledButton();

		} else if (data.length == 1 && data[0] instanceof PatronDto) {
			patron = (PatronDto) data[0];

			setDisabledButton();

			referenceField.setText(patron.getReferenceProperty() == null ? "" : patron.getReference());
			marqueField.setText(patron.getMarqueProperty() == null ? "" : patron.getMarque());
			modeleField.setText(patron.getModeleProperty() == null ? "" : patron.getModele());
			typeVetementField.setText(patron.getTypeVetementProperty() == null ? "" : patron.getTypeVetement());

			loadTissuRequisForPatron();
		}
	}
	/*
	txtInput.setEditable(false);
	txtInput.setMouseTransparent(true);
	txtInput.setFocusTraversable(false);
	*/
}
