package fr.vbillard.tissusdeprincesseboot;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@SpringBootApplication
public class TissuDePrincesseBootApplication {

	private static JFrame frame;

	public static void main(String[] args) {

		System.setProperty("prism.allowhidpi", "false");
		System.setProperty("glass.win.uiScale", "100%");

		frame = new JFrame("Démarrage TissusDePrincesses");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300,213);
		try {
			//TODO refaire l'image
			BufferedImage myPicture = ImageIO.read(new File(".\\src\\main\\resources\\img\\intro.jpg"));
			Image dimg = myPicture.getScaledInstance(300, 213, Image.SCALE_DEFAULT);
			JLabel picLabel = new JLabel(new ImageIcon(dimg));
			frame.setContentPane(picLabel);
		}catch (IOException ex){
			JPanel panel = new JPanel(new BorderLayout());
			JTextField text = new JTextField("Les Tissus de Princesse");
			panel.add(text, BorderLayout.CENTER);
			frame.setContentPane(panel);
		}
		frame.setUndecorated(true);

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		Application.launch(TissusDePrincesseFxApp.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void afterStartup() {
		frame.setVisible(false);
		frame.dispose();
	}

}
