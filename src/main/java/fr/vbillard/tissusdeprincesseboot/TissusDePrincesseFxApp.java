package fr.vbillard.tissusdeprincesseboot;

import org.apache.logging.log4j.util.Strings;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;

import antlr.StringUtils;
import fr.vbillard.tissusdeprincesseboot.exception.AbstractTissuDePricesseException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class TissusDePrincesseFxApp extends Application {

	private static ConfigurableApplicationContext applicationContext;

	private Stage primaryStage;

	@Override
	public void init() {
		applicationContext = new SpringApplicationBuilder(TissuDePrincesseBootApplication.class).run();
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

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void main(String[] args) {
		launch(args);
	}

	private void showError(Thread t, Throwable e) {
		System.err.println("***Default exception handler***");
		e.printStackTrace();
		if (Platform.isFxApplicationThread()) {
			Alert alert;

			Throwable e1 = e.getCause();
			if (e1.getCause() instanceof AbstractTissuDePricesseException) {
				AbstractTissuDePricesseException ex = (AbstractTissuDePricesseException) e1.getCause();
				alert = new Alert(ex.getAlertType() == null ? AlertType.ERROR : ex.getAlertType());
				alert.initOwner(primaryStage);
				alert.setTitle(Strings.isEmpty(ex.getTitle()) ? "Erreur inatendue" : ex.getTitle()) ;
				alert.setHeaderText(Strings.isEmpty(ex.getHeader())? "Une erreur est survenue" : ex.getHeader());
				alert.setContentText(Strings.isEmpty(ex.getContent()) ? "Veuillez nous excuser de la gène occasionnée. Contactez nous si le problème se répète" : ex.getContent());
			} else {
				alert = new Alert(AlertType.ERROR);
				alert.initOwner(primaryStage);
				alert.setTitle("Erreur inatendue");
				alert.setHeaderText("Une erreur est survenue");
				alert.setContentText(
						"Veuillez nous excuser de la gène occasionnée. Contactez nous si le problème se répète");
			}
			alert.showAndWait();
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
