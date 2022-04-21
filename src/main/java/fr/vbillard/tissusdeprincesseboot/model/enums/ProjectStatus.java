package fr.vbillard.tissusdeprincesseboot.model.enums;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import fr.vbillard.tissusdeprincesseboot.utils.worflow.BrouillonWorkFlow;
import fr.vbillard.tissusdeprincesseboot.utils.worflow.EnCoursWorkFlow;
import fr.vbillard.tissusdeprincesseboot.utils.worflow.PlanifieWorkFlow;
import fr.vbillard.tissusdeprincesseboot.utils.worflow.TermineWorkFlow;
import fr.vbillard.tissusdeprincesseboot.utils.worflow.Workflow;

public enum ProjectStatus {

	BROUILLON("brouillon", BrouillonWorkFlow.class) {
		@Override
		public String toString() {
			return "brouillon";
		}
	},
	PANIFIE("planifé", PlanifieWorkFlow.class) {
		@Override
		public String toString() {
			return "planifé";
		}
	},
	EN_COURS("en cours", EnCoursWorkFlow.class) {
		@Override
		public String toString() {
			return "en cours";
		}
	},
	TERMINE("terminé", TermineWorkFlow.class) {
		@Override
		public String toString() {
			return "terminé";
		}
	};

	public final String label;

	public Workflow workflow;

	ProjectStatus(String label, Class<? extends Workflow> workflowClass) {
		this.label = label;
		try {
			this.workflow = workflowClass.getConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			this.workflow = null;
			e.printStackTrace();
		}
	}

	public String getLabel() {
		return this.label;
	}

	public static ProjectStatus getEnum(String code) {
		for (ProjectStatus e : ProjectStatus.values()) {
			if (e.label.equals(code))
				return e;
		}
		return null;
	}

	public static List<String> labels() {
		List<String> list = new ArrayList();
		for (ProjectStatus e : ProjectStatus.values()) {
			list.add(e.label);
		}
		return list;
	}

	public static String displayClassName() {
		return "Statut du projet";
	}
}
