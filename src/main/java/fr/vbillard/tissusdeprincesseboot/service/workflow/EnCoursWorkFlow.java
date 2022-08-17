package fr.vbillard.tissusdeprincesseboot.service.workflow;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

@Component
public class EnCoursWorkFlow extends Workflow {

	private TissuUsedService tissuUsedService;
	private TissuService tissuService;

	@Autowired
	public EnCoursWorkFlow(ProjetService projetService, TissuUsedService tissuUsedService, TissuService tissuService) {
		this(projetService);
		this.tissuUsedService = tissuUsedService;
		this.tissuService = tissuService;
	}

	public EnCoursWorkFlow(ProjetService projetService) {
		super(projetService);
		cancelPossible = true;
		nextPossible = true;
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
	protected List<String> verifyNextStep() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	protected List<String> verifyCancel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected List<String> verifyDelete() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void doDelete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected List<String> verifyArchive() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void doArchive() {
		// TODO Auto-generated method stub
		
	}


}
