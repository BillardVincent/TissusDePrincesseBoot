package fr.vbillard.tissusdeprincesseboot.controller.caracteristique;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IModalController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.ShowAlert;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;

public abstract class AbstractCaracteristiqueController implements IModalController {

    @FXML
    protected JFXListView<String> listElement;
    @FXML
    protected JFXTextField newElement;
    @FXML
    protected JFXTextField editElement;
    @FXML
    protected JFXButton ajouterButton;
    @FXML
    protected JFXButton editerButton;
    @FXML
    protected JFXButton supprimerButton;
    @FXML
    protected JFXButton fermerButton;
    @FXML
    protected JFXButton cancelButton;

    protected String editedElements;

    protected ObservableList<String> allElements;

    protected static final String PAS_DE_VALEUR = "Pas de valeur";

    protected Stage dialogStage;
    protected boolean okClicked = false;
    protected StageInitializer mainApp;

    protected boolean fieldSaveEmpty() {
        return StringUtils.isEmpty(newElement.getText());
    }

    protected boolean fieldEditEmpty() {
        return StringUtils.isEmpty(editElement.getText());
    }

    protected abstract boolean validateSave();

    protected abstract boolean validateEdit();

    protected abstract String fieldAlreadyExists();

    protected abstract String thisField();

    protected abstract void save();

    protected abstract void edit();

    public void handleAddElement() {
        handleSaveOrEdit(true);

    }

    private void handleSaveOrEdit(boolean isSave) {
        if ((isSave && fieldSaveEmpty()) || (!isSave && fieldEditEmpty())) {
            ShowAlert.warn(mainApp.getPrimaryStage(), PAS_DE_VALEUR, PAS_DE_VALEUR, "Veuillez remplir une valeur");
        } else if (isSave && validateSave()) {
            save();
            newElement.setText(Strings.EMPTY);
            setElements();
            listElement.setItems(allElements);
        } else if (!isSave && validateEdit()) {
            edit();
            editElement.setText(Strings.EMPTY);
            setElements();
            listElement.setItems(allElements);
        } else {
            ShowAlert.warn(mainApp.getPrimaryStage(), "Duplicat", fieldAlreadyExists(), thisField() + " existe déjà");
        }
        setButton();
    }

    public void handleEditElement() {
        handleSaveOrEdit(false);
    }

    public void handleClose() {
        okClicked = true;
        dialogStage.close();
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @Override
    public FxData result() {
        return null;
    }

    @Override
    public void setStage(Stage dialogStage, FxData data) {
        this.dialogStage = dialogStage;

    }

    @FXML
    protected void initialize() {
        setElements();
        listElement.setItems(allElements);
        listElement.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> handleSelectElement(newValue));

        newElement.textProperty().addListener((observable, oldValue, newValue) -> setButton());

    }

    protected abstract void setElements();

    public void handleSelectElement(String element) {
        editedElements = element;
        editElement.setText(element);
        newElement.setText(Strings.EMPTY);
        setButton();
    }

    public void handleSuppressElement() {
        delete();
        listElement.setItems(allElements);
        editedElements = null;
        editElement.setText(Strings.EMPTY);
        setButton();

    }

    protected abstract void delete();

    @FXML
    private void handleCancelSelection() {
        newElement.setText(Strings.EMPTY);
        editElement.setText(Strings.EMPTY);
        setButton();
    }

    protected void setButton() {
        boolean isEditing = !editElement.getText().isEmpty();
        boolean hasNewContent = !newElement.getText().isEmpty();

        editElement.setDisable(!isEditing);
        editerButton.setDisable(!isEditing);
        newElement.setDisable(isEditing);
        ajouterButton.setDisable(isEditing || !hasNewContent);
        cancelButton.setDisable(!isEditing && !hasNewContent);
        supprimerButton.setDisable(!isEditing);
    }
}
