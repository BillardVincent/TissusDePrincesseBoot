package fr.vbillard.tissusdeprincesseboot.controller.patron.edit;

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
import fr.vbillard.tissusdeprincesseboot.dtos_fx.PatronDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuRequisDto;
import fr.vbillard.tissusdeprincesseboot.fx_custom_element.GlyphIconUtil;
import fr.vbillard.tissusdeprincesseboot.model.Patron;
import fr.vbillard.tissusdeprincesseboot.model.enums.SupportTypeEnum;
import fr.vbillard.tissusdeprincesseboot.service.PatronService;
import fr.vbillard.tissusdeprincesseboot.utils.DevInProgressService;
import fr.vbillard.tissusdeprincesseboot.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.utils.FxUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

@Component
public class PatronEditController implements IController {
	
	private final static String X = "X";

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
	@FXML
	public JFXButton archiverBtn;

	private StageInitializer initializer;
	private RootController root;
	private PatronDto patron;

	private PatronPictureHelper pictureUtils;
	private final ModelMapper mapper;
	private boolean okClicked = false;
	private StageInitializer mainApp;
	// private ObservableList<TissuRequisDto> listTissuRequis;
	private boolean unregistredPatron;
	private final PatronService patronService;

	// private HBox tissuRequisDisplayHbox;

	private VBox bottomRightVbox;

	private TissuPatronEditHelper tissuPatronEditHelper;
	private FourniturePatronEditHelper fourniturePatronEditHelper;

	public PatronEditController(RootController root, PatronPictureHelper pictureUtils,
			 ModelMapper mapper,
			PatronService patronService, FourniturePatronEditHelper fourniturePatronEditHelper, TissuPatronEditHelper tissuPatronEditHelper) {

		this.mapper = mapper;
		this.patronService = patronService;
		this.pictureUtils = pictureUtils;
		this.root = root;
		this.fourniturePatronEditHelper = fourniturePatronEditHelper;
		this.tissuPatronEditHelper = tissuPatronEditHelper;
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
		sb.append(marqueField.getText().trim().isEmpty() ? X : marqueField.getText().toUpperCase().charAt(0))
				.append(modeleField.getText().trim().isEmpty() ? X : modeleField.getText().toUpperCase().charAt(0))
				.append(typeVetementField.getText().trim().isEmpty() ? X
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
		tissuPatronEditHelper.displayRequis(new TissuRequisDto());
	}

	@FXML
	private void handleFournitureListedit() {
		fourniturePatronEditHelper.displayRequis(new FournitureRequiseDto());
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
			patron = mapper.map(new Patron("", "", "", "", "", false, SupportTypeEnum.NON_RENSEIGNE, null, null),
					PatronDto.class);
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

			tissuPatronEditHelper.setContainer(tissuEtFournitureContainer, tissusPatronListGrid, patron, initializer);
			fourniturePatronEditHelper.setContainer(tissuEtFournitureContainer, fournituresPatronListGrid, patron, initializer);

			tissuPatronEditHelper.loadRequisForPatron();
			fourniturePatronEditHelper.loadRequisForPatron();
			setBoutonArchiver();
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
	private void addPictureFromClipboard(){
			handleSavePatron();
			pictureUtils.addPictureClipBoard(patron);
	
	}

	@FXML
	private void pictureExpend() {
		DevInProgressService.notImplemented();

	}

	@FXML
	public void archiver(){
		patron.setArchived(!patron.isArchived());
		patron = patronService.saveOrUpdate(patron);
		setBoutonArchiver();
	}

	private void setBoutonArchiver() {
		archiverBtn.setText(patron.isArchived() ? "Désarchiver" :"Archiver");
	}

	/*
	 * txtInput.setEditable(false); txtInput.setMouseTransparent(true);
	 * txtInput.setFocusTraversable(false);
	 */
}
