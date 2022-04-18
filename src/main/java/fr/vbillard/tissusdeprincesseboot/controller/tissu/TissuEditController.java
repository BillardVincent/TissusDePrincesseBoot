package fr.vbillard.tissusdeprincesseboot.controller.tissu;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import fr.vbillard.tissusdeprincesseboot.utils.FxUtils;
import fr.vbillard.tissusdeprincesseboot.utils.PathEnum;

import org.imgscalr.Scalr;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.IController;
import fr.vbillard.tissusdeprincesseboot.controller.RootController;
import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuDto;
import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;
import fr.vbillard.tissusdeprincesseboot.exception.NotFoundException;
import fr.vbillard.tissusdeprincesseboot.fxCustomElements.GlyphIconUtil;
import fr.vbillard.tissusdeprincesseboot.fxCustomElements.IntegerSpinner;
import fr.vbillard.tissusdeprincesseboot.model.AbstractSimpleValueEntity;
import fr.vbillard.tissusdeprincesseboot.model.Photo;
import fr.vbillard.tissusdeprincesseboot.model.Preference;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.enums.ImageFormat;
import fr.vbillard.tissusdeprincesseboot.model.enums.UnitePoids;
import fr.vbillard.tissusdeprincesseboot.services.ImageService;
import fr.vbillard.tissusdeprincesseboot.services.MatiereService;
import fr.vbillard.tissusdeprincesseboot.services.PreferenceService;
import fr.vbillard.tissusdeprincesseboot.services.TissageService;
import fr.vbillard.tissusdeprincesseboot.services.TissuService;
import fr.vbillard.tissusdeprincesseboot.services.TypeTissuService;
import fr.vbillard.tissusdeprincesseboot.utils.FxData;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
//import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.RowConstraints;

@Component
public class TissuEditController implements IController {
    @FXML
    public JFXToggleButton decatiField;
    @FXML
    public JFXTextField descriptionField;
    @FXML
    public JFXTextField lieuDachatField;
    @FXML
    public JFXTextField poidsField;
    @FXML
    public JFXComboBox<String> unitePoidsField;
    @FXML
    public JFXTextField laizeField;
    @FXML
    public JFXTextField longueurField;
    @FXML
    public JFXToggleButton chuteField;
    @FXML
    public JFXButton addTypeButton;
    @FXML
    public JFXComboBox<String> typeField;
    @FXML
    public JFXComboBox<String> matiereField;
    @FXML
    public JFXButton addMatiereButton;
    @FXML
    public JFXComboBox<String> tissageField;
    @FXML
    public JFXButton addTissageButton;
    @FXML
    public JFXTextField referenceField;
    @FXML
    public JFXButton generateReferenceButton;
    @FXML
    public Label ancienneValeurLabel;
    @FXML
    public Label ancienneValeurInfo;
    @FXML
    public Label consommeLabel;
    @FXML
    public Label consommeIndo;
	@FXML
	public ImageView imagePane;

    public RowConstraints ancienneValeurRow;
    public RowConstraints consommeRow;

    private int longueur;
    private int laize;
    private int poids;

    private RootController root;
	private StageInitializer initializer;

    private TissuDto tissu;
	private boolean edit;
	private boolean okClicked = false;

	private ModelMapper mapper;
	private TypeTissuService typeTissuService;
	private MatiereService matiereService;
	private TissageService tissageService;
	private TissuService tissuService;
	private ImageService imageService;
 	private PreferenceService preferenceService;

    public TissuEditController(ImageService imageService, PreferenceService preferenceService, ModelMapper mapper, TissuService tissuService, TypeTissuService typeTissuService, MatiereService matiereService, TissageService tissageService, RootController root) {
        this.mapper = mapper;
		this.imageService = imageService;
        this.tissuService = tissuService;
        this.typeTissuService = typeTissuService;
        this.matiereService = matiereService;
        this.tissageService = tissageService;
		this.preferenceService = preferenceService;
		this.root = root;
    }

