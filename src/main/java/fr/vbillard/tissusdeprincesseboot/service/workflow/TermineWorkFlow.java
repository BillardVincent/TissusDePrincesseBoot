package fr.vbillard.tissusdeprincesseboot.service.workflow;

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


}
