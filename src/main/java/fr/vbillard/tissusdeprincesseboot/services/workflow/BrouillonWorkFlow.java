package fr.vbillard.tissusdeprincesseboot.services.workflow;

import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.exception.NotAllowed;
import fr.vbillard.tissusdeprincesseboot.model.Projet;
import fr.vbillard.tissusdeprincesseboot.model.enums.ProjectStatus;
import fr.vbillard.tissusdeprincesseboot.services.ProjetService;

@Component
public class BrouillonWorkFlow extends Workflow {

	public BrouillonWorkFlow(ProjetService projetService) {
		super(projetService);
		cancelPossible = false;
		nextPossible = true;
	}

	@Override
	public void nextStep(Projet projet) {
		projet.setStatus(ProjectStatus.PLANIFIE);
		projetService.saveOrUpdate(projet);

	}

	@Override
	public void cancel(Projet projet) {
		throw new NotAllowed();
	}

}
