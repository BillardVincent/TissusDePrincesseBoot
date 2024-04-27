package fr.vbillard.tissusdeprincesseboot;

import fr.vbillard.tissusdeprincesseboot.controller.utils.ShowAlert;
import fr.vbillard.tissusdeprincesseboot.exception.AbstractTissuDePricesseException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;

import static org.apache.commons.lang3.StringUtils.defaultIfEmpty;

@Getter
public class TissusDePrincesseFxApp extends Application {

    private static ConfigurableApplicationContext applicationContext;

    private Stage primaryStage;

    private static final Logger LOGGER = LogManager.getLogger(TissusDePrincesseFxApp.class);

    @Override
    public void init() {
        applicationContext = new SpringApplicationBuilder(TissuDePrincesseBootApplication.class).headless(false).run();
    }

    public static void restart() {
        ApplicationArguments args = applicationContext.getBean(ApplicationArguments.class);

        Thread thread = new Thread(() -> {
            applicationContext.close();
            applicationContext = SpringApplication.run(Application.class, args.getSourceArgs());
        });

        thread.setDaemon(false);
        thread.start();
    }

    @Override
    public void stop() {
        applicationContext.close();
        Platform.exit();
    }

    @Override
    public void start(Stage primaryStage) {
        applicationContext.publishEvent(new StageReadyEvent(primaryStage));
        Thread.setDefaultUncaughtExceptionHandler((this::showError));

        this.primaryStage = primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void showError(Thread t, Throwable e) {
        LOGGER.error("***Default exception handler***");
        LOGGER.error(e);
        e.printStackTrace();
        if (Platform.isFxApplicationThread()) {

            Throwable e1 = e.getCause();
            if (e1.getCause() instanceof AbstractTissuDePricesseException ex) {
                ShowAlert.alert(ex.getAlertType() == null ? AlertType.ERROR : ex.getAlertType(), primaryStage,
                        defaultIfEmpty(ex.getTitle(), "Erreur inattendue"),
                        defaultIfEmpty(ex.getHeader(), "Une erreur est survenue"),
                        defaultIfEmpty(ex.getContent(),
                                "Veuillez nous excuser de la gène occasionnée. Contactez nous si le problème se répète"));
            } else {
                ShowAlert.alert(AlertType.ERROR, primaryStage, "Erreur inattendue", "Une erreur est survenue",
                        "Veuillez nous excuser de la gène occasionnée. Contactez nous si le problème se répète");
            }
        }
    }

    public static class StageReadyEvent extends ApplicationEvent {
        public StageReadyEvent(Stage primaryStage) {
            super(primaryStage);
        }

        public Stage getStage() {
            return (Stage) getSource();
        }
    }
}
