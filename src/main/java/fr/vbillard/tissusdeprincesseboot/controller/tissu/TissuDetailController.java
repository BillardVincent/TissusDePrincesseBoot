package fr.vbillard.tissusdeprincesseboot.controller.tissu;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.jfoenix.controls.JFXButton;

import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.IController;
import fr.vbillard.tissusdeprincesseboot.controller.RootController;
import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuDto;
import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;
import fr.vbillard.tissusdeprincesseboot.model.Photo;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.enums.TypeTissuEnum;
import fr.vbillard.tissusdeprincesseboot.model.enums.UnitePoids;
import fr.vbillard.tissusdeprincesseboot.services.ImageService;
import fr.vbillard.tissusdeprincesseboot.services.MatiereService;
import fr.vbillard.tissusdeprincesseboot.services.TissageService;
import fr.vbillard.tissusdeprincesseboot.services.TissuService;
import fr.vbillard.tissusdeprincesseboot.utils.FxData;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.RowConstraints;

@Component
public class TissuDetailController implements IController {
	@FXML
	public Label decatiField;
	@FXML
	public Label descriptionField;
	@FXML
	public Label lieuDachatField;
	@FXML
	public Label poidsField;
	@FXML
	public Label unitePoidsField;
	@FXML
	public Label laizeField;
	@FXML
	public Label longueurField;
	@FXML
	public Label chuteField;
	@FXML
	public Label typeField;
	@FXML
	public Label matiereField;
	@FXML
	public Label tissageField;
	@FXML
	public Label referenceField;
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

	@FXML
	private JFXButton addToButton;
	@FXML
	private JFXButton editButton;

	StageInitializer initializer;

	TissuDto tissu;
	private boolean okClicked = false;

	ModelMapper mapper;
	MatiereService matiereService;
	TissageService tissageService;
	TissuService tissuService;
	RootController rootController;
	ImageService imageService;

	public TissuDetailController(ImageService imageService, RootController rootController, ModelMapper mapper,
			TissuService tissuService, MatiereService matiereService, TissageService tissageService) {
		this.mapper = mapper;
		this.tissuService = tissuService;
		this.matiereService = matiereService;
		this.tissageService = tissageService;
		this.rootController = rootController;
		this.imageService = imageService;
	}

	@Override
	public void setStageInitializer(StageInitializer initializer, FxData data) {
		this.initializer = initializer;
		if (data == null || data.getTissu() == null) {
			throw new IllegalData();
		}
		tissu = data.getTissu();
		if (tissu == null || tissu.getChuteProperty() == null) {
			tissu = mapper.map(new Tissu(0, "", 0, 0, "", null, TypeTissuEnum.NON_RENSEIGNE, 0,
					UnitePoids.NON_RENSEIGNE, false, "", null, false), TissuDto.class);
		}

		longueurField.setText(tissu.getLongueurProperty() == null ? "0" : Integer.toString(tissu.getLongueur()));
		laizeField.setText(tissu.getLaizeProperty() == null ? "0" : Integer.toString(tissu.getLaize()));
		poidsField.setText(tissu.getPoidseProperty() == null ? "0" : Integer.toString(tissu.getPoids()));
		referenceField.setText(tissu.getReferenceProperty() == null ? "" : tissu.getReference());
		descriptionField.setText(tissu.getDescriptionProperty() == null ? "" : tissu.getDescription());
		decatiField.setText(tissu.getDecatiProperty() != null && tissu.isDecati() ? "Décati" : "Non décati");
		lieuDachatField.setText(tissu.getLieuAchatProperty() == null ? "" : tissu.getLieuAchat());
		chuteField.setText(tissu.getChuteProperty() != null && tissu.isChute() ? "Chute" : "Coupon");
		unitePoidsField.setText(
				tissu.getUnitePoidsProperty() == null ? UnitePoids.NON_RENSEIGNE.label : tissu.getUnitePoids());
		typeField.setText(
				tissu.getTypeTissuProperty() == null ? TypeTissuEnum.NON_RENSEIGNE.label : tissu.getTypeTissu());
		matiereField.setText(tissu.getMatiereProperty() == null ? "" : tissu.getMatiere());
		tissageField.setText(tissu.getTissageProperty() == null ? "" : tissu.getTissage());
		Optional<Photo> pictures = imageService.getImage(mapper.map(tissu, Tissu.class));
		imagePane.setImage(imageService.imageOrDefault(pictures));

		addToButton.setVisible(rootController.hasTissuRequisSelected());
		editButton.setVisible(!rootController.hasTissuRequisSelected());

	}

	public boolean isOkClicked() {
		return okClicked;
	}

	public void edit() {
		rootController.displayTissusEdit(tissu);

	}

	public void addTo() {
		rootController.addToSelected(tissu);

	}
}
