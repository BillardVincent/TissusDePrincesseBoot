package fr.vbillard.tissusdeprincesseboot.service.workflow;

import fr.vbillard.tissusdeprincesseboot.controller.utils.ShowAlert;
import fr.vbillard.tissusdeprincesseboot.exception.InvalidWorkflowException;
import fr.vbillard.tissusdeprincesseboot.exception.NotAllowed;
import fr.vbillard.tissusdeprincesseboot.model.Projet;
import fr.vbillard.tissusdeprincesseboot.service.ProjetService;
import javafx.scene.control.ButtonType;
import lombok.Getter;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Utilise le Pattern Strategy: Ceci est la class abstraite regroupant les
 * methodes de base implémentées dans les stratégies concrètes
 * 
 * @author vbill
 *
 */
public abstract class Workflow {

	protected static final String NON_AUTORISE = "Non autorisé";

	/**
	 * Description du workflow
	 */
	@Getter
	protected String description;

	protected ProjetService projetService;

	
	/**
	 * Le projet qui servira à évaluer les vérifications et qui pourra être modifié
	 * par les methodes doNext() ou doCancel(), des strategy, au cours de
	 * l'évolution du Workflow
	 */
	protected Projet projet;

	// ------ partie "Next" -----------

	public boolean isNextPossible(){
		return false;
	}

	/**
	 * Point d'entrée de la stratégie
	 * 
	 * @param projet
	 */
	@Transactional
	public void nextStep(Projet projet) {
		ErrorWarn errors = verifyNextStep();
		if (isNextPossible() && errors.getError().isEmpty() && warnAlert(errors.getWarn())) {
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
	protected ErrorWarn verifyNextStep() {
		return new ErrorWarn();
	}

	/**
	 * Application de la stratégie à définir dans la stratégie
	 */
	protected void doNextStep() {
		throw new NotAllowed();
	}

	// ------ partie "Cancel" -----------

	public boolean isCancelPossible(){
		return false;
	}
	
	@Transactional
	public void cancel(Projet projet) {
		ErrorWarn errors = verifyNextStep();
		if (isCancelPossible() && errors.getError().isEmpty() && warnAlert(errors.getWarn())) {
			doCancel();
		} else {
			buildError(errors);
		}
	}

	protected ErrorWarn verifyCancel() {
		return new ErrorWarn();
	}

	protected void doCancel(){
		throw new NotAllowed();
	}

	// --------- suppression ----------
	
	public boolean isDeletePossible(){
		return false;
	}

	@Transactional
	public void delete(Projet projet) {
		ErrorWarn errors = verifyDelete();
		if (isDeletePossible() && errors.getError().isEmpty() && warnAlert(errors.getWarn())) {
			doDelete();
		} else {
			buildError(errors);
		}
	}

	protected ErrorWarn verifyDelete(){
		return new ErrorWarn();
	}

	protected void doDelete(){
		throw new NotAllowed();
	}


	// --------- archive ----------
	
	public boolean isArchivePossible() {
		return false;
	}
	
	@Transactional
	public void archive(Projet projet) {
		ErrorWarn errors = verifyArchive();
		if (isArchivePossible() && errors.getError().isEmpty() && warnAlert(errors.getWarn())) {
			doArchive();
		} else {
			buildError(errors);
		}
	}
	
	protected ErrorWarn verifyArchive() {
		return new ErrorWarn();
	}
	
	protected void doArchive() {
		throw new NotAllowed();
	}
		
	private boolean warnAlert(List<String> warn) {
		if (CollectionUtils.isEmpty(warn)) {
			return true;
		}

		return ShowAlert.confirmation(null, "Attention", "Nous avons relevé des problèmes potentiels",
				String.join(",\n", warn)).orElse(ButtonType.CANCEL).equals(ButtonType.OK);
	}
		
	// -------- Autre -------------
	private void buildError (ErrorWarn errors) {
		throw new InvalidWorkflowException(
				"Vous ne pouvez pas passser à l'étape suivante. Corrigez les erreurs suivantes : "
						+ String.join(",\n", errors.getError()) + "\n Souhaitez vous quand même continuer?");
	}

	public void setProjet(Projet projet) {
		this.projet = projet;
	}
	
	protected ErrorWarn nonAutorise() {
		ErrorWarn errorwarn = new ErrorWarn();
		errorwarn.addError(NON_AUTORISE);
		return errorwarn;
	}

}
