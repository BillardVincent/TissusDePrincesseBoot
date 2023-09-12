package fr.vbillard.tissusdeprincesseboot.service.workflow;

import fr.vbillard.tissusdeprincesseboot.model.Projet;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class WorkflowService {

  private final Map<String, Workflow> strategies;

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
