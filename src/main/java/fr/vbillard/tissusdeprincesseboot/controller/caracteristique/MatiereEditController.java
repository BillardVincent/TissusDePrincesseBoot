package fr.vbillard.tissusdeprincesseboot.controller.caracteristique;

import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.model.Matiere;
import fr.vbillard.tissusdeprincesseboot.service.MatiereService;
import org.springframework.stereotype.Component;

@Component
public class MatiereEditController extends AbstractCaracteristiqueController {

	private final MatiereService matiereService;

	public MatiereEditController(MatiereService matiereService, StageInitializer mainApp) {
		this.matiereService = matiereService;
		this.mainApp = mainApp;
	}

	@Override
	protected void setElements() {
		allElements = matiereService.getAllMatieresValues();
	}

	@Override
	protected void delete() {
		matiereService.delete(editElement.getText());
	}

	@Override
	protected boolean validateSave() {
		return matiereService.validate(newElement.getText());
	}

	@Override
	protected boolean validateEdit() {
		return matiereService.validate(editElement.getText());
	}

	@Override
	protected String fieldAlreadyExists() {
		return "Matière déja existante";
	}

	@Override
	protected String thisField() {
		return "Cette Matière";
	}

	@Override
	protected void save() {
		matiereService.saveOrUpdate(new Matiere(newElement.getText()));
	}

	@Override
	protected void edit() {
		Matiere m = matiereService.findMatiere(editedElements);
		m.setValue(editElement.getText());
		matiereService.saveOrUpdate(m);
	}

}
