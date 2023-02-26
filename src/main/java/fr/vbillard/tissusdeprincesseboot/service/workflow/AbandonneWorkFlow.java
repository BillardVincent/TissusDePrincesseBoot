package fr.vbillard.tissusdeprincesseboot.service.workflow;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.exception.NotAllowed;
import fr.vbillard.tissusdeprincesseboot.service.ProjetService;
import fr.vbillard.tissusdeprincesseboot.service.TissuRequisService;
import fr.vbillard.tissusdeprincesseboot.service.TissuUsedService;
import fr.vbillard.tissusdeprincesseboot.service.UserPrefService;

@Component("abandonné")
@Scope("prototype")
public class AbandonneWorkFlow extends Workflow {

	private TissuRequisService tissuRequisService;
	private TissuUsedService tissuUsedService;
	private UserPrefService userPrefService;
	
	public AbandonneWorkFlow(ProjetService projetService) {
		this.projetService = projetService;
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
		return nonAutorise();

	}

	@Override
	protected ErrorWarn verifyCancel() {
		return nonAutorise();

	}

	@Override
	protected ErrorWarn verifyDelete() {
		return nonAutorise();

	}

	@Override
	protected void doDelete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected ErrorWarn verifyArchive() {
		return nonAutorise();

	}

	@Override
	protected void doArchive() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isNextPossible() {
		return false;
	}

	@Override
	public boolean isCancelPossible() {
		return false;
	}

	@Override
	public boolean isDeletePossible() {
		return true;
	}

	@Override
	public boolean isArchivePossible() {
		return false;
	}

}
