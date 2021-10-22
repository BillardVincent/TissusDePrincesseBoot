package fr.vbillard.tissusdeprincesseboot;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.dtosFx.PatronDto;
import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuDto;
import fr.vbillard.tissusdeprincesseboot.model.Photo;
import fr.vbillard.tissusdeprincesseboot.model.Preference;
import fr.vbillard.tissusdeprincesseboot.model.enums.ImageFormat;
import fr.vbillard.tissusdeprincesseboot.services.PreferenceService;
import fr.vbillard.tissusdeprincesseboot.controlers.FxmlPathProperties;
import fr.vbillard.tissusdeprincesseboot.controlers.GenericChoiceBoxEditController;
import fr.vbillard.tissusdeprincesseboot.controlers.GenericTextEditController;
import fr.vbillard.tissusdeprincesseboot.controlers.MainOverviewController;
import fr.vbillard.tissusdeprincesseboot.controlers.MatiereEditController;
import fr.vbillard.tissusdeprincesseboot.controlers.PatronEditDialogController;
import fr.vbillard.tissusdeprincesseboot.controlers.PictureExpended;
import fr.vbillard.tissusdeprincesseboot.controlers.RootLayoutController;
import fr.vbillard.tissusdeprincesseboot.controlers.SetLongueurDialogController;
import fr.vbillard.tissusdeprincesseboot.controlers.TissageEditController;
import fr.vbillard.tissusdeprincesseboot.controlers.TissuEditDialogController;
import fr.vbillard.tissusdeprincesseboot.controlers.TypeEditController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import lombok.AllArgsConstructor;

@Component
public class StageInitializer implements ApplicationListener<TissusDePrincesseFxApp.StageReadyEvent> {

    private Image icon = new Image("file:resources/images/cut-cloth-red.png");
    private ApplicationContext applicationContext;
    private Stage stage;
    private JMetro jMetro;
    private final FxmlPathProperties pathProperties;
    private final PreferenceService preferenceService;

    public StageInitializer(ApplicationContext applicationContext, FxmlPathProperties pathProperties, PreferenceService preferenceService){
        this.applicationContext = applicationContext;
        this.pathProperties = pathProperties;
        this.preferenceService = preferenceService;
    }

    @Override
    public void onApplicationEvent(TissusDePrincesseFxApp.StageReadyEvent event) {
        try {
            jMetro = new JMetro(Style.LIGHT);

            FXMLLoader loader = new FXMLLoader();
            loader.setControllerFactory(aClass -> applicationContext.getBean(aClass));
            loader.setLocation(pathProperties.getMainOverview().getURL());
            AnchorPane tissuOverview = (AnchorPane) loader.load();

            MainOverviewController tissuOverviewController = loader.getController();
            tissuOverviewController.setMainApp(this);

            FXMLLoader rootLoader = new FXMLLoader();
            rootLoader.setLocation(pathProperties.getRoot().getURL());
            rootLoader.setControllerFactory(aClass -> applicationContext.getBean(aClass));

            BorderPane rootLayout = (BorderPane) rootLoader.load();
            RootLayoutController rootLayoutController = rootLoader.getController();
            rootLayoutController.setMainApp(this);

            stage = event.getStage();
            Scene scene = new Scene(rootLayout);
            jMetro.setScene(scene);

            stage.setScene(scene);
            stage.getIcons().add(icon);
            stage.setMaximized(true);
            stage.show();

            rootLayout.setCenter(tissuOverview);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Stage getPrimaryStage(){
        return stage;
    }

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
                    + (String) class1.getMethod("displayClassName", parameterType).invoke(null, new Object[] {});
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



    private ControlerHolder setStageAndLoader(URL path, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(path);
        loader.setControllerFactory(aClass -> applicationContext.getBean(aClass));

        AnchorPane page = (AnchorPane) loader.load();

        Stage dialogStage = new Stage();
        dialogStage.getIcons().add(icon);
        dialogStage.setTitle(title);

        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(getPrimaryStage());
        Scene scene = new Scene(page);
        jMetro.setScene(scene);
        dialogStage.setScene(scene);

        return new ControlerHolder(dialogStage, loader);
    }

    @AllArgsConstructor
    private class ControlerHolder{
        Stage dialogStage;
        FXMLLoader loader;
    }

}
