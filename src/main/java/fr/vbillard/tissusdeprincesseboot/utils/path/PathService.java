package fr.vbillard.tissusdeprincesseboot.utils.path;

import java.io.IOException;

import org.springframework.stereotype.Service;

import fr.vbillard.tissusdeprincesseboot.controller.InventaireBatchController;
import fr.vbillard.tissusdeprincesseboot.controller.PreferenceController;
import fr.vbillard.tissusdeprincesseboot.controller.RootController;
import fr.vbillard.tissusdeprincesseboot.controller.common.FournitureRequiseSelectedController;
import fr.vbillard.tissusdeprincesseboot.controller.common.TissuRequisCardController;
import fr.vbillard.tissusdeprincesseboot.controller.common.TissuRequisSelectedController;
import fr.vbillard.tissusdeprincesseboot.controller.caracteristique.MatiereEditController;
import fr.vbillard.tissusdeprincesseboot.controller.caracteristique.TissageEditController;
import fr.vbillard.tissusdeprincesseboot.controller.caracteristique.TypeFournitureEditController;
import fr.vbillard.tissusdeprincesseboot.controller.common.CheckBoxChoiceController;
import fr.vbillard.tissusdeprincesseboot.controller.common.PlusCardController;
import fr.vbillard.tissusdeprincesseboot.controller.common.SetLongueurDialogController;
import fr.vbillard.tissusdeprincesseboot.controller.common.SetWebUrlDialogController;
import fr.vbillard.tissusdeprincesseboot.controller.fourniture.FournitureCardController;
import fr.vbillard.tissusdeprincesseboot.controller.fourniture.FournitureCarrouselController;
import fr.vbillard.tissusdeprincesseboot.controller.fourniture.FournitureController;
import fr.vbillard.tissusdeprincesseboot.controller.fourniture.FournitureDetailController;
import fr.vbillard.tissusdeprincesseboot.controller.fourniture.FournitureEditController;
import fr.vbillard.tissusdeprincesseboot.controller.fourniture.FournitureSearchController;
import fr.vbillard.tissusdeprincesseboot.controller.patron.ListElementController;
import fr.vbillard.tissusdeprincesseboot.controller.patron.PatronCardController;
import fr.vbillard.tissusdeprincesseboot.controller.patron.PatronDetailController;
import fr.vbillard.tissusdeprincesseboot.controller.patron.PatronEditController;
import fr.vbillard.tissusdeprincesseboot.controller.patron.PatronListController;
import fr.vbillard.tissusdeprincesseboot.controller.patron.PatronSearchController;
import fr.vbillard.tissusdeprincesseboot.controller.projet.FournitureUsedCardController;
import fr.vbillard.tissusdeprincesseboot.controller.projet.ProjetCardController;
import fr.vbillard.tissusdeprincesseboot.controller.projet.ProjetEditController;
import fr.vbillard.tissusdeprincesseboot.controller.projet.ProjetEditListElementController;
import fr.vbillard.tissusdeprincesseboot.controller.projet.ProjetListController;
import fr.vbillard.tissusdeprincesseboot.controller.projet.ProjetSearchController;
import fr.vbillard.tissusdeprincesseboot.controller.projet.TissuUsedCardController;
import fr.vbillard.tissusdeprincesseboot.controller.tissu.CarrouselController;
import fr.vbillard.tissusdeprincesseboot.controller.tissu.TissuCardController;
import fr.vbillard.tissusdeprincesseboot.controller.tissu.TissuDetailController;
import fr.vbillard.tissusdeprincesseboot.controller.tissu.TissuEditController;
import fr.vbillard.tissusdeprincesseboot.controller.tissu.TissuSearchController;
import fr.vbillard.tissusdeprincesseboot.controller.tissu.TissusController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxmlPathProperties;

@Service
public class PathService {
	
	private final FxmlPathProperties pathProperties;
	
	
	public PathService(FxmlPathProperties pathProperties) {
		super();
		this.pathProperties = pathProperties;
	}


