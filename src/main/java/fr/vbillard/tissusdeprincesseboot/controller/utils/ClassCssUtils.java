package fr.vbillard.tissusdeprincesseboot.controller.utils;

import javafx.scene.Node;

public class ClassCssUtils {

	private ClassCssUtils() {
	}

	public static final String CLICKABLE = "clickable";
	public static final String TITRE = "title-custom";
	public static final String GRID_CELL ="grid-cell";
	public static final String LEFT_COLUMN = "left-column";
	public static final String SELECTED = "mainmenu-element-selected";
	

	public static void setStyle(Node node, String cssClass, boolean add) {
		boolean nodeHasStyle = node.getStyleClass().contains(cssClass);
		if (add && !nodeHasStyle) {
			node.getStyleClass().add(cssClass);
		} else if (!add && nodeHasStyle) {
			node.getStyleClass().remove(cssClass);
		}
	}

}
