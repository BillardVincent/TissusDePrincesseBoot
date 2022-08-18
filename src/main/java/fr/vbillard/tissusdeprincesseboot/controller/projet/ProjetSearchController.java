package fr.vbillard.tissusdeprincesseboot.controller.projet;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;

import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.RootController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IController;
import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.ProjetSpecification;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.TissuSpecification;
import fr.vbillard.tissusdeprincesseboot.model.enums.ProjectStatus;
import fr.vbillard.tissusdeprincesseboot.utils.FxData;
import javafx.fxml.FXML;

@Component
public class ProjetSearchController implements IController {

	private static final String AUCUN_FILTRE = "Aucun filtre";
	public static final String CHOIX = "Choix";

	@FXML
	public JFXTextField referenceField;
	@FXML
	public JFXCheckBox etudeCBox;
	@FXML
	public JFXCheckBox planedCBox;
	@FXML
	public JFXCheckBox inProgressCBox;
	@FXML
	public JFXCheckBox finishedCBox;

	private RootController root;
	private StageInitializer initializer;

	private boolean okClicked = false;

	public ProjetSearchController(RootController root) {
		this.root = root;
	}

	@Override
	public void setStageInitializer(StageInitializer initializer, FxData data) {
		this.initializer = initializer;

		etudeCBox.setSelected(true);
		planedCBox.setSelected(true);
		inProgressCBox.setSelected(true);
		finishedCBox.setSelected(true);
	}

	@FXML
	private void initialize() {

	}

	public boolean isOkClicked() {
		return okClicked;
	}

	private void setData(FxData data) {
		etudeCBox.setSelected(true);
		planedCBox.setSelected(true);
		inProgressCBox.setSelected(true);
		finishedCBox.setSelected(true);
	}

	@FXML
	private void handleCancel() {
		FxData data = new FxData();
		data.setSpecification(new TissuSpecification());
		setData(data);
	}

	@FXML
	private void handleOk() {
		List<ProjectStatus> status = null;
		if (!etudeCBox.isSelected() && !planedCBox.isSelected() && !inProgressCBox.isSelected()
				&& !finishedCBox.isSelected()) {
			throw new IllegalData("Veuillez renseigner au moins un statut avant de lancer la recherche");
		}
		if (!(etudeCBox.isSelected() && planedCBox.isSelected() && inProgressCBox.isSelected()
				&& finishedCBox.isSelected())) {
			status = setStatusSpec(status, etudeCBox, ProjectStatus.BROUILLON);
			status = setStatusSpec(status, planedCBox, ProjectStatus.PLANIFIE);
			status = setStatusSpec(status, inProgressCBox, ProjectStatus.EN_COURS);
			status = setStatusSpec(status, finishedCBox, ProjectStatus.TERMINE);
		}

		ProjetSpecification projetSpec = ProjetSpecification.builder().projectStatus(status).build();

		root.displayProjets(projetSpec);
	}

	private List<ProjectStatus> setStatusSpec(List<ProjectStatus> lst, JFXCheckBox cbox, ProjectStatus status) {
		if (cbox.isSelected()) {
			if (lst == null) {
				lst = new ArrayList<ProjectStatus>();
			}
			lst.add(status);
		}
		return lst;
	}

}
