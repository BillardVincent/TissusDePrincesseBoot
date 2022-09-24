package fr.vbillard.tissusdeprincesseboot.service.workflow;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import fr.vbillard.tissusdeprincesseboot.exception.InvalidWorkflowException;
import fr.vbillard.tissusdeprincesseboot.model.Projet;
import fr.vbillard.tissusdeprincesseboot.service.ProjetService;
import fr.vbillard.tissusdeprincesseboot.utils.model_to_string.Articles;
import fr.vbillard.tissusdeprincesseboot.utils.model_to_string.ModelUtils;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

/**
 * Utilise le Pattern Strategy: Ceci est la class abstraite regroupant les
 * methodes de base implémentées dans les stratégies concrètes
 * 
 * @author vbill
 *
 */
@Component
public abstract class Workflow {
	
	protected final static String NON_AUTORISE = "Non autorisé";

	/**
	 * Description du workflow
	 */
	protected String description;

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
	@Transactional
	public final void nextStep(Projet projet) {
		ErrorWarn errors = verifyNextStep();
		if (nextPossible && errors.getError().isEmpty() && warnAlert(errors.getWarn())) {
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
	protected abstract ErrorWarn verifyNextStep();

	/**
	 * Application de la stratégie à définir dans la stratégie
	 */
	protected abstract void doNextStep();

	// ------ partie "Cancel" -----------

	public final boolean isCancelPossible() {
		return cancelPossible;
	}

	@Transactional
	public final void cancel(Projet projet) {
		ErrorWarn errors = verifyNextStep();
		if (cancelPossible && errors.getError().isEmpty() && warnAlert(errors.getWarn())) {
			doCancel();
		} else {
			buildError(errors);
		}
	}

	protected abstract ErrorWarn verifyCancel();

	protected abstract void doCancel();

	// --------- suppression ----------
	
	public final boolean isDeletePossible() {
		return deletable;
	}

	@Transactional
	public final void delete(Projet projet) {
		ErrorWarn errors = verifyDelete();
		if (deletable && errors.getError().isEmpty() && warnAlert(errors.getWarn())) {
			doDelete();
		} else {
			buildError(errors);
		}
	}

	protected abstract ErrorWarn verifyDelete();

	protected abstract void doDelete();
	
	// --------- archive ----------
	
	public final boolean isArchivePossible() {
		return archivePossible;
	}
	
	@Transactional
	public final void archive(Projet projet) {
		ErrorWarn errors = verifyArchive();
		if (archivePossible && errors.getError().isEmpty() && warnAlert(errors.getWarn())) {
			doArchive();
		} else {
			buildError(errors);
		}
	}
	
	protected abstract ErrorWarn verifyArchive();
	
	protected abstract void doArchive();
		
	private boolean warnAlert(List<String> warn) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Attention");
		alert.setHeaderText("Nous avons relevé des problèmes potentiels");
		alert.setContentText(String.join(",\n", warn));

		return alert.showAndWait().orElse(ButtonType.CANCEL).equals(ButtonType.OK);
	}
		
	// -------- Autre -------------
	private void buildError (ErrorWarn errors) {
		throw new InvalidWorkflowException(
				"Vous ne pouvez pas passser à l'étape suivante. Corrigez les erreurs suivantes : "
						+ String.join(",\n", errors.getError()));
	}
	
	public String getDescription() {
		return description;
	}

}
