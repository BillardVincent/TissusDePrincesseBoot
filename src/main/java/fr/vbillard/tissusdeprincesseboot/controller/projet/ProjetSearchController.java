package fr.vbillard.tissusdeprincesseboot.controller.projet;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.misc.RootController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxUtils;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IController;
import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.ProjetSpecification;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.TissuSpecification;
import fr.vbillard.tissusdeprincesseboot.model.enums.ProjectStatus;
import javafx.fxml.FXML;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

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

		List<ProjectStatus> lst = null;

		if (data != null && data.getSpecification() != null && data.getSpecification() instanceof ProjetSpecification) {
			ProjetSpecification spec = (ProjetSpecification) data.getSpecification();
			lst = spec.getProjectStatus();
		}

		if (CollectionUtils.isEmpty(lst)) {
			etudeCBox.setSelected(true);
			planedCBox.setSelected(true);
			inProgressCBox.setSelected(true);
			finishedCBox.setSelected(false);
		} else {
			etudeCBox.setSelected(lst.contains(ProjectStatus.BROUILLON));
			planedCBox.setSelected(lst.contains(ProjectStatus.PLANIFIE));
			inProgressCBox.setSelected(lst.contains(ProjectStatus.EN_COURS));
			finishedCBox.setSelected(lst.contains(ProjectStatus.TERMINE));
		}

		FxUtils.setToggleColor(etudeCBox,	planedCBox,	inProgressCBox, finishedCBox);
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

		status = setStatusSpec(status, etudeCBox, ProjectStatus.BROUILLON);
		status = setStatusSpec(status, planedCBox, ProjectStatus.PLANIFIE);
		status = setStatusSpec(status, inProgressCBox, ProjectStatus.EN_COURS);
		status = setStatusSpec(status, finishedCBox, ProjectStatus.TERMINE);

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
