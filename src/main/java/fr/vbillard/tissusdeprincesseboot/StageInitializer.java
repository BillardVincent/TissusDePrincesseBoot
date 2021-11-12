package fr.vbillard.tissusdeprincesseboot;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.controlers.IController;
import fr.vbillard.tissusdeprincesseboot.controlers_v2.CardWPicture;
import fr.vbillard.tissusdeprincesseboot.controlers_v2.RootController;
import fr.vbillard.tissusdeprincesseboot.controlers_v2.TissusController;
import fr.vbillard.tissusdeprincesseboot.dtosFx.PatronDto;
import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuDto;
import fr.vbillard.tissusdeprincesseboot.model.Photo;
import fr.vbillard.tissusdeprincesseboot.model.Preference;
import fr.vbillard.tissusdeprincesseboot.model.enums.ImageFormat;
import fr.vbillard.tissusdeprincesseboot.services.PreferenceService;
import fr.vbillard.tissusdeprincesseboot.controlers.FxmlPathProperties;
import fr.vbillard.tissusdeprincesseboot.controlers.GenericChoiceBoxEditController;
import fr.vbillard.tissusdeprincesseboot.controlers.GenericTextEditController;
import fr.vbillard.tissusdeprincesseboot.controlers.MatiereEditController;
import fr.vbillard.tissusdeprincesseboot.controlers.PatronEditDialogController;
import fr.vbillard.tissusdeprincesseboot.controlers.PictureExpended;
import fr.vbillard.tissusdeprincesseboot.controlers.SetLongueurDialogController;
import fr.vbillard.tissusdeprincesseboot.controlers.TissageEditController;
import fr.vbillard.tissusdeprincesseboot.controlers.TissuEditDialogController;
import fr.vbillard.tissusdeprincesseboot.controlers.TypeEditController;
import fr.vbillard.tissusdeprincesseboot.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.utils.History;
import fr.vbillard.tissusdeprincesseboot.utils.PathEnum;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
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


    public StageInitializer(ApplicationContext applicationContext, FxmlPathProperties pathProperties, PreferenceService preferenceService){
        this.applicationContext = applicationContext;
        this.pathProperties = pathProperties;
        this.preferenceService = preferenceService;
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

    public Pane displayPane(PathEnum path){
        FXMLLoader loader = new FXMLLoader();
        Pane layout = null;
        try {
            PathHolder holder = PathEnumToURL(path);
            loader.setLocation(holder.url);
            loader.setControllerFactory(applicationContext::getBean);
            layout = loader.load();

            IController controller = loader.getController();
            controller.setStageInitializer(this);

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
            case TISSUS_CARD:
                return new PathHolder(pathProperties.getTissuCard().getURL(), CardWPicture.class);
            default:
                return null;
        }
    }


    private class PathHolder{
        URL url;
        Class controller;

        public PathHolder(URL url, Class controller){
            this.url = url;
            this.controller = controller;
        }
    }
    /*

    public boolean showTissuEditDialog(TissuDto tissu) {
        try {
            ControlerHolder holder = setStageAndLoader(pathProperties.getTissuEditDialog().getURL(), "Modification Tissu");

            TissuEditDialogController controller = holder.loader.getController();
            controller.setDialogStage(holder.dialogStage);
            if (tissu != null)
               controller.setTissu(tissu,this);

            holder.dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showTissuEditDialog(Map<TissuDto, Integer> mapTissu) {
        try {
            ControlerHolder holder = setStageAndLoader(pathProperties.getTissuEditDialog().getURL(), "Validation de fin de projet");

            TissuEditDialogController controller = holder.loader.getController();
            controller.setDialogStage(holder.dialogStage);
            if (mapTissu != null)
                controller.setMapTissu(mapTissu,this);

            holder.dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showPatronEditDialog(PatronDto patron) {
        try {
            ControlerHolder holder = setStageAndLoader(pathProperties.getPatronEditDialog().getURL(), "Modification de patron");

            PatronEditDialogController controller = holder.loader.getController();
            controller.setDialogStage(holder.dialogStage);
            controller.setPatron(patron, this);

            holder.dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showMatiereEditDialog() {
        try {
            ControlerHolder holder = setStageAndLoader(pathProperties.getMatiereEdit().getURL(), "Matieres");

            MatiereEditController controller = holder.loader.getController();
            controller.setDialogStage(holder.dialogStage);
            controller.setData(this);

            holder.dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showTypeTissuEditDialog() {
        try {
            ControlerHolder holder = setStageAndLoader(pathProperties.getTypeEdit().getURL(), "Types de tissu");

            TypeEditController controller = holder.loader.getController();
            controller.setDialogStage(holder.dialogStage);
            controller.setData(this);

            holder.dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showTissageEditDialog() {
        try {
            ControlerHolder holder = setStageAndLoader(pathProperties.getTissageEdit().getURL(), "Tissages");

            TissageEditController controller = holder.loader.getController();
            controller.setDialogStage(holder.dialogStage);
            controller.setData(this);

            holder.dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String showTextEditDialog(String value, String fieldName) {
        try {
            ControlerHolder holder = setStageAndLoader(pathProperties.getGenericTextEdit().getURL(), "Modification");

            GenericTextEditController controller = holder.loader.getController();
            controller.setDialogStage(holder.dialogStage);
            controller.setData(this, value);

            holder.dialogStage.showAndWait();

            return controller.result();
        } catch (IOException e) {
            e.printStackTrace();
            return "Error";
        }
    }

    public <T extends Enum> String showChoiceBoxEditDialog(String value, Class<T> class1) {
        try {
            Class<?>[] parameterType = new Class[] {};
            String title = "Modification : "
                    + class1.getMethod("displayClassName", parameterType).invoke(null, new Object[] {});
            ControlerHolder holder = setStageAndLoader(pathProperties.getGenericChoiceBoxEdit().getURL(), title);

            GenericChoiceBoxEditController controller = holder.loader.getController();
            controller.setDialogStage(holder.dialogStage);
            controller.setData(this, value, class1);

            holder.dialogStage.showAndWait();

            return controller.result();
        } catch (IOException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
            return "Error";
        }
    }

    public int showSetLongueurDialog(int required, TissuDto available) {
        try {
            ControlerHolder holder = setStageAndLoader(pathProperties.getLongueur().getURL(), "Longueur de tissu à allouer");

            SetLongueurDialogController controller = holder.loader.getController();
            controller.setDialogStage(holder.dialogStage);
            controller.setData(this, required, available.getLongueur());

            holder.dialogStage.showAndWait();

            return controller.result();
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void showPictureExpended(Photo photo) {
        try {
            ControlerHolder holder = setStageAndLoader(pathProperties.getPictureExpended().getURL(), photo.getNom());

            PictureExpended controller = holder.loader.getController();
            controller.setDialogStage(holder.dialogStage);
            controller.setData(this, photo);

            holder.dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
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

        return fileChooser.showOpenDialog(getPrimaryStage());
    }
    */

    private ControlerHolder setStageAndLoader(URL path) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(path);
        loader.setControllerFactory(applicationContext::getBean);

        AnchorPane page = loader.load();

        Stage stage = new Stage();
        stage.getIcons().add(icon);

        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(getPrimaryStage());
        Scene scene = new Scene(page);
        stage.setScene(scene);

        return new ControlerHolder(stage, loader);
    }

    @AllArgsConstructor
    private class ControlerHolder{
        Stage dialogStage;
        FXMLLoader loader;
    }

}
