package fr.vbillard.tissusdeprincesseboot.service.workflow;

import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.controller.utils.ShowAlert;
import fr.vbillard.tissusdeprincesseboot.controller.utils.path.PathEnum;
import fr.vbillard.tissusdeprincesseboot.model.Inventaire;
import fr.vbillard.tissusdeprincesseboot.model.Projet;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.TissuUsed;
import fr.vbillard.tissusdeprincesseboot.model.enums.ProjectStatus;
import fr.vbillard.tissusdeprincesseboot.service.InventaireService;
import fr.vbillard.tissusdeprincesseboot.service.ProjetService;
import fr.vbillard.tissusdeprincesseboot.service.TissuService;
import fr.vbillard.tissusdeprincesseboot.service.TissuUsedService;
import javafx.scene.control.ButtonType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Component("en cours")
@Scope("prototype")
public class EnCoursWorkFlow extends Workflow {

	private final TissuUsedService tissuUsedService;
	private final TissuService tissuService;
	private final InventaireService inventaireService;
	private final StageInitializer stage;

	@Autowired
	public EnCoursWorkFlow(StageInitializer stage, InventaireService inventaireService, ProjetService projetService, TissuUsedService tissuUsedService, TissuService tissuService) {
		this.projetService = projetService;
		this.tissuUsedService = tissuUsedService;
		this.tissuService = tissuService;
		this.inventaireService = inventaireService;
		this.stage = stage;
		description = "Le premier coup de ciseaux est donné ! Les tissus ne peuvent plus revenir dans le stock ! Les modifications sont plus difficiles.\r\n" + 
				"Les longueurs de tissus sont réservées. Elles ne sont pas retirées du stock, mais ne sont pas disponibles pour les autres projets.\r\n" + 
				"";
	}

	@Override
	@Transactional
	public void doNextStep() {
		if (validateNextStep().orElse(ButtonType.NO).equals(ButtonType.OK)) {
			Inventaire inventaire = new Inventaire();
			inventaire.setProjet(projet);
			inventaire.setTissus(tissuUsedService.getByProjet(projet));
			inventaireService.saveOrUpdate(inventaire);
			projet.setStatus(ProjectStatus.TERMINE);
			projetService.saveOrUpdate(projet);
			
			Iterator<TissuUsed> iterator = inventaire.getTissus().iterator();
			
			while (iterator.hasNext()) {
				TissuUsed tissuUsed = iterator.next();
				FxData data = new FxData();
				
				data.setTissuUsed(tissuUsed);
				
				FxData result = stage.displayModale(PathEnum.CARROUSEL, data, "Inventaire");
				
				if(result == null || result.getTissuUsed() == null) {
					break;
				}
				
				tissuService.saveOrUpdate(result.getTissu());
				iterator.remove();
				inventaireService.saveOrUpdate(inventaire);

			}
			
			if (inventaire.getTissus().size() == 0) {
				inventaireService.delete(inventaire);
			}
			//deleteTissuLenght(projet);

		}

	}

	@Override
	public void doCancel() {
		projet.setStatus(ProjectStatus.PLANIFIE);
		projetService.saveOrUpdate(projet);
	}

	private void deleteTissuLength(Projet projet) {
		List<TissuUsed> tissuUsedList = tissuUsedService.getByProjet(projet);
		for (TissuUsed tu : tissuUsedList) {
			Tissu t = tu.getTissu();
			t.setLongueur(t.getLongueur() - tu.getLongueur());
			tissuService.saveOrUpdate(t);
		}
	}

	private Optional<ButtonType> validateNextStep() {

		return ShowAlert.confirmation(null, "Terminer le projet", "Cette opération est définitive",
				"Souhaitez vous terminer ce projet? Vos stocks font être définitivement réduit de la quantité allouée à ce projet. Cette opération est définitive");
	}

	@Override
	protected ErrorWarn verifyNextStep() {
		ErrorWarn errorwarn = new ErrorWarn();
		// TODO Auto-generated method stub
		return errorwarn;
	}


	@Override
	protected ErrorWarn verifyCancel() {
		ErrorWarn errorwarn = new ErrorWarn();
		// TODO Auto-generated method stub
		return errorwarn;
	}

	@Override
	protected ErrorWarn verifyDelete() {
		ErrorWarn errorwarn = new ErrorWarn();
		// TODO Auto-generated method stub
		return errorwarn;
	}

	@Override
	protected void doDelete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected ErrorWarn verifyArchive() {
		ErrorWarn errorwarn = new ErrorWarn();
		// TODO Auto-generated method stub
		return errorwarn;
	}

	@Override
	protected void doArchive() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isNextPossible() {
		return true;
	}

	@Override
	public boolean isCancelPossible() {
		return true;
	}

	@Override
	public boolean isDeletePossible() {
		return false;
	}

	@Override
	public boolean isArchivePossible() {
		return false;
	}


}
