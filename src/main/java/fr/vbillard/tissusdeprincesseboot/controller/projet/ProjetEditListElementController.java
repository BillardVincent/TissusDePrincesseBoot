package fr.vbillard.tissusdeprincesseboot.controller.projet;

import java.util.List;
import java.util.Objects;

import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IController;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.AbstractRequisDto;
import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;
import fr.vbillard.tissusdeprincesseboot.model.AbstractEntity;
import fr.vbillard.tissusdeprincesseboot.model.AbstractRequis;
import fr.vbillard.tissusdeprincesseboot.model.AbstractUsedEntity;
import fr.vbillard.tissusdeprincesseboot.model.enums.ProjectStatus;
import fr.vbillard.tissusdeprincesseboot.service.AbstractUsedService;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.controller.utils.path.PathEnum;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public abstract class ProjetEditListElementController <T extends AbstractRequisDto<W, X>,
		U extends AbstractUsedEntity<X>, V extends AbstractUsedService<U, X>, W extends AbstractRequis<X>,
		X extends AbstractEntity> implements IController {

	@FXML
	protected HBox hbox;

	protected StageInitializer initializer;

	protected T dtoRequis;
	protected List<U> lstTissus;
	protected final V tissuUsedService;
	protected FxData data;
	protected boolean lock;
	
	public boolean isLocked() {
		return lock;
	}

	public ProjetEditListElementController(V tissuUsedService) {
		this.tissuUsedService = tissuUsedService;
	}

	@Override
	public void setStageInitializer(StageInitializer initializer, FxData data) {
		this.initializer = initializer;
		if (data == null || requisFromData(data) == null || data.getProjet() == null) {
			throw new IllegalData();
		}
		this.data = data;
		dtoRequis = requisFromData(data);

		refresh();
	}

	protected abstract T requisFromData(FxData data);

	private void setPane() {
		hbox.getChildren().clear();
		data.setParentController(this);
		Pane tr = initializer.displayPane(getPathEnumRequis(), data);
		hbox.getChildren().add(tr);

		for (U tissu : lstTissus) {
			FxData subData = new FxData();
			subData.setParentController(this);
			setSubData(subData, tissu);
			Pane tu = initializer.displayPane(getPathEnumUsed(), subData);
			hbox.getChildren().add(tu);
		}

		Pane plusCard = initializer.displayPane(PathEnum.PLUS_CARD, data);
		hbox.getChildren().add(plusCard);

	}

	protected abstract PathEnum getPathEnumUsed();

	protected abstract void setSubData(FxData subData, U tissu);

	protected abstract PathEnum getPathEnumRequis();

	public void initLock(){
		ProjectStatus status = ProjectStatus.getEnum(data.getProjet().getProjectStatus());
        switch(Objects.requireNonNull(status)) {
        case ABANDONNE:
        case TERMINE:
        case EN_COURS:
        	lock = true;
        	break;
        case BROUILLON:
        case PLANIFIE:
        	lock = false;
        	break;
        }
	}
	
	public void setLock(boolean lock){
		this.lock = lock;
		setPane();
	}

	public void refresh() {
		lstTissus = refreshLst();
		initLock();
		setPane();		
	}

	protected abstract List<U> refreshLst();

	public abstract void displaySelected(FxData data);
}
