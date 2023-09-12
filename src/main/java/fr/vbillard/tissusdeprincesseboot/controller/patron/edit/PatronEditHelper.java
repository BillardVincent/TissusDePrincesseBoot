package fr.vbillard.tissusdeprincesseboot.controller.patron.edit;

import com.jfoenix.controls.JFXButton;
import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.utils.fx_custom_element.GlyphIconUtil;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FxDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.PatronDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.PatronVersionDto;
import fr.vbillard.tissusdeprincesseboot.model.AbstractEntity;
import fr.vbillard.tissusdeprincesseboot.model.AbstractRequis;
import fr.vbillard.tissusdeprincesseboot.model.AbstractUsedEntity;
import fr.vbillard.tissusdeprincesseboot.service.AbstractRequisService;
import fr.vbillard.tissusdeprincesseboot.service.AbstractUsedService;
import fr.vbillard.tissusdeprincesseboot.service.PatronService;
import fr.vbillard.tissusdeprincesseboot.utils.model_to_string.EntityToString;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Getter;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public abstract class PatronEditHelper<E extends AbstractEntity, ER extends AbstractRequis<E>,
		UE extends AbstractUsedEntity<E>, DTO extends FxDto<E>, DTOR extends FxDto<ER>> {

	
	protected AbstractRequisService<ER, E, DTOR> requisService;
	protected AbstractUsedService<UE, E> usedService;
	protected PatronService patronService;

	protected VBox tissuEtFournitureContainer;
	protected GridPane listGrid;

	protected PatronDto patron;

	protected StageInitializer initializer;


	@Getter
	protected boolean editingVariant;
	private String labelText;

	public void setContainer(VBox tissuEtFournitureContainer, GridPane listGrid, PatronDto patron, StageInitializer initializer) {
		this.tissuEtFournitureContainer = tissuEtFournitureContainer;
		this.listGrid = listGrid;
		this.patron = patron;
		this.initializer = initializer;

	}
	
	public void displayRequis(DTOR requis) {

		tissuEtFournitureContainer.getChildren().clear();
		String labelText = EntityToString.getByEntity(getEntityClass()).getLabel() + " recommandés : ";

		tissuEtFournitureContainer.getChildren().add(new Label(labelText));

		GridPane topGrid = new GridPane();
		topGrid.setVgap(10);
		topGrid.setHgap(20);

		topGrid.setPadding(new Insets(10, 0, 10, 0));
		tissuEtFournitureContainer.getChildren().add(topGrid);

		JFXButton validateBtn = new JFXButton("Valider");
		
		completeTopGrid(topGrid, requis, validateBtn);

		JFXButton cancelBtn = new JFXButton("Annuler");
		cancelBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> tissuEtFournitureContainer.getChildren().clear());

		JFXButton deleteBtn = new JFXButton("Supprimer");
		deleteBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> deleteRequis(requis));

		HBox hboxBtn = new HBox(validateBtn, cancelBtn, deleteBtn);
		hboxBtn.setSpacing(10);
		hboxBtn.setAlignment(Pos.CENTER);
		hboxBtn.setPadding(new Insets(20, 20, 20, 20));

		tissuEtFournitureContainer.getChildren().add(hboxBtn);

		if (hasVariant()) {
			tissuEtFournitureContainer.getChildren().add(loadBottomRightVbox(requis));
		}

	}

	protected abstract boolean hasVariant();

	protected abstract Class<E> getEntityClass();

	protected abstract void completeTopGrid(GridPane topGrid, DTOR dto, JFXButton validateBtn);

	@Transactional
	public void saveRequis(DTOR requis, PatronDto patron){
		boolean edit = requis.getId() != 0;
		patron = patronService.saveOrUpdate(patron);
		PatronVersionDto version = new PatronVersionDto();
		DTOR returned = requisService.createOrUpdate(requis, version);
		if (!edit) {
			addToPatron(requis, patron);
			patron= patronService.saveOrUpdate(patron);
		}
		loadRequisForPatron();
		displayRequis(returned);
	}

	protected abstract void addToPatron(DTOR requis, PatronDto patron);

	void deleteRequis(DTOR requis) {
		ER requisEntity = requisService.convert(requis);
		//requisEntity.setVersion(patronService.convert(patron));
		requisService.delete(requisEntity);
		tissuEtFournitureContainer.getChildren().clear();
		loadRequisForPatron();
	}

	protected VBox loadBottomRightVbox(DTOR requis){

		VBox bottomRightVbox = new VBox();

		bottomRightVbox.getChildren().addAll(new Separator(Orientation.HORIZONTAL), new Label("Possibilités :"));

		if (requis != null && requis.getId() != 0) {

			JFXButton addTvBtn = new JFXButton();
			addTvBtn.setGraphic(GlyphIconUtil.plusCircleNormal());

			HBox hboxTissuVariant = completeLoadBottomRightVBox(addTvBtn, requis);

			hboxTissuVariant.setSpacing(10);
			hboxTissuVariant.setAlignment(Pos.CENTER);
			hboxTissuVariant.setPadding(new Insets(20, 20, 20, 20));
			bottomRightVbox.getChildren().addAll(new Separator(Orientation.HORIZONTAL), hboxTissuVariant);

		}

		return bottomRightVbox;
	}

	protected abstract HBox completeLoadBottomRightVBox(JFXButton addTvBtn, DTOR requis);

	protected abstract void setRequisToPatron();
	
	/**
	 * Charge les tissusRequis, en fonction du patron sélectionné. tableau sous le
	 * patron : tissusRequis.toString() - boutons
	 */
	protected void loadRequisForPatron() {
		listGrid.getChildren().clear();

		setRequisToPatron();

		List<DTOR> lstRequis = getListRequisFromPatron();

			for (int i = 0; i < lstRequis.size(); i++) {

				DTOR requis = lstRequis.get(i);

				JFXButton editButton = new JFXButton();
				editButton.setGraphic(GlyphIconUtil.editNormal());
				editButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> displayRequis(requis));

				JFXButton deleteButton = new JFXButton();
				deleteButton.setGraphic(GlyphIconUtil.suppressNormal());
				deleteButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> deleteRequis(requis));

				HBox hbox = new HBox(editButton, deleteButton);
				hbox.setSpacing(10);
				hbox.setAlignment(Pos.CENTER_LEFT);
				listGrid.add(new Label(requis.toString()), 0, i);
				listGrid.add(hbox, 1, i);
			}

	}


	public void setPatron(PatronDto patron){
		this.patron = patron;
	}

	/**
	 *
	 * @return une liste de requis contenus dans le patron
	 * ex : patron.getTissuRequis
	 */
	protected abstract List<DTOR> getListRequisFromPatron();

}
