package fr.vbillard.tissusdeprincesseboot.controller.components.aside.search;

import com.jfoenix.controls.JFXCheckBox;
import fr.vbillard.tissusdeprincesseboot.exception.NoSelectionException;
import fr.vbillard.tissusdeprincesseboot.utils.Constants;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class CaracChkBox extends Carac{
    private final VBox content = new VBox();
    private final JFXCheckBox selectAll = new JFXCheckBox();
    List<String> cboxList;

    boolean allSelectedEqualsNull;


    public CaracChkBox(String nom, List<String> cboxList, List<String> selectionDestination,
            boolean allSelectedEqualsNull) {
        super(nom);
        this.getChildren().add(content);
        this.allSelectedEqualsNull = allSelectedEqualsNull;
        this.cboxList = cboxList;

        for (String s : cboxList) {
            JFXCheckBox cb = new JFXCheckBox();
            cb.setCheckedColor(Constants.colorSecondary);

            cb.setText(s);
            if (CollectionUtils.isEmpty(selectionDestination) && allSelectedEqualsNull) {
                cb.setSelected(true);
            } else {
                cb.setSelected(selectionDestination.contains(s));
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

    public List<String> getSelection() {

        List<String> list = content.getChildren().stream().filter(cb -> ((JFXCheckBox) cb).isSelected())
                .map(cb -> ((JFXCheckBox) cb).getText()).toList();
        if (allSelectedEqualsNull && list.isEmpty()) {
            throw new NoSelectionException();
        } else if (allSelectedEqualsNull || !areAllCheckBoxChecked()) {
            list = cboxList;
        }
        return list;
    }


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
