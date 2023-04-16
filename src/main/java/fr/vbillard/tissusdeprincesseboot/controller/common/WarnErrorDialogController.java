package fr.vbillard.tissusdeprincesseboot.controller.common;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.jfoenix.controls.JFXCheckBox;

import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IModalController;
import fr.vbillard.tissusdeprincesseboot.exception.NoSelectionException;
import fr.vbillard.tissusdeprincesseboot.utils.FxData;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

@Component
public class WarnErrorDialogController implements IModalController {


	// TODO A FAIRE !!!!!!!!!!!!!!!!!!!

	@FXML
	private JFXCheckBox selectAll;

	protected StageInitializer initializer;
	private Stage dialogStage;

	private FxData data;
	private FxData result;

	@FXML
	public FlowPane content;

	/**
	 * return null if all selected. no selection impossible
	 */
	@FXML
	public void handleValidate() {
		result = new FxData();
		List<String> list = content.getChildren().stream().filter(cb -> ((JFXCheckBox) cb).isSelected())
				.map(cb -> ((JFXCheckBox) cb).getText()).collect(Collectors.toList());
		if (list.isEmpty()) {
			throw new NoSelectionException();
		} else if (!areAllCheckBoxChecked()) {
			result.setListValues(list);
		}
		dialogStage.close();

	}

	@FXML
	public void handleCancel() {
		result = data;
		dialogStage.close();
	}

	@Override
	public void setStage(Stage dialogStage, FxData data) {
		this.dialogStage = dialogStage;
		for (String s : data.getListDataCBox()) {
			JFXCheckBox cb = new JFXCheckBox();
			cb.setText(s);
			if (data.getListValues() == null || data.getListValues().isEmpty()) {
				cb.setSelected(true);
			} else {
				cb.setSelected(data.getListValues().contains(s));
			}
			cb.selectedProperty()
					.addListener((ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal) -> {
						if (Boolean.TRUE.equals(newVal) && areAllCheckBoxChecked()) {
							selectAll.setSelected(true);
						} else if (Boolean.FALSE.equals(newVal)) {
							selectAll.setSelected(false);
						}
					});
			content.getChildren().add(cb);
		}

		selectAll.setSelected(areAllCheckBoxChecked());
		selectAll.selectedProperty()
				.addListener((ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal) -> {
					if (Boolean.TRUE.equals(newVal)) {
						for (Node cb : content.getChildren()) {
							((JFXCheckBox) cb).setSelected(true);
						}
					}
				});
	}

	@Override
	public FxData result() {
		return areAllCheckBoxChecked() ? null : result;
	}

	@FXML
	public void deselectAll() {
		for (Node cb : content.getChildren()) {
			((JFXCheckBox) cb).setSelected(false);
		}
		selectAll.setSelected(false);
	}

	public boolean areAllCheckBoxChecked() {
		for (Node cb : content.getChildren()) {
			if (!((JFXCheckBox) cb).isSelected()) {
				return false;
			}
		}
		return true;

	}

}
