package fr.vbillard.tissusdeprincesseboot.controller.projet;

import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.path.PathEnum;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.AbstractRequisDto;
import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;
import fr.vbillard.tissusdeprincesseboot.model.AbstractEntity;
import fr.vbillard.tissusdeprincesseboot.model.AbstractRequis;
import fr.vbillard.tissusdeprincesseboot.model.AbstractUsedEntity;
import fr.vbillard.tissusdeprincesseboot.model.FournitureUsed;
import fr.vbillard.tissusdeprincesseboot.model.enums.ProjectStatus;
import fr.vbillard.tissusdeprincesseboot.service.AbstractUsedService;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.util.List;

public abstract class ProjetEditListElementController <T extends AbstractRequisDto<W, X>,
		U extends AbstractUsedEntity<X>, V extends AbstractUsedService<U, X>, W extends AbstractRequis<X>,
		X extends AbstractEntity> implements IController {

	private static final List<ProjectStatus> lockedStatus = List.of(ProjectStatus.ABANDONNE, ProjectStatus.TERMINE, ProjectStatus.EN_COURS);

	@FXML
	protected HBox hbox;

	protected StageInitializer initializer;

	protected T dtoRequis;
	protected List<U> lstRequis;
	protected final V elementUsedService;
	protected FxData data;
	protected boolean lock;
	
	public boolean isLocked() {
		return lock;
	}

	ProjetEditListElementController(V elementUsedService) {
		this.elementUsedService = elementUsedService;
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

		for (U requis : lstRequis) {
			FxData subData = new FxData();
			subData.setParentController(this);
			setSubData(subData, requis);
			Pane tu;
			if(requis instanceof FournitureUsed){
				tu = getPane();
				((FournitureUsedCardComponent)tu).setStageInitializer(initializer, subData);
			} else {
				tu = initializer.displayPane(getPathEnumUsed(), subData);
			}
			hbox.getChildren().add(tu);
		}

		Pane plusCard = initializer.displayPane(PathEnum.PLUS_CARD, data);
		hbox.getChildren().add(plusCard);

	}

	protected abstract Pane getPane();

	protected abstract PathEnum getPathEnumUsed();

	protected abstract void setSubData(FxData subData, U tissu);

	protected abstract PathEnum getPathEnumRequis();

	public void initLock(){
		ProjectStatus status = ProjectStatus.getEnum(data.getProjet().getProjectStatus());
		lock = lockedStatus.contains(status);
	}
	
	public void setLock(boolean lock){
		this.lock = lock;
		setPane();
	}

	public void refresh() {
		lstRequis = refreshLst();
		initLock();
		setPane();		
	}

	protected abstract List<U> refreshLst();

	public abstract void displaySelected(FxData data);
}
