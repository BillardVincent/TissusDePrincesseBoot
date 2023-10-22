package fr.vbillard.tissusdeprincesseboot;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

import javax.swing.*;
import java.awt.*;

@SpringBootApplication
public class TissuDePrincesseBootApplication {

	private static JFrame frame;

	public static void main(String[] args) {

		frame = new JFrame("Spring Boot Swing App");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300,300);
		JPanel panel = new JPanel(new BorderLayout());
		JTextField text = new JTextField("Spring Boot can be used with Swing apps");
		panel.add(text, BorderLayout.CENTER);
		frame.setContentPane(panel);
		frame.setVisible(true);
		Application.launch(TissusDePrincesseFxApp.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void afterStartup() {
		frame.setVisible(false);
		frame.dispose();
	}

}
