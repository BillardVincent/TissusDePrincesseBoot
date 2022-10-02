package fr.vbillard.tissusdeprincesseboot;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.config.PathImgProperties;
import fr.vbillard.tissusdeprincesseboot.controller.PreferenceController;
import fr.vbillard.tissusdeprincesseboot.controller.RootController;
import fr.vbillard.tissusdeprincesseboot.controller.TissuRequisCardController;
import fr.vbillard.tissusdeprincesseboot.controller.TissuRequisSelectedController;
import fr.vbillard.tissusdeprincesseboot.controller.caracteristiques.MatiereEditController;
import fr.vbillard.tissusdeprincesseboot.controller.caracteristiques.TissageEditController;
import fr.vbillard.tissusdeprincesseboot.controller.common.CheckBoxChoiceController;
import fr.vbillard.tissusdeprincesseboot.controller.common.PlusCardController;
import fr.vbillard.tissusdeprincesseboot.controller.common.SetLongueurDialogController;
import fr.vbillard.tissusdeprincesseboot.controller.common.SetWebUrlDialogController;
import fr.vbillard.tissusdeprincesseboot.controller.patron.ListElementController;
import fr.vbillard.tissusdeprincesseboot.controller.patron.PatronCardController;
import fr.vbillard.tissusdeprincesseboot.controller.patron.PatronDetailController;
import fr.vbillard.tissusdeprincesseboot.controller.patron.PatronEditController;
import fr.vbillard.tissusdeprincesseboot.controller.patron.PatronListController;
import fr.vbillard.tissusdeprincesseboot.controller.patron.PatronSearchController;
import fr.vbillard.tissusdeprincesseboot.controller.projet.ProjetCardController;
import fr.vbillard.tissusdeprincesseboot.controller.projet.ProjetEditController;
import fr.vbillard.tissusdeprincesseboot.controller.projet.ProjetEditListElementController;
import fr.vbillard.tissusdeprincesseboot.controller.projet.ProjetListController;
import fr.vbillard.tissusdeprincesseboot.controller.projet.ProjetSearchController;
import fr.vbillard.tissusdeprincesseboot.controller.projet.TissuUsedCardController;
import fr.vbillard.tissusdeprincesseboot.controller.tissu.TissuCardController;
import fr.vbillard.tissusdeprincesseboot.controller.tissu.TissuDetailController;
import fr.vbillard.tissusdeprincesseboot.controller.tissu.TissuEditController;
import fr.vbillard.tissusdeprincesseboot.controller.tissu.TissuSearchController;
import fr.vbillard.tissusdeprincesseboot.controller.tissu.TissusController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxmlPathProperties;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IModalController;
import fr.vbillard.tissusdeprincesseboot.model.Preference;
import fr.vbillard.tissusdeprincesseboot.model.enums.ImageFormat;
import fr.vbillard.tissusdeprincesseboot.service.PreferenceService;
import fr.vbillard.tissusdeprincesseboot.service.TissuService;
import fr.vbillard.tissusdeprincesseboot.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.utils.History;
import fr.vbillard.tissusdeprincesseboot.utils.PathEnum;
import fr.vbillard.tissusdeprincesseboot.utils.path.PathHolder;
import fr.vbillard.tissusdeprincesseboot.utils.path.PathService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.AllArgsConstructor;

@Component
public class StageInitializer implements ApplicationListener<TissusDePrincesseFxApp.StageReadyEvent> {

	private final ApplicationContext applicationContext;
	private Stage stage;
	private PathImgProperties pathImgProperties; 
	private final FxmlPathProperties pathProperties;
	private final PathService pathService;
	private final PreferenceService preferenceService;
	private static History history;
	private FxData fxData;
	RootController rootController;

	public StageInitializer(ApplicationContext applicationContext, FxmlPathProperties pathProperties,
			PreferenceService preferenceService, History history, FxData data, TissuService tissuService,
			PathImgProperties pathImgProperties, PathService pathService) {
		this.applicationContext = applicationContext;
		this.pathProperties = pathProperties;
		this.preferenceService = preferenceService;
		StageInitializer.history = history;
		this.fxData = data;
		this.pathImgProperties = pathImgProperties;
		this.pathService = pathService;

		tissuService.batchTissuDisponible();
	}

	@Override
	public void onApplicationEvent(TissusDePrincesseFxApp.StageReadyEvent event) {
		try {
			FXMLLoader rootLoader = new FXMLLoader();
			rootLoader.setLocation(pathProperties.getRoot2().getURL());
			rootLoader.setControllerFactory(applicationContext::getBean);
			Parent rootLayout = rootLoader.load();

			rootController = rootLoader.getController();
			rootController.setStageInitializer(this, null);

			stage = event.getStage();
			Scene scene = new Scene(rootLayout);
			stage.setScene(scene);
			stage.setMaximized(true);
			stage.getIcons().add(new Image(pathImgProperties.getAppIcon().getURL().toString()));
			stage.show();


		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Stage getPrimaryStage() {
		return stage;
	}

	public FxData getData() {
		return fxData;
	}

	public RootController getRoot() {
		return rootController;
	}

	public Pane displayPane(PathEnum path) {
		return displayPane(path, null);
	}

	public Pane displayPane(PathEnum path, FxData data) {
		FXMLLoader loader = new FXMLLoader();
		Pane layout = null;
		try {
			PathHolder holder = pathService.pathEnumToURL(path);
			loader.setLocation(holder.getUrl());
			loader.setControllerFactory(applicationContext::getBean);
			layout = loader.load();

			IController controller = loader.getController();
			controller.setStageInitializer(this, data);
			// TODO Fil d'ariane ici

		} catch (IOException e) {
			e.printStackTrace();
		}
		return layout;
	}

	public FxData displayModale(PathEnum path, FxData data, String title) {
		try {

			FXMLLoader loader = new FXMLLoader();
			PathHolder holder = pathService.pathEnumToURL(path);

			loader.setLocation(holder.getUrl());
			loader.setControllerFactory(applicationContext::getBean);

			Pane page = loader.load();

			Stage dialogStage = new Stage();
			dialogStage.getIcons().add(new Image(pathImgProperties.getAppIcon().getURL().toString()));

			dialogStage.setTitle(title);

			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(getPrimaryStage());
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			IModalController controller = loader.getController();
			controller.setStage(dialogStage, data);

			dialogStage.showAndWait();

			return controller.result();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public File directoryChooser(Preference pref) {
		FileChooser fileChooser = new FileChooser();
		pref = preferenceService.getPreferences();
		String path = pref.getPictureLastUploadPath();

		fileChooser.setInitialDirectory(new File(path));

		ImageFormat[] values = ImageFormat.values();
		String[] extensions = new String[values.length];
		for (int i = 0; i < values.length; i++) {
			extensions[i] = values[i].getExtension();
		}
		fileChooser.getExtensionFilters()
				.add(new FileChooser.ExtensionFilter("Images (" + String.join(", ", extensions) + ")", extensions));

		return fileChooser.showOpenDialog(stage);
	}

}
