package fr.vbillard.tissusdeprincesseboot.controlers;
import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuDto;
import fr.vbillard.tissusdeprincesseboot.services.PatronService;
import fr.vbillard.tissusdeprincesseboot.services.TissuService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

@Component
public class RootLayoutController {

    // Reference to the main application
    private StageInitializer mainApp;
    private final TissuService tissuService;
    private final PatronService patronService;
    private MainOverviewController tissuOverviewController;

    public RootLayoutController(TissuService tissuService, PatronService patronService) {
        this.tissuService = tissuService;
        this.patronService = patronService;
    }

    public void setMainApp(StageInitializer mainApp) {
        this.mainApp = mainApp;
    }


   
    @FXML
    private void handleSave() {
    	/*
        File tissuFile = Serializer.getFilePath();
        if (tissuFile != null) {
            Serializer.serialize(tissuFile);
        } else {
            handleSaveAs();
        }
        */
    }

    /**
     * Opens a FileChooser to let the user select a file to save to.
     */
    @FXML
    private void handleSaveAs() {
    	/*
		FileChooser fileChooser = new FileChooser();

		// Set extension filter
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
				 "JSON files (*.json)", "*.json");
		fileChooser.getExtensionFilters().add(extFilter);

		// Show save file dialog
		File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

		if (file != null) {
			// Make sure it has the correct extension
			if (!file.getPath().endsWith(".json")) {
				file = new File(file.getPath() + ".json");
			}
			Serializer.serialize(file);
		}
		*/
	}
    
    @FXML
    private void handleOpen() {
    	/*
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "JSON files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        if (file != null) {
           Serializer.Deserialize(file);
           tissuOverviewController.refreshList();
        }
        */
    }

    /**
     * Opens an about dialog.
     */
    @FXML
    private void handleAbout() {
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("Les Tissus de Princesse");
    	alert.setHeaderText("A Propos");
    	alert.setContentText("Développé par Vincent Billard");

    	alert.showAndWait();
    }
    /*
    @FXML
    private void handleNewMatiere() {
    	mainApp.showMatiereEditDialog();
    }
    
    @FXML
    private void handleNewTissu() {
    	mainApp.showTissuEditDialog(new TissuDto());
    }
    
    @FXML
    private void handleNewTissage() {
    	mainApp.showTissageEditDialog();
    }
    
    @FXML
    private void handleNewTypeTissu() {
    	mainApp.showTypeTissuEditDialog();
    }

     */

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        System.exit(0);
    }
}