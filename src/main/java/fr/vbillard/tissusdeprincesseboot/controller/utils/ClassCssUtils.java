package fr.vbillard.tissusdeprincesseboot.controller.utils;

import javafx.scene.Node;

public class ClassCssUtils {

    public static final String TITLE_ACC_2 = "title-acc-2";
	public static final String CLICKABLE = "clickable";
	public static final String TITRE = "title-custom";
	public static final String GRID_CELL ="grid-cell";
	public static final String LEFT_COLUMN = "left-column";
	public static final String SELECTED = "mainmenu-element-selected";
	public static final String TITLE_ACC_1 = "title-acc-1";
	public static final String TITLE_MAIN_PANE = "title-main-pane";
	public static final String TITLE_ACC_3 = "title-acc-3";
	public static final String TITLE_PANE_CUSTOM= "title-pane-custom";
	public static final String LIGHT_BACKGROUND = "light-background";
	public static final String TEXT_ERROR = "text-error";
	public static final String BORDER_ERROR = "border-error";

    private ClassCssUtils() {
	}

	public static void setStyle(Node node, String cssClass, boolean add) {
		boolean nodeHasStyle = node.getStyleClass().contains(cssClass);
		if (add && !nodeHasStyle) {
			node.getStyleClass().add(cssClass);
		} else if (!add && nodeHasStyle) {
			node.getStyleClass().remove(cssClass);
		}
	}

}
