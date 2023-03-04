package fr.vbillard.tissusdeprincesseboot.controller.patron.edit;

import java.util.ArrayList;
import java.util.List;

import com.jfoenix.controls.JFXButton;

import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FxDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.PatronDto;
import fr.vbillard.tissusdeprincesseboot.fx_custom_element.GlyphIconUtil;
import fr.vbillard.tissusdeprincesseboot.model.AbstractEntity;
import fr.vbillard.tissusdeprincesseboot.model.AbstractRequis;
import fr.vbillard.tissusdeprincesseboot.model.AbstractUsedEntity;
import fr.vbillard.tissusdeprincesseboot.model.AbstractVariant;
import fr.vbillard.tissusdeprincesseboot.service.AbstractRequisService;
import fr.vbillard.tissusdeprincesseboot.service.AbstractUsedService;
import fr.vbillard.tissusdeprincesseboot.service.AbstractVariantService;
import fr.vbillard.tissusdeprincesseboot.service.PatronService;
import fr.vbillard.tissusdeprincesseboot.utils.DevInProgressService;
import fr.vbillard.tissusdeprincesseboot.utils.model_to_string.EntityToString;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Getter;

public abstract class PatronEditHelper<E extends AbstractEntity, EV extends AbstractVariant<E>,
		ER extends AbstractRequis<E>, UE extends AbstractUsedEntity<E>, DTO extends FxDto<E>,
		DTOV extends FxDto<EV>, DTOR extends FxDto<ER>> {

	
	protected AbstractVariantService<EV, E, DTOV> variantService;
	protected AbstractRequisService<ER, E, DTOR> requisService;
	protected AbstractUsedService<UE, E> usedService;
	protected PatronService patronService;

	protected VBox tissuEtFournitureContainer;
	protected GridPane listGrid;

	protected ObservableList<DTOV> tvList;
	
	protected PatronDto patron;

	protected DTOV variantSelected;

	protected StageInitializer initializer;


	@Getter
	protected boolean editingVariant;
	
	public void setContainer(VBox tissuEtFournitureContainer, GridPane listGrid, PatronDto patron, StageInitializer initializer) {
		this.tissuEtFournitureContainer = tissuEtFournitureContainer;
		this.listGrid = listGrid;
		this.patron = patron;
		this.initializer = initializer;

	}
	
	public void displayRequis(DTOR requis) {

		tissuEtFournitureContainer.getChildren().clear();
		tvList = FXCollections.observableArrayList(new ArrayList<DTOV>());
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
		tvList = FXCollections.observableArrayList(variantService.getVariantDtoByRequis(requisService.convert(requis)));

		if (hasVariant()) {
			tissuEtFournitureContainer.getChildren().add(loadBottomRightVbox(requis));
		}

	}

	protected abstract boolean hasVariant();

	protected abstract Class<E> getEntityClass();

	protected abstract void completeTopGrid(GridPane topGrid, DTOR dto, JFXButton validateBtn);

	void saveRequis(DTOR requis, PatronDto patron){
		boolean edit = requis.getId() != 0;
		DTOR returned = requisService.createOrUpdate(requis, patron);
		if (!edit) {
			addToPatron(requis, patron);
			patronService.saveOrUpdate(patron);
		}
		loadRequisForPatron();
		displayRequis(returned);
	}

	protected abstract void addToPatron(DTOR requis, PatronDto patron);

	void deleteRequis(DTOR requis) {
		ER requisEntity = requisService.convert(requis);
		requisEntity.setPatron(patronService.convert(patron));
		requisService.delete(requisEntity);
		tissuEtFournitureContainer.getChildren().clear();
		loadRequisForPatron();
	}

	protected VBox loadBottomRightVbox(DTOR requis){

		VBox bottomRightVbox = new VBox();

		bottomRightVbox.getChildren().addAll(new Separator(Orientation.HORIZONTAL), new Label("Possibilités :"));

		if (tvList != null && tvList.size() > 0) {
			GridPane bottomGrid = new GridPane();
			bottomGrid.setPadding(new Insets(5, 0, 5, 0));
			bottomRightVbox.getChildren().add(bottomGrid);
			bottomGrid.getColumnConstraints().addAll(new ColumnConstraints(300), new ColumnConstraints(100));

			for (int i = 0; i < tvList.size(); i++) {
				DTOV tv = tvList.get(i);
				buildVariantDisplay(requis, bottomGrid, i, tv);
			}
		}

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

	private void buildVariantDisplay(DTOR requis, GridPane bottomGrid, int i, DTOV tv) {
		JFXButton editButton = new JFXButton();
		editButton.setGraphic(GlyphIconUtil.editNormal());
		editButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
			editingVariant = true;
			editVariant(tv);
		});

		JFXButton deleteButton = new JFXButton();
		deleteButton.setGraphic(GlyphIconUtil.suppressNormal());
		deleteButton.addEventHandler(MouseEvent.MOUSE_CLICKED,
				e -> deleteVariant(requis, tv));
		HBox btns = new HBox(editButton, deleteButton);
		btns.setAlignment(Pos.CENTER_RIGHT);
		btns.setSpacing(10);

		displayVariant(bottomGrid, tv, i);

		bottomGrid.add(btns, 1, i * 2);

		if (i != tvList.size() - 1) {
			HBox hbox = new HBox(new Label("-------------   OU   --------------  "));
			hbox.setAlignment(Pos.CENTER);
			bottomGrid.add(hbox, 0, i * 2 + 1, 2, 1);
			// displayTissuRequis(tissu);
		}
	}

	protected abstract void displayVariant(GridPane bottomGrid, DTOV tv, int index);

	protected void editVariant(DTOV tv){
		DevInProgressService.notImplemented();
	}

	protected void deleteVariant(DTOR requis, DTOV tv){
		variantService.delete(tv.getId());
		DTOR requisReloaded = requisService.getDtoById(requis.getId());
		displayRequis(requisReloaded);
	}

	/**
	 *
	 * @return une liste de requis contenus dans le patron
	 * ex : patron.getTissuRequis
	 */
	protected abstract List<DTOR> getListRequisFromPatron();

}
