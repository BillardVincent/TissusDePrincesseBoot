package fr.vbillard.tissusdeprincesseboot.service.workflow;


import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.model.enums.ProjectStatus;
import fr.vbillard.tissusdeprincesseboot.service.ProjetService;

@Component
public class PlanifieWorkFlow extends Workflow {

	public PlanifieWorkFlow(ProjetService projetService) {
		super(projetService);
		cancelPossible = true;
		nextPossible = true;
		description = "Pour un projet de type « Planifié », les longueurs de tissus sont réservées. Elles ne sont pas retirées du stock, mais ne sont pas disponibles pour les autres projets. Les modifications restent possibles";
	}

	@Override
	protected void doNextStep() {
		projet.setStatus(ProjectStatus.EN_COURS);
		projetService.saveOrUpdate(projet);

	}

	@Override
	protected void doCancel() {
		projet.setStatus(ProjectStatus.BROUILLON);
		projetService.saveOrUpdate(projet);
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
