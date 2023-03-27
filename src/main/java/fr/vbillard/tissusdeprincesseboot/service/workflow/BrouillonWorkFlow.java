package fr.vbillard.tissusdeprincesseboot.service.workflow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.exception.NotAllowed;
import fr.vbillard.tissusdeprincesseboot.model.enums.ProjectStatus;
import fr.vbillard.tissusdeprincesseboot.service.ProjetService;
import fr.vbillard.tissusdeprincesseboot.service.TissuRequisService;
import fr.vbillard.tissusdeprincesseboot.service.TissuUsedService;
import fr.vbillard.tissusdeprincesseboot.service.UserPrefService;

@Component("brouillon")
@Scope("prototype")
public class BrouillonWorkFlow extends Workflow {

	private final TissuRequisService tissuRequisService;
	private final TissuUsedService tissuUsedService;
	private final UserPrefService userPrefService;
	private final Rules rules;
	
	@Autowired
	public BrouillonWorkFlow(Rules rules, UserPrefService userPrefService, ProjetService projetService,
			TissuRequisService tissuRequisService, TissuUsedService tissuUsedService) {
		this.projetService = projetService;
		this.tissuRequisService = tissuRequisService;
		this.tissuUsedService = tissuUsedService;
		this.userPrefService = userPrefService;
		this.rules = rules;
		description = "Les projets « Brouillon » permettent de faire des essais. Les longueurs de tissus qui sont attribuées à un « Brouillon » ne sont pas retirées du stock et ne sont pas réservées pour ce projet.";
	}

	@Override
	protected void doNextStep() {
		projet.setStatus(ProjectStatus.PLANIFIE);
		projetService.saveOrUpdate(projet);

	}

	@Override
	public void doCancel() {
		throw new NotAllowed();
	}

	@Override
	protected ErrorWarn verifyNextStep() {
		ErrorWarn errorwarn = rules.verifyLenght(projet);

		return errorwarn;
	}

	@Override
	protected ErrorWarn verifyCancel() {
		return nonAutorise();
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
		return true;
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
		return true;
	}

}