    @Override
    public void setStageInitializer(StageInitializer initializer, FxData data) {
        this.initializer = initializer;
        
        if (data==null || data.getTissu()==null){
        	throw new IllegalData();
		}
            tissu = data.getTissu();
            if (tissu == null || tissu.getChuteProperty() == null) {
                tissu = mapper.map(new Tissu(0, "", 0, 0, "", null, typeTissuService.getAll().get(0), 0,
                        UnitePoids.NON_RENSEIGNE, false, "", null, false), TissuDto.class);
            }

            longueurField.setText(FxUtils.safePropertyToString(tissu.getLongueurProperty()));
            laizeField.setText(FxUtils.safePropertyToString(tissu.getLaizeProperty()));
            poidsField.setText(FxUtils.safePropertyToString(tissu.getPoidseProperty()));
            referenceField.setText(FxUtils.safePropertyToString(tissu.getReferenceProperty()));
            descriptionField.setText(FxUtils.safePropertyToString(tissu.getDescriptionProperty()));
            decatiField.setSelected(tissu.getDecatiProperty() != null && tissu.isDecati());
            lieuDachatField.setText(FxUtils.safePropertyToString(tissu.getLieuAchatProperty()));
            chuteField.setSelected(tissu.getChuteProperty() != null && tissu.isChute());

            unitePoidsField.setItems(FXCollections.observableArrayList(UnitePoids.labels()));
            unitePoidsField.setValue(
                    tissu.getUnitePoidsProperty() == null ? UnitePoids.NON_RENSEIGNE.label : tissu.getUnitePoids());

            typeField.setItems(FXCollections.observableArrayList(
                    typeTissuService.getAll().stream().map(AbstractSimpleValueEntity::getValue).collect(Collectors.toList())));
            typeField.setValue(FxUtils.safePropertyToString(tissu.getTypeProperty()));

            matiereField.setItems(FXCollections.observableArrayList(
                    matiereService.getAll().stream().map(AbstractSimpleValueEntity::getValue).collect(Collectors.toList())));
            matiereField.setValue(FxUtils.safePropertyToString(tissu.getMatiereProperty()));

            tissageField.setItems(FXCollections.observableArrayList(
                    tissageService.getAll().stream().map(AbstractSimpleValueEntity::getValue).collect(Collectors.toList())));
            tissageField.setValue(FxUtils.safePropertyToString(tissu.getTissageProperty()));

    		Optional<Photo> pictures = imageService.getImage(mapper.map(tissu, Tissu.class));
    		imagePane.setImage(imageService.imageOrDefault(pictures));
		}
    

    @FXML
    private void initialize() {

        addTissageButton.setGraphic(GlyphIconUtil.plusCircleTiny());
        addTypeButton.setGraphic(GlyphIconUtil.plusCircleTiny());
        addMatiereButton.setGraphic(GlyphIconUtil.plusCircleTiny());
        FontAwesomeIconView magicIcon = new FontAwesomeIconView(FontAwesomeIcon.MAGIC);
        generateReferenceButton.setGraphic(magicIcon);
        generateReferenceButton.setTooltip(new Tooltip("Générer une référence automatiquement"));

        longueurField.setTextFormatter(IntegerSpinner.getFormatter());
        laizeField.setTextFormatter(IntegerSpinner.getFormatter());
        poidsField.setTextFormatter(IntegerSpinner.getFormatter());

    }

    public boolean isOkClicked() {
		return okClicked;
	}

	@FXML
	private void handleOk() {
		if (isInputValid()) {
			if (tissu.getChuteProperty() == null) {
				tissu = mapper.map(new Tissu(0, "", 0, 0, "", null, typeTissuService.getAll().get(0), 0,
						UnitePoids.NON_RENSEIGNE, false, "", null, false), TissuDto.class);
			}
			tissu.setReference(referenceField.getText());
			tissu.setLongueur(longueur);
			tissu.setLaize(Integer.valueOf(laizeField.getText()));
			tissu.setDescription(descriptionField.getText());
			tissu.setMatiere(matiereField.getValue());
			tissu.setType(typeField.getValue());
			tissu.setPoids(Integer.valueOf(poidsField.getText()));
			tissu.setUnitePoids(unitePoidsField.getValue());
			tissu.setDecati(decatiField.isSelected());
			tissu.setLieuAchat(lieuDachatField.getText());
			tissu.setChute(chuteField.isSelected());
			tissu.setTissage(tissageField.getValue());

			tissu = tissuService.saveOrUpdate(tissu);
			okClicked = true;
		}
	}

	@FXML
	private void handleCancel() {
	}

	@FXML
	private void handleAddMatiere() {
		root.displayMatiereEdit();
	}

	@FXML
	private void handleAddTissage() {
		root.displayTissageEdit();
	}

	@FXML
	private void handleAddTypeTissu() {
		root.displayTypeEdit();
	}

