package fr.vbillard.tissusdeprincesseboot.controller.common;

import com.jfoenix.controls.JFXCheckBox;
import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IModalController;
import fr.vbillard.tissusdeprincesseboot.exception.NoSelectionException;
import fr.vbillard.tissusdeprincesseboot.utils.Constants;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CheckBoxChoiceController implements IModalController {

	@FXML
	private JFXCheckBox selectAll;

	protected StageInitializer initializer;
	private Stage dialogStage;
	private FxData result;

	@FXML
	public FlowPane content;

	/**
	 * return null if all selected. no selection impossible
	 */
	@FXML
	public void handleValidate() {
		boolean isAllSelectedEqualsNull = result.isAllSelectedEqualsNull();
		result = new FxData();
		List<String> list = content.getChildren().stream().filter(cb -> ((JFXCheckBox) cb).isSelected())
				.map(cb -> ((JFXCheckBox) cb).getText()).toList();
		if (isAllSelectedEqualsNull && list.isEmpty()) {
			throw new NoSelectionException();
		} else if (isAllSelectedEqualsNull || !areAllCheckBoxChecked()) {
			result.setListValues(list);
		}
		dialogStage.close();

	}

	@FXML
	public void handleCancel() {
		dialogStage.close();
	}

	@Override
	public void setStage(Stage dialogStage, FxData data) {
		result = data;
		this.dialogStage = dialogStage;
		for (String s : data.getListDataCBox()) {
			JFXCheckBox cb = new JFXCheckBox();
			cb.setCheckedColor(Constants.colorSecondary);

			cb.setText(s);
			if (CollectionUtils.isEmpty(data.getListValues()) && data.isAllSelectedEqualsNull()) {
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
		selectAll.setCheckedColor(Constants.colorSecondary);
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
		return result.isAllSelectedEqualsNull() && areAllCheckBoxChecked() ? new FxData() : result;
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
