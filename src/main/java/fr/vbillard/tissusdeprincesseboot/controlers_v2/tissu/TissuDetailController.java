package fr.vbillard.tissusdeprincesseboot.controlers_v2.tissu;

import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controlers_v2.IController;
import fr.vbillard.tissusdeprincesseboot.controlers_v2.RootController;
import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuDto;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.enums.UnitePoids;
import fr.vbillard.tissusdeprincesseboot.services.MatiereService;
import fr.vbillard.tissusdeprincesseboot.services.TissageService;
import fr.vbillard.tissusdeprincesseboot.services.TissuService;
import fr.vbillard.tissusdeprincesseboot.services.TypeTissuService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.RowConstraints;
import org.modelmapper.ModelMapper;

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
    public RowConstraints ancienneValeurRow;
    public RowConstraints consommeRow;

    StageInitializer initializer;

    TissuDto tissu;
	private boolean okClicked = false;

    ModelMapper mapper;
    TypeTissuService typeTissuService;
    MatiereService matiereService;
    TissageService tissageService;
    TissuService tissuService;
    RootController rootController;

    public TissuDetailController(RootController rootController, ModelMapper mapper, TissuService tissuService, TypeTissuService typeTissuService, MatiereService matiereService, TissageService tissageService) {
        this.mapper = mapper;
        this.tissuService = tissuService;
        this.typeTissuService = typeTissuService;
        this.matiereService = matiereService;
        this.tissageService = tissageService;
        this.rootController = rootController;
    }

    @Override
    public void setStageInitializer(StageInitializer initializer, Object... data) {
        this.initializer = initializer;
        if (data.length == 1 && data[0] instanceof TissuDto){
            tissu = (TissuDto) data[0];
            if (tissu == null || tissu.getChuteProperty() == null) {
                tissu = mapper.map(new Tissu(0, "", 0, 0, "", null, typeTissuService.getAll().get(0), 0,
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

            unitePoidsField.setText(tissu.getUnitePoidsProperty() == null ? UnitePoids.NON_RENSEIGNE.label : tissu.getUnitePoids());

			typeField.setText(tissu.getTypeProperty() == null ? "" : tissu.getType());

			 matiereField.setText(tissu.getMatiereProperty() == null ? "" : tissu.getMatiere());

			tissageField.setText(tissu.getTissageProperty() == null ? "" : tissu.getTissage());
      }
    }

    public boolean isOkClicked() {
		return okClicked;
	}

    public void edit(){
        rootController.displayTissusEdit(tissu);

    }
}
