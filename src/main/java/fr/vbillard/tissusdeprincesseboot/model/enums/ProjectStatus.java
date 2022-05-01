package fr.vbillard.tissusdeprincesseboot.model.enums;

import java.util.ArrayList;
import java.util.List;

public enum ProjectStatus {

	BROUILLON("brouillon") {
		@Override
		public String toString() {
			return "brouillon";
		}
	},
	PLANIFIE("planifié") {
		@Override
		public String toString() {
			return "planifié";
		}
	},
	EN_COURS("en cours") {
		@Override
		public String toString() {
			return "en cours";
		}
	},
	TERMINE("terminé") {
		@Override
		public String toString() {
			return "terminé";
		}
	};

	public final String label;

	ProjectStatus(String label) {
		this.label = label;
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
