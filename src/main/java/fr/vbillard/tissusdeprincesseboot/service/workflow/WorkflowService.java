package fr.vbillard.tissusdeprincesseboot.service.workflow;

import java.util.Map;

import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.model.Projet;

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
