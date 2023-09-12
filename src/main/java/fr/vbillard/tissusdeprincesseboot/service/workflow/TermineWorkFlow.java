package fr.vbillard.tissusdeprincesseboot.service.workflow;

import fr.vbillard.tissusdeprincesseboot.exception.NotAllowed;
import fr.vbillard.tissusdeprincesseboot.service.ProjetService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("terminé")
@Scope("prototype")
public class TermineWorkFlow extends Workflow {

	public TermineWorkFlow(ProjetService projetService) {
		this.projetService = projetService;
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
		ErrorWarn errorwarn = new ErrorWarn();
		// TODO Auto-generated method stub
		return errorwarn;
	}

	@Override
	protected ErrorWarn verifyCancel() {
		ErrorWarn errorwarn = new ErrorWarn();
		// TODO Auto-generated method stub
		return errorwarn;
	}

	@Override
	protected ErrorWarn verifyDelete() {
		ErrorWarn errorwarn = new ErrorWarn();
		// TODO Auto-generated method stub
		return errorwarn;
	}

	@Override
	protected void doDelete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected ErrorWarn verifyArchive() {
		ErrorWarn errorwarn = new ErrorWarn();
		// TODO Auto-generated method stub
		return errorwarn;
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
		return false;
	}

	@Override
	public boolean isArchivePossible() {
		return false;
	}

}
