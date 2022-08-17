package fr.vbillard.tissusdeprincesseboot.service.workflow;

import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.model.enums.ProjectStatus;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class WorkflowService {

	private BrouillonWorkFlow brouillonWorkFlow;
	private EnCoursWorkFlow enCoursWorkFlow;
	private PlanifieWorkFlow planifieWorkFlow;
	private TermineWorkFlow termineWorkFlow;

	/**
	 * "Context" dans le pattern de Strategy du workflow
	 * @param statut
	 * @return
	 */
	public Workflow getWorkflow(ProjectStatus statut) {
		switch (statut) {
		case BROUILLON:
			return brouillonWorkFlow;
		case EN_COURS:
			return enCoursWorkFlow;
		case PLANIFIE:
			return planifieWorkFlow;
		case TERMINE:
			return termineWorkFlow;
		default:
			return null;
		}

	}

}
