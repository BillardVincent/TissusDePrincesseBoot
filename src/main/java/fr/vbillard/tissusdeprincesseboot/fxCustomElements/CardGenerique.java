package fr.vbillard.tissusdeprincesseboot.fxCustomElements;

import fr.vbillard.tissusdeprincesseboot.model.Photo;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class CardGenerique {
	private int widthPx;
	private int heightPx;
	private String title;
	private String subTitle;
	private String description;
	private ButtonBar actions;
	private Photo image;
	public CardGenerique(int widthPx, int heightPx, String title, String subTitle, String description,
			 Photo image, Button... actions) {
		
		super();
		this.widthPx = widthPx;
		this.heightPx = heightPx;
		this.title = title;
		this.subTitle = subTitle;
		this.description = description;
		this.image = image;
		this.actions.getButtons().addAll(actions);
	}
	
	public Pane getCard() {
		Pane card = new Pane();
		card.setPrefHeight(heightPx);
		card.setPrefWidth(widthPx);
		
		VBox vbox = new VBox();
		
		return null;
	}
	
	
	
	

}
