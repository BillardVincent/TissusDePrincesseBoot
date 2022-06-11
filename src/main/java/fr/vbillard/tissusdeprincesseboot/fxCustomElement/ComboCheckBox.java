package fr.vbillard.tissusdeprincesseboot.fxCustomElement;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListCell;
import com.jfoenix.converters.base.NodeConverter;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.util.StringConverter;

public class ComboCheckBox<T> extends JFXComboBox<ComboCheckBoxItem<T>> {
	private static final String DEFAULT_STYLE_CLASS = "jfx-combo-box";

	public ComboCheckBox(ObservableList<ComboCheckBoxItem<T>> items) {
		super(items);
		initialize();
	}

	private void initialize() {
		/*
		 * getStyleClass().add(DEFAULT_STYLE_CLASS); this.setCellFactory(listView -> new
		 * JFXListCell<ComboCheckBoxItem<T>>() {
		 * 
		 * @Override public void updateItem(ComboCheckBoxItem<T> item, boolean empty) {
		 * super.updateItem(item, empty); updateDisplayText(this, item, empty); } });
		 * 
		 * this.setButtonCell(new ListCell<T>() { {
		 * ComboCheckBox.this.valueProperty().addListener(observable -> { if
		 * (ComboCheckBox.this.getValue() == null) { updateItem(null, true); } }); }
		 * 
		 * @Override protected void updateItem(T item, boolean empty) {
		 * updateDisplayText(this, item, empty); this.setVisible(item != null ||
		 * !empty); }
		 * 
		 * }); }
		 * 
		 * private boolean updateDisplayText(ListCell<T> cell, T item, boolean empty) {
		 * if (empty) {
		 * 
		 * if (cell == null) { return true; } cell.setGraphic(null); cell.setText(null);
		 * return true; } else if (item instanceof Node) { Node currentNode =
		 * cell.getGraphic(); Node newNode = (Node) item; NodeConverter<T> nc =
		 * this.getNodeConverter(); Node node = nc == null ? null : nc.toNode(item); if
		 * (currentNode == null || !currentNode.equals(newNode)) { cell.setText(null);
		 * cell.setGraphic(node == null ? newNode : node); } return node == null; } else
		 * { StringConverter<T> c = this.getConverter(); String s = item == null ?
		 * this.getPromptText() : (c == null ? item.toString() : c.toString(item));
		 * cell.setText(s); cell.setGraphic(null); return s == null || s.isEmpty(); }
		 */
	}

}
