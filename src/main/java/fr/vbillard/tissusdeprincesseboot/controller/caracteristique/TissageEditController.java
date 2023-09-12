package fr.vbillard.tissusdeprincesseboot.controller.caracteristique;

import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.model.Tissage;
import fr.vbillard.tissusdeprincesseboot.service.TissageService;
import org.springframework.stereotype.Component;

@Component
public class TissageEditController extends AbstractCaracteristiqueController {

	private TissageService tissageService;

	public TissageEditController(TissageService tissageService, StageInitializer mainApp) {
		this.tissageService = tissageService;
		this.mainApp = mainApp;
	}

	@Override
	protected void setElements() {
		allElements = tissageService.getAllObs();
	}


	@Override
	protected void delete() {
		tissageService.delete(editElement.getText());
	}

	@Override
	protected boolean validateSave() {
		return tissageService.validate(newElement.getText());
	}

	@Override
	protected boolean validateEdit() {
		return tissageService.validate(editElement.getText());
	}

	@Override
	protected String fieldAlreadyExists() {
		return "Tissage déjà existant";
	}

	@Override
	protected String thisField() {
		return "Ce tissage";
	}

	@Override
	protected void save() {
		tissageService.saveOrUpdate(new Tissage(newElement.getText()));
	}

	@Override
	protected void edit() {
		Tissage t = tissageService.findTissage(editedElements);
		t.setValue(editElement.getText());
		tissageService.saveOrUpdate(t);
	}

}
