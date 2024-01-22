package fr.vbillard.tissusdeprincesseboot.service.workflow;


import fr.vbillard.tissusdeprincesseboot.model.enums.ProjectStatus;
import fr.vbillard.tissusdeprincesseboot.service.ProjetService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("planifié")
@Scope("prototype")
public class PlanifieWorkFlow extends Workflow {

	public PlanifieWorkFlow(ProjetService projetService) {
		this.projetService = projetService;
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
	public boolean isNextPossible() {
		return true;
	}

	@Override
	public boolean isCancelPossible() {
		return true;
	}

}
