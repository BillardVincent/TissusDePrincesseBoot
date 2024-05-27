package fr.vbillard.tissusdeprincesseboot.service.workflow;

import fr.vbillard.tissusdeprincesseboot.model.enums.ProjectStatus;
import fr.vbillard.tissusdeprincesseboot.service.ProjetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Component("brouillon")
@Scope(SCOPE_PROTOTYPE)
public class BrouillonWorkFlow extends Workflow {

	private final Rules rules;
	
	@Autowired
	public BrouillonWorkFlow(Rules rules, ProjetService projetService) {
		this.projetService = projetService;
		this.rules = rules;
		description = "Les projets « Brouillon » permettent de faire des essais. Les longueurs de tissus qui sont attribuées à un « Brouillon » ne sont pas retirées du stock et ne sont pas réservées pour ce projet.";
	}

	@Override
	protected void doNextStep() {
		projet.setStatus(ProjectStatus.PLANIFIE);
		projetService.saveOrUpdate(projet);
	}

	@Override
	protected ErrorWarn verifyNextStep() {
        return rules.verifyLength(projet);
	}

	@Override
	public boolean isNextPossible() {
		return true;
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
