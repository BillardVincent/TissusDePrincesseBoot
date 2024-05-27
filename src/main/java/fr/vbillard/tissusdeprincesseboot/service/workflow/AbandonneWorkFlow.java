package fr.vbillard.tissusdeprincesseboot.service.workflow;

import fr.vbillard.tissusdeprincesseboot.service.ProjetService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Component("abandonné")
@Scope(SCOPE_PROTOTYPE)
public class AbandonneWorkFlow extends Workflow {

	
	public AbandonneWorkFlow(ProjetService projetService) {
		this.projetService = projetService;
		description = "";
	}


	@Override
	public boolean isDeletePossible() {
		return true;
	}



}
