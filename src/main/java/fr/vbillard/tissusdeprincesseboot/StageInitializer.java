package fr.vbillard.tissusdeprincesseboot;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import fr.vbillard.tissusdeprincesseboot.controlers_v2.caracteristiques.MatiereEditController;
import fr.vbillard.tissusdeprincesseboot.controlers_v2.caracteristiques.TissageEditController;
import fr.vbillard.tissusdeprincesseboot.controlers_v2.caracteristiques.TypeEditController;
import fr.vbillard.tissusdeprincesseboot.controlers_v2.patron.PatronCardController;
import fr.vbillard.tissusdeprincesseboot.controlers_v2.patron.PatronDetailController;
import fr.vbillard.tissusdeprincesseboot.controlers_v2.patron.PatronEditController;
import fr.vbillard.tissusdeprincesseboot.controlers_v2.patron.PatronListController;
import fr.vbillard.tissusdeprincesseboot.controlers_v2.projet.ProjetCardController;
import fr.vbillard.tissusdeprincesseboot.controlers_v2.projet.ProjetDetailController;
import fr.vbillard.tissusdeprincesseboot.controlers_v2.projet.ProjetEditController;
import fr.vbillard.tissusdeprincesseboot.controlers_v2.projet.ProjetListController;
import fr.vbillard.tissusdeprincesseboot.controlers_v2.tissu.TissuCardController;
import fr.vbillard.tissusdeprincesseboot.controlers_v2.tissu.TissuDetailController;
import fr.vbillard.tissusdeprincesseboot.controlers_v2.tissu.TissuEditController;
import fr.vbillard.tissusdeprincesseboot.controlers_v2.tissu.TissusController;

import fr.vbillard.tissusdeprincesseboot.model.Preference;
import fr.vbillard.tissusdeprincesseboot.model.enums.ImageFormat;
import javafx.stage.FileChooser;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.controlers.IController;
import fr.vbillard.tissusdeprincesseboot.controlers_v2.ListElementController;
import fr.vbillard.tissusdeprincesseboot.controlers_v2.RootController;
import fr.vbillard.tissusdeprincesseboot.services.PreferenceService;
import fr.vbillard.tissusdeprincesseboot.controlers.FxmlPathProperties;
import fr.vbillard.tissusdeprincesseboot.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.utils.History;
import fr.vbillard.tissusdeprincesseboot.utils.PathEnum;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.AllArgsConstructor;

@Component
public class StageInitializer implements ApplicationListener<TissusDePrincesseFxApp.StageReadyEvent> {

    private final Image icon = new Image("file:resources/images/cut-cloth-red.png");
    private final ApplicationContext applicationContext;
    private Stage stage;
    private final FxmlPathProperties pathProperties;
    private final PreferenceService preferenceService;
    private static History history;
    private FxData fxData;

    public StageInitializer(ApplicationContext applicationContext, FxmlPathProperties pathProperties, PreferenceService preferenceService, History history, FxData data){
        this.applicationContext = applicationContext;
        this.pathProperties = pathProperties;
        this.preferenceService = preferenceService;
        StageInitializer.history = history;
        this.fxData = data;
    }

    @Override
    public void onApplicationEvent(TissusDePrincesseFxApp.StageReadyEvent event) {
        try {
            FXMLLoader rootLoader = new FXMLLoader();
            rootLoader.setLocation(pathProperties.getRoot2().getURL());
            rootLoader.setControllerFactory(applicationContext::getBean);
            Parent rootLayout = rootLoader.load();

            RootController rootController = rootLoader.getController();
            rootController.setStageInitializer(this);

            stage = event.getStage();
            Scene scene = new Scene(rootLayout);
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Stage getPrimaryStage(){
        return stage;
    }

    public FxData getData() {
        return fxData;
    }

    public Pane displayPane(PathEnum path, Object... data){
        FXMLLoader loader = new FXMLLoader();
        Pane layout = null;
        try {
            PathHolder holder = PathEnumToURL(path);
            loader.setLocation(holder.url);
            loader.setControllerFactory(applicationContext::getBean);
            layout = loader.load();

            IController controller = loader.getController();
            controller.setStageInitializer(this, data);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return layout;
    }

    private PathHolder PathEnumToURL(PathEnum pathEnum) throws IOException {
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
            case PROJET_DETAILS:
                return new PathHolder(pathProperties.getProjetDetail().getURL(), ProjetDetailController.class);
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
            case TYPE_TISSU:
                return new PathHolder(pathProperties.getTypeEdit().getURL(), TypeEditController.class);
            case TISSAGE:
                return new PathHolder(pathProperties.getTissageEdit().getURL(), TissageEditController.class);
            case LIST_ELEMENT:
            	return new PathHolder(pathProperties.getListElement().getURL(), ListElementController.class);
            default:
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

    @AllArgsConstructor
    private class PathHolder{
        URL url;
        Class controller;
    }

}
