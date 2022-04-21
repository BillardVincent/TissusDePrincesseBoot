package fr.vbillard.tissusdeprincesseboot.fxCustomElements;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import com.sun.javafx.webkit.Accessor;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import fr.vbillard.tissusdeprincesseboot.config.PathIconsProperties;
import fr.vbillard.tissusdeprincesseboot.exception.PersistanceException;
import fr.vbillard.tissusdeprincesseboot.model.enums.TypeTissuEnum;
import fr.vbillard.tissusdeprincesseboot.utils.svg.BufferedImageTranscoder;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.ColorInput;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import javafx.scene.web.WebView;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CustomIcon {

	PathIconsProperties pathProperties;

	public void washingMachinIcon(WebView view, double size) {
		Resource path = pathProperties.getWashingMachine();
		loadSVG(view, path, size);
	}

	public void noWashingMachinIcon(WebView view, double size) {
		Resource path = pathProperties.getNoWashingMachine();
		loadSVG(view, path, size);
	}

	public WebView typeTissu(TypeTissuEnum typeTissu) {
		Resource path = null;
		switch (typeTissu) {
		case CHAINE_ET_TRAME:
			path = pathProperties.getWashingMachine();
			break;
		case MAILLE:
			path = pathProperties.getWashingMachine();
			break;
		case MIXILIGNE:
			path = pathProperties.getWashingMachine();
			break;
		case NON_TISSE:
			path = pathProperties.getWashingMachine();
			break;
		case NON_RENSEIGNE:
			path = pathProperties.getWashingMachine();
		}

		WebView view = new WebView();
		loadSVG(view, path, 20);
		return view;
	}

	private void loadSVG(WebView view, Resource path, double size) {
		Accessor.getPageFor(view.getEngine()).setBackgroundColor(0);

		view.setMinSize(size, size);
		view.setPrefSize(size, size);
		try {
			view.getEngine().load(path.getURL().toString());
		} catch (IOException e) {
			e.printStackTrace();
			throw new PersistanceException(path.getFilename());
		}
	}

}
