package fr.vbillard.testintegrationtdp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import fr.vbillard.tissusdeprincesseboot.TissusDePrincesseFxApp;
import javafx.application.Application;

@SpringBootApplication(scanBasePackages = {"fr.vbillard.testintegrationtdp", "fr.vbillard.tissusdeprincesseboot"})
public class TestIntegrationTdpApplication {

  public static void main(String[] args) {
    Application.launch(TissusDePrincesseFxApp.class, args);
  }

}
