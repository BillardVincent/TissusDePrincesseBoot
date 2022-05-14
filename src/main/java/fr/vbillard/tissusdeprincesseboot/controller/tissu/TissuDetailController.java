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
import fr.vbillard.tissusdeprincesseboot.services.TissuUsedService;
import fr.vbillard.tissusdeprincesseboot.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.utils.ShowAlert;
import fr.vbillard.tissusdeprincesseboot.utils.modelToString.EntityToString;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
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
	public Label consommeInfo;
	@FXML
	public ImageView imagePane;

	public RowConstraints ancienneValeurRow;
	public RowConstraints consommeRow;

	@FXML
	private JFXButton addToButton;
	@FXML
	private JFXButton editButton;
	@FXML
	private JFXButton genererChuteButton;
	@FXML
	private JFXButton supprimerButton;

	private Optional<Photo> pictures;

	private StageInitializer initializer;

	private TissuDto tissu;
	private boolean okClicked = false;

	private ModelMapper mapper;
	private TissuService tissuService;
	private RootController rootController;
	private ImageService imageService;
	private TissuUsedService tissuUsedService;

	public TissuDetailController(ImageService imageService, RootController rootController, ModelMapper mapper,
			TissuService tissuService, TissuUsedService tissuUsedService) {
		this.mapper = mapper;
		this.tissuService = tissuService;
		this.rootController = rootController;
		this.imageService = imageService;
		this.tissuUsedService = tissuUsedService;
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

		longueurField
				.setText(tissu.getLongueurRestanteProperty() == null ? "0" : Integer.toString(tissu.getLongueur()));
		ancienneValeurInfo.setText(tissu.getLongueurProperty() == null ? "0" : Integer.toString(tissu.getLongueur()));
		consommeInfo.setText(Integer.toString(tissuService.getLongueurUtilisée(tissu.getId())));

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
		pictures = imageService.getImage(mapper.map(tissu, Tissu.class));
		imagePane.setImage(imageService.imageOrDefault(pictures));

		addToButton.setVisible(rootController.hasTissuRequisSelected());
		editButton.setVisible(!rootController.hasTissuRequisSelected());
		genererChuteButton.setVisible(!rootController.hasTissuRequisSelected());

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

	public void createChuteFromThis() {
		tissu.setId(0);
		tissu.setReference(tissu.getReference() + "-chute");
		tissu.setChute(true);
		tissu = tissuService.saveOrUpdate(tissu);
		if (pictures.isPresent()) {
			Photo photo = pictures.get();
			photo.setTissu(mapper.map(tissu, Tissu.class));
			photo.setId(0);
			imageService.saveOrUpdate(photo);
		}
		rootController.displayTissusEdit(tissu);

	}

	public void delete() {
		Optional<ButtonType> result = ShowAlert.suppression(initializer.getPrimaryStage(), EntityToString.TISSU,
				tissu.toString());
		if (result.isPresent() && result.get().equals(ButtonType.OK)) {
			if (tissuUsedService.existsByTissuId(tissu.getId())) {
				ShowAlert.suppressionImpossible(initializer.getPrimaryStage(), EntityToString.TISSU, tissu.toString());
			} else {
				if (pictures.isPresent()) {
					imageService.delete(pictures.get());
				}
				tissuService.delete(tissu);
			}
			rootController.displayTissus();
		}
	}
}
