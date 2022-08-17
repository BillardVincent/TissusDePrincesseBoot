package fr.vbillard.tissusdeprincesseboot.service.workflow;

import java.util.List;

import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.model.Projet;
import fr.vbillard.tissusdeprincesseboot.model.enums.ProjectStatus;
import fr.vbillard.tissusdeprincesseboot.service.ProjetService;

@Component
public class PlanifieWorkFlow extends Workflow {

	public PlanifieWorkFlow(ProjetService projetService) {
		super(projetService);
		cancelPossible = true;
		nextPossible = true;
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
	protected List<String> verifyNextStep() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected List<String> verifyCancel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected List<String> verifyDelete() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void doDelete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected List<String> verifyArchive() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void doArchive() {
		// TODO Auto-generated method stub
		
	}

}
