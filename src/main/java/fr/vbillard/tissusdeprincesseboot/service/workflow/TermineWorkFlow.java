package fr.vbillard.tissusdeprincesseboot.service.workflow;

import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.exception.NotAllowed;
import fr.vbillard.tissusdeprincesseboot.model.Projet;
import fr.vbillard.tissusdeprincesseboot.service.ProjetService;

@Component
public class TermineWorkFlow extends Workflow {

	public TermineWorkFlow(ProjetService projetService) {
		super(projetService);
		cancelPossible = false;
		nextPossible = false;
	}

	@Override
	public void nextStep(Projet projet) {
		throw new NotAllowed();
	}

	@Override
	public void cancel(Projet projet) {
		throw new NotAllowed();

	}

}
