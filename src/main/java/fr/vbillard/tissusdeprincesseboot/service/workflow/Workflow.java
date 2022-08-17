package fr.vbillard.tissusdeprincesseboot.service.workflow;

import java.util.List;

import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.exception.InvalidWorkflowException;
import fr.vbillard.tissusdeprincesseboot.model.Projet;
import fr.vbillard.tissusdeprincesseboot.service.ProjetService;

/**
 * Utilise le Pattern Strategy: Ceci est la class abstraite regroupant les
 * methodes de base implémentées dans les stratégies concrètes
 * 
 * @author vbill
 *
 */
@Component
public abstract class Workflow {

	protected ProjetService projetService;
	/**
	 * défini si une étape suivante existe
	 */
	protected boolean nextPossible;
	/**
	 * défini si une étape précédente existe
	 */
	protected boolean cancelPossible;

	
	protected boolean deletable;
	protected boolean archivePossible;
	
	/**
	 * Le projet qui servira à évaluer les vérifications et qui pourra être modifié
	 * par les methodes doNext() ou doCancel(), des strategy, au cours de
	 * l'évolution du Workflow
	 */
	protected Projet projet;

	protected Workflow(ProjetService projetService) {
		this.projetService = projetService;
	}

	// ------ partie "Next" -----------

	public final boolean isNextPossible() {
		return nextPossible;
	}

	/**
	 * Point d'entrée de la stratégie
	 * 
	 * @param projet
	 */
	public final void nextStep(Projet projet) {
		List<String> errors = verifyNextStep();
		if (nextPossible && errors.isEmpty()) {
			this.projet = projet;
			doNextStep();
		} else {
			buildError(errors);
		}
	}

	/**
	 * Vérification de la possibilité d'avancer dans le Workflow. Défini dans la
	 * stratégie.
	 * 
	 * @return une liste d'erreurs sous forme de String
	 */
	protected abstract List<String> verifyNextStep();

	/**
	 * Application de la stratégie à définir dans la stratégie
	 */
	protected abstract void doNextStep();

	// ------ partie "Cancel" -----------

	public final boolean isCancelPossible() {
		return cancelPossible;
	}

	public final void cancel(Projet projet) {
		List<String> errors = verifyNextStep();
		if (cancelPossible && errors.isEmpty()) {
			doCancel();
		} else {
			buildError(errors);
		}
	}

	protected abstract List<String> verifyCancel();

	protected abstract void doCancel();

	// --------- suppression ----------
	
	public final boolean isDeletePossible() {
		return deletable;
	}

	public final void delete(Projet projet) {
		List<String> errors = verifyDelete();
		if (deletable && errors.isEmpty()) {
			doDelete();
		} else {
			buildError(errors);
		}
	}

	protected abstract List<String> verifyDelete();

	protected abstract void doDelete();
	
	// --------- archive ----------
	
		public final boolean isArchivePossible() {
			return archivePossible;
		}

		public final void archive(Projet projet) {
			List<String> errors = verifyArchive();
			if (archivePossible && errors.isEmpty()) {
				doArchive();
			} else {
				buildError(errors);
			}
		}

		protected abstract List<String> verifyArchive();

		protected abstract void doArchive();
	
	
	// -------- Autre -------------
	private void buildError(List<String> errors) {
		throw new InvalidWorkflowException(
				"Vous ne pouvez pas passser à l'étape suivante. Corrigez les erreurs suivantes : "
						+ String.join(", ", errors));
	}

}
