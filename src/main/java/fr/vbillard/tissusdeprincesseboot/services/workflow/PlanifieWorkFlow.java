package fr.vbillard.tissusdeprincesseboot.services.workflow;

import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.model.Projet;
import fr.vbillard.tissusdeprincesseboot.model.enums.ProjectStatus;
import fr.vbillard.tissusdeprincesseboot.services.ProjetService;

@Component
public class PlanifieWorkFlow extends Workflow {

	public PlanifieWorkFlow(ProjetService projetService) {
		super(projetService);
		cancelPossible = true;
		nextPossible = true;
	}

	@Override
	public void nextStep(Projet projet) {
		projet.setStatus(ProjectStatus.EN_COURS);
		projetService.saveOrUpdate(projet);

	}

	@Override
	public void cancel(Projet projet) {
		projet.setStatus(ProjectStatus.BROUILLON);
		projetService.saveOrUpdate(projet);
	}

}
