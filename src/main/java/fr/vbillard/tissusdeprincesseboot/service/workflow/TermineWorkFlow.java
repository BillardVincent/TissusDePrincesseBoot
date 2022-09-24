package fr.vbillard.tissusdeprincesseboot.service.workflow;

import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.exception.NotAllowed;
import fr.vbillard.tissusdeprincesseboot.service.ProjetService;

@Component
public class TermineWorkFlow extends Workflow {

	public TermineWorkFlow(ProjetService projetService) {
		super(projetService);
		cancelPossible = false;
		nextPossible = false;
		description = "Le projet est arrivé à son terme. Plus aucune action ne peut être effectuée. Les longueurs sont définitivement retirées des stocks de tissus.";
	}

	@Override
	protected void doNextStep() {
		throw new NotAllowed();
	}

	@Override
	protected void doCancel() {
		throw new NotAllowed();

	}

	@Override
	protected ErrorWarn verifyNextStep() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ErrorWarn verifyCancel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ErrorWarn verifyDelete() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void doDelete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected ErrorWarn verifyArchive() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void doArchive() {
		// TODO Auto-generated method stub
		
	}

}
