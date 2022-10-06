package fr.vbillard.tissusdeprincesseboot.service.workflow;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.model.Projet;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.TissuUsed;
import fr.vbillard.tissusdeprincesseboot.model.enums.ProjectStatus;
import fr.vbillard.tissusdeprincesseboot.service.ProjetService;
import fr.vbillard.tissusdeprincesseboot.service.TissuService;
import fr.vbillard.tissusdeprincesseboot.service.TissuUsedService;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

@Component("en cours")
@Scope("prototype")
public class EnCoursWorkFlow extends Workflow {

	private TissuUsedService tissuUsedService;
	private TissuService tissuService;

	@Autowired
	public EnCoursWorkFlow(ProjetService projetService, TissuUsedService tissuUsedService, TissuService tissuService) {
		this.projetService = projetService;
		this.tissuUsedService = tissuUsedService;
		this.tissuService = tissuService;
		description = "Le premier coup de ciseau est donné ! Les tissus ne peuvent plus revenir dans le stock ! Les modifications sont plus difficiles.\r\n" + 
				"Les longueurs de tissus sont réservées. Elles ne sont pas retirées du stock, mais ne sont pas disponibles pour les autres projets.\r\n" + 
				"";
	}

	@Override
	public void doNextStep() {
		if (validateNextStep().orElse(ButtonType.NO).equals(ButtonType.OK)) {
			deleteTissuLenght(projet);
			projet.setStatus(ProjectStatus.TERMINE);
			projetService.saveOrUpdate(projet);
		}

	}

	@Override
	public void doCancel() {
		projet.setStatus(ProjectStatus.PLANIFIE);
		projetService.saveOrUpdate(projet);
	}

	private void deleteTissuLenght(Projet projet) {
		List<TissuUsed> tissuUsedList = tissuUsedService.getByProjet(projet);
		for (TissuUsed tu : tissuUsedList) {
			Tissu t = tu.getTissu();
			t.setLongueur(t.getLongueur() - tu.getLongueur());
			tissuService.saveOrUpdate(t);
		}
	}

	private Optional<ButtonType> validateNextStep() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Terminer le projet");
		alert.setHeaderText("Cette opération est définitive");
		alert.setContentText(
				"Souhaitez vous terminer ce projet? Vos stocks font être définitivement réduit de la quantité allouée à ce projet. Cette opération est définitive");

		return alert.showAndWait();
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
