package fr.vbillard.tissusdeprincesseboot.fxCustomElements;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import fr.vbillard.tissusdeprincesseboot.config.PathIconsProperties;
import fr.vbillard.tissusdeprincesseboot.exception.PersistanceException;
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


	public void washingMachinIcon(WebView view, double size, Paint color) {
		Resource path = pathProperties.getWashingMachine();
		loadSVG(view, path, color, size);
	}

	private void loadSVG(WebView view, Resource path, Paint color, double size) {

		view.setMinSize(size, size);
		view.setPrefSize(size, size);
		try {
			view.getEngine().load(path.getURL().toString());
		} catch (IOException e) {
			e.printStackTrace();
			throw new PersistanceException(path.getFilename());
		}
	}

	public ImageView recolor(Image source, ImageView view, Paint color) {
		ColorAdjust monochrome = new ColorAdjust();
		monochrome.setSaturation(-1.0);
		Blend blush = new Blend(BlendMode.SRC_ATOP, monochrome,
				new ColorInput(0, 0, source.getWidth(), source.getHeight(), color));
		view.setImage(source);
		view.setEffect(blush);
		return view;
	}

}
