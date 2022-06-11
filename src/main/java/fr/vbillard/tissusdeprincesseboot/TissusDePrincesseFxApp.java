package fr.vbillard.tissusdeprincesseboot;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;

import fr.vbillard.tissusdeprincesseboot.exception.AbstractTissuDePricesseException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class TissusDePrincesseFxApp extends Application {

	private ConfigurableApplicationContext applicationContext;

	private Stage primaryStage;

	private Image icon = new Image("file:resources/images/cut-cloth-red.png");

	@Override
	public void init() {

		applicationContext = new SpringApplicationBuilder(TissuDePrincesseBootApplication.class).run();

	}

	@Override
	public void stop() {
		applicationContext.close();
		Platform.exit();
	}

	@Override
	public void start(Stage primaryStage) {
		applicationContext.publishEvent(new StageReadyEvent(primaryStage));
		Thread.setDefaultUncaughtExceptionHandler(((Thread t, Throwable e) -> showError(t, e)));

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
			if (e1 instanceof AbstractTissuDePricesseException) {
				alert = new Alert(((AbstractTissuDePricesseException) e1).getAlertType());
				alert.initOwner(primaryStage);
				alert.setTitle(((AbstractTissuDePricesseException) e1).getTitle());
				alert.setHeaderText(((AbstractTissuDePricesseException) e1).getHeader());
				alert.setContentText(((AbstractTissuDePricesseException) e1).getContent());
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
