package fr.vbillard.tissusdeprincesseboot.controller.rangement;

import com.jfoenix.controls.JFXTextField;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IModalController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class NewRangementModale implements IModalController {
    @FXML
    public Label warning;
    @FXML
    public JFXTextField champ;

    Stage dialogStage;
    private FxData result = null;

    @Override
    public FxData result() {
        return result;
    }

    @Override
    public void setStage(Stage dialogStage, FxData data) {
        this.dialogStage = dialogStage;
        warning.setVisible(false);
    }

    public void handleOk() {
        if (StringUtils.isNotBlank(champ.getText())) {
            result = new FxData();
            result.setNom(champ.getText());
            dialogStage.close();
        } else {
            warning.setVisible(true);
            warning.setText("Veuillez renseigner une valeur");
        }
    }

    public void handleCancel() {
        dialogStage.close();
    }
}
