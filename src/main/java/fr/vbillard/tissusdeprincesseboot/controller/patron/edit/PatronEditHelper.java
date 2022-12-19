package fr.vbillard.tissusdeprincesseboot.controller.patron.edit;

import java.util.ArrayList;
import java.util.stream.Collectors;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import fr.vbillard.tissusdeprincesseboot.dtos_fx.AbstractRequisDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.AbstractVariantDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FxDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.PatronDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuRequisDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuVariantDto;
import fr.vbillard.tissusdeprincesseboot.fx_custom_element.GlyphIconUtil;
import fr.vbillard.tissusdeprincesseboot.model.AbstractEntity;
import fr.vbillard.tissusdeprincesseboot.model.AbstractRequis;
import fr.vbillard.tissusdeprincesseboot.model.AbstractUsedEntity;
import fr.vbillard.tissusdeprincesseboot.model.AbstractVariant;
import fr.vbillard.tissusdeprincesseboot.model.Patron;
import fr.vbillard.tissusdeprincesseboot.model.enums.GammePoids;
import fr.vbillard.tissusdeprincesseboot.service.AbstractRequisService;
import fr.vbillard.tissusdeprincesseboot.service.AbstractService;
import fr.vbillard.tissusdeprincesseboot.service.AbstractUsedService;
import fr.vbillard.tissusdeprincesseboot.service.AbstractVariantService;
import fr.vbillard.tissusdeprincesseboot.utils.FxUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public abstract class PatronEditHelper<T extends AbstractEntity, U extends AbstractVariant<T>, V extends AbstractRequis<T>, W extends AbstractUsedEntity<T>> {

	
	protected AbstractVariantService<U, T> servicevariant;
	protected AbstractRequisService<V, T> requisService;
	protected AbstractUsedService<W, T> usedService;
	protected AbstractService<T> service;
	
	private VBox tissuEtFournitureContainer;
	private GridPane tissusPatronListGrid;
	private VBox bottomRightVbox;
	
	private ObservableList<AbstractVariantDto<U, T>> tvList;
	
	protected PatronDto patron;

	
	public void setContainer(VBox tissuEtFournitureContainer, GridPane tissusPatronListGrid, VBox bottomRightVbox, PatronDto patron) {
		this.tissuEtFournitureContainer = tissuEtFournitureContainer;
		this.tissusPatronListGrid = tissusPatronListGrid;
		this.bottomRightVbox = bottomRightVbox;
		this.patron = patron;

	}
	
	
	public void displayTissuRequis(AbstractRequisDto<V, T> tissu) {

		tvList = FXCollections.observableArrayList(new ArrayList<AbstractVariantDto<U, T>>());

		Label titre = new Label("Tissus recommandés : ");

		GridPane topGrid = new GridPane();
		topGrid.setVgap(10);
		topGrid.setHgap(20);

		topGrid.setPadding(new Insets(10, 0, 10, 0));
		JFXButton validateBtn = new JFXButton("Valider");
		completeTopGrid(topGrid, tissu, validateBtn);

		JFXButton cancelBtn = new JFXButton("Annuler");
		cancelBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> tissuEtFournitureContainer.getChildren().clear());

		JFXButton deleteBtn = new JFXButton("Supprimer");
		deleteBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> deleteRequis(tissu));

		HBox hboxBtn = new HBox(validateBtn, cancelBtn, deleteBtn);
		hboxBtn.setSpacing(10);
		hboxBtn.setAlignment(Pos.CENTER);
		hboxBtn.setPadding(new Insets(20, 20, 20, 20));

		tissuEtFournitureContainer.getChildren().addAll(titre, topGrid, hboxBtn, bottomRightVbox);

		tvList = FXCollections.observableArrayList(AbstractVariantService.getVariantByRequis(tissu));

		//TODO !!!!!!!
		// tvList = FXCollections.observableArrayList(fournitureVariantService.getVariantByFournitureRequis
		// (tissu));

		loadBottomRightVbox(tissu);

	}
	
	protected abstract void completeTopGrid(GridPane topGrid, AbstractRequisDto<V, T> dto, JFXButton validateBtn);

	abstract void saveRequis(FxDto<V> requis, Patron patron);
	
	abstract void deleteRequis(FxDto<V> requis);
	
	void loadBottomRightVbox(AbstractRequis<T> requis){
		
	}
	
	/**
	 * Charge les tissusRequis, en fonction du patron sélectionné. tableau sous le
	 * patron : tissusRequis.toString() - boutons
	 */
	protected void loadTissuRequisForPatron() {
		tissusPatronListGrid.getChildren().clear();
		patron.setTissusRequis(requisService.getAllRequisByPatron(patron.getId()).stream()
				.map(tr -> mapper.map(tr, TissuRequisDto.class)).collect(Collectors.toList()));

		if (patron.getTissusRequisProperty() != null && patron.getTissusRequis() != null) {

			for (int i = 0; i < patron.getTissusRequis().size(); i++) {

				TissuRequisDto tissu = patron.getTissusRequis().get(i);

				JFXButton editButton = new JFXButton();
				editButton.setGraphic(GlyphIconUtil.editNormal());
				editButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> displayTissuRequis(tissu));

				JFXButton deleteButton = new JFXButton();
				deleteButton.setGraphic(GlyphIconUtil.suppressNormal());
				deleteButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> deleteTissuRequis(tissu));

				HBox hbox = new HBox(editButton, deleteButton);
				hbox.setSpacing(10);
				hbox.setAlignment(Pos.CENTER_LEFT);
				tissusPatronListGrid.add(new Label(tissu.toString()), 0, i);
				tissusPatronListGrid.add(hbox, 1, i);
			}
		}
	}
	
	
}
