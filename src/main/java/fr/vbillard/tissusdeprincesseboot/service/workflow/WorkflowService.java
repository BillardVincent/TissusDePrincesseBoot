package fr.vbillard.tissusdeprincesseboot.service.workflow;

import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.model.Projet;
import fr.vbillard.tissusdeprincesseboot.model.enums.ProjectStatus;
import lombok.AllArgsConstructor;

@Component
public class WorkflowService {

	  private Map<String, Workflow> strategies;
	  
	    public WorkflowService(Map<String, Workflow> strategies) {
	      this.strategies = strategies;
	  
	    }
	  
	    public Workflow getWorkflow(Projet projet) {
	    	String statut = (projet.getStatus().getLabel());
	    	 if (!strategies.containsKey(statut)) {
	 	        throw new IllegalArgumentException("The strategy " + statut + " does not exist.");
	 	      }
	 	  
	    	Workflow workflow = strategies.get(statut);
	    	workflow.setProjet(projet);
	    	return workflow;
	    }



}
