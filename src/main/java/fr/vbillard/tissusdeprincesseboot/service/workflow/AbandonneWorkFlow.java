package fr.vbillard.tissusdeprincesseboot.service.workflow;

import fr.vbillard.tissusdeprincesseboot.service.ProjetService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("abandonné")
@Scope("prototype")
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
