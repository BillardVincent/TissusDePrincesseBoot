package fr.vbillard.tissusdeprincesseboot.service.workflow;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.exception.NotAllowed;
import fr.vbillard.tissusdeprincesseboot.model.TissuRequis;
import fr.vbillard.tissusdeprincesseboot.model.TissuUsed;
import fr.vbillard.tissusdeprincesseboot.model.enums.ProjectStatus;
import fr.vbillard.tissusdeprincesseboot.service.ProjetService;
import fr.vbillard.tissusdeprincesseboot.service.TissuRequisService;
import fr.vbillard.tissusdeprincesseboot.service.TissuUsedService;
import fr.vbillard.tissusdeprincesseboot.service.UserPrefService;

@Component("brouillon")
@Scope("prototype")
public class BrouillonWorkFlow extends Workflow {

	private TissuRequisService tissuRequisService;
	private TissuUsedService tissuUsedService;
	private UserPrefService userPrefService;
	
	@Autowired
	public BrouillonWorkFlow(UserPrefService userPrefService, ProjetService projetService, TissuRequisService tissuRequisService, TissuUsedService tissuUsedService) {
		this.projetService = projetService;
		this.tissuRequisService = tissuRequisService;
		this.tissuUsedService = tissuUsedService;
		this.userPrefService = userPrefService;
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
		ErrorWarn errorwarn = new ErrorWarn();
		List<TissuRequis> trList = tissuRequisService.getAllTissuRequisByPatron(projet.getPatron().getId());
		for (TissuRequis tr : trList) {
			List<TissuUsed> tuList = tissuUsedService.getTissuUsedByTissuRequisAndProjet(tr, projet);
			int longueurUtilisee = tuList.stream().map(t -> t.getLongueur()).reduce(0, Integer::sum);
			float marge = userPrefService.getUser().getPoidsMargePercent();
			
			if (tr.getLongueur() - marge * tr.getLongueur() > 0) {
				//TODO
			}
			
		}
		return errorwarn;
	}

	@Override
	protected ErrorWarn verifyCancel() {
		return nonAutorisé();
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
