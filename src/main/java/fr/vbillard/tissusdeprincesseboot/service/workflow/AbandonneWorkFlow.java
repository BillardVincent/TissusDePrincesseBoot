package fr.vbillard.tissusdeprincesseboot.service.workflow;

import java.util.List;

import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.exception.NotAllowed;
import fr.vbillard.tissusdeprincesseboot.model.TissuRequis;
import fr.vbillard.tissusdeprincesseboot.model.TissuUsed;
import fr.vbillard.tissusdeprincesseboot.model.enums.ProjectStatus;
import fr.vbillard.tissusdeprincesseboot.service.ProjetService;
import fr.vbillard.tissusdeprincesseboot.service.TissuRequisService;
import fr.vbillard.tissusdeprincesseboot.service.TissuUsedService;
import fr.vbillard.tissusdeprincesseboot.service.UserPrefService;

@Component
public class AbandonneWorkFlow extends Workflow {

	private TissuRequisService tissuRequisService;
	private TissuUsedService tissuUsedService;
	private UserPrefService userPrefService;
	
	public AbandonneWorkFlow(ProjetService projetService) {
		super(projetService);
		cancelPossible = false;
		nextPossible = false;
		description = "";
	}

	@Override
	protected void doNextStep() {
		throw new NotAllowed();

	}

	@Override
	public void doCancel() {
		throw new NotAllowed();
	}

	@Override
	protected ErrorWarn verifyNextStep() {
		ErrorWarn errorwarn = new ErrorWarn();
		errorwarn.addError("Non autorisé");
		return errorwarn;
	}

	@Override
	protected ErrorWarn verifyCancel() {
		ErrorWarn errorwarn = new ErrorWarn();
		errorwarn.addError("Non autorisé");
		return errorwarn;
	}

	@Override
	protected ErrorWarn verifyDelete() {
		ErrorWarn errorwarn = new ErrorWarn();
		errorwarn.addError("Non autorisé");
		return errorwarn;
	}

	@Override
	protected void doDelete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected ErrorWarn verifyArchive() {
		ErrorWarn errorwarn = new ErrorWarn();
		errorwarn.addError("Non autorisé");
		return errorwarn;
	}

	@Override
	protected void doArchive() {
		// TODO Auto-generated method stub
		
	}

}
