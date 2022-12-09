package fr.vbillard.tissusdeprincesseboot.controller.patron;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.RootController;
import fr.vbillard.tissusdeprincesseboot.controller.picture_helper.PatronPictureHelper;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IController;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureRequiseDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureVariantDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.PatronDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuRequisDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuVariantDto;
import fr.vbillard.tissusdeprincesseboot.fx_custom_element.GlyphIconUtil;
import fr.vbillard.tissusdeprincesseboot.model.Patron;
import fr.vbillard.tissusdeprincesseboot.model.enums.GammePoids;
import fr.vbillard.tissusdeprincesseboot.model.enums.SupportTypeEnum;
import fr.vbillard.tissusdeprincesseboot.model.enums.TypeTissuEnum;
import fr.vbillard.tissusdeprincesseboot.service.FournitureRequiseService;
import fr.vbillard.tissusdeprincesseboot.service.FournitureVariantService;
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
	private GridPane fournituresPatronListGrid;
	@FXML
	private JFXButton generateReferenceButton;
	@FXML
	private JFXTextField referenceField;
	@FXML
	public ImageView imagePane;
	@FXML
	public JFXComboBox<String> typeSupportCbBox;

	private StageInitializer initializer;
	private RootController root;
	private PatronDto patron;
	// private TissuRequisDto tissuRequisDto;
	private final TissuRequisService tissuRequisService;
	private final TissuVariantService tissuVariantService;
	private final MatiereService matiereService;
	private final TissageService tissageService;
	private PatronPictureHelper pictureUtils;
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
	private ObservableList<FournitureVariantDto> fvList;
	private VBox bottomRightVbox;
	private FournitureVariantService fournitureVariantService;
	private FournitureRequiseService fournitureRequiseService;

	public PatronEditController(RootController root, PatronPictureHelper pictureUtils,
			TissuRequisService tissuRequisService, TissuVariantService tissuVariantService,
			MatiereService matiereService, TissageService tissageService, ModelMapper mapper,
			PatronService patronService, FournitureVariantService fournitureVariantService) {

		this.tissuRequisService = tissuRequisService;
		this.tissuVariantService = tissuVariantService;
		this.matiereService = matiereService;
		this.tissageService = tissageService;
		this.mapper = mapper;
		this.patronService = patronService;
		this.pictureUtils = pictureUtils;
		this.root = root;
		this.fournitureVariantService = fournitureVariantService;
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

		JFXTextField longueurSpinner = FxUtils.buildSpinner(tissu.getLongueurProperty());
		topGrid.add(longueurSpinner, 1, 0);

		JFXTextField laizeSpinner = FxUtils.buildSpinner(tissu.getLaizeProperty());
		topGrid.add(laizeSpinner, 1, 1);

		JFXComboBox<String> gammePoidsChBx = FxUtils.buildComboBox(GammePoids.labels(),tissu.getGammePoidsProperty(), GammePoids.NON_RENSEIGNE.label);
		topGrid.add(gammePoidsChBx, 1, 2);

		JFXButton validateBtn = new JFXButton("Valider");
		validateBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
			tissu.setGammePoids(gammePoidsChBx.getValue());
			tissu.setLaize(Integer.parseInt(laizeSpinner.getText()));
			tissu.setLongueur(Integer.parseInt(longueurSpinner.getText()));
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

		tvList = FXCollections.observableArrayList(tissuVariantService.getVariantByTissuRequis(tissu));

		//TODO !!!!!!!
		// tvList = FXCollections.observableArrayList(fournitureVariantService.getVariantByFournitureRequis
		// (tissu));

		loadBottomRightVbox(tissu);

	}

	/**
	 * 2e partie de génération du panneau Tissu
	 */
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
				buildTissuVariantDisplay(tissu, bottomGrid, i, tv);
			}
		}

		if (tissu != null && tissu.getId() != 0) {

			JFXComboBox<String> typeField = FxUtils.buildComboBox(TypeTissuEnum.labels(), variantSelected.getTypeTissuProperty());

			JFXComboBox<String> matiereField = FxUtils.buildComboBox(matiereService.getAllMatieresValues(), variantSelected.getMatiereProperty());

			JFXComboBox<String> tissageField = FxUtils.buildComboBox(tissageService.getAllValues(), variantSelected.getTissageProperty());

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

	private void buildTissuVariantDisplay(TissuRequisDto tissu, GridPane bottomGrid, int i, TissuVariantDto tv) {
		JFXButton editButton = new JFXButton();
		editButton.setGraphic(GlyphIconUtil.editNormal());
		editButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
			editingVariant = true;
			editVariant(tv);
		});

		JFXButton deleteButton = new JFXButton();
		deleteButton.setGraphic(GlyphIconUtil.suppressNormal());
		deleteButton.addEventHandler(MouseEvent.MOUSE_CLICKED,
				e -> {
					deleteVariant(tissu, tv);
				});
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

	private void deleteVariant(TissuRequisDto tissu, TissuVariantDto tv) {
		tissuVariantService.delete(tv.getId());
		TissuRequisDto tissuReloaded = mapper.map(tissuRequisService.findTissuRequis(tissu.getId()), TissuRequisDto.class);
		displayTissuRequis(tissuReloaded);		
	}

	private void editVariant(TissuVariantDto tv) {
		DevInProgressService.notImplemented();
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
		tissuEtFournitureContainer.getChildren().clear();
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
			patron.setTypeSupport(typeSupportCbBox.getValue());

			patron = patronService.create(patron);
			setDisabledButton();

			okClicked = true;

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
		displayFournitureRequise(new FournitureRequiseDto());
		DevInProgressService.notImplemented();

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
			patron = mapper.map(new Patron("", "", "", "", "", SupportTypeEnum.NON_RENSEIGNE, null, null), PatronDto.class);
			setDisabledButton();

		} else {
			patron = data.getPatron();

			setDisabledButton();

			referenceField.setText(FxUtils.safePropertyToString(patron.getReferenceProperty()));
			marqueField.setText(FxUtils.safePropertyToString(patron.getMarqueProperty()));
			modeleField.setText(FxUtils.safePropertyToString(patron.getModeleProperty()));
			typeVetementField.setText(FxUtils.safePropertyToString(patron.getTypeVetementProperty()));
			FxUtils.buildComboBox(SupportTypeEnum.labels(), patron.getTypeSupportProperty(), SupportTypeEnum.NON_RENSEIGNE.label,typeSupportCbBox);
			pictureUtils.setPane(imagePane, patron);

			loadTissuRequisForPatron();
		}
	}

	@FXML
	private void addPicture() {
		handleSavePatron();

		pictureUtils.addPictureLocal(patron);

	}

	@FXML
	private void addPictureWeb() {
		handleSavePatron();

		pictureUtils.addPictureWeb(patron);

	}

	@FXML
	private void pictureExpend() {
		DevInProgressService.notImplemented();

	}

	/*
	 * txtInput.setEditable(false); txtInput.setMouseTransparent(true);
	 * txtInput.setFocusTraversable(false);
	 */

	//--------------------------------------------------------------------------------


	/**
	 * Détails d'un tissu requis, pour création ou édition
	 *
	 * @param tissu
	 */
	private void displayFournitureRequise(FournitureRequiseDto tissu) {
		variantSelected = new TissuVariantDto();
		/*
		longueur = tissu.getLongueur();
		laize = tissu.getLaize();
		 */
		tissuEtFournitureContainer.getChildren().clear();
		fvList = FXCollections.observableArrayList(new ArrayList<FournitureVariantDto>());
		bottomRightVbox = new VBox();

		Label titre = new Label("Tissus recommandés : ");

		GridPane topGrid = new GridPane();
		topGrid.setVgap(10);
		topGrid.setHgap(20);

		topGrid.setPadding(new Insets(10, 0, 10, 0));
		topGrid.add(new Label("Longeur"), 0, 0);
		topGrid.add(new Label("Laize"), 0, 1);
		topGrid.add(new Label("Gamme de poids"), 0, 2);
/*
		JFXTextField longueurSpinner = FxUtils.buildSpinner(tissu.getLongueurProperty());
		topGrid.add(longueurSpinner, 1, 0);

		JFXTextField laizeSpinner = FxUtils.buildSpinner(tissu.getLaizeProperty());
		topGrid.add(laizeSpinner, 1, 1);

		JFXComboBox<String> gammePoidsChBx = FxUtils.buildComboBox(GammePoids.labels(),tissu.getGammePoidsProperty(), GammePoids.NON_RENSEIGNE.label);
		topGrid.add(gammePoidsChBx, 1, 2);

		JFXButton validateBtn = new JFXButton("Valider");
		validateBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
			tissu.setGammePoids(gammePoidsChBx.getValue());
			tissu.setLaize(Integer.parseInt(laizeSpinner.getText()));
			tissu.setLongueur(Integer.parseInt(longueurSpinner.getText()));
			tissu.setGammePoids(gammePoidsChBx.getValue());
			saveTissuRequis(tissu);
		});
 */
		JFXButton cancelBtn = new JFXButton("Annuler");
		cancelBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> tissuEtFournitureContainer.getChildren().clear());

		JFXButton deleteBtn = new JFXButton("Supprimer");
		deleteBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> deleteFournitureRequise(tissu));

		/*
		HBox hboxBtn = new HBox(validateBtn, cancelBtn, deleteBtn);
		hboxBtn.setSpacing(10);
		hboxBtn.setAlignment(Pos.CENTER);
		hboxBtn.setPadding(new Insets(20, 20, 20, 20));
		 */
		//tissuEtFournitureContainer.getChildren().addAll(titre, topGrid, hboxBtn, bottomRightVbox);

		//tvList = FXCollections.observableArrayList(tissuVariantService.getVariantByTissuRequis(tissu));

		//TODO !!!!!!!
		// tvList = FXCollections.observableArrayList(fournitureVariantService.getVariantByFournitureRequis
		// (tissu));

		loadBottomRightVbox(tissu);

	}

	/**
	 * 2e partie de génération du panneau Tissu
	 */
	private void loadBottomRightVbox(FournitureRequiseDto tissu) {
		bottomRightVbox.getChildren().clear();

		bottomRightVbox.getChildren().addAll(new Separator(Orientation.HORIZONTAL), new Label("Possibilités :"));

		if (fvList != null && fvList.size() > 0) {
			GridPane bottomGrid = new GridPane();
			bottomGrid.setPadding(new Insets(5, 0, 5, 0));
			bottomRightVbox.getChildren().add(bottomGrid);
			bottomGrid.getColumnConstraints().addAll(new ColumnConstraints(300), new ColumnConstraints(100));

			for (int i = 0; i < fvList.size(); i++) {
				FournitureVariantDto tv = fvList.get(i);
				buildFournitureVariantDisplay(tissu, bottomGrid, i, tv);
			}
		}

		if (tissu != null && tissu.getId() != 0) {

			JFXComboBox<String> typeField = FxUtils.buildComboBox(TypeTissuEnum.labels(), variantSelected.getTypeTissuProperty());

			JFXComboBox<String> matiereField = FxUtils.buildComboBox(matiereService.getAllMatieresValues(), variantSelected.getMatiereProperty());

			JFXComboBox<String> tissageField = FxUtils.buildComboBox(tissageService.getAllValues(), variantSelected.getTissageProperty());

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
				displayFournitureRequise(tissu);
			});
			HBox hboxTissuVariant = new HBox(typeField, matiereField, tissageField, addTvBtn);
			hboxTissuVariant.setSpacing(10);
			hboxTissuVariant.setAlignment(Pos.CENTER);
			hboxTissuVariant.setPadding(new Insets(20, 20, 20, 20));
			bottomRightVbox.getChildren().addAll(new Separator(Orientation.HORIZONTAL), hboxTissuVariant);
		}
	}

	private void buildFournitureVariantDisplay(FournitureRequiseDto tissu, GridPane bottomGrid, int i, FournitureVariantDto tv) {
		JFXButton editButton = new JFXButton();
		editButton.setGraphic(GlyphIconUtil.editNormal());
		editButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
			editingVariant = true;
			editVariant(tv);
		});

		JFXButton deleteButton = new JFXButton();
		deleteButton.setGraphic(GlyphIconUtil.suppressNormal());
		deleteButton.addEventHandler(MouseEvent.MOUSE_CLICKED,
				e -> {
					deleteVariant(tissu, tv);
				});
		HBox btns = new HBox(editButton, deleteButton);
		btns.setAlignment(Pos.CENTER_RIGHT);
		btns.setSpacing(10);

		bottomGrid.add(new Label(Utils.safeString(tv.getTypeName())), 0, i * 2);
		bottomGrid.add(btns, 1, i * 2);

		if (i != tvList.size() - 1) {
			HBox hbox = new HBox(new Label("-------------   OU   --------------  "));
			hbox.setAlignment(Pos.CENTER);
			bottomGrid.add(hbox, 0, i * 2 + 1, 2, 1);
			// displayTissuRequis(tissu);
		}
	}

	private void deleteVariant(FournitureRequiseDto tissu, FournitureVariantDto tv) {
		tissuVariantService.delete(tv.getId());
		FournitureRequiseDto tissuReloaded = mapper.map(fournitureRequiseService.findFournitureRequise(tissu.getId()),
				FournitureRequiseDto.class);
		displayFournitureRequise(tissuReloaded);
	}

	private void editVariant(FournitureVariantDto tv) {
		DevInProgressService.notImplemented();
	}

	public void saveFournitureRequise(FournitureRequiseDto tissu) {
		boolean edit = tissu.getId() != 0;
		FournitureRequiseDto tissuReturned = fournitureRequiseService.createOrUpdate(tissu, patron);
		if (!edit) {
			patron.getFournituresRequises().add(tissu);
		}
		loadFournitureRequiseForPatron();
		displayFournitureRequise(tissuReturned);

	}

	private void deleteFournitureRequise(FournitureRequiseDto dto) {
		fournitureRequiseService.delete(dto);
		tissuEtFournitureContainer.getChildren().clear();
		loadFournitureRequiseForPatron();
	}

	/**
	 * Charge les tissusRequis, en fonction du patron sélectionné. tableau sous le
	 * patron : tissusRequis.toString() - boutons
	 */
	private void loadFournitureRequiseForPatron() {
		fournituresPatronListGrid.getChildren().clear();
		patron.setFournituresRequises(fournitureRequiseService.getAllFournitureRequiseDtoByPatron(patron.getId()));

		if (patron.getFournituresRequisesProperty() != null && patron.getFournituresRequises() != null) {

			for (int i = 0; i < patron.getFournituresRequises().size(); i++) {

				FournitureRequiseDto fourniture = patron.getFournituresRequises().get(i);

				JFXButton editButton = new JFXButton();
				editButton.setGraphic(GlyphIconUtil.editNormal());
				editButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> displayFournitureRequise(fourniture));

				JFXButton deleteButton = new JFXButton();
				deleteButton.setGraphic(GlyphIconUtil.suppressNormal());
				deleteButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> deleteFournitureRequise(fourniture));

				HBox hbox = new HBox(editButton, deleteButton);
				hbox.setSpacing(10);
				hbox.setAlignment(Pos.CENTER_LEFT);
				fournituresPatronListGrid.add(new Label(fourniture.toString()), 0, i);
				fournituresPatronListGrid.add(hbox, 1, i);
			}
		}
	}
}
