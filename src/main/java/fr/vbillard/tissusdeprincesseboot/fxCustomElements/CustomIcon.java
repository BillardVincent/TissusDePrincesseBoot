package fr.vbillard.tissusdeprincesseboot.fxCustomElements;

import java.io.IOException;

import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.config.PathIconsProperties;
import fr.vbillard.tissusdeprincesseboot.exception.PersistanceException;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CustomIcon {
	
	PathIconsProperties pathProperties;

	public ImageView washingMachinIcon() {
		String path;
		try {
			path = pathProperties.getWashingMachine().getURL().toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new PersistanceException("Erreur de chargement de l'image");
		}
		
		Image image = new Image(path, 50, 50, false, false);
		return new ImageView(image);
	}
	
}
