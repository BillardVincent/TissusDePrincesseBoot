package fr.vbillard.tissusdeprincesseboot.service.workflow;

import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.model.Projet;
import fr.vbillard.tissusdeprincesseboot.service.ProjetService;

@Component
public abstract class Workflow {

	protected ProjetService projetService;
	protected boolean nextPossible;
	protected boolean cancelPossible;

	protected Workflow(ProjetService projetService) {
		this.projetService = projetService;
	}

	public final boolean isNextPossible() {
		return nextPossible;
	}

	public final boolean isCancelPossible() {
		return cancelPossible;
	}

	public abstract void nextStep(Projet projet);

	public abstract void cancel(Projet projet);

}
