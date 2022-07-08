package fr.vbillard.tissusdeprincesseboot;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.controller.FxmlPathProperties;
import fr.vbillard.tissusdeprincesseboot.controller.IController;
import fr.vbillard.tissusdeprincesseboot.controller.IModalController;
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
import fr.vbillard.tissusdeprincesseboot.model.Preference;
import fr.vbillard.tissusdeprincesseboot.model.enums.ImageFormat;
import fr.vbillard.tissusdeprincesseboot.service.PreferenceService;
import fr.vbillard.tissusdeprincesseboot.service.TissuService;
import fr.vbillard.tissusdeprincesseboot.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.utils.History;
import fr.vbillard.tissusdeprincesseboot.utils.PathEnum;
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

	private final Image icon;
	private final ApplicationContext applicationContext;
	private Stage stage;
	private final FxmlPathProperties pathProperties;
	private final PreferenceService preferenceService;
	private static History history;
	private FxData fxData;
	RootController rootController;
	private TissuService tissuService;

	public StageInitializer(ApplicationContext applicationContext, FxmlPathProperties pathProperties,
			PreferenceService preferenceService, History history, FxData data, TissuService tissuService) {
		this.applicationContext = applicationContext;
		this.pathProperties = pathProperties;
		this.preferenceService = preferenceService;
		StageInitializer.history = history;
		this.fxData = data;
		icon = new Image("file:resources/images/cut-cloth-red.png");

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
			PathHolder holder = pathEnumToURL(path);
			loader.setLocation(holder.url);
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
			PathHolder holder = pathEnumToURL(path);

			loader.setLocation(holder.url);
			loader.setControllerFactory(applicationContext::getBean);

			Pane page = loader.load();

			Stage dialogStage = new Stage();
			dialogStage.getIcons().add(icon);
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

	private PathHolder pathEnumToURL(PathEnum pathEnum) throws IOException {
		switch (pathEnum) {
		case ROOT:
			return new PathHolder(pathProperties.getRoot2().getURL(), RootController.class);
		case TISSUS:
			return new PathHolder(pathProperties.getTissus2().getURL(), TissusController.class);
		case TISSUS_DETAILS:
			return new PathHolder(pathProperties.getTissuDetail().getURL(), TissuDetailController.class);
		case TISSUS_EDIT:
			return new PathHolder(pathProperties.getTissuEdit().getURL(), TissuEditController.class);
		case TISSUS_CARD:
			return new PathHolder(pathProperties.getTissuCard().getURL(), TissuCardController.class);
		case PROJET_LIST:
			return new PathHolder(pathProperties.getProjetList().getURL(), ProjetListController.class);
		case PROJET_EDIT:
			return new PathHolder(pathProperties.getProjetEdit().getURL(), ProjetEditController.class);
		case PROJET_CARD:
			return new PathHolder(pathProperties.getProjetCard().getURL(), ProjetCardController.class);
		case PATRON_LIST:
			return new PathHolder(pathProperties.getPatronList().getURL(), PatronListController.class);
		case PATRON_DETAILS:
			return new PathHolder(pathProperties.getPatronDetail().getURL(), PatronDetailController.class);
		case PATRON_EDIT:
			return new PathHolder(pathProperties.getPatronEdit().getURL(), PatronEditController.class);
		case PATRON_CARD:
			return new PathHolder(pathProperties.getPatronCard().getURL(), PatronCardController.class);
		case MATIERE:
			return new PathHolder(pathProperties.getMatiereEdit().getURL(), MatiereEditController.class);
		case TISSAGE:
			return new PathHolder(pathProperties.getTissageEdit().getURL(), TissageEditController.class);
		case LIST_ELEMENT:
			return new PathHolder(pathProperties.getListElement().getURL(), ListElementController.class);
		case TISSU_REQUIS:
			return new PathHolder(pathProperties.getTissuRequisCard().getURL(), TissuRequisCardController.class);
		case PROJET_EDIT_LIST_ELEMENT:
			return new PathHolder(pathProperties.getProjetEditListElement().getURL(),
					ProjetEditListElementController.class);
		case TISSU_USED_CARD:
			return new PathHolder(pathProperties.getTissuUsedCard().getURL(), TissuUsedCardController.class);
		case TISSU_REQUIS_SELECTED:
			return new PathHolder(pathProperties.getTissuRequisSelected().getURL(),
					TissuRequisSelectedController.class);
		case PLUS_CARD:
			return new PathHolder(pathProperties.getPlusCard().getURL(), PlusCardController.class);
		case SET_LONGUEUR:
			return new PathHolder(pathProperties.getLongueur().getURL(), SetLongueurDialogController.class);
		case WEB_URL:
			return new PathHolder(pathProperties.getUrl().getURL(), SetWebUrlDialogController.class);
		case PROJET_DETAILS:
			break;
		case TISSU_SEARCH:
			return new PathHolder(pathProperties.getTissuSearch().getURL(), TissuSearchController.class);
		case PROJET_SEARCH:
			return new PathHolder(pathProperties.getProjetSearch().getURL(), ProjetSearchController.class);
		case PATRON_SEARCH:
			return new PathHolder(pathProperties.getPatronSearch().getURL(), PatronSearchController.class);
		case CHECKBOX_CHOICE:
			return new PathHolder(pathProperties.getCheckBoxChoice().getURL(), CheckBoxChoiceController.class);
		case PREF:
			return new PathHolder(pathProperties.getPreference().getURL(), PreferenceController.class);
		default:
			break;

		}
		return null;
	}

	@AllArgsConstructor
	private class PathHolder {
		URL url;
		Class controller;
	}

}
