package fr.vbillard.tissusdeprincesseboot.controller;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.TissusDePrincesseFxApp;
import fr.vbillard.tissusdeprincesseboot.config.PathImgProperties;
import fr.vbillard.tissusdeprincesseboot.controller.misc.RootController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.DisplayInventaireService;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxmlPathProperties;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IModalController;
import fr.vbillard.tissusdeprincesseboot.model.Preference;
import fr.vbillard.tissusdeprincesseboot.model.enums.ImageFormat;
import fr.vbillard.tissusdeprincesseboot.service.PreferenceService;
import fr.vbillard.tissusdeprincesseboot.service.TissuService;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.utils.History;
import fr.vbillard.tissusdeprincesseboot.controller.utils.path.PathEnum;
import fr.vbillard.tissusdeprincesseboot.controller.utils.path.PathHolder;
import fr.vbillard.tissusdeprincesseboot.controller.utils.path.PathService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

@Component
public class StageInitializer implements ApplicationListener<TissusDePrincesseFxApp.StageReadyEvent> {

	private final ApplicationContext applicationContext;
	private Stage stage;
	private final PathImgProperties pathImgProperties;
	private final FxmlPathProperties pathProperties;
	private final PathService pathService;
	private final PreferenceService preferenceService;
	private static History history;
	private FxData fxData;
	private RootController rootController;
	private final DisplayInventaireService displayInventaireService;

	private static final Logger LOGGER = LogManager.getLogger(StageInitializer.class);


	public StageInitializer(ApplicationContext applicationContext, FxmlPathProperties pathProperties,
			PreferenceService preferenceService, History history, FxData data, TissuService tissuService,
			PathImgProperties pathImgProperties, DisplayInventaireService displayInventaireService, PathService pathService) {
		this.applicationContext = applicationContext;
		this.pathProperties = pathProperties;
		this.preferenceService = preferenceService;
		StageInitializer.history = history;
		this.fxData = data;
		this.pathImgProperties = pathImgProperties;
		this.pathService = pathService;
		this.displayInventaireService = displayInventaireService;

		tissuService.batchTissuDisponible();

	}
	
	@Override
	public void onApplicationEvent(TissusDePrincesseFxApp.StageReadyEvent event) {
		try {
			FXMLLoader rootLoader = new FXMLLoader();
			rootLoader.setLocation(pathProperties.getRoot().getURL());
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
			displayInventaireService.batchInventaire(this);


		} catch (IOException e) {
			LOGGER.error(e);
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
			LOGGER.error(e);
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
			LOGGER.error(e);
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