	public PathHolder pathEnumToURL(PathEnum pathEnum) throws IOException {
		switch (pathEnum) {
		case ROOT:
			return new PathHolder(pathProperties.getRoot().getURL(), RootController.class);
		case TISSUS:
			return new PathHolder(pathProperties.getTissus().getURL(), TissusController.class);
		case TISSUS_DETAILS:
			return new PathHolder(pathProperties.getTissuDetail().getURL(), TissuDetailController.class);
		case TISSUS_EDIT:
			return new PathHolder(pathProperties.getTissuEdit().getURL(), TissuEditController.class);
		case TISSUS_CARD:
			return new PathHolder(pathProperties.getTissuCard().getURL(), TissuCardController.class);
		case PROJET_LIST:
			return new PathHolder(pathProperties.getProjetList().getURL(), ProjetListController.class);
		case PROJET_EDIT:
			return new PathHolder(pathProperties.getProjetEdit().getURL(), ProjetEditController.class);
		case PROJET_CARD:
			return new PathHolder(pathProperties.getProjetCard().getURL(), ProjetCardController.class);
		case PATRON_LIST:
			return new PathHolder(pathProperties.getPatronList().getURL(), PatronListController.class);
		case PATRON_DETAILS:
			return new PathHolder(pathProperties.getPatronDetail().getURL(), PatronDetailController.class);
		case PATRON_EDIT:
			return new PathHolder(pathProperties.getPatronEdit().getURL(), PatronEditController.class);
		case PATRON_CARD:
			return new PathHolder(pathProperties.getPatronCard().getURL(), PatronCardController.class);
		case MATIERE:
			return new PathHolder(pathProperties.getMatiereEdit().getURL(), MatiereEditController.class);
		case TISSAGE:
			return new PathHolder(pathProperties.getTissageEdit().getURL(), TissageEditController.class);
		case LIST_ELEMENT:
			return new PathHolder(pathProperties.getListElement().getURL(), ListElementController.class);
		case TISSU_REQUIS:
			return new PathHolder(pathProperties.getTissuRequisCard().getURL(), TissuRequisCardController.class);
		case PROJET_EDIT_LIST_ELEMENT:
			return new PathHolder(pathProperties.getProjetEditListElement().getURL(),
					ProjetEditListElementController.class);
		case TISSU_USED_CARD:
			return new PathHolder(pathProperties.getTissuUsedCard().getURL(), TissuUsedCardController.class);
		case TISSU_REQUIS_SELECTED:
			return new PathHolder(pathProperties.getTissuRequisSelected().getURL(),
					TissuRequisSelectedController.class);
		case PLUS_CARD:
			return new PathHolder(pathProperties.getPlusCard().getURL(), PlusCardController.class);
		case SET_LONGUEUR:
			return new PathHolder(pathProperties.getLongueur().getURL(), SetLongueurDialogController.class);
		case WEB_URL:
			return new PathHolder(pathProperties.getUrl().getURL(), SetWebUrlDialogController.class);
		case PROJET_DETAILS:
			break;
		case TISSU_SEARCH:
			return new PathHolder(pathProperties.getTissuSearch().getURL(), TissuSearchController.class);
		case PROJET_SEARCH:
			return new PathHolder(pathProperties.getProjetSearch().getURL(), ProjetSearchController.class);
		case PATRON_SEARCH:
			return new PathHolder(pathProperties.getPatronSearch().getURL(), PatronSearchController.class);
		case CHECKBOX_CHOICE:
			return new PathHolder(pathProperties.getCheckBoxChoice().getURL(), CheckBoxChoiceController.class);
		case PREF:
			return new PathHolder(pathProperties.getPreference().getURL(), PreferenceController.class);
		case CARROUSEL:
			return new PathHolder(pathProperties.getCarrousel().getURL(), CarrouselController.class);
		case INVENTAIRE:
			return new PathHolder(pathProperties.getInventaire().getURL(), InventaireBatchController.class);
			case FOURNITURES :
				return new PathHolder(pathProperties.getFourniture().getURL(), FournitureController.class);
			case FOURNITURES_CARD:
				return new PathHolder(pathProperties.getFournitureCard().getURL(), FournitureCardController.class);
			case FOURNITURES_DETAILS:
				return new PathHolder(pathProperties.getFournitureDetail().getURL(), FournitureDetailController.class);
			case FOURNITURES_EDIT:
				return new PathHolder(pathProperties.getFournitureEdit().getURL(), FournitureEditController.class);
			case FOURNITURE_USED_CARD:
				return new PathHolder(pathProperties.getFournitureUsedCard().getURL(), FournitureUsedCardController.class);
			case FOURNITURE_REQUIS_SELECTED:
				return new PathHolder(pathProperties.getFournitureRequisSelected().getURL(), FournitureRequiseSelectedController.class);
			case FOURNITURE_CARROUSEL:
				return new PathHolder(pathProperties.getFournitureCarrousel().getURL(), FournitureCarrouselController.class);
			case TYPE_FOURNITURE:
				return new PathHolder(pathProperties.getTypeFournitureEdit().getURL(), TypeFournitureEditController.class);
			case FOURNITURE_SEARCH:
				return new PathHolder(pathProperties.getFournitureSearch().getURL(), FournitureSearchController.class);
			default:
			break;

		}
		return null;
	}

}