	private boolean isInputValid() {
		String errorMessage = "";

		if (referenceField.getText() == null || referenceField.getText().length() == 0) {
			errorMessage += "Référence non renseignée.\n";
		}
		if (matiereField.getValue() == null) {
			errorMessage += "Matière non renseignée.\n";
		}
		if (unitePoidsField.getValue() == null) {
			errorMessage += "Unité de poids non renseignée.\n";
		}
		if (poidsField.getText() == null) {
			errorMessage += "Poids non renseigné.\n";
		}
		if (tissageField.getValue() == null) {
			errorMessage += "Poids non renseigné.\n";
		}

		if (errorMessage.length() == 0) {
			return true;
		} else {
			// Show the error message.
			Alert alert = new Alert(AlertType.ERROR);
			//alert.initOwner(dialogStage);
			alert.setTitle("Invalid Fields");
			alert.setHeaderText("Please correct invalid fields");
			alert.setContentText(errorMessage);

			alert.showAndWait();

			return false;
		}
	}

	@FXML
	private void handleGenerateReference() {
		StringBuilder sb = new StringBuilder();
		sb.append(typeField.getValue().trim().isEmpty() ? "X" : typeField.getValue().toUpperCase().charAt(0))
				.append(matiereField.getValue().trim().isEmpty() ? "X"
						: matiereField.getValue().toUpperCase().charAt(0))
				.append(tissageField.getValue().trim().isEmpty() ? "X"
						: tissageField.getValue().toUpperCase().charAt(0))
				.append("-");
		if (Boolean.parseBoolean(chuteField.getText()))
			sb.append("cp-");
		boolean ref = true;
		int refNb = 0;
		while (ref) {
			refNb++;
			ref = tissuService.existByReference(sb.toString() + refNb);
		}
		referenceField.setText(sb.append(refNb).toString());
	}

	private void nextTissu(TissuDto tissu, Map<TissuDto, Integer> mapTissu) {
		mapTissu.remove(tissu);
	}

	public void setButton() {
		int height = edit ? 30 : 0;
		ancienneValeurRow.setMaxHeight(height);
		ancienneValeurRow.setMinHeight(height);
		ancienneValeurRow.setPrefHeight(height);
		consommeRow.setMaxHeight(height);
		consommeRow.setMinHeight(height);
		consommeRow.setPrefHeight(height);
		ancienneValeurLabel.setVisible(edit);
		consommeLabel.setVisible(edit);
		ancienneValeurInfo.setVisible(edit);
		consommeIndo.setVisible(edit);

		laizeField.setDisable(edit);
		poidsField.setDisable(edit);
		referenceField.setDisable(edit);
		descriptionField.setDisable(edit);
		decatiField.setDisable(edit);
		lieuDachatField.setDisable(edit);
		chuteField.setDisable(edit);
	}

	@FXML
	private void addPicture() {
		tissuService.saveOrUpdate(tissu);
		Preference pref = preferenceService.getPreferences();
		File file = initializer.directoryChooser(pref);
		
		if (file != null)
			try {

				String name = file.getName();
				String extension = name.substring(name.lastIndexOf(".") + 1);
				BufferedImage bufferedImage = ImageIO.read(file);
				setImage(name, extension, bufferedImage);

				pref.setPictureLastUploadPath(file.getAbsolutePath());
				preferenceService.savePreferences(pref);
			} catch (IOException e) {
				e.printStackTrace();
			}
		 
	}
	
	@FXML
	private void addPictureWeb() {
		tissuService.saveOrUpdate(tissu);
		Preference pref = preferenceService.getPreferences();
		URL url = initializer.displayModale(PathEnum.WEB_URL, null, "URL de l'image").getUrl();
		
		if (url != null)
			try {

				String name = url.getHost();
				String path = url.getPath();
				String extension = path.substring(path.lastIndexOf('.') + 1);
				BufferedImage bufferedImage = ImageIO.read(url);
				setImage(name, extension, bufferedImage);

			} catch (IOException e) {
				e.printStackTrace();
				throw new NotFoundException(url.toString());
			}
	}

	private void setImage(String name, String extension, BufferedImage bufferedImage) throws IOException {
		bufferedImage = Scalr.resize(bufferedImage, 900);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(bufferedImage, extension, baos);
		byte[] data = baos.toByteArray();
		Photo image = new Photo();
		image.setData(data);
		image.setNom(name);
		image.setFormat(ImageFormat.valueOf(extension.toUpperCase()));
		image.setTissu(mapper.map(tissu, Tissu.class));
		imageService.saveOrUpdate(image);
		baos.close();
		imagePane.setImage(new Image(new ByteArrayInputStream(image.getData())));
	}

	@FXML
	private void pictureExpend(){

	}
	
	
}
