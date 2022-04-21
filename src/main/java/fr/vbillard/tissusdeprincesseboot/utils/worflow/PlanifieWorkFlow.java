package fr.vbillard.tissusdeprincesseboot.utils.worflow;

import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.exception.NotAllowed;
import fr.vbillard.tissusdeprincesseboot.model.Projet;
import fr.vbillard.tissusdeprincesseboot.model.enums.ProjectStatus;

@Component
public class PlanifieWorkFlow extends Workflow {

	@Override
	public void nextStep(Projet projet) {
		projet.setStatus(ProjectStatus.PANIFIE);
	}

	@Override
	public void cancel(Projet projet) {
		throw new NotAllowed();
	}

	@Override
	public boolean IsNextPossible() {
		return true;
	}

	@Override
	public boolean IsCancelPossible() {
		return false;
	}

}
