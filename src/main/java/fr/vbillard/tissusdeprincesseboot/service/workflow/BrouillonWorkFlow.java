package fr.vbillard.tissusdeprincesseboot.service.workflow;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.exception.NotAllowed;
import fr.vbillard.tissusdeprincesseboot.model.Projet;
import fr.vbillard.tissusdeprincesseboot.model.TissuRequis;
import fr.vbillard.tissusdeprincesseboot.model.TissuUsed;
import fr.vbillard.tissusdeprincesseboot.model.enums.ProjectStatus;
import fr.vbillard.tissusdeprincesseboot.service.PreferenceService;
import fr.vbillard.tissusdeprincesseboot.service.ProjetService;
import fr.vbillard.tissusdeprincesseboot.service.TissuRequisService;
import fr.vbillard.tissusdeprincesseboot.service.TissuUsedService;
import fr.vbillard.tissusdeprincesseboot.service.UserPrefService;

@Component
public class BrouillonWorkFlow extends Workflow {

	private TissuRequisService tissuRequisService;
	private TissuUsedService tissuUsedService;
	private UserPrefService userPrefService;
	
	public BrouillonWorkFlow(UserPrefService userPrefService, ProjetService projetService, TissuRequisService tissuRequisService, TissuUsedService tissuUsedService) {
		super(projetService);
		this.tissuRequisService = tissuRequisService;
		this.tissuUsedService = tissuUsedService;
		this.userPrefService = userPrefService;
		cancelPossible = false;
		nextPossible = true;
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
	protected List<String> verifyNextStep() {
		List<String> errors = new ArrayList();
		List<TissuRequis> trList = tissuRequisService.getAllTissuRequisByPatron(projet.getPatron().getId());
		for (TissuRequis tr : trList) {
			List<TissuUsed> tuList = tissuUsedService.getTissuUsedByTissuRequisAndProjet(tr, projet);
			int longueur = tuList.stream().map(t -> t.getLongueur()).reduce(0, Integer::sum);
			float marge = userPrefService.getUser().getPoidsMargePercent();
			
		}
		return errors;
	}

	@Override
	protected List<String> verifyCancel() {
		List<String> errors = new ArrayList();
		errors.add("Non autorisé");
		return errors;
	}

	@Override
	protected List<String> verifyDelete() {
		List<String> errors = new ArrayList();
		// TODO Auto-generated method stub
		return errors;
	}

	@Override
	protected void doDelete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected List<String> verifyArchive() {
		List<String> errors = new ArrayList();
		// TODO Auto-generated method stub
		return errors;
	}

	@Override
	protected void doArchive() {
		// TODO Auto-generated method stub
		
	}

}
