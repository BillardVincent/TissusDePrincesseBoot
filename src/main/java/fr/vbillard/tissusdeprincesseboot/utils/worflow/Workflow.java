package fr.vbillard.tissusdeprincesseboot.utils.worflow;

import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.model.Projet;

@Component
public abstract class Workflow {

	public abstract boolean IsNextPossible();

	public abstract boolean IsCancelPossible();

	public abstract void nextStep(Projet projet);

	public abstract void cancel(Projet projet);

}
